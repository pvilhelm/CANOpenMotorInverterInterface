/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;
import core.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.*;
/**
 *
 * @author fisksoppa
 */
public class MotorInverterController {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        try {
            File file = new File("./test.json");
            FileOutputStream out = new FileOutputStream(file);
            out.write('a');
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MotorInverterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MotorInverterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CANdevice kvDevice = new KvaserDevice();
        try {
            kvDevice.init(0);
            kvDevice.goOnBus();
            Message test = new Message(0,new byte[8],8,0);
            CANOpenMessageSDO test2 = new CANOpenMessageSDO();
            test2.setSDOdataToSend(new byte[]{0,4}, 10, 1, true);
            kvDevice.sendMessageBlocking(test);
            kvDevice.sendMessage(test2.getAsMessage());
        } catch (CANInterfaceException ex) {
            Logger.getLogger(MotorInverterController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}

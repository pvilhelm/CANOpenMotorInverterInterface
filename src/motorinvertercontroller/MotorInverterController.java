/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;
import core.*;
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
        CANdevice kvDevice = new KvaserDevice();
        try {
            kvDevice.init(0);
            kvDevice.goOnBus();
            Message test = new Message(0,new byte[8],8,0);
            kvDevice.sendMessageBlocking(test);
        } catch (CANInterfaceException ex) {
            Logger.getLogger(MotorInverterController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}

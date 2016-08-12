/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;
import core.*;
import obj.*;
/**
 *
 * @author fisksoppa
 */
public class MotorInverterController {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CanlibException {
        int msgId = 123;
        byte[] msgData = {0,1,2,3,4,5,6,7};
        int msgDlc = 8;
        int msgFlags = 0;

        System.out.println("Opening channel 0");
        Handle handle = new Handle(0);

        System.out.println("Setting channel bitrate");
        handle.setBusParams(Canlib.canBITRATE_250K, 0, 0, 0, 0, 0);

        System.out.println("Going on bus");
        handle.busOn();

        System.out.println("Writing a message to the channel");
        handle.write(new Message(msgId, msgData, msgDlc, msgFlags));

        System.out.println("Waiting until the message has been sent...");
        handle.writeSync(50);

        System.out.println("Going off bus");
        handle.busOff();

        System.out.println("Closing the channel");
        handle.close();
    }
    
}

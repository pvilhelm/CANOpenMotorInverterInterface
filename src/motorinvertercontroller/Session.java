/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;

import java.util.Timer;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.Message;

/**
 *
 * @author fisksoppa
 */
public class Session {
    LinkedBlockingDeque<Message> sendQueue;
    LinkedBlockingDeque<Message> receiveQueue;
    CANdevice kvDevice;
    CANtimerTask senderTaskPDO1;
    Timer timerSDO1;
    CANsenderTask senderTask;
    
    Session(){
        sendQueue = new LinkedBlockingDeque();
        receiveQueue = new LinkedBlockingDeque();
    }
    
    boolean initSession(){
        kvDevice = new KvaserDevice();
        try {
            kvDevice.init(0);
            kvDevice.goOnBus();
        } catch (CANInterfaceException ex) {
            Logger.getLogger(MotorInverterController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        senderTask = new CANsenderTask(kvDevice,sendQueue);
        Thread senderThread = new Thread(senderTask);
        senderThread.start();
        
        CANOpenMessageSDO sdo1 = new CANOpenMessageSDO();
        sdo1.setId(0x601);
        
        try {
            sdo1.setSDOdataToSend(new byte[]{1,2,3,4}, 1, 2, true);
        } catch (CANInterfaceException ex) {
            Logger.getLogger(MotorInverterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        timerSDO1 = new Timer();
        senderTaskPDO1 = new CANtimerTask(sendQueue);
        senderTaskPDO1.setMessage(sdo1.getAsMessage());
        
    }
    
    boolean sendSDO1(){
        timerSDO1.scheduleAtFixedRate(senderTaskPDO1, 0, 20);
    }
}

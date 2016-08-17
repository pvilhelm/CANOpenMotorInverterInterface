/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;

import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.CanlibException;
import obj.Handle;
import obj.Message;

/**
 *
 * @author fisksoppa
 */
public class CANsenderTask implements Runnable{
    
    boolean RUN = true; 

    public boolean isRUN() {
        return RUN;
    }

    public void setRUN(boolean RUN) {
        this.RUN = RUN;
    }
    CANdevice device; 
    LinkedBlockingDeque<Message> sendQueue; 
    
    CANsenderTask(CANdevice device, LinkedBlockingDeque sendQueue){
        this.device = device; 
        this.sendQueue = sendQueue;
    }
    
    @Override
    public void run(){
        while(RUN){
            Message message;
            try {
                message = sendQueue.take();
            } catch (InterruptedException ex) {
                Logger.getLogger(CANsenderTask.class.getName()).log(Level.SEVERE, null, ex);
                continue;  
            }
            
            try {
                device.sendMessageBlocking(message);
            } catch (CANInterfaceException ex) {
                Logger.getLogger(CANsenderTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}

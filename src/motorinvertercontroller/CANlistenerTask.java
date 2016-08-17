/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.Message;

/**
 *
 * @author fisksoppa
 */
public class CANlistenerTask implements Runnable{
    boolean RUN = true; 

    public boolean isRUN() {
        return RUN;
    }

    public void setRUN(boolean RUN) {
        this.RUN = RUN;
    }
    CANdevice device; 
    LinkedBlockingDeque<Message> receiveQueue; 
    
    CANlistenerTask(CANdevice device, LinkedBlockingDeque receiveQueue){
        this.device = device; 
        this.receiveQueue = receiveQueue;
    }
    
    @Override
    public void run(){
        while(RUN){
            Message message = null;
            try {
                message = device.getMessageBlocking();
            } catch (CANInterfaceException ex) {
                Logger.getLogger(CANsenderTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            if(message!= null)
                receiveQueue.push(message);
            
            
        }
    }
    
    
}

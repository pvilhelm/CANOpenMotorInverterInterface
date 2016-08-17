/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingDeque;
import obj.Message;

/**
 *
 * @author fisksoppa
 */
class CANtimerTask extends TimerTask{
        
        Message message; 
        LinkedBlockingDeque<Message> sendQueue; 
        
        synchronized public Message getMessage() {
            return message;
        }

        synchronized public void setMessage(Message message) {
            this.message = message;
        }
        
        
        CANtimerTask(LinkedBlockingDeque sendQueue){
            this.sendQueue = sendQueue; 
        }
        
        @Override
        public void run(){
            Message messageToSend = getMessage();
            
            if(messageToSend!=null)
                sendQueue.push(message);
        }
        
    }

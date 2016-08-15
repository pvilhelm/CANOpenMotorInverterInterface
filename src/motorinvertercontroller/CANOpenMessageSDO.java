/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;

import core.Canlib;
import obj.CanlibException;
import obj.Handle;
import obj.Message;
import java.util.Date;

/**
 *
 * @author fisksoppa
 */
public class CANOpenMessageSDO extends CANOpenMessage{
    
    CANOpenMessageSDO(Message message){
        super(message);
    }
    
    CANOpenMessageSDO(int id, byte[] data, int flags){
        super(id, data, flags);
    }
    
    CANOpenMessageSDO(){
        
    }
    
    boolean setSDOdata(byte[] data, int index, int subindex){
        if(data.length>6)
            return false; 
        if(index> 1<<16)
            return false;
        if(subindex > 255)
            return false; 
        
        else{
            this.data = new byte[data.length+2];
        }
            
    }
}

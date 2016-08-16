/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;

import core.Canlib;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
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
    byte[] getSDOData() throws CANInterfaceException{
        int cmd = getSDOCmd();
        switch(cmd){
            case(0x4f):
                if(this.data.length<5)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,5);
            case(0x4b):
                if(this.data.length<6)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,6);
            case(0x47):
                if(this.data.length<7)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,7);
            case(0x43):
                if(this.data.length<8)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,8);
            case(0x2f):
                if(this.data.length<5)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,5);
            case(0x2b):
                if(this.data.length<6)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,6);
            case(0x27):
                if(this.data.length<7)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,7);
            case(0x23):
                if(this.data.length<8)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,8);
            case(0x40):case(0x22):case(60):
                if(this.data.length<5)
                    throw new CANInterfaceException("CAN SDO received message data length out of spec.");
                return Arrays.copyOfRange(this.getData(),4,Math.min(8,this.data.length));  
            default:
                throw new CANInterfaceException("CAN SDO received message command byte out of spec.");
        }
    }
    
    byte getSDOCmd(){
        return data[0];
    }
    
    void setSDOdataToSend(byte[] data, int index, int subindex, boolean write) throws CANInterfaceException {
        if(data == null || !write)
            data = new byte[0];
        if(data.length>4)
            throw new CANInterfaceException("SDO Data length over 4b"); 
        if(index> ~((~0)<<16))
            throw new CANInterfaceException("SDO Index not short"); 
        if(subindex > 255)
            throw new CANInterfaceException("SDO Sub-index not byte"); 
        
 
        {
            this.data = new byte[data.length+4];
            ByteBuffer dataBuffer = ByteBuffer.wrap(this.data);
            dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
            if(write){
                
                switch(data.length){
                    case(1):
                        dataBuffer.put((byte)0x2f);
                        break;
                    case(2):
                        dataBuffer.put((byte)0x2b);
                        break;
                    case(4):
                        dataBuffer.put((byte)0x23);
                        break;
                    default:
                        throw new CANInterfaceException("SDO Write data length out of spec. Length:"+data.length); 
                }
            }
            
            else{
                dataBuffer.put((byte)0x40);
            }
            
            dataBuffer.putChar((char)index);
            dataBuffer.put((byte)subindex);
            if(write)
                dataBuffer.put(data);
            
            return;
        }
    }
}

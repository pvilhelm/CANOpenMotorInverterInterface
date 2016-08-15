/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinvertercontroller;

import core.Canlib;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.CanlibException;
import obj.Handle;
import obj.Message;

/**
 *
 * @author fisksoppa
 */
public class KvaserDevice extends CANdevice{
    Handle handle;
    KvaserDevice(){
    }
    
    boolean init(int channel) throws CANInterfaceException {
        try {
            System.out.println("Opening channel "+channel);
            handle = new Handle(channel);
            this.nameOfDevice = handle.getBusTypeAsString();
            handle.setBusParams(Canlib.canBITRATE_250K, 0, 0, 0, 0, 0);
            return true;
        } catch (CanlibException ex) {
            Logger.getLogger(KvaserDevice.class.getName()).log(Level.SEVERE, null, ex);
            throw(new CANInterfaceException(ex.toString()));
        }
    }
    
     
    boolean goOffBus() throws CANInterfaceException {
        if(handle == null)
            throw(new CANInterfaceException("CAN interface object is null"));
        try {
            handle.busOff();
        } catch (CanlibException ex) {
            Logger.getLogger(KvaserDevice.class.getName()).log(Level.SEVERE, null, ex);
            throw(new CANInterfaceException(ex.toString()));
        }
        return true; 
    }
    
    boolean goOnBus() throws CANInterfaceException {
        if(handle==null)
            throw(new CANInterfaceException("CAN interface object is null"));
        try {
            handle.busOn();
        } catch (CanlibException ex) {
            Logger.getLogger(KvaserDevice.class.getName()).log(Level.SEVERE, null, ex);
            throw(new CANInterfaceException(ex.toString()));
        }
        return true; 
    }
    
    boolean setBitrate(int bitrate) throws CANInterfaceException {
        if(handle==null)
            throw(new CANInterfaceException("CAN interface object is null"));
        try {
            handle.setBitrate(bitrate);
            
        } catch (CanlibException ex) {
            Logger.getLogger(KvaserDevice.class.getName()).log(Level.SEVERE, null, ex);
            throw(new CANInterfaceException(ex.toString()));
        }
        return true; 
    }
    
    Message getMessageBlocking() throws CANInterfaceException{
        if(handle==null)
            throw(new CANInterfaceException("CAN interface object is null"));
        try {
            return handle.readWait(Long.MAX_VALUE);
        } catch (CanlibException ex) {
            Logger.getLogger(KvaserDevice.class.getName()).log(Level.SEVERE, null, ex);
            throw(new CANInterfaceException(ex.toString()));
        }
    }
    
    boolean sendMessage(Message message) throws CANInterfaceException{
        if(handle==null)
            throw(new CANInterfaceException("CAN interface object is null"));
        try {
            handle.write(message);
        } catch (CanlibException ex) {
            Logger.getLogger(KvaserDevice.class.getName()).log(Level.SEVERE, null, ex);
            throw(new CANInterfaceException(ex.toString()));
        }
        return true; 
    }
    
    boolean sendMessageBlocking(Message message) throws CANInterfaceException{
        if(handle==null)
            throw(new CANInterfaceException("CAN interface object is null"));
        try {
            handle.write(message);
            handle.writeSync(Long.MAX_VALUE);
        } catch (CanlibException ex) {
            Logger.getLogger(KvaserDevice.class.getName()).log(Level.SEVERE, null, ex);
            throw(new CANInterfaceException(ex.toString()));
        }
        return true; 
    }
}

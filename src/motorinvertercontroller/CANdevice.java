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
public abstract class CANdevice {
    String nameOfDevice;
    int nrOfChannels;
    
    abstract boolean setBitrate(int bitrate) throws CANInterfaceException;
    abstract Message getMessageBlocking() throws CANInterfaceException;
    abstract boolean sendMessage(Message message) throws CANInterfaceException;
    abstract boolean sendMessageBlocking(Message message) throws CANInterfaceException;
    abstract boolean goOnBus() throws CANInterfaceException;
    abstract boolean goOffBus()throws CANInterfaceException;
    abstract boolean init(int channel) throws CANInterfaceException;
}

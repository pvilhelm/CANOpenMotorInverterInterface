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
 * @author petter
 */
public class CANOpenMessage {
    int id;
    int flags;
    byte[] data; 
    long remoteTimestamp;
    long localTimestamp;
    int nodeId; 
    int SDOdataLength;
    int functionCode; 
    CANOpenType type;

    public long getRemoteTimestamp() {
        return remoteTimestamp;
    }

    public void setRemoteTimestamp(long remoteTimestamp) {
        this.remoteTimestamp = remoteTimestamp;
    }

    public long getLocalTimestamp() {
        return localTimestamp;
    }

    public void setLocalTimestamp(long localTimestamp) {
        this.localTimestamp = localTimestamp;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getDataLength() {
        return SDOdataLength;
    }

    public void setDataLength(int dataLength) {
        this.SDOdataLength = dataLength;
    }

    public int getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(int functionCode) {
        this.functionCode = functionCode;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flag) {
        this.flags = flag;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    CANOpenMessage(){
        
    }
    
    CANOpenMessage(Message message){
        this.id = message.id;
        this.data = message.data;
        this.flags = message.flags;
        this.remoteTimestamp = message.time;
        this.localTimestamp = new Date().getTime();
        this.type = parseCANOpenType(message);
        if(this.type != CANOpenType.NOT_CANOPEN){
            this.nodeId = parseNodeId(this.id);
            this.functionCode = parseFunctionCode(this.id);
        }
        
    }
    
    CANOpenMessage(int id, byte[] data, int flags){
        this.id = id;
        this.data = data;
        this.flags = flags;
        this.remoteTimestamp = 0;
        this.localTimestamp = new Date().getTime();
        this.functionCode = parseFunctionCode(this.id);
        this.nodeId = parseNodeId(this.id);
    }
    
    Message getAsMessage(){
        Message message = new Message(id,data,data.length,flags);
        return message; 
    }
    
    static int parseNodeId(int id){
        final int mask = 0b11111110000;
        return (id & mask) >>> 4; 
    }
    
    static int parseFunctionCode(int id){
        final int mask = 0b1111;
        return mask & id; 
    }
    
    static boolean mightBeCANOpenMessage(Message message){
        int id = message.id;
        
        if(CANOpenIdRanges.getCANOpenTypeFromId(id)==CANOpenType.NOT_CANOPEN)
            return false;
        else
            return true;
    }
    
    static class CANOpenIdRange{
        int low;
        int high;
        CANOpenType type; 
        
        CANOpenIdRange(int low,int high,CANOpenType type){
            this.low = low;
            this.high = high;
            this.type = type; 
            assert low<=high;
        }
        
        boolean isInRange(int id){
            return id>= low && id<=high;
        }
        
        CANOpenType getType(){
            return type;
        }
        
    }
    
    static class CANOpenIdRanges{
        static CANOpenIdRange[] CANOpenIdRangeArray = 
        {new CANOpenIdRange(0,0,CANOpenType.NMT),
         new CANOpenIdRange(0x80,0x80,CANOpenType.SYNC),
         new CANOpenIdRange(0x81,0xff,CANOpenType.EMCY),
         new CANOpenIdRange(0x100,0x100,CANOpenType.TIME),
         new CANOpenIdRange(0x181,0x1ff,CANOpenType.PDO),
         new CANOpenIdRange(0x201,0x27f,CANOpenType.PDO),
         new CANOpenIdRange(0x281,0x2ff,CANOpenType.PDO),
         new CANOpenIdRange(0x301,0x37f,CANOpenType.PDO),
         new CANOpenIdRange(0x381,0x3ff,CANOpenType.PDO),
         new CANOpenIdRange(0x401,0x47f,CANOpenType.PDO),
         new CANOpenIdRange(0x481,0x4ff,CANOpenType.PDO),
         new CANOpenIdRange(0x501,0x57f,CANOpenType.PDO),
         new CANOpenIdRange(0x581,0x5ff,CANOpenType.SDO),
         new CANOpenIdRange(0x601,0x67f,CANOpenType.SDO),
         new CANOpenIdRange(0x701,0x77f,CANOpenType.LSS),
        };
        
        static CANOpenType getCANOpenTypeFromId(int id){
            for(CANOpenIdRange object : CANOpenIdRangeArray){
                if(object.isInRange(id))
                    return object.getType();
            }
            return CANOpenType.NOT_CANOPEN;
        }
    }
    /*
    //Dont change without fixing parseCANOpenType() and mightBeCANOpenMessage()
    static int[][] CANOpenIdRanges = 
    {       {0,0},//NMT
            {0x80,0x80},//SYNC
            {0x81,0xff},//EMCY
            {0x100,0x100},//TIME
            {0x181,0x1ff},//PDO1 tx
            {0x201,0x27f},//PDO1 rx
            {0x281,0x2ff},//PDO2 tx
            {0x301,0x37f},//PDO2 rx
            {0x381,0x3ff},//PDO3 tx
            {0x401,0x47f},//PDO3 tx
            {0x481,0x4ff},//PDO4 rx
            {0x501,0x57f},//PDO4 tx
            {0x581,0x5ff},//SDO tx
            {0x601,0x67f},//SDO rx
            {0x701,0x77f},//NMT-EC
            {0x7e4,0x7e5}//LSS
    };
    */
    static enum CANOpenType {
        NMT,
        SYNC,
        EMCY,
        TIME,
        PDO,
        SDO,
        NMT_EC,
        LSS,
        NOT_CANOPEN
    }
    
    static CANOpenType parseCANOpenType(Message message){
        if(message==null)
            return CANOpenType.NOT_CANOPEN;
        return CANOpenIdRanges.getCANOpenTypeFromId(message.id);
    }
}

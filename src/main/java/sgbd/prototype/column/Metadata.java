package sgbd.prototype.column;

public class Metadata {


    public static final short NONE= 0;

    public static final short DINAMIC_COLUMN_SIZE = (1<<0);

    public static final short CAN_NULL_COLUMN = (1<<1);

    public static final short LSHIFT_8_SIZE_COLUMN = (1<<2);

    public static final short SIGNED_INTEGER_COLUMN = (1<<3);

    public static final short PRIMARY_KEY = (1<<4);

    public static final short STRING = (1<<5);

    public static final short FLOATING_POINT = (1<<6);

    public static final short BOOLEAN = (1<<7);

    private short size;
    private short flags;

    public Metadata(short size,short flags){
        this.size = size;
        this.flags = flags;
    }
    public Metadata(Metadata metadata){
        this.size = metadata.size;
        this.flags = metadata.flags;
    }

    public int getSize() {
        if(isBoolean())return 1;
        if(isShift8Size())
            return size<<8;
        else
            return size;
    }

    public short getFlags(){
        return flags;
    }

    public boolean isDinamicSize(){
        return (flags&DINAMIC_COLUMN_SIZE)!=0;
    }
    public boolean isShift8Size(){
        return (flags&LSHIFT_8_SIZE_COLUMN)!=0;
    }
    public boolean isSignedInteger(){
        return (flags&SIGNED_INTEGER_COLUMN)!=0;
    }
    public boolean camBeNull(){
        return (flags&CAN_NULL_COLUMN)!=0;
    }
    public boolean isPrimaryKey(){
        return (flags&PRIMARY_KEY)!=0;
    }


    public boolean isInt(){
        return (!isString() && (flags&FLOATING_POINT)==0 && !isDinamicSize()) || (flags&SIGNED_INTEGER_COLUMN)!=0;
    }
    public boolean isLong(){
        return this.isInt() && size==8;
    }
    public boolean isDouble(){
        return (flags&FLOATING_POINT)!=0 && size==8;
    }
    public boolean isFloat(){
        return (flags&FLOATING_POINT)!=0 && size==4;
    }
    public boolean isString(){
        return (flags&STRING)!=0;
    }
    public boolean isBoolean(){
        return (flags&BOOLEAN)!=0;
    }

}

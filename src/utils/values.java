package utils;

final public class values {
    public static final String[] columnNames= {"RemoteIP","Host","Bytes In", "Bytes Out", "Speed In", "Speed Out","Idle Time",
        "Avg In", "Avg Out", "Max In", "Max Out"};
    public static final int columnNumber= columnNames.length;

    /**
     * Adds a leading zero if <code>aNumber</code> is less than 10
     * @param aNumber   source value
     * @return  <code>aNumber</code> converted to String
     */
    public static final String  make2(long aNumber){
        if (aNumber<10) return "0"+(new Long(aNumber)).toString();
        else return (new Long(aNumber)).toString();
    }

    public static final String getTimeFromSec(long aSec){
        long mSec= aSec;
        long hours= aSec / (3600);
        mSec-= hours*3600;
        long min= mSec/(60);
        mSec-= min*60;
        long sec= mSec;
        String result= make2(hours)+":"+make2(min)+":"+ make2(sec);
        return result;
    }

    public static final long truncateValue(long aValue){
        long value= aValue, val=1;

        while (value>100){
            value/= 10;
            val*=10;
        }
        return value*val;
    }

    public static final String valueKB(long value){
        long f=0, l=0;
        String num= new String();
        if (value>1024*1024){ // >1 MB
            f= value/(1024*1024);
            l= value%(1024*1024) /10000;
            num= "MB";
        }
        else
        if (value>9999){    //<1MB >1KB
            f= value/1024;
            l= value%1024/100;
            num= "KB";
        }
        else
        if (value>0){  //<1KB
            f= value;
            return (new Long(f)).toString()+ " B";
        }
        else {return "--";}  // in case of 0

        return f+"."+l+" "+num;
    }

    public static final String valueKBpS(long value){
        if (value==0) return "--";
            else return valueKB(value)+"/s";
    }
}

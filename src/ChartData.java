/********************************************************
 * IP Traffic Client
 * януари 2003
 * Автори:
 *      Иван Дилов  ф.н. 42678
 *      Пламен Игнатов  ф.н. 42683
 ********************************************************/

/**
 * class:   ChartData
 * task:    Information about single chart data
 */
public class ChartData {
    private long[] mData;
    private long min;
    private long max;
    private int length=0;

    public ChartData(int length) {
        mData= new long[length];
        this.length= length;
    }

    public int getLength() {
        return length;
    }

    /**
     * Adds a new value to the
     * @param value
     */
    public void add(long value){
        long newmin= value, newmax= value;
        for (int i=1; i< mData.length; i++){
            if (newmin> mData[i]) newmin= mData[i];
            if (newmax< mData[i]) newmax= mData[i];
            mData[i-1]= mData[i];
        }
        //not very elegant
        mData[length-1]= value;
        min= newmin;
        max= newmax;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public long getAt(int i){
        return mData[i];
    }
}

package utils;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: Dec 28, 2003
 * Time: 10:47:37 PM
 * To change this template use Options | File Templates.
 */
public class tools {
    public static int getMin(int[] aBuffer, int aOffset, int aLength){
        int returnValue= aBuffer[aOffset];
        for (int i=aOffset+1; i<aOffset+aLength; i++){
            if(aBuffer[i]< returnValue)
                returnValue= aBuffer[i];
        }
        return returnValue;
    }

    public static int getMean(int[] aBuffer , int aOffset, int aLength){
        float meanValue= aBuffer[aOffset];
        int samples= 1;
        for (int i=aOffset+1; i<aOffset+aLength; i++){
            samples++;
            meanValue+= ((float)aBuffer[i] - meanValue)/samples;
        }
        return (int)meanValue;
    }

    public static void normalize(int meanValue, int[] aBuffer, int aOffset, int aLength){
        for (int i=aOffset; i<aOffset+aLength; i++){
               aBuffer[i] -= meanValue;
        }
    }

    public static void filter(int threshold, int[] aBuffer, int aOffset, int aLength){
        for (int i=aOffset; i<aOffset+aLength; i++){
              if((aBuffer[i]< threshold)  && (aBuffer[i]> -threshold))  aBuffer[i]= 0;
        }
    }
}

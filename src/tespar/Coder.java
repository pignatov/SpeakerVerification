package tespar;

import java.util.ArrayList;

public class Coder {
    public byte[] Encode(int[] aBuffer, int aOffset,  int aLength){
        byte[]   returnArray;
        ArrayList arrayList= new ArrayList();
        int lastValue = aBuffer[aOffset];
        int lastZeroCrossing = aOffset;
        int currentEpoch = 0;
        boolean positive = aBuffer[aOffset]>0 ? true: false;
        int D = 0;
        int S = 0;

        for(int i = aOffset+1; i < aOffset+aLength-1; i++){
            if(aBuffer[ i ] * lastValue < 0)    //Zero Crossing -> new Epoch
            {
                positive= aBuffer[i] >0 ? true: false;
                D = i - lastZeroCrossing;
                System.out.println("D: "+D+" S: "+S);
                arrayList.add(currentEpoch, new Integer(Alphabet.get(D,S)));
                lastZeroCrossing = i;
                currentEpoch++;
                S = 0;
                D = 0;
            }

            int tmp1=lastValue-aBuffer[i];
            int tmp2=aBuffer[i]-aBuffer[i+1];

            if( tmp1 * tmp2 < 0){ //We have an extremum
                if (positive) {
                    if (tmp1 >0 ) S++;
                }
                else{
                    if (tmp1 <0 ) S++;
                }
            }
            lastValue = aBuffer[i];
        }

        returnArray= new byte[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++)
              returnArray[i]= ((Integer)arrayList.get(i)).byteValue();

        return returnArray;
    }

    public int[] EncodeSTespar(int[] aBuffer, int aOffset,  int aLength){
        int[] stespar = new int[28];
        byte[] output = Encode(aBuffer, aOffset, aLength);
        for (int i = 0; i < output.length; i++){
            stespar[output[i]]++;
        }
        return stespar;
    }
}

package sound;

import gui.JChart;
import gui.ChartData;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioSystem;

import utils.tools;
import tespar.Coder;

public class Record  extends Thread{
    JChart mChart;
    ChartData mChartData;
    boolean hasStarted = false;

    public Record(JChart aChart, ChartData aChartData) {
        mChart = aChart;
        mChartData = aChartData;
    }

    private  AudioFormat getAudioFormat() {
        float sampleRate = 11025.0F;
        //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        //8,16
        int channels = 1;
        //1,2 = mono/stereo
        boolean signed = true;
        //true,false
        boolean bigEndian = true;
        //true,false
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }//end getAudioFormat

    public boolean isStarted() {
        return hasStarted;
    }

    public void run() {
        super.run();
        AudioFormat audioFormat;
        TargetDataLine targetDataLine;
        int meanValue;

        try {
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            hasStarted = true;

            int[] endBuffer = new int[50000];
            byte[] buffer = new byte[200];
            int[] newBuffer= new int[100];
            int current_sample = 0;
            int j = 0;
            meanValue = 5500;

            while (j < 400) {
                targetDataLine.read(buffer, 0, 200);
                j++;
                for (int i = 0; i < buffer.length; i += 2) {
                        newBuffer[i/2]= (buffer[i]+ buffer[i+1]*256);
                    if(i %10 == 0){
                        int entry = newBuffer[i/2]-5500;
                        if (entry <500 && entry > -500) entry = 0;
                        mChartData.add(entry);
                        meanValue= tools.getMean(newBuffer,0,newBuffer.length);
                        endBuffer[current_sample] = entry;
                        current_sample++;
                    }
                }

                mChart.repaint();
//                tools.normalize(meanValue, newBuffer,0,newBuffer.length);
//                System.out.println("Mean Value: "+meanValue);
//                tools.filter(300, newBuffer,0,newBuffer.length);

            }
                Coder coder = new Coder();
                int[] tes = coder.EncodeSTespar(endBuffer, 0, current_sample);
                for(int k = 0; k< tes.length; k++){
                    System.out.println(k+":"+tes[k]);
                }

            targetDataLine.close();
        } catch (Exception e) {
            e.printStackTrace();
        }//end catch

    }
}

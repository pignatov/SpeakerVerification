
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.sound.sampled.*;

import tespar.*;

import java.io.*;


public class AudioRec extends JFrame {
    public AudioRec() {
        super("Title");
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JButton btnOK = new JButton("OK");
        content.add("South", btnOK);

        final ChartData data1 = new ChartData(500);
        data1.add(530);
        ChartData data2 = new ChartData(500);
//        content.add("Center",chart);
        ActionListener alOK = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Capture();
            }
        };
        btnOK.addActionListener(alOK);
    }

    private static AudioFormat getAudioFormat() {
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

    public void Capture() {
        AudioFormat audioFormat;
        TargetDataLine targetDataLine;
        try {
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            byte[] buffer = new byte[16000];
            int[] newBuffer= new int[8000];
            int j = 0;

            while (j < 1) {
                targetDataLine.read(buffer, 0, 16000);
                j++;
                for (int i = 0; i < buffer.length; i += 2) {
                    newBuffer[i/2]= (buffer[i]+ buffer[i+1]*256);
                }

                Coder coder = new Coder();
                int meanValue= tools.getMean(newBuffer,0,newBuffer.length);
                tools.normalize(meanValue, newBuffer,0,newBuffer.length);
                System.out.println("Mean Value: "+meanValue);
                tools.filter(300, newBuffer,0,newBuffer.length);
                System.out.println();

//                for (int i = 0; i < newBuffer.length; i += 1) {
//                    System.out.print(" "+newBuffer[i]);
//                }


//                System.out.println(tools.getMean(newBuffer,0,newBuffer.length));

                System.out.println("----------TESPAR start-----------");
                int[] tesparEncoded = coder.EncodeSTespar(newBuffer, 0, 8000);
                try{
                for (int tmp = 0; tmp < tesparEncoded.length; tmp++) {
                    System.out.println(tmp+":"+tesparEncoded[tmp]);
                }
                }catch (Exception ex){
                    System.out.println("***");
                }
                System.out.println("----------TESPAR end-----------"+tesparEncoded.length);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }//end catch
    }

    public static void main(String[] args) {
        AudioRec frame = new AudioRec();
        frame.pack();
        frame.show();
    }
}

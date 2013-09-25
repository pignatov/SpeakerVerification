package gui;

import sound.Record;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

public class SoundSample extends JFrame{
    javax.swing.Timer mTimer;
    private JProgressBar mProgressBar;
    private JPanel mButtonsPanel;
    private JPanel mMainPanel;
    private JButton btnRecord;
    private JChart mChart;
    private ChartData mData;
    Record record;

    Random random;

    public SoundSample(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        InitializeComponenets();
        random = new Random();
        record = new Record(mChart, mData);

    }

    private void InitializeComponenets(){
        Container content = getContentPane();
        mData = new ChartData(500);
        mChart = new JChart(mData);

        mButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mMainPanel = new JPanel(new BorderLayout());
        btnRecord = new JButton("Record");
        mProgressBar = new JProgressBar(0,100);
        mMainPanel.add("South", mProgressBar);
        mMainPanel.add("Center", mChart);
        mButtonsPanel.add(btnRecord);
        content.add("Center", mMainPanel);
        content.add("South", mButtonsPanel);
        mTimer = new Timer(20, new TimerActionListener());
        btnRecord.addActionListener(new btnRecordActionListener());
    }

    public static void main(String[] args){
        SoundSample form = new SoundSample("Recording");
        form.pack();
        form.show();
    }

    public final class TimerActionListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            mProgressBar.setValue( mProgressBar.getValue() + 1);
            mChart.repaint();
            if (mProgressBar.getValue() == mProgressBar.getMaximum()){
                btnRecord.setEnabled(true);
                mTimer.stop();
            }
        }
    }

    public final class btnRecordActionListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            record.start();
            try{
            while(!record.isStarted()){
                Thread.sleep(100);
            }
            }catch (Exception ex){

            }

            mProgressBar.setValue(0);
            mTimer.start();
            btnRecord.setEnabled(false);

        }
    }
}

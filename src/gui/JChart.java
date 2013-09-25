package gui;

import utils.*;
import javax.swing.*;
import java.awt.*;

public class JChart extends JPanel{
    private ChartData mFirstData;
    private long currentH= 20000;

    public JChart(ChartData aFirstData) {
        mFirstData= aFirstData;
    }

    public void paint(Graphics g){
        super.paint(g);
        int startX= 50;
        int startY= 5;
        int height= getHeight()- startY;
        int width= getWidth()- startX;
        float divisionX= (float)width/(float)500;
        float divisionY= (float)height/(float)10;
        g.drawLine(startX,startY,startX, height);
        g.drawLine(startX,height, width, height);

        int jump=3;
        int iter=0;
        for(float i= startX;i< width+startX; i+= (divisionX) ){
            iter++;
            if(iter%10==0)jump=5;
                else jump=3;
            g.drawLine(Math.round(i), height-jump, Math.round(i), height);
        }

        for(int i= height;i> startY; i-=Math.round(divisionY) ){
            g.drawLine(startX,i, startX+3, i);
        }

        g.setColor(Color.GRAY);
        g.drawLine(startX,(height-startY)/2+1,width+startX, (height-startY)/2+1);

        g.setColor(Color.RED);
        //Enlarges the drawing area if necessary
/*        if (mFirstData.getMax()>=currentH || mSecondData.getMax()>=currentH)
            currentH= java.lang.Math.max(mFirstData.getMax()*3/2+1,mSecondData.getMax()*3/2+1);

        if (mFirstData.getMax()<=currentH/2 && mSecondData.getMax()<=currentH/2)
            currentH= java.lang.Math.min(mFirstData.getMax()*3/2+1,mSecondData.getMax()*3/2+1);*/

        long value_to_draw, previous_to_draw;
        for (int i=1; i<mFirstData.getLength(); i++){
            value_to_draw= mFirstData.getAt(i) + currentH/2;
            previous_to_draw= mFirstData.getAt(i-1) + currentH/2;
            g.drawLine(startX+(i-1)*width/500, (height- (int)previous_to_draw*height/(int)currentH), startX+i*width/500, (height- (int)value_to_draw*height/(int)currentH));
        }

/*        g.setColor(Color.BLACK);
        g.drawString((new Long(currentH)).toString(), 1,15);
        g.drawString("0", 1,height/2+5);
        g.drawString((new Long(-currentH)).toString(), 1,height);*/
    }

}

package neural;

import java.io.Serializable;

public class TrainingTest implements Serializable{
    double[] mInputData;
    double[] mDesiredOutput;

    public TrainingTest(double[] aInputData, double[] aDesiredOutput) {
        this.mInputData = new double[aInputData.length];
        this.mDesiredOutput = new double[aDesiredOutput.length];
        for (int i = 0; i < aInputData.length; i++)
            mInputData[i] = aInputData[i];
        for (int i = 0; i < aDesiredOutput.length; i++)
            mDesiredOutput[i] = aDesiredOutput[i];
    }

    public double[] getInputData() {
        return mInputData;
    }

    public double[] getDesiredOutput() {
        return mDesiredOutput;
    }
}

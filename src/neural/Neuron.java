package neural;

import java.io.Serializable;

public class Neuron implements Serializable{
    double mInput;
    double mOutput;
    double delta;
    boolean isChanged = true;

    public Neuron() {
        this.mInput = this.mOutput = this.delta = 0.0d;
    }

    public void add( double value){
        mInput += value;
        isChanged = true;
    }

    public void reset(){
        mInput = 0.0d;
        isChanged = true;
    }

    private double calculate(){
        double value;
        if (isChanged)
            value = sigmoid(mInput);
        else
            value = mOutput;
        isChanged = false;
        return value;
    }

    public double getOutput(){
        mOutput = calculate();
        return mOutput;
    }

    protected double sigmoid(double x){
        double value = 1 / ( 1 + java.lang.Math.exp(-x));
//        double value = 3 * x;
        return   value;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public void setDeltaSpecial(double delta){
        this.delta = getOutput() * delta;
    }

}

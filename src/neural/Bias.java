package neural;

/**
 * This class represents a special type of neurons - Bias neuron
 * It produces constant 1
 */
public class Bias extends Neuron{
    public double getOutput(){
        return 1;
    }
}

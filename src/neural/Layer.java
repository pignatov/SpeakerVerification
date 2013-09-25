package neural;

import java.util.Random;
import java.io.Serializable;

public class Layer implements Serializable{
    double[][] weights;
    Random random;
    int mNeuronsNet1, mNeuronsNet2;

    public Layer(int aNeuronsNet1, int aNeuronsNet2){
        //One more neuron for the bias
        this.mNeuronsNet1 = aNeuronsNet1 + 1;
        this.mNeuronsNet2 = aNeuronsNet2;
        random = new Random();
        weights = new double[mNeuronsNet1][mNeuronsNet2] ;
        initializeWeights();
    }

    /**
     * Initialize all weights with random float numbers between 0.0 and 1.0
     */
    public void initializeWeights(){
        for (int i = 0; i < mNeuronsNet1; i++)
            for (int j = 0; j < mNeuronsNet2; j++){
                weights[i][j] = random.nextDouble() - 0.5d;
        }
    }

    /**
     * Get weight of the connection between <code>aNeuron1</code> from the first level
     * and <code>aNeuron2</code> fron the second level
     * @param aNeuron1 Index of the first neuron
     * @param aNeuron2 Index of the second neuron
     * @return  weight of connection
     */
    public double get(int aNeuron1, int aNeuron2){
        return weights[aNeuron1][aNeuron2];
    }

    public void set(int aNeuron1, int aNeuron2, double aNewValue){
        weights[aNeuron1][aNeuron2] = aNewValue;
    }

}

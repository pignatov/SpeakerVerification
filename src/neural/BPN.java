package neural;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Back Propagation Network
 */

public class BPN implements Serializable{
    ArrayList mLevels;
    ArrayList mLayers;
    ArrayList mTrainingTests;
    int mCurrentTest;
    int mNumberOfLevels;
    int mNumberOfLayers;
    int[] mNumberOfNeurons;
    int mCountInputNeurons;
    int mCountOutputNeurons;
    int maxIterations = 10000;
    int maxGIterations = 10000;
    double eta = 0.15;

    public int getMaxTestIterations() {
        return maxIterations;
    }

   /**
    * Set maximum number of iterations within a single pattern. Default is <code>10000</code>
    * @param maxIterations Number of iterations
    */
    public void setMaxTestIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public int getMaxGlobalIterations() {
        return maxGIterations;
    }

    /**
     * Set maximum number of iterations performed for a training suite of patterns. Default is <code>10000</code>
     * @param maxGIterations Number of iterations
     */
    public void setMaxGlobalIterations(int maxGIterations) {
        this.maxGIterations = maxGIterations;
    }

    public double getLearningRate() {
        return eta;
    }

   /**
    * Set Learning rate. Accepted range is (0.0 - 1.0). Recommended utils.values are between 0.05 and 0.25
    *  Default is <code>0.15</code>
    * @param eta Learning rate
    */
    public void setLearningRate(double eta) {
       if (eta <= 0.0d) eta =0.01d;
       if (eta >= 1.0d) eta = 0.99d;
        this.eta = eta;
    }


    public BPN(int aNumberOfLevels, int[] aNumbersOfNeurons){
        mNumberOfLevels = aNumberOfLevels;
        mNumberOfLayers = mNumberOfLevels - 1;
        mNumberOfNeurons = new int[mNumberOfLevels];
        for(int i = 0; i < mNumberOfLevels; i++)
            mNumberOfNeurons[i] = aNumbersOfNeurons[i];
        mLevels = new ArrayList(mNumberOfLevels);
        mLayers = new ArrayList(mNumberOfLayers);
        mTrainingTests = new ArrayList(100);
        mCountInputNeurons = mNumberOfNeurons[0];
        mCountOutputNeurons = mNumberOfNeurons[mNumberOfLevels - 1];
        mCurrentTest = 0;
        InitLevels();
        InitLayers();
    }

    private void InitLevels(){
        for (int i = 0; i < mNumberOfLevels; i++){
            int current_number_of_neurons = mNumberOfNeurons[i];
            //One more neuron for the bias (added to all layers but the output)
            int additional_neuron =  ( i != mNumberOfLevels - 1 ) ? 1 : 0;
            Neuron[] current_level = new Neuron[current_number_of_neurons + additional_neuron];
            for (int j = 0; j < current_number_of_neurons; j++){
                current_level[j] = new Neuron();
            }
            //Add the additional bias neuron to every level but the output
            if ( i != mNumberOfLevels - 1)
                current_level[current_number_of_neurons] = new Bias();
            mLevels.add( i , current_level);
        }
    }

    private void InitLayers(){
        for (int i = 0; i < mNumberOfLayers; i++){
            Layer current_layer = new Layer(mNumberOfNeurons[i], mNumberOfNeurons[i+1]);
            mLayers.add( i , current_layer);
        }
    }

    private void resetNetwork(){
        for (int i = 0; i < mNumberOfLevels; i++){
            Neuron[] current_level = (Neuron[])mLevels.get(i);
            for (int j = 0; j < current_level.length; j++)
                current_level[j].reset();
        }
    }

    public double[] getOutput( double[] aData) {
        double[] values = new double[mCountOutputNeurons];
        resetNetwork();

        //Input data for input level of neurons
        for ( int i = 0; i < mCountInputNeurons ; i++){
            ( (Neuron[])mLevels.get( 0 ) )[ i ].add( aData[i]);
        }
        for (int i = 1; i < mNumberOfLevels; i++){
            Neuron[] current_level = ( (Neuron[])mLevels.get( i ) );
            Neuron[] previous_level = ( (Neuron[])mLevels.get( i - 1 ) );
            Layer current_layer = (Layer)mLayers.get( i-1);

            for (int j = 0; j < mNumberOfNeurons[i]; j++)
            {
                // The last neuron is actually the bias neuron
                for(int k = 0; k < mNumberOfNeurons[i-1] + 1; k++)
                {
                    current_level[j].add( current_layer.get( k, j) * previous_level[k].getOutput());
                }
            }
        }

        Neuron[] output_level = ( (Neuron[])mLevels.get( mNumberOfLevels - 1 ) );
        for (int i = 0; i < mCountOutputNeurons; i++){
            values[i] = output_level[i].getOutput();
        }

        return values;
    }

    //Ep
    private double getErrorSq( double[] aActualOutput, double[] aDesiredOutput){
        double values = 0.0d;
        for( int i = 0; i < mCountOutputNeurons; i++){
            values += (aDesiredOutput[ i ] - aActualOutput[ i ]) * (aDesiredOutput[ i ] - aActualOutput[ i ]);
        }
        return (1.0/2.0) * values;
    }

    private double[] getErrorLin( double[] aActualOutput, double[] aDesiredOutput){
        double values[] = new double[mCountOutputNeurons];
        for( int i = 0; i < mCountOutputNeurons; i++){
            values[i] += (aDesiredOutput[ i ] - aActualOutput[ i ]);
        }
        return values;
    }

    public double Train(double[] mInputData, double[] mDesiredOutput, double mAcceptableError){
        TrainingTest temp = new TrainingTest(mInputData, mDesiredOutput);
        mTrainingTests.add( temp );
        return Train(mInputData, mDesiredOutput, mAcceptableError, eta);
    }

    public double Train(double[] mInputData, double[] mDesiredOutput, double mAcceptableError, double mLearningRate){
        double[] output = getOutput(mInputData);
        double[] error = getErrorLin(output, mDesiredOutput);
        double sq_error = getErrorSq(output, mDesiredOutput);
        int iter = 0;


        while (sq_error > mAcceptableError &&  iter < maxIterations)
        {
                //Fills Delta utils.values
            calcOutputDelta(error);
            for (int i = mNumberOfLevels - 2; i >= 0;  i--){
                calcDelta( i );
            }
            //Adjust Weights
            for (int i = mNumberOfLevels - 1; i > 0;  i--){
                adjustWeight( i, mLearningRate );
            }
            output = getOutput(mInputData);
            error = getErrorLin(output, mDesiredOutput);
            sq_error = getErrorSq(output, mDesiredOutput);
            iter ++;
        }

        return sq_error;
    }

    private void adjustWeight(int aLevel, double eta){
        Neuron[] current_level = (Neuron[])mLevels.get(aLevel);
        Neuron[] previous_level = (Neuron[])mLevels.get(aLevel - 1);
        Layer current_layer = (Layer)mLayers.get( aLevel - 1);

        double new_weight;
        //Include the bias
        for (int i = 0; i < mNumberOfNeurons[aLevel] ; i++){
            for (int j = 0; j < mNumberOfNeurons[aLevel - 1] + 1; j++){
                new_weight = current_layer.get(j, i ) + eta * current_level[i].getDelta() * previous_level[ j ].getOutput();
                current_layer.set( j, i, new_weight );
            }
            current_level[ i ].reset();
        }
    }

    private void calcOutputDelta(double[] aError){
        Neuron[] current_level = (Neuron[])mLevels.get(mNumberOfLevels - 1);
        for (int i = 0; i < mCountOutputNeurons; i++){
            current_level[ i ].setDeltaSpecial(aError[ i ]);
        }
    }

    private void calcDelta( int aLevel){
        Neuron[] current_level = (Neuron[])mLevels.get(aLevel);
        Neuron[] next_level = (Neuron[])mLevels.get(aLevel + 1);
        Layer current_layer = (Layer)mLayers.get( aLevel );
        double sum = 0.0d;

        // Including the bias
        for ( int i = 0; i < mNumberOfNeurons[aLevel] + 1; i++){
            sum = 0.0d;
            for( int j =0; j < mNumberOfNeurons[aLevel + 1]; j++){
                sum += next_level[j].getDelta() * current_layer.get( i, j);
            }
            current_level[ i ].setDeltaSpecial(sum);
        }
    }

    public void addTrainingTest(double[] mInputData, double[] mDesiredOutput){
         TrainingTest new_test = new TrainingTest(mInputData, mDesiredOutput);
        mTrainingTests.add(mCurrentTest,  new_test );
        mCurrentTest++;
    }

    public void executeTraining(double mAcceptableError){
        double max = 0.0d;
        double global_max= 1.0d;
        int iter = 0;
        ArrayList copy_of_layers = new ArrayList();
    do{
        max = 0.0d;
        for (int i = 0; i < mCurrentTest; i++){
            TrainingTest test = (TrainingTest)mTrainingTests.get( i );
            Train(test.getInputData(), test.getDesiredOutput(), mAcceptableError/2, eta);
        }
        for (int i = 0; i < mCurrentTest; i++){
            TrainingTest test = (TrainingTest)mTrainingTests.get( i );
            double res = getErrorSq(getOutput(test.getInputData()), test.getDesiredOutput());
            if (res > max) max = res;
            }

        if(max < global_max){
            global_max = max;
            copy_of_layers = new ArrayList();
             for(int counter = 0; counter < mLayers.size(); counter++){
                  copy_of_layers.add(counter, mLayers.get(counter));
              }
        }
        iter++;
        } while ( max > mAcceptableError && iter < maxGIterations);

        mLayers = new ArrayList();
        for(int counter = 0; counter < copy_of_layers.size(); counter++){
            mLayers.add(counter, copy_of_layers.get(counter));
        }
    }
}

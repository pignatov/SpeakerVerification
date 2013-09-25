import neural.*;

public class TestNN {
    public static void main(String[] args){
        int[] dim = {2,10,1};
        double[] params = {0.5, 0.4};
        double[] output = { 0.6 };
        neural.BPN bpn = new neural.BPN(3, dim);
        double[] result = bpn.getOutput(params);
        System.out.println(result[0]);
        result = bpn.getOutput(params);
        System.out.println(result[0]);

        bpn.setLearningRate(0.15);

        for (double  i = 0.0; i < 1.0; i+= 0.1){
            for (double j =0.0; j< 1.0; j+= 0.1){
                params[0] = i;
                params[1] = j;
                output[ 0 ] = i*j;
                bpn.addTrainingTest(params, output);
            }
        }
        bpn.executeTraining(0.001);

        try{
            BPNLoader.write(bpn, "plamen.bpn");
        } catch(Exception ex){
            System.out.println("File IO Error");
        }

         try{
            bpn = BPNLoader.read( "plamen.bpn");
        } catch(Exception ex){
            System.out.println("File IO Error");
        }


     for (double  i = 0.0; i < 1.0; i+= 0.1){
            for (double j =0.0; j< 1.0; j+= 0.1){
            params[0] = i;
                params[1]=j;
            result = bpn.getOutput(params);
            System.out.println(result[0]+"--------------"+i*j);
            }
     }

    }
}

import tespar.*;

public class TestTespar {
    public static void main(String[] args){
        Coder coder = new Coder();

        int[] test = {-2, 10000,30000,300,10,-10,-30,-20,-5,5,10,9,7,3,2,3,4,5,7,4,2,5,6,9,10,9,3,2,-3,-5};
        byte[] out = coder.Encode(test, 0, test.length);

        for (int i = 0; i < out.length; i++){
            System.out.println(out[i]);
        }
    }
}

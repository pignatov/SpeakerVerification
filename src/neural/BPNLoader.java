package neural;

import java.io.*;

public class BPNLoader {
    public static BPN read(String aFileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream obj_input_stream = new ObjectInputStream(new FileInputStream(aFileName));
        BPN  temp = (BPN) obj_input_stream.readObject();
        obj_input_stream.close();
        return temp;
    }

    public static void write(BPN aBPN, String aFileName) throws IOException {
        ObjectOutputStream obj_output_stream = new ObjectOutputStream(new FileOutputStream( aFileName ));
            obj_output_stream.writeObject( aBPN );
            obj_output_stream.flush();
            obj_output_stream.close();
    }

}
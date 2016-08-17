import java.util.Vector;
import java.util.Random;
import org.apache.xmlrpc.*;
import java.io.*;

public class FactorizationXMLClient {

  // The location of our server.
  private final static String server_url = "http://localhost:12345/RPC2";

  public static void main (String [] args) {

    long start_val, end_val;
    //data types being used to calculate how long it takes the server to perform its task.
    Random r = new Random();
    //random generator

    try {
      //Identify the server
      XmlRpcClient server = new XmlRpcClient(server_url);

      //Build Parameter Vector
      Vector params = new Vector();
      params.addElement((Object) new Integer(r.nextInt(100)));
      //adding a random number to later find the factors of.
      //That calculation is being conducted on the server side.
      System.out.println("Transmitting: " + params);

      start_val = System.currentTimeMillis();
      //start the timing
      Vector v = ((Vector) server.execute("fact.Factors", params));
      //the server executing its task using a vector
      end_val = System.currentTimeMillis();
      //end the timing
      try {
        BufferedWriter out = new BufferedWriter(new FileWriter("ctimes", true));
        //output how long it took for the server to complete its task.
        //This is being outputting to a text document called ctimes.txt
        out.write((((double)end_val-start_val)/1000)+", ");
        //writing the output in seconds versus milliseconds
        out.close();
        //close the output stream like a good boy
      }
      catch(IOException e) {
      }

      System.out.println("Received: " + v);
      //this is the number being received from the server
    }

    catch (XmlRpcException exception) {
      //catch the exception if the connection does not go right
      System.err.println("JavaClient: XML-RPC Fault #" +
          Integer.toString(exception.code) + ": " +
          exception.toString());
    }
    catch (Exception exception) {
      System.err.println("JavaClient: " + exception.toString());
    }
  }
}

import java.util.Vector;
import java.util.Random;
import org.apache.xmlrpc.*;
import java.io.*;

public class LLXMLClient {

  // The location of our server.
  private final static String server_url = "http://localhost:12345/RPC2";
static String holder;
  public static void main (String [] args) {
    long start_val, end_val;
    Random r = new Random();
    holder = args[0];

    try {
      //Identify the server
      XmlRpcClient server = new XmlRpcClient(server_url);

      //Build Parameter Vector
      Vector params = new Vector();
      
     params.addElement((Object) new String( holder + holder + holder + holder + holder + holder));
    
      System.out.println("Transmitting: " + params);

      start_val = System.currentTimeMillis();
      Object v =  server.execute("fact.Expand", params);
      end_val = System.currentTimeMillis();
      try {
        BufferedWriter out = new BufferedWriter(new FileWriter("LLClienttimes", true));
        out.write((((double)end_val-start_val)/1000)+", ");
        out.close();
      }
      catch(IOException e) {

      }

      System.out.println("Received: " + v);
    }

    catch (XmlRpcException exception) {
      System.err.println("JavaClient: XML-RPC Fault #" +
          Integer.toString(exception.code) + ": " +
          exception.toString());
    }
    catch (Exception exception) {
      System.err.println("JavaClient: " + exception.toString());
    }
  }
}

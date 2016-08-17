import java.util.Vector;
import java.util.Random;
import org.apache.xmlrpc.*;
import java.io.*;

public class SLXMLClient {

  // The location of our server.
  private final static String server_url = "http://localhost:12345/RPC2";

  public static void main (String [] args) {
    long start_val, end_val;
    Random r = new Random();

    try {
      //Identify the server
      XmlRpcClient server = new XmlRpcClient(server_url);

      //Build Parameter Vector
      Vector params = new Vector();
      params.addElement((Object) new Integer(r.nextInt(100)));// picks a random int from 0-99

      System.out.println("Transmitting: " + params);

      start_val = System.currentTimeMillis();
      Vector v = ((Vector) server.execute("fact.Before", params));
      end_val = System.currentTimeMillis();
      try {
        BufferedWriter out = new BufferedWriter(new FileWriter("SLClienttimes", true));
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

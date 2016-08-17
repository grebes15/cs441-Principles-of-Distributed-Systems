import org.apache.xmlrpc.*;
import java.util.*;
import java.io.*;

public class FactorizationXMLServer {

  public FactorizationXMLServer() {
  }

  public Vector Factors(int x) {

    System.out.println("Received: " + x);
    //the number being received from the client

    //Create a vector with initial capacity of 32,000
    Vector factors = new Vector(0);

    //Find the factors of x
    for (int i = 1; i <= x; i++){
      if ((x % i) == 0){
        factors.addElement((Object) new Integer(i));
      }
    }
    //returning the factors of the random number from the client
    System.out.println("Returning: " + factors);

    return factors;
  }

  public static void main(String [] args) {
    //data types being used to calculate how long it takes the server to complete its task.
    long start, stop;
    //start the timing
    start = System.currentTimeMillis();
    try {
      // Invoke me as http://localhost:12345/RPC2
      WebServer server = new WebServer(12345);
      //start the server
      server.addHandler("fact", new FactorizationXMLServer());
      server.start();
    }
    catch (Exception exception) {
      //catch any exceptions
      System.err.println("JavaServer: " + exception.toString());
    }
    //stop the timing
    stop = System.currentTimeMillis();
    try {
      //output how long it took the server to do its thing to a text document called stimes.txt
      BufferedWriter out = new BufferedWriter(new FileWriter("stimes", true));
      //write the output in seconds instead of milliseconds.
      out.write((((double)stop-start)/1000)+"\n");
      //close the output stream like a good boy.
      out.close();
    }
    catch(IOException e) {
    }
  }
}

import org.apache.xmlrpc.*;
import java.util.*;
import java.io.*;

public class ShuffleServer {

  public ShuffleServer() {
  }

  public Vector Shuffle(String x) {

    System.out.println("Received: " + x);

    //Create a vector with initial capacity of 32,000
    Vector shuffle = new Vector(0);
   for(int i =0; i <x.length(); i++){
      shuffle.addElement(new String("" + x.charAt(i)));// picks the character and adds to the vector
   }
    
    Collections.shuffle(shuffle); // shuffles the elements in the vector
    System.out.println("Returning: " + shuffle);

    return shuffle;
  }

  public static void main(String [] args) {
    long start, stop;
    start = System.currentTimeMillis();
    try {
      // Invoke me as http://localhost:12345/RPC2
      WebServer server = new WebServer(12345);
      server.addHandler("fact", new ShuffleServer());
      server.start();
    }
    catch (Exception exception) {
      System.err.println("JavaServer: " + exception.toString());
    }
    stop = System.currentTimeMillis();
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter("ShuffleServertimes", true));
      out.write((((double)stop-start)/1000)+"\n");
      out.close();
    }
    catch(IOException e) {
    }
  }
}

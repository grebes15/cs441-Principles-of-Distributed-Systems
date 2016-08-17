import org.apache.xmlrpc.*;
import java.util.*;
import java.io.*;

public class LLXMLServer {

  public LLXMLServer() {
  }

  public Vector Expand(String x) {

    System.out.println("Received: " + x);

    //Create a vector with initial capacity of 32,000
    Vector expand = new Vector(0);
   for(int i =0; i <10; i++){ // string concatinate 10 times
    x = x +x;
   }
     expand.addElement(new String(""+ x));

    System.out.println("Returning: " + expand);

    return expand;
  }

  public static void main(String [] args) {
    long start, stop;
    start = System.currentTimeMillis();
    try {
      // Invoke me as http://localhost:12345/RPC2
      WebServer server = new WebServer(12345);
      server.addHandler("fact", new LLXMLServer());
      server.start();
    }
    catch (Exception exception) {
      System.err.println("JavaServer: " + exception.toString());
    }
    stop = System.currentTimeMillis();
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter("LLServertimes", true));
      out.write((((double)stop-start)/1000)+"\n");
      out.close();
    }
    catch(IOException e) {
    }
  }
}

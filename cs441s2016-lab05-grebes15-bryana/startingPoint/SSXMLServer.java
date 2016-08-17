import org.apache.xmlrpc.*;
import java.util.*;
import java.io.*;

public class SSXMLServer {

  public SSXMLServer() {
  }

  public Vector Product(int x) {

    System.out.println("Received: " + x);

    //Create a vector with initial capacity of 32,000
    Vector product = new Vector(0);

    product.addElement((Object) new Integer(x*x)); // multiplies parameter by iteself and adds it to the vector
      
    

    System.out.println("Returning: " + product);

    return product;
  }

  public static void main(String [] args) {
    long start, stop;
    start = System.currentTimeMillis();
    try {
      // Invoke me as http://localhost:12345/RPC2
      WebServer server = new WebServer(12345);
      server.addHandler("fact", new SSXMLServer());
      server.start();
    }
    catch (Exception exception) {
      System.err.println("JavaServer: " + exception.toString());
    }
    stop = System.currentTimeMillis();
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter("SSServertimes", true));
      out.write((((double)stop-start)/1000)+"\n");
      out.close();
    }
    catch(IOException e) {
    }
  }
}

import org.apache.xmlrpc.*;
import java.util.*;
import java.io.*;

public class LSXMLServer {

  public LSXMLServer() {
  }

  public Vector FirstL(String x) {
    System.out.println("Received: " + x);


    //Create a vector with initial capacity of 32,000
    Vector first = new Vector(0);

    first.addElement(new String(""+ x.charAt(0)));// gets first character in the string and adds it to the vector

    System.out.println("Returning: " + first);

    return first;
  }

  public static void main(String [] args) {
    long start, stop;
    start = System.currentTimeMillis();
    try {
      // Invoke me as http://localhost:12345/RPC2
      WebServer server = new WebServer(12345);
      server.addHandler("fact", new LSXMLServer());
      server.start();
    }
    catch (Exception exception) {
      System.err.println("JavaServer: " + exception.toString());
    }
    stop = System.currentTimeMillis();
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter("LSServertimes", true));
      out.write((((double)stop-start)/1000)+"\n");
      out.close();
    }
    catch(IOException e) {
    }
  }
}

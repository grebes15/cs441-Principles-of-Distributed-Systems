import org.apache.xmlrpc.*;
import java.util.*;
import java.io.*;

public class SLXMLServer {

  public SLXMLServer() {
  }

  public Vector Before(int x) {

    System.out.println("Received: " + x);

    //Create a vector with initial capacity of 32,000
    Vector before = new Vector(0);
    for(int i = 0; i <x; i++){
    before.addElement((Object) new Integer(x-i));// adds every number before x to the vector
    }

    System.out.println("Returning: " + before);

    return before;
  }

  public static void main(String [] args) {
    long start, stop;
    start = System.currentTimeMillis();
    try {
      // Invoke me as http://localhost:12345/RPC2
      WebServer server = new WebServer(12345);
      server.addHandler("fact", new SLXMLServer());
      server.start();
    }
    catch (Exception exception) {
      System.err.println("JavaServer: " + exception.toString());
    }
    stop = System.currentTimeMillis();
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter("SLServertimes", true));
      out.write((((double)stop-start)/1000)+"\n");
      out.close();
    }
    catch(IOException e) {
    }
  }
}

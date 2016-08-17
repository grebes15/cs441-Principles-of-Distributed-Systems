//import statements
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MulticastSocketSender {

  // This example is from: http://examples.javacodegeeks.com/core-java/net/multicastsocket-net/java-net-multicastsocket-example/

  final static String INET_ADDR = "224.0.0.3";
  //This is the IP address tht you are going to connect to hopefully
  final static int PORT = 12345;
  //This is the port nuymber being used to perform this communication

  public static void main(String[] args) throws UnknownHostException, InterruptedException {
    // Get the address that we are going to connect to.
    InetAddress addr = InetAddress.getByName(INET_ADDR);

    // Open a new DatagramSocket, which will be used to send the data.
    try (DatagramSocket serverSocket = new DatagramSocket()) {
      for (int i = 0; i < 5; i++) {
        //try to connect to the receiver and the message is just the number 0 to 4.
        String msg = "Sent message number: " + i;
        //this is the string or message being sent

        // Create a packet that will contain the data
        // (in the form of bytes) and send it.
        DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
            msg.getBytes().length, addr, PORT);
        //send the packet that is the object DatagramSocket 
        serverSocket.send(msgPacket);

        System.out.println("The server sent a packet with this message: " + msg);
        //print the message


        Thread.sleep(500);
        //this line suspends the thread from doing its thing for a period of time.
        //in this case, it is 500 milliseconds
      }
    } catch (IOException ex) { //if the connection is not successful, then catch it.
      ex.printStackTrace();
    }
  }
}

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

// This class is based off of the example at:
// https://gist.github.com/CarlEkerot/2693246

public class FileSocketServer extends Thread {

    private ServerSocket ss;

    public FileSocketServer(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
//The accept method waits until a client starts up and requests a connection on the host and port of this server.
//Since it is a thread we are working with, it will run in parallel with other processes or threads in this multi-threaded system.

                saveFile(clientSock);
                //this method call will save the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());

        long fSize; //long variable for the size of the file
        String fName; //string variable for the name of the file
		
	   long startTime = System.currentTimeMillis();	   
	   
        fSize = dis.readLong(); //having the server read the size of the file
        fName = dis.readUTF(); //having the server read the name of the file

        FileOutputStream fos = new FileOutputStream("files/received/" + fName); 
        //this is the locations where the file that is going through the client server interfaction will end up
        //the paper will be remained paper.pdf.
        byte[] buffer = new byte[4096];
        //this is the amount of bytes being used to read the file as pieces instead of reading the entire file at once
        int filesize =  (int) fSize; //401210; // Send the file size in a separate message, hard-coded for right now
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read); //
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        DecimalFormat df = new DecimalFormat ("#0.0000");
        System.out.println(df.format(elapsedTime));
        //close the data input stream and file output stream like a good boy
        fos.close(); //close the data input stream
        dis.close(); //close the file output stream
    }

    public static void main(String[] args) {
        FileSocketServer fs = new FileSocketServer(12345);
        fs.start(); //start the file socket server using the port 12345.
    }

}

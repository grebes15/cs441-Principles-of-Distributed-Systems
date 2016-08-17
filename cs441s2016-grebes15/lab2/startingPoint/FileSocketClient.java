import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// This class is based off of the example at:
// https://gist.github.com/CarlEkerot/2693246

public class FileSocketClient {

    private Socket s;

    public FileSocketClient(String host, int port, String file) {
        //this is the constructor.
        //the host is a device with an IP address.
        //an IP address is a unique number to identify a number.
        //an port is a way to organize programs since different programs listen to different ports.
        //In this case, these programs interact at port 12345. 

        try {
            //try to connect to the server
            s = new Socket(host, port);
            sendFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String file) throws IOException {
        File sendFile = new File(file);
        Long sendFileSize;
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //this is the data the client is sending as output
        FileInputStream fis = new FileInputStream(file); //This is the file that the server is taking as input
        String sendFileName = sendFile.getName();
        sendFileSize = sendFile.length();

        byte[] buffer = new byte[4096];
        //the amount of bytes being read each time so the the file is being read in pieces instead of the whole thing

        dos.writeLong(sendFileSize);
        dos.writeUTF(sendFileName);

        while (fis.read(buffer) > 0) { //if the data output stream has been read server from the server, then write to it.
            dos.write(buffer); //write to the file input stream
        }
        //close the data output stream and file input stream like a good boy
        fis.close();
        dos.close();
    }

    public static void main(String[] args) {
        int i=0;
        String hostName = "localhost"; //default name for hostName
        int portNumber = 12345; //default port number
        ArrayList<String> fileNames = new ArrayList<String>();
        //arraylist used to store the filesNames that is being sent to the server
        while (i<args.length) {
            if ((args[i]=="-host") || (args[i]=="-h")) { 
                hostName = args[++i]; //if -host or -h is specified, then the next argument is the host name
            } 
            else if ((args[i]=="-port") || (args[i]=="-p")) {
                portNumber = Integer.parseInt(args[++i]);
                //if -port or -p is specified, then the next argument is the port number
            } else {
                fileNames.add(args[i]); //else it is assumed to be a file name
            }
            i++;

        }

        for (i=0; i<fileNames.size();i++) {
           FileSocketClient fc = new FileSocketClient(hostName, portNumber, fileNames.get(i)); 
           //for loop to transfer all of the files and arguments          
        }

        //FileSocketClient fc = new FileSocketClient("localhost", 12345, "files/send/seke2015-kinneer-kapfhammer-wright-mcminn.pdf");
          //FileSocketClient fc = new FileSocketClient(args[0], Integer.parseInt(args[1]), args[2]);
          //args[0] is the host so "localhost"
          //args[1] is the port so 12345
          //args[2] is the fileName

    }

}

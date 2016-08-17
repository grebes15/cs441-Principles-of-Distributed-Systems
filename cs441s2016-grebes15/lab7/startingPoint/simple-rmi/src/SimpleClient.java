import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class SimpleClient
{
  final public static int BUF_SIZE = 1024 * 64;

  /**
   * Copy the input stream to the output stream
   *
   * @param in
   * @param out
   * @throws IOException
   */
  public static void copy(InputStream in, OutputStream out)
    throws IOException {

    byte[] b = new byte[BUF_SIZE];
    int len;
    while ((len = in.read(b)) >= 0) {
      out.write(b, 0, len);
    }
    in.close();
    out.close();
  }

  public static void upload(Simple server, File src, File dest) throws IOException {
    copy (new FileInputStream(src), server.getOutputStream(dest));
    //upload a file between the client and the server. 
  }

  public static void download(Simple server, File src, File dest) throws IOException {
    copy (server.getInputStream(src), new FileOutputStream(dest));
    //download a file between the client and the server.
  }

  public static void main(String arg[])
  {
    //main method
    ResourceBundle properties = PropertyResourceBundle.getBundle("Simple");
    int port = Registry.REGISTRY_PORT;
    //int data port used for to store the port
    try {
      port = Integer.parseInt(properties.getString("server.port"));
      //parse the int to a string for the server port
    } catch (Exception e) {
      port = Registry.REGISTRY_PORT;
    }
    String command = null;
    if (arg.length > 0) {
      //if the length of the argument is greater than 0, then start the String at the first index of the array. 
      command = arg[0];
    }

    if (command != null && command.length() > 0) {

      boolean useSecurityManager = false;
      //if statement for security manager if addes security was used between the client and the server. 
      try {
        Boolean.valueOf(properties.getString("useSecurityManager"));
      } catch (Exception e) {
        // default to false
      }
      if (useSecurityManager && System.getSecurityManager() == null) {
        System.setSecurityManager(new SecurityManager());
      }

      try
      //catch the connection between the client and the server. The server.ip would be the localhost.
      {
        String serverIP = System.getProperty("server.ip");
        if (null == serverIP) {
          try {
            serverIP = properties.getString("server.ip");
          } catch (MissingResourceException e) {
            //catch the exception if it is not working properly. 
            throw new Exception("Undefined server IP.  Please define 'server.ip' as system property (ex. java -Dserver.ip=xxx) or in the Simple.properties file.");
          }
        }

        Simple server = (Simple) Naming.lookup( "//" +
            serverIP +
            ":" + port +
            "/SimpleServer");

        if ( command.equalsIgnoreCase("ping") ) {
          //if ping is being called as an argument, then run the ping method.
          //the ping method will send the "Hello, world!" message to the server from the client
          System.out.println(server.ping());

        } else if (command.equalsIgnoreCase("upload") ) {
          //if upload is being called as an argument, then run the upload method
          //the upload method will specify a local file to be uploaded
          //if the length of the argument is more than one, then the second argument is the remote file to be written to. 
          if (arg.length > 1) {
            String srcFilename = arg[1];
            if (srcFilename != null && srcFilename.length() > 0) {
              String destFilename = srcFilename;
              if (arg.length > 2) {
                destFilename = arg[2];
              }
              upload(server, new File(srcFilename), new File(destFilename));
            }
          }

        } else if (command.equalsIgnoreCase("download") ) {
          //if download is being called as an argument, then run the download method
          //the download method will specify a remote file to be downloaded
          //if the length of the argument is more than one, then the second argument is the local file to be written to.
          if (arg.length > 1) {
            String srcFilename = arg[1];
            if (srcFilename != null && srcFilename.length() > 0) {
              String destFilename = srcFilename;
              if (arg.length > 2) {
                destFilename = arg[2];
              }
              download(server, new File(srcFilename), new File(destFilename));
            }
          }

        } else {
          System.out.println(server.runCommand(command, null));
        }
      }
      catch (Exception e)
      {
        //catch the exception
        System.out.println("SimpleClient exception: " + e.getMessage());
        e.printStackTrace();
      }
    } else {
      //print the commands if they are not being used correctly.
      System.out.println("Usage: SimpleClient command");
      System.out.println("\nExample: java [-Djava.security.policy=rmi.policy] -jar simple-client.jar ping");
      System.out.println("\n         java [-Djava.security.policy=rmi.policy] -jar simple-client.jar upload srcfile.txt [destfile.txt]");
      System.out.println("\n         java [-Djava.security.policy=rmi.policy] -jar simple-client.jar download afile.txt [destfile.txt]");
      System.out.println("\n         java [-Djava.security.policy=rmi.policy] -jar simple-client.jar \"db2 reorg indexes all for table ADWSRNCT.F_INCIDENT\"");
    }
  }
}

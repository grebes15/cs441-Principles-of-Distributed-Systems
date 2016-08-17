//import statements
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author pothoven
 *
 */
public class SimpleServer
{

  public static void main(String args[])
  {
    ResourceBundle properties = PropertyResourceBundle.getBundle("Simple");
    //ResourceBundle object to get an object from the client
    int port = Registry.REGISTRY_PORT;
    //integer used for the port
    try {
      port = Integer.parseInt(properties.getString("server.port"));
      //try the connection on this server port
    } catch (Exception e) {
      // default to Registry.REGISTRY_PORT
    }

    try
    {
      boolean useSecurityManager = false;
      //set the security protocol to be false
      try {
        Boolean.valueOf(properties.getString("useSecurityManager"));
        //try to use the security manager if the above boolean was changed to true.
      } catch (Exception e) {
        // default to false
      }

      if (useSecurityManager && System.getSecurityManager() == null) {
        //this is all used for the security manager if you specify it in the command line.
        System.setSecurityManager(new SecurityManager());
        //setting the securtity up if it is being used.
      }

      Registry registry = LocateRegistry.createRegistry(port);
      //registry object with the port specified.

      SimpleImpl obj = new SimpleImpl();
      /* Bind this object instance to the name "SimpleServer" */
      // Naming.rebind("SimpleServer", obj);
      registry.rebind("SimpleServer", obj);

      System.out.println("SimpleServer started on port " + port);
        //started up the server and specifiy the port that it started on
    }
    catch (Exception e)
    //if the server is not able to start, catch the exception
    {
      System.out.println("SimpleServer err: " + e.getMessage());
      e.printStackTrace();
    }
  }
}

import io.RMIInputStream;
import io.RMIInputStreamImpl;
import io.RMIOutputStream;
import io.RMIOutputStreamImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * @author pothoven
 *
 */
public class SimpleImpl extends UnicastRemoteObject implements Simple {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public static final int DEFAULT_MAX_THREAD_COUNT = 5;

  private static Vector<Thread> pendingCommandThreads = new Vector<Thread>();
  //pending the running the command threads using a vector.
  private static Vector<Thread> runningCommandThreads = new Vector<Thread>();

  public SimpleImpl() throws RemoteException {}

  /* (non-Javadoc)
   * @see Simple#ping()
   */
  @Override
  public String ping() { return "Hello world!"; }
  //if you speicify ping in the command line, the client will send the server the message "Hello world!"


  /* (non-Javadoc)
   * @see Simple#runCommand(java.lang.String, java.lang.String[])
   */
  @Override
  public String runCommand(String command, String[] envp)
  throws RemoteException {
    //running the command

  CommandThread t = new CommandThread(command, envp);
  //command thread object
  try {

    if (getActiveThreadCount() < getMaxThreadCount()) {
      //if the active thread count is less than max thread count, then the whole vector<thread> has not been processed
      runningCommandThreads.add(t); //adding the common thread to the end of the vector 
      t.start(); //starting the common thread
      //
    } else {
      pendingCommandThreads.add(t); //adding the common thread to the end of the vector
      System.out.println("Queued (thread: " + t.getName() + "): " + command); //print debugging information to ensure the command is running correctly.
    }
    t.join(); //waits for this common thread to die

  } catch (Exception e) { //catch the exception if it does not work correctly.
    throw new RemoteException(e.getMessage());
  }

  return t.getResults(); //return the results.
  }

  /* (non-Javadoc)
   * @see Simple#getOutputStream(java.io.File)
   */
  public OutputStream getOutputStream(File f) throws IOException {
    //get the output stream
    System.out.println("Upload file: " + f.getName());
    //print the name of the upoload file between the client and the server
    return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
    //return the output stream
  }

  /* (non-Javadoc)
   * @see Simple#getInputStream(java.io.File)
   */
  public InputStream getInputStream(File f) throws IOException {
    //get the input stream
    System.out.println("Download file: " + f.getName());
    //print the name of the downloaded file
    return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
    //return the input stream
  }

  /**
   * Get the number of pending command threads.
   *
   * @return number of pending command threads
   */
  public static int getPendingThreadCount() {
    //get the pending thread count method
    return pendingCommandThreads.size();
    //return the size of the thread
  }

  /**
   * Get the number of active command threads.
   *
   * @return number of active command threads
   */
  public static int getActiveThreadCount() {
    //get the active thread count
    return runningCommandThreads.size();
    //return the size of the thread count
  }

  protected int  getMaxThreadCount() { return DEFAULT_MAX_THREAD_COUNT; }
  //return the maximum thread count

  class CommandThread extends Thread {
    private String command = null; //string data type to store the command being used in the command line
    private String[] envp = null; //string array data type.
    private StringBuffer results = new StringBuffer(); // data type being used for the results

    public CommandThread(String command, String[] envp) {
      //constructor
      super(); //calls the parent constructor with no arguments
      this.command = command; //
      this.envp = envp;
    }

    public void run() {
      long startTime = System.currentTimeMillis();
      //start the timing between the client and the server

      // give a random time up to 2 sec delay before starting new threads
      // (if this isn't the first thread) to help prevent traffic jams
      if (SimpleImpl.getActiveThreadCount() > 1)
      {
        try {
          sleep((long) (Math.random() * 2000));
          //causes the currently executing thread to sleep for the specified number of milliseconds. 
        } catch (InterruptedException e1) { }
      }

      try {
        System.out.println("Running (thread: " + this.getName() + ") : " + command);
        //print the name of the thread with the command

        Process cmdProcess = Runtime.getRuntime().exec(command, envp);
        //command process 

        BufferedReader stdInput = new BufferedReader(new
            InputStreamReader(cmdProcess.getInputStream()));
        //get the input stream

        BufferedReader stdError = new BufferedReader(new
            InputStreamReader(cmdProcess.getErrorStream()));
        //get the error stream  
        String s = null;

        /* read the output from the command */
        while ((s = stdInput.readLine()) != null) {
          results.append(s);
        }

        /* read any errors from the attempted command  */
        while ((s = stdError.readLine()) != null) {
          results.append(s);
        }

      } catch (IOException e) {
        results.append(e.getMessage());
      } finally {
        long endTime = System.currentTimeMillis();
        //end the timing between the client and the server
        runningCommandThreads.remove(this);
        //print the thread when it is completed
        System.out.println("Completed (thread: " + this.getName() + ") in "+(endTime - startTime) + " ms");

        // start up the next pending thread
        if (getPendingThreadCount() > 0 &&
            getActiveThreadCount() < getMaxThreadCount()) {
          //if the thread count is still not the end of the file, then keep going.
          Thread t = pendingCommandThreads.remove(0);
        //add to the thread
          runningCommandThreads.add(t);
          t.start();
          //starting the common thread
            }
      }
    }

    /**
     * @return the results
     */
    public String getResults() {
      return results.toString();
      //return the results in a toString() method
    }

  }
}

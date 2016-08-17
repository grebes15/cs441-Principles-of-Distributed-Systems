package io;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * from https://www.censhare.com/en/insight/overview/article/file-streaming-using-java-rmi
 */


/**
 * @author pothoven
 *
 */
public class RMIInputStreamImpl implements RMIInputStreamInterf {
	 private InputStream in;
	 //input stream object
	    private byte[] b;
	    //byte object

	    public RMIInputStreamImpl(InputStream in) throws IOException {
	        this.in = in;
	        UnicastRemoteObject.exportObject(this, 1099);
	        //export the object of the input stream specfying the port
	    }

	    public void close() throws IOException, RemoteException {
	        in.close();
	        //close the input stream
	    }

	    public int read() throws IOException, RemoteException {
	        return in.read();
	        //reading the input stream
	    }

	    public byte[] readBytes(int len) throws IOException, 
	            RemoteException {
	            	//method used to read the bytes
	        if (b == null || b.length != len)
	        	//if there is nothing in the bytes data types or it doesn't equal to int len parameter, then new bytes will be used
	            b = new byte[len];
	            //read the bytes in a new int primitve data type.
	        int len2 = in.read(b);
	        if (len2 < 0) //if len2 does not equal 0, then you have reached the end of the final.
	            return null; // EOF reached
	        
	        if (len2 != len) { //if the two int of len2 and len does not equal each other, then copy bytes to byte[] to correct length and return it.
	            // copy bytes to byte[] of correct length and return it
	            byte[] b2 = new byte[len2];
	            System.arraycopy(b, 0, b2, 0, len2);
	            return b2;
	        }
	        else
	            return b;
	    }
}

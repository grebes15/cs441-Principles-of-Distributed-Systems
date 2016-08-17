package io;

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.server.UnicastRemoteObject;

/**
 * from https://www.censhare.com/en/insight/overview/article/file-streaming-using-java-rmi
 */


/**
 * @author pothoven
 *
 */
public class RMIOutputStreamImpl implements RMIOutputStreamInterf {
    private OutputStream out;
    
    public RMIOutputStreamImpl(OutputStream out) throws IOException {
        this.out = out;
        //export the object output stream specifying the port 1099
        UnicastRemoteObject.exportObject(this, 1099);
    }
    
    public void write(int b) throws IOException {
        out.write(b);
        //write method for write the output stream
    }

    public void write(byte[] b, int off, int len) throws 
            IOException {
        out.write(b, off, len);
        //write method for writing the output stream with different parameters
    }

    public void close() throws IOException {
        out.close();
        //close the output stream
    }
}

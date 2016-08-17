package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * from https://www.censhare.com/en/insight/overview/article/file-streaming-using-java-rmi
 */

/**
 * @author pothoven
 *
 */
public class RMIOutputStream extends OutputStream implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RMIOutputStreamInterf out;
    //output steam object
    
    public RMIOutputStream(RMIOutputStreamImpl out) {
        this.out = out;
        //output stream method
    }
    
    public void write(int b) throws IOException {
        out.write(b);
        //write method for writing the output stream
    }

    public void write(byte[] b, int off, int len) throws 
            IOException {
        out.write(b, off, len);
        //write method for writing the output stream with different parameters specified
    }
    
    public void close() throws IOException {
        out.close();
        //close the output stream
    }   
}

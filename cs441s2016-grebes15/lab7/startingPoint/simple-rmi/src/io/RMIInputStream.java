package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * from https://www.censhare.com/en/insight/overview/article/file-streaming-using-java-rmi
 */

/**
 * @author pothoven
 *
 */
public class RMIInputStream extends InputStream implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	RMIInputStreamInterf in;
    
    public RMIInputStream(RMIInputStreamInterf in) {
        this.in = in;
        //inputn steam method
    }
    
    public int read() throws IOException {
        return in.read();
        //reading the input stream
    }

    public int read(byte[] b, int off, int len) throws IOException {
        //reading the input stream with different parameters
        byte[] b2 = in.readBytes(len);
        //reading the bytes
        if (b2 == null)
            //if b2 == 
            return -1;
        int i = b2.length; //set int i to the length of the bytes arrat being used. 
        System.arraycopy(b2, 0, b, off, i);
        //copy the bytes[] array
        return i;
    }
    
    public void close() throws IOException {
        super.close();
        //close the input stream
    }
}

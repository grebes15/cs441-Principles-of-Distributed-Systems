package io;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * from https://www.censhare.com/en/insight/overview/article/file-streaming-using-java-rmi
 */

/**
 * @author pothoven
 *
 */
public interface RMIOutputStreamInterf extends Remote {
    public void write(int b) throws IOException, RemoteException;
    //write method for an output stream
    public void write(byte[] b, int off, int len) throws IOException, RemoteException;
    //write method for an output stream
    public void close() throws IOException, RemoteException;
    //close the output stream
}

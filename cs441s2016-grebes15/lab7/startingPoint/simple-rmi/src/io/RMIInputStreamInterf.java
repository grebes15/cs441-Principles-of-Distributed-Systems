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
public interface RMIInputStreamInterf extends Remote {
    //input stream
    public byte[] readBytes(int len) throws IOException, RemoteException;
    //reading the bytes from the input stream
    public int read() throws IOException, RemoteException;
    //reading the input stream
    public void close() throws IOException, RemoteException;
    //close the input stream
}
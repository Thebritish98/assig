package gen1;
/* The Client Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 * 
 * Modified by Lucas Fontaine for Assigment 2
 */
import java.net.*;
import java.io.*;

public class Client {
	
	private static int portNumber = 5051;
    private Socket socket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;

	// the constructor expects the IP address of the server - the port is fixed
    public Client(String serverIP) {
    	if (!connectToServer(serverIP)) {
    		System.out.println("XX. Failed to open socket connection to: " + serverIP);            
    	}
    }

    private boolean connectToServer(String serverIP) {
    	try { // open a new socket to the server 
    		this.socket = new Socket(serverIP,portNumber);
    		this.os = new ObjectOutputStream(this.socket.getOutputStream());
    		this.is = new ObjectInputStream(this.socket.getInputStream());
    		System.out.println("00. -> Connected to Server:" + this.socket.getInetAddress() 
    				+ " on port: " + this.socket.getPort());
    		System.out.println("    -> from local address: " + this.socket.getLocalAddress() 
    				+ " and port: " + this.socket.getLocalPort());
    	} 
        catch (Exception e) {
        	System.out.println("XX. Failed to Connect to the Server at port: " + portNumber);
        	System.out.println("    Exception: " + e.toString());	
        	return false;
        }
		return true;
    }

    public DataPackage getData() {
    	//Modify to receive and convert into DataPackage
    	String theDataCommand = "GetData";
    	String theDataReceived="";
    	DataPackage theData=null;
    	System.out.println("01. -> Sending Command (" + theDataCommand + ") to the server...");
    	this.send(theDataCommand);
    	try{
    		theDataReceived = (String)receive();
    		System.out.println("05. <- The Server responded with: ");
    		System.out.println("    <- " + theDataReceived);
    	}
    	catch (Exception e){
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}
    	
    	System.out.println("06. -- Disconnected from Server.");
    	System.out.println("07. -- Translating Response.");
    	theData=new DataPackage(theDataReceived);
    	System.out.println("08. -- Response Translated!.");
    	return theData;
    }
	
    // method to send a generic object.
    private void send(Object o) {
		try {
		    System.out.println("02. -> Sending an object...");
		    os.writeObject(o);
		    os.flush();
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Sending:" +  e.toString());
		}
    }

    // method to receive a generic object.
    private Object receive() 
    {
		Object o = null;
		try {
			System.out.println("03. -- About to receive an object...");
		    o = is.readObject();
		    System.out.println("04. <- Object received...");
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
		}
		return o;
    }

   /* public static void main(String args[]) 
    {
    	System.out.println("**. Java Client Application - EE402 OOP Module, DCU");
    	if(args.length==1){
    		Client theApp = new Client(args[0]);
		    theApp.getData();
		}
    	else
    	{
    		System.out.println("Error: you must provide the address of the server");
    		System.out.println("Usage is:  java Client x.x.x.x  (e.g. java Client 192.168.7.2)");
    		System.out.println("      or:  java Client hostname (e.g. java Client localhost)");
    	}    
    	System.out.println("**. End of Application.");
    }*/
}
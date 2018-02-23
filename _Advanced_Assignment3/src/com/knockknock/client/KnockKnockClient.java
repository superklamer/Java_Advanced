package com.knockknock.client;

import java.io.*;
import java.net.*;

public class KnockKnockClient {
	
    Socket kkSocket;
    PrintWriter out;
    BufferedReader in;
   
    public void clientInit() {
    	String laptopName = "";
        try {
        	laptopName = InetAddress.getLocalHost().getHostName();
            kkSocket = new Socket(laptopName, 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + laptopName + ".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + laptopName + ".");
            System.exit(1);
        }
    }
    
    public String readServerInput() {
        String fromServer;
        String output = "";
        
    	try {
			while ((fromServer = in.readLine()) != null) {
			    output = String.format("Server: %s", fromServer);
			    
			    if (fromServer.equals("Bye."))
			        break;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return output;
    }
    
    public String readClientInput() {
    	String fromUser = "";
        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));) {
			fromUser = stdIn.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	    if (fromUser != null) {
                 System.out.println("Client: " + fromUser);
                 out.println(fromUser);
 	    }
		return fromUser;
    }
	
	
    public void close() throws IOException {

        out.close();
        in.close();
        kkSocket.close();
    }
}

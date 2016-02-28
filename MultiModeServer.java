
/***
 * 
 * @author Aarti Gorade
 *
 * System which can measure the network throughput between two computers. 
 * The command line argument should allow to switch between UDP and TCP/IP.
 *
 */



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//Implementation of Server class , Mode - TCP / UDP can be selected

public class MultiModeServer {
	
	private static ServerSocket aServerSocket1;
	private static DatagramSocket aServerSocket2;
	private static Socket connection;
	private static String hostName; 
	private static int	port = 1800;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static InetAddress ip;
	
	

	private static void printMessage()	{
		System.out.println("-h		---->	help");
		System.out.println("[-mode 	typeOfConnection");
		System.out.println("[-host 		hostName");
		System.out.println(" -port 		port");
		System.out.println(" {-port 		port}");
		System.out.println("or ");
		System.out.println(" no argument");
		System.exit(0);
	   }


	public static void main(String[] args) throws ClassNotFoundException, IOException{
		
		String typeOfConnection="TCP";
		
		for (int i = 0; i < args.length; i ++) {
			   	if (args[i].equals("-h")) 
					printMessage();
			   	else if (args[i].equals("-mode")) {
			   		typeOfConnection = args[++i];
			   	}
			   	else if (args[i].equals("-host")) {
			   		hostName = args[++i];
			   		try {
						ip = InetAddress.getByName(hostName);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   		}
			   	else if (args[i].equals("-port")) {
			   		port = new Integer(args[++i]).intValue();
			   	}
			}

	try{	
	if (typeOfConnection.equals("TCP")){
		aServerSocket1 = new ServerSocket(port);
		connection=aServerSocket1.accept();
		output=new ObjectOutputStream(connection.getOutputStream());
		input=new ObjectInputStream(connection.getInputStream());
		System.out.println("\nConnection received from "+connection.getInetAddress().getHostName());
		System.out.println("Starting test to measure network Throughput for connected client ...");
		byte[] bytes = new byte[100 * 2024]; // 10K
        for (int i = 0; i < bytes.length; i++) { 
        	bytes[i] = 20; 
        }

        // send them again and again
        while (true) {
            output.write(bytes);
        }
		
	}
	else{
		System.out.println("Trying to bind on to port: "+port);
        aServerSocket2 = new DatagramSocket(port);      
        System.out.println ("Listening on port: " + aServerSocket2.getLocalPort());
        byte[] bytes = new byte[100 * 2024]; // 10K
        for (int i = 0; i < bytes.length; i++) { 
        	bytes[i] = 20; 
        }

        // send them again and again
        while (true) {
            output.write(bytes);
        }
  	}	
	}catch(Exception e){
		System.out.println("Closing connections");
	}
	
	
}
}
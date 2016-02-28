/***
 * 
 * @author Aarti Gorade
 * 
 * System which can measure the network throughput between two computers. 
 * The command line argument should allow to switch between UDP and TCP/IP.
 *
 *
 */



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


// Implementation of CLient class , Mode - TCP / UDP can be selected

public class MultiModeClient {	
	private static ServerSocket aServerSocket;
	private static Socket connection;
	static String hostName = "192.168.0.6";
	private static int	port = 1800;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static InetAddress ip;
	private static Socket client1;
	private static DatagramSocket client2;
	
	private static void printMessage()	{
		System.out.println("-h		---->	help");
		System.out.println("[-mode 	typeOfConnection");
		System.out.println("[-playerName 		playerName");
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
					e.printStackTrace();
				}
		   		}
		   	else if (args[i].equals("-port")) {
		   		port = new Integer(args[++i]).intValue();
		   	}
		}
	
	try{
	if (typeOfConnection.equals("TCP")){
		System.out.println("I m trying to connect to server");
		try {
			client1=new Socket(hostName,port);
			output=new ObjectOutputStream(client1.getOutputStream());
			input=new ObjectInputStream(client1.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connected to "+client1.getInetAddress().getHostName());
		System.out.println("\n\nConnection successful");
		long total = 0;
        long start = System.currentTimeMillis();
		System.out.println("Starting test to measure network Throughput...");
		byte[] bytes = new byte[10240]; 
		
		while(true){
			int read = input.read(bytes);
	            total += read;
	            long cost = System.currentTimeMillis() - start;
	          //  System.out.println("cost= "+cost);
	            if (cost > 0 && System.currentTimeMillis() % 1000 == 0) {
	                 System.out.println("Read " + total + " bytes, speed: " + (total / (1024.0*1024)) / (cost / 1000.0) + " MB/s");
	            }
	          
		}
		
	}
	else{
		System.out.println("I m trying to connect to server");
		client2 = new DatagramSocket();
		System.out.println("I am connected");
		byte[] data = new byte[1024];
		long total = 0;
        long start = System.currentTimeMillis();
		System.out.println("Starting test to measure network Throughput...");
		byte[] bytes = new byte[10240]; 
		
		while(true){
			int read = input.read(bytes);
			DatagramPacket packet = new DatagramPacket(data, data.length);
			client2.receive(packet);
	            total += read;
	            long cost = System.currentTimeMillis() - start;
	            //System.out.println("cost= "+cost);
	            if (cost > 0 && System.currentTimeMillis() % 1000 == 0) {
	                 System.out.println("Read " + total + " bytes, speed: " + (total / (1024.0*1024)) / (cost / 1000.0) + " MB/s");
	            }
		}
	}
	}catch(Exception e){
		System.out.println("closing connections");
	}
	
}
}

package controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import javax.swing.JTextArea;

import models.Client;

public class ServerHandler implements Runnable{
	public static final int PORT = 8080;
	private boolean run;
	private ServerSocket serverSocket;
	private JTextArea clientRequest;
	
	public static Vector<Client> clientTable;
	
	static{
		clientTable = new Vector<>();
	}
	
	public boolean init(JTextArea clientRequest){
		this.clientRequest = clientRequest;
		try {
			serverSocket = new ServerSocket(PORT,10);
			
			System.out.println("[Socket creado en puerto "+PORT+"].");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void receive(){
		Thread t = new Thread(this);
		t.start();
	}
	
	public void stop() {
		run = false;
		try {
			serverSocket.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[Se ha detenido el servidor].");
		
	}

	@Override
	public void run() {
		Socket clientSocket = null;
		run = true;
		
		while(run){
			try {
				System.out.println("Esperando solicitud.");
				clientRequest.append("Esperando solicitud.\n");
				clientSocket = serverSocket.accept();
				clientSocket.setKeepAlive(true);
				clientRequest.append("[Se ha conectado un cliente].\n");	
				new RequestHandler(clientSocket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if(e instanceof SocketException){
					
				}
				System.out.println(e.getMessage());
			} 
		}
		
		if(clientSocket != null){
			try {
				clientSocket.close();
				System.out.println("[Comunicacion con el cliente cerrada].");				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JTextArea;

public class ServerHandler implements Runnable{
	public static final int PORT = 8080;
	private boolean run;
	private ServerSocket serverSocket;
	private JTextArea clientRequest;
	
	public boolean init(JTextArea clientRequest){
		this.clientRequest = clientRequest;
		try {
			serverSocket = new ServerSocket(PORT);
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
			// TODO Auto-generated catch block
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
				System.out.println("[Se ha conectado un cliente].");
				clientRequest.append("[Se ha conectado un cliente].\n");
				
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
				BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				String request = br.readLine();
				clientRequest.append(request+"\n");
				System.out.println(request);
				String tokens [] = request.split("::");
				if(tokens.length > 1){
					request = tokens[1];
					if(request.equalsIgnoreCase("hola")){
						out.println("Hola :3 ^___^ xP");
					}else if(request.equals("help")){
						out.println("De momento la palabra 'hola' es la que te dice algo genial xD");
					}else{
						out.println("Tu mensaje ha sido recibido.");
					}
				}
				
				out.close();
				System.out.println("[Salida cerrada].");
				clientRequest.append("[Salida cerrada].\n");
				br.close();
				System.out.println("[Lector cerrado].");
				System.out.println("[Lector cerrado].");
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

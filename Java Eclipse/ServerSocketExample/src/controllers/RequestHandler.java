package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Iterator;

import models.Client;

public class RequestHandler implements Runnable{
	private Socket requestSocket;
	
	public RequestHandler(Socket requestSocket){
		this.requestSocket = requestSocket;
		Thread t = new Thread(this);
		t.start();
	}
	
	
	@Override
	public void run() {
		try {
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
					requestSocket.getInputStream()));
			String request = in.readLine();
			String tokens [] = request.split("::");			
			String action;
			String user;
			if(tokens.length > 1){				
				action = tokens[0];
				user = tokens[1];
				switch(action){
				
				case "sign":
					synchronized (this) {
						Client c = new Client(requestSocket,user);
						ServerHandler.clientTable.add(c);
						PrintWriter out = c.getOut();
						out.println("Te has conectado");
						out.close();
					}					
					break;
				case "msg":
					String msg = tokens[2];
					sendMsgToAll(user,msg);					
					break;
				case "hola":
					PrintWriter out = new PrintWriter(requestSocket.getOutputStream());
					out.println("Esto si esta funcionando :3");
					//out.close();
					break;				
				}
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}


	private void sendMsgToAll(String user, String msg) {
		String response = user+": "+msg;
		for (Client c : ServerHandler.clientTable) {
			PrintWriter out = c.getOut();
			out.print(response);
		}	
	}
}

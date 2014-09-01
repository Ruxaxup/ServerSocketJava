package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private Socket echoSocket;
	private String name;
	private PrintWriter out;
	private BufferedReader in;
	
	
	
	public Client(Socket echoSocket, String name) {		
		this.echoSocket = echoSocket;
		this.name = name;
		try {
			this.out = new PrintWriter(echoSocket.getOutputStream());
			this.in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	/**
	 * @return the echoSocket
	 */
	public Socket getEchoSocket() {
		return echoSocket;
	}
	/**
	 * @param echoSocket the echoSocket to set
	 */
	public void setEchoSocket(Socket echoSocket) {
		this.echoSocket = echoSocket;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	/**
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}
	/**
	 * @param in the in to set
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
	
}

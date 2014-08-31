package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import controllers.ServerHandler;

public class ServerGUI extends JFrame implements ActionListener{
	private JButton startServer;
	private JButton stopServer;
	private JButton cleanConsole;
	private JTextArea clientRequest;
	
	private ServerHandler server;
	
	public ServerGUI(){
		/** Initiate GUI components **/
		setTitle("Server");
		setPreferredSize(new Dimension(640,480));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		pack();
		
		/** Create instance of our server controller **/
		server = new ServerHandler();
	}

	private void initComponents() {
		JPanel mainPanel = new JPanel();
		JPanel leftPanel = new JPanel(new GridLayout(3,1));		
		mainPanel.setLayout(new BorderLayout());		
		
		startServer = new JButton("Start Server");		
		startServer.addActionListener(this);
		stopServer = new JButton("Stop Server");
		stopServer.addActionListener(this);
		stopServer.setEnabled(false);
		cleanConsole = new JButton("Clean Console");
		cleanConsole.addActionListener(this);
		
		clientRequest = new JTextArea(20,20);
		clientRequest.setEditable(false);
		clientRequest.setBorder(new TitledBorder("Client's Requests"));
		
		leftPanel.add(startServer);
		leftPanel.add(stopServer);
		leftPanel.add(cleanConsole);
		mainPanel.add(BorderLayout.WEST,leftPanel);
		mainPanel.add(BorderLayout.CENTER,clientRequest);
		
		add(mainPanel);		
	}
	
	public static void main(String[] args) {
		ServerGUI serverGUI = new ServerGUI();		
		serverGUI.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object == startServer){		
			server.init(clientRequest);
			server.receive();
			startServer.setEnabled(false);
			stopServer.setEnabled(true);
		}else if(object == stopServer){
			server.stop();
			stopServer.setEnabled(false);
			startServer.setEnabled(true);
		}else{
			clientRequest.setText("");
		}
	}
}

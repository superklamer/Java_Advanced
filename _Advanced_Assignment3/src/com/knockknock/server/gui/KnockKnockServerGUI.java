package com.knockknock.server.gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.KeyManagementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.knockknock.server.KKMultiServer;
import com.knockknock.server.KKServerConst;

public class KnockKnockServerGUI extends JFrame {
	
	private static final long serialVersionUID = 6995715762008339632L;
	
	private final JPanel mainPanel;
	private final JButton startServer;
	private final JButton stopServer;
	private final JLabel statusLabel;
	private final JPanel buttonPanel;
	private final BorderLayout mainLayout;
	private final FlowLayout buttonLayout;
	
	private final ExecutorService pool;
	private KKMultiServer server;
	
	public KnockKnockServerGUI() {
		super("Knock Knock Server");
		 
		server = new KKMultiServer();
		pool = Executors.newFixedThreadPool(KKServerConst.MAXTHREADS.getValue());
		
		mainLayout = new BorderLayout();
		mainPanel = new JPanel(mainLayout);
		
		buttonLayout = new FlowLayout(FlowLayout.CENTER, 5, 1);
		ButtonHandler buttonHandler = new ButtonHandler();
		startServer = new JButton("Start server");
		startServer.addActionListener(buttonHandler);
		stopServer = new JButton("Stop server");
		stopServer.addActionListener(buttonHandler);
		buttonPanel = new JPanel(buttonLayout);
		
		buttonPanel.add(startServer);
		buttonPanel.add(stopServer);
		
		statusLabel = new JLabel("The status will be shown here: ");
		
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(statusLabel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
	}
	
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startServer) {
				
				if (server.isListening()) {
					pool.execute(server);
					statusLabel.setText("Server listening on port: "  + server.getPort());
					
					startServer.setEnabled(false);
					stopServer.setEnabled(true);
				}
				else {
					statusLabel.setText("Server is off and not accepting connections");
					startServer.setEnabled(true);
					stopServer.setEnabled(false);
				}
			}
			else if (e.getSource() == stopServer) {
				server.setListening(false);
				pool.shutdown();
				
				startServer.setEnabled(true);
				stopServer.setEnabled(false);
				statusLabel.setText("Server stopped");
			}
		}
		
	}
	
	void shutdownAndAwaitTermination(ExecutorService pool) {
		   pool.shutdown(); // Disable new tasks from being submitted
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
		       pool.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!pool.awaitTermination(60, TimeUnit.SECONDS))
		           System.err.println("Pool did not terminate");
		     }
		   } catch (InterruptedException ie) {
		     // (Re-)Cancel if current thread also interrupted
		     pool.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
		 }
	
	public static void main(String[] args) {
		KnockKnockServerGUI gui = new KnockKnockServerGUI();
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(450, 350);
		gui.setVisible(true);
	}
	
}










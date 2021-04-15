package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Timer;

import server.game.QuestGame;

public class Server {

    public boolean gameRunning = false;
    
	private QuestGame game;
	
	private int port = 7096;
	
	private HashMap<Integer, ClientThread> clients = new HashMap<>();
	
	private Random random;
	
    public Timer gameTimer = new Timer(5, new ActionListener() { // gameticker
        public void actionPerformed(ActionEvent e) {
        	if (gameRunning) {
                game.tick();
        	}
        }
    });

	public Server(boolean local, int seed) {
		this.random = new Random(seed);
		
		this.game = new QuestGame();
        this.gameTimer.start();
		
		if (local) {
			
		} else {
			listenForClients();
		}
	}
	
	private void listenForClients() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// while the game is running, continue listening on the given socket
		while (gameRunning) {
			Socket socket = null;
		    try {
				socket = serverSocket.accept();
				ClientThread t = new ClientThread(this, socket);
				t.startThread();
				clients.put(random.nextInt(), t);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(2);
			}
		}
		
		// close all sockets
		for (ClientThread t : clients.values()) {
			try {
				t.stopThread();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// close server
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

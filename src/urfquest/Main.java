package urfquest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import urfquest.client.Client;
import urfquest.client.QuestPanel;
import urfquest.server.Server;

public class Main implements Runnable {
	
	// logger
	public static Logger logger;
	
	// game state container
	public static Server server;
	
    // should never need to be accessed
	private static Main root;
	
	// for getting graphics properties
    public static JFrame frame;
    public static QuestPanel panel;
    
    // for client-server communication
    public static Client client;
	
	// frame properties
	public static boolean isFullscreen;
	
	public static void main(String[] args) {
		logger = new Logger(Logger.LOG_DEBUG);
		
		// parse arguments
		String ip = "localhost";
		int port = 7096;
		int mode = 0;
		if (args.length > 0) {
			ip = args[0];
		}
		if (args.length > 1) {
			port = Integer.parseInt(args[1]);
		}
		if (args.length > 2) {
			mode = Integer.parseInt(args[2]);
		}
		
		// start either the client, the server, or both
		if (mode == 0) {
			// client and server
			int portMirror = port;
			new Thread(() -> {
				startServer(0, portMirror);
			}).start();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startClient(ip, port);
		} else if (mode == 1) {
			// client only
			startClient(ip, port);
		} else if (mode == 2) {
			// server only
			startServer(0, port);
		}
	}
	
	public static void startServer(int seed, int port) {
		logger.all("Starting UrfQuest server");
		Main.server = new Server(seed, port);
		Main.server.processMessages();
	}
	
	public static void startClient(String ip, int port) {
		logger.all("Starting UrfQuest client");
		root = new Main();
		
		// initialize the game client
		Socket socket = null;
        try {
			socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // initialize the networking engine
        client = new Client(socket);
        Thread clientThread = new Thread(client);
        clientThread.start();

        // initialize the display and java swing framework
		SwingUtilities.invokeLater(root);
	}

	@Override
	public void run() {
        Main.panel = new QuestPanel();
        panel.initOverlays();
        resetFrame(true);
        panel.renderTimer.start();
        panel.inputScanTimer.start();
	}
	
	public static void resetFrame(boolean fullscreen) {
		if (frame != null) {
			frame.dispose();
		}
		
        frame = new JFrame("UrfQuest");

        if (fullscreen) {
    		frame.setResizable(false);
    		frame.setUndecorated(true);
    		isFullscreen = true;
        } else {
            frame.setMinimumSize(new Dimension(700, 600));
    		frame.setResizable(true);
    		frame.setExtendedState(JFrame.NORMAL);
    		frame.setUndecorated(false);
    		isFullscreen = false;
        }
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		
		frame.add(panel);
		frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                panel.setSize(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
            }
        });
	}
}

package urfquest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import urfquest.client.Client;
import urfquest.client.QuestPanel;
import urfquest.server.Server;

public class Main implements Runnable {
	
	static final int MODE_FULL = 0;
	static final int MODE_CLIENT = 1;
	static final int MODE_SERVER = 2;
	
	// logger
	public static Logger launchLogger;
	
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
	
	// startup arguments
	public static String ip = "localhost";
	public static int port = 7096;
	public static int mode = MODE_FULL;
	public static String playerName = "default";
	
	public static void main(String[] args) {
		launchLogger = new Logger(Logger.LogLevel.LOG_DEBUG, "LAUNCHER");
		
		// check for proper number of arguments
		if (args.length == 3) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
			mode = Integer.parseInt(args[2]);
			playerName = args[3];
		} else if (args.length == 0) {
			// try to load last used config from file
			File startupPrefs = new File("startupPrefs.config");
			if (startupPrefs.exists()) {
				try {
					launchLogger.info("loading config file");
					BufferedReader prefsReader = new BufferedReader(new FileReader(startupPrefs));
					ip = prefsReader.readLine();
					port = Integer.parseInt(prefsReader.readLine());
					mode = Integer.parseInt(prefsReader.readLine());
					playerName = prefsReader.readLine();
					prefsReader.close();
				} catch (IOException e) {
					System.err.println("Malformed prefs file. going with defaults");
				}
			}
			
			// create the startup dialog with filled-in defaults
			launchLogger.info("opening startup dialog");
			StartupDialog dialog = new StartupDialog(ip, port + "", mode, playerName);
			
			// load inputs from dialog
			ip = dialog.ip.getText();
			port = Integer.parseInt(dialog.portNum.getText());
			switch (dialog.modeGroup.getSelection().getActionCommand()) {
			case StartupDialog.SERVER_CLIENT_STRING:
				mode = 0;
				break;
			case StartupDialog.CLIENT_ONLY_STRING:
				mode = 1;
				break;
			case StartupDialog.SERVER_ONLY_STRING:
				mode = 2;
				break;
			}
			playerName = dialog.playerName.getText();
			
			// save inputs to prefs file
			try {
				launchLogger.info("saving config file");
				PrintWriter prefsWriter = new PrintWriter(new FileWriter(startupPrefs));
				prefsWriter.println(ip);
				prefsWriter.println(port + "");
				prefsWriter.println(mode + "");
				prefsWriter.println(playerName);
				prefsWriter.close();
			} catch (IOException e) {
				System.err.println("Error writing startup prefs to file");
			}
		} else if (args.length != 0) {
			System.err.println("Wrong number of arguments. Usage:");
			System.err.println("UrfQuest.java [<ip> <port> <mode> <playerName>]");
			System.exit(1);
		}
		
		// start either the client, the server, or both
		if (mode == MODE_FULL) {
			// client and server
			Thread serverThread = new Thread(() -> {
				startServer(0, port);
			});
			serverThread.setName("ServerProcessorThread");
			serverThread.start();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startClient(ip, port);
		} else if (mode == MODE_CLIENT) {
			// client only
			startClient(ip, port);
		} else if (mode == MODE_SERVER) {
			// server only
			startServer(0, port);
		}
		
		launchLogger.info("all launcher tasks done");
	}
	
	public static void startServer(int seed, int port) {
		launchLogger.all("Starting server on port " + port);
		Main.server = new Server(seed, port);
		Main.server.processMessages();
	}
	
	public static void startClient(String ip, int port) {
		launchLogger.all("Starting client, connecting to " + ip + ":" + port);
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
        clientThread.setName("LocalClientThread");
        clientThread.start();
	}
	
	public static void initClientFrontend() {
        // initialize the display and java swing framework
		SwingUtilities.invokeLater(root);
	}

	@Override
	public void run() {
        Main.panel = new QuestPanel();
        panel.initOverlays();
        resetFrame(false);
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
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

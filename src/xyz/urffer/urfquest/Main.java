package xyz.urffer.urfquest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.shared.Constants;

public class Main {
	
	// reference to instance of self. necessary for initialization of resources
	public static Main self = new Main();
	
	// logger
	public static Logger mainLogger;
	
	// startup arguments
	private static String ip = "localhost";
	private static int port = 7096;
	private static StartupMode mode = StartupMode.FULL;
	private static String playerName = "playerName";
	
//	public static LogLevel debugLevel = LogLevel.ALL;
	public static LogLevel debugLevel = LogLevel.NONE;
	
	public static void main(String[] args) {
		mainLogger = new Logger(debugLevel, "LAUNCHER");
		
		// check for proper number of arguments
		if (args.length == 3) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
			mode = StartupMode.valueOf(Integer.parseInt(args[2]));
			playerName = args[3];
		} else if (args.length == 0) {
			// try to load last used config from file
			File startupPrefs = new File(Constants.FILE_STARTUP_PREFS);
			if (startupPrefs.exists()) {
				try {
					mainLogger.info("loading config file");
					BufferedReader prefsReader = new BufferedReader(new FileReader(startupPrefs));
					ip = prefsReader.readLine();
					port = Integer.parseInt(prefsReader.readLine());
					mode = StartupMode.valueOf(Integer.parseInt(prefsReader.readLine()));
					playerName = prefsReader.readLine();
//					debugLevel = LogLevel.valueOf(prefsReader.readLine());
					prefsReader.close();
				} catch (IOException e) {
					System.err.println("Malformed prefs file. going with defaults");
				}
			}
			
			// create the startup dialog with filled-in defaults
			mainLogger.info("opening startup dialog");
			StartupDialog dialog = new StartupDialog(ip, port + "", mode, playerName);
			
			// load inputs from dialog
			ip = dialog.ip.getText();
			port = Integer.parseInt(dialog.portNum.getText());
			switch (dialog.modeGroup.getSelection().getActionCommand()) {
			case StartupDialog.SERVER_CLIENT_STRING:
				mode = StartupMode.FULL;
				break;
			case StartupDialog.CLIENT_ONLY_STRING:
				mode = StartupMode.CLIENT_ONLY;
				break;
			case StartupDialog.SERVER_ONLY_STRING:
				mode = StartupMode.SERVER_ONLY;
				break;
			}
			playerName = dialog.playerName.getText();
			if (dialog.useDebug.isSelected()) {
//				debugLevel = LogLevel.ALL;
			} else {
//				debugLevel = LogLevel.INFO;
			}
			
			// save inputs to prefs file
			try {
				mainLogger.info("saving config file");
				PrintWriter prefsWriter = new PrintWriter(new FileWriter(startupPrefs));
				prefsWriter.println(ip);
				prefsWriter.println(port + "");
				prefsWriter.println(mode.value + "");
				prefsWriter.println(playerName);
//				prefsWriter.println(debugLevel);
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
		if (mode == StartupMode.FULL) {
			startServer(Constants.DEFAULT_SERVER_SEED, port);
			try {
				// Sleep briefly to give the server time to boot up
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startClient(ip, port, playerName);
		} else if (mode == StartupMode.CLIENT_ONLY) {
			// client only
			startClient(ip, port, playerName);
		} else if (mode == StartupMode.SERVER_ONLY) {
			// server only
			startServer(Constants.DEFAULT_SERVER_SEED, port);
		}
		
		mainLogger.info("all launcher tasks done");
	}
	
	private static void startServer(long seed, int port) {
		mainLogger.all("Starting server on port " + port);
		Server server = new Server(seed, port);
		server.getLogger().setLogLevel(debugLevel);
		Thread serverThread = new Thread(new Runnable() {
			public void run() {
				server.mainLoop();
			}
		});
		serverThread.setName("LocalServerThread");
		serverThread.start();
		
		server.initListenerThread();
	}

	private static void startClient(String ip, int port, String playerName) {
		mainLogger.all("Starting client, connecting to " + ip + ":" + port);
		
		// initialize the game client
		Socket socket = null;
        try {
			socket = new Socket(ip, port);
	        mainLogger.debug(socket.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // initialize the networking engine
        Client client = new Client(socket, playerName);
        client.getLogger().setLogLevel(debugLevel);
        Thread clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				client.mainLoop();
			}
        });
        clientThread.setName("LocalClientThread");
        clientThread.start();
	}
}

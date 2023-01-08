package xyz.urffer.urfquest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfutils.math.PairInt;

/**
 * UrfQuest (name change pending). A top-down, single or multiplayer 
 * procedurally generated adventure roleplaying game.
 * 
 * @author Chris Urffer
 * @version %I%, %G%
 */
public class Main {
	
	// reference to instance of self. necessary for initialization of resources
	public static Main self = new Main();
	
	// logger
	public static Logger mainLogger;
	
	// Debug level
	public static LogLevel debugLevel = LogLevel.INFO;
	
	/**
	 * Starts either the server, client, or both. Instantiates loggers, reads 
	 * startup preferences, parses arguments
	 * @param args
	 * 	set of arguments passed in via the command line
	 */
	public static void main(String[] args) {
		// Create argument parser
		ArgumentParser parser = ArgumentParsers.newFor("UrfQuest").build()
	                .defaultHelp(true)
	                .description("A 2D top-down adventure game");
		
		parser.addArgument("-g", "--gui")
				.type(Boolean.class)
				.setDefault(Boolean.FALSE);
		
		parser.addArgument("-m", "--mode")
				.type(StartupMode.class)
				.setDefault(StartupMode.FULL);
		parser.addArgument("-i", "--ip")
				.type(String.class)
				.setDefault("localhost")
				.help("IP address to connect to");
		parser.addArgument("-p", "--port")
				.type(Integer.class)
				.setDefault(7096)
				.help("Port to connect to");
		parser.addArgument("-n", "--playerName")
				.type(String.class)
				.setDefault("Chris")
				.help("The name of the player to use");
		parser.addArgument("-d", "--debuglevel")
				.type(LogLevel.class)
				.setDefault(debugLevel);
		parser.addArgument("--windowWidth")
				.type(Integer.class)
				.setDefault(1440)
				.help("Width of game window upon first opening");
		parser.addArgument("--windowHeight")
				.type(Integer.class)
				.setDefault(900)
				.help("Height of game window upon first opening");
		parser.addArgument("--windowXPos")
				.type(Integer.class)
				.setDefault(0)
				.help("X-position of the window upon first opening");
		parser.addArgument("--windowYPos")
				.type(Integer.class)
				.setDefault(0)
				.help("Y-position of the window upon first opening");
		
		
		// Parse arguments
		Namespace ns = null;
		try {
		    ns = parser.parseArgs(args);
		} catch (ArgumentParserException e) {
		    parser.handleError(e);
		    System.exit(1);
		}
		Boolean guiStart = ns.getBoolean("gui");
		StartupMode mode = (StartupMode)ns.get("mode");
		String ip = ns.getString("ip");
		Integer port = ns.getInt("port");
		String playerName = ns.getString("playerName");
		LogLevel debugLevel = (LogLevel)ns.get("debuglevel");
		PairInt initWindowDims = new PairInt(
				ns.getInt("windowWidth").intValue(),
				ns.getInt("windowHeight").intValue()
		);
		PairInt initWindowPos = new PairInt(
				ns.getInt("windowXPos").intValue(),
				ns.getInt("windowYPos").intValue()
		);
		
		// Create the launcher's logger
		mainLogger = new Logger(debugLevel, "LAUNCHER");
		
		// Parse arguments through GUI/config if requested
		if (guiStart.booleanValue() == true) {
			// try to load last used config from file
			File startupPrefs = new File(Constants.FILE_STARTUP_PREFS);
			if (startupPrefs.exists()) {
				mainLogger.info("loading config file");
				try {
					JSONParser jsonParser = new JSONParser();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(startupPrefs));
					playerName = (String)jsonObject.keySet().iterator().next();
					
					JSONObject firstProfileObject = (JSONObject)jsonObject.get(playerName);
					ip = (String)firstProfileObject.get("ip");
					port = Integer.parseInt((String)firstProfileObject.get("port"));
					mode = StartupMode.valueOf(Integer.parseInt((String)firstProfileObject.get("mode")));
					debugLevel = LogLevel.valueOf((String)firstProfileObject.get("debugLevel"));
				} catch (IOException e) {
					System.err.println("Malformed prefs file. going with defaults");
				} catch (ParseException e) {
					e.printStackTrace();
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
				debugLevel = LogLevel.ALL;
			} else {
				debugLevel = LogLevel.INFO;
			}
			
			// save inputs to prefs file
			mainLogger.info("saving config file");
			try {
				JSONObject userPrefsObject = new JSONObject();
				userPrefsObject.put("ip", ip);
				userPrefsObject.put("port", port + "");
				userPrefsObject.put("mode", mode.value + "");
				userPrefsObject.put("debugLevel", debugLevel.toString());
				
				JSONObject prefsObject = new JSONObject();
				prefsObject.put(playerName, userPrefsObject);
				
				FileWriter prefsWriter = new FileWriter(startupPrefs);
				prefsWriter.write(prefsObject.toJSONString());
				prefsWriter.close();
			} catch (IOException e) {
				System.err.println("Error writing startup prefs to file");
			}
		}
		
		// Start either the client, the server, or both
		if (mode == StartupMode.FULL) {
			startServer(debugLevel, Constants.DEFAULT_SERVER_SEED, port);
			try {
				// Sleep briefly to give the server time to boot up
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startClient(debugLevel, ip, port, playerName, initWindowDims, initWindowPos);
		} else if (mode == StartupMode.CLIENT_ONLY) {
			// client only
			startClient(debugLevel, ip, port, playerName, initWindowDims, initWindowPos);
		} else if (mode == StartupMode.SERVER_ONLY) {
			// server only
			startServer(debugLevel, Constants.DEFAULT_SERVER_SEED, port);
		}
		
		mainLogger.info("all launcher tasks done");
	}
	
	/**
	 * Starts a new Server on the specified port and with the specified seed.
	 * 
	 * @param seed
	 * 	The number to use as random number generator seed for the server
	 * @param port
	 * 	The port to start the server on
	 * @return
	 * 	A new Server object, which the listener thread has been started for
	 */
	private static Server startServer(LogLevel debugLevel, long seed, int port) {
		mainLogger.info("Starting server on port " + port);
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
		
		return server;
	}

	/**
	 * Starts a new Client, connects it to the specified port and IP, and
	 * 	requests the specified name for the Client's player.
	 * 
	 * @param ip
	 * 	The IP to connect the Client to
	 * @param port
	 * 	The port to connect the Client to
	 * @param playerName
	 * 	The name to request from the server for this client's player
	 * @param windowWidth
	 * 	Initial width dimension of the game window
	 * @param windowHeight
	 * 	Initial height dimension of the game window
	 * @return
	 * 	A new client object, which has attempted to connect and requested
	 * 	the given name
	 */
	private static Client startClient(LogLevel debugLevel, String ip, int port, 
			String playerName, PairInt initWindowDims, PairInt initWindowPos) {
		mainLogger.info("Starting client, connecting to " + ip + ":" + port);
		
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
        Client client = new Client(socket, playerName, initWindowDims, initWindowPos);
        client.getLogger().setLogLevel(debugLevel);
        Thread clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				client.mainLoop();
			}
        });
        clientThread.setName("LocalClientThread");
        clientThread.start();
        
        return client;
	}
}

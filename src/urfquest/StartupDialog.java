package urfquest;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class StartupDialog {
	
	public JTextField ip;
	public JTextField portNum;
	public JTextField playerName;
	public ButtonGroup modeGroup = new ButtonGroup();
	
	public static final String SERVER_ONLY_STRING = "Server only";
	public static final String CLIENT_ONLY_STRING = "Client only";
	public static final String SERVER_CLIENT_STRING = "Both";
	
	public StartupDialog(String defaultIP, String defaultPortNum, int startupMode, String defaultPlayerName) {
		ip = new JTextField(defaultIP, 10);
		portNum = new JTextField(defaultPortNum, 10);
		playerName = new JTextField(defaultPlayerName, 10);
		
		// set up mode radio buttons
		JRadioButton serverOnly = new JRadioButton(SERVER_ONLY_STRING);
		serverOnly.setActionCommand(SERVER_ONLY_STRING);
		JRadioButton clientOnly = new JRadioButton(CLIENT_ONLY_STRING);
		clientOnly.setActionCommand(CLIENT_ONLY_STRING);
		JRadioButton clientServer = new JRadioButton(SERVER_CLIENT_STRING);
		clientServer.setActionCommand(SERVER_CLIENT_STRING);
		
		switch (startupMode) {
		case Main.MODE_FULL:
			clientServer.setSelected(true);
			break;
		case Main.MODE_SERVER:
			serverOnly.setSelected(true);
			break;
		case Main.MODE_CLIENT:
			clientOnly.setSelected(true);
			break;
		default:
			clientServer.setSelected(true);
			break;
		}
		
		modeGroup.add(serverOnly);
		modeGroup.add(clientOnly);
		modeGroup.add(clientServer);
		
		JPanel modeRadioPanel = new JPanel(new GridLayout(0, 1));
		modeRadioPanel.add(serverOnly);
		modeRadioPanel.add(clientOnly);
		modeRadioPanel.add(clientServer);
		
		// set up text input panel
		JPanel textInputPanel = new JPanel(new GridLayout(3, 2));
		textInputPanel.add(new JLabel("IP:"));
		textInputPanel.add(ip);
		textInputPanel.add(new JLabel("Port:"));
		textInputPanel.add(portNum);
		textInputPanel.add(new JLabel("Player Name:"));
		textInputPanel.add(playerName);
		
		// create whole panel
		JPanel myPanel = new JPanel(new GridLayout(0, 1));
		myPanel.add(textInputPanel);
		myPanel.add(modeRadioPanel);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, 
				"Enter server IP", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			return;
		} else {
			System.exit(0);
		}
	}
}

package urfquest;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class StartupDialog implements ActionListener {
	
	public JTextField ip;
	public JTextField portNum;
	public JTextField playerName;
	public ButtonGroup modeGroup = new ButtonGroup();
	
	public static final String SERVER_ONLY_STRING = "Server only";
	public static final String CLIENT_ONLY_STRING = "Client only";
	public static final String SERVER_CLIENT_STRING = "Both";
	
	public StartupDialog(String defaultIP, String defaultPortNum, StartupMode startupMode, String defaultPlayerName) {
		ip = new JTextField(defaultIP, 10);
		portNum = new JTextField(defaultPortNum, 10);
		playerName = new JTextField(defaultPlayerName, 10);
		
		// set up text input panel
		JPanel textInputPanel = new JPanel(new GridLayout(3, 2));
		textInputPanel.add(new JLabel("IP:"));
		textInputPanel.add(ip);
		textInputPanel.add(new JLabel("Port:"));
		textInputPanel.add(portNum);
		textInputPanel.add(new JLabel("Player Name:"));
		textInputPanel.add(playerName);
		
		// set up mode radio buttons
		JRadioButton serverOnly = new JRadioButton(SERVER_ONLY_STRING);
		serverOnly.setActionCommand(SERVER_ONLY_STRING);
		serverOnly.addActionListener(this);
		JRadioButton clientOnly = new JRadioButton(CLIENT_ONLY_STRING);
		clientOnly.setActionCommand(CLIENT_ONLY_STRING);
		clientOnly.addActionListener(this);
		JRadioButton clientServer = new JRadioButton(SERVER_CLIENT_STRING);
		clientServer.setActionCommand(SERVER_CLIENT_STRING);
		clientServer.addActionListener(this);
		
		switch (startupMode) {
		case FULL:
			clientServer.doClick();
			break;
		case SERVER_ONLY:
			serverOnly.doClick();
			break;
		case CLIENT_ONLY:
			clientOnly.doClick();
			break;
		default:
			clientServer.doClick();
			break;
		}
		
		modeGroup.add(serverOnly);
		modeGroup.add(clientOnly);
		modeGroup.add(clientServer);
		
		JPanel modeRadioPanel = new JPanel(new GridLayout(0, 1));
		modeRadioPanel.add(serverOnly);
		modeRadioPanel.add(clientOnly);
		modeRadioPanel.add(clientServer);
		
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

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case SERVER_ONLY_STRING:
			ip.setEnabled(false);
			portNum.setEnabled(true);
			playerName.setEnabled(false);
			break;
		case CLIENT_ONLY_STRING:
			ip.setEnabled(true);
			portNum.setEnabled(true);
			playerName.setEnabled(true);
			break;
		case SERVER_CLIENT_STRING:
			ip.setEnabled(false);
			portNum.setEnabled(true);
			playerName.setEnabled(true);
			break;
		}
	}
}

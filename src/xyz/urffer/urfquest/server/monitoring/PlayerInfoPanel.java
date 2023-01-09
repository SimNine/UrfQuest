package xyz.urffer.urfquest.server.monitoring;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.UserMap;
import xyz.urffer.urfquest.server.entities.mobs.Player;

@SuppressWarnings("serial")
public class PlayerInfoPanel extends JPanel {
	
	private Server server;
	
	private static Dimension minimumDims = new Dimension(400, 500);

	public PlayerInfoPanel(Server s) {
		super();
		this.setMinimumSize(minimumDims);
		this.setPreferredSize(minimumDims);
		this.setSize(minimumDims);
		this.server = s;
	}

	@Override
	public void paintComponent(Graphics g) {
		UserMap userMap = server.getUserMap();
		
		// Draw background
		g.setColor(Color.GRAY.brighter());
		g.fillRect(0, 0, minimumDims.width, minimumDims.height);
		
		// Draw list of players
		g.setColor(Color.BLACK);
		int lineHeight = 15;
		g.drawString("ClientID", 0, lineHeight);
		g.drawString("PlayerID", 75, lineHeight);
		g.drawString("PlayerName", 150, lineHeight);
		g.drawString("Position", 225, lineHeight);
		for (int clientID : userMap.getAllClientIDs()) {
			int playerID = userMap.getPlayerIdFromClientId(clientID);
			Player player = (Player)server.getState().getEntity(playerID);
			
			lineHeight += 15;
			g.drawString(clientID + "", 0, lineHeight);
			g.drawString(userMap.getPlayerIdFromClientId(clientID) + "", 75, lineHeight);
			g.drawString(userMap.getPlayerNameFromClientId(clientID) + "", 150, lineHeight);
			g.drawString(player.getPos().toInt().toString(), 225, lineHeight);
		}
	}
	
}

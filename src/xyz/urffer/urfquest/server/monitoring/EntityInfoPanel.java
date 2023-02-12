package xyz.urffer.urfquest.server.monitoring;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.map.Map;

@SuppressWarnings("serial")
public class EntityInfoPanel extends JPanel {
	
	private Server server;
	
	private static Dimension minimumDims = new Dimension(200, 500);

	public EntityInfoPanel(Server s) {
		super();
		this.setMinimumSize(minimumDims);
		this.setPreferredSize(minimumDims);
		this.setSize(minimumDims);
		this.server = s;
	}

	@Override
	public void paintComponent(Graphics g) {
		Map map = server.getState().getSurfaceMap();
		
		// Draw background
		g.setColor(Color.GRAY.brighter());
		g.fillRect(0, 0, minimumDims.width, minimumDims.height);
		
		// Draw list of players
		g.setColor(Color.BLACK);
		int lineHeight = 15;
		int lineNum = 1;
		g.drawString("Num players: " + map.getPlayers().size(), 0, lineHeight*(lineNum++));
		g.drawString("Num projectiles: " + map.getProjectiles().size(), 0, lineHeight*(lineNum++));
		g.drawString("Num mobs: " + map.getMobs().size(), 0, lineHeight*(lineNum++));
		g.drawString("Num items: " + map.getItems().size(), 0, lineHeight*(lineNum++));
//		for (int clientID : userMap.getAllClientIDs()) {
//			int playerID = userMap.getPlayerIdFromClientId(clientID);
//			Player player = (Player)server.getState().getEntity(playerID);
//			
//			lineHeight += 15;
//			g.drawString(clientID + "", 0, lineHeight);
//			g.drawString(userMap.getPlayerIdFromClientId(clientID) + "", 75, lineHeight);
//			g.drawString(userMap.getPlayerNameFromClientId(clientID) + "", 150, lineHeight);
//			g.drawString(player.getPos().toInt().toString(), 225, lineHeight);
//		}
	}
	
}

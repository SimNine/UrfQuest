package xyz.urffer.urfquest.server.monitoring;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map.Entry;

import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.entities.mobs.Player;
import xyz.urffer.urfquest.server.map.MapChunk;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfutils.pannablepanel.PannablePanel;

@SuppressWarnings("serial")
public class MapMonitor extends PannablePanel {
	
	private Server server;

	public MapMonitor(Server s, int width, int height) {
		super(width, height, true);
		this.server = s;
	}

	@Override
	public void paintComponent(Graphics g) {
		// draw background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// draw chunks
		g.setColor(Color.GREEN);
		for (Entry<Integer, HashMap<Integer, MapChunk>> row : server.getState().getSurfaceMap().getAllChunks().entrySet()) {
			int xChunk = row.getKey();
			for (Entry<Integer, MapChunk> col : row.getValue().entrySet()) {
				int yChunk = col.getKey();
				MapChunk chunk = col.getValue();
				
				int xDisp = xChunk*Constants.MAP_CHUNK_SIZE;
				int yDisp = yChunk*Constants.MAP_CHUNK_SIZE;
				g.drawImage(chunk.getMinimap(), 
							-xScr + xDisp, -yScr + yDisp, null);
				
				// if debug, draw chunk grid
				if (server.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
					g.setColor(Color.RED);
					g.drawRect(-xScr + xDisp, -yScr + yDisp, 
							   Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE);
				}
			}
		}
		
		// draw players with nameplates
		for (Player p : server.getState().getSurfaceMap().getPlayers().values()) {
			PairInt pPos = p.getPos().toInt();
			g.setColor(Color.BLACK);
			g.drawString(p.getName(), -xScr + pPos.x, -yScr + pPos.y);
			g.fillRect(-xScr + pPos.x, -yScr + pPos.y, 5, 5);
		}
		
		// draw mobs with purple nameplates
		for (Mob m : server.getState().getSurfaceMap().getMobs().values()) {
			PairInt pPos = m.getPos().toInt();
			g.setColor(Color.MAGENTA);
			g.fillRect(-xScr + pPos.x, -yScr + pPos.y, 3, 3);
		}
	}

}

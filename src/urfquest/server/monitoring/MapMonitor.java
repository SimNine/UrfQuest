package urfquest.server.monitoring;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map.Entry;

import urf.pannablepanel.PannablePanel;
import urfquest.LogLevel;
import urfquest.server.Server;
import urfquest.server.entities.mobs.Player;
import urfquest.server.map.MapChunk;
import urfquest.shared.ArrayUtils;
import urfquest.shared.Constants;

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
			int[] pPos = ArrayUtils.castToIntArr(p.getPos());
			g.setColor(Color.BLACK);
			g.drawString(p.getName(), -xScr + pPos[0], -yScr + pPos[1]);
			g.fillRect(-xScr + pPos[0], -yScr + pPos[1], 5, 5);
		}
	}

}

package framework;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import display.QuestPanel;
import game.Entity;
import game.QuestGame;
import game.QuestMap;

public class V {
	public static int scale = 3; // the scale is 1/10th the width of a block, i.e., the width of one "pixel" of a block
	public static int dispCenterX; // the center of this JPanel relative to the top-left corner, in pixels
	public static int dispCenterY;
	public static int dispTileWidth; // the number of blocks needed to fill the screen
	public static int dispTileHeight;
	
	public static double gameCenterX;
	public static double gameCenterY;
	public static int gameCenterTileX;
	public static int gameCenterTileY;
	public static int facing;
	public static double health = 100.0;
	public static double mana = 100.0;
	public static double speed = 0.05;
	
	public static Entity[] entities;
	
	public static QuestMap qMap;
	public static QuestGame qGame;
	public static QuestPanel qPanel;
	
	public static Set<Integer> keys = new HashSet<Integer>(0);
    public static Timer time = new Timer(5, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            qGame.tick();
            qPanel.repaint();
        }
    });
}

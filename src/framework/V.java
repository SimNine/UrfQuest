package framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import entities.Entity;
import entities.characters.Player;
import display.QuestPanel;
import game.QuestGame;
import game.QuestMap;

public class V {
	public static boolean debug = true;
	
	public static int tileWidth = 30; // the width, in pixels, of each tile
	public static int dispCenterX; // the center of this JPanel relative to the window's top-left corner, in pixels
	public static int dispCenterY;
	public static int dispTileWidth; // the number of blocks needed to fill the screen
	public static int dispTileHeight;
	
	public static Player player;
	public static ArrayList<Entity> entities;
	
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
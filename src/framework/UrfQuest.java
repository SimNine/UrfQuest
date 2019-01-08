package framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import entities.characters.Player;
import entities.items.Item;
import framework.QuestPanel;
import game.QuestGame;
import tiles.Tiles;

// The main class, where everything else is initialized
public class UrfQuest implements Runnable {
    private static final String VERSION = "0.11.1";
    private static final String GAME_NAME = "UrfQuest";
    
	public static UrfQuest quest;
    public static boolean debug;
    public static JFrame frame;
    public static QuestPanel panel;
    public static QuestGame game;
	public static Set<Integer> keys;
	public static int[] mousePos;
	public static boolean mousePressed;
	
    public static Timer time = new Timer(5, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            game.tick();
            panel.repaint();
        }
    });
    
	public void run() {
        System.out.println();
	    System.out.println("---------------------");
	    System.out.println(GAME_NAME + " " + VERSION);
        System.out.println("---------------------");
        System.out.println();
		
		Tiles.initGraphics();
		SoundEngine.initSounds();
		Player.initPlayer();
		Item.initItems();
        
        UrfQuest.debug = false;
        UrfQuest.frame = new JFrame(GAME_NAME + " " + VERSION);
        UrfQuest.panel = new QuestPanel();
        UrfQuest.game = new QuestGame();
        UrfQuest.keys = new HashSet<Integer>(0);
        UrfQuest.mousePos = new int[2];
        UrfQuest.mousePressed = false;
	    
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(700, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		
		frame.add(panel);
		frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                panel.setSize(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
            }
        });
	}

	public static void main(String[] args) {
		quest = new UrfQuest();
		SwingUtilities.invokeLater(quest);
	}
}
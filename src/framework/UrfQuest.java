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

import framework.QuestPanel;
import game.QuestGame;
import tiles.Tiles;

// The main class, where everything else is initialized
public class UrfQuest implements Runnable {
    private static final String VERSION = "0.18.0_pre4";
    private static final String GAME_NAME = "UrfQuest";
    
    // should never need to be accessed
	public static UrfQuest quest;
    public static JFrame frame;
    
    // debugging toggler
    public static boolean debug;
    
    // commonly accessed
    public static QuestPanel panel;
    public static QuestGame game;
	public static Set<Integer> keys;
	public static int[] mousePos;
	public static boolean mouseDown;
	
	// frame properties
	public static boolean isFullscreen;
	
	// tickers
    public static Timer gameTimer = new Timer(5, new ActionListener() { // gameticker
        public void actionPerformed(ActionEvent e) {
        	if (gameRunning) {
                game.tick();
        	}
        }
    });
    public static Timer renderTimer = new Timer(30, new ActionListener() { // renderticker
        public void actionPerformed(ActionEvent e) {
            panel.repaint();
        }
    });
    
    // game properties
    public static boolean gameRunning;
    
	public void run() {
        System.out.println();
	    System.out.println("---------------------");
	    System.out.println(GAME_NAME + " " + VERSION);
        System.out.println("---------------------");
        System.out.println();
		
		Tiles.initGraphics();
        
        UrfQuest.debug = false;
        UrfQuest.game = new QuestGame();
        UrfQuest.keys = new HashSet<Integer>(0);
        UrfQuest.mousePos = new int[2];
        UrfQuest.mouseDown = false;
        UrfQuest.gameRunning = false;

        // the game must be initialized before the display can be initialized
        UrfQuest.panel = new QuestPanel();
        panel.initOverlays();
        resetFrame(true);
        gameTimer.start();
        renderTimer.start();
	}

	public static void main(String[] args) {
		quest = new UrfQuest();
		SwingUtilities.invokeLater(quest);
	}
	
	public static void resetFrame(boolean fullscreen) {
		if (frame != null) {
			frame.dispose();
		}
		
        frame = new JFrame(GAME_NAME + " " + VERSION);

        if (fullscreen) {
    		frame.setResizable(false);
    		frame.setUndecorated(true);
    		isFullscreen = true;
        } else {
            frame.setMinimumSize(new Dimension(700, 600));
    		frame.setResizable(true);
    		frame.setExtendedState(JFrame.NORMAL);
    		frame.setUndecorated(false);
    		isFullscreen = false;
        }
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
}
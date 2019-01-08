package framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

import display.QuestPanel;
import game.QuestGame;

/** Main class that specifies the frame and widgets of the GUI
 */
public class UrfQuest implements Runnable {
    private String version = "0.1.0";
    private String gameName = "UrfQuest";
    
	public void run() {
        System.out.println();
	    System.out.println("---------------------");
	    System.out.println(gameName + " " + version);
        System.out.println("---------------------");
        System.out.println();
	    
		final JFrame frame = new JFrame(gameName + " " + version);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(700, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		
		final QuestGame questGame = new QuestGame();
		
		final QuestPanel questPanel = new QuestPanel(questGame, frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
		frame.add(questPanel);
		frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                questPanel.setSize(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
            }
        });
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new UrfQuest());
	}
}
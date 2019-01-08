package framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

import display.QuestPanel;
import game.QuestGame;
import game.QuestMap;

/** Main class that specifies the frame and widgets of the GUI
 */
public class UrfQuest implements Runnable {
    private String version = "0.4.1";
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
		
		final QuestPanel questPanel = new QuestPanel(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
		V.qPanel = questPanel;
		frame.add(questPanel);
		frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                questPanel.setSize(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
            }
        });
		
		final QuestMap questMap = new QuestMap(500, 500);
		V.qMap = questMap;
		
		final QuestGame questGame = new QuestGame();
		V.qGame = questGame;
		
		V.time.start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new UrfQuest());
	}
}
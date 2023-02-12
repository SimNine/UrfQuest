package xyz.urffer.urfquest.client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfutils.math.PairDouble;
import xyz.urffer.urfutils.math.PairInt;

public class Explosion extends Projectile {

	public Explosion(Client c, int id, int sourceID) {
		super(c, id, sourceID);
		
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
	}

	public void update() {
//		this.incrementPos(new PairDouble(-0.04, -0.04));
//		bounds.setRect(bounds.x, bounds.y, bounds.width + 0.08, bounds.height + 0.08);
//		
//		// clear trees
//		for(int i = 0; i < 20; i++) {
//			int xPos = (int)Math.round(bounds.getCenterX() + bounds.width/2 * Math.cos((Math.PI/10)*i) - 0.5);
//			int yPos = (int)Math.round(bounds.getCenterY() + bounds.width/2 * Math.sin((Math.PI/10)*i) - 0.5);
//			PairInt pos = new PairInt(xPos, yPos);
//			Tile tileAtPos = map.getTileAt(pos);
//			
//			if (tileAtPos.objectType == ObjectType.TREE) {
//				tileAtPos.objectType = ObjectType.VOID;
//			}
//		}
	}

	public boolean isDead() {
		// return (animStage > 100);
		return false;
	}
	
	public void collideWith(Mob m) {
//		m.incrementHealth(-0.15);
	}

	@Override
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.ORANGE);
		g.fillOval(client.getPanel().gameToWindowX(bounds.getX()), 
				   client.getPanel().gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}
	
	public void drawDebug(Graphics g) {
		super.drawDebug(g);
		
		g.setColor(Color.RED);
		PairDouble boundsPos = new PairDouble(bounds.getX(), bounds.getY());
		PairInt boundsPixel = client.getPanel().gameToWindow(boundsPos);
		g.drawOval(boundsPixel.x, 
				   boundsPixel.y,
				   (int)(bounds.getWidth()*QuestPanel.TILE_WIDTH), 
				   (int)(bounds.getHeight()*QuestPanel.TILE_WIDTH));
	}
}
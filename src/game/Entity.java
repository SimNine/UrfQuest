package game;

import java.awt.Color;
import java.awt.Graphics;

import framework.V;

public class Entity {
	private int coordBlockX;
	private int coordBlockY;
	private Color color;
	private double moveStage = 0;
	private int diameter;
	
	private double Xacceleration = 0;
	private double Yacceleration = 0;
	private double Xvelocity = 0;
	private double Yvelocity = 0;
	private double Xcoord;
	private double Ycoord;

	public Entity(double x, double y, Color col, int dia) {
		Xcoord = x;
		Ycoord = y;
		coordBlockX = (int)x;
		coordBlockY = (int)y;
		color = col;
		diameter = dia;
	}
	
	// Drawing methods
	public void draw(Graphics g) {
		drawEntity(g);
		drawPhysics(g);
	}
	
	private void drawEntity(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(V.dispCenterX - (V.gameCenterX - Xcoord)*V.scale*10 - V.scale*diameter*.5),
				   (int)(V.dispCenterY - (V.gameCenterY - Ycoord)*V.scale*10 - V.scale*diameter*.5), V.scale*diameter, V.scale*diameter);
	}
	
	private void drawPhysics(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("["+Xcoord+","+Ycoord+"]",
					(int)(V.dispCenterX - (V.gameCenterX - Xcoord)*V.scale*10 - V.scale*diameter*.5),
					(int)(V.dispCenterY - (V.gameCenterY - Ycoord)*V.scale*10 + 10 - V.scale*diameter*.5));
		g.drawString("["+Xvelocity+","+Yvelocity+"]",
					(int)(V.dispCenterX - (V.gameCenterX - Xcoord)*V.scale*10 - V.scale*diameter*.5),
					(int)(V.dispCenterY - (V.gameCenterY - Ycoord)*V.scale*10 + 20 - V.scale*diameter*.5));
		g.drawString("["+Xacceleration+","+Yacceleration+"]",
					(int)(V.dispCenterX - (V.gameCenterX - Xcoord)*V.scale*10 - V.scale*diameter*.5),
					(int)(V.dispCenterY - (V.gameCenterY - Ycoord)*V.scale*10 + 30 - V.scale*diameter*.5));
	}
	
	// Updating methods
	public void update() {
		physicsUpdate();
	}
	
	private void physicsUpdate() {
		if (Xcoord > V.gameCenterX) {
			Xacceleration -= 0.00001;
		} else if (Xcoord < V.gameCenterX) {
			Xacceleration += 0.00001;
		}
		if (Ycoord > V.gameCenterY) {
			Yacceleration -= 0.00001;
		} else if (Ycoord < V.gameCenterY) {
			Yacceleration += 0.00001;
		}
		
		Xvelocity += Xacceleration;
		Yvelocity += Yacceleration;
		
		Xcoord += Xvelocity;
		Ycoord += Yvelocity;

		Xacceleration += -(Xacceleration/500);
		Yacceleration += -(Yacceleration/500);
		Xvelocity += -(Xvelocity/10);
		Yvelocity += -(Yvelocity/10);
	}
}
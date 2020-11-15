package Mole.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
	
	private double x;
	private double y; 
	
	private BufferedImage player;
	
	public Player(double x, double y, Game game) {
		this.x = x;
		this.y = y;
		
		SpriteSheet spr1 = new SpriteSheet(game.getSpriteSheet());
		
		player = spr1.grabHumImage(1, 1, 50, 64);
	}
	
	public void tick() { // �޼ҵ带 ������Ʈ �Ҷ� ���
		
	}
	
	public void render(Graphics g) { // �̹��� �׸��� ���
		g.drawImage(player, (int)x, (int)y, null);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
}

package Mole.Game;

import java.awt.Graphics;
import java.util.LinkedList;

public class Controller {

	private LinkedList<Bullet> b = new LinkedList<>();
	
	Bullet tempBullet;
	
	
	Game game;
	
	public Controller(Game game) {
		this.game = game;

	}
	
	public void tick() {
		for(int i =0; i < b.size(); i++) {
			tempBullet = b.get(i);
			if(game.buldirection == true && (tempBullet.getX() - game.getPlayer().getX()) > 150) // �Ѿ��� �����Ÿ� 150�� �Ѿ�� ���쵵�� ����
				removeBullet(tempBullet);
			else if(game.buldirection == false && Math.abs(game.getPlayer().getX() - tempBullet.getX()) > 100)
				removeBullet(tempBullet);
			tempBullet.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i<b.size(); i++) {
			tempBullet = b.get(i);
			
			tempBullet.render(g);
		}
	}
	
	public void addBullet(Bullet block) {
		b.add(block);
	}
	public void removeBullet(Bullet block) {
		b.remove(block);
	}
}

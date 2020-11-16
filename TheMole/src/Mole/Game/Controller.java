package Mole.Game;

import java.awt.Graphics;
import java.util.LinkedList;

public class Controller { // ����Ʈ�� �ʿ��Ѱ͵��� ���� �Ѿ�,�����۰���?

	private LinkedList<Bullet> b = new LinkedList<>();
	private LinkedList<Mole> m = new LinkedList<>();
	
	Bullet tempBullet;
	Mole tempMole;
	
	Game game;
	Textures texture;
	
	public Controller(Game game, Textures texture) {
		this.game = game;
		this.texture = texture;
		
		for(int i = 0; i < 3; i++)
			for(int j=0; j<3; j++)
				addMole(new Mole(150 + (j*70), 350+(i*70), false ,texture));
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
		for(int i =0; i < m.size(); i++) {
			tempMole = m.get(i);
			
			tempMole.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i<b.size(); i++) {
			tempBullet = b.get(i);
			
			tempBullet.render(g);
		}
		for(int i = 0; i<m.size(); i++) {
			tempMole = m.get(i);
			
			tempMole.render(g);
		}
	}
	
	public void addBullet(Bullet block) {
		b.add(block);
	}
	public void removeBullet(Bullet block) {
		b.remove(block);
	}
	public void addMole(Mole block) {
		m.add(block);
	}
	public void removeMole(Mole block) {
		m.remove(block);
	}
}

package Mole.Game;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Controller { // ����Ʈ�� �ʿ��Ѱ͵��� ���� �Ѿ�,�����۰���?

	private LinkedList<Entity> e = new LinkedList<>();
	private LinkedList<Bullet> b = new LinkedList<>();
	private LinkedList<Mole> m = new LinkedList<>();
	
	Entity ent;
	Bullet tempBullet;
	Mole tempMole;
	
	Game game;
	Textures texture;
	
	Random r = new Random();
	public Controller(Game game, Textures texture) {
		this.game = game;
		this.texture = texture;
		
		for(int i = 0; i < 3; i++)
			for(int j=0; j<3; j++)
				addMoleEnt(new Mole(100+r.nextInt(Game.WIDTH*Game.SCALE-100), 350+r.nextInt(Game.HEIGHT*Game.SCALE-350), false ,texture));
	}
	
	public void tick() {
		for(int i= 0; i < e.size(); i++) {
			ent = e.get(i);
			ent.tick();
		}
		for(int i =0; i < b.size(); i++) {
			tempBullet = b.get(i);
			if(game.buldirection == true && (tempBullet.getX() - game.getPlayer().getX()) > 150) // �Ѿ��� �����Ÿ� 150�� �Ѿ�� ���쵵�� ����
				removeBulletEnt(tempBullet);
			else if(game.buldirection == false && Math.abs(game.getPlayer().getX() - tempBullet.getX()) > 100)
				removeBulletEnt(tempBullet);
			tempBullet.tick();
		}
		for(int i =0; i < m.size(); i++) {
			tempMole = m.get(i);
			
			tempMole.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i= 0; i < e.size(); i++) {
			ent = e.get(i);
			ent.render(g);
		}
		for(int i = 0; i<b.size(); i++) {
			tempBullet = b.get(i);
			
			tempBullet.render(g);
		}
		for(int i = 0; i<m.size(); i++) {
			tempMole = m.get(i);
			
			tempMole.render(g);
		}
	}
	
	public void addEntity(Entity block) {
		e.add(block);
	}
	public void removeEntity(Entity block) {
		e.remove(block);
	}
	public void addBulletEnt(Bullet block) {
		b.add(block);
	}
	public void removeBulletEnt(Bullet block) {
		b.remove(block);
	}
	public void addMoleEnt(Mole block) {
		m.add(block);
	}
	public void removeMoleEnt(Mole block) {
		m.remove(block);
	}
}

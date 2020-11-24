package Mole.Game;

import java.awt.Graphics;
import java.awt.Rectangle;

import Mole.Game.Entities.EntityB;
import Mole.Game.libs.Animation;

public class Mole implements EntityB {

	private double x, y;
	
	private double velX = 0;
	private double velY = 1;
	private boolean selected; // �ش� �δ����� ���õǾ����� true�� ��ȯ, �⺻ ������ false��
	
	public static int mole_count = 9; // �δ��� ��������

	private Textures texture;
	private Game game;
	private Animation anim;
	private Controller controller;
	private Graphics g;

	public Mole(double x, double y, boolean selected, Textures texture, Controller c, Game game) {
		this.x = x;
		this.y = y;
		this.selected = selected;
		this.texture = texture;
		this.game = game;
		this.controller = c;
		
		anim = new Animation(10,texture.mole[0],texture.mole[1],texture.mole[2]);
	}

	public void tick() {
		x += velX;
		y -= velY;
		//y += velY;
		
		if(x <= 20)
			x = 20;
		if(x >= 780 - 32)
			x = 780 - 32;
		if(y <= 250) // �۹���(�ʷϺκ�)���� ������ ������ ���
			y = 250 - 1;
		if(y >= 580 - 32)
			y = 580 - 32;
		
		anim.runAnimation();
	}

	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
		//g.drawImage(texture.mole[0], (int) x, (int) y, null); // x�� y�� double�̱⿡ (int)�� ��ȯ ���־�� �Ѵ�.
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return x;
	}
	public void getY(double y) {
		this.y = y;
	}

	public int getMole_count() {
		return mole_count;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
	
	public void dispose(Graphics g) {
		g.dispose();
	}
}

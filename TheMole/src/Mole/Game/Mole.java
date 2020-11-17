package Mole.Game;

import java.awt.Graphics;

public class Mole implements Entity {

	private double x, y;
	
	private double velX = 0;
	private double velY = 0;
	private boolean selected; // �ش� �δ����� ���õǾ����� true�� ��ȯ, �⺻ ������ false��

	private Textures texture;

	public Mole(double x, double y, boolean selected, Textures texture) {
		this.x = x;
		this.y = y;
		this.selected = selected;
		this.texture = texture;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(x <= 20)
			x = 20;
		if(x >= 780 - 32)
			x = 780 - 32;
		if(y <= 250) // �۹���(�ʷϺκ�)���� ������ ������ ���
			y = 250 - 1;
		if(y >= 580 - 32)
			y = 580 - 32;
	}

	public void render(Graphics g) {
		g.drawImage(texture.mole, (int) x, (int) y, null); // x�� y�� double�̱⿡ (int)�� ��ȯ ���־�� �Ѵ�.
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
}

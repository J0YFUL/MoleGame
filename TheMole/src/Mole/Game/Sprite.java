package Mole.Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

	protected int x;
	protected int y;
	protected int dx;
	protected int dy;
	private Image image;

	// ������
	public Sprite(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}

	// ��������Ʈ�� ���� ���̸� ��ȯ
	public int getWidth() {
		return image.getWidth(null);
	}

	// ��������Ʈ�� ���� ���̸� ��ȯ
	public int getHeight() {
		return image.getHeight(null);
	}

	// ��������Ʈ�� ȭ�鿡 �׸���.
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	// ��������Ʈ�� �����δ�.
	public void move() {
		x += dx;
		y += dy;
	}

	// dx�� ����
	public void setDx(int dx) {
		this.dx = dx;
	}

	// dy�� ����
	public void setDy(int dy) {
		this.dy = dy;
	}

	// dx�� ��ȯ
	public int getDx() {
		return dx;
	}

	// dy�� ��ȯ
	public int getDy() {
		return dy;
	}

	// x�� ��ȯ
	public int getX() {
		return x;
	}

	// y�� ��ȯ
	public int getY() {
		return y;
	}

	// �ٸ� ��������Ʈ���� �浹 ���θ� ���, �浹�̸� true ��ȯ
	public boolean checkCollision(Sprite other) {
		Rectangle myRect = new Rectangle();
		Rectangle otherRect = new Rectangle();
		myRect.setBounds(x, y, getWidth(), getHeight());
		otherRect.setBounds(other.getX(), other.getY(), other.getWidth(), other.getHeight());

		return myRect.intersects(otherRect);
	}
	
	// �浹�� ó���Ѵ�.
	public void handleCollision(Sprite other) {
		
	}
}

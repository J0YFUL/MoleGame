package Mole.Game;

import java.awt.Image;

public class Human extends Sprite { // AlienSprite Ŭ����

	private Game game;

	public Human(Game game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dx = 0;
		dy = 0;
		// dx = -3; // �ʱ⿡�� �������� �̵��Ѵ�.
	}

	@Override
	public void move() {
		if(((dx<0) && (x<10))) {
			return;
		}
		if((dx>0) && (x>800)) {
			return;
		}
		super.move();
	}

	@Override
	public void handleCollision(Sprite other) {
		// TODO Auto-generated method stub
		super.handleCollision(other);
	}

}

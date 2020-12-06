package Mole.Game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Snake extends JLabel {

	private int x;
	private int y;
	private int status; // �����ɶ� ���� ���ϰ�, ���� ���� ���� ������ �޶�����.

	MoleUI molePanel;
	
	private Timer move;
	private int snakeTempo = 0;
	private boolean moving = true;

	// 4���� ��������Ʈ �̹����� ����
	private ImageIcon snake[] = { new ImageIcon("img/snakeResource/snakeL1.png"),
			new ImageIcon("img/snakeResource/snakeL2.png"), new ImageIcon("img/snakeResource/snakeR1.png"),
			new ImageIcon("img/snakeResource/snakeR2.png") };

	public Snake(MoleUI molePanel) {
		this.y = 250;
		this.molePanel = molePanel;

		status = (int) (Math.random() * 2);
		if (status == 0) // �������� ���� �̵��ϴ� ��
			this.x = 760;
		else // ���������� ���� �̵��ϴ� ��
			this.x = 40;

		setBounds(x, y, 32, 32);
		if (status == 0) // ���� ��������Ʈ
			setIcon(snake[0]);
		else
			setIcon(snake[2]); // ������ ��������Ʈ
		moveStart();
	}
	public int getStatus() {
		return status;
	}
	public boolean getMoving () {
		return moving;
	}
	
	public void moveStart() {
		move();
		move.start();
	}
	public void snakeDie() {
		setVisible(false);
		move.stop();
		moving = false;
	}

	public void move() {
		move = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				snakeTempo++;
				snakeTempo = snakeTempo % 2;
				if (status == 0) { // ���� �������� ������
					moveX(-5);
					if (snakeTempo == 0)
						setIcon(snake[0]);
					if (snakeTempo == 1)
						setIcon(snake[1]);
					setBounds(getX(),y,32,32);	
				} else if (status == 1) { // ���� �������� ������
					moveX(5);
					if (snakeTempo == 0)
						setIcon(snake[2]);
					if (snakeTempo == 1)
						setIcon(snake[3]);
					setBounds(getX(),y,32,32);	
				}
				if(molePanel.getMoleInHumanPerformance().getX() == getX()) {
					molePanel.getMoleInHumanPerformance().minushumanlife(1);
					snakeDie();
				}
			}
		});
	}
//	public void collisionCheck(JLabel human) {
//		if(this.getBounds().intersects(human.getBounds()))
//			this.setVisible(false);
//	}

	public void moveX(int x) {
		this.x = this.x + x;
		//collisionCheck(human);
		if (status == 0) {
			if (this.x < 20)
				this.setVisible(false);
		} else {
			if(this.x > 780)
				this.setVisible(false);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
}
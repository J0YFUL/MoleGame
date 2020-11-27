package Mole.Game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Human extends JLabel{

	private static double x = 0;
	private static double y = 0;
	private double velX = 0;
	private double velY = 0;
	private static int status = 0; // 1�� ������, 2�� ����,

	private boolean shooting = false;

	private Timer mover;
	private boolean moving = false;

	private ImageIcon human[] = { new ImageIcon("img/humanResource/human1.png"),
			new ImageIcon("img/humanResource/human2.png"), new ImageIcon("img/humanResource/human3.png"),
			new ImageIcon("img/humanResource/human4.png"), new ImageIcon("img/humanResource/human5.png"),
			new ImageIcon("img/humanResource/human6.png"), new ImageIcon("img/humanResource/human7.png"),
			new ImageIcon("img/humanResource/human8.png"), new ImageIcon("img/humanResource/human9.png"),
			new ImageIcon("img/humanResource/human10.png"), };

	public Human(int x, int y) {
		this.x = x;
		this.y = y;
		setBounds((int) x, (int) y, 50, 64);
		setIcon(human[0]);
		addKeyListener(new KeyListener() {

			double x = this.x;

			public void keyPressed(KeyEvent e) {
				
				if (e.getKeyChar() == KeyEvent.VK_LEFT) { // ���� ����Ű
					moving = true;
					status = 2;
					System.out.println("����");
					//mover.start();
					setVelX(-3);
					x -= velX;
				}
				if (e.getKeyChar() == KeyEvent.VK_RIGHT) { // ������ ����Ű
					moving = true;
					status = 1;
					System.out.println("������");
					//mover.start();
					setVelX(3);
					x += velX;
				}
				if (e.getKeyChar() == KeyEvent.VK_A && shooting == false) {
					shooting = true;
					System.out.println("���� �Ѿ�");
				}
				if (e.getKeyChar() == KeyEvent.VK_D && shooting == false) {
					shooting = true;
					System.out.println("������ �Ѿ�");
				}
			}

			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (e.getKeyChar() == KeyEvent.VK_LEFT) { // ���� ����Ű
					setVelX(0);
					moving = false;
					setIcon(human[5]);
				} else if (e.getKeyChar() == KeyEvent.VK_RIGHT) { // ������ ����Ű
					setVelX(0);
					moving = false;
					setIcon(human[0]);
				} else if (e.getKeyChar() == KeyEvent.VK_A) {
					shooting = false;
				} else if (e.getKeyChar() == KeyEvent.VK_D) {
					shooting = false;
				}
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});
	}

	public void timer() {
		mover = new Timer(3000,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(moving == true && status == 1) { // �����ʹ������� �����϶� -����
					for(int i= 1; i<=4; i++)
						setIcon(human[i]);
				}
				if(moving == true && status == 2) { // ���ʹ������� �����϶� -����
					for(int i= 6; i<=9; i++)
						setIcon(human[i]);
				}
				
				mover.stop();
			}
		});
	}

	public int getX() {
		return (int)x;
	}

	public int getY() {
		return (int)y;
	}

	public double getVelX() {
		return velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public int getStatus() {
		return status;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 50, 64);
	}

}
package Mole.Game;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

import io.netty.channel.ChannelHandlerContext;

public class HumanInMole extends JButton {
	private Rectangle champion;
	
	private Timer timer;
	private double speed = 0.15;
	private Long startTime;
	
	 private double targetX, targetY;
     private double startX, startY;
     private double runTime;
     
     private int x, y;
     private int direction = 0;
     private int molesecond = 0;
     
	private ImageIcon mole[] = { new ImageIcon("img/moleResource/moleL1.png"),
			new ImageIcon("img/moleResource/moleL2.png"), new ImageIcon("img/moleResource/moleL3.png"),
			new ImageIcon("img/moleResource/moleR1.png"),new ImageIcon("img/moleResource/moleR2.png"),
			new ImageIcon("img/moleResource/moleR3.png"),new ImageIcon("img/moleResource/moleS.png") };

	
	public HumanInMole(int x, int y) {
		this.x = x;
		this.y = y;
		champion = new Rectangle(x, y, 32, 32);
		
		setIcon(mole[6]);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBounds(x, y, 32, 32);
		setVisible(false);
	}
	public Timer getTimer() {
		return timer;
	}

	
	public void humanInMoleMove(String message, int x, int y) {
		timer = new Timer(30, e -> {
			molesecond++;
			molesecond = molesecond % 4;
			if (direction == 1) { // 오른쪽방향으로 움직일때 -누름
				if (molesecond == 1)
					setIcon(mole[3]);
				else if (molesecond == 2)
					setIcon(mole[4]);
				else if (molesecond == 3)
					setIcon(mole[5]);
			}
			if (direction == 2) { // 왼쪽방향으로 움직일때 -누름
				if (molesecond == 1)
					setIcon(mole[0]);
				else if (molesecond == 2)
					setIcon(mole[1]);
				else if (molesecond == 3)
					setIcon(mole[2]);
			}
			try {
				TimeMove();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			//timer.stop();
	});
		
			setIcon(mole[5]);
			timer.stop();
			calculateChampionMovement(x, y, champion);
			startTime = System.currentTimeMillis();
			timer.start();
	
	}
	
	public void TimeMove() throws InterruptedException {
		long duration = System.currentTimeMillis() - startTime;
		double progress = duration / runTime;

		if (progress >= 1.0) {
			progress = 1.0;
			timer.stop();
			setIcon(mole[6]);
		}

		double x = (int) (startX + ((targetX - startX) * progress));
		double y = (int) (startY + ((targetY - startY) * progress));

		repaint();
		if (y >= 270 && x >= 12 && x <= 770) {
			setBounds((int) x - 15, (int) y - 15, 30, 30);
			champion.setRect(x - 5, y - 5, 10, 10);
		}
		if (y < 300)
			setVisible(true);
		else if (y > 300)
			setVisible(false);
	}
	
	public void calculateChampionMovement(double x, double y, Rectangle champion) {

		if (x != champion.getCenterX() || y != champion.getCenterY()) {

			targetX = x;
			targetY = y;

			System.out.println(targetX + "     " + targetY);
			startX = champion.getCenterX();
			startY = champion.getCenterY();

			
			if (targetX - startX > 0) {
                direction = 1;
            } else if (targetX - startX < 0) {
                direction = 2;
            }
			 

			double distance = Math
					.sqrt((startX - targetX) * (startX - targetX) + (startY - targetY) * (startY - targetY));

			runTime = distance / (double) speed;
//			if ((x <= v0.getx() + 10 && x > v0.getx() - 10) || (x <= v1.getx() + 10 && x > v1.getx() - 10)
//					|| (x <= v2.getx() + 10 && x > v2.getx() - 10) && y < 290) {
//				eat = true;
//			}
		}
	}
}

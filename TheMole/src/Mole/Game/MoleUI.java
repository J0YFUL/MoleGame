package Mole.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class MoleUII extends JFrame {
	private MolePanel molePanel;

	public MoleUII() throws IOException, InterruptedException { // Mole UI â
		setTitle("Mole Game");
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		molePanel = new MolePanel(); // molePanel ����

		add(molePanel);
		setVisible(true);
	}
}

class MolePanel extends JPanel {
	private BufferedImage backImage;
	private ImageIcon human = new ImageIcon("img/human.png");
	private ImageIcon chicken = new ImageIcon("img/chicken.gif");
	private JButton humanButton, ch;
	MoleThread m1;
	MoleThread m2;
	MoleThread m3;
	MoleThread m4;
	MoleThread m5;
	MoleThread m6;
	MoleThread m7;
	MoleThread m8;
	MoleThread m9;
	vegetableThread v0;
	vegetableThread v1;
	vegetableThread v2;

	public MolePanel() {
		try {
			setLayout(null);
			backImage = ImageIO.read(new File("img/Back4.png"));

			humanButton = new JButton(human);
			humanButton.setBorderPainted(false);
			humanButton.setFocusPainted(false);
			humanButton.setContentAreaFilled(false);
			humanButton.setBounds(300, 192, 70, 70);
			add(humanButton);

			ch = new JButton(chicken);
			ch.setBorderPainted(false);
			ch.setFocusPainted(false);
			ch.setContentAreaFilled(false);
			ch.setBounds(0, 0, 50, 50);
			add(ch);
			// ch.setVisible(false);

			m1 = new MoleThread(50, 400);
			m2 = new MoleThread(100, 400);
			m3 = new MoleThread(150, 400);
			m4 = new MoleThread(50, 450);
			m5 = new MoleThread(100, 450);
			m6 = new MoleThread(150, 450);
			m7 = new MoleThread(50, 500);
			m8 = new MoleThread(100, 500);
			m9 = new MoleThread(150, 500);
			v0 = new vegetableThread(0);
			v1 = new vegetableThread(1);
			v2 = new vegetableThread(2);

			
//			v0.setVisible(false);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	class vegetableThread extends Thread implements Runnable {
		private ImageIcon veget = new ImageIcon("img/vegetables.png");
		JLabel vegetable = new JLabel(veget);
		int x, y,section;

		public vegetableThread(int section) {
			this.section = section;
			x = ((int) (Math.random() * 260)) + 263 * this.section;
			y = 260;
			vegetable.setBounds(x, y, 16, 16);
			// vegetable.setIcon(veget);
			add(vegetable);
			System.out.println("�۹���ġ " + x + " " + y);
		}

		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		
		
		public void setposition() {
			x = ((int) (Math.random() * 260)) + 263 * this.section;
			y = 260;
			vegetable.setBounds(x, y, 16, 16);
			 
		}

		@Override
		public void run() {
			try {
				this.sleep(000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void setVisible(boolean b) {
			if (b == true)
				vegetable.setVisible(true);
			else if(b == false)
				vegetable.setVisible(false);
		}
	}

	class MoleThread extends Thread {
		private int x, y;
		private JButton moleButton;
		private Rectangle champion;
		private ImageIcon mole = new ImageIcon("img/moleimg.png");
		private ImageIcon moleSelect = new ImageIcon("img/moleselect.png");
		Thread t1 = new Thread(v0);


		private Timer timer;
		private double speed = 0.15;
		private Long startTime;

		private double targetX, targetY;
		private double startX, startY;
		private double runTime;

		public int getx() {
			return x;
		}

		public int gety() {
			return y;
		}

		public MoleThread(int x, int y) {
			this.x = x;
			this.y = y;

			champion = new Rectangle(x, y, 10, 10);

			moleButton = new JButton(mole);
			moleButton.setBorderPainted(false);
			moleButton.setFocusPainted(false);
			moleButton.setContentAreaFilled(false);
			moleButton.setBounds(x, y, 30, 30);
			add(moleButton);

			moleButton.addActionListener(e -> {
				if (e.getSource() == moleButton) {
					moleButton.setIcon(moleSelect);
					addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							if (e.getButton() == MouseEvent.BUTTON3) {
								if (moleButton.getIcon().equals(moleSelect)) {
									timer.stop();
									calculateChampionMovement(e.getX(), e.getY(), champion);
									startTime = System.currentTimeMillis();
									timer.start();
								}
							}

							if (e.getButton() == MouseEvent.BUTTON1) {
								if (moleButton.getIcon().equals(moleSelect))
									moleButton.setIcon(mole);
							}
						}
					});
				}

			});
			timer = new Timer(10, e -> {
				TimeMove();
			});

		}

		public void run() {
			

		}

		public void TimeMove() {
			long duration = System.currentTimeMillis() - startTime;
			double progress = duration / runTime;

			if (progress >= 1.0) {
				progress = 1.0;
				timer.stop();
			}

			double x = (int) (startX + ((targetX - startX) * progress));
			double y = (int) (startY + ((targetY - startY) * progress));
			
			repaint();
			if (y >= 270 && x >= 12 && x <= 770) {
				moleButton.setBounds((int) x - 15, (int) y - 15, 30, 30);
				champion.setRect(x - 5, y - 5, 10, 10);
			}
			if (v0.getX() == x && v0.getY() >= y - 15) {
				v0.setVisible(false);
				try {
					t1.sleep(5000);
					v0.setVisible(true);
					System.out.println("1");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (v1.getX() == x && v1.getY() >= y - 15) {
				v1.setVisible(false);
				v1.setposition();
				v1.setVisible(true);
			} else if (v2.getX() == x && v2.getY() >= y - 15) {
				v2.setVisible(false);
				v2.setposition();
				v2.setVisible(true);
			}

		}

		public void calculateChampionMovement(double x, double y, Rectangle champion) {

			if (x != champion.getCenterX() || y != champion.getCenterY()) {

				targetX = x;
				targetY = y;

				startX = champion.getCenterX();
				startY = champion.getCenterY();
				double distance = Math
						.sqrt((startX - targetX) * (startX - targetX) + (startY - targetY) * (startY - targetY));

				runTime = distance / (double) speed;
//				if ((x <= v0.getx() + 10 && x > v0.getx() - 10) || (x <= v1.getx() + 10 && x > v1.getx() - 10)
//						|| (x <= v2.getx() + 10 && x > v2.getx() - 10) && y < 290) {
//					eat = true;
//				}
			}
		}
	}

	public void paintComponent(Graphics g) {// �׸��� �Լ�
		super.paintComponent(g);
		g.drawImage(backImage, 0, 0, null);
	}
}

public class MoleUI {
	public static void main(String[] args) throws IOException, InterruptedException {
		new MoleUII();
	}
}
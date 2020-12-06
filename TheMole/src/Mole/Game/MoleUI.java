package Mole.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Mole.Game.Sound.SoundJLayer;
import javazoom.jl.decoder.JavaLayerException;

public class MoleUI extends JFrame {
	private MolePanel molePanel;

	public MoleUI() throws IOException, InterruptedException { // Mole UI â
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
	private BufferedImage backImage, humanHud, moleHud, humanInv, moleInv, intHuman, intMole;

	private JLabel counterLabel;
	private JLabel vegcountLabel;
	private JLabel bulletLabel;
	private JLabel lifeLabel;
	private JLabel moleCountLabel;
	private Font font1 = new Font("Arial", Font.BOLD, 30);
	private Font font2 = new Font("Arial", Font.BOLD, 15);
	public int snakesecond = 15;
	
	public MoleUI frame;
	
	Timer timer;

	SoundJLayer soundToPlay = new SoundJLayer("res/ingameBG_Lisport.mp3");
	
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

	private ImageIcon itemteeth = new ImageIcon("img/itemResource/strongteeth.png");
	private ImageIcon itemtrap = new ImageIcon("img/itemResource/trapM.png");
	private ImageIcon itemsnakepipe = new ImageIcon("img/itemResource/Snakepipe.png");
	private JLabel itembox1;
	private JLabel itembox2;
	private JLabel humitembox1;
	private JLabel humitembox2;
	itemBoxThread i0;
	itemBoxThread i1;
	itemBoxThread i2;
	itemBoxThread i3;
	
	Snake snake;
	
	Human hum;
	private boolean humtrap = false;
	private Timer snakeTimer;
	private Timer humstoptimer;
	private int humstun = 3;
	public boolean humstop = false;
	public boolean musicStatus = true;
	public boolean isSnake = false;

	int second, minute;
	String ddSecond, ddMinute;
	DecimalFormat dFormat = new DecimalFormat("00");
	
	public MolePanel() {
		
		// this.setFocusable(true); // Ű ������

		try {
			setLayout(null);
			backImage = ImageIO.read(new File("img/Back4.png"));
			humanHud = ImageIO.read(new File("img/humanHud.png"));
			moleHud = ImageIO.read(new File("img/moleHud.png"));
			humanInv = ImageIO.read(new File("img/inventory.png"));
			moleInv = ImageIO.read(new File("img/inventory.png"));
			intHuman = ImageIO.read(new File("img/humanint.png"));
			intMole = ImageIO.read(new File("img/moleint.png"));
		
			m1 = new MoleThread(50, 400, this);
			m2 = new MoleThread(100, 400, this);
			m3 = new MoleThread(150, 400, this);
			m4 = new MoleThread(50, 450, this);
			m5 = new MoleThread(100, 450, this);
			m6 = new MoleThread(150, 450, this);
			m7 = new MoleThread(50, 500, this);
			m8 = new MoleThread(100, 500, this);
			m9 = new MoleThread(150, 500, this);
			
			v0 = new vegetableThread(0);
			v1 = new vegetableThread(1);
			v2 = new vegetableThread(2);
			
			i0 = new itemBoxThread(0);
			i1 = new itemBoxThread(1);
			
			hum = new Human(this, 225);	
			add(hum);
			
			add(i0);
			add(i1);
			add(v0);
			add(v1);
			add(v2);

			counterLabel = new JLabel("");
			counterLabel.setBounds(345, 0, 100, 50);
			counterLabel.setHorizontalAlignment(JLabel.CENTER);
			counterLabel.setFont(font1);

			add(counterLabel);
	
			counterLabel.setText("03:00");
			second = 0;
			minute = 3;
			normalTimer();
			timer.start();
	
			vegcountLabel = new JLabel("15");
			vegcountLabel.setBounds(758, 90, 20, 20);
			vegcountLabel.setHorizontalAlignment(JLabel.CENTER);
			vegcountLabel.setFont(font2);
			
			bulletLabel = new JLabel("5");
			bulletLabel.setBounds(21, 90, 20, 20);
			bulletLabel.setHorizontalAlignment(JLabel.CENTER);
			bulletLabel.setFont(font2);
			
			lifeLabel = new JLabel("2");
			lifeLabel.setBounds(21, 70, 20, 20);
			lifeLabel.setHorizontalAlignment(JLabel.CENTER);
			lifeLabel.setFont(font2);
			
			moleCountLabel = new JLabel("9");
			moleCountLabel.setBounds(758, 70, 20, 20);
			moleCountLabel.setHorizontalAlignment(JLabel.CENTER);
			moleCountLabel.setFont(font2);
	
			add(vegcountLabel);
			add(bulletLabel);
			add(lifeLabel);
			add(moleCountLabel);
	
			itembox1 = new JLabel();
			itembox2 = new JLabel();
			humitembox1 = new JLabel();
			humitembox2 = new JLabel();
			itembox1.setBounds(655,5,38,38);
			itembox2.setBounds(696,5,38,38);
			humitembox1.setBounds(655,5,38,38);
			humitembox2.setBounds(696,5,38,38);
			itembox1.setVisible(false);
			add(itembox1);
			add(itembox2);
			add(humitembox1);
			add(humitembox2);
			
			soundToPlay.play();
			
			//�����÷��� �� 'm'Ű�� ������ ���Ұ�, �ٽ� ������ �ٽ� ����
			addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_M) {
						if(musicStatus == true) {
							try {
								soundToPlay.pause();
								musicStatus = false;
							} catch (JavaLayerException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else {
							soundToPlay.play();
							musicStatus = true;
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sethumtrap(boolean a) {
		humtrap = a;
	}
	
	public void makeSnake() {
		snake = new Snake(this);
		add(snake);
	}
	
	public boolean gethumtrap() {
		return humtrap;
	}

	// �������̽� �׸��� ��ġ
	public void paintComponent(Graphics g) {// �׸��� �Լ�
		super.paintComponent(g); // ���⼭ super�� �гο� �׸��� �׸��°�
		g.drawImage(backImage, 0, 0, null);
		g.drawImage(humanHud, 0, 70, null);
		g.drawImage(moleHud, 715, 70, null);
		g.drawImage(humanInv, 55, 0, null);
		g.drawImage(moleInv, 650, 0, null);
		g.drawImage(intHuman, 0, 0, null);
		g.drawImage(intMole, 740, 0, null);
	}

	public void normalTimer() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				second--;

				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);

				counterLabel.setText(ddMinute + ":" + ddSecond);
				vegcountLabel.setText(15 - (v0.getvegcount() + v1.getvegcount() + v2.getvegcount()) + "");
				bulletLabel.setText(hum.getbcount() + "");
				lifeLabel.setText(hum.gethumanlife() + "");
				//System.out.println(m1.getmoleLife() +"" + m2.getmoleLife() +""+ m3.getmoleLife());
				moleCountLabel.setText((m1.getmoleLife()) + (m2.getmoleLife()) + (m3.getmoleLife())
												+ (m4.getmoleLife()) + (m5.getmoleLife()) + (m6.getmoleLife())
												+ (m7.getmoleLife()) + (m8.getmoleLife()) + (m9.getmoleLife()) + "");
				
				if (second == -1) {
					second = 59;
					minute--;

					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);
					counterLabel.setText(ddMinute + ":" + ddSecond);
				}
				if (minute == 0 && second == 0 || moleCountLabel.getText().equals("0")) {
					timer.stop();
					try {
						try {
							soundToPlay.pause();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (JavaLayerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "�ΰ� �¸�!!", "Result", JOptionPane.PLAIN_MESSAGE);
					MainFrame main = new MainFrame();
					main.setVisible(true);
				}
				if (vegcountLabel.getText().equals("0") || lifeLabel.getText().equals("0")) {
					timer.stop();
					try {
						try {
							soundToPlay.pause();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (JavaLayerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "�δ��� �¸�!!", "Result", JOptionPane.PLAIN_MESSAGE);
					MainFrame main = new MainFrame();
					main.setVisible(true);
				}
			}
		});
	}

	class MoleThread {
		private int x, y;
		private int direction = 0;
		public int molesecond = 0;
		private JButton moleButton;
		private Rectangle champion;
		private boolean life = true;
		private MolePanel n;
		private int molecount = 1;
		
		// mole ��������Ʈ 14��
		private ImageIcon mole[] = { new ImageIcon("img/moleResource/moleL1.png"),
				new ImageIcon("img/moleResource/moleL2.png"), new ImageIcon("img/moleResource/moleL3.png"),
				new ImageIcon("img/moleResource/moleLS1.png"), new ImageIcon("img/moleResource/moleLS2.png"),
				new ImageIcon("img/moleResource/moleLS3.png"), new ImageIcon("img/moleResource/moleR1.png"),
				new ImageIcon("img/moleResource/moleR2.png"), new ImageIcon("img/moleResource/moleR3.png"),
				new ImageIcon("img/moleResource/moleRS1.png"), new ImageIcon("img/moleResource/moleRS2.png"),
				new ImageIcon("img/moleResource/moleRS3.png"), new ImageIcon("img/moleResource/moleS.png"),
				new ImageIcon("img/moleResource/moleSS.png") };

		private Timer timer;
		private double speed = 0.15;
		private Long startTime;

		private Timer eattimer;
		private int eatsecond;
		private boolean eating = false;

		private double targetX, targetY;
		private double startX, startY;
		private double runTime;

		private boolean enhenceteeth = false;

		public int getx() {
			return x;
		}

		public int gety() {
			return y;
		}

		public MoleThread(int x, int y, MolePanel m) {
			this.x = x;
			this.y = y;
			n = m;

			champion = new Rectangle(x, y, 32, 32);

			moleButton = new JButton(mole[12]);
			moleButton.setBorderPainted(false);
			moleButton.setFocusPainted(false);
			moleButton.setContentAreaFilled(false);
			moleButton.setBounds(x, y, 32, 32);
			add(moleButton);

			moleButton.addActionListener(e -> {
				if (e.getSource() == moleButton && eating == false) {
					moleButton.setIcon(mole[13]);
					addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							if (e.getButton() == MouseEvent.BUTTON3) {
								if (eating == true) // �Ծ��� �� �����ϰ� ����
									moleButton.setIcon(mole[12]);
								if (moleButton.getIcon().equals(mole[13]) || moleButton.getIcon().equals(mole[11])
										|| moleButton.getIcon().equals(mole[10]) || moleButton.getIcon().equals(mole[9])
										|| moleButton.getIcon().equals(mole[5]) || moleButton.getIcon().equals(mole[4])
										|| moleButton.getIcon().equals(mole[3])) {
									timer.stop();
									calculateChampionMovement(e.getX(), e.getY(), champion);
									startTime = System.currentTimeMillis();
									timer.start();
								}
							}
							if (e.getButton() == MouseEvent.BUTTON1) {
								if (moleButton.getIcon().equals(mole[13]))
									moleButton.setIcon(mole[12]);
								else if (moleButton.getIcon().equals(mole[11]))
									moleButton.setIcon(mole[8]);
								else if (moleButton.getIcon().equals(mole[10]))
									moleButton.setIcon(mole[7]);
								else if (moleButton.getIcon().equals(mole[9]))
									moleButton.setIcon(mole[6]);
								else if (moleButton.getIcon().equals(mole[5]))
									moleButton.setIcon(mole[2]);
								else if (moleButton.getIcon().equals(mole[4]))
									moleButton.setIcon(mole[1]);
								else if (moleButton.getIcon().equals(mole[3]))
									moleButton.setIcon(mole[0]);
							}
						}
					});
				}
			});
			timer = new Timer(10, e -> {
				if (eating == false) {

					molesecond++;
					molesecond = molesecond % 4;
					if (direction == 1) { // �����ʹ������� �����϶� -����
						if (molesecond == 1)
							moleButton.setIcon(mole[8]);
						else if (molesecond == 2)
							moleButton.setIcon(mole[9]);
						else if (molesecond == 3)
							moleButton.setIcon(mole[10]);
					}
					if (direction == 2) { // ���ʹ������� �����϶� -����
						if (molesecond == 1)
							moleButton.setIcon(mole[3]);
						else if (molesecond == 2)
							moleButton.setIcon(mole[4]);
						else if (molesecond == 3)
							moleButton.setIcon(mole[5]);
					}
					TimeMove();
					/*
					if (getBounds().intersects(human.getBounds()))
						this.moleButton.setVisible(false);
						*/
				} else
					timer.stop();
			});
		}
		
		public void moledie() {
			moleButton.setVisible(false);
			champion.setBounds(0,0,0,0);
			life = false;
			molecount --;
		}
		
		public boolean getlife() {
			return life;
		}
		
		public int getmoleLife() {
			return molecount;
		}

		public void snakeTimer() {
			snakeTimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					snakesecond--;
					System.out.println(snakesecond);
					if (itembox1.getIcon() == itemsnakepipe) {
						itembox1.setFont(font1);
						itembox1.setText(snakesecond + "");
						itembox1.setVerticalTextPosition(JLabel.CENTER);
						itembox1.setHorizontalTextPosition(JLabel.CENTER);
						itembox1.setForeground(Color.cyan);
					} else {
						itembox2.setText(snakesecond + "");
						itembox2.setFont(font1);
						itembox2.setVerticalTextPosition(JLabel.CENTER);
						itembox2.setHorizontalTextPosition(JLabel.CENTER);
						itembox2.setForeground(Color.cyan);
					}
					if (snakesecond == 0) {
						if (itembox1.getIcon() == itemsnakepipe) {
							itembox1.setIcon(null);
							itembox1.setText(null);
							itembox1.setVisible(false);
						} else {
							itembox2.setIcon(null);
							itembox2.setText(null);
						}
						snakesecond = 15;
						itembox1.setVisible(false);
						snakeTimer.stop();
					}
				}
			});
		}
		
		public void molegetitem() {
			//int itemnum = 0;
			int itemnum = ((int)(Math.random()*10));
			switch (itemnum) {
			case 0:
			case 2:
			case 9:
				System.out.println(itemnum);
				System.out.println("���Ǹ� ȹ��");
				System.out.println(itembox1.isVisible());
				n.makeSnake();
				if(itembox1.isVisible() == false) {
					itembox1.setVisible(true);
					itembox1.setIcon(itemsnakepipe);
					snakeTimer();
					snakeTimer.start();
				} else if (itembox1.getIcon() == itemsnakepipe || itembox2.getIcon() == itemsnakepipe) {
					snakesecond += 17;
				} else {
					snakeTimer();
					snakeTimer.start();
					itembox2.setIcon(itemsnakepipe);
				}
				break;
			case 1:
			case 8:
				System.out.println(itemnum);
				System.out.println("��� ����");
				if(itembox1.isVisible() == false) {
					itembox1.setVisible(true);
					itembox1.setIcon(itemtrap);
					humstop = true;
					humstop();
					humstoptimer.start();
				}
				else if (itembox1.getIcon() == itemtrap || itembox2.getIcon() == itemtrap) {
					humstun+= 3;
				}
				else {
					humstop();
					humstoptimer.start();
					itembox2.setIcon(itemtrap);
				}
				break;
			default: 
				enhenceteeth = true;
				System.out.println("��ö�̻� ȹ��");
				System.out.println(itembox1.isVisible());
				if(itembox1.isVisible() == false) {
					itembox1.setVisible(true);
					itembox1.setIcon(itemteeth);
				}
				else {
					itembox2.setIcon(itemteeth);
				}
				break;
			}
		}
		
		public void humstop() {
			humstoptimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					humstun--;
					if (itembox1.getIcon() == itemtrap) {
						itembox1.setFont(font1);
						itembox1.setText(humstun + "");
						itembox1.setVerticalTextPosition(JLabel.CENTER);
						itembox1.setHorizontalTextPosition(JLabel.CENTER);
						itembox1.setForeground(Color.cyan);
					} else {
						itembox2.setText(humstun + "");
						itembox2.setFont(font1);
						itembox2.setVerticalTextPosition(JLabel.CENTER);
						itembox2.setHorizontalTextPosition(JLabel.CENTER);
						itembox2.setForeground(Color.cyan);
					}
					if(humstun == 0) {
						if(itembox1.getIcon() == itemtrap) {
							itembox1.setIcon(null);
							itembox1.setText(null);
						}
						else {
							itembox2.setIcon(null);
							itembox2.setText(null);
						}
						humstop = false;
						humstun = 3;
						humstoptimer.stop();
					}
				}
			});
		}
		
		public void eatTimer() {
			eating = true;
			eattimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					eatsecond++;
					if (eatsecond == 3) {
						eattimer.stop();
						eating = false;
					}
				}
			});
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
			
			if (y >= 270) {
				moleButton.setBounds((int) x - 15, (int) y - 15, 32, 32);
				champion.setRect(x - 15, y - 15, 32, 32);
			}
			
			if(y < 290 && humtrap == true) {
				moleButton.setVisible(false);
				humtrap = false;
				molecount--;
			}

			if (i0.getBounds().intersects(champion) && i0.timerstop == false) {
				i0.setVisible(false);
				i0.setsecond(0);
				i0.itemtimer();
				i0.itemtimer.start();
				molegetitem();

			} else if (i1.getBounds().intersects(champion) && i1.timerstop == false) {
				i1.setVisible(false);
				i1.setsecond(0);
				i1.itemtimer();
				i1.itemtimer.start();
				molegetitem();
			}

			if (v0.getBounds().intersects(champion) && v0.timerstop == false && eating == false) {
				v0.setVisible(false);
				v0.setsecond(0);
				v0.vegtimer();
				v0.vegtimer.start();
				eatsecond = 0;
				if(enhenceteeth == false) {	
					eatTimer();
					eattimer.start();
					} else {
						enhenceteeth = false ;
						if(itembox1.getIcon() == itemteeth && itembox2.getIcon() == itemteeth) {
							itembox1.setIcon(null);
							enhenceteeth = true;
						}
						else if(itembox1.getIcon() == itemteeth)
							itembox1.setIcon(null);
						else 
							itembox2.setIcon(null);
					}
				v0.plusvegcount();
			} else if (v1.getBounds().intersects(champion) && v1.timerstop == false && eating == false) {
				v1.setVisible(false);
				v1.setsecond(0);
				v1.vegtimer();
				v1.vegtimer.start();
				eatsecond = 0;
				if(enhenceteeth == false) {	
					eatTimer();
					eattimer.start();
					} else {
						enhenceteeth = false ;
						if(itembox1.getIcon() == itemteeth && itembox2.getIcon() == itemteeth) {
							itembox1.setIcon(null);
							enhenceteeth = true;
						}
						else if(itembox1.getIcon() == itemteeth)
							itembox1.setIcon(null);
						else 
							itembox2.setIcon(null);
						}
				v1.plusvegcount();
			} else if (v2.getBounds().intersects(champion) && v2.timerstop == false && eating == false) {
				v2.setVisible(false);
				v2.setsecond(0);
				v2.vegtimer();
				v2.vegtimer.start();
				eatsecond = 0;
				if(enhenceteeth == false) {	
					eatTimer();
					eattimer.start();
					} else {
						enhenceteeth = false ;
						if(itembox1.getIcon() == itemteeth && itembox2.getIcon() == itemteeth) {
							itembox1.setIcon(null);
							enhenceteeth = true;
						}
						else if(itembox1.getIcon() == itemteeth)
							itembox1.setIcon(null);
						else 
							itembox2.setIcon(null);
					}
				v2.plusvegcount();
			}
		}

		public void calculateChampionMovement(double x, double y, Rectangle champion) {

			if (x != champion.getCenterX() || y != champion.getCenterY()) {

				targetX = x;
				targetY = y;

				startX = champion.getCenterX();
				startY = champion.getCenterY();

				if (targetX - startX > 0) // ����üũ, ��и�
					direction = 1;
				else
					direction = 2;

				double distance = Math
						.sqrt((startX - targetX) * (startX - targetX) + (startY - targetY) * (startY - targetY));

				runTime = distance / (double) speed;
			}
		}
	}
} // MolePanel ������

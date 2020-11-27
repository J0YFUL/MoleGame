package Mole.Game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Game extends Canvas implements Runnable { // �ٸ� Ŭ����,�ڹ����Ͽ��� new Ű����� Game�� �������� �� ��.

	private static final long serialVersionUID = 1L;
	// ������ ���� ����
	public static final int WIDTH = 800;
	public static final int HEIGHT = (int) (WIDTH / 12 * 9);
	public static final int SCALE = 1;
	public final String TITLE = "Mole Game";

	public static int BULLETCOUNT = 5;
	private boolean is_shooting = false; // �Ѿ� �߻��ư�� �ڴ����� ������ �����°� ����
	public static boolean buldirection = true; // �Ѿ˹���, true�� ������, false�� ����
	
	private static String countdown = "";
	
	private static int vegCount = 0;
	
	// 3���� ī��Ʈ�ٿ�-> Frame�� ��.
	private static JLabel count;
	private static Font fonts = new Font("Arial", Font.BOLD, 30);
	private static Timer timer;
	private static int second, minute;
	private static String ddSecond, ddMinute;
	private static DecimalFormat dFormat = new DecimalFormat("00");

	private boolean running = false; // ������ ���࿩��
	private Thread thread;

	private BufferedImage background = null; // �������ϴ� �����̹���
	private BufferedImage humSpriteSheet = null; // �ΰ�����ϴ� �����̹���
	private BufferedImage bulSpriteSheet = null; // �Ѿ�����ϴ� �����̹���
	private BufferedImage molSpriteSheet = null; // �δ�������ϴ� �����̹���
	private BufferedImage snaSpriteSheet = null; // �δ�������ϴ� �����̹���
	private BufferedImage vegSpriteSheet = null; // �δ�������ϴ� �����̹���

	// ĳ���� ����
	private Human human;	
	private Mole mole;
	
	// ��Ÿ��� 
	private Controller c; // ��Ʈ�ѷ�
	private Snake snake;
	private Vegetable vegetable;
	
	private Textures texture;
	private Hud hud;
	
	public LinkedList<Bullet> b;
	public LinkedList<Mole> m;
	public LinkedList<Vegetable> v;

	private enum STATE {
		MENU,
		GAME
	};
	
	private STATE State = STATE.MENU;
	
	public void init() {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			humSpriteSheet = loader.loadImage("/humanSpr.png");
			bulSpriteSheet = loader.loadImage("/bulimg.png");
			molSpriteSheet = loader.loadImage("/moleSpr.png");
			snaSpriteSheet = loader.loadImage("/snakeSpr.png");
			vegSpriteSheet = loader.loadImage("/vegetables.png");
			background = loader.loadImage("/Backgrounds.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		texture = new Textures(this); // �������� �ؽ�ó�� ����
		human = new Human(200, 225, texture,this);
		snake = new Snake(texture,this);
		
		c = new Controller(this, texture);
		hud = new Hud(this);
		
		
		b = c.getBullet(); // �޼ҵ带 �θ������� Controller�� �ʱ�ȭ ���־���Ѵ�.
		m = c.getMole();
		v = c.getVegetable();
		
		addKeyListener(new KeyInput(this));
		addMouseListener(new MouseInput(this));
		c.addMole(mole.mole_count);
		c.addVegetable(3);
	}

	public synchronized void start() { // �����ϴ� �Լ� - start, ������ ���ư����ִ��� Ȯ���ϰ� �ȵ��ư���������, �����带 ������ ���ÿ� ������.start�� run�Լ��� �����Ѵ�.
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running)
			return;

		running = false;
		try {
			thread.join(); // ��� �����尡 ���������� ��ٷȴٰ� ����(System.exit(1))���� ����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	@Override
	public void run() { // Runnable�� �⺻���� ������ �޼ҵ�
		
		init(); // �ʱ⼳�� �ϴ� �޼ҵ�; ��ü ������Ұ� �� �������
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0; // ��ŸŸ��, ���ӿ����� �ð� ���
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) { // Game Loop
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now; // OldTime = CurTime,

			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, Fps - " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void tick() { // �����? �ð����������� ��ü������ ���ӿ��� �ݺ��Ǵ� ������ ���� �ν��Ͻ� �Ǵ� ������ �Һ��ϴ� �Ⱓ�� ��Ÿ���ϴ�.
		human.tick();
		c.tick();
		snake.tick();
	}

	private void render() { // render - ���� ������ ȭ���� �׷��ִ� �Լ�, bufferstrategy�� ��� ���� ��� ���۸��� �����Ѵ�.

		BufferStrategy bs = this.getBufferStrategy(); // this�� Canvas ��ü�� ����Ŵ

		if (bs == null) {
			createBufferStrategy(3); // 3���� ���۸� �����Ѵٴ� ��, �ٷ� �ʿ��Ѱ�(����ϴ���) �ڿ� �ΰ��� �̹����� �ε��ϰ� ���� ���̴�.
										// 30���� ���۸� �����Ѵٸ�, �׸�ŭ CPU�� �ڿ��� ��Ƹ԰� �ȴ�. ������ ������ ���,���
			return;
		}
		Graphics g = bs.getDrawGraphics(); // graphic�� ���⿡ ��������, ���۸� �׸��� ���� graphics context�� �����, �ܺ� ���۸� �׸�
		/////////////// �׸� �׸��� ���� ���� ����//////////////////////////
		
		
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		countDownRender(g, countdown);
		lifCountRender(g,String.format("%d",human.getLife()));
		bulCountRender(g,String.format("%d",BULLETCOUNT));
		vegCountRender(g,String.format("%d",vegCount));
		molCountRender(g,String.format("%d",mole.mole_count));
		
		c.render(g); // c.render�� mole, bullet, vegetable�� �������
		human.render(g); // �ΰ� �׸���
		
		// moleP.render(g);
		snake.render(g);
		hud.render(g);

		///////////////////////////////////////////////////////////
		g.dispose(); // ��� ������ �ϴµ� dispose�� �������� �ʴ´ٸ�..?
		bs.show();
	}

	public void keyPressed(KeyEvent e) { // Key�� key ����
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			 // ������ ����Ű
			human.setVelX(3);
			human.rightMove();
		} else if (key == KeyEvent.VK_LEFT) {
			// ���� ����Ű
			human.setVelX(-3);
			human.leftMove();
		} else if (key == KeyEvent.VK_A && (human.getStatus() == 1 || human.getStatus() == 3) && (BULLETCOUNT > 0) && !is_shooting) {
			// ������ �����ִ� ���¿��� AŰ
			this.buldirection = false;
			is_shooting = true;
			c.addEntity(new Bullet(human.getX(), human.getY() + 35, texture, this));
			--BULLETCOUNT;
		} else if (key == KeyEvent.VK_D && (human.getStatus() == 0 || human.getStatus() == 2) && (BULLETCOUNT > 0) && !is_shooting) { 
			// �������� �����ִ� ���¿��� DŰ
			this.buldirection = true;
			is_shooting = true;
			c.addEntity(new Bullet(human.getX() + 50, human.getY() + 35, texture, this));
			--BULLETCOUNT;
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			human.setVelX(0);
			human.rightStand();
		} else if (key == KeyEvent.VK_LEFT) {
			human.setVelX(0);
			human.leftStand();
		} else if (key == KeyEvent.VK_A) {
			is_shooting = false;
		} else if (key == KeyEvent.VK_D) {
			is_shooting = false;
		}
	}
	
	
	public static void main(String args[]) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.setLayout(new BorderLayout());
		
		count = new JLabel("");
		count.setText("03:00"); // �ð� ���� : 3:00
		second = 0;
		minute = 3;
		normalTimer(game,count);
		timer.start();

		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		game.start();
	}
	public static void normalTimer(Game game, JLabel label) {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				second--;

				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);

				label.setText(ddMinute + ":" + ddSecond);

				if (second == -1) {
					second = 59;
					minute--;

					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);
					label.setText(ddMinute + ":" + ddSecond);
				}
				countdown = label.getText();
				if (minute == 0 && second == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "�ΰ� �¸�!!", "Result", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
	}
	public void countDownRender(Graphics g,String countdown) {
		g.setFont(fonts);
		g.drawString(countdown, 380, 25);
		g.setFont(getFont()); // �⺻�� ����
	}
	public void lifCountRender(Graphics g,String value) {
		g.drawString(value, 28, 85);
	}
	public void bulCountRender(Graphics g,String value) {
		g.drawString(value, 28, 105);
	}
	public void vegCountRender(Graphics g,String value) {
		g.drawString(value, 768, 105);
	}
	public void molCountRender(Graphics g,String value) {
		g.drawString(value, 768, 85);
	}
	
	
	public BufferedImage getHumSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return humSpriteSheet;
	}

	public BufferedImage getBulSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return bulSpriteSheet;
	}

	public BufferedImage getMolSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return molSpriteSheet;
	}
	public BufferedImage getSnaSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return snaSpriteSheet;
	}
	public BufferedImage getVegSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return vegSpriteSheet;
	}

	public Human getPlayer() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������, Controller Ŭ�������� ���
		return human;
	}
}
package Mole.Game;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 1;
	public final String TITLE = "Mole Game";
	
	private boolean running = false;
	private Thread thread;
	
	private synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if(!running)
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
		while(running) {
			// Game Loop
		}
	}
	public static void main(String args[]) {
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
	
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
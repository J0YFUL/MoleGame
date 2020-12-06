package Mole.Game;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

class QuestionFrame extends JFrame { // ���� ��ư�� Ŭ������ �� ������ ������
	private QuestionBG questionBG = new QuestionBG();
	private JButton back;
	private JPanel board;
	private JLabel label1;
	private JTabbedPane tab;
	private JLabel lab1;

	ImageIcon back_img = new ImageIcon("img/mainResource/back.png");
	ImageIcon back1_img = new ImageIcon("img/mainResource/back1.png");

	QuestionFrame() throws IOException {
		CustomCursor();
		setTitle("����"); // Ÿ��Ʋ
		setSize(800, 600); // �������� ũ��
		setResizable(false); // â�� ũ�⸦ �������� ���ϰ�
		setLocationRelativeTo(null); // â�� ��� ������
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// JFrame�� ���������� ����ǰ� ��.

		questionBG.setLayout(null);

		// ������ �̹��� ����
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image icon = kit.getImage("img/mainResource/moleicon.png");
		setIconImage(icon);

		// ��Ʈ ����
		Font wr = new Font("HY�߰��", Font.PLAIN, 18);

		DataInputStream in = null;

		tab = new JTabbedPane(); // ����
		tab.setBounds(50, 50, 700, 450);
		JTextArea p1 = new JTextArea(); // �г� 1 - ���� ��Ģ �� ����
		p1.setEditable(false);
		p1.setFont(wr);
		try {
			in = new DataInputStream(new BufferedInputStream(new FileInputStream("lib/playRule.bin")));
			p1.append(in.readUTF());
		} finally {
			if (in != null) {
				in.close();
			}

		}

		JTextArea p2 = new JTextArea(); // �г� 2 - �ΰ� ���� �� ������
		p2.setEditable(false);
		p2.setFont(wr);
		try {
			in = new DataInputStream(new BufferedInputStream(new FileInputStream("lib/humanRule.bin")));
			p2.append(in.readUTF());
		} finally {
			if (in != null)
				in.close();
		}

		JTextArea p3 = new JTextArea(); // �г� 3 - �δ��� ���� �� ������
		p3.setEditable(false);
		p3.setFont(wr);
		try {
			in = new DataInputStream(new BufferedInputStream(new FileInputStream("lib/moleRule.bin")));
			p3.append(in.readUTF());
		} finally {
			if (in != null)
				in.close();
		}

		JTextArea p4 = new JTextArea(); // �г� 4 - etc(������)
		p4.setEditable(false);
		p4.setFont(wr);
		try {
			in = new DataInputStream(new BufferedInputStream(new FileInputStream("lib/etc.bin")));
			p4.append(in.readUTF());
		} finally {
			if (in != null)
				in.close();
		}

		tab.addTab("����", p1);
		tab.addTab("�ΰ�", p2);
		tab.addTab("�δ���", p3);
		tab.addTab("ETC", p4);

		questionBG.add(tab);

		back = new JButton(back_img);
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		back.setBounds(720, 0, 50, 50);
		back.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				back.setIcon(back1_img);
			}

			public void mouseExited(MouseEvent e) {
				back.setIcon(back_img);
			}

			public void mousePressed(MouseEvent e) {
				System.out.println("���� �ݱ�");
				setVisible(false);
				new MainFrame();
			}
		});
		questionBG.add(back);

		add(questionBG);
		setVisible(true);
	}

	public void CustomCursor() { // Ŀ���� Ŀ��(���콺 Ŀ��)

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("img/mainResource/cropcursor.png");
		Point point = new Point(20, 20);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "crop");
		questionBG.setCursor(cursor);
	}

	class QuestionBG extends JPanel {
		private BufferedImage backs;

		public QuestionBG() {
			try {
				backs = ImageIO.read(new File("img/mainResource/threemoles.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backs, 0, 0, null);
		}
	}
}
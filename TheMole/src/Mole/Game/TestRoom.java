package Mole.Game;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import MoleServer.MoleClientMainHandler;
import io.netty.channel.ChannelHandlerContext;

/*public class TestRoom extends JFrame {
	public TestRoom(ChannelHandlerContext ctx, LinkedList<String> roomList) {
		setTitle("RoomTest");
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		RoomListPanel roomListPanel = new RoomListPanel(ctx, roomList, this);
		RoomTest roomTest = new RoomTest(ctx, LoginForm.getId(), "");
		add(roomListPanel);
		roomListPanel.testButton.addActionListener(e -> {
			ctx.writeAndFlush("[CREAT]," + LoginForm.getId());
			add(roomTest);
			roomListPanel.setVisible(false);
			roomTest.setVisible(true);
		});
	//	roomListPanel.setVisible(true);
		setVisible(true);
	}
}*/
public class TestRoom extends JPanel{
	JPanel panel;
	JButton[] button;
	JButton testButton;
	JButton testButtonB;
	JButton outButton;
	public TestRoom(ChannelHandlerContext ctx, LinkedList<String> roomList) {
		button = new JButton[roomList.size()];
		for(int i = 0; i < roomList.size(); i++) {
			button[i] = new JButton(roomList.get(i) + "���� ��");
			add(button[i]);
			button[i].addActionListener(e -> {
				String n = e.getActionCommand();
				String s[] = n.split("��");
				ctx.writeAndFlush("[JOIN]" + "," + s[0] + "," + LoginForm.getId());
			});
		}
		testButton = new JButton("�����");
		add(testButton);
		testButton.addActionListener(e -> {
			ctx.writeAndFlush("[CREAT]," + LoginForm.getId());
		});
		testButtonB = new JButton("���ΰ�ħ");
		add(testButtonB);
		testButtonB.addActionListener(e -> {
			setVisible(false);
			System.out.println("1");
			ctx.writeAndFlush("[REFRESH]");
			System.out.println("1");
		//	setVisible(false);
		//	setVisible(true);
		});
		outButton = new JButton("������");
		add(outButton);
		outButton.addActionListener(e -> {
			setVisible(false);
			MoleClientMainHandler.homePanel.setVisible(true);
		});
	}
}



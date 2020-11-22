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
			ctx.writeAndFlush("[REFRESH]");
		});
		outButton = new JButton("������");
		add(outButton);
		outButton.addActionListener(e -> {
			setVisible(false);
			MoleClientMainHandler.homePanel.setVisible(true);
		});
	}
}



package Mole.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import MoleServer.MoleClientMainHandler;
import io.netty.channel.ChannelHandlerContext;

public class RoomTest extends JPanel {
	public static JButton testStart;
	JButton testOut;
	JButton host;
	JButton guest;
	JTextField chatField;
	public static JTextArea chatArea;
	JScrollPane scroll;
	public static JLabel ready;
	public RoomTest(ChannelHandlerContext ctx, String hostName, String guestName) {

		testStart = new JButton("����");
		testOut = new JButton("������");
		host = new JButton(hostName);
		host.addActionListener(e -> {
			ctx.writeAndFlush("[INFORMATION]" + "," + hostName);
		});
		guest = new JButton(guestName);
		guest.addActionListener(e -> {
			ctx.writeAndFlush("[INFORMATION]" + "," + guestName);
		});
		ready = new JLabel("");
		testOut.addActionListener(e -> {
			setVisible(false);
			if(LoginForm.getId().equals(hostName))
				ctx.writeAndFlush("[HOSTOUT]," + hostName);
			else
				ctx.writeAndFlush("[GUESTOUT]," + hostName);
		});	
		
		testStart.addActionListener(e -> {
			if (testStart.getText().equals("�غ�")) { 
				testStart.setText("�غ����");
				ctx.writeAndFlush("[READY]," + hostName);
			} 
			else if (testStart.getText().equals("�غ����")) {
				testStart.setText("�غ�");
				ctx.writeAndFlush("[CANSLE]," + hostName);
			}
			else
				if (ready.getText().equals("�غ�Ϸ�"))
					ctx.writeAndFlush("[START]," + hostName + "," + guestName);
				else
					JOptionPane.showMessageDialog(MoleClientMainHandler.mainFrame, "������ �غ����� �ʾҽ��ϴ�.");
		});
		chatField = new JTextField(30);
		chatField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if (!chatField.getText().equals("")) {
						ctx.writeAndFlush("[SENDMESSAGE]," + chatField.getText() + "," + LoginForm.getId() + "," + hostName);
						chatField.setText("");
					}
				}
			}
			
		});
		chatArea = new JTextArea(10, 30);
		chatArea.setEditable(false);
		scroll = new JScrollPane(chatArea);
		add(ready);
		add(host);
		add(guest);
		add(testStart);
		add(testOut);
		add(chatField);
		add(scroll);
	}
}
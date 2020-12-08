package MoleServer;

import Mole.Game.GameStart;
import Mole.Game.HumanUI;
import Mole.Game.MoleInHumanPerformance;
import Mole.Game.MoleUI;
import Mole.Game.vegetableThread;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MoleClientGameHandler extends ChannelInboundHandlerAdapter {
	public static MoleUI moleUI;
	public static HumanUI humanUI;
	private GameStart gameStart;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = (String) msg;
		String[] s = readMessage.split(",");

		if (s[0].equals("MOLESTART")) {
			MoleClientMainHandler.roomTest.setVisible(false);
			gameStart = new GameStart(s[3], s[4]);
			gameStart.molePlayer.setText(s[3]);
			gameStart.humanPlayer.setText(s[4]);
			MoleClientMainHandler.mainFrame.add(gameStart);
			gameStart.setVisible(true);
			moleUI = new MoleUI(ctx, s[1], Integer.parseInt(s[5]), Integer.parseInt(s[6]), Integer.parseInt(s[7]),
					Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[10]));
			MoleClientMainHandler.mainFrame.add(moleUI);
			Thread.sleep(2000);
			gameStart.setVisible(false);
			System.out.println(s[5]);
			System.out.println(s[6]);
			System.out.println(s[7]);
			moleUI.setVisible(true);
		}

		else if (s[0].equals("HUMANSTART")) {
			MoleClientMainHandler.roomTest.setVisible(false);
			GameStart gameStart = new GameStart(s[3], s[4]);
			gameStart.humanPlayer.setText(s[3]);
			gameStart.molePlayer.setText(s[4]);
			MoleClientMainHandler.mainFrame.add(gameStart);
			gameStart.setVisible(true);
			humanUI = new HumanUI(ctx, s[1], Integer.parseInt(s[5]), Integer.parseInt(s[6]), Integer.parseInt(s[7]),
					Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[10]));
			MoleClientMainHandler.mainFrame.add(humanUI);
			Thread.sleep(2000);
			gameStart.setVisible(false);
			humanUI.setVisible(true);
		}

		for (int i = 0; i < s.length; i++) {
		if (s[i].equals("RIGHT"))
			moleUI.getMoleInHumanPerformance().moleInHumanMove(s[i]);
		else if (s[i].equals("LEFT"))
			moleUI.getMoleInHumanPerformance().moleInHumanMove(s[i]);
		else if (s[i].equals("LEFTSTOP"))
			moleUI.getMoleInHumanPerformance().moleInHumanMove(s[i]);
		else if (s[i].equals("RIGHTSTOP"))
			moleUI.getMoleInHumanPerformance().moleInHumanMove(s[i]);
		else if (s[i].equals("v1EAT")) {
			humanUI.getV1().setVisible(false);
			humanUI.setVegcount(humanUI.getVegcount() - 1);
			humanUI.getVegCountLabel().setText(humanUI.getVegcount() + "");
		} else if (s[i].equals("MOLEv1")) {
			int x = Integer.parseInt(s[i+1]);
			int y = Integer.parseInt(s[i+2]);
			moleUI.getV1().setIcon(vegetableThread.vegetables[y]);
			moleUI.getV1().setBounds(x, 240, 32, 32);
			moleUI.getV1().setVisible(true);
		} else if (s[i].equals("HUMANv1")) {
			int x = Integer.parseInt(s[i+1]);
			int y = Integer.parseInt(s[i+2]);
			humanUI.getV1().setIcon(vegetableThread.vegetables[y]);
			humanUI.getV1().setBounds(x, 240, 32, 32);
			humanUI.getV1().setVisible(true);
		} else if (readMessage.equals("v2EAT")) {
			humanUI.getV2().setVisible(false);
			humanUI.setVegcount(humanUI.getVegcount() - 1);
			humanUI.getVegCountLabel().setText(humanUI.getVegcount() + "");
		} else if (s[i].equals("MOLEv2")) {
			int x = Integer.parseInt(s[i+1]);
			int y = Integer.parseInt(s[i+2]);
			moleUI.getV2().setIcon(vegetableThread.vegetables[y]);
			moleUI.getV2().setBounds(x, 240, 32, 32);
			moleUI.getV2().setVisible(true);
		} else if (s[i].equals("HUMANv2")) {
			int x = Integer.parseInt(s[i+1]);
			int y = Integer.parseInt(s[i+2]);
			humanUI.getV2().setIcon(vegetableThread.vegetables[y]);
			humanUI.getV2().setBounds(x, 240, 32, 32);
			humanUI.getV2().setVisible(true);
		} else if (readMessage.equals("v3EAT")) {
			humanUI.getV3().setVisible(false);
			humanUI.setVegcount(humanUI.getVegcount() - 1);
			humanUI.getVegCountLabel().setText(humanUI.getVegcount() + "");
		} else if (s[i].equals("MOLEv3")) {
			int x = Integer.parseInt(s[i+1]);
			int y = Integer.parseInt(s[i+2]);
			moleUI.getV3().setIcon(vegetableThread.vegetables[y]);
			moleUI.getV3().setBounds(x, 240, 32, 32);
			moleUI.getV3().setVisible(true);
		} else if (s[i].equals("HUMANv3")) {
			int x = Integer.parseInt(s[i+1]);
			int y = Integer.parseInt(s[i+2]);
			humanUI.getV3().setIcon(vegetableThread.vegetables[y]);
			humanUI.getV3().setBounds(x, 240, 32, 32);
			humanUI.getV3().setVisible(true);
		} else if (s[i].equals("MOLEITEM")) {
			int a = Integer.parseInt(s[i+1]);
			int b = Integer.parseInt(s[i+2]);
			moleUI.getI1().setBounds(a, 270, 40, 40);
			moleUI.getI1().setVisible(true);
			moleUI.getI2().setBounds(b, 270, 40, 40);
			moleUI.getI2().setVisible(true);
		} else if (s[i].equals("HUMANITEM")) {
			int a = Integer.parseInt(s[i+1]);
			int b = Integer.parseInt(s[i+2]);
			humanUI.getI1().setTimerstop(false);
			humanUI.getI2().setTimerstop(false);
			humanUI.getI1().setBounds(a, 270, 40, 40);
			humanUI.getI1().setVisible(true);
			humanUI.getI2().setBounds(b, 270, 40, 40);
			humanUI.getI2().setVisible(true);
		} else if (readMessage.equals("MOLEITEM1EAT"))
			humanUI.getI1().setVisible(false);
		else if (readMessage.equals("MOLEITEM2EAT"))
			humanUI.getI2().setVisible(false);
		else if (s[i].equals("HUMANITEM1EAT"))
				moleUI.getI1().setVisible(false);
			else if (s[i].equals("HUMANITEM2EAT"))
				moleUI.getI2().setVisible(false);
			else if (s[i].equals("HUMANSPEEDUP"))
				moleUI.getMoleInHumanPerformance().setHumanspeed(7);
			else if (s[i].equals("HUMANSPEEDDOWN"))
				moleUI.getMoleInHumanPerformance().setHumanspeed(5);
			else if (s[i].equals("MOLEMOVE")) {
				int x = Integer.parseInt(s[i + 1]);
				int y = Integer.parseInt(s[i + 2]);
				humanUI.moleMessage(s[i + 3], x, y);
			} else if (s[i].equals("MOLETRAP"))
				humanUI.getHuman().setHumanspeed(0);
			else if (s[i].equals("MOLETRAPSTOP"))
				humanUI.getHuman().setHumanspeed(5);
			else if (s[i].equals("HUMANTRAP"))
				moleUI.setMoleKill(false);
			else if (s[i].equals("HUMANTRAPSTOP"))
				moleUI.setMoleKill(true);
			else if (s[i].equals("MOLESNAKE")) {
				int status = Integer.parseInt(s[i+1]);
				moleUI.makeSnake(status);
			} else if (s[i].equals("HUMANSNAKE")) {
				int status = Integer.parseInt(s[i+1]);
				humanUI.makeSnake(status);
			} else if (s[i].equals("BULLET")) {
				int direction = Integer.parseInt(s[i+1]);
				int status = Integer.parseInt(s[i+2]);
				moleUI.getMoleInHumanPerformance().bullet(moleUI.getMoleInHumanPerformance().getX(), direction, status);
			}
		}
	}
}
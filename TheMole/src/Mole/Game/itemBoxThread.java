package Mole.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class itemBoxThread extends JLabel{
	private ImageIcon itemB = new ImageIcon("img/itemBox.png");
	private int x, y,section;
	public Timer itemtimer;
	private int itemsecond;
	private int itemcount = 0;
	public boolean timerstop = false;
	private boolean enhenceteeth = false;
	private boolean trap;
	private boolean snakepipe;
			
	public itemBoxThread(int section) {
		this.section = section;
		x = ((int) (Math.random() * 260)) + 263 * this.section;
		y = 255;
		setBounds(x, y, 40, 40);
		setIcon(itemB);
		// itemBox.setIcon(itemB);
		//System.out.println("��������ġ " + x + " " + y);
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setsecond(int second) {
		itemsecond = second;
	}
	public int getsecond() {
		return itemsecond;
	}
	public int getcouont() {
		return itemcount;
	}
	public int setcount(int count) {
		return itemcount = count;
	}
	
	public void setposition() {
		x = ((int) (Math.random() * 260)) + 263 * this.section;
		y = 235;
		setBounds(x, y, 40, 40);			 
	}
	public void itemtimer() {
		timerstop = true;
		itemtimer = new Timer(1000,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				itemsecond++;
				if(itemsecond == 30) {
					setposition();
					setVisible(true);
					itemtimer.stop();
					timerstop = false;
					itemcount++;
				}
			}
		});
	}
}
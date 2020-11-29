package Dump;

import java.applet.AudioClip;

import javax.swing.JApplet;

public class SoundPlayer {

	public static final SoundPlayer sound = new SoundPlayer("test.mp3");
	
	private AudioClip inputSound; // ���ϸ� �׳� ���ϸ� ������ // �����˾Ƽ� ã����

	private SoundPlayer(String SoundFileURL) {
			inputSound = JApplet.newAudioClip(getClass().getResource(SoundFileURL));
	}

	public void play() {
			inputSound.play();	
	}

	public void stopPlayer() {
		inputSound.stop();
	}
}
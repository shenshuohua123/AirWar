package AircraftWars;

import java.awt.event.KeyEvent;

public class MyPlane extends flyingObject {
	int lives ;
	int speed ;
	boolean up, down, left, right;
	// ����һ���ҩ�����
	int power = 1;
	//���ؿ�����3ʱ
	public MyPlane(int rankNum) {
		if(rankNum>3) {
			lives = 3;
			speed = 6;
			image = APP.getImg("/img/hero.png");
			width = image.getWidth();
			height = image.getHeight();
		}else {
			lives = 3;
			speed = 3;
			image = APP.getImg("/img/MyPlane.png");
			width = 114;
			height = 93;
		}		
		
		x = 521 / 2;/* ���ڿ��/2 */
		y = 500;/* ���ڸ߶� */		
	}

	/*
	 * public void move() {// �һ��ƶ� if (up) y -= speed; if (down) y += speed; if
	 * (left) x -= speed; if (right) x += speed; }
	 */
	// �����ƶ�
	public void moveUp() {
		y -= 20;
	}
	// �����ƶ�
	public void moveDown() {
		y += 20;
	}
	// �����ƶ�
	public void moveLeft() {
		x -= 20;
	}
	// �����ƶ�
	public void moveRight() {
		x += 20;
	}

	public void press_key(KeyEvent e) {// ���°���
		switch (e.getKeyCode()) {
		case 37:// ��37
			left = true;
			break;
		case 38:// �ϣ�38
			up = true;
			break;
		case 39:// �ң�39
			right = true;
			break;
		case 40:// �£�40
			down = true;
			break;
		default:
			break;
		}
	}

	public void release_key(KeyEvent e) {// �ɿ�����
		switch (e.getKeyCode()) {
		case 37:// ��37
			left = false;
			break;
		case 38:// �ϣ�38
			up = false;
			break;
		case 39:// �ң�39
			right = false;
			break;
		case 40:// �£�40
			down = false;
			break;
		default:
			break;
		}
	}

	public boolean Crash(EnemyPlane f) {// ײ��
		  return f.x<this.x+this.width&&f.x+f.width>this.x&&f.y<this.y+this.height&&f.y+f.height>this.y;
	}

	public boolean outOfBounds() {// �ж��Ƿ����
		return false;// ����Խ��
	}
}
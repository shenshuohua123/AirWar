package AircraftWars;

import java.awt.event.KeyEvent;

public class MyPlane extends flyingObject {
	int lives ;
	int speed ;
	boolean up, down, left, right;
	// 添加我机弹药库火力
	int power = 1;
	//当关卡大于3时
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
		
		x = 521 / 2;/* 窗口宽度/2 */
		y = 500;/* 窗口高度 */		
	}

	/*
	 * public void move() {// 我机移动 if (up) y -= speed; if (down) y += speed; if
	 * (left) x -= speed; if (right) x += speed; }
	 */
	// 向上移动
	public void moveUp() {
		y -= 20;
	}
	// 向下移动
	public void moveDown() {
		y += 20;
	}
	// 向左移动
	public void moveLeft() {
		x -= 20;
	}
	// 向右移动
	public void moveRight() {
		x += 20;
	}

	public void press_key(KeyEvent e) {// 按下按键
		switch (e.getKeyCode()) {
		case 37:// 左：37
			left = true;
			break;
		case 38:// 上：38
			up = true;
			break;
		case 39:// 右：39
			right = true;
			break;
		case 40:// 下：40
			down = true;
			break;
		default:
			break;
		}
	}

	public void release_key(KeyEvent e) {// 松开按键
		switch (e.getKeyCode()) {
		case 37:// 左：37
			left = false;
			break;
		case 38:// 上：38
			up = false;
			break;
		case 39:// 右：39
			right = false;
			break;
		case 40:// 下：40
			down = false;
			break;
		default:
			break;
		}
	}

	public boolean Crash(EnemyPlane f) {// 撞机
		  return f.x<this.x+this.width&&f.x+f.width>this.x&&f.y<this.y+this.height&&f.y+f.height>this.y;
	}

	public boolean outOfBounds() {// 判断是否出界
		return false;// 永不越界
	}
}
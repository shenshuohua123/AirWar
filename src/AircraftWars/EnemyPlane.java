package AircraftWars;

import java.util.Random;



public class EnemyPlane extends flyingObject {
	int typeOfPlane;// 敌机类型（1、2、3）
	int speed;// 速度
	int blood;// 敌机血量

	public EnemyPlane(int rankNum) {
		Random rand = new Random();// 随机数类rand
		if(rankNum<=3) {		
			int typeOfPlane = rand.nextInt(3)+1;//0 <= nextInt(n) < n		
			image = APP.getImg("/img/EnemyPlane"+typeOfPlane+".png");					
			speed=2;
			blood=1;		
		}else {
			int typeOfPlane = rand.nextInt(15)+1;//		
			image = APP.getImg("/img/ep"+(typeOfPlane<10?"0":"")+typeOfPlane+".png");					
			speed=2+rankNum;
			blood=1+rankNum;
		}
		width = image.getWidth();
		height = image.getHeight();
		x = rand.nextInt(512-width);
		y = -height;
	}

	public void move() {// 敌机移动
		y += speed;
	}

	public boolean beHit(Bullet bullet) {// 敌机被击中
		  int x = bullet.x;
		  int y = bullet.y;
		  return this.x < x + bullet.width && this.x + this.width > x && this.y < y + bullet.height
		    && this.y + this.height > y;

		 }
	
	public boolean outOfBounds() {// 判断是否出界
		return y > /*窗口高度*/+this.height / 2;
	}
	
}
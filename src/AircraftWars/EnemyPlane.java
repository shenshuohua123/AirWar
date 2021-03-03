package AircraftWars;

import java.util.Random;



public class EnemyPlane extends flyingObject {
	int typeOfPlane;// �л����ͣ�1��2��3��
	int speed;// �ٶ�
	int blood;// �л�Ѫ��

	public EnemyPlane(int rankNum) {
		Random rand = new Random();// �������rand
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

	public void move() {// �л��ƶ�
		y += speed;
	}

	public boolean beHit(Bullet bullet) {// �л�������
		  int x = bullet.x;
		  int y = bullet.y;
		  return this.x < x + bullet.width && this.x + this.width > x && this.y < y + bullet.height
		    && this.y + this.height > y;

		 }
	
	public boolean outOfBounds() {// �ж��Ƿ����
		return y > /*���ڸ߶�*/+this.height / 2;
	}
	
}
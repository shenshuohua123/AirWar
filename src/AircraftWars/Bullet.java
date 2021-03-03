package AircraftWars;

public class Bullet extends flyingObject {
	int speed ;

	public Bullet(int x,int y,int rankNum) {// 将我机x赋给子弹x，我机y+height赋给子弹y
		if(rankNum<=3) {
			speed = 2;
			image= APP.getImg("/img/MyBullet2.png");
			width = image.getWidth()/2;
			height = image.getHeight()/2;
		}
		else {
			speed = 4;
			image = APP.getImg("/img/fire.png");
			width = image.getWidth()/4;
			height = image.getHeight()/4;
		}
		
		this.x=x;
		this.y=y;
	}

	public void move() {
		y -= speed;
	}

}
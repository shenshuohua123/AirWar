package AircraftWars;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

/**
 * ��Ϸ����
 * 
 * @author ��shuohua
 *
 */
/*
 * protected��ͬ�������಻�����ã������Ķ�������
 * ��Ϸ���壺�̳�JPanel����Ҫʵ�ֵĴ󲿷����ݣ� �������屳��ͼ�������л���Ӣ�ۻ����ӵ����󣬴����л��ⵯҩ�⣬��������Ϸ���أ�
 * Ӣ�ۻ���������Ϸ��ʼ�������̣߳��������л��ƶ��������ӵ����ӵ��ƶ����ж��ӵ��Ƿ񵽴�л����жϵл��Ƿ�ײ��Ӣ�ۻ����ػ桢�л��볡��
 * ���幹���������ñ�����ɫ����ʼ��ͼƬ���������������̼�������������������������У������������� ר�û�ͼ���������������������ȵȣ�
 * ��һ�ص������ض��Ǽ򵥵�ͼƬ ���ؿ�����3�ǣ��������л����ӵ�����
 */
public class GameJPanel extends JPanel {
	// ���ùؿ�
	int rankNum = 1;
	// ��־�Ƿ�ͨ��
	boolean passFlag = false;
	// ���屳��ͼ
	BufferedImage background;

	// �����һ�
	MyPlane myPlane = new MyPlane(rankNum);

	// �����л����ڼ�������
	List<EnemyPlane> eps = new ArrayList<EnemyPlane>();//��ʼ����Ϊ10

	// �����һ���ҩ��
	List<Bullet> bts = new ArrayList<Bullet>();

	// �������
	int score = 0;
	// ������Ϸ����
	boolean gameover = false;
	// ������Ϸ��ʼ��ʾ
	boolean gameStart = true;

	/*
	 * ��������һ���̣߳�������Ϸ�����������ƶ�
	 */
	public void action() {
		new Thread() {
			public void run() {
				// ��ѭ��Ϊ��Ϸû����
				while (true) {
					// ���°�����ʼ��Ϸ��
					if (gameStart) {
						epEnter();// �л��볡
						epMove();// �л��ƶ�
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {//�߳��ж��쳣
						e.printStackTrace();
					}

					// ͨ�ػ�����Ϸ����
					if (!passFlag && !gameover && !gameStart) {// ûͨ����û����
						epEnter();// �л��볡
						epMove();// �л��ƶ�
						shoot();// �����ӵ�
						BulletMove();// �ӵ��ƶ�
						shootEp();// �ж��Ƿ���ел�
						hit();// ����Ƿ���ײ��
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// �ػ�
					repaint();
				}
			}
		}.start();
	}

	// ���л��Ƿ�ײ���һ�
	protected void hit() {
		for (int i = 0; i < eps.size(); i++) {
			// ��ȡһ���л�
			EnemyPlane ep = eps.get(i);
			// ����л���Ӣ�ۼ�ײ��
			if (myPlane.Crash(eps.get(i))) {
				// ɾ���л�
				eps.remove(ep);
				// �һ�Ѫ������
				myPlane.lives--;
				// ���ӷ���
				score += 100;
				// ����ͨ��
				if (score % 1000 == 0) {// ͨ��
					rankNum++;
					passFlag = true;
				} else {
					passFlag = false;
				}
				// ��Ӣ�ۻ�Ѫ�����ٵ�0����Ϸ����
				if (myPlane.lives <= 0) {
					gameover = true;
				}
			}
		}
	}

	// ��л�
	protected void shootEp() {
		for (int i = 0; i < bts.size(); i++) {
			Bullet bt = bts.get(i);
			// �ж��ӵ��Ƿ���ел�
			bang(bt);
		}
	}

	// �ж��Ƿ����
	int num = 0;// ���ел�����

	protected void bang(Bullet bt) {
		for (int i = 0; i < eps.size(); i++) {
			EnemyPlane ep = eps.get(i);
			if (ep.beHit(bt)) {
				ep.blood--;
				if (ep.blood <= 0) {
					eps.remove(ep);
					score += 100;// ���ӷ���
					// ���ел���
					num++;
					// ÿ����10���л�����������
					if (num % 5 == 0) {
						myPlane.power++;
					}
					if (score % 1000 == 0) {
						rankNum++;
						passFlag = true;
					} else {
						passFlag = false;
					}
					bts.remove(bt);
				} else {
					shootEp();
				}
				eps.remove(ep);
			}
		}
	}

	// �ӵ��ƶ�
	protected void BulletMove() {
		for (int i = 0; i < bts.size(); i++) {
			Bullet bt = bts.get(i);
			bt.move();
		}
	}

	// �����ӵ�
	int count = 0;// �����ӵ�����ִ�д���

	protected void shoot() {
		count++;
		if (count >= 12) {
			if (myPlane.power == 1) {
				// �����ӵ�
				Bullet bt = new Bullet(myPlane.x + 50, myPlane.y - 20, rankNum);
				// ���ӵ����뵯ҩ��
				bts.add(bt);
			} else if (myPlane.power == 2) {
				// �����ӵ�
				Bullet bt1 = new Bullet(myPlane.x + 75, myPlane.y, rankNum);
				// ���ӵ����뵯ҩ��
				bts.add(bt1);
				// �����ӵ�
				Bullet bt2 = new Bullet(myPlane.x + 15, myPlane.y, rankNum);
				// ���ӵ����뵯ҩ��
				bts.add(bt2);
			} else {
				// �����ӵ�
				Bullet bt1 = new Bullet(myPlane.x + 15, myPlane.y, rankNum);
				// ���ӵ����뵯ҩ��
				bts.add(bt1);
				// �����ӵ�
				Bullet bt2 = new Bullet(myPlane.x + 45, myPlane.y - 20, rankNum);
				// ���ӵ����뵯ҩ��
				bts.add(bt2);
				// �����ӵ�
				Bullet bt3 = new Bullet(myPlane.x + 75, myPlane.y - 20, rankNum);
				// ���ӵ����뵯ҩ��
				bts.add(bt3);
			}
			count = 0;
		}
	}

	// �л��ƶ�
	protected void epMove() {
		for (int i = 0; i < eps.size(); i++) {
			EnemyPlane ep = eps.get(i);
			ep.move();
		}
	}

	// �л��볡���ڴ���ѭ����ִ��20�Σ�����һ���л�
	int count1 = 0;

	protected void epEnter() {
		count1++;
		if (count1 >= 20) {
			EnemyPlane ep = new EnemyPlane(rankNum);
			eps.add(ep);
			count1 = 0;
		}
	}

	public GameJPanel(GameJFrame frame) {
		// ��ʼ��ͼƬ
		background = APP.getImg("/img/background1.png");
		// ���������
		MouseAdapter mouseadapter = new MouseAdapter() {
			// ����Ϸ����ʱ��������Ļ�����¿�ʼ
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gameStart) {
					gameStart = false;
					eps.clear();
				}
				if (gameover || passFlag ) {
					myPlane = new MyPlane(rankNum);
					score = 0;
					if (gameover) {
						rankNum = 0;// �ȼ���Ϊ��
					}
					eps.clear();
					bts.clear();

					if (rankNum <= 3) {// �ؿ�С����
						background = APP.getImg("/img/background1.png");
					} else {// �ؿ�������
						Random rd = new Random();
						int index = rd.nextInt(5) + 1;
						background = APP.getImg("/img/bg" + index + ".jpg");
					}
					// �ػ�
					repaint();
					gameover = false;
					passFlag = false;
				}
			}

		};
		// �������������������
		addMouseListener(mouseadapter);
		addMouseMotionListener(mouseadapter);
		// ʹ�ü��̼�����
		// �������̼�����
		KeyAdapter kd = new KeyAdapter() {
			// ȷ�������ļ����¼�
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == 37) {
					myPlane.moveLeft();
				} else if (keyCode == 38) {
					myPlane.moveUp();
				} else if (keyCode == 40) {
					myPlane.moveDown();
				} else if (keyCode == 39) {
					myPlane.moveRight();
				}
				// �ػ�
				repaint();
			}
		};
		// ��������봰����
		frame.addKeyListener(kd);
	}

	/**
	 * ר�û�ͼ���� Graphics��������ͼ�������ĵĳ�����࣬����Ӧ�ó�������ڸ����豸��ʵ�ֵ�������Լ���Ļ�ϵ�ͼ�� Graphics g ����
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, 512, 768, null);
		if (gameStart) {// ��Ϸ��ʼʱ������ͼ�����л����л��ƶ�
			g.setColor(Color.red);
			g.setFont(new Font("����", Font.BOLD, 20));
			g.drawString("�����Ļ��Ϸ��ʼ", 160, 320);
		} 
		// ��Ϸ�Ѿ���ʼ
		// ��ͼƬ
		// �������꣬������ͼƬ�����Ͻǵ�����
		
		// ��paint�У���ͼ����˳��ģ��Ȼ��Ļᱻ�󻭵ĸ���
		// ��Ӣ�ۻ�
		g.drawImage(myPlane.image, myPlane.x, myPlane.y, myPlane.width, myPlane.height, null);
		// ���л�
		for (int i = 0; i < eps.size(); i++) {
			EnemyPlane ep = eps.get(i);
			g.drawImage(ep.image, ep.x, ep.y, ep.width, ep.height, null);
		}
		// ���ӵ�
		for (int i = 0; i < bts.size(); i++) {
			Bullet bt = bts.get(i);
			g.drawImage(bt.image, bt.x, bt.y, bt.width, bt.height, null);
		}
		// ������
		g.setColor(Color.white);
		g.setFont(new Font("����", Font.BOLD, 20));// ����
		g.drawString("����" + score, 10, 30);
		// ��Ӣ�ۻ�Ѫ��
		for (int i = 0; i < myPlane.lives; i++) {
			g.drawImage(myPlane.image, 350 + i * 35, 5, 30, 30, null);
		}
		// ����Ϸ����ʱ��������Ϸ����
		if (gameover) {
			g.setFont(new Font("����", Font.BOLD, 35));
			g.setColor(Color.red);
			g.drawString("�˼���Ϸ����", 20, 300);
			g.setColor(Color.green);
			g.drawString("skr����������Ļ���¿�ʼ��Ϸ", 160, 320);
			rankNum = 0;
		}
		// �����rankNum�ؿ�
		if (rankNum > 1 && passFlag == true) {
			g.setFont(new Font("����", Font.BOLD, 35));
			g.setColor(Color.red);
			g.drawString("������" + rankNum + "��",160, 320);
		}

	}
}

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
 * 游戏画板
 * 
 * @author 沈shuohua
 *
 */
/*
 * protected不同包非子类不可以用，其他的都可以用
 * 游戏画板：继承JPanel（放要实现的大部分内容） 包含定义背景图，创建敌机，英雄机，子弹对象，创建敌机库弹药库，分数，游戏开关，
 * 英雄机火力，游戏开始方法（线程，方法：敌机移动、发射子弹、子弹移动、判断子弹是否到达敌机、判断敌机是否撞到英雄机、重绘、敌机入场）
 * 画板构造器（设置背景颜色，初始化图片，鼠标监听器，键盘监听器，将适配器加入监听器中，键盘适配器） 专用画图方法（画出背景，分数等等）
 * 第一关到第三关都是简单的图片 当关卡大于3是，场景，敌机，子弹升级
 */
public class GameJPanel extends JPanel {
	// 设置关卡
	int rankNum = 1;
	// 标志是否通关
	boolean passFlag = false;
	// 定义背景图
	BufferedImage background;

	// 创建我机
	MyPlane myPlane = new MyPlane(rankNum);

	// 创建敌机放在集合里面
	List<EnemyPlane> eps = new ArrayList<EnemyPlane>();//初始容量为10

	// 创建我机弹药库
	List<Bullet> bts = new ArrayList<Bullet>();

	// 定义分数
	int score = 0;
	// 设置游戏开关
	boolean gameover = false;
	// 设置游戏开始提示
	boolean gameStart = true;

	/*
	 * 创建启动一个线程，控制游戏场景中物体移动
	 */
	public void action() {
		new Thread() {
			public void run() {
				// 此循环为游戏没结束
				while (true) {
					// 按下按键开始游戏：
					if (gameStart) {
						epEnter();// 敌机入场
						epMove();// 敌机移动
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {//线程中断异常
						e.printStackTrace();
					}

					// 通关或者游戏结束
					if (!passFlag && !gameover && !gameStart) {// 没通关且没结束
						epEnter();// 敌机入场
						epMove();// 敌机移动
						shoot();// 发射子弹
						BulletMove();// 子弹移动
						shootEp();// 判断是否击中敌机
						hit();// 检测是否发生撞击
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// 重绘
					repaint();
				}
			}
		}.start();
	}

	// 检测敌机是否撞到我机
	protected void hit() {
		for (int i = 0; i < eps.size(); i++) {
			// 获取一个敌机
			EnemyPlane ep = eps.get(i);
			// 如果敌机被英雄记撞到
			if (myPlane.Crash(eps.get(i))) {
				// 删除敌机
				eps.remove(ep);
				// 我机血量减少
				myPlane.lives--;
				// 增加分数
				score += 100;
				// 升级通关
				if (score % 1000 == 0) {// 通关
					rankNum++;
					passFlag = true;
				} else {
					passFlag = false;
				}
				// 当英雄机血量减少到0，游戏结束
				if (myPlane.lives <= 0) {
					gameover = true;
				}
			}
		}
	}

	// 射敌机
	protected void shootEp() {
		for (int i = 0; i < bts.size(); i++) {
			Bullet bt = bts.get(i);
			// 判断子弹是否击中敌机
			bang(bt);
		}
	}

	// 判断是否击中
	int num = 0;// 击中敌机数量

	protected void bang(Bullet bt) {
		for (int i = 0; i < eps.size(); i++) {
			EnemyPlane ep = eps.get(i);
			if (ep.beHit(bt)) {
				ep.blood--;
				if (ep.blood <= 0) {
					eps.remove(ep);
					score += 100;// 增加分数
					// 击中敌机数
					num++;
					// 每击中10个敌机，火力增加
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

	// 子弹移动
	protected void BulletMove() {
		for (int i = 0; i < bts.size(); i++) {
			Bullet bt = bts.get(i);
			bt.move();
		}
	}

	// 发射子弹
	int count = 0;// 计算子弹方法执行次数

	protected void shoot() {
		count++;
		if (count >= 12) {
			if (myPlane.power == 1) {
				// 创建子弹
				Bullet bt = new Bullet(myPlane.x + 50, myPlane.y - 20, rankNum);
				// 将子弹加入弹药库
				bts.add(bt);
			} else if (myPlane.power == 2) {
				// 创建子弹
				Bullet bt1 = new Bullet(myPlane.x + 75, myPlane.y, rankNum);
				// 将子弹加入弹药库
				bts.add(bt1);
				// 创建子弹
				Bullet bt2 = new Bullet(myPlane.x + 15, myPlane.y, rankNum);
				// 将子弹加入弹药库
				bts.add(bt2);
			} else {
				// 创建子弹
				Bullet bt1 = new Bullet(myPlane.x + 15, myPlane.y, rankNum);
				// 将子弹加入弹药库
				bts.add(bt1);
				// 创建子弹
				Bullet bt2 = new Bullet(myPlane.x + 45, myPlane.y - 20, rankNum);
				// 将子弹加入弹药库
				bts.add(bt2);
				// 创建子弹
				Bullet bt3 = new Bullet(myPlane.x + 75, myPlane.y - 20, rankNum);
				// 将子弹加入弹药库
				bts.add(bt3);
			}
			count = 0;
		}
	}

	// 敌机移动
	protected void epMove() {
		for (int i = 0; i < eps.size(); i++) {
			EnemyPlane ep = eps.get(i);
			ep.move();
		}
	}

	// 敌机入场，在此死循环中执行20次，创建一个敌机
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
		// 初始化图片
		background = APP.getImg("/img/background1.png");
		// 鼠标适配器
		MouseAdapter mouseadapter = new MouseAdapter() {
			// 当游戏结束时，点下屏幕则重新开始
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
						rankNum = 0;// 等级变为零
					}
					eps.clear();
					bts.clear();

					if (rankNum <= 3) {// 关卡小于三
						background = APP.getImg("/img/background1.png");
					} else {// 关卡大于三
						Random rd = new Random();
						int index = rd.nextInt(5) + 1;
						background = APP.getImg("/img/bg" + index + ".jpg");
					}
					// 重绘
					repaint();
					gameover = false;
					passFlag = false;
				}
			}

		};
		// 将适配器加入监听器中
		addMouseListener(mouseadapter);
		addMouseMotionListener(mouseadapter);
		// 使用键盘监听器
		// 创建键盘监听器
		KeyAdapter kd = new KeyAdapter() {
			// 确定监听的键盘事件
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
				// 重绘
				repaint();
			}
		};
		// 将画板加入窗体中
		frame.addKeyListener(kd);
	}

	/**
	 * 专用画图方法 Graphics类是所有图形上下文的抽象基类，允许应用程序绘制在各种设备上实现的组件，以及屏幕上的图像 Graphics g 画笔
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, 512, 768, null);
		if (gameStart) {// 游戏开始时画背景图，画敌机，敌机移动
			g.setColor(Color.red);
			g.setFont(new Font("楷体", Font.BOLD, 20));
			g.drawString("点击屏幕游戏开始", 160, 320);
		} 
		// 游戏已经开始
		// 画图片
		// 横纵坐标，是设置图片在左上角的坐标
		
		// 在paint中，画图是有顺序的，先画的会被后画的覆盖
		// 画英雄机
		g.drawImage(myPlane.image, myPlane.x, myPlane.y, myPlane.width, myPlane.height, null);
		// 画敌机
		for (int i = 0; i < eps.size(); i++) {
			EnemyPlane ep = eps.get(i);
			g.drawImage(ep.image, ep.x, ep.y, ep.width, ep.height, null);
		}
		// 画子弹
		for (int i = 0; i < bts.size(); i++) {
			Bullet bt = bts.get(i);
			g.drawImage(bt.image, bt.x, bt.y, bt.width, bt.height, null);
		}
		// 画分数
		g.setColor(Color.white);
		g.setFont(new Font("楷体", Font.BOLD, 20));// 字体
		g.drawString("分数" + score, 10, 30);
		// 画英雄机血量
		for (int i = 0; i < myPlane.lives; i++) {
			g.drawImage(myPlane.image, 350 + i * 35, 5, 30, 30, null);
		}
		// 当游戏结束时，画出游戏结束
		if (gameover) {
			g.setFont(new Font("楷体", Font.BOLD, 35));
			g.setColor(Color.red);
			g.drawString("菜鸡游戏结束", 20, 300);
			g.setColor(Color.green);
			g.drawString("skr提醒你点击屏幕重新开始游戏", 160, 320);
			rankNum = 0;
		}
		// 进入第rankNum关卡
		if (rankNum > 1 && passFlag == true) {
			g.setFont(new Font("楷体", Font.BOLD, 35));
			g.setColor(Color.red);
			g.drawString("请进入第" + rankNum + "关",160, 320);
		}

	}
}

package AircraftWars;

import javax.swing.JFrame;
/*
游戏窗体构造器（设置标题，设置大小高宽，设置窗体位置居中，坐标的原点为屏幕左上角，
设置不允许玩家改变界面大小，设置默认的关闭选项）
创建窗体对象，面板对象，调用游戏开始的方法，将面板加入窗体，显示窗体
*/
/**
 * 游戏窗体
 * @author 沈shuohua
 *	
 *
 */
public class GameJFrame extends JFrame{
	public GameJFrame() {
		//设置标题
		setTitle("飞机大作战");
		//设置大小
		setSize(521, 768);
		//设置窗体相对于指定组件的位置，设置为null，则此窗口将置于屏幕中央
		setLocationRelativeTo(null);
		setResizable(false);
		//设置默认的关闭选项
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		//创建窗口对象
		GameJFrame frame = new GameJFrame();
		
		//创建画板对象
		GameJPanel panel = new GameJPanel(frame);
		
		//通过panel调用游戏开始函数
		panel.action();
		//将画板加入窗体
		frame.add(panel);	
		//显示窗体
		frame.setVisible(true);
	}
}

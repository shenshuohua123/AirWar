package AircraftWars;

import javax.swing.JFrame;
/*
��Ϸ���幹���������ñ��⣬���ô�С�߿����ô���λ�þ��У������ԭ��Ϊ��Ļ���Ͻǣ�
���ò�������Ҹı�����С������Ĭ�ϵĹر�ѡ�
����������������󣬵�����Ϸ��ʼ�ķ������������봰�壬��ʾ����
*/
/**
 * ��Ϸ����
 * @author ��shuohua
 *	
 *
 */
public class GameJFrame extends JFrame{
	public GameJFrame() {
		//���ñ���
		setTitle("�ɻ�����ս");
		//���ô�С
		setSize(521, 768);
		//���ô��������ָ�������λ�ã�����Ϊnull����˴��ڽ�������Ļ����
		setLocationRelativeTo(null);
		setResizable(false);
		//����Ĭ�ϵĹر�ѡ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		//�������ڶ���
		GameJFrame frame = new GameJFrame();
		
		//�����������
		GameJPanel panel = new GameJPanel(frame);
		
		//ͨ��panel������Ϸ��ʼ����
		panel.action();
		//��������봰��
		frame.add(panel);	
		//��ʾ����
		frame.setVisible(true);
	}
}

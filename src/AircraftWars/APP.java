package AircraftWars;
/**
 * ����ͼƬ�Ĺ���
 * @author ��shuohua
 *
 */

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class APP {
	public static BufferedImage getImg(String path) {
		try {			
			BufferedImage img = ImageIO.read(APP.class.getResource(path));
			return img;
		}catch(IOException e) {
			e.printStackTrace();
		}		
		return null;		
	}
}

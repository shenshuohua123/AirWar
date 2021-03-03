package AircraftWars;
/**
 * 处理图片的工具
 * @author 沈shuohua
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

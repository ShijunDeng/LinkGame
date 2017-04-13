package junit.test;

import java.io.File;

import org.junit.Test;

import cn.fouad.commons.Point;
import cn.fouad.utils.ImageUtils;

public class MyTest {

	@Test
	public void testImageUtils(){
		try {
			//System.out.println(ImageUtils.getImages(new File(ImageUtils.IMAGEFOLDERNAME+File.separator+ImageUtils.PIECEIMAGEFOLDERNAME), ImageUtils.IMAGETYPE).size());
			//System.out.println(ImageUtils.getBackgroundImageIcon().getIconHeight());
			ImageUtils.randomImages(ImageUtils.getImages(new File(ImageUtils.IMAGEFOLDERNAME+File.separator+ImageUtils.PIECEIMAGEFOLDERNAME), ImageUtils.IMAGETYPE));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPoint(){
		Point p1=new Point(3,3);
		Point p2=new Point(2,2);
		System.out.println(p1.isLeftUp (p2));
	}
	
			
}

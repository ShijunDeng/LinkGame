package cn.fouad.utils;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import cn.fouad.exception.GameException;

/**
 * ImageUtils
 * 
 */
public class ImageUtils {
	public static String IMAGETYPE = ".gif";
	public static String IMAGEFOLDERNAME = "images";
	public static String PIECEIMAGEFOLDERNAME = "pieces";

	public static boolean eauals(BufferedImage imagea, BufferedImage imageb) {
		return imagea.equals(imageb);
	}

	public static List<BufferedImage> getImages(File folder, String suffix)
			throws IOException {
		File[] items = folder.listFiles();
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		for (File item : items) {
			if (item.getName().endsWith(suffix))
				result.add(ImageIO.read(item));
		}
		return result;
	}

	
	public static List<BufferedImage> randomImages(List<BufferedImage> orgImages) {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		List<Integer> randomIndex = new ArrayList<Integer>();
		Random random = new Random();
		for (int i = 0; i < orgImages.size(); i++) {
			Integer index = random.nextInt(orgImages.size());
			if (randomIndex.contains(index)) {
				i--;
				continue;
			} else {
				randomIndex.add(index);
			}
		}
		for (int i = 0; i < randomIndex.size(); i++) {
			result.add(orgImages.get(randomIndex.get(i)));
		}
		return result;
	}

	
	public static List<BufferedImage> getRandomImages(
			List<BufferedImage> orgImages, int size) {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			result.add(orgImages.get(random.nextInt(orgImages.size())));
		}
		return result;
	}

	
	public static List<BufferedImage> getPieceImages(int size)
			throws GameException {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		if (size % 2 != 0) {
			throw new GameException("size Error£º" + size);
		}
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		try {
			images = getImages(new File(IMAGEFOLDERNAME + File.separator
					+ PIECEIMAGEFOLDERNAME), IMAGETYPE);
			images = getRandomImages(images, size / 2);
			result.addAll(images);
			result.addAll(randomImages(images));
		} catch (IOException e) {
			throw new GameException("read images error:" + e.getMessage());
		}
		return result;
	}

	public static BufferedImage getImage(String pathname) throws IOException {
		return ImageIO.read(new File(pathname));
	}

	public static ImageIcon getBackgroundImageIcon() throws GameException {
		ImageIcon imageIcon = null;
		try {
			imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
					IMAGEFOLDERNAME + File.separator + "background_image"
							+ IMAGETYPE));
		} catch (Exception e) {
			throw new GameException("Load image resources error!");
		}
		return imageIcon;
	}

}

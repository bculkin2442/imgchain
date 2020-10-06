package bjc.imgchain.utils;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import bjc.imgchain.ImgChain;

/**
 * Utility stuff for ImgChain.
 * 
 * @author Ben Culkin
 *
 */
public class Utils {
	/**
	 * Converts an image into a buffered image.
	 * 
	 * @param img
	 *            The image to convert to a buffered image.
	 * 
	 * @return The buffered image. Note that if img is already a buffered image, it
	 *         will return the same argument.
	 */
	public static BufferedImage toBuffered(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		/*
		 * Create a buffered image with transparency
		 */
		BufferedImage bufimg = new BufferedImage(img.getWidth(null), img.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		/*
		 * Draw the image on to the buffered image
		 */
		Graphics2D graph = bufimg.createGraphics();
		graph.drawImage(img, 0, 0, null);
		graph.dispose();

		/*
		 * Return the buffered image
		 */
		return bufimg;
	}

	/**
	 * Convert a pixel to an ARGB quad array.
	 * 
	 * @param rbg
	 *            The pixel to convert.
	 * 
	 * @return The pixel as an ARGB array.
	 */
	public static int[] toARGBQuad(int rbg) {
		return new int[] {
				(rbg >> 24) & 0xff, (rbg >> 16) & 0xff, (rbg >> 8) & 0xff, rbg & 0xff
		};
	}

	/**
	 * Convert an ARGB quad array to a pixel.
	 * 
	 * @param argb
	 *             An ARGB array.
	 * 
	 * @return The pixel
	 */
	public static int fromARGBQuad(int[] argb) {
		return (argb[0] << 24) | (argb[1] << 16) | (argb[2] << 8) | argb[3];
	}

	/**
	 * Pads an array on both sides.
	 * 
	 * @param arr
	 *                  The initial array.
	 * @param padWith
	 *                  The element to pad with.
	 * @param numOfPads
	 *                  The number of pads to apply.
	 * 
	 * @return The padded array.
	 */
	public static int[][] padarray(int[][] arr, int padWith, int numOfPads) {
		int[][] temp = new int[arr.length + numOfPads * 2][arr[0].length + numOfPads * 2];

		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				temp[i][j] = padWith;
			}
		}

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				temp[i + numOfPads][j + numOfPads] = arr[i][j];
			}
		}
		return temp;
	}

	/**
	 * Displays an image.
	 * 
	 * @param processed
	 *                  The image to display.
	 * @param title
	 *                  The title for the image.
	 */
	public static void displayImage(Image processed, String title) {
		BufferedImage resimg = toBuffered(processed);

		JInternalFrame displayFrame = new JInternalFrame(title, false, true, true);
		displayFrame.setSize(resimg.getWidth(), resimg.getHeight());
		displayFrame.setLayout(new GridLayout(1, 1));

		JLabel displayLabel = new JLabel(new ImageIcon(resimg));

		displayFrame.add(displayLabel);

		ImgChain.chan.desktop.add(displayFrame);
		displayFrame.setVisible(true);
	}
}

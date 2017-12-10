package com.erui.comm.util.image;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.IOException;

public class SecurityCode {
	/**
	 * 准备生成的随机字符
	 */
	private String base = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	public static final int WIDTH = 120;
	public static final int HEIGHT = 50;
	

	/**
	 * 设置背景色      
	 * 
	 * @param g
	 *                 
	 */
	private void setBackGround(Graphics g) {
		// 设置颜色
		// g.setColor(Color.WHITE);
		// 填充区域
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(new GradientPaint(0, 0, Color.WHITE, 0, HEIGHT, Color.GRAY));
		g2.fillRect(0, 0, WIDTH, HEIGHT);
	}

	/**
	 * 设置边框
	 * 
	 * @param g
	 */
	private void setBorder(Graphics g) {
		// 设置边框颜色
//		g.setColor(Color.BLACK);
		// 边框区域
//		g.drawRect(1, 1, WIDTH - 2, HEIGHT - 10);
	}

	/**
	 * 画随机线条      
	 * 
	 * @param g
	 *                 
	 */
	private void drawRandomLine(Graphics g) {
		Random random = new Random();
		// 设置线条个数并画线
		for (int i = 0; i < 20; i++) {
			/**
			 * 设置干扰线长度
			 */
			int x1 = random.nextInt(WIDTH);
			int y1 = random.nextInt(HEIGHT);
			int x2 = random.nextInt(WIDTH);
			int y2 = random.nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
			
			/**
			 * 设置干扰线颜色
			 */
			setColor(g, random);
		}
	}

	private void setColor(Graphics g, Random random) {
		int red = random.nextInt(255);
		int green = random.nextInt(255);
		int blue = random.nextInt(255);
		g.setColor(new Color(red, green, blue));
	}

	/**
	 * 画随机汉字      
	 * 
	 * @param g
	 *                 
	 * @return      
	 */
	private String drawRandomNum(Graphics2D g) {
		Random random = new Random();
		StringBuffer str = new StringBuffer();
		// 设置字体
		g.setFont(new Font("宋体", Font.BOLD, 25));
		int x = 3;
		// 控制字数
		for (int i = 0; i < 4; i++) {
			// 设置字体旋转角度
			int degree = new Random().nextInt() % 30;
			// 截取汉字
			String ch = base.charAt(new Random().nextInt(base.length())) + "";
			/*if (i == 0 || i == 2) {
				// 设置颜色
				g.setColor(Color.GRAY);
			} else {
				// 设置颜色
				g.setColor(Color.RED);
			}*/
			str.append(ch);
			
			/**
			 * 设置验证码的颜色
			 */
			setColor(g, random);
			
			// 正向角度
			g.rotate(degree * Math.PI / 180, x, 20);
			g.drawString(ch, x, 35);
			// 反向角度
			g.rotate(-degree * Math.PI / 180, x, 20);
			x += 30;
		}
		return str.toString();
	}

	private void setColor(Graphics2D g, Random random) {
		int red = random.nextInt(255);
		int green = random.nextInt(255);
		int blue = random.nextInt(255);
		g.setColor(new Color(red, green, blue));
	}

	public String getSCode(OutputStream out) {
		BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		// 获得画布
		Graphics g = bi.getGraphics();
		// 设置背影色
		setBackGround(g);
		// 设置边框
		setBorder(g);
		// 画干扰线
		drawRandomLine(g);
		// 写随机数
		String random = drawRandomNum((Graphics2D) g);
		g.dispose();
		bi.flush();
		try {
			ImageIO.write(bi, "JPEG", out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return random;
	}
}

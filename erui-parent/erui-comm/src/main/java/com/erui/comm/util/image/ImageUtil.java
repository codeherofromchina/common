package com.erui.comm.util.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageUtil {
	/**
     * 截图
     * @param srcPath
     * @param startX
     * @param startY
     * @param width
     * @param height
     */
    public static void cut(String srcPath,String newPath,int startX,int startY,int width,int height){
        try {
            BufferedImage bufImg = ImageIO.read(new File(srcPath));
            BufferedImage subImg = bufImg.getSubimage(startX, startY, width, height);
            String lastf = srcPath.substring(srcPath.lastIndexOf(".")+1);
            save(subImg,lastf,newPath,width,height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存截图。
     * @param imgType
     */
    public static void save(BufferedImage subImg,String imgType,String path,int width,int height){
        try {/**压缩图片为指定尺寸*/
            if(subImg.getWidth()!=width || subImg.getHeight()!=height){
                BufferedImage tempImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
                tempImg.getGraphics().drawImage(subImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,null);
                ImageIO.write(tempImg, imgType, new File(path));
            }else{
                ImageIO.write(subImg,imgType,new File(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 得到图片的尺寸
     * @author Kongliang
     * @date 2015年9月11日 下午2:49:26
     * @version 0.0.1
     */
    public static Map<String,Object> getImgWH(String imgUrl) throws FileNotFoundException, IOException {
    	  File picture = new File(imgUrl);
    	  BufferedImage sourceImg =ImageIO.read(new FileInputStream(picture));
    	  Map<String, Object> mapWH=new HashMap<String, Object>();
    	  mapWH.put("width",sourceImg.getWidth());
    	  mapWH.put("height",sourceImg.getHeight());
    	  return mapWH;
    }
}

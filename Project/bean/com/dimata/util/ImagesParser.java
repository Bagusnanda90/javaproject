/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Satrya Ramayu
 */
public class ImagesParser {
    /**
     * Method Untuk Memanggil Image/gambar
     * @param ref
     * @return 
     */
    public static BufferedImage loadImage(String ref) {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(new File(ref));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimg;
    }
    /**
     * Method untuk Resize Image
     * @param img
     * @param newW
     * @param newH
     * @return 
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(newW, newH,img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
    
     /**
     * Method Untuk Menampilkan Gambar Pada JLabel
     */
    public void tampilkanImage(JLabel IconHarisma,JLabel IconClient,String urlGambarHarisma,String urlGambarClient) {
        
        if(urlGambarHarisma!=null && urlGambarHarisma.length()>0){
            BufferedImage loadImg = loadImage(urlGambarHarisma);
            ImageIcon imageIcon = new ImageIcon(resize(loadImg,
            IconHarisma.getWidth(), IconHarisma.getHeight()));
            IconHarisma.setIcon(imageIcon);
        }
        if(urlGambarClient!=null && urlGambarClient.length()>0){
            BufferedImage loadImgClient = loadImage(urlGambarClient);
            ImageIcon imageIconClient = new ImageIcon(resize(loadImgClient,
            IconClient.getWidth(), IconClient.getHeight()));
            IconClient.setIcon(imageIconClient);
        }
    }
    
    public void tampilkanImage(JLabel LabelLogoDimata,String urlGambar) {
       if(urlGambar!=null && urlGambar.length()>0){ 
        BufferedImage loadImg = loadImage(urlGambar);
        ImageIcon imageIcon = new ImageIcon(resize(loadImg,
        LabelLogoDimata.getWidth(), LabelLogoDimata.getHeight()));
        LabelLogoDimata.setIcon(imageIcon);
       } 
    }
    
    /**
     * Menampilkan images  JButton 
     * create by satrya 2014-02-12
     * @param BtnActionLogin
     * @param urlGambar 
     */
    public void tampilkanImageJButton(JButton BtnActionLogin,String urlGambar) {
      if(urlGambar!=null && urlGambar.length()>0){
        BufferedImage loadImg = loadImage(urlGambar);
        ImageIcon imageIcon = new ImageIcon(resize(loadImg,
        BtnActionLogin.getWidth(), BtnActionLogin.getHeight()));
        BtnActionLogin.setIcon(imageIcon);
      }   
    }
}

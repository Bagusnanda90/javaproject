/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

/**
 *
 * @author GUSWIK
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.PanelUI;


/**
 *
 * @author Jie
 */
public class LaunchProgressBar extends javax.swing.JDialog{

    /** Creates new form ProgressBar */
    public LaunchProgressBar() {
        setUndecorated(true);
        initComponents();
        splashPanel.setUI(new BackgroundUI());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width-getWidth())/2;
        int y = (dim.height-getHeight())/2;
        setLocation(x, y);
       progressBar.setStringPainted(true);
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        LaunchProgressBar pb = new LaunchProgressBar();
        pb.setVisible(true);
        for(int i=0;i<=100;i++){
            try {
                pb.getProgressBar().setValue(i);
                Thread.sleep(40); //lamanya progressBar berjalan
            } catch (InterruptedException ex) {
                Logger.getLogger(LaunchProgressBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       pb.dispose();
        new Login().setVisible(true);
    }

    // Variables declaration - do not modify
    public javax.swing.JProgressBar progressBar;
    private javax.swing.JPanel splashPanel;
    // End of variables declaration

  
class BackgroundUI extends PanelUI {
    ImageIcon background = new ImageIcon(getClass().getResource("image/dimata_system_logo.png"));
    @Override
    public void paint(Graphics g, JComponent c) {
         g.drawImage(background.getImage(), 0, 0, null);
    }
}

    private void initComponents() {//GEN-BEGIN:initComponents
        setLayout(new java.awt.BorderLayout());

    }//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

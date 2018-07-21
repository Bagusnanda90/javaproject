/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Dimata 007
 */
public class ScrolledPane extends JFrame {

    private JScrollPane scrollPane;

    public ScrolledPane() {
        setTitle("Scrolling Pane Application");
        setSize(300, 200);
        setBackground(Color.gray);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

       
        JLabel label = new JLabel();
        String dxd = "";
        for (int x = 0; x < 200; x++) {
            dxd = dxd + "yoi <br>";
        }
     
        label.setText("<html>" + dxd + "</html>");
        // Create a tabbed pane
        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(label);
        topPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
        // Create an instance of the test application
        ScrolledPane mainFrame = new ScrolledPane();
        mainFrame.setVisible(true);
    }
}

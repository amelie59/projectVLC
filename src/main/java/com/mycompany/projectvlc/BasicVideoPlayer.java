/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projectvlc;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Gaëlle
 */
public class BasicVideoPlayer {
    
     /**
     *
     * @param args
     */
    public static void main( final String[] args )
    {
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new BasicVideoPlayer(args);
                }
            });
    }
     
      private BasicVideoPlayer(String[] args) {
            JFrame frame = new JFrame("vlcj Tutorial");
            frame.setLocation(100, 100);
            frame.setSize(1050, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
}

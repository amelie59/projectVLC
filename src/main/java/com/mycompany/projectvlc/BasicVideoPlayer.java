/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projectvlc;

import com.mycompany.projectvlc.NewJFrame;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author Gaëlle
 */
public class BasicVideoPlayer {

    /**
     *
     * @param args
     */
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public static void main(final String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                new BasicVideoPlayer(args);
            }
        });
    }

    private BasicVideoPlayer(String[] args) {


       // JFrame frame = new JFrame("vlcj Tutorial");
        
        NewJFrame frame = new NewJFrame();
        JPanel pnVideo = frame.getPanelVideo();
        JScrollPane pnPlaylist = frame.getPanelPlaylist();
        JPanel pnBtVideo = frame.getPanelBouton();
        JPanel pnBtPlaylist = frame.getPanelBtPlaylist();
       //frame.getContentPane().add(panelVideo);

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        
        pnVideo.add(mediaPlayerComponent, BorderLayout.CENTER);
       //frame.setContentPane(mediaPlayerComponent);
        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mediaPlayerComponent.getMediaPlayer().playMedia("D:\\Perso\\Video\\Greys.Anatomy.S10E11.PROPER.VOSTFR.HDTV.XviD-ATeam\\GAS10e11.avi");
    }
}

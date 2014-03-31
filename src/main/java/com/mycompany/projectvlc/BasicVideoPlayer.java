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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        
        JButton btPlayPause = frame.getPlay();
        JButton btStop = frame.getStop();
        JButton btRejouer = frame.getRejouer();
        JButton btMute = frame.getMute();
        
        btPlayPause.addActionListener(new ButtonPlayPauseListener(btPlayPause));
        btStop.addActionListener(new ButtonStopListener());
        btRejouer.addActionListener(new ButtonRejouerListener());
        btMute.addActionListener(new ButtonMuteListener(btMute));
       //frame.getContentPane().add(panelVideo);

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        
        pnVideo.add(mediaPlayerComponent, BorderLayout.CENTER);
       //frame.setContentPane(mediaPlayerComponent);
        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mediaPlayerComponent.getMediaPlayer().prepareMedia("/home/isen/h264_720p_hp_5.1_6mbps_ac3_planet.mp4");
        
    }

    class ButtonMuteListener implements ActionListener 
    {
        
        JButton bt = new JButton();

        public ButtonMuteListener(JButton button) {
            this.bt = button;
        }

        public void actionPerformed(ActionEvent e) {
            if (mediaPlayerComponent.getMediaPlayer().isMute())
            {
                mediaPlayerComponent.getMediaPlayer().mute(false);
                bt.setText("Mute");
            }
            else
            {
                mediaPlayerComponent.getMediaPlayer().mute(true);
                bt.setText("no Mute");
            }
        }
    }

    class ButtonRejouerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            mediaPlayerComponent.getMediaPlayer().stop();
            mediaPlayerComponent.getMediaPlayer().start();
        }

        
    }

    class ButtonStopListener implements ActionListener 
    {

        public void actionPerformed(ActionEvent e) 
        {
            mediaPlayerComponent.getMediaPlayer().stop();
        }

    }

    

    class ButtonPlayPauseListener implements ActionListener
    {
        JButton bt = new JButton();

        public ButtonPlayPauseListener(JButton button) {
            this.bt = button;
        }
        
        
        
        public void actionPerformed(ActionEvent e) 
        {
            if(mediaPlayerComponent.getMediaPlayer().isPlaying())
            {
                mediaPlayerComponent.getMediaPlayer().pause();
                bt.setText("Play");
            }
            else
            {
                mediaPlayerComponent.getMediaPlayer().play();
                bt.setText("Pause");
                
            }
        }
    }
}

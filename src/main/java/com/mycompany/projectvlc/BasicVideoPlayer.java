/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projectvlc;

import com.mycompany.projectvlc.NewJFrame;
import com.sun.jmx.snmp.BerDecoder;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.manager.MediaManager;
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
    
    private final NewJFrame frame = new NewJFrame();
    private final JPanel pnVideo = frame.getPanelVideo();
    private final JScrollPane pnPlaylist = frame.getPanelPlaylist();
    private final JPanel pnBtVideo = frame.getPanelBouton();
    private final JPanel pnBtPlaylist = frame.getPanelBtPlaylist();
        
    private final JButton btPlayPause = frame.getPlay();
    private final JButton btStop = frame.getStop();
    private final JButton btRejouer = frame.getRejouer();
    private final JButton btMute = frame.getMute();
    private final JSlider slider = frame.getSlider();
    private final JSlider volumeSlider = frame.getVolumeSlider();
    
    private int positionInMovie = 0;
    private Boolean playing = false;
    private MediaPlayer player;
    private int volume = 50;
    

    class ThreadSlider extends Thread {
        private Thread t;
        private String threadName;
        
        
        public ThreadSlider(String threadName) {
            this.threadName = threadName;
            System.out.println("Creating : " + this.threadName);
        }
        
        public void start()
        {
            System.out.println("Starting : " + this.threadName);
            if (t == null)
            {
                t = new Thread (this, this.threadName);
                t.start();
            }
        }
        
        public void run ()
        {
            System.out.println("Running : " + this.threadName);
           
            int cursorPosition = 0;
            
            mediaPlayerComponent.getMediaPlayer().prepareMedia("/home/isen/h264_720p_hp_5.1_6mbps_ac3_planet.mp4");
            
            slider.setMinimum(0);
            slider.setMaximum(500);
            slider.setMajorTickSpacing(500);
            slider.setMinorTickSpacing(20);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setValue(0);
            
            
            volumeSlider.setMinimum(0);
            volumeSlider.setMaximum(100);
            volumeSlider.setMajorTickSpacing(100);
            volumeSlider.setMinorTickSpacing(5);
            volumeSlider.setPaintTicks(true);
            volumeSlider.setPaintLabels(true);
            volumeSlider.setValue(volume);
            //volumeSlider.setSize(5, 25);
            
            player.setVolume(volume);
            
            
              /*  volumeSlider.addChangeListener(new ChangeListener(){
                       @Override
                    public void stateChanged(ChangeEvent arg0)
                    {
                        volume = volumeSlider.getValue();

                        volumeSlider.setValue(volume);
                        player.setVolume(volume);
                    }
                }
                );*/
            
            
           // slider.addChangeListener(new ChangeListener(){
           //     @Override
           //     public void stateChanged(ChangeEvent arg0)
           //     {
                    positionInMovie = slider.getValue();
                    
                    slider.setValue(positionInMovie);
                    player.setTime((long) positionInMovie);
            //    }
            //}
            //);
            cursorPosition = slider.getValue();
            System.out.println(cursorPosition);
            
            while(true)
            {
                    if((slider.getValue() != player.getTime()/1000) && (slider.getValue()-1 != player.getTime()/1000)
                            && (slider.getValue()+1 != player.getTime()/1000))
                    {
                    
                        slider.setValue(slider.getValue());
                        player.setTime((long) (slider.getValue()*1000));
                    }
                
                if (playing)
                {
                    moveCursor(slider);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BasicVideoPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    
    class ThreadPlaylist extends Thread {
        private Thread t;
        private String threadName;
        
        
        public ThreadPlaylist(String threadName) {
            this.threadName = threadName;
            System.out.println("Creating : " + this.threadName);
        }
        
        public void start()
        {
            System.out.println("Starting : " + this.threadName);
            if (t == null)
            {
                t = new Thread (this, this.threadName);
                t.start();
            }
        }
        
        public void run ()
        {
            System.out.println("Running : " + this.threadName);
           
            
            
        }
    }
    
    
    
    class ThreadVideo extends Thread {
        private Thread t;
        private String threadName;
        String[] args ;
        public JSlider slider = null;

        public ThreadVideo(String threadName, String[] args) {
            this.threadName = threadName;
            this.args = args;
            System.out.println("Creating : " + this.threadName);
        }
        
        public JSlider getSlider ()
        {
            return slider;
        }
        
        public void start()
        {
            System.out.println("Starting : " + this.threadName);
            if (t == null)
            {
                t = new Thread (this, this.threadName);
                t.start();
            }
        }
        
        public void run()
        {
            System.out.println("Running : " + this.threadName);
            int cursorPosition = 0;
        
        
        
        btPlayPause.addActionListener(new ButtonPlayPauseListener(btPlayPause));
        btStop.addActionListener(new ButtonStopListener(btPlayPause));
        btRejouer.addActionListener(new ButtonRejouerListener());
        btMute.addActionListener(new ButtonMuteListener(btMute));
        
        
       //frame.getContentPane().add(panelVideo);

        
        
        pnVideo.add(mediaPlayerComponent, BorderLayout.CENTER);
       //frame.setContentPane(mediaPlayerComponent);
        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        
        
        
        
        }

        
        
    }
    
    
    public static void main(final String[] args) {
        
            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
            SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                /*ThreadVideo threadVideo = new ThreadVideo("Thread Video", args);
                threadVideo.start();*/
                new BasicVideoPlayer(args);
            }
        });
        
        
    }


    private BasicVideoPlayer(String[] args) {

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        
        mediaPlayerComponent.getMediaPlayer().prepareMedia("/home/isen/h264_720p_hp_5.1_6mbps_ac3_planet.mp4");
        player = mediaPlayerComponent.getMediaPlayer();
        
        
        ThreadVideo threadVideo = new ThreadVideo("Thread Video", args);
                threadVideo.start();
                
                ThreadSlider threadSlide = new ThreadSlider ("Thread Slide");
                threadSlide.start();
                
                ThreadPlaylist threadPlaylist = new ThreadPlaylist("Thread Playlist");
                threadPlaylist.start();

       // JFrame frame = new JFrame("vlcj Tutorial");
        
        /*int cursorPosition = 0;
        
        
        NewJFrame frame = new NewJFrame();
        JPanel pnVideo = frame.getPanelVideo();
        JScrollPane pnPlaylist = frame.getPanelPlaylist();
        JPanel pnBtVideo = frame.getPanelBouton();
        JPanel pnBtPlaylist = frame.getPanelBtPlaylist();
        
        JButton btPlayPause = frame.getPlay();
        JButton btStop = frame.getStop();
        JButton btRejouer = frame.getRejouer();
        JButton btMute = frame.getMute();
        JButton btFullScreen = frame.getFullScreen();
        JSlider slider = frame.getSlider();
        
        btPlayPause.addActionListener(new ButtonPlayPauseListener(btPlayPause, slider));
        btStop.addActionListener(new ButtonStopListener(btPlayPause));
        btRejouer.addActionListener(new ButtonRejouerListener());
        btMute.addActionListener(new ButtonMuteListener(btMute));
        btFullScreen.addActionListener(new ButtonFullCreenListener());
        
        
       //frame.getContentPane().add(panelVideo);

        
        
        pnVideo.add(mediaPlayerComponent, BorderLayout.CENTER);
       //frame.setContentPane(mediaPlayerComponent);
        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mediaPlayerComponent.getMediaPlayer().prepareMedia("/home/isen/h264_720p_hp_5.1_6mbps_ac3_planet.mp4");
        
        
        slider.setMinimum(0);
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue(0);
        cursorPosition = slider.getValue();
        System.out.println(cursorPosition);
        
              */
        
        
    }
    
    public void moveCursor(JSlider slide)
    {
        int value = slide.getValue();
        System.out.println(value);
        
        
        System.out.println(positionInMovie);
        
        //while (playing)
        //{
            //valueFloat = mediaPlayerComponent.getMediaPlayer().getPosition() * 100;
            //value = (int) valueFloat;
            positionInMovie = (int) player.getTime()/1000;
            //positionInMovie ++;
            System.out.println(positionInMovie);
            slide.removeChangeListener(null);
            slide.setValue(positionInMovie);
            slide.addChangeListener(null);
            
            
        //}
    }

    

    class ButtonMuteListener implements ActionListener 
    {
        
        JButton bt = new JButton();

        public ButtonMuteListener(JButton button) {
            this.bt = button;
        }

        public void actionPerformed(ActionEvent e) {
            if (player.isMute())
            {
                player.mute(false);
                bt.setText("Mute");
            }
            else
            {
                player.mute(true);
                bt.setText("no Mute");
            }
        }
    }

    class ButtonRejouerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            player.stop();
            player.start();
        }

        
    }

    class ButtonStopListener implements ActionListener 
    {
        JButton bt = new JButton ();

        private ButtonStopListener(JButton btPlayPause) {
            this.bt = btPlayPause;
        }

        public void actionPerformed(ActionEvent e) 
        {
            player.stop();
            bt.setText("Play");
            playing = false;
            slider.setValue(0);
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
            if(player.isPlaying())
            {
                player.pause();
                bt.setText("Play");
                playing = false;
            }
            else
            {
                player.play();
                
                bt.setText("Pause");
                playing = true ;
                positionInMovie = (int) player.getTime()/100;
                
                
                
            }
           
            
            
        }
    }
    
   
    
}

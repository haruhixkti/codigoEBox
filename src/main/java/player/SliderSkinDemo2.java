/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Katherine
 */
public class SliderSkinDemo2 {
     public JComponent makeUI() {
    UIDefaults d = new UIDefaults();
    d.put("Slider:SliderTrack[Enabled].backgroundPainter", new Painter<JSlider>() {
      @Override public void paint(Graphics2D g, JSlider c, int w, int h) {
          System.out.println("w: "+ w);
        int arc         = 10;
        int trackHeight = 8;
        int trackWidth  = w - 2;
        int fillTop     = 4;
        int fillLeft    = 1;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(1.5f));
        g.setColor(Color.GRAY);
        g.fillRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);

        int fillBottom = fillTop + trackHeight;
        int fillRight  = xPositionForValue(
            c.getValue(), c,
            new Rectangle(fillLeft, fillTop, trackWidth, fillBottom - fillTop));

       g.setColor(Color.ORANGE);
        g.fillRect(fillLeft + 1, fillTop + 1, fillRight - fillLeft, fillBottom - fillTop);

       g.setColor(Color.WHITE);
        g.drawRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);
      }
      //@see javax/swing/plaf/basic/BasicSliderUI#xPositionForValue(int value)
      protected int xPositionForValue(int value, JSlider slider, Rectangle trackRect) {
        int min = slider.getMinimum();
        int max = slider.getMaximum();
        int trackLength = trackRect.width;
        double valueRange = (double) max - (double) min;
        double pixelsPerValue = (double) trackLength / valueRange;
        int trackLeft = trackRect.x;
        int trackRight = trackRect.x + (trackRect.width - 1);
        int xPosition;

        xPosition = trackLeft;
        xPosition += Math.round(pixelsPerValue * ((double) value - min));

        xPosition = Math.max(trackLeft, xPosition);
        xPosition = Math.min(trackRight, xPosition);
          System.out.println("posición en x: "+ xPosition);
        return xPosition;
      }
    });

    JSlider slider = new JSlider();
    
    slider.putClientProperty("Nimbus.Overrides", d);

    JPanel p = new JPanel();
    p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    p.setBackground(Color.DARK_GRAY);
    //p.add(new JSlider());
   // p.add(Box.createRigidArea(new Dimension(200, 20)));
    p.add(slider);
    return slider;
    //return p;
  }
  public static void main(String... args) {
  int i = 0;
      try {
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            System.out.println("i: "+ i);
          if ("Nimbus".equals(laf.getName())) {
            UIManager.setLookAndFeel(laf.getClassName());
               
            
            System.out.println("entre al if");
            break;
          }
          i+=1;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                JSlider slider2 = new JSlider();
                JPanel p = new JPanel();
    p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    p.setBackground(Color.DARK_GRAY);
    p.add(new SliderSkinDemo2().makeUI());
     p.add(new SliderSkinDemo2().makeUI());
                f.getContentPane().add(p);
               
                f.setSize(720, 480);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                      }
        });
     
   
  }
}

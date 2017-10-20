/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 *
 * @author Katherine
 */
public class demo {
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
                f.getContentPane().add(new SliderSkinDemo2().makeUI());
                f.setSize(320, 240);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                      }
        });
     
   
  }
}

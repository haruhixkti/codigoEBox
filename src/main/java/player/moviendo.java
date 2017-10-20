/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;
/**
 *
 * @author Katherine
 */
public class moviendo extends javax.swing.JFrame {
   
  static  final int PREF_WIDTH = 638;
   static   final int PREF_HEIGHT = 193;
   private static final Color RECT_COLOR = new Color(102,255,255);
   ArrayList<JLabel> arregloTexto = new ArrayList<>();
   ArrayList<JScrollPane> arreglo = new ArrayList<>();
   static private int x, y, width, height;
   static boolean drawRect = false;
    int contador = 0;
   static public javax.swing.JPanel test;
   static public Color colorTag = new Color(200,230,201);
   static public Color lineTag = new Color(121,85,72);
   static public Color colorReproductor = new Color(56,142,60);
   
    /**
     * Creates new form moviendo
     */
    public moviendo() {
       //initComponents(); 
       iniciarComponentes();
        
        
        
        
        
       
        
    }
    private void iniciarComponentes(){
    PANEL = new javax.swing.JPanel();
   tagPanel = new javax.swing.JPanel(new BorderLayout()){
               @Override
                protected void paintComponent(Graphics g) {
                    System.out.println("CUARTO EVENTO: paintComponent");
                    //el cuadrado que se muestra antes de que aparezca el jtext
                   super.paintComponent(g);
                  // 
                   if (drawRect) {
                       System.out.println(" iF paintComponent IF");
                      g.setColor(lineTag);
                      g.drawRect(x, y, width, height);         
                   }
                   else{
                       System.out.println("Creando textArea");

                   }
                }
                @Override
                public Dimension getPreferredSize() {
                      System.out.println("getPreferredSize");
                   return new Dimension(PREF_WIDTH, PREF_HEIGHT);
                }
                          };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
       // setBackground(colorReproductor);
        setBackground(colorReproductor);

        tagPanel.setBackground(colorReproductor);
        //tagPanel.setBackground(colorReproductor);
        

        javax.swing.GroupLayout tagPanelLayout = new javax.swing.GroupLayout(tagPanel);
        tagPanel.setLayout(tagPanelLayout);
        tagPanelLayout.setHorizontalGroup(
            tagPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 638, Short.MAX_VALUE)
        );
        tagPanelLayout.setVerticalGroup(
            tagPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );
        PANEL.setBackground(new java.awt.Color(255, 204, 255));
        javax.swing.GroupLayout PANELLayout = new javax.swing.GroupLayout(PANEL);
        PANEL.setLayout(PANELLayout);
        PANELLayout.setHorizontalGroup(
            PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PANELLayout.setVerticalGroup(
            PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tagPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();       


///____________________________________________
        if(contador == 0){
            crearAgregar();
            //arregloTexto
            
            for (int i = 0; i < arregloTexto.size(); i++) {
                
                
                tagPanel.add(arregloTexto.get(i));
                
            }
            /*for (int i = 0; i < arreglo.size(); i++) {
                tagPanel.add(arreglo.get(i));
                //PANEL.add(arreglo.get(i));
            }*/
 
     }
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter(); 
        MyMouseAdapter myMouseAdapter2 = new MyMouseAdapter(); 
        tagPanel.addMouseListener(myMouseAdapter);
        tagPanel.addMouseMotionListener(myMouseAdapter);
        //PANEL.addMouseListener(myMouseAdapter2);
        //PANEL.addMouseMotionListener(myMouseAdapter2);
     
             
    
    
    }
    
   public void crearAgregar(){
       System.out.println("Entre a crearAgregar");
    Color borderTag = new Color(189,189,189);       
       Border border = BorderFactory.createLineBorder(borderTag, 1);
 
       for (int i = 0; i < 10; i++) {
           //JScrollPane tag = 
          JLabel txt = new JLabel();
           txt.setText("tag");
           txt.setOpaque(true);
           
           txt.setBackground(colorTag);
           txt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
           txt.setBorder(border);


               
           arregloTexto.add(txt);
           //arreglo.add(new JScrollPane());
       }
   
     }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tagPanel = new javax.swing.JPanel();
        PANEL = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));

        tagPanel.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout tagPanelLayout = new javax.swing.GroupLayout(tagPanel);
        tagPanel.setLayout(tagPanelLayout);
        tagPanelLayout.setHorizontalGroup(
            tagPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 638, Short.MAX_VALUE)
        );
        tagPanelLayout.setVerticalGroup(
            tagPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );

        PANEL.setBackground(new java.awt.Color(255, 204, 255));

        jLabel1.setBackground(new java.awt.Color(204, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout PANELLayout = new javax.swing.GroupLayout(PANEL);
        PANEL.setLayout(PANELLayout);
        PANELLayout.setHorizontalGroup(
            PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PANELLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
        );
        PANELLayout.setVerticalGroup(
            PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PANELLayout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(94, 94, 94))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tagPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(moviendo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(moviendo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(moviendo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(moviendo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             JFrame m = new moviendo(); 
            
             
                                  
                          
                          m.setVisible(true);
                         }
                     });
        
    }
  
  private class MyMouseAdapter extends MouseAdapter {
        
      private int innerX, innerY;

      @Override
      public void mousePressed(MouseEvent e) {
           System.out.println("PRIMER EVENTO: mousePressed");
         //SE CONSIGUE LA POSICIÓN DEL MOUSE (ACA PODRIA LIMITAR EL ESPACIO)
         
         x = e.getX();
         y = e.getY();
         innerX = x;
         innerY = y;
         width = 0;
         height = 0;
         
         drawRect = true;
      }

      @Override
      public void mouseDragged(MouseEvent e) {
          System.out.println("mouseDragged");
         calcBounds(e);

         drawRect = true;
         tagPanel.repaint();
         //ResizeableTextArea.this.repaint();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
          System.out.println("TERCER EVENTO EVENTO: mouseReleased");
          
         calcBounds(e);
        //cuando el mouse se suelta
         drawRect = false;
         
               
          System.out.println("x: "+ x);
          System.out.println("y: "+ y);
          System.out.println("widtg: "+ width);
          System.out.println("height: "+ height);
          //crearAgregar();
          arregloTexto.get(contador).setBounds(x, y, width, height);
          //arregloTexto.get(contador).setBackground(new java.awt.Color(204,204,255));
          //arreglo.get(contador).setBounds(x, y, width, height);
          //arreglo.get(contador).setBackground(new java.awt.Color(204,204,255));
    
         contador = contador+1;
         
         //ResizeableTextArea.this.repaint();
         
         
         
          ///System.out.println("TEXTO: "+ textArea.getText());

      }

      private void calcBounds(MouseEvent e) {
          System.out.println("SEGUNDO EVENTO: calcBounds");
           
          
          
         
          //eje x
           if (Math.min(innerX, e.getX()) < 0){
            x = 0;
            width = Math.abs(innerX - e.getX());
         
         }
         else if(Math.min(innerX, e.getX()) + width > PREF_WIDTH ){

            x = PREF_WIDTH - width;
            width = Math.abs(innerX - e.getX());
         }
         else {
            x = Math.min(innerX, e.getX()); 
            width = Math.abs(innerX - e.getX());
         }


         //eje y
        if(Math.min(innerY, e.getY())<0){
             y = 0;
             height = 49;
         }
        else if(Math.min(innerY, e.getY()) + height > PREF_HEIGHT ){
            y = PREF_HEIGHT - 53;
            height = 49;
         }
        else{
            y = Math.min(innerY, e.getY());
            height = 49;
            
        }
      }

   }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PANEL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel tagPanel;
    // End of variables declaration//GEN-END:variables

}

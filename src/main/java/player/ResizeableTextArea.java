/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

/**
 *
 * @author Katherine
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
@SuppressWarnings("serial")
public class ResizeableTextArea extends JPanel  {
   //tamaño de la pantalla inicial
    private static final int PREF_WIDTH = 700;
   private static final int PREF_HEIGHT = 500;
   
   private static final int ROWS = 60;
   private static final int COLS = 80;
   //color del borde del cuadrado (antes de soltar el click)
   private static final Color RECT_COLOR = new Color(102,255,255);
   private JLabel textArea = new JLabel();
   //private JTextArea textArea = new JTextArea("tag",ROWS, COLS);
  
   private JScrollPane scrollPane = new JScrollPane(textArea);
   private int x, y, width, height;
   private boolean drawRect = false;

   public ResizeableTextArea() {
       System.out.println("ResizeableTextArea()");
      setLayout(null);
      add(scrollPane);

      MyMouseAdapter myMouseAdapter = new MyMouseAdapter(); 
      addMouseListener(myMouseAdapter);
      addMouseMotionListener(myMouseAdapter);
   }
   @Override
   protected void paintComponent(Graphics g) {
       System.out.println("CUARTO EVENTO: paintComponent");
       //el cuadrado que se muestra antes de que aparezca el jtext
      super.paintComponent(g);
     // 
      if (drawRect) {
          System.out.println(" iF paintComponent IF");
         g.setColor(RECT_COLOR);
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
         ResizeableTextArea.this.repaint();
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
         
         scrollPane.setBounds(x, y, width, height);
         
         scrollPane.getViewport().revalidate();
         ResizeableTextArea.this.repaint();
         textArea.setText("tag");
         textArea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         textArea.setBackground(new java.awt.Color(204,204,255));
         textArea.setOpaque(true);
       
         
         
          System.out.println("TEXTO: "+ textArea.getText());

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

   private static void createAndShowUI() {
       System.out.println("createAndShowUI");
      JFrame frame = new JFrame("ResizeableTextArea");
      frame.getContentPane().add(new ResizeableTextArea());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            createAndShowUI();
         }
      });
   }
}

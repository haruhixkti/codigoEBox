/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import javax.swing.JSlider;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


/**
 *
 * @author Katherine
 */
public class Reproductor extends javax.swing.JFrame {
    
    /**
     * Creates new form Reproductor
     */
    
   //CONSTANTES DE COLOR
   
   //TAG
   static public Color colorFondoTag = new Color(200,230,201);
   static public Color colorBordeTag = new Color(189,189,189); 
   static public Color colorLineaTag = new Color(121,85,72);
   static public Color colorFondoGuiTag = new Color(56,142,60);
   
   //REPRODUCTOR
   static public Color colorFondoGuiReproductor = new Color(255,255,255);
   static public Color colorBordeGuiPerspectiva = new Color(117,117,117);
   
    ArrayList<JLabel> arregloTag = new ArrayList<>();
    public String textoTagAutomatico = "Felicidad";
    public String textoTagManual = "Asombro";
    static private int x, y, width, height;
    static boolean drawRect = false;
    int contador = 0;
    static  final int PREF_WIDTH = 1290;
    static   final int PREF_HEIGHT = 49;
    static   final int tamanoTag = 52;
  
    
    //REPRODUCTOR
   
    //path de las perspectivas    
    public static String store = "FaceRecorderTemporal";
    //variable que permite detener o iniciar la reproducción de las perspectivas
    boolean isRunning = false;
    //FPS
    public static int captureInterval = 40;
    public static int fPS = 25;

    public int tiempoVideoTranscurrido = 0;
    //variable que permite habilitar o deshabilitar el jslider
    public boolean enable = true;
    //frames que han sido mostrados
    public int frameSegundo =0;
    //variable que permite setear los valores iniciales
    public boolean primerInicio = true;
    //variable que permite detener el video
    public boolean detener = false;
    //tiempo de duración de la muestra
    public int tiempoDuracionMuestra = 12;

    public int contadorFrameSegundo;

    public File f;   
    public File[] fileLst;
    public ImageIcon icon; 

    //frame x segundo
    public Hashtable<Integer, Integer> FPS = new Hashtable<Integer, Integer>();

    //tags
    public Hashtable<Integer, JLabel> tag = new Hashtable<Integer, JLabel>();

    public int posTagManual;    
    public Reproductor() {
        //initComponents();
        iniciarComponentes();
    
    }
    private void iniciarComponentes(){
        
        principal = new javax.swing.JPanel();
        vistaPerspectivaCara = new javax.swing.JPanel();
        videoCara = new javax.swing.JLabel();
        vistaTagAutomatico = new javax.swing.JPanel();
        vistaConfTagAutomatico = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        vistaConfTagManual = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        vistaPanelTag = new javax.swing.JPanel();
        vistaPanelPlayer = new javax.swing.JPanel();
        btnPlay = new javax.swing.JLabel();
        btnFinal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        vistaPanelPerspectiva = new javax.swing.JPanel();
        vistaPerspectivaActividad = new javax.swing.JPanel();
        vistaPerspectivaActividad1 = new javax.swing.JPanel();
        vistaTiempo = new javax.swing.JPanel();
        
        //_____________________________________//
       
        sliderTiempo = new SliderSkinDemo2().makeUI();
        sliderTiempo.setMaximum(tiempoDuracionMuestra);
        sliderTiempo.setMajorTickSpacing(1);
        sliderTiempo.setMinorTickSpacing(1);
        sliderTiempo.setPaintTicks(true);
        sliderTiempo.setPaintLabels(true);
        
        
        
        
        
        //_____________________________________//

        btnPlay.setText("iniciar");
        btnPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
             btnPlayMouseClicked(evt);
            }
        });


    //_____________________________________//
        vistaTagManual = new javax.swing.JPanel(new BorderLayout()){
               @Override
                protected void paintComponent(Graphics g) {
                    System.out.println("CUARTO EVENTO: paintComponent");
                    //el cuadrado que se muestra antes de que aparezca el jtext
                   super.paintComponent(g);
                  // 
                   if (drawRect) {
                       System.out.println(" iF paintComponent IF");
                      g.setColor(colorLineaTag);
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
        
        
        //_____________________________________//
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);

        principal.setBackground(new java.awt.Color(51, 255, 204));
        principal.setPreferredSize(new java.awt.Dimension(1366, 768));
        principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vistaPerspectivaCara.setBackground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout vistaPerspectivaCaraLayout = new javax.swing.GroupLayout(vistaPerspectivaCara);
        vistaPerspectivaCara.setLayout(vistaPerspectivaCaraLayout);
        vistaPerspectivaCaraLayout.setHorizontalGroup(
            vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPerspectivaCaraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(videoCara, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        vistaPerspectivaCaraLayout.setVerticalGroup(
            vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                .addComponent(videoCara, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        principal.add(vistaPerspectivaCara, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 455, 370));

        vistaTagAutomatico.setBackground(new java.awt.Color(51, 204, 0));

        javax.swing.GroupLayout vistaTagAutomaticoLayout = new javax.swing.GroupLayout(vistaTagAutomatico);
        vistaTagAutomatico.setLayout(vistaTagAutomaticoLayout);
        vistaTagAutomaticoLayout.setHorizontalGroup(
            vistaTagAutomaticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaTagAutomaticoLayout.setVerticalGroup(
            vistaTagAutomaticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaTagAutomatico, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 1290, 49));

        vistaTagManual.setBackground(new java.awt.Color(0, 0, 255));

        javax.swing.GroupLayout vistaTagManualLayout = new javax.swing.GroupLayout(vistaTagManual);
        vistaTagManual.setLayout(vistaTagManualLayout);
        vistaTagManualLayout.setHorizontalGroup(
            vistaTagManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaTagManualLayout.setVerticalGroup(
            vistaTagManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaTagManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, 1290, 49));

        vistaConfTagAutomatico.setBackground(new java.awt.Color(204, 255, 204));
        vistaConfTagAutomatico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Automático");
        vistaConfTagAutomatico.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        principal.add(vistaConfTagAutomatico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 80, 50));

        vistaConfTagManual.setBackground(new java.awt.Color(153, 153, 255));
        vistaConfTagManual.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Manual");
        vistaConfTagManual.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        principal.add(vistaConfTagManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 80, 50));

        vistaPanelTag.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout vistaPanelTagLayout = new javax.swing.GroupLayout(vistaPanelTag);
        vistaPanelTag.setLayout(vistaPanelTagLayout);
        vistaPanelTagLayout.setHorizontalGroup(
            vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaPanelTagLayout.setVerticalGroup(
            vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaPanelTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 470, 240));

        vistaPanelPlayer.setBackground(new java.awt.Color(102, 0, 102));
        vistaPanelPlayer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Play-50.png"))); // NOI18N
        vistaPanelPlayer.add(btnPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, -1, 50));

        btnFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-End-50.png"))); // NOI18N
        vistaPanelPlayer.add(btnFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-begin.png"))); // NOI18N
        vistaPanelPlayer.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        principal.add(vistaPanelPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 530, 425, 240));

        vistaPanelPerspectiva.setBackground(new java.awt.Color(255, 255, 102));

        javax.swing.GroupLayout vistaPanelPerspectivaLayout = new javax.swing.GroupLayout(vistaPanelPerspectiva);
        vistaPanelPerspectiva.setLayout(vistaPanelPerspectivaLayout);
        vistaPanelPerspectivaLayout.setHorizontalGroup(
            vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaPanelPerspectivaLayout.setVerticalGroup(
            vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaPanelPerspectiva, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, 480, 240));

        vistaPerspectivaActividad.setBackground(new java.awt.Color(255, 204, 255));

        javax.swing.GroupLayout vistaPerspectivaActividadLayout = new javax.swing.GroupLayout(vistaPerspectivaActividad);
        vistaPerspectivaActividad.setLayout(vistaPerspectivaActividadLayout);
        vistaPerspectivaActividadLayout.setHorizontalGroup(
            vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaPerspectivaActividadLayout.setVerticalGroup(
            vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaPerspectivaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 0, 450, 370));

        vistaPerspectivaActividad1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout vistaPerspectivaActividad1Layout = new javax.swing.GroupLayout(vistaPerspectivaActividad1);
        vistaPerspectivaActividad1.setLayout(vistaPerspectivaActividad1Layout);
        vistaPerspectivaActividad1Layout.setHorizontalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaPerspectivaActividad1Layout.setVerticalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaPerspectivaActividad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 0, 460, 370));

        vistaTiempo.setBackground(new java.awt.Color(153, 153, 0));
        vistaTiempo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sliderTiempo.setMaximum(12);
        vistaTiempo.add(sliderTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 40));

        principal.add(vistaTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 1290, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
         //_____________________________________//
        if(contador == 0){
            crearAgregar();
                     
        for (int i = 0; i < arregloTag.size(); i++) {
                vistaTagManual.add(arregloTag.get(i));
           }
       
     }
          //_____________________________________//
       MyMouseAdapter myMouseAdapter = new MyMouseAdapter(); 
       vistaTagManual.addMouseListener(myMouseAdapter);
       vistaTagManual.addMouseMotionListener(myMouseAdapter);
         //_____________________________________//
        
        
    }
    private void btnPlayMouseClicked(java.awt.event.MouseEvent evt) {                                        

        detener = false;
        //Se determina si es el primer inicio para determinar el largo y la división de las lineas de tiempoDuracionMuestra
        if(primerInicio){
            
            sliderTiempo.setMaximum(tiempoDuracionMuestra);
            primerInicio= false;
        
        }
        
        if(enable){
            
            isRunning= true;
            enable = false;
            sliderTiempo.setEnabled(false);
            
        
        }
        else
        {
            isRunning= false;
            enable = true;
            sliderTiempo.setEnabled(true);
            
        }
        ///SE INICIA LA REPRODUCCIÓN DE MUESTRAS
        new VideoFeedTaker().start();
        ///1000-> 1 acción cada 1 segundo
        final Timer t = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
            if(tiempoVideoTranscurrido == tiempoDuracionMuestra || detener == true){
                    //se detiene linea de tiempo
                    ((Timer) e.getSource()).stop();
          } else{
                tiempoVideoTranscurrido=tiempoVideoTranscurrido+1;
                sliderTiempo.setValue(tiempoVideoTranscurrido);
                
                FPS.put(tiempoVideoTranscurrido, frameSegundo);
                
                System.out.println("Al segundo: "+ tiempoVideoTranscurrido + " han pasado "+ frameSegundo+ " frames");
            }
            
            }
    });
    t.start();
    }  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        principal = new javax.swing.JPanel();
        vistaPerspectivaCara = new javax.swing.JPanel();
        videoCara = new javax.swing.JLabel();
        vistaTagAutomatico = new javax.swing.JPanel();
        vistaTagManual = new javax.swing.JPanel();
        vistaConfTagAutomatico = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        vistaConfTagManual = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        vistaPanelTag = new javax.swing.JPanel();
        vistaPanelPlayer = new javax.swing.JPanel();
        btnPlay = new javax.swing.JLabel();
        btnFinal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        vistaPanelPerspectiva = new javax.swing.JPanel();
        vistaPerspectivaActividad = new javax.swing.JPanel();
        vistaPerspectivaActividad1 = new javax.swing.JPanel();
        vistaTiempo = new javax.swing.JPanel();
        sliderTiempo = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);

        principal.setBackground(new java.awt.Color(51, 255, 204));
        principal.setPreferredSize(new java.awt.Dimension(1366, 768));
        principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vistaPerspectivaCara.setBackground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout vistaPerspectivaCaraLayout = new javax.swing.GroupLayout(vistaPerspectivaCara);
        vistaPerspectivaCara.setLayout(vistaPerspectivaCaraLayout);
        vistaPerspectivaCaraLayout.setHorizontalGroup(
            vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPerspectivaCaraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(videoCara, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        vistaPerspectivaCaraLayout.setVerticalGroup(
            vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                .addComponent(videoCara, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        principal.add(vistaPerspectivaCara, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 455, 370));

        vistaTagAutomatico.setBackground(new java.awt.Color(51, 204, 0));

        javax.swing.GroupLayout vistaTagAutomaticoLayout = new javax.swing.GroupLayout(vistaTagAutomatico);
        vistaTagAutomatico.setLayout(vistaTagAutomaticoLayout);
        vistaTagAutomaticoLayout.setHorizontalGroup(
            vistaTagAutomaticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaTagAutomaticoLayout.setVerticalGroup(
            vistaTagAutomaticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaTagAutomatico, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 1290, 49));

        vistaTagManual.setBackground(new java.awt.Color(0, 0, 255));

        javax.swing.GroupLayout vistaTagManualLayout = new javax.swing.GroupLayout(vistaTagManual);
        vistaTagManual.setLayout(vistaTagManualLayout);
        vistaTagManualLayout.setHorizontalGroup(
            vistaTagManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaTagManualLayout.setVerticalGroup(
            vistaTagManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaTagManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, 1290, 49));

        vistaConfTagAutomatico.setBackground(new java.awt.Color(204, 255, 204));
        vistaConfTagAutomatico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Automático");
        vistaConfTagAutomatico.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        principal.add(vistaConfTagAutomatico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 80, 50));

        vistaConfTagManual.setBackground(new java.awt.Color(153, 153, 255));
        vistaConfTagManual.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Manual");
        vistaConfTagManual.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        principal.add(vistaConfTagManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 80, 50));

        vistaPanelTag.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout vistaPanelTagLayout = new javax.swing.GroupLayout(vistaPanelTag);
        vistaPanelTag.setLayout(vistaPanelTagLayout);
        vistaPanelTagLayout.setHorizontalGroup(
            vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaPanelTagLayout.setVerticalGroup(
            vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaPanelTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 470, 240));

        vistaPanelPlayer.setBackground(new java.awt.Color(102, 0, 102));
        vistaPanelPlayer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Play-50.png"))); // NOI18N
        vistaPanelPlayer.add(btnPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, -1, 50));

        btnFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-End-50.png"))); // NOI18N
        vistaPanelPlayer.add(btnFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-begin.png"))); // NOI18N
        vistaPanelPlayer.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        principal.add(vistaPanelPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 530, 425, 240));

        vistaPanelPerspectiva.setBackground(new java.awt.Color(255, 255, 102));

        javax.swing.GroupLayout vistaPanelPerspectivaLayout = new javax.swing.GroupLayout(vistaPanelPerspectiva);
        vistaPanelPerspectiva.setLayout(vistaPanelPerspectivaLayout);
        vistaPanelPerspectivaLayout.setHorizontalGroup(
            vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaPanelPerspectivaLayout.setVerticalGroup(
            vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaPanelPerspectiva, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, 480, 240));

        vistaPerspectivaActividad.setBackground(new java.awt.Color(255, 204, 255));

        javax.swing.GroupLayout vistaPerspectivaActividadLayout = new javax.swing.GroupLayout(vistaPerspectivaActividad);
        vistaPerspectivaActividad.setLayout(vistaPerspectivaActividadLayout);
        vistaPerspectivaActividadLayout.setHorizontalGroup(
            vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaPerspectivaActividadLayout.setVerticalGroup(
            vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaPerspectivaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 0, 450, 370));

        vistaPerspectivaActividad1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout vistaPerspectivaActividad1Layout = new javax.swing.GroupLayout(vistaPerspectivaActividad1);
        vistaPerspectivaActividad1.setLayout(vistaPerspectivaActividad1Layout);
        vistaPerspectivaActividad1Layout.setHorizontalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaPerspectivaActividad1Layout.setVerticalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        principal.add(vistaPerspectivaActividad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 0, 460, 370));

        vistaTiempo.setBackground(new java.awt.Color(153, 153, 0));
        vistaTiempo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sliderTiempo.setMaximum(12);
        vistaTiempo.add(sliderTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 40));

        principal.add(vistaTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 1290, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public void crearAgregar(){
       System.out.println("Entre a crearAgregar");
       Border border = BorderFactory.createLineBorder(colorBordeTag, 1);
 
       for (int i = 0; i < 60; i++) {
         JLabel tag = new JLabel();
          tag.setText(textoTagManual);
          tag.setOpaque(true);
          tag.setBackground(colorFondoTag);
          tag.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
          tag.setBorder(border);
          arregloTag.add(tag);
        
       }
   
     }
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
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("size: "+ Toolkit.getDefaultToolkit().getScreenSize());
                new Reproductor().setVisible(true);
                
            }
        });
    }
          class VideoFeedTaker extends Thread{
           @Override
        public void run() {
            System.out.println("Entre al hilo");
            f = new File(store);
            fileLst = f.listFiles();
            
            System.out.println("cantidad de imagenes: "+ fileLst.length);
            while(isRunning){
                                
                   try {
                       //System.out.println("frameSegundo: "+frameSegundo);
                     //  System.out.println("path: "+fileLst[frameSegundo].getAbsolutePath());
                       icon = new ImageIcon(fileLst[frameSegundo].getAbsolutePath());
                       videoCara.setIcon(icon);
                       if(frameSegundo==fileLst.length-1){
                       isRunning=false;
                       //detener = true;
                       }
                       frameSegundo+=1;
                
                   
			// 10 FPS Thread.sleep(100);
			Thread.sleep(captureInterval);
                        
                } catch (InterruptedException ex) {
                    //Logger.getLogger(CameraTest.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
                }
      
        }
    
    }
  class MyMouseAdapter extends MouseAdapter {
        
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
         vistaTagManual.repaint();
        
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
       
          arregloTag.get(contador).setBounds(x, y, width, height);
      
         contador = contador+1;
     
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
             height = tamanoTag;
         }
        else if(Math.min(innerY, e.getY()) + height > PREF_HEIGHT ){
            y = PREF_HEIGHT - 53;
            height = tamanoTag;
         }
        else{
            y = Math.min(innerY, e.getY());
            height = tamanoTag;
            
        }
      }
  
   }
   public class SliderSkinDemo2{
    
       public JSlider makeUI() {
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
 int FPS_MIN = 0;
   int FPS_MAX = 100;
     int FPS_INIT = 0;
    JSlider slider = new JSlider(JSlider.HORIZONTAL,
                                              FPS_MIN, FPS_MAX, FPS_INIT);
    
    slider.putClientProperty("Nimbus.Overrides", d);
      
    return slider;
    
  }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnFinal;
    private javax.swing.JLabel btnPlay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel principal;
    private javax.swing.JSlider sliderTiempo;
    private javax.swing.JLabel videoCara;
    private javax.swing.JPanel vistaConfTagAutomatico;
    private javax.swing.JPanel vistaConfTagManual;
    private javax.swing.JPanel vistaPanelPerspectiva;
    private javax.swing.JPanel vistaPanelPlayer;
    private javax.swing.JPanel vistaPanelTag;
    private javax.swing.JPanel vistaPerspectivaActividad;
    private javax.swing.JPanel vistaPerspectivaActividad1;
    private javax.swing.JPanel vistaPerspectivaCara;
    private javax.swing.JPanel vistaTagAutomatico;
    private javax.swing.JPanel vistaTagManual;
    private javax.swing.JPanel vistaTiempo;
    // End of variables declaration//GEN-END:variables
}

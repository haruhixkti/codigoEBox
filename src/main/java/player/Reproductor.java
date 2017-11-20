/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.toIntExact;
import java.util.Hashtable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    static public Color colorFondoTag = new Color(200, 230, 201);
    static public Color colorBordeTag = new Color(189, 189, 189);
    static public Color colorLineaTag = new Color(121, 85, 72);
    static public Color colorFondoGuiTag = new Color(56, 142, 60);

    //REPRODUCTOR
    static public Color colorFondoGuiReproductor = new Color(255, 255, 255);
    static public Color colorBordeGuiPerspectiva = new Color(117, 117, 117);

     
      
    ArrayList<ArrayList<JLabel>> contenedor = new ArrayList<ArrayList<JLabel>>();
   // ArrayList<JLabel> arregloTag = new ArrayList<>();
    
    public String textoTagAutomatico = "Felicidad";
    public String textoTagManual = "Asombro";
    static private int x, y, width, height;
    static boolean drawRect = false;
    
    static final int PREF_WIDTH = 1290;
    static final int PREF_HEIGHT = 49;
    static final int tamanoTag = 52;

    //REPRODUCTOR
    //path de las perspectivas    
    public static String store = "FaceRecorderTemporal";
    //variable que permite detener o iniciar la reproducción de las perspectivas
    boolean isRunning = false;
    //FPS
    public static int captureInterval = 100;
    public static int fPS = 10;

    public int tiempoVideoTranscurrido = 0;
    //variable que permite habilitar o deshabilitar el jslider
    public boolean enable = true;
    //frames que han sido mostrados
    public int frameSegundo = 0;
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

    public int posTagManual, numeroSeleccionada;
    String tiempo, nombre;

    final CyclicBarrier gate = new CyclicBarrier(4);
    public File fA, fF, fE;
    public File[] fileLstA, fileLstF, fileLstE;
    ImageIcon icon1, icon2, icon3;
    public int frameSegundoA = 0;
    public int frameSegundoF = 0;
    public int frameSegundoE = 0;
    String tiempoTotal;
    int posicionfinal = 0;
    public boolean mouseDown, flechaArriba, flechaAbajo, flechaIzq, flechaDer, espacio;
    public int frameMas = 0;
    public boolean AR = false;
    public boolean FR = false;
    public boolean PE = false;
    public int objeto = 0;
    public String nombreProyecto, codigoProyecto, descripcionProyecto, rutaProyecto, ruta;
    public ArrayList<String> rutasMuestras = new ArrayList<>();
    public ArrayList<String> duracionMuestras = new ArrayList<>();
    public ArrayList<String> nombreMuestras = new ArrayList<>();
    public ArrayList<String> nombreIndividuo = new ArrayList<>();
    public ArrayList<String> descripcionMuestra = new ArrayList<>();
    private final String direccion;
    int cantidadTagsManual = 5;
    int cantidadTagsAutomaticos = 5;
    int muestra;
    int tiempoParaTags = 0;
    public int minutos = 0;
    public int contadorTiempo = 0;
    int contador = 0;
    public ArrayList<Integer> tagsPorMinuto = new ArrayList<>();
    

    public Reproductor(String dir) {
        //System.out.println("<<<<<<REPRODUCTOR DE MUESTRAS>>>>>");
        this.direccion = dir;
        //initComponents();

       leerJson();
        iniciarComponentes();
        

    }

  
    public void leerJson() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        
        
        try (FileReader reader = new FileReader(this.direccion+"informacionProyecto.json")) {
            ////System.out.println("DIRECCION!!!!!!!!!!!!!!!: "+ this.direccion+"informacionProyecto.json");
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            //System.out.println("discoteca: " + employeeList);

            //Iterate over employee array
            employeeList.forEach(emp -> parseEmployeeObject((JSONObject) emp));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void parseEmployeeObject(JSONObject employee) {
        //Get employee object within list
        //Get employee object within list
        //System.out.println("APROVECHALO: " + employee);
        if (objeto == 0) {

            JSONObject employeeObject1 = (JSONObject) employee.get("proyecto");

            //Get employee first name
            nombreProyecto = (String) employeeObject1.get("nombre");
            //System.out.println(nombreProyecto);
            rutaProyecto = (String) employeeObject1.get("destino");
            //System.out.println(rutaProyecto);
            codigoProyecto = (String) employeeObject1.get("codigo");
            //System.out.println(codigoProyecto);
            descripcionProyecto = (String) employeeObject1.get("descripcion");
            //System.out.println(descripcionProyecto);

        }
        if (objeto == 1) {
            JSONObject employeeObject2 = (JSONObject) employee.get("perspectivas");
            //System.out.println("objeto2: " + employeeObject2);
            //Get employee first name
            PE = stringToBoolean((String) employeeObject2.get("perspectivaExterna"));
            //System.out.println(PE);
            AR = stringToBoolean((String) employeeObject2.get("perspectivaActividad"));
            //System.out.println(AR);
            FR = stringToBoolean((String) employeeObject2.get("perspectivaCara"));
            //System.out.println(FR);

        }
        if (objeto > 1) {
            JSONObject employeeObject3 = (JSONObject) employee.get("muestra");

            //Get employee first name
            String firstNamea = (String) employeeObject3.get("ruta");
            rutasMuestras.add(firstNamea);
            //System.out.println(firstNamea);

            String secondNamea = (String) employeeObject3.get("tiempo");
            duracionMuestras.add(secondNamea);
            //System.out.println("tiempo: "+secondNamea);
            String[] parts = secondNamea.split(":");
           tiempoParaTags = Integer.parseInt(parts[0]);
            
 
            
            String tresNamea = (String) employeeObject3.get("nombre");
            nombreMuestras.add(tresNamea);
            //System.out.println(tresNamea);
            
            String seleccionada = (String) employeeObject3.get("seleccionada");
            
            if("true".equals(seleccionada)){
                numeroSeleccionada = muestra;
                //System.out.println("pos OBjeto seleccionado: "+ numeroSeleccionada);
                String firstName = (String) employeeObject3.get("ruta");
                ruta = firstName;
                //System.out.println(firstName);

                String secondName = (String) employeeObject3.get("tiempo");
                tiempo = secondName;
                //System.out.println(secondName);

                String tresName = (String) employeeObject3.get("nombre");
                nombre = tresName;
                //System.out.println(tresName);

            }
                muestra+=1;

            

        }
        objeto += 1;

    }

    public boolean stringToBoolean(String elemento) {
        if (elemento == "true") {
            return true;

        }
        return false;

    }
    public void escribirTags(){
     
        JSONObject employeeObject1 = new JSONObject();
        JSONObject employeeObject2 = new JSONObject();
        JSONObject tagManual = new JSONObject();
        for (int i = 0; i < cantidadTagsManual; i++) {
            tagManual.put("id", String.valueOf(i));
            tagManual.put("xInicial", "1");
            tagManual.put("xFinal", "1");
            tagManual.put("frameInicial", "1");
            tagManual.put("frameFinal", "1");
            tagManual.put("tiempoInicial", "1");
            tagManual.put("tiempoFinal", "1");
            
            employeeObject1.put("tagsManuales", tagManual);
        }
        
        
        
        JSONObject tagAutomatico = new JSONObject();
        for (int i = 0; i < cantidadTagsManual; i++) {
            tagAutomatico.put("id", String.valueOf(i));
            tagAutomatico.put("xInicial", "1");
            tagAutomatico.put("xFinal", "1");
            tagAutomatico.put("frameInicial", "1");
            tagAutomatico.put("frameFinal", "1");
            tagAutomatico.put("tiempoInicial", "1");
            tagAutomatico.put("tiempoFinal", "1");
            employeeObject2.put("tagsAutomaticos", tagAutomatico);
        }
        JSONArray employeeList = new JSONArray();
        employeeList.add(employeeObject1);
        employeeList.add(employeeObject2);
        
        
        

        
      
        try (FileWriter file = new FileWriter(ruta+"/informacionTags.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
    }
    public void escribirJson() {
        JSONArray employeeList = new JSONArray();

        JSONObject employeeDetails1 = new JSONObject();
        employeeDetails1.put("nombre", nombreProyecto);
        employeeDetails1.put("destino", rutaProyecto);
        employeeDetails1.put("codigo", codigoProyecto);
        employeeDetails1.put("descripcion", descripcionProyecto);

        JSONObject employeeObject = new JSONObject();
        employeeObject.put("proyecto", employeeDetails1);
        employeeList.add(employeeObject);

        JSONObject employeeDetails2 = new JSONObject();
        employeeDetails2.put("perspectivaCara", String.valueOf(FR));
        employeeDetails2.put("perspectivaActividad", String.valueOf(AR));
        employeeDetails2.put("perspectivaExterna", String.valueOf(PE));

        JSONObject employeeObject2 = new JSONObject();
        employeeObject2.put("perspectivas", employeeDetails2);
        employeeList.add(employeeObject2);
        
        
        for (int i = 0; i <  rutasMuestras.size(); i++) {
            JSONObject employeeDetails3 = new JSONObject();
            employeeDetails3.put("ruta", rutasMuestras.get(i));
            employeeDetails3.put("tiempo", duracionMuestras.get(i));
            employeeDetails3.put("nombre", nombreMuestras.get(i));
            //System.out.println("nombre muestra:" +nombreMuestras.get(i));
            employeeDetails3.put("seleccionada", "false");
         
            
            JSONObject employeeObject3 = new JSONObject();
            employeeObject3.put("muestra", employeeDetails3);
            employeeList.add(employeeObject3);
        }
        try (FileWriter file = new FileWriter(this.direccion+"informacionProyecto.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void iniciarComponentes() {

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
        videoActividad = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        videoExterno = new javax.swing.JLabel();
        minutero = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        //_____________________________________//
        sliderTiempo = new SliderSkinDemo2().makeUI();

        sliderTiempo.setValue(0);
        //sliderTiempo.setFocusTraversalKeysEnabled(false);
        //sliderTiempo.setMaximum(100); -> 10 segundos
        //600-> 1 minuto (60 segundos)
        //130->13 segundos 
        int tiempoMinuto = 600;
        sliderTiempo.setMaximum(tiempoMinuto);
        sliderTiempo.setMinorTickSpacing(10);
        sliderTiempo.setPaintTicks(true);

        
        Hashtable labelTable = new Hashtable();
        int j = 0;
        for (int i = 0; i <= tiempoMinuto; i++) {

            if (j == 10) {
                labelTable.put(i, new JLabel(String.valueOf(i / 10)));
                j = 0;
            }
            j = j + 1;

        }

        sliderTiempo.setLabelTable(labelTable);

        sliderTiempo.setPaintLabels(true);

        sliderTiempo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        // handle up 
                        //System.out.println("up");
                        flechaArriba = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        // handle down 
                        flechaAbajo = true;
                        //System.out.println("down");
                        break;
                    case KeyEvent.VK_SPACE:
                        espacio = true;
                        btnPlayMouseClicked();
                        //System.out.println("ESPACIOOOOOOO");
                        break;
                    case KeyEvent.VK_LEFT:
                        // handle left
                        flechaIzq = true;
                        //System.out.println("left");
                        break;
                    case KeyEvent.VK_RIGHT:
                        // handle right
                        flechaDer = true;
                       // //System.out.println("right");
                        break;
                }
            }
        });

        //_____________________________________//
        btnPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlayMouseClicked();
            }
        });
        btnPlay.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

               // //System.out.println("teclado");
                int keyCode = e.getKeyCode();
               // //System.out.println("keyCode: " + keyCode);
                switch (keyCode) {

                    case KeyEvent.VK_SPACE:
                        espacio = true;
                        btnPlayMouseClicked();
                     //   //System.out.println("ESPACIO");
                        break;

                }
            }

        });

        //_____________________________________//
        vistaTagManual = new javax.swing.JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
               // //System.out.println("CUARTO EVENTO: paintComponent");
                //el cuadrado que se muestra antes de que aparezca el jtext
                super.paintComponent(g);
                // 
                if (drawRect) {
                   // //System.out.println(" iF paintComponent IF");
                    g.setColor(colorLineaTag);
                    g.drawRect(x, y, width, height);
                } else {
                   // //System.out.println("Creando textArea");

                }
            }

            @Override
            public Dimension getPreferredSize() {
                ////System.out.println("getPreferredSize");
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
            .addComponent(videoCara, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
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

        jLabel8.setFont(new java.awt.Font("Roboto Light", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(200, 230, 201));
        jLabel8.setText("00:00:000");
        vistaPanelPlayer.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

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
            .addComponent(videoActividad, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        vistaPerspectivaActividadLayout.setVerticalGroup(
            vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoActividad, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );

        principal.add(vistaPerspectivaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 0, 450, 370));

        vistaPerspectivaActividad1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout vistaPerspectivaActividad1Layout = new javax.swing.GroupLayout(vistaPerspectivaActividad1);
        vistaPerspectivaActividad1.setLayout(vistaPerspectivaActividad1Layout);
        vistaPerspectivaActividad1Layout.setHorizontalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoExterno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );
        vistaPerspectivaActividad1Layout.setVerticalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoExterno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );

        principal.add(vistaPerspectivaActividad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 0, 460, 370));

        vistaTiempo.setBackground(new java.awt.Color(153, 153, 0));
        vistaTiempo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vistaTiempo.add(sliderTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 60));

        principal.add(vistaTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 1290, 60));

        minutero.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(minutero)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(minutero)
                .addGap(22, 22, 22))
        );

        principal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 80, 60));

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
        crearAgregar();
        agregarTag();
        //_____________________________________//
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
        vistaTagManual.addMouseListener(myMouseAdapter);
        vistaTagManual.addMouseMotionListener(myMouseAdapter);
        //_____________________________________//

    }

    private void btnPlayMouseClicked() {
        escribirJson();
        escribirTags();


        //PLAY
        if (isRunning == false) {
            isRunning = true;
            btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Pause-50.png")));
        } else {
            isRunning = false;
            btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Play-50.png")));

        }
        //PAUSA

        //REPRODUCIR PERSPECTIVA 1 FACE
        Thread face = new Thread() {
            public void run() {
                try {
                    gate.await();
                   // //System.out.println("--Comienzo grabaciones <FaceRecorder> --");
                    fF = new File(ruta + "/storeFaceRecorder/");
                    fileLstF = fF.listFiles();
                    
                    

                    while (isRunning) {

                        icon1 = new ImageIcon(fileLstF[frameSegundoF].getAbsolutePath());
                        videoCara.setIcon(icon1);
                        if (frameSegundoF == frameMas) {
                            isRunning = false;
                        }
                        frameSegundoF += 1;
                        contadorMinutero(true);
                        //System.out.println("FRAMES: "+ frameSegundoF );
                        
                        calculoTiempo(frameSegundoF);
                       // System.out.println("x del slider: "+ sliderTiempo.getBounds());
                       // //System.out.println("frame que se le suma: " + frameSegundoF);
                        
                        

                        Thread.sleep(captureInterval);
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrokenBarrierException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        };
        //REPRODUCIR PERSPECTIVA 2 ACTIVITY
        Thread activity = new Thread() {
            public void run() {
                try {
                    gate.await();
                   // //System.out.println("--Comienzo grabaciones <ActivityRender> --");
                    fA = new File(ruta + "/storeActivityRender/");
                    fileLstA = fA.listFiles();
                    while (isRunning) {

                        icon2 = new ImageIcon(fileLstA[frameSegundoA].getAbsolutePath());
                        videoActividad.setIcon(icon2);
                        if (frameSegundoA == frameMas) {
                            isRunning = false;
                        }
                        frameSegundoA += 1;
                        Thread.sleep(captureInterval);
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrokenBarrierException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        //REPRODUCIR PERSPECTIVA 3 EXTERNA
        Thread externa = new Thread() {
            public void run() {
                try {
                    gate.await();
                   // //System.out.println("--Comienzo grabaciones <Perspectiva external> --");
                    fE = new File(ruta + "/storeExternalPerspective");
                    fileLstE = fE.listFiles();
                    while (isRunning) {

                        icon3 = new ImageIcon(fileLstE[frameSegundoE].getAbsolutePath());
                        videoExterno.setIcon(icon3);
                        if (frameSegundoE == frameMas) {
                            isRunning = false;
                        }
                        frameSegundoE += 1;
                        Thread.sleep(captureInterval);
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrokenBarrierException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        if (isRunning == true) {
            face.start();
            activity.start();
            externa.start();
            try {
                gate.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
          //  //System.out.println("all threads started");
        }

    }
    public void agregarTag(){
            
            System.out.println("entre a agregar tag");

            for (int i = 0; i < 40; i++) {
                
                //
                vistaTagManual.add(contenedor.get(minutos).get(i));
                
               // System.out.println("[AGREGAR]TAMAÑO TAG MANUAL"+vistaTagManual.getComponentCount());
                vistaTagManual.revalidate();
                vistaTagManual.repaint();        
            }
            System.out.println("Tiempo: "+ minutos);
            System.out.println("medidas EN AGREGAR TAG: "+ contenedor.get(minutos).get(0).getBounds());
            vistaTagManual.revalidate();
            vistaTagManual.repaint();        

        
    }
    
    public void quitarTag(){
    
        Component[] componentList = vistaTagManual.getComponents();
        //Loop through the components
        for(Component c : componentList){

            //Find the components you want to remove
            if(c instanceof JLabel){

                //Remove it
                //System.out.println("TEXTO EN QUITAR TAG: "+ ((JLabel) c).getText());
                vistaTagManual.remove(c);
                //System.out.println("[ELIMINAR]TAMAÑO TAG MANUAL"+vistaTagManual.getComponentCount());
                
            }
        }

        //IMPORTANT
        vistaTagManual.revalidate();
        vistaTagManual.repaint();        
        System.out.println("termine quitar tag");
    }
    
    public void crearAgregar() {
    
        Border border = BorderFactory.createLineBorder(colorBordeTag, 1);
        //tiempoParaTags
        //outer.add(new ArrayList<[var type]>(inner));

   
        for (int j = 0; j < tiempoParaTags; j++) {
            //System.out.println("minuto: " + j);
           ArrayList<JLabel> arregloTag = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                 
            JLabel tag = new JLabel();
            tag.setText(textoTagManual);
            tag.setText("numA:"+i+"/numC:"+j);
            tag.setOpaque(true);
            tag.setBackground(colorFondoTag);
            tag.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            tag.setBorder(border);
            arregloTag.add(tag);
            
                //System.out.println("tag agregados: "+ arregloTag.size());
        }
            tagsPorMinuto.add(0);
            contenedor.add(new ArrayList<JLabel>(arregloTag));
            System.out.println("CONTENEDOR: "+ contenedor.get(j).get(0).getText());
//            contenedor.add(arregloTag);
        }

   

}
    
 public void contadorMinutero(boolean accion){

     if (accion) {
         //se completo el minuto
         if(contadorTiempo == 600){
             
             System.out.println("entre con: ");
           System.out.println("contadorTiempo: " + contadorTiempo);
             System.out.println("minutos: " + minutos);
             contadorTiempo = 0;
             //agregarTag();
             quitarTag();
             minutos+=1;
            agregarTag();
             
             
             
         }else{
             contadorTiempo+=1;
         }
         
     }
     //retroceder false
     else{
         if(contadorTiempo == 0){
             contadorTiempo = 600;
             quitarTag();
             minutos-=1;
             
             agregarTag();
         }
         else{
             contadorTiempo -= 1;
         }
     
     }
     
    sliderTiempo.setValue(contadorTiempo);
    minutero.setText( String.valueOf(minutos));
 }
    public String calculoTiempo(int frameSegundo) {

        String tiempoFinal = "";
        int minutos = 0;
        int restoMinutos = 0;
        int segundos = 0;
        int restoSegundos = 0;
        int millesimas = 0;

        //para 10FPS
        if (fPS == 10) {
            //minutos

            //hay minutos
            minutos = frameSegundo / 600;
            restoMinutos = frameSegundo - (minutos * 600);

            //hay segundos
            segundos = restoMinutos / 10;
            restoSegundos = restoMinutos - (segundos * 10);

            //hay millesimas
            millesimas = (restoSegundos * 1000) / 10;

            String minutosStr, segundosStr, millesimasStr;
            int cantidadMinutos = Integer.toString(minutos).length();
            int cantidadSegundos = Integer.toString(segundos).length();
            int cantidadMillesimas = Integer.toString(millesimas).length();

            if (cantidadMinutos == 1) {
                minutosStr = "0" + String.valueOf(minutos);
            } else {
                minutosStr = String.valueOf(minutos);
            }

            if (cantidadSegundos == 1) {
                segundosStr = "0" + String.valueOf(segundos);
            } else {
                segundosStr = String.valueOf(segundos);
            }
            if (cantidadMillesimas == 1) {
                millesimasStr = "00" + String.valueOf(millesimas);
            } else if (cantidadMillesimas == 2) {
                millesimasStr = "0" + String.valueOf(millesimas);
            } else {
                millesimasStr = String.valueOf(millesimas);
            }

            tiempoTotal = minutosStr + ":" + segundosStr + ":" + millesimasStr;
           // //System.out.println("TIEMPO TOTAL: " + tiempoTotal);
            jLabel8.setText(tiempoTotal);

        }
        //para 20FPS
        if (fPS == 20) {
            //minutos
            //segundos
            //millisegundos
        }
        //para 25FPS
        if (fPS == 25) {
            //minutos
            //segundos
            //millisegundos
        }

        return tiempoFinal;
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
        jLabel8 = new javax.swing.JLabel();
        vistaPanelPerspectiva = new javax.swing.JPanel();
        vistaPerspectivaActividad = new javax.swing.JPanel();
        videoActividad = new javax.swing.JLabel();
        vistaPerspectivaActividad1 = new javax.swing.JPanel();
        videoExterno = new javax.swing.JLabel();
        vistaTiempo = new javax.swing.JPanel();
        sliderTiempo = new javax.swing.JSlider();
        jPanel1 = new javax.swing.JPanel();
        minutero = new javax.swing.JLabel();

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
            .addComponent(videoCara, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
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

        jLabel8.setFont(new java.awt.Font("Roboto Light", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(200, 230, 201));
        jLabel8.setText("00:00:000");
        vistaPanelPlayer.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

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
            .addComponent(videoActividad, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        vistaPerspectivaActividadLayout.setVerticalGroup(
            vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoActividad, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );

        principal.add(vistaPerspectivaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 0, 450, 370));

        vistaPerspectivaActividad1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout vistaPerspectivaActividad1Layout = new javax.swing.GroupLayout(vistaPerspectivaActividad1);
        vistaPerspectivaActividad1.setLayout(vistaPerspectivaActividad1Layout);
        vistaPerspectivaActividad1Layout.setHorizontalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoExterno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );
        vistaPerspectivaActividad1Layout.setVerticalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoExterno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );

        principal.add(vistaPerspectivaActividad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 0, 460, 370));

        vistaTiempo.setBackground(new java.awt.Color(153, 153, 0));
        vistaTiempo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sliderTiempo.setMaximum(1000);
        sliderTiempo.setMinorTickSpacing(500);
        sliderTiempo.setPaintLabels(true);
        sliderTiempo.setPaintTicks(true);
        sliderTiempo.setSnapToTicks(true);
        sliderTiempo.setToolTipText("1");
        sliderTiempo.setValue(0);
        vistaTiempo.add(sliderTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 60));

        principal.add(vistaTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 1290, 60));

        minutero.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(minutero)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(minutero)
                .addGap(22, 22, 22))
        );

        principal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 80, 60));

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
                //System.out.println("size: " + Toolkit.getDefaultToolkit().getScreenSize());
                String direc = "C:/Users/Katherine/Desktop/paraReproductorMuestras/";
               new Reproductor(direc).setVisible(true);

            }
        });
    }



    class MyMouseAdapter extends MouseAdapter {

        private int innerX, innerY;

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println("PRIMER EVENTO: mousePressed");
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
            //System.out.println("mouseDragged");
            calcBounds(e);

            drawRect = true;
            vistaTagManual.repaint();

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println("TERCER EVENTO EVENTO: mouseReleased");

            calcBounds(e);
            //cuando el mouse se suelta
            drawRect = false;

            //System.out.println("x: " + x);
            //System.out.println("y: " + y);
            //System.out.println("widtg: " + width);
            //System.out.println("height: " + height);
            int numTag = tagsPorMinuto.get(minutos);
            vistaTagManual.getComponent(numTag).setBounds(x, y, width, height);
            //contenedor.get(minutos).get(numTag)
            tagsPorMinuto.set(minutos,numTag + 1);
            

        }

        private void calcBounds(MouseEvent e) {
            //System.out.println("SEGUNDO EVENTO: calcBounds");

            //eje x
            if (Math.min(innerX, e.getX()) < 0) {
                x = 0;
                width = Math.abs(innerX - e.getX());

            } else if (Math.min(innerX, e.getX()) + width > PREF_WIDTH) {

                x = PREF_WIDTH - width;
                width = Math.abs(innerX - e.getX());
            } else {
                x = Math.min(innerX, e.getX());
                width = Math.abs(innerX - e.getX());
            }

            //eje y
            if (Math.min(innerY, e.getY()) < 0) {
                y = 0;
                height = tamanoTag;
            } else if (Math.min(innerY, e.getY()) + height > PREF_HEIGHT) {
                y = PREF_HEIGHT - 53;
                height = tamanoTag;
            } else {
                y = Math.min(innerY, e.getY());
                height = tamanoTag;

            }
        }

    }

    public class SliderSkinDemo2 {

        public JSlider makeUI() {
            UIDefaults d = new UIDefaults();
            d.put("Slider:SliderTrack[Enabled].backgroundPainter", new Painter<JSlider>() {
                @Override
                public void paint(Graphics2D g, JSlider c, int w, int h) {
                    if (mouseDown == false) {

                        //System.out.println("w: " + w);
                        int arc = 10;
                        int trackHeight = 8;
                        int trackWidth = w - 2;
                        int fillTop = 4;
                        int fillLeft = 1;

                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                        g.setStroke(new BasicStroke(1.5f));
                        g.setColor(Color.GRAY);
                        g.fillRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);

                        int fillBottom = fillTop + trackHeight;
                        int fillRight = xPositionForValue(
                                c.getValue(), c,
                                new Rectangle(fillLeft, fillTop, trackWidth, fillBottom - fillTop));

                        g.setColor(Color.ORANGE);
                        g.fillRect(fillLeft + 1, fillTop + 1, fillRight - fillLeft, fillBottom - fillTop);

                        g.setColor(Color.WHITE);
                        g.drawRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);
                    }
                }
                //@see javax/swing/plaf/basic/BasicSliderUI#xPositionForValue(int value)

                protected int xPositionForValue(int value, JSlider slider, Rectangle trackRect) {
                    int posicionAnterior = posicionfinal;
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
                    //System.out.println("posición en x: " + xPosition);
                    int posicionMarcada = xPosition;

                    //play
                    if (isRunning == true) {
                        if (mouseDown == true || flechaArriba == true || flechaAbajo == true || flechaIzq == true || flechaDer == true) {
                            mouseDown = false;
                            flechaArriba = false;
                            flechaAbajo = false;
                            flechaIzq = false;
                            flechaDer = false;
                            posicionfinal = posicionAnterior;
                            return posicionfinal;
                        } else {
                            posicionfinal = posicionMarcada;
                            return posicionfinal;
                        }

                    }

                    //pause  
                    if (isRunning == false) {

                        if (mouseDown == true) {
                            mouseDown = false;
                            posicionfinal = posicionAnterior;
                            return posicionfinal;

                        }
                        if (flechaArriba == true) {
                            if (frameMas >= frameSegundoA + 1) {
                                frameSegundoA = frameSegundoA + 1;
                                
                                
                            } else {
                                frameSegundoA = frameMas;

                            }

                            if (frameMas >= frameSegundoF + 1) {
                                frameSegundoF = frameSegundoF + 1;
                                calculoTiempo(frameSegundoF);
                                //contadorMinutero(true, true);
                                
                            } else {
                                frameSegundoF = frameMas;
                                calculoTiempo(frameSegundoF);

                            }

                            if (frameMas >= frameSegundoE + 1) {
                                frameSegundoE = frameSegundoE + 1;
                            } else {
                                frameSegundoE = frameMas;
                            }
                            posicionfinal = xPosition;
                            flechaArriba = false;

                            cargaFace(false);
                            cargaActivity(false);
                            cargaExterna(false);

                            return posicionfinal;
                        }
                        if (flechaAbajo == true) {
                            if (0 <= frameSegundoA - 1) {
                                frameSegundoA = frameSegundoA - 1;
                            } else {
                                frameSegundoA = 0;
                            }

                            if (0 <= frameSegundoF - 1) {
                                frameSegundoF = frameSegundoF - 1;
                                calculoTiempo(frameSegundoF);
                                //contadorMinutero(false, true);
                            } else {
                                frameSegundoF = 0;
                                calculoTiempo(0);
                            }

                            if (0 <= frameSegundoE - 1) {
                                frameSegundoE = frameSegundoE - 1;
                            } else {
                                frameSegundoE = 0;
                            }
                            posicionfinal = xPosition;
                            flechaAbajo = false;

                            cargaFace(true);
                            cargaActivity(true);
                            cargaExterna(true);
                            return posicionfinal;
                        }
                        if (flechaIzq == true) {

                            if (0 <= frameSegundoA - 1) {
                                frameSegundoA = frameSegundoA - 1;
                            } else {
                                frameSegundoA = 0;
                            }

                            if (0 <= frameSegundoF - 1) {
                                frameSegundoF = frameSegundoF - 1;
                                calculoTiempo(frameSegundoF);
                            } else {
                                frameSegundoF = 0;
                                calculoTiempo(0);
                            }

                            if (0 <= frameSegundoE - 1) {
                                frameSegundoE = frameSegundoE - 1;
                            } else {
                                frameSegundoE = 0;
                            }
                            posicionfinal = xPosition;

                            cargaFace(true);
                            cargaActivity(true);
                            cargaExterna(true);
                            flechaIzq = false;
                            return posicionfinal;
                        }
                        if (flechaDer == true) {
                            if (frameMas >= frameSegundoA + 1) {
                                frameSegundoA = frameSegundoA + 1;
                            } else {
                                frameSegundoA = frameMas;
                            }

                            if (frameMas >= frameSegundoF + 1) {
                                frameSegundoF = frameSegundoF + 1;
                                calculoTiempo(frameSegundoF);
                            } else {
                                frameSegundoF = frameMas;
                                calculoTiempo(frameSegundoF);
                            }

                            if (frameMas >= frameSegundoE + 1) {
                                frameSegundoE = frameSegundoE + 1;
                            } else {
                                frameSegundoE = frameMas;
                            }

                            posicionfinal = xPosition;
                            cargaFace(false);
                            cargaActivity(false);
                            cargaExterna(false);
                            flechaDer = false;
                            return posicionfinal;

                        }

                    }
                    //play

                    return xPosition;

                }
            });

            //JSlider slider = new JSlider(JSlider.HORIZONTAL,FPS_MIN, FPS_MAX, FPS_INIT);
            JSlider slider = new JSlider(JSlider.HORIZONTAL);

            MouseListener[] a = slider.getMouseListeners();
            slider.removeMouseListener(slider.getMouseListeners()[0]);
            slider.removeMouseMotionListener(slider.getMouseMotionListeners()[0]);
            //  slider.removeMouseWheelListener(slider.getMouseWheelListeners()[0]);

            //System.out.println("TAMAÑO: " + a.length);

            slider.putClientProperty("Nimbus.Overrides", d);
            cantFrames();
            return slider;

        }
 public void contadorMinutero2(boolean accion){

     if (accion==false) {
         //se completo el minuto
         if(contadorTiempo == 600){
             
           
             contadorTiempo = 599;
          

         }else{
                  
             contadorTiempo+=1;
         }  }
     //retroceder false
     else{
         if(contadorTiempo == 0){
           
             contadorTiempo = 1;
             
         }
       
         else{
             contadorTiempo -= 1;
         }
     
     }
     
    sliderTiempo.setValue(contadorTiempo);
    minutero.setText( String.valueOf(minutos));
 }
        public void cargaFace(boolean rever) {
            
      
             
            
            //System.out.println("--Comienzo grabaciones <FaceRecorder> --");
            fF = new File(ruta + "/storeFaceRecorder/");
            fileLstF = fF.listFiles();
            icon1 = new ImageIcon(fileLstF[frameSegundoF].getAbsolutePath());
            videoCara.setIcon(icon1);
            contadorMinutero2(rever);
            /*  if(rever == true){frameSegundoF-=1;}
                else { frameSegundoF+=1;}*/

            //System.out.println("FRAME SUMADO: " + frameSegundoF);
            
           
        }

        public void cargaActivity(boolean rever) {
            fA = new File(ruta + "/storeActivityRender/");
            fileLstA = fA.listFiles();
            icon2 = new ImageIcon(fileLstA[frameSegundoA].getAbsolutePath());
            videoActividad.setIcon(icon2);
            /* if(rever == true){
                frameSegundoA-=1;
                }
                else {frameSegundoA+=1;}*/

        }

        public void cargaExterna(boolean rever) {

            fE = new File(ruta + "/storeExternalPerspective");
            fileLstE = fE.listFiles();

            icon3 = new ImageIcon(fileLstE[frameSegundoE].getAbsolutePath());
            videoExterno.setIcon(icon3);
            /* if(rever == true){frameSegundoE-=1;}
                else{frameSegundoE+=1;}*/

        }

        public void cantFrames() {
            //System.out.println("ruta:" + ruta);
            fE = new File(ruta + "/storeExternalPerspective");
            File[] afileLstE = fE.listFiles();
            int tamanoE = toIntExact(afileLstE.length);

            fA = new File(ruta + "/storeActivityRender/");
            File[] afileLstA = fA.listFiles();
            int tamanoA = toIntExact(afileLstA.length);

            fF = new File(ruta + "/storeFaceRecorder/");
            File[] afileLstF = fF.listFiles();
            int tamanoF = toIntExact(afileLstF.length);

            frameMas = Math.min(Math.min(tamanoE, tamanoA), tamanoF);
            //System.out.println("CANTIDAD DE ARCHIVOS: " + frameMas);

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnFinal;
    private javax.swing.JLabel btnPlay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel minutero;
    private javax.swing.JPanel principal;
    private javax.swing.JSlider sliderTiempo;
    private javax.swing.JLabel videoActividad;
    private javax.swing.JLabel videoCara;
    private javax.swing.JLabel videoExterno;
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

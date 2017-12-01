/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import com.github.sarxos.webcam.Webcam;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import player.Reproductor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Katherine
 */
public class VisualizacionMuestras extends javax.swing.JFrame {

    
    public static String storeMuestras = "muestras";
    public static String storeMuestra = "muestra";
    public static String storeActivityRender = "activityRender";
    public static String storeFaceRecorder = "faceRecorder";
    public boolean carpetaPrincipalCreada = false;
    public int cantidadMuestras = 0;
    Webcam webcam;
    boolean isRunning = false;
    boolean repr = true;
    public boolean activityRender = true;
    public boolean faceRecorder = true;
    BufferedImage image2;
    Thread t1, t2;
    int cantidadFrame = 0;

    long tiempoFaceRecorderi, tiempoActivityRenderi, tiempoFaceRecorderf, tiempoActivityRenderf;
    String codigoMuestra, tiempoTotal;

    public File fA, fF;
    public File[] fileLstA, fileLstF;

    //frames que han sido mostrados
    public int frameSegundoA = 0;
    public int frameSegundoF = 0;

    /**
     * Screen Width.
     */
    public static int screenWidth = (int) Toolkit.getDefaultToolkit()
            .getScreenSize().getWidth();

    /**
     * Screen Height.
     */
    public static int screenHeight = (int) Toolkit.getDefaultToolkit()
            .getScreenSize().getHeight();
    //Ancho máximo
    public static int MAX_WIDTH = 320;
    //Alto máximo
    public static int MAX_HEIGHT = 300;
    /**
     * Interval between which the image needs to be captured.
     */
    // 10 FPS Thread.sleep(100);
    // 20 FPS -> (50)
    // 25 FPS -> (40)
    public static int captureInterval = 100;
    public static int fps = 10;
    String nombreCarpeta, nombreMuestraActual;
    public ArrayList<String> rutasMuestras = new ArrayList<>();
    public ArrayList<String> duracionMuestras = new ArrayList<>();
    public ArrayList<String> nombreMuestras = new ArrayList<>();
    public ArrayList<String> nombreIndividuo = new ArrayList<>();
    public ArrayList<String> descripcionMuestra = new ArrayList<>();
    boolean edit = false;
    int muestraTablaActual;
    public boolean AR = false;
    public boolean FR = false;
    public boolean PE = false;
    public int objeto = 0;
    public String nombreProyecto, codigoProyecto, descripcionProyecto, ruta;
    private final String direccion;

    /**
     * Creates new form VentanaPrincipal
     */
    public VisualizacionMuestras(String dir) {
        System.out.println("<<<<<<VISUALIZACION DE MUESTRAS>>>>>");
        this.direccion = dir;
        
        initComponents();
                this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            
            escribirJson(-1);
            System.exit(0);
    }
});
        jPanel2.setVisible(false);
        jPanel8.setVisible(true);
        
        leerJson();

        for (int i = 0; i < rutasMuestras.size(); i++) {
            System.out.println("finalmente1: " + rutasMuestras.get(i));
            System.out.println("finalmente2: " + duracionMuestras.get(i));
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{nombreMuestras.get(i), duracionMuestras.get(i)});
            nombreIndividuo.add("");
            descripcionMuestra.add("");
            

        }
        cantidadMuestras =  rutasMuestras.size();

    }

  
    
      public void leerJson() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(this.direccion+"informacionProyecto.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println("discoteca: " +employeeList);

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
        System.out.println("APROVECHALO: "+ employee);
      
       if (objeto == 1) {

            JSONObject employeeObject1 = (JSONObject) employee.get("proyecto");
            
            //Get employee first name
            nombreProyecto = (String) employeeObject1.get("nombre");
            System.out.println(nombreProyecto);
            ruta = (String) employeeObject1.get("destino");
            System.out.println(ruta);
            codigoProyecto = (String) employeeObject1.get("codigo");
            System.out.println(codigoProyecto);
            descripcionProyecto = (String) employeeObject1.get("descripcion");
            System.out.println(descripcionProyecto);

            

        } if(objeto == 2) {
            JSONObject employeeObject2 = (JSONObject) employee.get("perspectivas");
        
            System.out.println("objeto2: "+ employeeObject2);
            //Get employee first name
            PE = stringToBoolean((String) employeeObject2.get("perspectivaExterna"));
            System.out.println(PE);
            AR = stringToBoolean((String) employeeObject2.get("perspectivaActividad"));
            System.out.println(AR);
            FR = stringToBoolean((String) employeeObject2.get("perspectivaCara"));
            System.out.println(FR);
            
            
            
        }
        if( objeto > 2){
        
               JSONObject employeeObject3 = (JSONObject) employee.get("muestra");
               
        //Get employee first name
        String firstName = (String) employeeObject3.get("ruta");
        rutasMuestras.add(firstName);
        System.out.println(firstName);

        String secondName = (String) employeeObject3.get("tiempo");
        duracionMuestras.add(secondName);
        System.out.println(secondName);

        String tresName = (String) employeeObject3.get("nombre");
        nombreMuestras.add(tresName);
        System.out.println(tresName);
        
        }
        objeto += 1;

    }

    public boolean stringToBoolean(String elemento) {
        if(elemento == "true") {
            return true;

        }
        return false;

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrameMin = new javax.swing.JFrame();
        jPanel6 = new javax.swing.JPanel();
        jButtonDetenerMin = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jFrameMin1 = new javax.swing.JFrame();
        jPanel7 = new javax.swing.JPanel();
        Face = new javax.swing.JLabel();
        Activity = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jFrameMin2 = new javax.swing.JFrame();
        frame = new javax.swing.JPanel();
        jButtonDetenerMin2 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        txtNombreNuevo = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        jButtonDetenerMin3 = new javax.swing.JButton();
        TxtNombreActual = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        TxtCodigoActual = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        TxtCodigoNuevo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        cargando = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcionMuestra = new javax.swing.JTextArea();
        jLabel24 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jButton5 = new javax.swing.JButton();
        nombreMuestra = new javax.swing.JLabel();
        txtNombredelIndividuo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        jFrameMin.setAlwaysOnTop(true);
        jFrameMin.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin.setUndecorated(true);

        jPanel6.setBackground(new java.awt.Color(56, 142, 60));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

        jButtonDetenerMin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDetenerMin.setForeground(java.awt.Color.red);
        jButtonDetenerMin.setText("Detener");
        jButtonDetenerMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetenerMinActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton7.setText("Maximizar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(200, 230, 201));
        jLabel8.setText("00:00:000");

        jLabel20.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(200, 230, 201));
        jLabel20.setText("GRABANDO");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDetenerMin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDetenerMin)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        jLabel18.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(200, 230, 201));
        jLabel18.setText("Visualización de muestras");

        javax.swing.GroupLayout jFrameMinLayout = new javax.swing.GroupLayout(jFrameMin.getContentPane());
        jFrameMin.getContentPane().setLayout(jFrameMinLayout);
        jFrameMinLayout.setHorizontalGroup(
            jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameMinLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jLabel18)
                    .addContainerGap(18, Short.MAX_VALUE)))
        );
        jFrameMinLayout.setVerticalGroup(
            jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameMinLayout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jLabel18)
                    .addContainerGap(51, Short.MAX_VALUE)))
        );

        jFrameMin1.setAlwaysOnTop(true);
        jFrameMin1.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin1.setUndecorated(true);

        jPanel7.setBackground(new java.awt.Color(56, 142, 60));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

        Face.setBackground(new java.awt.Color(255, 153, 51));
        Face.setOpaque(true);

        Activity.setBackground(new java.awt.Color(255, 204, 204));
        Activity.setForeground(new java.awt.Color(255, 204, 255));
        Activity.setOpaque(true);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Delete_1.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("ActivityRender");

        jLabel23.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("FaceRecorder");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(Face, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(Activity, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(76, 76, 76)
                        .addComponent(jLabel3)))
                .addGap(22, 22, 22))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(100, 100, 100)
                    .addComponent(jLabel23)
                    .addContainerGap(571, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(Face, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(Activity, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jLabel23)
                    .addContainerGap(318, Short.MAX_VALUE)))
        );

        jLabel21.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Nombre de la muestra");

        javax.swing.GroupLayout jFrameMin1Layout = new javax.swing.GroupLayout(jFrameMin1.getContentPane());
        jFrameMin1.getContentPane().setLayout(jFrameMin1Layout);
        jFrameMin1Layout.setHorizontalGroup(
            jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameMin1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameMin1Layout.createSequentialGroup()
                    .addGap(238, 238, 238)
                    .addComponent(jLabel21)
                    .addContainerGap(347, Short.MAX_VALUE)))
        );
        jFrameMin1Layout.setVerticalGroup(
            jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameMin1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameMin1Layout.createSequentialGroup()
                    .addGap(161, 161, 161)
                    .addComponent(jLabel21)
                    .addContainerGap(201, Short.MAX_VALUE)))
        );

        jFrameMin2.setAlwaysOnTop(true);
        jFrameMin2.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin2.setUndecorated(true);

        frame.setBackground(new java.awt.Color(56, 142, 60));
        frame.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

        jButtonDetenerMin2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDetenerMin2.setText("Guardar");
        jButtonDetenerMin2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDetenerMin2MouseClicked(evt);
            }
        });
        jButtonDetenerMin2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetenerMin2ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(200, 230, 201));
        jLabel25.setText("Nombre del Proyecto Nuevo");

        txtNombreNuevo.setBackground(new java.awt.Color(56, 142, 60));
        txtNombreNuevo.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtNombreNuevo.setForeground(new java.awt.Color(204, 204, 204));
        txtNombreNuevo.setBorder(null);
        txtNombreNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreNuevoActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(200, 230, 201));
        jLabel26.setText("Nombre del Proyecto Actual:");

        jButtonDetenerMin3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDetenerMin3.setText("Cancelar");
        jButtonDetenerMin3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDetenerMin3MouseClicked(evt);
            }
        });
        jButtonDetenerMin3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetenerMin3ActionPerformed(evt);
            }
        });

        TxtNombreActual.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        TxtNombreActual.setForeground(new java.awt.Color(200, 230, 201));
        TxtNombreActual.setText("Texto actual:");

        jLabel27.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(200, 230, 201));
        jLabel27.setText("Código del proyecto Actual");

        TxtCodigoActual.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        TxtCodigoActual.setForeground(new java.awt.Color(200, 230, 201));
        TxtCodigoActual.setText("Código del proyecto Actual");

        jLabel28.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(200, 230, 201));
        jLabel28.setText("Código del proyecto nuevo");

        TxtCodigoNuevo.setBackground(new java.awt.Color(56, 142, 60));
        TxtCodigoNuevo.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        TxtCodigoNuevo.setForeground(new java.awt.Color(204, 204, 204));
        TxtCodigoNuevo.setBorder(null);
        TxtCodigoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCodigoNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frameLayout = new javax.swing.GroupLayout(frame);
        frame.setLayout(frameLayout);
        frameLayout.setHorizontalGroup(
            frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frameLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButtonDetenerMin3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDetenerMin2)
                        .addGap(70, 70, 70))
                    .addGroup(frameLayout.createSequentialGroup()
                        .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(frameLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(35, 35, 35)
                                .addComponent(TxtNombreActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(frameLayout.createSequentialGroup()
                                .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel28))
                                .addGap(38, 38, 38)
                                .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TxtCodigoActual, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                                    .addComponent(jSeparator4)
                                    .addComponent(jSeparator5)
                                    .addComponent(txtNombreNuevo)
                                    .addComponent(TxtCodigoNuevo))))
                        .addContainerGap(60, Short.MAX_VALUE))))
        );
        frameLayout.setVerticalGroup(
            frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frameLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(TxtNombreActual))
                .addGap(27, 27, 27)
                .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(TxtCodigoActual))
                .addGap(64, 64, 64)
                .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addGroup(frameLayout.createSequentialGroup()
                        .addComponent(txtNombreNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frameLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel28))
                    .addGroup(frameLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(TxtCodigoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                .addGroup(frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDetenerMin2)
                    .addComponent(jButtonDetenerMin3))
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout jFrameMin2Layout = new javax.swing.GroupLayout(jFrameMin2.getContentPane());
        jFrameMin2.getContentPane().setLayout(jFrameMin2Layout);
        jFrameMin2Layout.setHorizontalGroup(
            jFrameMin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameMin2Layout.createSequentialGroup()
                .addComponent(frame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jFrameMin2Layout.setVerticalGroup(
            jFrameMin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(76, 175, 80));
        jPanel8.setToolTipText("");
        jPanel8.setPreferredSize(new java.awt.Dimension(683, 683));

        jLabel5.setBackground(new java.awt.Color(117, 117, 117));
        jLabel5.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(200, 230, 201));
        jLabel5.setText("1. Selecciona una muestra en la tabla de la derecha");

        jLabel11.setBackground(new java.awt.Color(117, 117, 117));
        jLabel11.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(200, 230, 201));
        jLabel11.setText("2. Agrega información adicional a la muestra seleccionada");

        jLabel4.setBackground(new java.awt.Color(117, 117, 117));
        jLabel4.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(200, 230, 201));
        jLabel4.setText("3. Análiza la muestra");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flecha.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(73, 73, 73))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(322, 322, 322))
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(141, 141, 141))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(jLabel5)
                .addGap(40, 40, 40)
                .addComponent(jLabel11)
                .addGap(42, 42, 42)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(252, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 720, 730));

        jPanel2.setBackground(new java.awt.Color(76, 175, 80));
        jPanel2.setToolTipText("");
        jPanel2.setPreferredSize(new java.awt.Dimension(683, 683));

        jButton4.setBackground(new java.awt.Color(121, 85, 72));
        jButton4.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Analizar muestra");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton4.setContentAreaFilled(false);
        jButton4.setOpaque(true);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(200, 230, 201));

        cargando.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        cargando.setForeground(new java.awt.Color(255, 255, 255));

        jLabel19.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Descripción de la muestra");

        txtDescripcionMuestra.setEditable(false);
        txtDescripcionMuestra.setColumns(20);
        txtDescripcionMuestra.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        txtDescripcionMuestra.setForeground(new java.awt.Color(117, 117, 117));
        txtDescripcionMuestra.setRows(5);
        jScrollPane1.setViewportView(txtDescripcionMuestra);

        jLabel24.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Nombre del Individuo");

        jButton5.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Editar ");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusPainted(false);
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        nombreMuestra.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        nombreMuestra.setForeground(new java.awt.Color(255, 255, 255));
        nombreMuestra.setText("Nombre de la muestra");

        txtNombredelIndividuo.setEditable(false);
        txtNombredelIndividuo.setBackground(new java.awt.Color(76, 175, 80));
        txtNombredelIndividuo.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        txtNombredelIndividuo.setForeground(new java.awt.Color(117, 117, 117));
        txtNombredelIndividuo.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(284, 284, 284)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtNombredelIndividuo)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(nombreMuestra)
                                        .addGap(124, 124, 124)))
                                .addComponent(cargando))
                            .addComponent(jLabel24)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(236, 236, 236))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cargando)
                        .addGap(112, 112, 112))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(nombreMuestra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombredelIndividuo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 720, 730));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setForeground(new java.awt.Color(33, 33, 33));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(33, 33, 33));
        jLabel7.setText("LISTA DE MUESTRAS CAPTURADAS");

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO MUESTRA", "DURACIÓN MUESTRA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTable1.setRowHeight(25);
        jTable1.setRowSorter(null);
        jTable1.setSelectionBackground(new java.awt.Color(200, 230, 201));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(10);
        }

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Imagen1.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Settings.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(jLabel1)
                        .addGap(143, 143, 143)
                        .addComponent(jLabel6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(427, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(230, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, 650, 770));

        jPanel5.setBackground(new java.awt.Color(56, 142, 60));

        jLabel15.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(200, 230, 201));
        jLabel15.setText("Visualización de muestras");

        jLabel16.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(200, 230, 201));
        jLabel16.setText("-------->");

        jLabel14.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(200, 230, 201));
        jLabel14.setText("Obtención de muestras");

        jLabel17.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(200, 230, 201));
        jLabel17.setText("-------->");

        jLabel13.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(200, 230, 201));
        jLabel13.setText("Creación del proyecto");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addGap(86, 86, 86))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addContainerGap())
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButtonDetenerMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerMinActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButtonDetenerMinActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_jLabel3MouseClicked
public void escribirJson(int seleccionada) {
        JSONArray employeeList = new JSONArray();
        
        JSONObject paso = new JSONObject();
        paso.put("CreacionProyecto1", "0");
        paso.put("CreacionProyecto2", "0");
        paso.put("ObtencionMuestras", "0");
        paso.put("VisualizacionMuestras", "1");
       
        JSONObject agregarPaso = new JSONObject();
        agregarPaso.put("Paso", paso);
        employeeList.add(agregarPaso);

        JSONObject employeeDetails1 = new JSONObject();
        employeeDetails1.put("nombre", nombreProyecto);
        employeeDetails1.put("destino", ruta);
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
        
        
        for (int i = 0; i < cantidadMuestras; i++) {
            JSONObject employeeDetails3 = new JSONObject();
            employeeDetails3.put("ruta", rutasMuestras.get(i));
            employeeDetails3.put("tiempo", duracionMuestras.get(i));
            employeeDetails3.put("nombre", nombreMuestras.get(i));
            if (i == seleccionada){
            employeeDetails3.put("seleccionada", "true");
            
            }
                
            else{
            employeeDetails3.put("seleccionada", "false");
            }
            
            

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
    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:

        // TODO add your handling code here:
        escribirJson(muestraTablaActual);

        Reproductor reproductor = new Reproductor(direccion);
        reproductor.setVisible(true);
        this.setVisible(false);


    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        if (edit == false) {
            txtNombredelIndividuo.setEditable(true);
            txtDescripcionMuestra.setEditable(true);

            edit = true;

            jButton5.setText("Guardar");
        } else {
            nombreIndividuo.add(muestraTablaActual, txtNombredelIndividuo.getText());
            descripcionMuestra.add(muestraTablaActual, txtDescripcionMuestra.getText());
            txtNombredelIndividuo.setEditable(false);
            txtDescripcionMuestra.setEditable(false);
            edit = false;
            jButton5.setText("Editar");
        }

    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
            jPanel2.setVisible(true);
            jPanel8.setVisible(false);
            if (jButton5.getText() == "Guardar") {

                txtNombredelIndividuo.setEditable(false);
                txtDescripcionMuestra.setEditable(false);
                edit = false;
                jButton5.setText("Editar");
            }
            nombreMuestra.setText(nombreMuestras.get(row));
            txtNombredelIndividuo.setText(nombreIndividuo.get(row));
            txtDescripcionMuestra.setText(descripcionMuestra.get(row));
            muestraTablaActual = row;
            System.out.println("row: " + row);
            System.out.println("col: " + col);
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButtonDetenerMin2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDetenerMin2MouseClicked
        // TODO add your handling code here:
            if(txtNombreNuevo.getText()!= null){
                nombreProyecto = txtNombreNuevo.getText();
            }
            if(TxtCodigoNuevo.getText()!=null){
                codigoProyecto = TxtCodigoNuevo.getText();
            }
            
            
        jFrameMin2.setVisible(false);
        setVisible(true);
            
    }//GEN-LAST:event_jButtonDetenerMin2MouseClicked

    private void jButtonDetenerMin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerMin2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDetenerMin2ActionPerformed

    private void txtNombreNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreNuevoActionPerformed

    private void jButtonDetenerMin3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDetenerMin3MouseClicked
        // TODO add your handling code here:

             jFrameMin2.setVisible(false);
        setVisible(true);;
    }//GEN-LAST:event_jButtonDetenerMin3MouseClicked

    private void jButtonDetenerMin3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerMin3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDetenerMin3ActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        System.out.println("nombreProyecto: "+nombreProyecto);
        System.out.println("codigoProyecto:"+codigoProyecto);
         TxtNombreActual.setText(nombreProyecto);
         TxtCodigoActual.setText(codigoProyecto);
         txtNombreNuevo.setText("");
         TxtCodigoNuevo.setText("");
         jFrameMin2.setSize(594, 507);
        
        jFrameMin2.setLocationRelativeTo(null);
        
        jFrameMin2.setVisible(true);
        setVisible(false);

        
        
    }//GEN-LAST:event_jLabel6MouseClicked

    private void TxtCodigoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCodigoNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCodigoNuevoActionPerformed

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
            java.util.logging.Logger.getLogger(VisualizacionMuestras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisualizacionMuestras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisualizacionMuestras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisualizacionMuestras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new VisualizacionMuestras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Activity;
    private javax.swing.JLabel Face;
    private javax.swing.JLabel TxtCodigoActual;
    private javax.swing.JTextField TxtCodigoNuevo;
    private javax.swing.JLabel TxtNombreActual;
    private javax.swing.JLabel cargando;
    private javax.swing.JPanel frame;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButtonDetenerMin;
    private javax.swing.JButton jButtonDetenerMin2;
    private javax.swing.JButton jButtonDetenerMin3;
    private javax.swing.JFrame jFrameMin;
    private javax.swing.JFrame jFrameMin1;
    private javax.swing.JFrame jFrameMin2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel nombreMuestra;
    private javax.swing.JTextArea txtDescripcionMuestra;
    private javax.swing.JTextField txtNombreNuevo;
    private javax.swing.JTextField txtNombredelIndividuo;
    // End of variables declaration//GEN-END:variables
}

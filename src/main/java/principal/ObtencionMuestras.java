/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.imageio.ImageIO;
import java.awt.Robot;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.ImageIcon;
import static jmapps.ui.ImageArea.loadImage;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Katherine
 */
public class ObtencionMuestras extends javax.swing.JFrame {

    public static String storeMuestras = "muestras";
    public static String storeMuestra = "muestra";
    public static String storeActivityRender = "activityRender";
    public static String storeFaceRecorder = "faceRecorder";
    public static String storeExternalPerspective = "ExternalPerspective";
    public boolean carpetaPrincipalCreada = false;
    public int cantidadMuestras = 0;
    Webcam webcamPC, webcamCelu;
    boolean isRunning = false;
    boolean repr = true;
    public boolean activityRender = true;
    public boolean faceRecorder = true;
    public boolean externalPerspective = true;
    BufferedImage image2, image3;
    Thread t1, t2, t3;
    int cantidadFrame = 0;
    final CyclicBarrier gate = new CyclicBarrier(4);
    final CyclicBarrier gate2 = new CyclicBarrier(4);
    long tiempoFaceRecorderi, tiempoActivityRenderi, tiempoFaceRecorderf, tiempoActivityRenderf;
    String codigoMuestra, tiempoTotal;

    public File fA, fF, fE;
    public File[] fileLstA, fileLstF, fileLstE;
    public ImageIcon icon, icon2;
    //frames que han sido mostrados
    public int frameSegundoA = 0;
    public int frameSegundoF = 0;
    public int frameSegundoE = 0;
    public ArrayList<String> rutasMuestras = new ArrayList<>();
    public ArrayList<String> nombreMuestras = new ArrayList<>();
    public ArrayList<String> duracionMuestras = new ArrayList<>();

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
    public boolean AR = false;
    public boolean FR = false;
    public boolean PE = false;
    public int objeto = 0;
    public String nombreProyecto, codigoProyecto, descripcionProyecto, ruta;
    private final String direccion;
    /**
     * Creates new form VentanaPrincipal
     */
    public ObtencionMuestras(String dir) {
        this.direccion = dir;
        leerJson();
        
        initComponents();
        creacionCarpetas();

    }

    public void leerJson() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(this.direccion+"informacionProyecto.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

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

        if (objeto == 0) {

            JSONObject employeeObject = (JSONObject) employee.get("proyecto");

            //Get employee first name
            nombreProyecto = (String) employeeObject.get("nombre");
            System.out.println(nombreProyecto);
            ruta = (String) employeeObject.get("destino");
            System.out.println(ruta);
            codigoProyecto = (String) employeeObject.get("codigo");
            System.out.println(codigoProyecto);
            descripcionProyecto = (String) employeeObject.get("descripcion");
            System.out.println(descripcionProyecto);

            objeto += 1;

        } else {
            JSONObject employeeObject = (JSONObject) employee.get("perspectivas");

            //Get employee first name
            PE = stringToBoolean((String) employeeObject.get("perspectivaExterna"));
            System.out.println(PE);
            AR = stringToBoolean((String) employeeObject.get("perspectivaActividad"));
            System.out.println(AR);
            FR = stringToBoolean((String) employeeObject.get("perspectivaCara"));
            System.out.println(FR);
            
        }

    }

    public boolean stringToBoolean(String elemento) {
        if(elemento == "true") {
            return true;

        }
        return false;

    }

    public void escribirJson() {
        JSONArray employeeList = new JSONArray();

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

    public void creacionCarpetas() {
        /*
    |-muestras(carpeta)
    |--m1(carpeta)
    |---acvtivityRender(carpeta)
    |---faceRecorder(carpeta)
    |---videoAcvtivityRender(archivo)
    |---videoFaceRecorder(archivo)
    |--m2(carpeta)
    |---acvtivityRender(carpeta)
    |---faceRecorder(carpeta)
    |---videoAcvtivityRender(archivo)
    |---videoFaceRecorder(archivo)
    |--m3(carpeta)
    |---acvtivityRender(carpeta)
    |---faceRecorder(carpeta)
    |---videoAcvtivityRender(archivo)
    |---videoFaceRecorder(archivo)
    
    
         */
    
            //creacion de carpetas de muestra
            nombreMuestraActual = this.direccion + "Muestra" + String.valueOf(cantidadMuestras);
            File f2 = new File(nombreMuestraActual);
            f2.mkdir();
            System.out.println("se creo el directorio de muestras: " + nombreMuestraActual);

            if (activityRender) {
                File f3 = new File(nombreMuestraActual + "/" + "storeActivityRender");
                f3.mkdir();
                System.out.println("se creo el directorio de muestras: " + nombreMuestraActual + "/" + "storeActivityRender");
                storeActivityRender = nombreMuestraActual + "/" + "storeActivityRender" + "/";
            }
            if (faceRecorder) {
                File f3 = new File(nombreMuestraActual + "/" + "storeFaceRecorder");
                f3.mkdir();
                System.out.println("se creo el directorio de muestras: " + nombreMuestraActual + "/" + "storeFaceRecorder");
                storeFaceRecorder = nombreMuestraActual + "/" + "storeFaceRecorder" + "/";
            }
            if (externalPerspective) {
                File f3 = new File(nombreMuestraActual + "/" + "storeExternalPerspective");
                f3.mkdir();
                System.out.println("se creo el directorio de muestras: " + nombreMuestraActual + "/" + "storeExternalPerspective");
                storeExternalPerspective = nombreMuestraActual + "/" + "storeExternalPerspective" + "/";
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

        jFrameMin = new javax.swing.JFrame();
        jPanel6 = new javax.swing.JPanel();
        jButtonDetenerMin = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jFrameMin1 = new javax.swing.JFrame();
        jPanel7 = new javax.swing.JPanel();
        Face = new javax.swing.JLabel();
        Activity = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        Externa = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jButton6 = new javax.swing.JButton();
        cargando = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
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
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel20))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jButtonDetenerMin))
                            .addComponent(jLabel8))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonDetenerMin)
                .addContainerGap())
        );

        javax.swing.GroupLayout jFrameMinLayout = new javax.swing.GroupLayout(jFrameMin.getContentPane());
        jFrameMin.getContentPane().setLayout(jFrameMinLayout);
        jFrameMinLayout.setHorizontalGroup(
            jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameMinLayout.setVerticalGroup(
            jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        Externa.setBackground(new java.awt.Color(255, 153, 51));
        Externa.setOpaque(true);

        jLabel24.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Perspectiva Externa");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(Face, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(Activity, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(Externa, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(249, 249, 249)
                .addComponent(jLabel24)
                .addGap(45, 45, 45))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(31, 31, 31))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 65, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(Activity, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Externa, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Face, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))))
        );

        javax.swing.GroupLayout jFrameMin1Layout = new javax.swing.GroupLayout(jFrameMin1.getContentPane());
        jFrameMin1.getContentPane().setLayout(jFrameMin1Layout);
        jFrameMin1Layout.setHorizontalGroup(
            jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameMin1Layout.setVerticalGroup(
            jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameMin1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(76, 175, 80));
        jPanel2.setToolTipText("");
        jPanel2.setPreferredSize(new java.awt.Dimension(683, 683));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nombre de la muestra");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(200, 230, 201));
        jLabel2.setText("cod1Muestra01");

        jButton2.setBackground(new java.awt.Color(56, 142, 60));
        jButton2.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Iniciar captura");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jButton2.setOpaque(true);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Vista previa de la captura");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Duración de la muestra");

        jLabel6.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(200, 230, 201));
        jLabel6.setText("00:00:000");

        jButton5.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Descartar captura");
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

        jSeparator1.setBackground(new java.awt.Color(200, 230, 201));

        jSeparator2.setBackground(new java.awt.Color(200, 230, 201));

        jButton6.setBackground(new java.awt.Color(56, 142, 60));
        jButton6.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Detener captura");
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.setOpaque(true);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        cargando.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        cargando.setForeground(new java.awt.Color(255, 255, 255));

        jButton4.setBackground(new java.awt.Color(121, 85, 72));
        jButton4.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Guardar captura");
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jLabel19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cargando)
                        .addGap(141, 141, 141))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cargando))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(75, 75, 75)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addGap(80, 80, 80)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(117, 117, 117))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 720, 730));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setForeground(new java.awt.Color(33, 33, 33));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(33, 33, 33));
        jLabel7.setText("LISTA DE MUESTRAS CAPTURADAS");

        jButton1.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton1.setText("Análizar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(33, 33, 33)));
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
        jTable1.setSelectionBackground(new java.awt.Color(200, 230, 201));
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(10);
        }

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Imagen1.png"))); // NOI18N

        jButton8.setBackground(new java.awt.Color(121, 85, 72));
        jButton8.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Análizar muestra");
        jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton8.setContentAreaFilled(false);
        jButton8.setOpaque(true);
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(330, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(224, 224, 224)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(110, Short.MAX_VALUE))))
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

        jLabel15.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(200, 230, 201));
        jLabel15.setText("Visualización de muestras");

        jLabel16.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(200, 230, 201));
        jLabel16.setText("-------->");

        jLabel14.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        creacionCarpetas();
        System.out.println("Se iniciaran las grabaciones (se aumento en 1 la cantidad de muestras");
        cargando.setText("Cargando...");
        cantidadMuestras += 1;
        codigoMuestra = "cod1Muestra" + String.valueOf(cantidadMuestras);
        jLabel2.setText(codigoMuestra);
        isRunning = true;
        cantidadFrame = 0;
        System.out.println("Se crearan las carpetas");
        
        System.out.println("Carpetas creadas");

        System.out.println("Se iniciaran los thread");
        //FACERECORDEMSMARTPHONE
        t3 = new Thread() {
            @Override
            public void run() {

                try {
                    gate.await();
                    System.out.println("****** Inicio captura de muestras face Celular ******");
                    System.out.println("Se debio haber abierto la camara");
                    long timeFRC = 0;

                    while (isRunning) {
                        try {

                            image3 = webcamCelu.getImage();
                            timeFRC = System.currentTimeMillis();
                            String ruta = storeExternalPerspective + timeFRC + ".jpg";
                            ImageIO.write(image3, "jpg", new File(ruta));
                            copyImage(ruta, ruta);
                            Thread.sleep(captureInterval);

                        } catch (InterruptedException ex) {
                            //Logger.getLogger(CameraTest.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            // Logger.getLogger(FaceRecorder.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    System.out.println("Captura de cara finalizada");

                } catch (InterruptedException ex) {
                    Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrokenBarrierException ex) {
                    Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        //FACERECORDERWEBCAM
        t1 = new Thread() {
            @Override
            public void run() {

                try {
                    gate.await();
                    System.out.println("****** Inicio captura de muestras face PC ******");
                    System.out.println("Se debio haber abierto la camara");
                    long timeAR = 0;
                    jFrameMin.setSize(198, 110);
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
                    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
                    int x = (int) rect.getMaxX() - jFrameMin.getWidth();
                    int y = (int) rect.getMaxY() - jFrameMin.getHeight();
                    jFrameMin.setLocation(x, y);
                    jFrameMin.setVisible(true);
                    setVisible(false);

                    while (isRunning) {
                        try {

                            cantidadFrame += 1;
                            image2 = webcamPC.getImage();
                            timeAR = System.currentTimeMillis();
                            ImageIO.write(image2, "jpg", new File(storeFaceRecorder + timeAR + ".jpg"));
                            calculoTiempo(cantidadFrame);
                            Thread.sleep(captureInterval);

                        } catch (InterruptedException ex) {
                            //Logger.getLogger(CameraTest.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            // Logger.getLogger(FaceRecorder.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    System.out.println("Captura de cara finalizada");
                    tiempoFaceRecorderf = System.currentTimeMillis() - tiempoFaceRecorderi;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrokenBarrierException ex) {
                    Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        //ACTIVIY RENDER
        t2 = new Thread() {
            @Override
            public void run() {
                System.out.println("****** Inicio captura de muestras activity ******");
                long timeAR = 0;
                tiempoActivityRenderi = System.currentTimeMillis();

                try {
                    gate.await();
                    Robot rt;
                    try {
                        rt = new Robot();
                        while (isRunning) {
                            BufferedImage img = rt.createScreenCapture(new Rectangle(screenWidth, screenHeight));

                            timeAR = System.currentTimeMillis();
                            String name = storeActivityRender + String.valueOf(timeAR) + ".jpeg";
                            ImageIO.write(img, "jpeg", new File(name));

                            copyImage(name, name);

                            // System.out.println(record);
                            Thread.sleep(captureInterval);
                        }
                        System.out.println("Captura de pantalla finalizada");
                        tiempoActivityRenderf = System.currentTimeMillis() - tiempoActivityRenderi;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception ex) {
                    Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        System.out.println("Antes de abrir la camara");

//webcam = Webcam.getDefault();
        webcamPC = Webcam.getWebcamByName("HP Truevision HD 0");
        // System.out.println("TAMAÑOS CELULAR"+webcamCelu.getCustomViewSizes());

        webcamCelu = Webcam.getWebcamByName("DroidCam Source 3 1");

        webcamPC.setViewSize(new Dimension(320, 240));
        //webcamCelu.setViewSize(new Dimension(320,240));

        webcamPC.open(true);
        webcamCelu.open(true);

        if (webcamPC.isOpen() && webcamCelu.isOpen()) {
            System.out.println("Despues de abrir la camara");
            cargando.setText("Grabando");
            t1.start();
            t2.start();
            t3.start();
        }

        try {
            gate.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("all threads started");
        System.out.println("CLICK");
        //   jTable1.addRow(new Object[] { "data1", "data2"});
        // jTable1.


    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        isRunning = false;
        System.out.println("Deteniendo capturas");

        webcamPC.open(false);
        webcamPC.close();
        webcamCelu.open(false);
        webcamCelu.close();
        //gate.wait();
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();

        calculoTiempo(cantidadFrame);
        int tiempo = cantidadFrame / 10;
        System.out.println("Segundos que deberia durar: " + tiempo + " segundos");
        // jLabel6.setText(String.valueOf(tiempo)+" segundos");
        cargando.setText(String.valueOf(tiempo));


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButtonDetenerMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerMinActionPerformed
        // TODO add your handling code here:
        setVisible(true);
        jFrameMin.setVisible(false);
        jButton6ActionPerformed(evt);
    }//GEN-LAST:event_jButtonDetenerMinActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        //vista previa
        jFrameMin1.setSize(1086, 391);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - jFrameMin1.getWidth();
        int y = (int) rect.getMaxY() - jFrameMin1.getHeight();
        //jFrameMin1.setLocation(x, y);
        jFrameMin1.setVisible(true);
        setVisible(false);
        repr = true;

        Thread ta = new Thread() {
            public void run() {
                try {
                    gate2.await();
                    //do stuff 
                    System.out.println("Entre al hilo Vista previa activyty");
                    fA = new File(storeActivityRender);
                    fileLstA = fA.listFiles();

                    System.out.println("cantidad de imagenes: " + fileLstA.length);
                    while (repr) {
                        System.out.println("Entre al while");

                        try {

                            icon = new ImageIcon(fileLstA[frameSegundoA].getAbsolutePath());
                            Activity.setIcon(icon);
                            if (frameSegundoA == fileLstA.length - 1) {
                                repr = false;
                                //detener = true;
                            }
                            frameSegundoA += 1;

                            // 10 FPS Thread.sleep(100);
                            Thread.sleep(captureInterval);

                        } catch (InterruptedException ex) {
                            //Logger.getLogger(CameraTest.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    setVisible(true);
                    jFrameMin1.setVisible(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ObtencionMuestras.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrokenBarrierException ex) {
                    Logger.getLogger(ObtencionMuestras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread tb = new Thread() {
            public void run() {
                try {
                    gate2.await();
                    //do stuff   
                    System.out.println("Entre al hilo");
                    fF = new File(storeFaceRecorder);
                    fileLstF = fF.listFiles();

                    System.out.println("cantidad de imagenes: " + fileLstF.length);
                    while (repr) {
                        System.out.println("Entre al while2");
                        try {

                            icon = new ImageIcon(fileLstF[frameSegundoF].getAbsolutePath());
                            Face.setIcon(icon);
                            if (frameSegundoF == fileLstF.length - 1) {
                                repr = false;
                                //detener = true;
                            }
                            frameSegundoF += 1;

                            // 10 FPS Thread.sleep(100);
                            Thread.sleep(captureInterval);

                        } catch (InterruptedException ex) {
                            //Logger.getLogger(CameraTest.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    setVisible(true);
                    jFrameMin1.setVisible(false);

                } catch (InterruptedException ex) {
                    Logger.getLogger(ObtencionMuestras.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrokenBarrierException ex) {
                    Logger.getLogger(ObtencionMuestras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread tc = new Thread() {
            public void run() {
                try {
                    gate2.await();
                    //do stuff   
                    System.out.println("Entre al hilo");
                    fE = new File(storeExternalPerspective);
                    fileLstE = fE.listFiles();

                    System.out.println("cantidad de imagenes: " + fileLstE.length);
                    while (repr) {
                        System.out.println("Entre al while2");
                        try {

                            icon = new ImageIcon(fileLstE[frameSegundoE].getAbsolutePath());
                            Externa.setIcon(icon);
                            if (frameSegundoE == fileLstF.length - 1) {
                                repr = false;
                                //detener = true;
                            }
                            frameSegundoE += 1;

                            // 10 FPS Thread.sleep(100);
                            Thread.sleep(captureInterval);

                        } catch (InterruptedException ex) {
                            //Logger.getLogger(CameraTest.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    setVisible(true);
                    jFrameMin1.setVisible(false);

                } catch (InterruptedException ex) {
                    Logger.getLogger(ObtencionMuestras.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrokenBarrierException ex) {
                    Logger.getLogger(ObtencionMuestras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        ta.start();
        tb.start();
        tc.start();

        try {
            // At this point, t1 and t2 are blocking on the gate.
// Since we gave "3" as the argument, gate is not opened yet.
// Now if we block on the gate from the main thread, it will open
// and all threads will start to do stuff!

            gate2.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(ObtencionMuestras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(ObtencionMuestras.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("all threads started");


    }//GEN-LAST:event_jButton3MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        setVisible(true);
        jFrameMin1.setVisible(false);
        repr = true;
        frameSegundoA = 0;
        frameSegundoF = 0;
        frameSegundoE = 0;


    }//GEN-LAST:event_jLabel3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{codigoMuestra, tiempoTotal});
        rutasMuestras.add(nombreMuestraActual);
        duracionMuestras.add(tiempoTotal);
        nombreMuestras.add("Muestra" + String.valueOf(cantidadMuestras));


    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        System.out.println("nombreCarpeta: " + nombreMuestraActual);
        Path path = Paths.get(nombreMuestraActual);
        try {
            //Files.delete(path);

            FileUtils.deleteDirectory(new File(nombreMuestraActual));
            if (cantidadMuestras - 1 != 0) {
                cantidadMuestras = cantidadMuestras - 1;
            } else {
                cantidadMuestras = 0;
            }
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
         escribirJson();

        VisualizacionMuestras visualizacionMuestras = new VisualizacionMuestras();
        visualizacionMuestras.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    public static void copyImage(String filePath, String copyPath) {
        BufferedImage bimage = loadImage(filePath);
        if (bimage.getHeight() > bimage.getWidth()) {
            int heigt = (bimage.getHeight() * MAX_WIDTH) / bimage.getWidth();
            bimage = resize(bimage, MAX_WIDTH, heigt);
            int width = (bimage.getWidth() * MAX_HEIGHT) / bimage.getHeight();
            bimage = resize(bimage, width, MAX_HEIGHT);
        } else {
            int width = (bimage.getWidth() * MAX_HEIGHT) / bimage.getHeight();
            bimage = resize(bimage, width, MAX_HEIGHT);
            int heigt = (bimage.getHeight() * MAX_WIDTH) / bimage.getWidth();
            bimage = resize(bimage, MAX_WIDTH, heigt);
        }
        saveImage(bimage, copyPath);
    }

    /*
    Este método se utiliza para cargar la imagen de disco
     */
    public static BufferedImage loadImage(String pathName) {
        BufferedImage bimage = null;
        try {
            bimage = ImageIO.read(new File(pathName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimage;
    }

    /*
    Este método se utiliza para almacenar la imagen en disco
     */
    public static void saveImage(BufferedImage bufferedImage, String pathName) {
        try {
            String format = (pathName.endsWith(".png")) ? "png" : "jpg";
            File file = new File(pathName);
            file.getParentFile().mkdirs();
            ImageIO.write(bufferedImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Este método se utiliza para redimensionar la imagen
     */
    public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }

    public String calculoTiempo(int frameSegundo) {

        String tiempoFinal = "";
        int minutos = 0;
        int restoMinutos = 0;
        int segundos = 0;
        int restoSegundos = 0;
        int millesimas = 0;

        //para 10FPS
        if (fps == 10) {
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
            jLabel6.setText(tiempoTotal);
            jLabel8.setText(tiempoTotal);

        }
        //para 20FPS
        if (fps == 20) {
            //minutos
            //segundos
            //millisegundos
        }
        //para 25FPS
        if (fps == 25) {
            //minutos
            //segundos
            //millisegundos
        }

        return tiempoFinal;
    }

    static public String formatMillis(long val) {
        StringBuilder buf = new StringBuilder(20);
        String sgn = "";

        if (val < 0) {
            sgn = "-";
            val = Math.abs(val);
        }

        append(buf, sgn, 0, (val / 3600000));
        append(buf, ":", 2, ((val % 3600000) / 60000));
        append(buf, ":", 2, ((val % 60000) / 1000));
        append(buf, ".", 3, (val % 1000));
        return buf.toString();
    }

    static private void append(StringBuilder tgt, String pfx, int dgt, long val) {
        tgt.append(pfx);
        if (dgt > 1) {
            int pad = (dgt - 1);
            for (long xa = val; xa > 9 && pad > 0; xa /= 10) {
                pad--;
            }
            for (int xa = 0; xa < pad; xa++) {
                tgt.append('0');
            }
        }
        tgt.append(val);
    }

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
            java.util.logging.Logger.getLogger(ObtencionMuestras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ObtencionMuestras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ObtencionMuestras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ObtencionMuestras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new ObtencionMuestras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Activity;
    private javax.swing.JLabel Externa;
    private javax.swing.JLabel Face;
    private javax.swing.JLabel cargando;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButtonDetenerMin;
    private javax.swing.JFrame jFrameMin;
    private javax.swing.JFrame jFrameMin1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

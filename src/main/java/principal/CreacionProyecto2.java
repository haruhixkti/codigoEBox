/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import com.github.sarxos.webcam.Webcam;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import javax.swing.ImageIcon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Katherine
 */
public class CreacionProyecto2 extends javax.swing.JFrame {

    
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
        nombreCarpeta = ruta+"/"+nombreProyecto + "Muestras";
  
            //creacion de carpetas de muestra
            nombreMuestraActual = nombreCarpeta + "/Muestra" + String.valueOf(cantidadMuestras);
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
    public CreacionProyecto2(String dir) {
        System.out.println("<<<<<<CREACIÓN DEL PROYECTO 2>>>>>");
        this.direccion = dir;
        initComponents();
        
        leerJson();

    }
    public void fn(){
    
    System.out.println("NUMEROSdsñkfjlsdjflsdkjflksdjfds: "+ direccion);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(76, 175, 80));
        jPanel2.setToolTipText("");
        jPanel2.setPreferredSize(new java.awt.Dimension(683, 683));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Face recorder");

        jLabel18.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Activity render");

        jLabel20.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Agregar perspectiva");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel18)
                    .addComponent(jLabel12))
                .addContainerGap(402, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(69, 69, 69)
                .addComponent(jLabel18)
                .addGap(100, 100, 100)
                .addComponent(jLabel20)
                .addGap(434, 434, 434))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 720, 730));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setForeground(new java.awt.Color(33, 33, 33));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(33, 33, 33));
        jLabel7.setText("CREACIÓN DEL PROYECTO");

        jLabel3.setBackground(new java.awt.Color(117, 117, 117));
        jLabel3.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(117, 117, 117));
        jLabel3.setText("computador");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Imagen1.png"))); // NOI18N

        jButton1.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jButton1.setText("Crear proyecto");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(33, 33, 33)));
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(117, 117, 117));
        jLabel5.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(117, 117, 117));
        jLabel5.setText("Seleccione las perspectivas que desea análizar. Puede elegir");

        jLabel11.setBackground(new java.awt.Color(117, 117, 117));
        jLabel11.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(117, 117, 117));
        jLabel11.setText("la captura desde la webcam y la captura de la pantalla del");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addComponent(jLabel4))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(157, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(78, 78, 78)
                .addComponent(jLabel7)
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(155, 155, 155)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
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

        jLabel14.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(200, 230, 201));
        jLabel14.setText("Obtención de muestras");

        jLabel17.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(200, 230, 201));
        jLabel17.setText("-------->");

        jLabel13.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(200, 230, 201));
        jLabel13.setText("Creación del proyecto");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addGap(84, 84, 84))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addContainerGap(12, Short.MAX_VALUE))
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

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        escribirJson();
        ObtencionMuestras obtencionMuestras = new ObtencionMuestras(direccion);
        obtencionMuestras.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_jButton1MouseClicked
    public void leerJson() {
        fn();
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(direccion+"informacionProyecto.json")) {
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
        JSONObject employeeObject = (JSONObject) employee.get("proyecto");

        //Get employee first name
        nombreProyecto = (String) employeeObject.get("nombre");
        System.out.println("NOMBRE leido: "+ nombreProyecto);
        ruta = (String) employeeObject.get("destino");
        codigoProyecto = (String) employeeObject.get("codigo");
        descripcionProyecto= (String) employeeObject.get("descripcion");
        

    }
    public void escribirJson() {
        JSONArray employeeList = new JSONArray();

        JSONObject employeeDetails1 = new JSONObject();
        System.out.println("nombreProyecto escrito: "+ nombreProyecto);
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

        try (FileWriter file = new FileWriter(direccion +"informacionProyecto.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            java.util.logging.Logger.getLogger(CreacionProyecto2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreacionProyecto2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreacionProyecto2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreacionProyecto2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                //new CreacionProyecto2(5).setVisible(true);        
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}

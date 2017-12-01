/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import io.indico.Indico;
import io.indico.api.image.FacialEmotion;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.List;
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
import java.awt.event.ComponentListener;
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
import java.net.MalformedURLException;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.MediaLocator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import static oracle.jrockit.jfr.events.Bits.intValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import principal.VisualizacionMuestras;
import java.util.Map.Entry;
import javax.swing.JOptionPane;
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
    static public Color colorFondoTag = new Color(121, 85, 72);
    static public Color colorBordeTag = new Color(189, 189, 189);
    static public Color colorTxtTag = new Color(200, 230, 201);
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
    public int time = 0;

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
    public int e = -1;
    public ArrayList<Integer> tagsPorMinuto = new ArrayList<>();
    public int cantidadTags = 0;
    int lista[] = {0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17, 17, 18, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 30, 30, 31, 31, 32, 32, 33, 33, 33, 34, 34, 35, 35, 36, 36, 37, 37, 38, 38, 39, 39, 40, 40, 41, 41, 41, 42, 42, 43, 43, 44, 44, 45, 45, 46, 46, 47, 47, 48, 48, 48, 49, 49, 50, 50, 51, 51, 52, 52, 53, 53, 54, 54, 55, 55, 56, 56, 56, 57, 57, 58, 58, 59, 59, 60, 60, 61, 61, 62, 62, 63, 63, 64, 64, 64, 65, 65, 66, 66, 67, 67, 68, 68, 69, 69, 70, 70, 71, 71, 72, 72, 72, 73, 73, 74, 74, 75, 75, 76, 76, 77, 77, 78, 78, 79, 79, 79, 80, 80, 81, 81, 82, 82, 83, 83, 84, 84, 85, 85, 86, 86, 87, 87, 87, 88, 88, 89, 89, 90, 90, 91, 91, 92, 92, 93, 93, 94, 94, 95, 95, 95, 96, 96, 97, 97, 98, 98, 99, 99, 100, 100, 101, 101, 102, 102, 102, 103, 103, 104, 104, 105, 105, 106, 106, 107, 107, 108, 108, 109, 109, 110, 110, 110, 111, 111, 112, 112, 113, 113, 114, 114, 115, 115, 116, 116, 117, 117, 118, 118, 118, 119, 119, 120, 120, 121, 121, 122, 122, 123, 123, 124, 124, 125, 125, 125, 126, 126, 127, 127, 128, 128, 129, 129, 130, 130, 131, 131, 132, 132, 133, 133, 133, 134, 135, 136, 136, 136, 136, 137, 137, 138, 138, 139, 139, 140, 140, 141, 141, 141, 142, 142, 143, 143, 144, 144, 145, 145, 146, 146, 147, 147, 148, 148, 148, 149, 149, 150, 150, 151, 151, 152, 152, 153, 153, 154, 154, 155, 155, 156, 156, 156, 157, 157, 158, 158, 159, 159, 160, 160, 161, 161, 162, 162, 163, 163, 164, 164, 164, 165, 165, 166, 166, 167, 167, 168, 168, 169, 169, 170, 170, 171, 171, 172, 172, 172, 173, 173, 174, 174, 175, 175, 176, 176, 177, 177, 178, 178, 179, 179, 179, 180, 180, 181, 181, 182, 182, 183, 183, 184, 184, 185, 185, 186, 186, 187, 187, 187, 188, 188, 189, 189, 190, 190, 191, 191, 192, 192, 193, 193, 194, 194, 195, 195, 195, 196, 196, 197, 197, 198, 198, 199, 199, 200, 200, 201, 201, 202, 202, 202, 203, 203, 204, 204, 205, 205, 206, 206, 207, 207, 208, 208, 209, 209, 210, 210, 210, 211, 211, 212, 212, 213, 213, 214, 214, 215, 215, 216, 216, 217, 217, 218, 218, 218, 219, 219, 220, 220, 221, 221, 222, 222, 223, 223, 224, 224, 225, 225, 225, 226, 226, 227, 227, 228, 228, 229, 229, 230, 230, 231, 231, 232, 232, 233, 233, 233, 234, 234, 235, 235, 236, 236, 237, 237, 238, 238, 239, 239, 240, 240, 241, 241, 241, 242, 242, 243, 243, 244, 244, 245, 245, 246, 246, 247, 247, 248, 248, 248, 249, 249, 250, 250, 251, 251, 252, 252, 253, 253, 254, 254, 255, 255, 256, 256, 256, 257, 257, 258, 258, 259, 259, 260, 260, 261, 261, 262, 262, 263, 263, 264, 264, 264, 265, 265, 266, 266, 267, 267, 268, 268, 269, 269, 270, 270, 271, 271, 272, 272, 272, 273, 273, 274, 274, 275, 275, 276, 276, 277, 277, 278, 278, 279, 279, 279, 280, 280, 281, 281, 282, 282, 283, 283, 284, 284, 285, 285, 286, 286, 287, 287, 287, 288, 288, 289, 289, 290, 290, 291, 291, 292, 292, 293, 293, 294, 294, 295, 295, 295, 296, 296, 297, 297, 298, 298, 299, 299, 300, 300, 301, 301, 302, 302, 302, 303, 303, 304, 304, 305, 305, 306, 306, 307, 307, 308, 308, 309, 309, 310, 310, 310, 311, 311, 312, 312, 313, 313, 314, 314, 315, 315, 316, 316, 317, 317, 318, 318, 318, 319, 319, 320, 320, 321, 321, 322, 322, 323, 323, 324, 324, 325, 325, 325, 326, 326, 327, 327, 328, 328, 329, 329, 330, 330, 331, 331, 332, 332, 333, 333, 333, 334, 334, 335, 335, 336, 336, 337, 337, 338, 338, 339, 339, 340, 340, 341, 341, 341, 342, 342, 343, 343, 344, 344, 345, 345, 346, 346, 347, 347, 348, 348, 348, 349, 349, 350, 350, 351, 351, 352, 352, 353, 353, 354, 354, 355, 355, 356, 356, 356, 357, 357, 358, 358, 359, 359, 360, 360, 361, 361, 362, 362, 363, 363, 364, 364, 364, 365, 365, 366, 366, 367, 367, 368, 368, 369, 369, 370, 370, 371, 371, 372, 372, 372, 373, 373, 374, 374, 375, 375, 376, 376, 377, 377, 378, 378, 379, 379, 379, 380, 380, 381, 381, 382, 382, 383, 383, 384, 384, 385, 385, 386, 386, 387, 387, 387, 388, 388, 389, 389, 390, 390, 391, 391, 392, 392, 393, 393, 394, 394, 395, 395, 395, 396, 396, 397, 397, 398, 398, 399, 399, 400, 400, 401, 401, 402, 402, 402, 403, 403, 404, 404, 405, 405, 406, 406, 407, 407, 408, 408, 409, 409, 410, 410, 410, 411, 411, 412, 412, 413, 413, 414, 414, 415, 415, 416, 416, 417, 417, 418, 418, 418, 419, 419, 420, 420, 421, 421, 422, 422, 423, 423, 424, 424, 425, 425, 425, 426, 426, 427, 427, 428, 428, 429, 429, 430, 430, 431, 431, 432, 432, 433, 433, 433, 434, 434, 435, 435, 436, 436, 437, 437, 438, 438, 439, 439, 440, 440, 441, 441, 441, 442, 442, 443, 443, 444, 444, 445, 445, 446, 446, 447, 447, 448, 448, 448, 449, 449, 450, 450, 451, 451, 452, 452, 453, 453, 454, 454, 455, 455, 456, 456, 456, 457, 457, 458, 458, 459, 459, 460, 460, 461, 461, 462, 462, 463, 463, 464, 464, 464, 465, 465, 466, 466, 467, 467, 468, 468, 469, 469, 470, 470, 471, 471, 472, 472, 472, 473, 473, 474, 474, 475, 475, 476, 476, 477, 477, 478, 478, 479, 479, 479, 480, 480, 481, 481, 482, 482, 483, 483, 484, 484, 485, 485, 486, 486, 487, 487, 487, 488, 488, 489, 489, 490, 490, 491, 491, 492, 492, 493, 493, 494, 494, 495, 495, 495, 496, 496, 497, 497, 498, 498, 499, 499, 500, 500, 501, 501, 502, 502, 502, 503, 503, 504, 504, 505, 505, 506, 506, 507, 507, 508, 508, 509, 509, 510, 510, 510, 511, 511, 512, 512, 513, 513, 514, 514, 515, 515, 516, 516, 517, 517, 518, 518, 518, 519, 519, 520, 520, 521, 521, 522, 522, 523, 523, 524, 524, 525, 525, 525, 526, 526, 527, 527, 528, 528, 529, 529, 530, 530, 531, 531, 532, 532, 533, 533, 533, 534, 534, 535, 535, 536, 536, 537, 537, 538, 538, 539, 539, 540, 540, 541, 541, 541, 542, 542, 543, 543, 544, 544, 545, 545, 546, 546, 547, 547, 548, 548, 548, 549, 549, 550, 550, 551, 551, 552, 552, 553, 553, 554, 554, 555, 555, 556, 556, 556, 557, 557, 558, 558, 559, 559, 560, 560, 561, 561, 562, 562, 563, 563, 564, 564, 564, 565, 565, 566, 566, 567, 567, 568, 568, 569, 569, 570, 570, 571, 571, 572, 572, 572, 573, 573, 574, 574, 575, 575, 576, 576, 577, 577, 578, 578, 579, 579, 579, 580, 580, 581, 581, 582, 582, 583, 583, 584, 584, 585, 585, 586, 586, 587, 587, 587, 588, 588, 589, 589, 590, 590, 591, 591, 592, 592, 593, 593, 594, 594, 595, 595, 595, 596, 596, 597, 597, 598, 599};
    int posMuestraElegida;
    public int frameX[];
    public ArrayList<Integer> felicidad1 = new ArrayList<Integer>();
    public ArrayList<Integer> sorpresa1 = new ArrayList<Integer>();
    public ArrayList<Integer> tristeza1 = new ArrayList<Integer>();
    public ArrayList<Integer> temeroso1 = new ArrayList<Integer>();
    public ArrayList<Integer> enojado1 = new ArrayList<Integer>();
    public ArrayList<Integer> neutral1 = new ArrayList<Integer>();
    
    
         
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

    public Reproductor(String dir) {
        //System.out.println("<<<<<<REPRODUCTOR DE MUESTRAS>>>>>");

        this.direccion = dir;
        System.out.println("DIRECCION: " + this.direccion);
        //initComponents();

        leerJson();
        iniciarComponentes();
        inicioTiempoTag.setVisible(false);
        finTiempoTag.setVisible(false);

    }

    public void asignacion() {
        //this.frameX[posicionX]= frame
        int lista[] = {0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17, 17, 18, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 30, 30, 31, 31, 32, 32, 33, 33, 33, 34, 34, 35, 35, 36, 36, 37, 37, 38, 38, 39, 39, 40, 40, 41, 41, 41, 42, 42, 43, 43, 44, 44, 45, 45, 46, 46, 47, 47, 48, 48, 48, 49, 49, 50, 50, 51, 51, 52, 52, 53, 53, 54, 54, 55, 55, 56, 56, 56, 57, 57, 58, 58, 59, 59, 60, 60, 61, 61, 62, 62, 63, 63, 64, 64, 64, 65, 65, 66, 66, 67, 67, 68, 68, 69, 69, 70, 70, 71, 71, 72, 72, 72, 73, 73, 74, 74, 75, 75, 76, 76, 77, 77, 78, 78, 79, 79, 79, 80, 80, 81, 81, 82, 82, 83, 83, 84, 84, 85, 85, 86, 86, 87, 87, 87, 88, 88, 89, 89, 90, 90, 91, 91, 92, 92, 93, 93, 94, 94, 95, 95, 95, 96, 96, 97, 97, 98, 98, 99, 99, 100, 100, 101, 101, 102, 102, 102, 103, 103, 104, 104, 105, 105, 106, 106, 107, 107, 108, 108, 109, 109, 110, 110, 110, 111, 111, 112, 112, 113, 113, 114, 114, 115, 115, 116, 116, 117, 117, 118, 118, 118, 119, 119, 120, 120, 121, 121, 122, 122, 123, 123, 124, 124, 125, 125, 125, 126, 126, 127, 127, 128, 128, 129, 129, 130, 130, 131, 131, 132, 132, 133, 133, 133, 134, 135, 136, 136, 136, 136, 137, 137, 138, 138, 139, 139, 140, 140, 141, 141, 141, 142, 142, 143, 143, 144, 144, 145, 145, 146, 146, 147, 147, 148, 148, 148, 149, 149, 150, 150, 151, 151, 152, 152, 153, 153, 154, 154, 155, 155, 156, 156, 156, 157, 157, 158, 158, 159, 159, 160, 160, 161, 161, 162, 162, 163, 163, 164, 164, 164, 165, 165, 166, 166, 167, 167, 168, 168, 169, 169, 170, 170, 171, 171, 172, 172, 172, 173, 173, 174, 174, 175, 175, 176, 176, 177, 177, 178, 178, 179, 179, 179, 180, 180, 181, 181, 182, 182, 183, 183, 184, 184, 185, 185, 186, 186, 187, 187, 187, 188, 188, 189, 189, 190, 190, 191, 191, 192, 192, 193, 193, 194, 194, 195, 195, 195, 196, 196, 197, 197, 198, 198, 199, 199, 200, 200, 201, 201, 202, 202, 202, 203, 203, 204, 204, 205, 205, 206, 206, 207, 207, 208, 208, 209, 209, 210, 210, 210, 211, 211, 212, 212, 213, 213, 214, 214, 215, 215, 216, 216, 217, 217, 218, 218, 218, 219, 219, 220, 220, 221, 221, 222, 222, 223, 223, 224, 224, 225, 225, 225, 226, 226, 227, 227, 228, 228, 229, 229, 230, 230, 231, 231, 232, 232, 233, 233, 233, 234, 234, 235, 235, 236, 236, 237, 237, 238, 238, 239, 239, 240, 240, 241, 241, 241, 242, 242, 243, 243, 244, 244, 245, 245, 246, 246, 247, 247, 248, 248, 248, 249, 249, 250, 250, 251, 251, 252, 252, 253, 253, 254, 254, 255, 255, 256, 256, 256, 257, 257, 258, 258, 259, 259, 260, 260, 261, 261, 262, 262, 263, 263, 264, 264, 264, 265, 265, 266, 266, 267, 267, 268, 268, 269, 269, 270, 270, 271, 271, 272, 272, 272, 273, 273, 274, 274, 275, 275, 276, 276, 277, 277, 278, 278, 279, 279, 279, 280, 280, 281, 281, 282, 282, 283, 283, 284, 284, 285, 285, 286, 286, 287, 287, 287, 288, 288, 289, 289, 290, 290, 291, 291, 292, 292, 293, 293, 294, 294, 295, 295, 295, 296, 296, 297, 297, 298, 298, 299, 299, 300, 300, 301, 301, 302, 302, 302, 303, 303, 304, 304, 305, 305, 306, 306, 307, 307, 308, 308, 309, 309, 310, 310, 310, 311, 311, 312, 312, 313, 313, 314, 314, 315, 315, 316, 316, 317, 317, 318, 318, 318, 319, 319, 320, 320, 321, 321, 322, 322, 323, 323, 324, 324, 325, 325, 325, 326, 326, 327, 327, 328, 328, 329, 329, 330, 330, 331, 331, 332, 332, 333, 333, 333, 334, 334, 335, 335, 336, 336, 337, 337, 338, 338, 339, 339, 340, 340, 341, 341, 341, 342, 342, 343, 343, 344, 344, 345, 345, 346, 346, 347, 347, 348, 348, 348, 349, 349, 350, 350, 351, 351, 352, 352, 353, 353, 354, 354, 355, 355, 356, 356, 356, 357, 357, 358, 358, 359, 359, 360, 360, 361, 361, 362, 362, 363, 363, 364, 364, 364, 365, 365, 366, 366, 367, 367, 368, 368, 369, 369, 370, 370, 371, 371, 372, 372, 372, 373, 373, 374, 374, 375, 375, 376, 376, 377, 377, 378, 378, 379, 379, 379, 380, 380, 381, 381, 382, 382, 383, 383, 384, 384, 385, 385, 386, 386, 387, 387, 387, 388, 388, 389, 389, 390, 390, 391, 391, 392, 392, 393, 393, 394, 394, 395, 395, 395, 396, 396, 397, 397, 398, 398, 399, 399, 400, 400, 401, 401, 402, 402, 402, 403, 403, 404, 404, 405, 405, 406, 406, 407, 407, 408, 408, 409, 409, 410, 410, 410, 411, 411, 412, 412, 413, 413, 414, 414, 415, 415, 416, 416, 417, 417, 418, 418, 418, 419, 419, 420, 420, 421, 421, 422, 422, 423, 423, 424, 424, 425, 425, 425, 426, 426, 427, 427, 428, 428, 429, 429, 430, 430, 431, 431, 432, 432, 433, 433, 433, 434, 434, 435, 435, 436, 436, 437, 437, 438, 438, 439, 439, 440, 440, 441, 441, 441, 442, 442, 443, 443, 444, 444, 445, 445, 446, 446, 447, 447, 448, 448, 448, 449, 449, 450, 450, 451, 451, 452, 452, 453, 453, 454, 454, 455, 455, 456, 456, 456, 457, 457, 458, 458, 459, 459, 460, 460, 461, 461, 462, 462, 463, 463, 464, 464, 464, 465, 465, 466, 466, 467, 467, 468, 468, 469, 469, 470, 470, 471, 471, 472, 472, 472, 473, 473, 474, 474, 475, 475, 476, 476, 477, 477, 478, 478, 479, 479, 479, 480, 480, 481, 481, 482, 482, 483, 483, 484, 484, 485, 485, 486, 486, 487, 487, 487, 488, 488, 489, 489, 490, 490, 491, 491, 492, 492, 493, 493, 494, 494, 495, 495, 495, 496, 496, 497, 497, 498, 498, 499, 499, 500, 500, 501, 501, 502, 502, 502, 503, 503, 504, 504, 505, 505, 506, 506, 507, 507, 508, 508, 509, 509, 510, 510, 510, 511, 511, 512, 512, 513, 513, 514, 514, 515, 515, 516, 516, 517, 517, 518, 518, 518, 519, 519, 520, 520, 521, 521, 522, 522, 523, 523, 524, 524, 525, 525, 525, 526, 526, 527, 527, 528, 528, 529, 529, 530, 530, 531, 531, 532, 532, 533, 533, 533, 534, 534, 535, 535, 536, 536, 537, 537, 538, 538, 539, 539, 540, 540, 541, 541, 541, 542, 542, 543, 543, 544, 544, 545, 545, 546, 546, 547, 547, 548, 548, 548, 549, 549, 550, 550, 551, 551, 552, 552, 553, 553, 554, 554, 555, 555, 556, 556, 556, 557, 557, 558, 558, 559, 559, 560, 560, 561, 561, 562, 562, 563, 563, 564, 564, 564, 565, 565, 566, 566, 567, 567, 568, 568, 569, 569, 570, 570, 571, 571, 572, 572, 572, 573, 573, 574, 574, 575, 575, 576, 576, 577, 577, 578, 578, 579, 579, 579, 580, 580, 581, 581, 582, 582, 583, 583, 584, 584, 585, 585, 586, 586, 587, 587, 587, 588, 588, 589, 589, 590, 590, 591, 591, 592, 592, 593, 593, 594, 594, 595, 595, 595, 596, 596, 597, 597, 598, 599};

    }

    public void leerJson() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(this.direccion + "informacionProyecto.json")) {
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
        if (objeto == 1) {

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
        if (objeto == 2) {
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
        if (objeto > 2) {
            JSONObject employeeObject3 = (JSONObject) employee.get("muestra");

            //Get employee first name
            String firstNamea = (String) employeeObject3.get("ruta");
            rutasMuestras.add(firstNamea);
            //System.out.println(firstNamea);

            String secondNamea = (String) employeeObject3.get("tiempo");
            duracionMuestras.add(secondNamea);
            //System.out.println("tiempo: "+secondNamea);

            String tresNamea = (String) employeeObject3.get("nombre");
            nombreMuestras.add(tresNamea);
            //System.out.println(tresNamea);

            String seleccionada = (String) employeeObject3.get("seleccionada");

            if ("true".equals(seleccionada)) {

                numeroSeleccionada = muestra;
                System.out.println("pos OBjeto seleccionado: " + numeroSeleccionada);
                String firstName = (String) employeeObject3.get("ruta");
                ruta = firstName;
                System.out.println(firstName);

                String secondName = (String) employeeObject3.get("tiempo");
                tiempo = secondName;
                System.out.println(secondName);
                String[] parts = secondNamea.split(":");
                System.out.println("partes: " + parts);
                tiempoParaTags = Integer.parseInt(parts[0]);

                String tresName = (String) employeeObject3.get("nombre");
                nombre = tresName;
                System.out.println(tresName);

            }
            muestra += 1;

        }
        objeto += 1;

    }

    public boolean stringToBoolean(String elemento) {
        if (elemento == "true") {
            return true;

        }
        return false;

    }

    public int convercionPosicionFrame(int pos) {
        System.out.println("pos que llega a conversion: " + pos);
        int frameFinal = 0;
        frameFinal = lista[pos];
        System.out.println("Lista en la pos 0: " + frameFinal);

        return frameFinal;

    }
        public int convercionFramePosicion(int frame) {
        System.out.println("pos que llega a conversion: " + frame);
        int posFinal = 0;
        boolean first = true;
            for (int i = 0; i < lista.length; i++) {
                if(frame == lista[i] && first == true){
                    first = false;
                    posFinal = lista[i];
                }
            }
        
        System.out.println("Lista en la pos 0: " + posFinal);

        return posFinal;

    }

    public void escribirTags() {

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

        try (FileWriter file = new FileWriter(ruta + "/informacionTags.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void escribirJson(boolean paraBorrar) {
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

        for (int i = 0; i < rutasMuestras.size(); i++) {
            //paraBorrar = True Borra
            //paraBorrar = False No borra

            if (paraBorrar) {
                if (numeroSeleccionada != i) {
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

            } else {

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

        }
        try (FileWriter file = new FileWriter(this.direccion + "informacionProyecto.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void iniciarComponentes() {

        jFrameMin = new javax.swing.JFrame();
        editarMarcas = new javax.swing.JPanel();
        jButtonDetenerMin = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jButtonDetenerMin1 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jFrameMin1 = new javax.swing.JFrame();
        editarMarcas1 = new javax.swing.JPanel();
        jButtonDetenerMin2 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        textoEditarNuevo1 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        jButtonDetenerMin3 = new javax.swing.JButton();
        textoEditarActual1 = new javax.swing.JLabel();
        jFrameMin2 = new javax.swing.JFrame();
        editarMarcas2 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        iconCargando = new javax.swing.JLabel();
        principal = new javax.swing.JPanel();
        vistaPerspectivaCara = new javax.swing.JPanel();
        videoCara = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        vistaTagAutomatico = new javax.swing.JPanel();
        vistaTagManual = new javax.swing.JPanel();
        vistaConfTagAutomatico = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        vistaConfTagManual = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        vistaPanelTag = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        agregarMarcaTxT = new javax.swing.JLabel();
        editarMarcasBtn = new javax.swing.JLabel();
        editarMarcasTxt = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        vistaPanelPlayer = new javax.swing.JPanel();
        btnPlay = new javax.swing.JLabel();
        avanzarBtn = new javax.swing.JLabel();
        retrocederBtn = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        finTiempoTag = new javax.swing.JLabel();
        inicioTiempoTag = new javax.swing.JLabel();
        vistaPanelPerspectiva = new javax.swing.JPanel();
        eliminarMuestraTxt = new javax.swing.JLabel();
        eliminarMuestraBtn = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        obtenerEpisodiosBtn = new javax.swing.JLabel();
        cambiarMuestraBtn = new javax.swing.JLabel();
        cambiarMuestraTxt = new javax.swing.JLabel();
        obtenerEpisodiosTxt = new javax.swing.JLabel();
        vistaPerspectivaActividad = new javax.swing.JPanel();
        videoActividad = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        vistaPerspectivaActividad1 = new javax.swing.JPanel();
        videoExterno = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        vistaTiempo = new javax.swing.JPanel();

        jPanel1 = new javax.swing.JPanel();
        minutero = new javax.swing.JLabel();

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
        jFrameMin.setAlwaysOnTop(true);
        jFrameMin.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin.setUndecorated(true);

        editarMarcas.setBackground(new java.awt.Color(56, 142, 60));
        editarMarcas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

        jButtonDetenerMin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDetenerMin.setText("Iniciar análisis");
        jButtonDetenerMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDetenerMinMouseClicked(evt);
            }
        });
        jButtonDetenerMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetenerMinActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(200, 230, 201));
        jLabel22.setText("Análisis de sentimiento automático");

        jButtonDetenerMin1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDetenerMin1.setText("Cancelar");
        jButtonDetenerMin1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDetenerMin1MouseClicked(evt);
            }
        });
        jButtonDetenerMin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetenerMin1ActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(200, 230, 201));
        jLabel27.setText("Seleccionar sentimiento a analizar");

        jComboBox1.setBackground(new java.awt.Color(56, 142, 60));
        jComboBox1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"felicidad", "tristeza", "enfado", "miedo", "sorpresa", "neutro"}));
        jComboBox1.setBorder(null);
        jComboBox1.setFocusable(false);
        jComboBox1.setOpaque(false);

        jLabel33.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(200, 230, 201));
        jLabel33.setText("¡Advertencia!");

        jLabel34.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(200, 230, 201));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("El uso de esta funcionalidad requiere de una conexión a internet");

        jLabel35.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(200, 230, 201));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("El uso de esta funcionalidad toma un tiempo considerable.");

        javax.swing.GroupLayout editarMarcasLayout = new javax.swing.GroupLayout(editarMarcas);
        editarMarcas.setLayout(editarMarcasLayout);
        editarMarcasLayout.setHorizontalGroup(
                editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editarMarcasLayout.createSequentialGroup()
                                .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(editarMarcasLayout.createSequentialGroup()
                                                .addGap(103, 103, 103)
                                                .addComponent(jLabel22))
                                        .addGroup(editarMarcasLayout.createSequentialGroup()
                                                .addGap(135, 135, 135)
                                                .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(editarMarcasLayout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(editarMarcasLayout.createSequentialGroup()
                                                .addComponent(jButtonDetenerMin1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButtonDetenerMin)
                                                .addGap(75, 75, 75))
                                        .addGroup(editarMarcasLayout.createSequentialGroup()
                                                .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(editarMarcasLayout.createSequentialGroup()
                                                                .addGap(114, 114, 114)
                                                                .addComponent(jLabel33))
                                                        .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap(58, Short.MAX_VALUE))))
        );
        editarMarcasLayout.setVerticalGroup(
                editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editarMarcasLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel22)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel27)
                                .addGap(27, 27, 27)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel33)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel35)
                                .addGap(19, 19, 19)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonDetenerMin)
                                        .addComponent(jButtonDetenerMin1))
                                .addGap(63, 63, 63))
        );

        javax.swing.GroupLayout jFrameMinLayout = new javax.swing.GroupLayout(jFrameMin.getContentPane());
        jFrameMin.getContentPane().setLayout(jFrameMinLayout);
        jFrameMinLayout.setHorizontalGroup(
                jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(editarMarcas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jFrameMinLayout.setVerticalGroup(
                jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameMinLayout.createSequentialGroup()
                                .addComponent(editarMarcas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        jFrameMin1.setAlwaysOnTop(true);
        jFrameMin1.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin1.setUndecorated(true);

        editarMarcas1.setBackground(new java.awt.Color(56, 142, 60));
        editarMarcas1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

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

        jLabel24.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(200, 230, 201));
        jLabel24.setText("Nuevo texto");

        textoEditarNuevo1.setBackground(new java.awt.Color(56, 142, 60));
        textoEditarNuevo1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        textoEditarNuevo1.setForeground(new java.awt.Color(204, 204, 204));
        textoEditarNuevo1.setBorder(null);
        textoEditarNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoEditarNuevo1ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(200, 230, 201));
        jLabel26.setText("Texto actual:");

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

        textoEditarActual1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        textoEditarActual1.setForeground(new java.awt.Color(200, 230, 201));
        textoEditarActual1.setText("Texto actual:");

        javax.swing.GroupLayout editarMarcas1Layout = new javax.swing.GroupLayout(editarMarcas1);
        editarMarcas1.setLayout(editarMarcas1Layout);
        editarMarcas1Layout.setHorizontalGroup(
                editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editarMarcas1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(editarMarcas1Layout.createSequentialGroup()
                                                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editarMarcas1Layout.createSequentialGroup()
                                                                .addComponent(jLabel26)
                                                                .addGap(35, 35, 35)
                                                                .addComponent(textoEditarActual1))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editarMarcas1Layout.createSequentialGroup()
                                                                .addComponent(jLabel24)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(textoEditarNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(editarMarcas1Layout.createSequentialGroup()
                                                                .addGap(81, 81, 81)
                                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(editarMarcas1Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jButtonDetenerMin3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                                                .addComponent(jButtonDetenerMin2)
                                                .addGap(70, 70, 70))))
        );
        editarMarcas1Layout.setVerticalGroup(
                editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editarMarcas1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel26)
                                        .addComponent(textoEditarActual1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel24)
                                        .addComponent(textoEditarNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonDetenerMin2)
                                        .addComponent(jButtonDetenerMin3))
                                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout jFrameMin1Layout = new javax.swing.GroupLayout(jFrameMin1.getContentPane());
        jFrameMin1.getContentPane().setLayout(jFrameMin1Layout);
        jFrameMin1Layout.setHorizontalGroup(
                jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(editarMarcas1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jFrameMin1Layout.setVerticalGroup(
                jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameMin1Layout.createSequentialGroup()
                                .addComponent(editarMarcas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        jFrameMin2.setAlwaysOnTop(true);
        jFrameMin2.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin2.setUndecorated(true);

        editarMarcas2.setBackground(new java.awt.Color(56, 142, 60));
        editarMarcas2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

        jLabel28.setFont(new java.awt.Font("Roboto Light", 0, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(200, 230, 201));
        jLabel28.setText("Cargando");

        iconCargando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cargando.png"))); // NOI18N

        javax.swing.GroupLayout editarMarcas2Layout = new javax.swing.GroupLayout(editarMarcas2);
        editarMarcas2.setLayout(editarMarcas2Layout);
        editarMarcas2Layout.setHorizontalGroup(
                editarMarcas2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editarMarcas2Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(iconCargando)
                                .addContainerGap(68, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editarMarcas2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel28)
                                .addGap(135, 135, 135))
        );
        editarMarcas2Layout.setVerticalGroup(
                editarMarcas2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editarMarcas2Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel28)
                                .addGap(52, 52, 52)
                                .addComponent(iconCargando)
                                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFrameMin2Layout = new javax.swing.GroupLayout(jFrameMin2.getContentPane());
        jFrameMin2.getContentPane().setLayout(jFrameMin2Layout);
        jFrameMin2Layout.setHorizontalGroup(
                jFrameMin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(editarMarcas2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameMin2Layout.setVerticalGroup(
                jFrameMin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameMin2Layout.createSequentialGroup()
                                .addComponent(editarMarcas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        principal.setPreferredSize(new java.awt.Dimension(1366, 768));
        principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vistaPerspectivaCara.setBackground(new java.awt.Color(33, 33, 33));
        vistaPerspectivaCara.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Face Recorder");

        javax.swing.GroupLayout vistaPerspectivaCaraLayout = new javax.swing.GroupLayout(vistaPerspectivaCara);
        vistaPerspectivaCara.setLayout(vistaPerspectivaCaraLayout);
        vistaPerspectivaCaraLayout.setHorizontalGroup(
                vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                                .addGroup(vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                                                .addGap(134, 134, 134)
                                                .addComponent(jLabel6))
                                        .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                                                .addGap(65, 65, 65)
                                                .addComponent(videoCara, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(68, Short.MAX_VALUE))
        );
        vistaPerspectivaCaraLayout.setVerticalGroup(
                vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addGap(42, 42, 42)
                                .addComponent(videoCara, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(46, Short.MAX_VALUE))
        );

        principal.add(vistaPerspectivaCara, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 455, 370));

        vistaTagAutomatico.setBackground(new java.awt.Color(76, 175, 80));

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

        vistaTagManual.setBackground(new java.awt.Color(56, 142, 60));

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

        vistaConfTagAutomatico.setBackground(new java.awt.Color(56, 142, 60));
        vistaConfTagAutomatico.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(189, 189, 189), 1, true));
        vistaConfTagAutomatico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Automático");
        vistaConfTagAutomatico.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        principal.add(vistaConfTagAutomatico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 80, 50));

        vistaConfTagManual.setBackground(new java.awt.Color(76, 175, 80));
        vistaConfTagManual.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(189, 189, 189), 1, true));
        vistaConfTagManual.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("Manual");
        vistaConfTagManual.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        principal.add(vistaConfTagManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 80, 50));

        vistaPanelTag.setBackground(new java.awt.Color(255, 255, 255));
        vistaPanelTag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregarTag.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        agregarMarcaTxT.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        agregarMarcaTxT.setForeground(new java.awt.Color(33, 33, 33));
        agregarMarcaTxT.setText("Agregar marcas");
        agregarMarcaTxT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agregarMarcaTxTMouseClicked(evt);
            }
        });

        editarMarcasBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editarTag.png"))); // NOI18N
        editarMarcasBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editarMarcasBtnMouseClicked(evt);
            }
        });

        editarMarcasTxt.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        editarMarcasTxt.setForeground(new java.awt.Color(33, 33, 33));
        editarMarcasTxt.setText("Editar marcas");
        editarMarcasTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editarMarcasTxtMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(33, 33, 33));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Help.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(33, 33, 33));
        jLabel13.setText("Menú marcas");

        javax.swing.GroupLayout vistaPanelTagLayout = new javax.swing.GroupLayout(vistaPanelTag);
        vistaPanelTag.setLayout(vistaPanelTagLayout);
        vistaPanelTagLayout.setHorizontalGroup(
                vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(vistaPanelTagLayout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(agregarMarcaTxT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(editarMarcasTxt)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPanelTagLayout.createSequentialGroup()
                                                .addComponent(editarMarcasBtn)
                                                .addGap(17, 17, 17)))
                                .addGap(71, 71, 71))
                        .addGroup(vistaPanelTagLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12)
                                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(vistaPanelTagLayout.createSequentialGroup()
                                                .addGap(46, 46, 46)
                                                .addComponent(jLabel4))
                                        .addGroup(vistaPanelTagLayout.createSequentialGroup()
                                                .addGap(125, 125, 125)
                                                .addComponent(jLabel13))))
        );
        vistaPanelTagLayout.setVerticalGroup(
                vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(vistaPanelTagLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(vistaPanelTagLayout.createSequentialGroup()
                                                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(vistaPanelTagLayout.createSequentialGroup()
                                                                .addGap(42, 42, 42)
                                                                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(editarMarcasBtn)
                                                                        .addComponent(jLabel4)))
                                                        .addComponent(jLabel13))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(editarMarcasTxt)
                                                        .addComponent(agregarMarcaTxT)))
                                        .addComponent(jLabel12))
                                .addGap(0, 107, Short.MAX_VALUE))
        );

        principal.add(vistaPanelTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 470, 240));

        vistaPanelPlayer.setBackground(new java.awt.Color(255, 255, 255));
        vistaPanelPlayer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));
        vistaPanelPlayer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Play-50.png"))); // NOI18N
        btnPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlayMouseClicked(evt);
            }
        });
        vistaPanelPlayer.add(btnPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, -1, 50));

        avanzarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-End-50.png"))); // NOI18N
        avanzarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                avanzarBtnMouseClicked(evt);
            }
        });
        vistaPanelPlayer.add(avanzarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, -1));

        retrocederBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-begin.png"))); // NOI18N
        retrocederBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                retrocederBtnMouseClicked(evt);
            }
        });
        vistaPanelPlayer.add(retrocederBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Roboto Light", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(33, 33, 33));
        jLabel8.setText("00:00:000");
        vistaPanelPlayer.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel25.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(33, 33, 33));
        jLabel25.setText("1 minuto");
        vistaPanelPlayer.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 80, -1));

        jLabel29.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(33, 33, 33));
        jLabel29.setText("Retroceder");
        vistaPanelPlayer.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 90, -1));

        jLabel30.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(33, 33, 33));
        jLabel30.setText("1 minuto");
        vistaPanelPlayer.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 70, -1));

        jLabel31.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(33, 33, 33));
        jLabel31.setText("Play");
        vistaPanelPlayer.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 40, -1));

        jLabel32.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(33, 33, 33));
        jLabel32.setText("Avanzar");
        vistaPanelPlayer.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 70, -1));

        finTiempoTag.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        finTiempoTag.setForeground(new java.awt.Color(33, 33, 33));
        finTiempoTag.setText("00:00:000");
        vistaPanelPlayer.add(finTiempoTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));

        inicioTiempoTag.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        inicioTiempoTag.setForeground(new java.awt.Color(33, 33, 33));
        inicioTiempoTag.setText("00:00:000");
        vistaPanelPlayer.add(inicioTiempoTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        principal.add(vistaPanelPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 530, 425, 240));

        vistaPanelPerspectiva.setBackground(new java.awt.Color(255, 255, 255));
        vistaPanelPerspectiva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        eliminarMuestraTxt.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        eliminarMuestraTxt.setForeground(new java.awt.Color(33, 33, 33));
        eliminarMuestraTxt.setText("Eliminar muestra");

        eliminarMuestraBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminarMuestra.png"))); // NOI18N
        eliminarMuestraBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminarMuestraBtnMouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(33, 33, 33));
        jLabel23.setText("Menú muestras");

        obtenerEpisodiosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/obtenerEpisodios.png"))); // NOI18N
        obtenerEpisodiosBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                obtenerEpisodiosBtnMouseClicked(evt);
            }
        });

        cambiarMuestraBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cambiarMuestra.png"))); // NOI18N
        cambiarMuestraBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarMuestraBtnMouseClicked(evt);
            }
        });

        cambiarMuestraTxt.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        cambiarMuestraTxt.setForeground(new java.awt.Color(33, 33, 33));
        cambiarMuestraTxt.setText("Cambiar muestra");

        obtenerEpisodiosTxt.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        obtenerEpisodiosTxt.setForeground(new java.awt.Color(33, 33, 33));
        obtenerEpisodiosTxt.setText("Obtener episodios");

        javax.swing.GroupLayout vistaPanelPerspectivaLayout = new javax.swing.GroupLayout(vistaPanelPerspectiva);
        vistaPanelPerspectiva.setLayout(vistaPanelPerspectivaLayout);
        vistaPanelPerspectivaLayout.setHorizontalGroup(
                vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPanelPerspectivaLayout.createSequentialGroup()
                                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(obtenerEpisodiosTxt))
                                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                                .addGap(65, 65, 65)
                                                .addComponent(obtenerEpisodiosBtn)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                                .addComponent(cambiarMuestraTxt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPanelPerspectivaLayout.createSequentialGroup()
                                                .addComponent(cambiarMuestraBtn)
                                                .addGap(54, 54, 54)))
                                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                                .addGap(45, 45, 45)
                                                .addComponent(eliminarMuestraBtn))
                                        .addComponent(eliminarMuestraTxt))
                                .addGap(31, 31, 31))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPanelPerspectivaLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23)
                                .addGap(174, 174, 174))
        );
        vistaPanelPerspectivaLayout.setVerticalGroup(
                vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                                .addComponent(eliminarMuestraBtn)
                                                .addGap(28, 28, 28))
                                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                                .addComponent(jLabel23)
                                                .addGap(28, 28, 28)
                                                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                                                .addComponent(obtenerEpisodiosBtn)
                                                                .addGap(28, 28, 28))
                                                        .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                                                .addComponent(cambiarMuestraBtn)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(cambiarMuestraTxt)
                                                                        .addComponent(obtenerEpisodiosTxt)
                                                                        .addComponent(eliminarMuestraTxt))))))
                                .addContainerGap(99, Short.MAX_VALUE))
        );

        principal.add(vistaPanelPerspectiva, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, 480, 240));

        vistaPerspectivaActividad.setBackground(new java.awt.Color(33, 33, 33));
        vistaPerspectivaActividad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Activity Render");

        javax.swing.GroupLayout vistaPerspectivaActividadLayout = new javax.swing.GroupLayout(vistaPerspectivaActividad);
        vistaPerspectivaActividad.setLayout(vistaPerspectivaActividadLayout);
        vistaPerspectivaActividadLayout.setHorizontalGroup(
                vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(videoActividad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(vistaPerspectivaActividadLayout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addComponent(jLabel5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vistaPerspectivaActividadLayout.setVerticalGroup(
                vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPerspectivaActividadLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(videoActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.getAccessibleContext().setAccessibleDescription("");

        principal.add(vistaPerspectivaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 0, 450, 370));

        vistaPerspectivaActividad1.setBackground(new java.awt.Color(33, 33, 33));
        vistaPerspectivaActividad1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Perspectiva Externa");

        javax.swing.GroupLayout vistaPerspectivaActividad1Layout = new javax.swing.GroupLayout(vistaPerspectivaActividad1);
        vistaPerspectivaActividad1.setLayout(vistaPerspectivaActividad1Layout);
        vistaPerspectivaActividad1Layout.setHorizontalGroup(
                vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(videoExterno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(vistaPerspectivaActividad1Layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jLabel7)
                                .addContainerGap(144, Short.MAX_VALUE))
        );
        vistaPerspectivaActividad1Layout.setVerticalGroup(
                vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(vistaPerspectivaActividad1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(videoExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        principal.add(vistaPerspectivaActividad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 0, 460, 370));

        vistaTiempo.setBackground(new java.awt.Color(153, 153, 0));
        vistaTiempo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vistaTiempo.add(sliderTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 60));

        principal.add(vistaTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 1290, 60));

        minutero.setFont(new java.awt.Font("Roboto Light", 0, 36)); // NOI18N
        minutero.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(minutero)
                                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

                        String time = calculoTiempo(frameSegundoF);
                        jLabel8.setText(time);
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
                    //String nombreParaCopia =  nombreMuestraActual + "/" + "/storeActivityRenderVisualizacion/" + "/"+ String.valueOf(timeAR) + ".jpeg";
                    fA = new File(ruta + "/storeActivityRenderVisualizacion/");
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

    public void agregarTag() {

        //System.out.println("entre a agregar tag");
        for (int i = 0; i < 40; i++) {

            //
            vistaTagManual.add(contenedor.get(minutos).get(i));

            // System.out.println("[AGREGAR]TAMAÑO TAG MANUAL"+vistaTagManual.getComponentCount());
            vistaTagManual.revalidate();
            vistaTagManual.repaint();
        }
        //  System.out.println("Tiempo: "+ minutos);
        //   System.out.println("medidas EN AGREGAR TAG: "+ contenedor.get(minutos).get(0).getBounds());
        vistaTagManual.revalidate();
        vistaTagManual.repaint();

    }

    public void quitarTag() {

        Component[] componentList = vistaTagManual.getComponents();
        //Loop through the components
        for (Component c : componentList) {

            //Find the components you want to remove
            if (c instanceof JLabel) {

                //Remove it
                //System.out.println("TEXTO EN QUITAR TAG: "+ ((JLabel) c).getText());
                vistaTagManual.remove(c);
                //System.out.println("[ELIMINAR]TAMAÑO TAG MANUAL"+vistaTagManual.getComponentCount());

            }
        }

        //IMPORTANT
        vistaTagManual.revalidate();
        vistaTagManual.repaint();
        //System.out.println("termine quitar tag");
    }

    public void crearAgregar() {

        Border border = BorderFactory.createLineBorder(colorBordeTag, 1);
        //tiempoParaTags
        //outer.add(new ArrayList<[var type]>(inner));
        System.out.println("Tengo este tiempo: " + tiempoParaTags);
        for (int j = 0; j < tiempoParaTags; j++) {
            //System.out.println("minuto: " + j);
            ArrayList<JLabel> arregloTag = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                System.out.println("entre a agregar");

                JLabel tag = new JLabel();

                AdapterForTag adapterForTag = new AdapterForTag();

                adapterForTag.test(i, j);
                tag.addMouseListener(adapterForTag);
                tag.addMouseMotionListener(adapterForTag);

                tag.setText(textoTagManual);
                tag.setForeground(colorTxtTag);

                tag.setOpaque(true);
                tag.setBackground(colorFondoTag);
                tag.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

                tag.setBorder(border);
                arregloTag.add(tag);

            }
            tagsPorMinuto.add(0);
            contenedor.add(new ArrayList<JLabel>(arregloTag));
            //System.out.println("CONTENEDOR: "+ contenedor.get(j).get(0).getText());
//            contenedor.add(arregloTag);
        }
        System.out.println("tamaño: " + contenedor.size());
    }

    public void contadorMinutero(boolean accion) {

        if (accion) {
            //se completo el minuto
            if (contadorTiempo == 600) {

                //   System.out.println("entre con: ");
                // System.out.println("contadorTiempo: " + contadorTiempo);
                //  System.out.println("minutos: " + minutos);
                contadorTiempo = 0;
                //agregarTag();
                quitarTag();
                minutos += 1;
                agregarTag();

            } else {
                contadorTiempo += 1;
            }

        } //retroceder false
        else {
            if (contadorTiempo == 0) {
                contadorTiempo = 600;
                quitarTag();
                minutos -= 1;

                agregarTag();
            } else {
                contadorTiempo -= 1;
            }

        }

        sliderTiempo.setValue(contadorTiempo);
        minutero.setText(String.valueOf(minutos));
    }

    public static void makeVideo(String movFile, String rutaInicial, int frameInicial, int frameFinal, int AWidht, int LHeight) {
        System.out.println("Creando video");
        //ruta + "/storeFaceRecorder/"
        JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
        Vector<String> imgLst = new Vector<String>();
        File f = new File(rutaInicial);
        File[] fileLst = f.listFiles();
        for (int i = frameInicial; i < frameFinal; i++) {
            imgLst.add(fileLst[i].getAbsolutePath());
        }
        // Generate the output media locators.

        MediaLocator oml;
        if ((oml = imageToMovie.createMediaLocator(movFile)) == null) {

            System.err.println("Cannot build media locator from: " + movFile);
            System.exit(0);
        }
        try {
            imageToMovie.doIt(AWidht, LHeight, (1000 / captureInterval),
                    imgLst, oml);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        return tiempoTotal;
    }
    public void cargaSentimientoAutomatico() throws IndicoException, IOException{
         File tam = new File(ruta + "/storeFaceRecorder/");
         File[] afileLstF = tam.listFiles();
         int cantElementos = afileLstF.length;
         boolean analizar = true;
         Indico indico = new Indico("aea60a29b92333183d284f087d633b22");
         ArrayList< Map<FacialEmotion, Double>> resultadosAnalisis = new ArrayList< Map<FacialEmotion, Double>>();
         
         int imagenAnalizada = 0;
                
                    while (analizar) {
                            if (cantElementos == imagenAnalizada) {
                                                        break;
                                                    }
                        String direccionFoto = afileLstF[imagenAnalizada].getAbsolutePath();
                        System.out.println("direccion: "+ direccionFoto);
                        IndicoResult single = indico.fer.predict(direccionFoto);
                        Map<FacialEmotion, Double> result = single.getFer();
                        resultadosAnalisis.add(result);
                        System.out.println("imagen num: "+ imagenAnalizada);
                        imagenAnalizada += 1;
                        
                        
                        
                    }
         for (int i = 0; i < resultadosAnalisis.size(); i++) {
             //System.out.println("resultados "+ i+": "+resultadosAnalisis.get(i));
     //Angry=0.0947634052, Sad=0.3484043497, Fear=0.23150029, Happy=0.0940359132, Surprise=0.0489270408, Neutral=0.18236900
                    for (Entry<FacialEmotion, Double> e: resultadosAnalisis.get(i).entrySet()) {
                         
                        
                        if(e.getKey().toString()=="Happy"){
                        System.out.println("["+e.getKey() + "=" + e.getValue()+"]");    
                
                            
                            felicidad1.add(i);
                            
                            
                        }
                        
                        if(e.getKey().toString()=="Surprise"){
                        System.out.println("["+e.getKey() + "=" + e.getValue()+"]");    
                            
                                sorpresa1.add(i);
                                 
                        
                        }
                        
                        if(e.getKey().toString()=="Fear"){
                        System.out.println("["+e.getKey() + "=" + e.getValue()+"]");    
                     
                            
                               temeroso1.add(i);
                            
                        }
                        
                        if(e.getKey().toString()=="Angry"){
                        System.out.println("["+e.getKey() + "=" + e.getValue()+"]");    
                           
                                enojado1.add(i);
                           
                        }
                        
                        if(e.getKey().toString()=="Neutral"){
                        System.out.println("["+e.getKey() + "=" + e.getValue()+"]");    
                   
                           
                            neutral1.add(i);
                            
                           
                        }
                        if(e.getKey().toString()=="Sad"){
                        System.out.println("["+e.getKey() + "=" + e.getValue()+"]");    
                    
                           
                                tristeza1.add(i);
                            
                                                   
                        }
                    }
             
        }
         escibirTagAutomaticos();

    
    }
    public void escibirTagAutomaticos(){
        //Anger, Sadness, Surprise, Fear, Happiness, and Neutral.
        JSONArray employeeList = new JSONArray();

        

        JSONObject employeeDetails1 = new JSONObject();
        
/*
felicidad1
sorpresa1 
tristeza1 
temeroso1 
enojado1 
neutral1
        */for (int i = 0; i < enojado1.size(); i++) {
            employeeDetails1.put(String.valueOf(i),String.valueOf(enojado1.get(i)) );
        }
        JSONObject employeeObject = new JSONObject();
        employeeObject.put("Anger", employeeDetails1);
        employeeList.add(employeeObject);

        ///
        
        JSONObject employeeDetails2 = new JSONObject();
        for (int i = 0; i < tristeza1.size(); i++) {
            employeeDetails2.put(String.valueOf(i),String.valueOf(tristeza1.get(i)) );
        }
        JSONObject employeeObject2 = new JSONObject();
        employeeObject2.put("Sadnes", employeeDetails2);
        employeeList.add(employeeObject2);
        
        ///
        
        JSONObject employeeDetails3 = new JSONObject();
        for (int i = 0; i < sorpresa1.size(); i++) {
            employeeDetails3.put(String.valueOf(i),String.valueOf(sorpresa1.get(i)) );
        }
        JSONObject employeeObject3 = new JSONObject();
        employeeObject3.put("Surprise", employeeDetails3);
        employeeList.add(employeeObject3);
        ///
        
        
        JSONObject employeeDetails4 = new JSONObject();
        for (int i = 0; i < temeroso1.size(); i++) {
            employeeDetails4.put(String.valueOf(i),String.valueOf(temeroso1.get(i)) );
        }
        JSONObject employeeObject4 = new JSONObject();
        employeeObject4.put("Fear", employeeDetails4);
        employeeList.add(employeeObject4);
        
        
        JSONObject employeeDetails5 = new JSONObject();
      for (int i = 0; i < felicidad1.size(); i++) {
            employeeDetails5.put(String.valueOf(i),String.valueOf(felicidad1.get(i)) );
        }
        JSONObject employeeObject5 = new JSONObject();
        employeeObject5.put("Happiness", employeeDetails5);
        employeeList.add(employeeObject5);
        
        
        JSONObject employeeDetails6 = new JSONObject();
         for (int i = 0; i < neutral1.size(); i++) {
            employeeDetails6.put(String.valueOf(i),String.valueOf(neutral1.get(i)) );
        }
        JSONObject employeeObject6 = new JSONObject();
        employeeObject6.put("neutral", employeeDetails6);
        employeeList.add(employeeObject6);
        



        try (FileWriter file = new FileWriter(ruta + "/informacionTagAutomaticos.json")) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
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
        editarMarcas = new javax.swing.JPanel();
        jButtonDetenerMin = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jButtonDetenerMin1 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jFrameMin1 = new javax.swing.JFrame();
        editarMarcas1 = new javax.swing.JPanel();
        jButtonDetenerMin2 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        textoEditarNuevo1 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        jButtonDetenerMin3 = new javax.swing.JButton();
        textoEditarActual1 = new javax.swing.JLabel();
        jFrameMin2 = new javax.swing.JFrame();
        editarMarcas2 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        iconCargando = new javax.swing.JLabel();
        principal = new javax.swing.JPanel();
        vistaPerspectivaCara = new javax.swing.JPanel();
        videoCara = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        vistaTagAutomatico = new javax.swing.JPanel();
        vistaTagManual = new javax.swing.JPanel();
        vistaConfTagAutomatico = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        vistaConfTagManual = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        vistaPanelTag = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        agregarMarcaTxT = new javax.swing.JLabel();
        editarMarcasBtn = new javax.swing.JLabel();
        editarMarcasTxt = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        vistaPanelPlayer = new javax.swing.JPanel();
        btnPlay = new javax.swing.JLabel();
        avanzarBtn = new javax.swing.JLabel();
        retrocederBtn = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        finTiempoTag = new javax.swing.JLabel();
        inicioTiempoTag = new javax.swing.JLabel();
        vistaPanelPerspectiva = new javax.swing.JPanel();
        eliminarMuestraTxt = new javax.swing.JLabel();
        eliminarMuestraBtn = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        obtenerEpisodiosBtn = new javax.swing.JLabel();
        cambiarMuestraBtn = new javax.swing.JLabel();
        cambiarMuestraTxt = new javax.swing.JLabel();
        obtenerEpisodiosTxt = new javax.swing.JLabel();
        vistaPerspectivaActividad = new javax.swing.JPanel();
        videoActividad = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        vistaPerspectivaActividad1 = new javax.swing.JPanel();
        videoExterno = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        vistaTiempo = new javax.swing.JPanel();
        sliderTiempo = new javax.swing.JSlider();
        jPanel1 = new javax.swing.JPanel();
        minutero = new javax.swing.JLabel();

        jFrameMin.setAlwaysOnTop(true);
        jFrameMin.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin.setUndecorated(true);

        editarMarcas.setBackground(new java.awt.Color(56, 142, 60));
        editarMarcas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

        jButtonDetenerMin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDetenerMin.setText("Iniciar análisis");
        jButtonDetenerMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDetenerMinMouseClicked(evt);
            }
        });
        jButtonDetenerMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetenerMinActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(200, 230, 201));
        jLabel22.setText("Análisis de sentimiento automático");

        jButtonDetenerMin1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDetenerMin1.setText("Cancelar");
        jButtonDetenerMin1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDetenerMin1MouseClicked(evt);
            }
        });
        jButtonDetenerMin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetenerMin1ActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(200, 230, 201));
        jLabel27.setText("Seleccionar sentimiento a analizar");

        jComboBox1.setBackground(new java.awt.Color(56, 142, 60));
        jComboBox1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "felicidad", "tristeza", "enfado", "miedo", "sorpresa", "neutro" }));
        jComboBox1.setBorder(null);
        jComboBox1.setFocusable(false);
        jComboBox1.setOpaque(false);

        jLabel33.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(200, 230, 201));
        jLabel33.setText("¡Advertencia!");

        jLabel34.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(200, 230, 201));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("El uso de esta funcionalidad requiere de una conexión a internet");

        jLabel35.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(200, 230, 201));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("El uso de esta funcionalidad toma un tiempo considerable.");

        javax.swing.GroupLayout editarMarcasLayout = new javax.swing.GroupLayout(editarMarcas);
        editarMarcas.setLayout(editarMarcasLayout);
        editarMarcasLayout.setHorizontalGroup(
            editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editarMarcasLayout.createSequentialGroup()
                .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editarMarcasLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel22))
                    .addGroup(editarMarcasLayout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(editarMarcasLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editarMarcasLayout.createSequentialGroup()
                        .addComponent(jButtonDetenerMin1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDetenerMin)
                        .addGap(75, 75, 75))
                    .addGroup(editarMarcasLayout.createSequentialGroup()
                        .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(editarMarcasLayout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(jLabel33))
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(58, Short.MAX_VALUE))))
        );
        editarMarcasLayout.setVerticalGroup(
            editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editarMarcasLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel22)
                .addGap(40, 40, 40)
                .addComponent(jLabel27)
                .addGap(27, 27, 27)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addGap(19, 19, 19)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(editarMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDetenerMin)
                    .addComponent(jButtonDetenerMin1))
                .addGap(63, 63, 63))
        );

        javax.swing.GroupLayout jFrameMinLayout = new javax.swing.GroupLayout(jFrameMin.getContentPane());
        jFrameMin.getContentPane().setLayout(jFrameMinLayout);
        jFrameMinLayout.setHorizontalGroup(
            jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editarMarcas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jFrameMinLayout.setVerticalGroup(
            jFrameMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameMinLayout.createSequentialGroup()
                .addComponent(editarMarcas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jFrameMin1.setAlwaysOnTop(true);
        jFrameMin1.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin1.setUndecorated(true);

        editarMarcas1.setBackground(new java.awt.Color(56, 142, 60));
        editarMarcas1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

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

        jLabel24.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(200, 230, 201));
        jLabel24.setText("Nuevo texto");

        textoEditarNuevo1.setBackground(new java.awt.Color(56, 142, 60));
        textoEditarNuevo1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        textoEditarNuevo1.setForeground(new java.awt.Color(204, 204, 204));
        textoEditarNuevo1.setBorder(null);
        textoEditarNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoEditarNuevo1ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(200, 230, 201));
        jLabel26.setText("Texto actual:");

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

        textoEditarActual1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        textoEditarActual1.setForeground(new java.awt.Color(200, 230, 201));
        textoEditarActual1.setText("Texto actual:");

        javax.swing.GroupLayout editarMarcas1Layout = new javax.swing.GroupLayout(editarMarcas1);
        editarMarcas1.setLayout(editarMarcas1Layout);
        editarMarcas1Layout.setHorizontalGroup(
            editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editarMarcas1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editarMarcas1Layout.createSequentialGroup()
                        .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editarMarcas1Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(35, 35, 35)
                                .addComponent(textoEditarActual1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editarMarcas1Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textoEditarNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(editarMarcas1Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(editarMarcas1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButtonDetenerMin3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(jButtonDetenerMin2)
                        .addGap(70, 70, 70))))
        );
        editarMarcas1Layout.setVerticalGroup(
            editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editarMarcas1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(textoEditarActual1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(textoEditarNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(editarMarcas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDetenerMin2)
                    .addComponent(jButtonDetenerMin3))
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout jFrameMin1Layout = new javax.swing.GroupLayout(jFrameMin1.getContentPane());
        jFrameMin1.getContentPane().setLayout(jFrameMin1Layout);
        jFrameMin1Layout.setHorizontalGroup(
            jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editarMarcas1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jFrameMin1Layout.setVerticalGroup(
            jFrameMin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameMin1Layout.createSequentialGroup()
                .addComponent(editarMarcas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jFrameMin2.setAlwaysOnTop(true);
        jFrameMin2.setBackground(new java.awt.Color(56, 142, 60));
        jFrameMin2.setUndecorated(true);

        editarMarcas2.setBackground(new java.awt.Color(56, 142, 60));
        editarMarcas2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 230, 201)));

        jLabel28.setFont(new java.awt.Font("Roboto Light", 0, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(200, 230, 201));
        jLabel28.setText("Cargando");

        iconCargando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cargando.png"))); // NOI18N

        javax.swing.GroupLayout editarMarcas2Layout = new javax.swing.GroupLayout(editarMarcas2);
        editarMarcas2.setLayout(editarMarcas2Layout);
        editarMarcas2Layout.setHorizontalGroup(
            editarMarcas2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editarMarcas2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(iconCargando)
                .addContainerGap(68, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editarMarcas2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addGap(135, 135, 135))
        );
        editarMarcas2Layout.setVerticalGroup(
            editarMarcas2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editarMarcas2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel28)
                .addGap(52, 52, 52)
                .addComponent(iconCargando)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFrameMin2Layout = new javax.swing.GroupLayout(jFrameMin2.getContentPane());
        jFrameMin2.getContentPane().setLayout(jFrameMin2Layout);
        jFrameMin2Layout.setHorizontalGroup(
            jFrameMin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editarMarcas2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrameMin2Layout.setVerticalGroup(
            jFrameMin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameMin2Layout.createSequentialGroup()
                .addComponent(editarMarcas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        principal.setPreferredSize(new java.awt.Dimension(1366, 768));
        principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vistaPerspectivaCara.setBackground(new java.awt.Color(33, 33, 33));
        vistaPerspectivaCara.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Face Recorder");

        javax.swing.GroupLayout vistaPerspectivaCaraLayout = new javax.swing.GroupLayout(vistaPerspectivaCara);
        vistaPerspectivaCara.setLayout(vistaPerspectivaCaraLayout);
        vistaPerspectivaCaraLayout.setHorizontalGroup(
            vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                .addGroup(vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel6))
                    .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(videoCara, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        vistaPerspectivaCaraLayout.setVerticalGroup(
            vistaPerspectivaCaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPerspectivaCaraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(42, 42, 42)
                .addComponent(videoCara, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        principal.add(vistaPerspectivaCara, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 455, 370));

        vistaTagAutomatico.setBackground(new java.awt.Color(76, 175, 80));

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

        vistaTagManual.setBackground(new java.awt.Color(56, 142, 60));

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

        vistaConfTagAutomatico.setBackground(new java.awt.Color(56, 142, 60));
        vistaConfTagAutomatico.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(189, 189, 189), 1, true));
        vistaConfTagAutomatico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Automático");
        vistaConfTagAutomatico.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        principal.add(vistaConfTagAutomatico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 80, 50));

        vistaConfTagManual.setBackground(new java.awt.Color(76, 175, 80));
        vistaConfTagManual.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(189, 189, 189), 1, true));
        vistaConfTagManual.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("Manual");
        vistaConfTagManual.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        principal.add(vistaConfTagManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 80, 50));

        vistaPanelTag.setBackground(new java.awt.Color(255, 255, 255));
        vistaPanelTag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregarTag.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        agregarMarcaTxT.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        agregarMarcaTxT.setForeground(new java.awt.Color(33, 33, 33));
        agregarMarcaTxT.setText("Agregar marcas");
        agregarMarcaTxT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agregarMarcaTxTMouseClicked(evt);
            }
        });

        editarMarcasBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editarTag.png"))); // NOI18N
        editarMarcasBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editarMarcasBtnMouseClicked(evt);
            }
        });

        editarMarcasTxt.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        editarMarcasTxt.setForeground(new java.awt.Color(33, 33, 33));
        editarMarcasTxt.setText("Editar marcas");
        editarMarcasTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editarMarcasTxtMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(33, 33, 33));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Help.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(33, 33, 33));
        jLabel13.setText("Menú marcas");

        javax.swing.GroupLayout vistaPanelTagLayout = new javax.swing.GroupLayout(vistaPanelTag);
        vistaPanelTag.setLayout(vistaPanelTagLayout);
        vistaPanelTagLayout.setHorizontalGroup(
            vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPanelTagLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(agregarMarcaTxT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editarMarcasTxt)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPanelTagLayout.createSequentialGroup()
                        .addComponent(editarMarcasBtn)
                        .addGap(17, 17, 17)))
                .addGap(71, 71, 71))
            .addGroup(vistaPanelTagLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vistaPanelTagLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel4))
                    .addGroup(vistaPanelTagLayout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jLabel13))))
        );
        vistaPanelTagLayout.setVerticalGroup(
            vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPanelTagLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vistaPanelTagLayout.createSequentialGroup()
                        .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(vistaPanelTagLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(editarMarcasBtn)
                                    .addComponent(jLabel4)))
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(vistaPanelTagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editarMarcasTxt)
                            .addComponent(agregarMarcaTxT)))
                    .addComponent(jLabel12))
                .addGap(0, 107, Short.MAX_VALUE))
        );

        principal.add(vistaPanelTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 470, 240));

        vistaPanelPlayer.setBackground(new java.awt.Color(255, 255, 255));
        vistaPanelPlayer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));
        vistaPanelPlayer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-Play-50.png"))); // NOI18N
        btnPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlayMouseClicked(evt);
            }
        });
        vistaPanelPlayer.add(btnPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, -1, 50));

        avanzarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-End-50.png"))); // NOI18N
        avanzarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                avanzarBtnMouseClicked(evt);
            }
        });
        vistaPanelPlayer.add(avanzarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, -1));

        retrocederBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-begin.png"))); // NOI18N
        retrocederBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                retrocederBtnMouseClicked(evt);
            }
        });
        vistaPanelPlayer.add(retrocederBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Roboto Light", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(33, 33, 33));
        jLabel8.setText("00:00:000");
        vistaPanelPlayer.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel25.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(33, 33, 33));
        jLabel25.setText("1 minuto");
        vistaPanelPlayer.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 80, -1));

        jLabel29.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(33, 33, 33));
        jLabel29.setText("Retroceder");
        vistaPanelPlayer.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 90, -1));

        jLabel30.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(33, 33, 33));
        jLabel30.setText("1 minuto");
        vistaPanelPlayer.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 70, -1));

        jLabel31.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(33, 33, 33));
        jLabel31.setText("Play");
        vistaPanelPlayer.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 40, -1));

        jLabel32.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(33, 33, 33));
        jLabel32.setText("Avanzar");
        vistaPanelPlayer.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 70, -1));

        finTiempoTag.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        finTiempoTag.setForeground(new java.awt.Color(33, 33, 33));
        finTiempoTag.setText("00:00:000");
        vistaPanelPlayer.add(finTiempoTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));

        inicioTiempoTag.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        inicioTiempoTag.setForeground(new java.awt.Color(33, 33, 33));
        inicioTiempoTag.setText("00:00:000");
        vistaPanelPlayer.add(inicioTiempoTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        principal.add(vistaPanelPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 530, 425, 240));

        vistaPanelPerspectiva.setBackground(new java.awt.Color(255, 255, 255));
        vistaPanelPerspectiva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        eliminarMuestraTxt.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        eliminarMuestraTxt.setForeground(new java.awt.Color(33, 33, 33));
        eliminarMuestraTxt.setText("Eliminar muestra");

        eliminarMuestraBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminarMuestra.png"))); // NOI18N
        eliminarMuestraBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminarMuestraBtnMouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(33, 33, 33));
        jLabel23.setText("Menú muestras");

        obtenerEpisodiosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/obtenerEpisodios.png"))); // NOI18N
        obtenerEpisodiosBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                obtenerEpisodiosBtnMouseClicked(evt);
            }
        });

        cambiarMuestraBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cambiarMuestra.png"))); // NOI18N
        cambiarMuestraBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarMuestraBtnMouseClicked(evt);
            }
        });

        cambiarMuestraTxt.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        cambiarMuestraTxt.setForeground(new java.awt.Color(33, 33, 33));
        cambiarMuestraTxt.setText("Cambiar muestra");

        obtenerEpisodiosTxt.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        obtenerEpisodiosTxt.setForeground(new java.awt.Color(33, 33, 33));
        obtenerEpisodiosTxt.setText("Obtener episodios");

        javax.swing.GroupLayout vistaPanelPerspectivaLayout = new javax.swing.GroupLayout(vistaPanelPerspectiva);
        vistaPanelPerspectiva.setLayout(vistaPanelPerspectivaLayout);
        vistaPanelPerspectivaLayout.setHorizontalGroup(
            vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPanelPerspectivaLayout.createSequentialGroup()
                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(obtenerEpisodiosTxt))
                    .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(obtenerEpisodiosBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                        .addComponent(cambiarMuestraTxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPanelPerspectivaLayout.createSequentialGroup()
                        .addComponent(cambiarMuestraBtn)
                        .addGap(54, 54, 54)))
                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(eliminarMuestraBtn))
                    .addComponent(eliminarMuestraTxt))
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPanelPerspectivaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(174, 174, 174))
        );
        vistaPanelPerspectivaLayout.setVerticalGroup(
            vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                        .addComponent(eliminarMuestraBtn)
                        .addGap(28, 28, 28))
                    .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(28, 28, 28)
                        .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                .addComponent(obtenerEpisodiosBtn)
                                .addGap(28, 28, 28))
                            .addGroup(vistaPanelPerspectivaLayout.createSequentialGroup()
                                .addComponent(cambiarMuestraBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(vistaPanelPerspectivaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cambiarMuestraTxt)
                                    .addComponent(obtenerEpisodiosTxt)
                                    .addComponent(eliminarMuestraTxt))))))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        principal.add(vistaPanelPerspectiva, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, 480, 240));

        vistaPerspectivaActividad.setBackground(new java.awt.Color(33, 33, 33));
        vistaPerspectivaActividad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Activity Render");

        javax.swing.GroupLayout vistaPerspectivaActividadLayout = new javax.swing.GroupLayout(vistaPerspectivaActividad);
        vistaPerspectivaActividad.setLayout(vistaPerspectivaActividadLayout);
        vistaPerspectivaActividadLayout.setHorizontalGroup(
            vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoActividad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(vistaPerspectivaActividadLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vistaPerspectivaActividadLayout.setVerticalGroup(
            vistaPerspectivaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vistaPerspectivaActividadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(videoActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.getAccessibleContext().setAccessibleDescription("");

        principal.add(vistaPerspectivaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 0, 450, 370));

        vistaPerspectivaActividad1.setBackground(new java.awt.Color(33, 33, 33));
        vistaPerspectivaActividad1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 189, 189)));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Perspectiva Externa");

        javax.swing.GroupLayout vistaPerspectivaActividad1Layout = new javax.swing.GroupLayout(vistaPerspectivaActividad1);
        vistaPerspectivaActividad1.setLayout(vistaPerspectivaActividad1Layout);
        vistaPerspectivaActividad1Layout.setHorizontalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(videoExterno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(vistaPerspectivaActividad1Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel7)
                .addContainerGap(144, Short.MAX_VALUE))
        );
        vistaPerspectivaActividad1Layout.setVerticalGroup(
            vistaPerspectivaActividad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPerspectivaActividad1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(videoExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        minutero.setFont(new java.awt.Font("Roboto Light", 0, 36)); // NOI18N
        minutero.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(minutero)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:

        jFrameMin.setSize(515, 417);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        jFrameMin.setLocationRelativeTo(null);
        jFrameMin.setVisible(true);
        setVisible(false);

    }//GEN-LAST:event_jLabel4MouseClicked

    private void editarMarcasBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editarMarcasBtnMouseClicked
        // TODO add your handling code here:
        textoEditarNuevo1.setText("");
        jFrameMin1.setSize(365, 245);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        jFrameMin1.setLocationRelativeTo(null);
        textoEditarActual1.setText(textoTagManual);
        jFrameMin1.setVisible(true);
        setVisible(false);


    }//GEN-LAST:event_editarMarcasBtnMouseClicked

    private void agregarMarcaTxTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agregarMarcaTxTMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_agregarMarcaTxTMouseClicked

    private void editarMarcasTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editarMarcasTxtMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_editarMarcasTxtMouseClicked

    private void retrocederBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retrocederBtnMouseClicked

        if (minutos != 0) {
            contadorTiempo = 0;
            quitarTag();
            minutos -= 1;
            agregarTag();
            contador = 0;

        }

        sliderTiempo.setValue(contadorTiempo);
        minutero.setText(String.valueOf(minutos));
        int frame = minutos * 600;
        frameSegundoA = frame;
        frameSegundoF = frame;
        frameSegundoE = frame;
        String tiempog = calculoTiempo(frame);

        jLabel8.setText(tiempog);


    }//GEN-LAST:event_retrocederBtnMouseClicked

    private void btnPlayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlayMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPlayMouseClicked

    private void avanzarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avanzarBtnMouseClicked
        // TODO add your handling code here:
        String[] parts = tiempo.split(":");
        int minMax = Integer.parseInt(parts[0]);

        System.out.println("[avanzar] min max: " + minMax);
        if (minutos < minMax) {
            System.out.println("[avanzar] if con min: " + minutos);
            contadorTiempo = 0;
            quitarTag();
            minutos += 1;
            agregarTag();
            contador = 0;

        }

        sliderTiempo.setValue(contadorTiempo);
        minutero.setText(String.valueOf(minutos));
        int frame = minutos * 600;
        if (frame > frameMas) {
            System.out.println("[avanzar] framemas: " + frameMas);
            frame = frameMas;
        }
        frameSegundoA = frame;
        frameSegundoF = frame;
        frameSegundoE = frame;
        System.out.println("[avanzar]frame: " + frameSegundoA);
        String tiempog = calculoTiempo(frame);

        jLabel8.setText(tiempog);
    }//GEN-LAST:event_avanzarBtnMouseClicked

    private void obtenerEpisodiosBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_obtenerEpisodiosBtnMouseClicked
        // TODO add your handling code here:

        for (int i = 0; i < tiempoParaTags; i++) {
            for (int j = 0; j < 40; j++) {
                if (contenedor.get(i).get(j).getBounds().getX() == 0 && contenedor.get(i).get(j).getBounds().getY() == 0) {
                } else {
                    System.out.println("tag min " + i + "/id " + j + ": " + intValue(contenedor.get(i).get(j).getBounds().getX()));
                    System.out.println("tag min " + i + "/id " + j + ": " + contenedor.get(i).get(j).getBounds());

                    ///faceRecorder
                    //
                    String rutaInicialFR = ruta + "/storeFaceRecorder/";
                    String rutaVideoFR = ruta + "/episodio" + i + j + "/faceRecorder.mov";
                    int frameIFR = intValue(contenedor.get(i).get(j).getBounds().getX());
                    int frameFFR = frameIFR + intValue(contenedor.get(i).get(j).getBounds().getWidth());
                    int frameInicialFR = convercionPosicionFrame(frameIFR);
                    int frameFinalFR = convercionPosicionFrame(frameFFR);

                    //makeVideo(rutaVideoFR,rutaInicialFR,frameInicialFR,frameFinalFR);
                    ////C:/Users/Katherine/Desktop/ReproductorMuestras/Muestra0/test1.mov"
                    makeVideo(rutaVideoFR, rutaInicialFR, frameInicialFR, frameFinalFR, 320, 240);

                    ///activityRender
                    //ruta + "/storeActivityRender/"
                    String rutaInicialAR = ruta + "/storeActivityRender/";
                    String rutaVideoAR = ruta + "/episodio" + i + j + "/activityRender.mov";
                    int frameIAR = intValue(contenedor.get(i).get(j).getBounds().getX());
                    int frameFAR = frameIAR + intValue(contenedor.get(i).get(j).getBounds().getWidth());
                    int frameInicialAR = convercionPosicionFrame(frameIAR);
                    int frameFinalAR = convercionPosicionFrame(frameFAR);
                    makeVideo(rutaVideoAR, rutaInicialAR, frameInicialAR, frameFinalAR,screenWidth, screenHeight );
                    ///Perspectiva Externa
                    //ruta + "/storeExternalPerspective"   
                    String rutaInicialEP = ruta + "/storeExternalPerspective/";
                    String rutaVideoEP = ruta + "/episodio" + i + j + "/ExternalPerspective.mov";
                    int frameIEP = intValue(contenedor.get(i).get(j).getBounds().getX());
                    int frameFEP = frameIEP + intValue(contenedor.get(i).get(j).getBounds().getWidth());
                    int frameInicialEP = convercionPosicionFrame(frameIEP);
                    int frameFinalEP = convercionPosicionFrame(frameFEP);
                    makeVideo(rutaVideoEP, rutaInicialEP, frameInicialEP, frameFinalEP,640,480);
                    

                }
            }

        }

       JOptionPane.showMessageDialog(null, "Muestra Guardada", "InfoBox: " + "Creacion Captura", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_obtenerEpisodiosBtnMouseClicked

    private void cambiarMuestraBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cambiarMuestraBtnMouseClicked
        // TODO add your handling code here:

        escribirJson(false);
        escribirTags();

        VisualizacionMuestras visualizacionMuestras = new VisualizacionMuestras(direccion);
        visualizacionMuestras.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_cambiarMuestraBtnMouseClicked

    private void eliminarMuestraBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eliminarMuestraBtnMouseClicked
        // TODO add your handling code here:

        escribirJson(true);
        VisualizacionMuestras visualizacionMuestras = new VisualizacionMuestras(direccion);
        visualizacionMuestras.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_eliminarMuestraBtnMouseClicked

    private void jButtonDetenerMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerMinActionPerformed
        // TODO add your handling code here:

        //jButton6ActionPerformed(evt);
    }//GEN-LAST:event_jButtonDetenerMinActionPerformed

    private void jButtonDetenerMin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerMin1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDetenerMin1ActionPerformed

    private void jButtonDetenerMin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerMin2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDetenerMin2ActionPerformed

    private void textoEditarNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoEditarNuevo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoEditarNuevo1ActionPerformed

    private void jButtonDetenerMin3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerMin3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDetenerMin3ActionPerformed

    private void jButtonDetenerMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDetenerMinMouseClicked
        // TODO add your handling code here:
    
        
 
         
         
         
        
        //int numeroi = (int) (Math.random() * n) + 1;
         
        //felicidad, tristeza, enfado, miedo, sorpresa, neutro

        System.out.println("Opcion elegida: " + x);
        jFrameMin2.setSize(431, 490);
        
        jFrameMin2.setLocationRelativeTo(null);
        jFrameMin.setVisible(false);
        jFrameMin2.setVisible(true);
        this.setVisible(false);
       try {
            cargaSentimientoAutomatico();
            
        
            
            
        } catch (IndicoException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        //t.start();
        jFrameMin2.setVisible(false);
        this.setVisible(true);

    }//GEN-LAST:event_jButtonDetenerMinMouseClicked

    private void jButtonDetenerMin1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDetenerMin1MouseClicked
        // TODO add your handling code here:

        String opcionRealizada = String.valueOf(jComboBox1.getSelectedItem());
        System.out.println("Opcion: " + opcionRealizada);
        setVisible(true);
        jFrameMin.setVisible(false);


    }//GEN-LAST:event_jButtonDetenerMin1MouseClicked

    private void jButtonDetenerMin2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDetenerMin2MouseClicked
        // TODO add your handling code here:

        String ntextoTagManual = textoEditarNuevo1.getText();
        textoTagManual = ntextoTagManual;
        for (int i = 0; i < tiempoParaTags; i++) {
            for (int j = 0; j < 40; j++) {
                contenedor.get(i).get(j).setText(ntextoTagManual);
            }

        }
        vistaTagManual.revalidate();
        vistaTagManual.repaint();
        jFrameMin1.setVisible(false);
        setVisible(true);

    }//GEN-LAST:event_jButtonDetenerMin2MouseClicked

    private void jButtonDetenerMin3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDetenerMin3MouseClicked
        // TODO add your handling code here:

        jFrameMin1.setVisible(false);
        setVisible(true);

    }//GEN-LAST:event_jButtonDetenerMin3MouseClicked

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

            }
        });
    }

    class PopUpDemo extends JPopupMenu {

        JMenuItem anItem;
        private int i, j;

        public PopUpDemo() {

            anItem = new JMenuItem(new AbstractAction("Eliminar Marca") {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Hice click en eliminar");
                    vistaTagManual.getComponent(i).setBounds(new Rectangle(0, 0, 0, 0));
                    vistaTagManual.revalidate();
                    vistaTagManual.repaint();
                }
            });
            //tag.;

            add(anItem);

        }

        public void setI(int iN) {
            this.i = iN;

        }

        public void setJ(int jN) {
            this.j = jN;
        }

    }

    class AdapterForTag extends MouseAdapter {

        private int id, idContenedor;

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("FUI CLICKEADO");
            System.out.println("id: " + this.id);
            System.out.println("contenedor: " + this.idContenedor);

        }

        public void test(int i, int j) {

            this.id = i;
            this.idContenedor = j;

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                doPop(e);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                doPop(e);
            }
        }

        private void doPop(MouseEvent e) {
            PopUpDemo menu = new PopUpDemo();
            menu.setI(this.id);
            menu.setJ(this.idContenedor);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }

    }

    class MyMouseAdapter extends MouseAdapter {

        private int innerX, innerY, id, idContenedor;
        //false = aun no se le asignan bonds
        //true = ya se le asignan bonds
        private boolean estado = false;

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
            System.out.println("pos que sale de mouse presionado " + innerX);
            int frame = convercionPosicionFrame(innerX);
            String tiempo = calculoTiempo(frame);
            inicioTiempoTag.setVisible(true);
            finTiempoTag.setVisible(true);
            inicioTiempoTag.setText(tiempo);

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
            if (!this.estado) {
                int numTag = tagsPorMinuto.get(minutos);
                vistaTagManual.getComponent(numTag).setBounds(x, y, width, height);
                System.out.println("x: " + vistaTagManual.getComponent(numTag).getBounds());

                tagsPorMinuto.set(minutos, numTag + 1);
                inicioTiempoTag.setVisible(false);
                finTiempoTag.setVisible(false);

            }

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
            int frameTemp = 0;

            frameTemp = convercionPosicionFrame(x + width);

            String tiempoTempo = calculoTiempo(frameTemp);
            finTiempoTag.setText(tiempoTempo);

        }

    }

    public class SliderSkinDemo2 {

        public JSlider makeUI() {
            UIDefaults d = new UIDefaults();
            d.put("Slider:SliderTrack[Enabled].backgroundPainter", new Painter<JSlider>() {
                @Override
                public void paint(Graphics2D g, JSlider c, int w, int h) {
                    if (mouseDown == false) {

                        //  System.out.println("w: " + w);
                        int arc = 10;
                        int trackHeight = 8;
                        int trackWidth = w - 2;
                        int fillTop = 4;
                        int fillLeft = 1;

                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                        g.setStroke(new BasicStroke(1.5f));
                        g.setColor(Color.WHITE);
                        g.fillRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);

                        int fillBottom = fillTop + trackHeight;
                        int fillRight = xPositionForValue(
                                c.getValue(), c,
                                new Rectangle(fillLeft, fillTop, trackWidth, fillBottom - fillTop));

                        g.setColor(colorFondoTag);
                        g.fillRect(fillLeft + 1, fillTop + 1, fillRight - fillLeft, fillBottom - fillTop);

                        g.setColor(Color.BLACK);
                        g.drawRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);
                    }
                }
                //@see javax/swing/plaf/basic/BasicSliderUI#xPositionForValue(int value)

                protected int xPositionForValue(int value, JSlider slider, Rectangle trackRect) {
                    int posicionAnterior = posicionfinal;
                    String tiempoFinal = "";
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
                                tiempoFinal = calculoTiempo(frameSegundoF);
                                jLabel8.setText(tiempoFinal);
                                //contadorMinutero(true, true);

                            } else {
                                frameSegundoF = frameMas;
                                calculoTiempo(frameSegundoF);
                                jLabel8.setText(tiempoFinal);

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
                                tiempoFinal = calculoTiempo(frameSegundoF);
                                jLabel8.setText(tiempoFinal);
                                //contadorMinutero(false, true);
                            } else {
                                frameSegundoF = 0;
                                tiempoFinal = calculoTiempo(0);
                                jLabel8.setText(tiempoFinal);
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
                                tiempoFinal = calculoTiempo(frameSegundoF);
                                jLabel8.setText(tiempoFinal);
                            } else {
                                frameSegundoF = 0;
                                tiempoFinal = calculoTiempo(0);
                                jLabel8.setText(tiempoFinal);
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
                                tiempoFinal = calculoTiempo(frameSegundoF);
                                jLabel8.setText(tiempoFinal);
                            } else {
                                frameSegundoF = frameMas;
                                tiempoFinal = calculoTiempo(frameSegundoF);
                                jLabel8.setText(tiempoFinal);
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

        public void contadorMinutero2(boolean accion) {

            if (accion == false) {
                //se completo el minuto
                if (contadorTiempo == 600) {

                    contadorTiempo = 599;

                } else {

                    contadorTiempo += 1;
                }
            } //retroceder false
            else {
                if (contadorTiempo == 0) {

                    contadorTiempo = 1;

                } else {
                    contadorTiempo -= 1;
                }

            }

            sliderTiempo.setValue(contadorTiempo);
            minutero.setText(String.valueOf(minutos));
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
    private javax.swing.JLabel agregarMarcaTxT;
    private javax.swing.JLabel avanzarBtn;
    private javax.swing.JLabel btnPlay;
    private javax.swing.JLabel cambiarMuestraBtn;
    private javax.swing.JLabel cambiarMuestraTxt;
    private javax.swing.JPanel editarMarcas;
    private javax.swing.JPanel editarMarcas1;
    private javax.swing.JPanel editarMarcas2;
    private javax.swing.JLabel editarMarcasBtn;
    private javax.swing.JLabel editarMarcasTxt;
    private javax.swing.JLabel eliminarMuestraBtn;
    private javax.swing.JLabel eliminarMuestraTxt;
    private javax.swing.JLabel finTiempoTag;
    private javax.swing.JLabel iconCargando;
    private javax.swing.JLabel inicioTiempoTag;
    private javax.swing.JButton jButtonDetenerMin;
    private javax.swing.JButton jButtonDetenerMin1;
    private javax.swing.JButton jButtonDetenerMin2;
    private javax.swing.JButton jButtonDetenerMin3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFrame jFrameMin;
    private javax.swing.JFrame jFrameMin1;
    private javax.swing.JFrame jFrameMin2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel minutero;
    private javax.swing.JLabel obtenerEpisodiosBtn;
    private javax.swing.JLabel obtenerEpisodiosTxt;
    private javax.swing.JPanel principal;
    private javax.swing.JLabel retrocederBtn;
    private javax.swing.JSlider sliderTiempo;
    private javax.swing.JLabel textoEditarActual1;
    private javax.swing.JTextField textoEditarNuevo1;
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

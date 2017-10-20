/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faceRecorder;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import principal.Pestanas;


/**
 *
 * @author Katherine
 */
public class FaceRecorder {
    Webcam webcam;
    boolean isRunning = true;
    File file;
    IMediaWriter writer;
    Dimension size;
    long start;
    BufferedImage image, image2;
    IConverter converter;
    IVideoPicture frame; 
   // public static String store = "FaceRecorderTemporal";


public void inicializar(){
             if(!isRunning){
            isRunning = true;
           /*File f = new File(store);
		if(!f.exists()){
			f.mkdir();
                        System.out.println("Se creo directorio");
		}
            new VideoFeedTaker().start();
*/
        }
    

}
public void detener (){

isRunning = false;
}
 public void startRecord(String store) {
  Thread recordThread = new Thread() {
   @Override
    public void run() {
        System.out.println("Se comenzo a capturar la cara...");
       /* size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320,240));
        webcam.open(true);*/
        System.out.println("Se debio haber abierto la camara");
        start = System.currentTimeMillis();
     
          while(isRunning && webcam.isOpen()){
                try {
                   image2 = webcam.getImage();
                   ImageIO.write(image2, "jpg", new File("./"+store+"/"+System.currentTimeMillis()+".jpg"));
                   Thread.sleep(10);
                        
                } catch (InterruptedException ex) {
                    //Logger.getLogger(CameraTest.class.getName()).log(Level.SEVERE, null, ex);
                }  catch (IOException ex) {
                       Logger.getLogger(FaceRecorder.class.getName()).log(Level.SEVERE, null, ex);
                   }
               
          }
            
    }
  
  
  };
  recordThread.start();
  
 
 }
class VideoFeedTaker extends Thread{
        @Override
        public void run() {
               System.out.println("Entre al hilo");
            //file = new File("output.ts");
            //writer = ToolFactory.makeWriter(file.getName());
            size = WebcamResolution.QVGA.getSize();
           // writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
            
            

            webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(320,240));
            //webcam.setViewSize(size);
            webcam.open(true);
            
            System.out.println("Se debio haber abierto la camara");
            start = System.currentTimeMillis();
            int i=0;
            while(isRunning){
          
                try {
                   // System.out.println("Estoy grabando?");
                   image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
                   image2 = webcam.getImage();
                   //ImageIO.write(image2, "jpg", new File("./"+store+"/"+System.currentTimeMillis()+".jpg"));
                   //converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);
                   //frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
                   //frame.setKeyFrame(i == 0);
			//frame.setQuality(0);

			//writer.encodeVideo(0, frame);

			// 10 FPS
		Thread.sleep(10);
                        
                } catch (InterruptedException ex) {
                    //Logger.getLogger(CameraTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                i=i+1;
            }
            //writer.close();

		//System.out.println("Video recorded in file: " + file.getAbsolutePath());
        }
    
    }
}

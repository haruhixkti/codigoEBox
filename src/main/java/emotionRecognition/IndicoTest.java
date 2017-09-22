/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emotionRecognition;

import io.indico.Indico;
import io.indico.api.image.FacialEmotion;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Katherine
 */

public class IndicoTest {
    public static void main(String[] args) throws IndicoException, UnsupportedOperationException, IOException 
    {
        String filePath = "C:\\Users\\Katherine\\Desktop\\kathy.jpg";
        Indico indico = new Indico("aea60a29b92333183d284f087d633b22");
        IndicoResult single = indico.fer.predict(filePath);
        Map<FacialEmotion, Double> result = single.getFer();
        System.out.println(result);

      
        //List<Double> result = single.getFacialFeatures();
        //System.out.println(result);
    }
}

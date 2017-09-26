/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestVlc extends JFrame {
     public static void main(String[] args) {
    new TestVlc();
  }
     
  public TestVlc() {
    Container content = getContentPane();
    content.setBackground(Color.white);

    JSlider slider1 = new JSlider();
    slider1.setBorder(BorderFactory.createTitledBorder("JSlider without Tick Marks"));

    content.add(slider1, BorderLayout.NORTH);

    JSlider slider2 = new JSlider();
    slider2.setBorder(BorderFactory.createTitledBorder("JSlider with Tick Marks"));

    slider2.setMajorTickSpacing(20);
    slider2.setMinorTickSpacing(5);
    slider2.setPaintTicks(true);
    content.add(slider2, BorderLayout.CENTER);

    JSlider slider3 = new JSlider();
    slider3.setBorder(BorderFactory.createTitledBorder("JSlider with Tick Marks & Labels"));

    slider3.setMajorTickSpacing(20);
    slider3.setMinorTickSpacing(5);
    slider3.setPaintTicks(true);
    slider3.setPaintLabels(true);
    content.add(slider3, BorderLayout.SOUTH);

    setSize(300,300);
    setVisible(true);
  }
}

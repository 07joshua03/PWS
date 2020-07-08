package main;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private int width;
    private int height;
    private JPanel mainPanel;

    public MainWindow(int w, int h){

        //jFrame stuff
        this.width = w;
        this.height = h;
        this.setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //mainPanel stuff
        mainPanel = new JPanel();
        mainPanel.setSize(width, height);
        mainPanel.setBackground(new Color(0,0,0));
        this.add(mainPanel);

        this.setVisible(true);
    }

    public void update(){

    }


}
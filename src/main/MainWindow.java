package main;

import grid.Grid;
import grid.GridPanel;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class MainWindow extends JFrame {

    private final int width;
    private final int height;
    public final GridPanel mainPanel;

    public MainWindow(int w, int h){

        //jFrame stuff
        this.width = w;
        this.height = h;
        this.setSize(width, height);
        this.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(true);

        //mainPanel stuff
        mainPanel = new GridPanel(width,height - 25);
        this.add(mainPanel);
        this.setVisible(true);

    }

    public void update(){
        mainPanel.updatePanel();
    }

}

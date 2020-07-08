package main;

import grid.Grid;
import grid.GridPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private int width;
    private int height;
    private GridPanel mainPanel;

    public MainWindow(int w, int h){

        //jFrame stuff
        this.width = w;
        this.height = h;
        this.setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //mainPanel stuff
        mainPanel = new GridPanel(width,height - 25);
        this.add(mainPanel);
        this.setVisible(true);
    }

    public void update(Grid grid){
        mainPanel.update(grid);
    }


}

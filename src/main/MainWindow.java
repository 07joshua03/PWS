package main;

import grid.GridPanel;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public final GridPanel mainPanel;

    public MainWindow(int w, int h){

        //jFrame stuff
        this.setSize(w, h);
        this.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(true);

        //mainPanel stuff
        mainPanel = new GridPanel(w, h - 25);
        this.add(mainPanel);
        this.setVisible(true);

    }

}

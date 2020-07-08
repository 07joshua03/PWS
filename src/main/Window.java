package main;

import javax.swing.*;

public class Window extends JFrame {

    private final int WIDTH = 320;
    private final int HEIGHT = 240;


    public Window(){
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}

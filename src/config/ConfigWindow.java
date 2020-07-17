package config;

import grid.Grid;
import helper.GridDataParser;
import helper.Slider;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigWindow extends JFrame implements ActionListener {

    private final Slider sliderWidth;
    private final Slider sliderHeight;
    private final JLabel sliderWidthText;
    private final JLabel sliderHeightText;
    private final JButton changeGridButton;
    private final JComboBox gridsList;
    private ArrayList<Grid> gridsFromSave;
    private final JButton saveGridButton;

    public ConfigWindow(int width, int height){
        setLayout(null);
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(Main.mainWindow.getLocation().x - width, Main.mainWindow.getLocation().y);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gridsFromSave = new ArrayList<>();
        try {
            gridsFromSave = GridDataParser.readAllGridsFromSave();
        }catch(IOException e){
            e.printStackTrace();
        }
        //Initialize sliders
        sliderWidth = new Slider(this,6);
        sliderWidth.setLocation(35,10);
        sliderWidthText = new JLabel(Integer.toString(6));
        sliderWidthText.setLocation(17,17);
        sliderWidthText.setSize(25,20);
        sliderWidthText.setForeground(Color.BLACK);

        sliderHeight = new Slider(this,6);
        sliderHeight.setLocation(35,60);
        sliderHeightText = new JLabel(Integer.toString(6));
        sliderHeightText.setLocation(17,67);
        sliderHeightText.setSize(25,20);
        sliderHeightText.setForeground(Color.BLACK);

        changeGridButton = new JButton("Generate new Grid");
        changeGridButton.addActionListener(this);
        changeGridButton.setSize(165,20);
        changeGridButton.setLocation(10, 110);

        gridsList = new JComboBox();
        gridsList.setSize(165,30);
        gridsList.setLocation(10,150);

        saveGridButton = new JButton("Save current grid");
        saveGridButton.addActionListener(this);
        saveGridButton.setSize(165,20);
        saveGridButton.setLocation(10, 200);

        for (Grid g: gridsFromSave) gridsList.addItem(g);
        gridsList.addActionListener(this);

        add(sliderWidthText);
        add(sliderWidth);
        add(sliderHeightText);
        add(sliderHeight);
        add(changeGridButton);
        add(gridsList);
        add(saveGridButton);
        this.setVisible(true);
    }

    public void change(Slider slider, int value){
        if(slider == sliderWidth) sliderWidthText.setText(Integer.toString(value));
        else if(slider == sliderHeight) sliderHeightText.setText(Integer.toString(value));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == changeGridButton) {
            Main.grid.changeGridSize(sliderWidth.getValue(), sliderHeight.getValue());
            Main.mainWindow.update(Main.grid);
        }else if(e.getSource() == gridsList){
            Main.grid = gridsFromSave.get(gridsList.getSelectedIndex());
            Main.mainWindow.update(Main.grid);
        }else if(e.getSource() == saveGridButton){
            try {
                Main.grid.setGridName("Test" + gridsFromSave.size());
                GridDataParser.writeGridToSave(Main.grid, gridsFromSave.size());
                gridsFromSave = GridDataParser.readAllGridsFromSave();
                gridsList.addItem(gridsFromSave.get(gridsFromSave.size()-1));
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }

    }
}

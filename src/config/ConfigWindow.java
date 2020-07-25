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
    private final JLabel saveListText;
    private final JComboBox gridsList;
    private ArrayList<Grid> gridsFromSave;
    private final JButton saveGridButton;
    private final JTextField saveNameField;
    private final JLabel modeText;
    private final JComboBox modeList;
    public int mode;

    public ConfigWindow(int width, int height){
        mode = 0;
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


        sliderHeight = new Slider(this,6);
        sliderHeight.setLocation(35,60);
        sliderHeightText = new JLabel(Integer.toString(6));
        sliderHeightText.setLocation(17,67);
        sliderHeightText.setSize(25,20);
        sliderHeightText.setForeground(Color.BLACK);

        changeGridButton = new JButton("Generate new Grid");
        changeGridButton.addActionListener(this);
        changeGridButton.setSize(175,20);
        changeGridButton.setLocation(10, 110);
        changeGridButton.setForeground(new Color(0,0,0));
        changeGridButton.setBackground(new Color(255,255,255));

        saveListText = new JLabel("Save file:");
        saveListText.setSize(165, 20);
        saveListText.setLocation(10,135);

        gridsList = new JComboBox();
        gridsList.setSize(175,30);
        gridsList.setLocation(10,155);
        for (Grid g: gridsFromSave) gridsList.addItem(g);
        gridsList.addActionListener(this);
        gridsList.setForeground(new Color(0,0,0));
        gridsList.setBackground(new Color(255,255,255));
        gridsList.setFocusable(false);

        saveNameField = new JTextField();
        saveNameField.setSize(105,25);
        saveNameField.setLocation(10, 190);
        saveNameField.setColumns(1);

        saveGridButton = new JButton("Save");
        saveGridButton.addActionListener(this);
        saveGridButton.setSize(65,25);
        saveGridButton.setLocation(120, 190);
        saveGridButton.setForeground(new Color(0,0,0));
        saveGridButton.setBackground(new Color(255,255,255));

        modeText = new JLabel("Mode selection:");
        modeText.setSize(165, 20);
        modeText.setLocation(10,220);

        modeList = new JComboBox();
        modeList.setLocation(10,240);
        modeList.setSize(175,30);
        modeList.addActionListener(this);
        modeList.addItem("Add/Remove walls");
        modeList.addItem("Select start location");
        modeList.addItem("Select goal location");
        modeList.setForeground(new Color(0,0,0));
        modeList.setBackground(new Color(255,255,255));
        modeList.setFocusable(false);

        add(sliderWidthText);
        add(sliderWidth);
        add(sliderHeightText);
        add(sliderHeight);
        add(changeGridButton);
        add(saveListText);
        add(gridsList);
        add(saveNameField);
        add(saveGridButton);
        add(modeText);
        add(modeList);
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
            Main.mainWindow.update();
        }else if(e.getSource() == gridsList){
            Main.grid = gridsFromSave.get(gridsList.getSelectedIndex());
            Main.mainWindow.update();
            Main.solve();
        }else if(e.getSource() == saveGridButton){
            try {
                Grid gridToSave = Main.grid;
                gridToSave.setGridName(saveNameField.getText());
                GridDataParser.writeGridToSave(gridToSave, gridsFromSave.size());
                gridsFromSave = GridDataParser.readAllGridsFromSave();
                gridsList.removeAll();
                for (Grid g: gridsFromSave) gridsList.addItem(g);
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }else if(e.getSource() == modeList){
            mode = modeList.getSelectedIndex();
        }

    }
}

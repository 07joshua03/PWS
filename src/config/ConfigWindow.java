package config;

import helper.DataParser;
import helper.Slider;
import helper.Vec2;
import main.Main;
import robot.Maze;

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
    private final JComboBox<Maze> mazesList;
    private ArrayList<Maze> mazesFromSave;
    private final JButton saveGridButton;
    private final JTextField saveNameField;
    private final JComboBox<String> modeList;
    private final JCheckBox totalGridCheckBox;
    private final JCheckBox seenGridCheckBox;

    public int mode;
    public boolean totalGridVisible = true;
    public boolean seenGridVisible = false;

    public ConfigWindow(int width, int height){
        mode = 0;
        setLayout(null);
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2-800/2) - width, (dim.height/2-800/2));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mazesFromSave = new ArrayList<>();
        try {
            mazesFromSave = DataParser.readAllMazesFromSave();
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

        changeGridButton = new JButton("Generate new maze");
        changeGridButton.addActionListener(this);
        changeGridButton.setSize(175,20);
        changeGridButton.setLocation(10, 110);
        changeGridButton.setForeground(new Color(0,0,0));
        changeGridButton.setBackground(new Color(255,255,255));

        JLabel saveListText = new JLabel("Save file:");
        saveListText.setSize(165, 20);
        saveListText.setLocation(10,135);

        mazesList = new JComboBox<>();
        mazesList.setSize(175,30);
        mazesList.setLocation(10,155);
        for (Maze maze: mazesFromSave) mazesList.addItem(maze);
        mazesList.addActionListener(this);
        mazesList.setForeground(new Color(0,0,0));
        mazesList.setBackground(new Color(255,255,255));
        mazesList.setFocusable(false);

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

        JLabel modeText = new JLabel("Mode selection:");
        modeText.setSize(165, 20);
        modeText.setLocation(10,220);

        modeList = new JComboBox<>();
        modeList.setLocation(10,240);
        modeList.setSize(175,30);
        modeList.addActionListener(this);
        modeList.addItem("Add/Remove walls");
        modeList.addItem("Select start location");
        modeList.addItem("Select goal location");
        modeList.setForeground(new Color(0,0,0));
        modeList.setBackground(new Color(255,255,255));
        modeList.setFocusable(false);

        totalGridCheckBox = new JCheckBox();
        totalGridCheckBox.setLocation(10,275);
        totalGridCheckBox.setSize(20,20);
        totalGridCheckBox.setSelected(true);
        totalGridCheckBox.addActionListener(this);

        JLabel totalGridCheckBoxText = new JLabel("See total grid");
        totalGridCheckBoxText.setLocation(30,275);
        totalGridCheckBoxText.setSize(80,20);

        seenGridCheckBox = new JCheckBox();
        seenGridCheckBox.setLocation(10,300);
        seenGridCheckBox.setSize(20,20);
        seenGridCheckBox.setSelected(false);
        seenGridCheckBox.addActionListener(this);

        JLabel seenGridCheckBoxText = new JLabel("See seen grid");
        seenGridCheckBoxText.setLocation(30,300);
        seenGridCheckBoxText.setSize(80,20);

        add(sliderWidthText);
        add(sliderWidth);
        add(sliderHeightText);
        add(sliderHeight);
        add(changeGridButton);
        add(saveListText);
        add(mazesList);
        add(saveNameField);
        add(saveGridButton);
        add(modeText);
        add(modeList);
        add(totalGridCheckBox);
        add(totalGridCheckBoxText);
        add(seenGridCheckBox);
        add(seenGridCheckBoxText);
        this.setVisible(true);
    }

    public void change(Slider slider, int value){
        if(slider == sliderWidth) sliderWidthText.setText(Integer.toString(value));
        else if(slider == sliderHeight) sliderHeightText.setText(Integer.toString(value));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == changeGridButton) {
            Main.maze.grid.changeGridSize(sliderWidth.getValue(), sliderHeight.getValue());
            Main.maze = Maze.generateMaze("", sliderWidth.getValue(), sliderHeight.getValue(), new Vec2(0,0), new Vec2(sliderWidth.getValue() - (sliderWidth.getValue()/2) - 1, sliderHeight.getValue() - (sliderHeight.getValue()/2) - 1));
            Main.shouldSolve = true;
            Main.render();
            //Main.mainWindow.update();
        }else if(e.getSource() == mazesList){
            Main.maze = mazesFromSave.get(mazesList.getSelectedIndex());
            //Main.mainWindow.update();
            Main.shouldSolve = true;
            Main.render();
        }else if(e.getSource() == saveGridButton){
            try {
                Maze mazeToSave = Main.maze;
                mazeToSave.grid.setGridName(saveNameField.getText());
                DataParser.writeMazeToSave(mazeToSave, mazesFromSave.size());
                mazesFromSave = DataParser.readAllMazesFromSave();
                mazesList.addItem(mazesFromSave.get(mazesFromSave.size() - 1));
                Main.render();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }else if(e.getSource() == modeList){
            mode = modeList.getSelectedIndex();
        }else if(e.getSource() == totalGridCheckBox){
            totalGridVisible = totalGridCheckBox.isSelected();
        }else if(e.getSource() == seenGridCheckBox){
            seenGridVisible = seenGridCheckBox.isSelected();
        }

    }
}

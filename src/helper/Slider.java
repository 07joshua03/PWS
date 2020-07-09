package helper;

import config.ConfigWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider extends JSlider implements ChangeListener{

        ConfigWindow configWindow;

        public Slider(ConfigWindow cw, int value){
            configWindow = cw;
            this.setSize(135,50);
            this.setOrientation(SwingConstants.HORIZONTAL);
            this.setMinimum(1);
            this.setMaximum(12);
            this.setValue(value);
            this.setPaintTicks(true);
            this.setMajorTickSpacing(1);
            this.setOpaque(false);
            this.setSnapToTicks(true);
            this.addChangeListener(this);
        }

    public void stateChanged(ChangeEvent ce){
        configWindow.change(this,getValue());
    }

    }

/*
    SwingMinesweeper - Simple Minesweeper game in javax.swing (for exercising).
    ControlBar class   

    Author: Arnold Beiland (beiland.arnold@gmail.com)


    Copyright (C) 2017 Arnold Beiland

    This file is part of SwingMinesweeper.

    SwingMinesweeper is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SwingMinesweeper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SwingMinesweeper.  If not, see <http://www.gnu.org/licenses/>.   
*/


package com.github.arnoldbeiland.swingminesweeper.view;

import com.github.arnoldbeiland.swingminesweeper.controller.GameController;
import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;



public class ControlBar extends JPanel {
    private GameController controller;
    
    private final int[][] boardConfigs = {
        {8, 10},
        {15, 50},
        {20, 100}
    };
    private int[] selectedBoardConfig = boardConfigs[0];
   
    private JPanel leftPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JButton newGameButton = new JButton("New Game");
    private ButtonGroup myButtonGroup = new ButtonGroup();
    
    private int timeValue = 0;
    private int stepsValue = 0;
    private JLabel counterLabel = new JLabel();
    
    
    public ControlBar() {
        newGameButton.addActionListener( e -> controller.newGame());    
        leftPanel.add(newGameButton);
        
        for(int[] bc : boardConfigs){
            JRadioButton currRadio = new JRadioButton(
                bc[0] + "x" + bc[0], 
                bc == selectedBoardConfig
            );
            
            currRadio.addActionListener( e -> selectedBoardConfig = bc );     
            centerPanel.add(currRadio);
            myButtonGroup.add(currRadio);
        }
        
        setLayout(new BorderLayout());
        add(leftPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(counterLabel,BorderLayout.EAST);
    }
    
    
    public void setController(GameController controller) {
        this.controller = controller;
    }

    
    public int[] getSelectedBoardConfig() {
        return selectedBoardConfig;
    }
    
    
    public void setTimeValue(int value) {
        timeValue = value;
        counterLabel.setText(stepsValue + " steps / " + timeValue + "s");
    }
    
    
    public void setStepsValue(int value) {
        stepsValue = value;
        counterLabel.setText(stepsValue + " steps / " + timeValue + "s");
    }
}

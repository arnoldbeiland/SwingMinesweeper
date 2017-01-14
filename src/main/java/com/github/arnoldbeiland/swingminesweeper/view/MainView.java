/*
    SwingMinesweeper - Simple Minesweeper game in javax.swing (for exercising).
    MainView class   

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
import com.github.arnoldbeiland.swingminesweeper.model.Board;
import java.awt.BorderLayout;
import javax.swing.JFrame;


public class MainView extends JFrame {
    private Board board;
    
    private ControlBar controlBar;
    private BoardView boardView;
    
    private GameController gameController;
    
    
    public MainView() {
        board = new Board();
        
        controlBar = new ControlBar();
        boardView = new BoardView(board);
        
        gameController = new GameController(board, boardView,controlBar);
        gameController.newGame();
        
        boardView.setController(gameController);
        controlBar.setController(gameController);
         
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(controlBar, BorderLayout.NORTH);
        getContentPane().add(boardView, BorderLayout.CENTER);
        
        
        setTitle("Swing Minesweeper");
        setSize(440,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

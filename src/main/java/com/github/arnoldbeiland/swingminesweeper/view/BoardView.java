/*
    SwingMinesweeper - Simple Minesweeper game in javax.swing (for exercising).
    BoardView class   

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
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;


public class BoardView extends JPanel {
    private Board board;
    private GameController controller;
    private ImageRepository myImgRepo = null;
    
    private int boardSize;
    private int cellSize;
    private int panelWidth;
    private int panelHeight;
    private int widthOffset;
    private int heightOffset;
    
    public BoardView(Board board) {
        this.board = board;
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = (int) Math.round((e.getY() - heightOffset) / cellSize);
                int col = (int) Math.round((e.getX() - widthOffset) / cellSize); 
                
                if (e.getButton() == e.BUTTON1) {
                    controller.reveal(row, col);
                }
                
                else if (e.getButton() == e.BUTTON3) {
                    controller.toggleMark(row, col);
                }
            }
        });
    } 
     
    
    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        panelWidth = this.getWidth();
        panelHeight = this.getHeight();
        
        boardSize = Math.min(panelWidth-5, panelHeight-5);
        
        int newCellSize = boardSize / board.getSize();
        if (myImgRepo == null || cellSize != newCellSize) {
            cellSize = newCellSize;
            myImgRepo = new ImageRepository(cellSize);
        }
        
        boardSize = cellSize * board.getSize();
        
        widthOffset = (panelWidth - boardSize) / 2;
        heightOffset = (panelHeight - boardSize) / 2;
        
        for (int i = 0; i < board.getSize(); ++i){
            for (int j = 0; j < board.getSize(); ++j) {
                g.drawImage(
                    myImgRepo.getImageForCell(board.getCell(i,j)),
                    widthOffset + j * cellSize,
                    heightOffset + i * cellSize,
                    null
                );
            }
        }
    }
}

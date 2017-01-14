/*
    SwingMinesweeper - Simple Minesweeper game in javax.swing (for exercising).
    GameController class   

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


package com.github.arnoldbeiland.swingminesweeper.controller;

import com.github.arnoldbeiland.swingminesweeper.model.Board;
import com.github.arnoldbeiland.swingminesweeper.model.Cell;
import com.github.arnoldbeiland.swingminesweeper.view.BoardView;
import com.github.arnoldbeiland.swingminesweeper.view.ControlBar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;


public class GameController {
    private Board board;
    private BoardView boardView;
    private ControlBar controlBar;
    
    private Random random = new Random();
    private Timer timer;
    private int counterValue = 0;
    private int stepsValue = 0;
    
    private int mineCount;
    private int still_covered;
    private boolean inGame;
    
        
    public GameController(Board board, BoardView boardView, ControlBar controlBar) {
        this.board = board;
        this.boardView = boardView;
        this.controlBar = controlBar;
        this.inGame = false;
    }
    
    
    public void newGame() {
        this.inGame = true;
        board.setSizeReinitCells(controlBar.getSelectedBoardConfig()[0]);
        mineCount = controlBar.getSelectedBoardConfig()[1];
        still_covered = board.getSize() * board.getSize() - mineCount;
        restart_timer();
       
        fillRandom();
        stepsValue = 0;
        controlBar.setStepsValue(stepsValue);
        
        boardView.repaint();
    }
    
    
    public void endGame(boolean won) {
        inGame = false;
        timer.cancel();
        uncoverAll();
        boardView.repaint();
        JOptionPane.showMessageDialog(boardView, "You " + (won ? "won":"lost") + "!");
    }
    
    
    public void reveal(int row, int col) {
        if (!inGame || 
            row < 0 || row >= board.getSize() ||
            col < 0 || col >= board.getSize()    ) return;
        
        if (!board.getCell(row,col).isMarked()) {
            controlBar.setStepsValue(++stepsValue);
            if (board.getCell(row,col).isMine()) {  
                endGame(false);
            }
            else {
                dfs_check_uncover(row, col);
                boardView.repaint();
                if (still_covered == 0) {
                    endGame(true);
                }
            }
        }
    }
    
    
    private void dfs_check_uncover(int row, int col) {
        if (row>=0 && row<board.getSize() && col>=0 && col<board.getSize()) {
            Cell c = board.getCell(row,col);
            
            if(c.isCovered() && !c.isMine()){
                --still_covered;
                c.setMarked(false);
                c.setCovered(false);
                if (c.getContent() == 0) {
                    dfs_check_uncover(row - 1, col - 1); //NW
                    dfs_check_uncover(row - 1, col    ); //N
                    dfs_check_uncover(row - 1, col + 1); //NE
                    dfs_check_uncover(row    , col - 1); //W
                    dfs_check_uncover(row    , col + 1); //E
                    dfs_check_uncover(row + 1, col - 1); //SW
                    dfs_check_uncover(row + 1, col    ); //S
                    dfs_check_uncover(row + 1, col + 1); //SE
                }
            }
        }
    }
    
    
    public void toggleMark(int row, int col) {
        if (!inGame || 
            row < 0 || row >= board.getSize() ||
            col < 0 || col >= board.getSize()    ) return;
        
        Cell c = board.getCell(row,col);
        if (c.isCovered()) {
            controlBar.setStepsValue(++stepsValue);
            c.setMarked(!c.isMarked());
        }
        boardView.repaint();
    }
    
    
    private void restart_timer() {
        if (timer != null) { 
            timer.cancel();
        }
        
        counterValue = 0;
        controlBar.setTimeValue(counterValue);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counterValue++;
                controlBar.setTimeValue(counterValue);
            }
        },1000,1000);
    }
    
    
    public void fillRandom() {
        int size = board.getSize();
        
        // generate mines' positions
        for(int i = 0; i < mineCount; ++i){
            int ci;
            int cj;
            
            do {
                ci = random.nextInt(size);
                cj = random.nextInt(size);
            } while (board.getCell(ci,cj) != null);

            board.setCell(ci, cj, new Cell(Cell.MINE_CONTENT_VALUE));
        }
        
        // set cells' values
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (board.getCell(i,j) == null) {
                    int nrmines =                     
                        ( isCorrectAndMine(i-1, j-1) ? 1 : 0 ) + //NW
                        ( isCorrectAndMine(i-1, j  ) ? 1 : 0 ) + //N
                        ( isCorrectAndMine(i-1, j+1) ? 1 : 0 ) + //NE
                        ( isCorrectAndMine(i,   j-1) ? 1 : 0 ) + //W
                        ( isCorrectAndMine(i,   j+1) ? 1 : 0 ) + //E
                        ( isCorrectAndMine(i+1, j-1) ? 1 : 0 ) + //SW
                        ( isCorrectAndMine(i+1, j  ) ? 1 : 0 ) + //S
                        ( isCorrectAndMine(i+1, j+1) ? 1 : 0 );  //SE
                    board.setCell(i, j, new Cell(nrmines)); 
                }
            }
        }
    }
    
    
    boolean isCorrectAndMine(int i, int j) {
        return ( i>=0 && i<board.getSize() && j>=0 && j<board.getSize() && 
                 board.getCell(i,j) != null &&
                 board.getCell(i,j).isMine() );
    }
    
    
    public void uncoverAll() {
        for (int i = 0; i < board.getSize(); ++i) {
            for (int j = 0; j < board.getSize(); ++j) {
                board.getCell(i, j).setCovered(false);
            }
        }
    }
}

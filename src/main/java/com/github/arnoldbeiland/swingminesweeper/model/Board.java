/*
    SwingMinesweeper - Simple Minesweeper game in javax.swing (for exercising).
    Board class   

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


package com.github.arnoldbeiland.swingminesweeper.model;

import java.util.Random;


public class Board {
    
    private Random random = new Random();
    private int size;
    private Cell[][] cells;
 

    public Board() {
        this(0, null);
    }
    
    
    public Board(int size, Cell[][] cells) {
        this.size = size;
        this.cells = cells;
    }

    
    public int getSize() {
        return size;
    }

    
    public void setSizeReinitCells(int size) {
        this.size = size;
        cells = new Cell[size][];
        for (int i = 0; i < size; ++i) {
            cells[i] = new Cell[size];
        }
    }

    
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    
    public void setCell(int row, int col, Cell value) {
        this.cells[row][col] = value;
    }
}

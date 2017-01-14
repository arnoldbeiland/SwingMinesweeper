/*
    SwingMinesweeper - Simple Minesweeper game in javax.swing (for exercising).
    Cell class   

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


public class Cell {
    
    public static final int MINE_CONTENT_VALUE = -1;
    
    private int content;        // (-1) means: "mine", (>=0) means: number of neighbouring mines 
    private boolean covered;
    private boolean marked;

    
    public Cell() {
        this(0,true,false);
    }
    
    
    public Cell(int content) {
        this(content, true, false);
    }
    
    
    public Cell(int content, boolean covered, boolean marked) {
        this.content = content;
        this.covered = covered;
        this.marked = marked;
    }

    
    public boolean isCovered() {
        return covered;
    }

    
    public void setCovered(boolean covered) {
        this.covered = covered;
    }
    
    
    public boolean isMarked() {
        return marked;
    }

    
    public void setMarked(boolean marked) {
        this.marked = marked;
    }
    
    
    public boolean isMine() {
        return content == -1;
    }
    
    
    public void makeMine() {
        content = -1;
    }
    
    
    public int getContent() {
        return content;
    }
    
    
    public void setContent(int value) {
        this.content = value;
    } 
}

/*
    SwingMinesweeper - Simple Minesweeper game in javax.swing (for exercising).
    ImageRepository class   

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

import com.github.arnoldbeiland.swingminesweeper.model.Cell;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;


public class ImageRepository {
    private static final Color BORDER_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = new Color(180,180,180);
    private static final Color COVER_COLOR = new Color(100,100,100);
    private static final Color FONT_COLOR = Color.BLUE;
    private static final Color MINE_COLOR = Color.BLACK;
    private static final Color MINE_COLOR_INT = new Color(150,0,0);
    private static final Color MARK_COLOR = Color.RED;
    
    
    private int size;
    private int globalOffset;
    private int activeSize;
    
    private Font font;
    private FontMetrics fontMetrics;
    
    private BufferedImage numberContentImages[] = new BufferedImage[9];
    private BufferedImage wrongMarkImages[] = new BufferedImage[9];
    private BufferedImage mineImage;
    private BufferedImage coveredCellImage;
    private BufferedImage markedCellImage;
    private BufferedImage wellMarkedCellImage; 
    
    public ImageRepository(int size) {
        this.size = size;
        activeSize = (int) (size * 0.98);
        globalOffset = (int) (size * 0.01);
        setCurrentFont();
        
        Graphics2D g2d;
        
        for (int i = 0; i <= 8; ++i) {
            numberContentImages[i] = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB); 
            g2d = numberContentImages[i].createGraphics();
            paintBackground(g2d);
            if(i!=0){
                paintCharacter((char) ('0' + i), g2d);
            }
            g2d.dispose();
            
            wrongMarkImages[i] = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB); 
            g2d = wrongMarkImages[i].createGraphics();
            paintWrongMark(g2d);
            if(i!=0){
                paintCharacter((char) ('0' + i), g2d);
            }
            g2d.dispose();
        }
        
        mineImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        g2d = mineImage.createGraphics();
        paintBackground(g2d);
        paintMine(g2d);
        g2d.dispose();
        
        coveredCellImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        g2d = coveredCellImage.createGraphics();
        paintCover(g2d);
        g2d.dispose();
        
        markedCellImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        g2d = markedCellImage.createGraphics();
        paintMark(g2d);
        g2d.dispose();
        
        wellMarkedCellImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        g2d = wellMarkedCellImage.createGraphics();
        paintMark(g2d);
        paintMine(g2d);
        g2d.dispose();
    } 
    
  
    public BufferedImage getImageForCell(Cell cell) {
        if (cell.isCovered()) {
            return cell.isMarked() ? markedCellImage : coveredCellImage;
        }
        else if (cell.isMarked()) {
            return cell.isMine() ? wellMarkedCellImage : wrongMarkImages[cell.getContent()];
        }   
        else {
            return cell.isMine() ? mineImage : numberContentImages[cell.getContent()]; 
        }
    }
    
    
    private void setCurrentFont() {
        int targetHeight = activeSize;
        int candidateFontSize = 1;
        
        Font nextFont = new Font("Arial", Font.PLAIN, candidateFontSize);
        FontMetrics nextFontMetrics = new Canvas().getFontMetrics(nextFont);
        int nextHeight = 0;
        
        do {
            font = nextFont;
            fontMetrics = nextFontMetrics;
            ++candidateFontSize;
            nextFont = new Font("Arial", Font.PLAIN, candidateFontSize);
            FontMetrics fm = new Canvas().getFontMetrics(nextFont);
            nextHeight = fm.getAscent();
        } while (nextHeight <= targetHeight);
    }
    
    
    private void paintBackground(Graphics2D g2d) {
        g2d.setColor(BORDER_COLOR);
        g2d.fillRect(0,0,size,size);
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(globalOffset, globalOffset, activeSize, activeSize);
    }
    
    
    private void paintCharacter(char c, Graphics2D g2d) {
        g2d.setFont(font);
        g2d.setColor(FONT_COLOR);
        
        char[] c_arr = new char[] {c};
        String text = new String(c_arr);
            
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);     
        int textwidth = (int)( font.getStringBounds(text, frc).getWidth() );

        int woffs = (size - textwidth) / 2;
        int hoffs = (int) (activeSize * 0.92); 

        g2d.drawChars(c_arr, 0, 1, woffs, hoffs);
    }
    
    
    private void paintCover(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,size,size);
        g2d.setColor(COVER_COLOR);
        g2d.fillRect(globalOffset, globalOffset, activeSize, activeSize);
    }
    
    
    private void paintMine(Graphics2D g2d) {
        int ovalSize = (int) (activeSize * 0.5);
        int ovalOffset = globalOffset + (activeSize - ovalSize) / 2;
        
        g2d.setColor(MINE_COLOR);
        g2d.fillOval(ovalOffset,ovalOffset,ovalSize,ovalSize);
        
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(ovalOffset/2.6F));
        float strokeStart = globalOffset + activeSize * 0.2F;
        float strokeEnd = strokeStart + activeSize * 0.6F;
        g2d.draw(new Line2D.Float(strokeStart, strokeStart, strokeEnd, strokeEnd));
        g2d.draw(new Line2D.Float(strokeEnd, strokeStart, strokeStart, strokeEnd));
        g2d.setStroke(oldStroke);
        
        int oval2Size = (int) (activeSize * 0.09);
        int oval2Offset = globalOffset + (int) ((activeSize - oval2Size) / 2.3);
        g2d.setColor(MINE_COLOR_INT);
        g2d.fillOval(oval2Offset,oval2Offset,oval2Size,oval2Size);
    }
    
    
    private void paintMark(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,size,size);
        g2d.setColor(Color.RED);
        g2d.fillRect(globalOffset, globalOffset, activeSize, activeSize);
    }
    
    
    private void paintWrongMark(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,size,size);
        g2d.setColor(MARK_COLOR);
        g2d.fillRect(globalOffset,globalOffset,activeSize,activeSize);
        g2d.setColor(BACKGROUND_COLOR);
        int increment = (int) (activeSize * 0.08);
        g2d.fillRect(globalOffset + increment, globalOffset + increment, activeSize - 2*increment, activeSize - 2*increment); 
    }
}

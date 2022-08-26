/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

/**
 *
 * @author Bcc
 */

import java.util.ArrayList;

public class CELL {

    Cell cellName;
    int x;
    int y;
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getRange(){return this.cellName.Range;}
    public enum Cell {
        River (15),
        Valley(1),
        Bridge(2);
        private int Range;
        Cell(int Range) {
            this.Range = Range;
        }
        public int getRange() {
            return Range;
        }
    }
    boolean CellINRange(){
        return this.x + this.cellName.getRange() <= Grid.NUM_CELLS && this.x - this.cellName.getRange() >= 0
                && this.y + this.cellName.getRange() <= Grid.NUM_CELLS && this.y - this.cellName.getRange() >= 0;
    }
    boolean squareIsOccupiedCELL(ArrayList<CELL> cellArrayList){
        boolean x = false;
        for (CELL cell : cellArrayList) {
            if (this.x>cell.getX()){
                if(this.x-this.cellName.Range>cell.getX()+cell.cellName.getRange()){
                    x = true;
                }
                else {
                    if(this.y>cell.getY()){
                        x= this.y - this.cellName.Range > cell.getY() + cell.cellName.getRange();
                    }
                    else if(this.y<cell.getY()) {
                        x = this.y + this.cellName.Range < cell.getY() - cell.cellName.getRange();
                    }
                    else x = false;
                }
            }
            else if(this.x < cell.getX()){
                if(this.x + this.cellName.Range < cell.getX()- cell.cellName.getRange()){
                    x = true;
                }
                else {
                    if(y>cell.getY()){
                        x= y - this.cellName.Range > cell.getY() + cell.cellName.getRange();
                    }
                    else if(y<cell.getY()){
                        x = y + this.cellName.Range < cell.getY() - cell.cellName.getRange();
                    }
                    else x = false;
                }
            }
            else {
                if(y>cell.getY()){
                    x= y - this.cellName.Range > cell.getY() + cell.cellName.getRange();
                }
                else if(y<cell.getY()){
                    x = y + this.cellName.Range < cell.getY() - cell.cellName.getRange();
                }
                else x = false;
            }
            if(!x){
                break;
            }

        }
        return x;
    }

}
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
public class Order {
    private int orderNum;
    private int x; 
    private int y;
    private int secondToWait;
    private int excuted;
    Order(int orderNum)
    {
        this.orderNum=orderNum;
        excuted=-1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSecondToWait() {
        return secondToWait;
    }

    public void setSecondToWait(int secondToWait) {
        this.secondToWait = secondToWait;
    }

    public int getExcuted() {
        return excuted;
    }

    public void setExcuted(int excuted) {
        this.excuted = excuted;
    }
    
    
}

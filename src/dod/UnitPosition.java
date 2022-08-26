/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bcc
 */
import java.util.ArrayList;

public class UnitPosition {

	private int centerX;
	private int centerY;
	private int radius;
    UnitPosition(int x , int y , int r){
    	this.centerX = x;
    	this.centerY = y;
    	this.radius  = r;
	}
	public boolean squareIsOccupied(ArrayList<Unit> AllUnits) {
		boolean x = false;
		for (Unit allUnit : AllUnits) {
			if (this.centerX>allUnit.getPosition().centerX){
				if(this.centerX-this.radius>allUnit.getPosition().centerX+allUnit.getPosition().radius){
					x = true;
				}
				else {
					if(this.centerY>allUnit.getPosition().centerY){
						x= this.centerY - this.radius > allUnit.getPosition().centerY + allUnit.getPosition().radius;
					}
					else if(this.centerY<allUnit.getPosition().centerY){
						x = this.centerY + this.radius < allUnit.getPosition().centerY - allUnit.getPosition().radius;
					}
					else x = false;
				}
			}
			else if(this.centerX<allUnit.getPosition().centerX){
				if(this.centerX+this.radius<allUnit.getPosition().centerX-allUnit.getPosition().radius){
					x = true;
				}
				else {
					if(this.centerY>allUnit.getPosition().centerY){
						x = this.centerY - this.radius > allUnit.getPosition().centerY + allUnit.getPosition().radius;
					}
					else if(this.centerY<allUnit.getPosition().centerY){
						x = this.centerY + this.radius < allUnit.getPosition().centerY - allUnit.getPosition().radius;
					}
					else x = false;
				}
			}
			else {
				if(this.centerY>allUnit.getPosition().centerY){
					x = this.centerY - this.radius > allUnit.getPosition().centerY + allUnit.getPosition().radius;
				}
				else if(this.centerY<allUnit.getPosition().centerY){
					x = this.centerY + this.radius < allUnit.getPosition().centerY - allUnit.getPosition().radius;
				}
				else x = false;
			}
			if(!x){
				break;
			}

		}
		return x;
	}
	boolean UnitInRange(){
            return this.centerX + this.radius < 1000000
                            && this.centerY+this.radius < 1000000
                            && this.centerX-this.radius >= 0
                            && this.centerY-this.radius >= 0;
	}
	public int getCenterX() {
		return centerX;
	}
	public int getCenterY() {
		return centerY;
	}
	public int getRadius() {
		return radius;
	}
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}


}

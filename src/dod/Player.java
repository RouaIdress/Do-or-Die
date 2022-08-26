/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.util.List;

/**
 *
 * @author Bcc
 */
public abstract class Player {

    protected int teamId;
    protected int coins;
    private int index;
    private List<Order> plan;
    
    Player(List<Order> plan)
    {
        this.plan=plan; 
    }
    
    public abstract UnitPosition NewGame(Grid grid);

    public abstract void LoadGame();

    public void setUnit(int index) {
        this.index = index;
    }

    public int getUnit() {
        return index;
    }
        
    public void buyUnit() {
        this.coins-=UnitName.values()[index].getUnitPrice();
    }

    void addtoPlan(Order order)
    {
        plan.add(order);
    }
    
    public List<Order> getPlan() {
        return plan;
    }
    
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import Property.UnitProperty;
import static dod.DoDGameManager.state;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bcc
 */
public class Unit extends Thread implements UnitDestroyObserver {

    private String name;//set
    private Unit _next;
    private Unit _prev;
    private UnitAttack activeUnitAttack;
    private UnitType[] canAttack;
    private Movement movement;
    private UnitPosition position;//set
    private Unit targetedUnit;//set
    private List<UnitDestroyObserver> unitDestroyedObservers;//don't know
    private UnitProperty[] unitProperties;//set
    private UnitType unitType;//set
    private UnitDestroyObserver destructionObservers;//don't know
    private Player owner;
    private AttackStrategy attackStrategy;
    private Grid grid;
    Timer timer;
    Logger l;
    
    public Unit(Grid grid, int x, int y, AttackStrategy attackStrategy, Player owner, Movement movement) {            
        _next=_prev=null;
        activeUnitAttack=null;
        canAttack=null;
        this.movement = movement;
        targetedUnit=null;
        this.owner=owner;
        this.attackStrategy=attackStrategy;
        this.grid=grid;
        this.unitDestroyedObservers=new ArrayList<>();
        position=new UnitPosition(x,y,0);
    }

    public Grid getGrid() {
        return grid;
    }
    
    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }

    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }
    
    public void setTime(Timer timer) {
        this.timer = timer;
    }    
    
    public String getUnitName() {
        return name;
    }

    public void setUnitName(String name) {
        this.name = name;
    }

    public Player getOwner() {
        return owner;
    }
        
    public void set_next(Unit _next) {
            this._next = _next;
    }
    public void set_prev(Unit _prev) {
            this._prev = _prev;
    }
    public void setActiveUnitAttack(UnitAttack activeUnitAttack) {
            this.activeUnitAttack = activeUnitAttack;
    }
    public void setCanAttack(UnitType[] canAttack) {
            this.canAttack = canAttack;
    }
    public void setDestructionObservers(UnitDestroyObserver destructionObservers) {
            this.destructionObservers = destructionObservers;
    }
    public void setMovement(Movement movement) {
            this.movement = movement;
    }
    public void setPosition(UnitPosition position) {
            this.position = position;
    }
    public void setTargetedUnit(Unit targetedUnit) {
            this.targetedUnit = targetedUnit;
    }
    public void setUnitProperties(UnitProperty[] unitProperties) {
            this.unitProperties = unitProperties;
    }
    public void setUnitType(UnitType unitType) {
            this.unitType = unitType;
    }
    
    public Unit get_next() {
            return _next;
    }
    public Unit get_prev() {
            return _prev;
    }
    public Movement getMovement() {
            return movement;
    }
    public UnitAttack getActiveUnitAttack() {
            return activeUnitAttack;
    }
    public Unit getTargetedUnit() {
            return targetedUnit;
    }
    public UnitType[] getCanAttack() {
            return canAttack;
    }
    public UnitDestroyObserver getDestructionObservers() {
            return destructionObservers;
    }
    public UnitPosition getPosition() {
            return position;
    }
    public UnitProperty[] getUnitProperties() {
            return unitProperties;
    }
    public UnitType getUnitType() {
            return unitType;
    }

    public UnitProperty getUnitProperties(int n) {
        return this.unitProperties[n];
    }
    public synchronized void AcceptDamage(double damage) {
        unitProperties[0].setPropertyValue(unitProperties[0].getPropertyValue()-damage);
        l=Logger.getLogger(Unit.class.getName());
        l.log(Level.INFO, name +" Health is dicreasing "+unitProperties[0].getPropertyValue());
        if(unitProperties[0].getPropertyValue()<=0.0 && unitType!=UnitType.MainBase && owner.teamId==1)
        {
            DoDGameManager.setAttackerUnitsCounter();
            l.log(Level.SEVERE, "******************** Attacker Unit "+ name+" is down ********************");
        }
        if(unitProperties[0].getPropertyValue()<=0.0 && unitType==UnitType.MainBase)
        {
            DoDGameManager.state=GameState.AttackerWon;
            l.log(Level.SEVERE, "******************** MAin Base is down ********************");
        }
    }

    public void AttackUnit(Unit targetUnit)//new NormalUnitAttack(this)
    {
        l=Logger.getLogger(Unit.class.getName());
        l.log(Level.INFO, this.name +" is attacking "+targetUnit.getUnitName());
        activeUnitAttack=new NormalUnitAttack(this);
        targetUnit.AcceptDamage(activeUnitAttack.PerformAttack(targetUnit).result);
    }

    public final void onDestroy() {
        attackStrategy.setUnit(this);
        Unit targetedUnit=this.attackStrategy.PrioritizeUnitToAttack(grid.getAllUnits());
        if(targetedUnit!=null)
        {
            this.AttackUnit(targetedUnit);
        }
    }

    public void addUnitDestroyObserver(UnitDestroyObserver unitDestroyObserver) {
        unitDestroyedObservers.add(this);
    }


    @Override
    public void OnUnitDestroy(Unit destroyedUnit) {
        //addUnitDestroyObserver.
    }

    boolean canTarget(Unit TargetUnit)
    {
        for(int i=0;i<canAttack.length;i++)
        {
            if(TargetUnit.unitType==canAttack[i])
            {
                return true;
            }
        }
        return false;
    }
    
    
    boolean onMyVision(Unit TargetUnit)
    {
        double nourth=position.getCenterX()-position.getRadius()-unitProperties[3].getPropertyValue();
        double south=position.getCenterX()+position.getRadius()+unitProperties[3].getPropertyValue();
        double western=position.getCenterY()+position.getRadius()+unitProperties[3].getPropertyValue();
        double eastern=position.getCenterY()-position.getRadius()-unitProperties[3].getPropertyValue();
        int x=TargetUnit.position.getCenterX();
        int y=TargetUnit.position.getCenterY();
        int r=TargetUnit.position.getRadius();
        if((x+r>=nourth && x-r<=south) && (y-r<=western && y+r>=eastern))
        {
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while(!timer.Finished()&& DoDGameManager.state==GameState.Running)
        {
            if(unitProperties[0].getPropertyValue()>0.00)
            {
            this.onDestroy();
            if(this.unitProperties[4].getPropertyValue()>0.0)
            {                
                LocalTime time=LocalTime.now();
                Timer t;
                int min;
                int sec;
                if(time.getMinute()+(int)(unitProperties[4].getPropertyValue()%10)>59)
                {
                    min=time.getMinute()+(int)(unitProperties[4].getPropertyValue()%10)-59;
                    if(LocalTime.now().getSecond()+((int)(unitProperties[4].getPropertyValue()*10)%10)>59)
                    {
                        sec=LocalTime.now().getSecond()+((int)(unitProperties[4].getPropertyValue()*10)%10)-59;
                        t=new Timer(min,sec);
                    }
                    else
                    {
                        sec=LocalTime.now().getSecond()+((int)(unitProperties[4].getPropertyValue()*10)%10);
                        t=new Timer(min,sec);
                    } 
                }
                else
                {
                    min=time.getMinute()+(int)(unitProperties[4].getPropertyValue()%10);
                    if(LocalTime.now().getSecond()+((int)(unitProperties[4].getPropertyValue()*10)%10)>59)
                    {
                        sec=LocalTime.now().getSecond()+((int)(unitProperties[4].getPropertyValue()*10)%10)-59;
                        t=new Timer(min,sec);
                    }
                    else
                    {
                        sec=LocalTime.now().getSecond()+((int)(unitProperties[4].getPropertyValue()*10)%10);
                        t=new Timer(min,sec);
                    } 
                }   
                t.start();
                while(!t.Finished())
                {
                    movement.move(grid, this);
                    //move or plan
                }
            }
        movement.move(grid, this);
        }
    }
        System.out.println("time should finish: "+timer.min+": "+timer.sec);
        System.out.println("time now: "+LocalTime.now().getMinute()+": "+LocalTime.now().getSecond());
        if(timer.Finished())
        {
            DoDGameManager.state=GameState.Paused;
        }
    }

    public class MaxDamage implements Comparator
    {
        double getDamageAttack(Unit unit)
        {
            return unit.unitProperties[2].getPropertyValue();
        }
        
        @Override
        public int compare(Object o1, Object o2) {
            double n=getDamageAttack((Unit)o1)-getDamageAttack((Unit)o2);
            if(n>0)
            {
                return 1;
            }
            else if(n<0)
            {
                return -1;
            }
            return 0;
        }        
    }
    
    public class MinHealth implements Comparator
    {
        double getHealth(Unit unit)
        {
            return unit.unitProperties[0].getPropertyValue();
        }
        
        @Override
        public int compare(Object o1, Object o2) {
            double n =getHealth((Unit)o1)-getHealth((Unit)o2);
            if(n>0)
            {
                return 1;
            }
            else if(n<0)
            {
                return -1;
            }
            return 0;
        }        
    }
}

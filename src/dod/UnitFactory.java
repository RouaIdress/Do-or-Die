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
import Property.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnitFactory {

    public Unit CreateUnit(Grid grid, int x, int y, Player owner,int index) {
            
        Movement movement=null;
        if(owner.teamId==0)
        {
            movement=new DefenderMovement();
        }
        else if(owner.teamId==1)
        {
            movement=new AttackerMovement();
        }

        AttackStrategy attackStrategy=null;          
        Scanner scanner = new Scanner(System.in);
        int a ;
          
        do
        {
          Logger logger = Logger.getLogger("");
          logger.log(Level.INFO, "Enter the type of strategy you want "
                  + "\n1. Random attack strategy "
                  + "\n2. Highest damage atttack strategy"
                  + "\n3. lowest health attack strategy");

          a = scanner.nextInt();
          switch(a){
              case 1 :
                 attackStrategy = new RandomAttackStrategy();
                 break;
              case 2:
                 attackStrategy = new HighestDamageAttackStrategy();
                 break;
              case 3:
                  attackStrategy =new LowestHealthAttackStrategy();
                  break;         
          }
        }while (a < 1 || a > 3);
            
            
        Unit unit = new Unit(grid , x, y, attackStrategy, owner, movement);
        Unit unit_prev = new Unit(grid,x,y,null,null,null);
        unit.set_prev(unit_prev);
        //		UnitAttack unitAttack = new NormalUnitAttack();
//		unit.setActiveUnitAttack(unitAttack);
        
        unit.setUnitType(UnitName.values()[index].getUnitType());
        unit.setCanAttack(UnitName.values()[index].getUnitTypes());
        unit.setPosition(new UnitPosition(x,y,UnitName.values()[index].getRadius()));
        unit.setUnitName(UnitName.values()[index].name());
        UnitProperty[] unitProperties=setProperties(index);
        unit.setUnitProperties(unitProperties);

        return unit;
    }
    
    UnitProperty[] setProperties(int index)
    {
        UnitProperty unitProperties[] = new UnitProperty[8];
        unitProperties[0] = new HealthUnitProperty();
        unitProperties[1] = new ArmorUnitProperty();
        unitProperties[2] = new AttackDamageProperty();
        unitProperties[3] = new AttackRangeProperty();
        unitProperties[4] = new AttackSpeedUnitProperty();
        unitProperties[5] = new RadiusProperty();
        unitProperties[6] = new MovementSpeedProperty();
        
        unitProperties[0].setPropertyValue(UnitName.values()[index].getHealth());
        unitProperties[1].setPropertyValue(UnitName.values()[index].getArmor());
        unitProperties[2].setPropertyValue(UnitName.values()[index].getAttackDamage());
        unitProperties[3].setPropertyValue(UnitName.values()[index].getAttackRange());
        unitProperties[4].setPropertyValue(UnitName.values()[index].getAttackSpeed());
        unitProperties[5].setPropertyValue(UnitName.values()[index].getRadius());
        unitProperties[6].setPropertyValue(UnitName.values()[index].getMovementSpeed());
        return unitProperties;
    }
}

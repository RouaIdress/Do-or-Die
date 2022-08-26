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
public class HighestDamageAttackStrategy implements AttackStrategy {

    private Unit unit;
    
    @Override
    public void setUnit(Unit unit) {
        this.unit=unit;
    }
    
    public Unit PrioritizeUnitToAttack(List<Unit> units) {
        Unit.MaxDamage m ;
        boolean check=false;
        int index=-1;
        while(!check)
        {
            index++;
            if(index==units.size())
            {
                return null;
            }
            if(!unit.equals(units.get(index))&& (units.get(index).getUnitProperties()[0].getPropertyValue()>0.0) && unit.canTarget(units.get(index)) && ((unit.getOwner().teamId==1 && (units.get(index).getUnitType()==UnitType.MainBase || (units.get(index).getUnitType()!=UnitType.MainBase&& units.get(index).getOwner().teamId==0)))|| (unit.getOwner().teamId==0 && (units.get(index).getUnitType()!=UnitType.MainBase && units.get(index).getOwner().teamId==1))) && unit.onMyVision(units.get(index)))
            {
                check=true;
                if(index==units.size()-1)
                {
                    return units.get(index);
                }
            }
        }
        m= unit.new MaxDamage();
        for(int i=index+1;i<units.size();i++)
        {
            if(!unit.equals(units.get(i)) && unit.canTarget(units.get(i))&& (units.get(i).getUnitProperties()[0].getPropertyValue()>0.0) && ((unit.getOwner().teamId==1 && (units.get(i).getUnitType()==UnitType.MainBase ||(units.get(i).getUnitType()!=UnitType.MainBase&& units.get(i).getOwner().teamId==0)))|| (unit.getOwner().teamId==0 && units.get(i).getUnitType()!=UnitType.MainBase && units.get(i).getOwner().teamId==1)) && unit.onMyVision(units.get(i)))
            {
                int result=m.compare(units.get(index), units.get(i));
                if(result==-1)
                {
                    index=i;
                }
            }
        }
        return units.get(index);
    }

}

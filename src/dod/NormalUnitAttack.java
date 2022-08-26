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
public class NormalUnitAttack extends UnitAttack {

    Unit unitAttacker;
    
    NormalUnitAttack(Unit unitAttacker)
    {
        this.unitAttacker=unitAttacker;
    }
    
    public AttackResult PerformAttack(Unit unit) {

        double result=unitAttacker.getUnitProperties(2).getPropertyValue()-(unitAttacker.getUnitProperties(2).getPropertyValue()*unit.getUnitProperties(1).getPropertyValue());
        AttackResult attackResult = new AttackResult();
        attackResult.ApplyAttackResultOnReceiver(result);
        return attackResult;
    }

}

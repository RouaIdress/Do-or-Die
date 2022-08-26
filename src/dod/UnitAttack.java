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
public abstract class UnitAttack {

	private AttackResult attackResult;
        protected Unit unitAttacker;
        
	public abstract AttackResult PerformAttack(Unit unit);

	public void setAttackResult(AttackResult attackResult) {
		this.attackResult = attackResult;
	}

}

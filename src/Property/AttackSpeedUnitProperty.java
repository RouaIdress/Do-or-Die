/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Property;

/**
 *
 * @author Bcc
 */
public class AttackSpeedUnitProperty extends UnitProperty {

    @Override
    public double getPropertyValue() {
        return this.propertyValue;
    }

    @Override
    public void setPropertyValue(double value) {
        this.propertyValue = value;
    }
}

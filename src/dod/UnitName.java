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


public enum UnitName {
    TeslaTank(1000,0.50,300,250,0.60,20,30,
            50,UnitType.Tank, new UnitType[]{UnitType.Tank, UnitType.Solider},""),
    Sniper(250,0.10,75,700,0.4,3,10,
            5,UnitType.Solider,new UnitType[]{UnitType.Solider},""),
    MirageTank(750,0.10,1000,10,1,15,60
            ,70,UnitType.Tank,new UnitType[]{UnitType.Tank,UnitType.Solider,UnitType.Structure,UnitType.MainBase},""),
    Infantry(250,0.20,50,50,1.5,3,10
            ,3,UnitType.Solider,new UnitType[]{UnitType.Solider},""),
    GrizzlyTank(1000,0.40,250,250,0.60,20,30
           ,50,UnitType.Solider,new UnitType[]{UnitType.Tank,UnitType.Solider,UnitType.Structure,UnitType.MainBase},""),
    NavySEAL(400,0.20,60,50,2,3,20
            ,10,UnitType.Solider,new UnitType[]{UnitType.Tank,UnitType.Solider},""),
    TankDestroyer(1000,0.50,400,150,0.60,20,20
            ,50,UnitType.Tank ,new UnitType[]{UnitType.Tank},""),
    PrismTank (1000,0.35,300,150,0.60,20,20
            ,60, UnitType.Tank, new UnitType[]{UnitType.Tank,UnitType.Solider,UnitType.Structure,UnitType.MainBase},""),
    Pillbox (4000,0.70,100,200,0.70,40,0
            ,150,UnitType.Structure,new UnitType[]{UnitType.Solider},""),
    PrismTower(4000,0.70,100,200,0.50,30,0
            ,150,UnitType.Structure,new UnitType[]{UnitType.Solider,UnitType.Tank},""),
    GrandCannon(6500,0.30,150,400,0.90,40,0
            ,200,UnitType.Structure,new UnitType[]{UnitType.Solider,UnitType.Tank},""),
    MainBase(10000,0.00,0,0,0.00,50,0
            ,0 ,UnitType.MainBase,null,""),
    BlackEagle(1500,0.00,400,30,0.00,0,100
            ,75,UnitType.Airplane,new UnitType[]{UnitType.MainBase},""),
    PatriotMissileSystem(2500,0.20,50,400,0.90,40,0
            ,175,UnitType.Structure,new UnitType[]{UnitType.Airplane},"");

    private double Health, Armor,AttackDamage,AttackRange,AttackSpeed  ,MovementSpeed;
    int UnitPrice,Radius;
    private UnitType unitType;
    private UnitType[] unitTypes;
    private UnitName unit;
    private String ImageName;
    UnitName(double Health,double Armor,double AttackDamage,double AttackRange,double AttackSpeed ,int Radius ,double MovementSpeed,int UnitPrice,UnitType unitType,UnitType[] unitTypes,String ImageName)
    {
    this.Health = Health;
    this.Armor  = Armor;
    this.AttackDamage = AttackDamage;
    this.AttackRange  = AttackRange;
    this.AttackSpeed  = AttackSpeed;
    this.Radius = Radius;
    this.MovementSpeed = MovementSpeed;
    this.UnitPrice = UnitPrice;
    this.unitType = unitType;
    this.unitTypes = unitTypes;
    this.ImageName=ImageName;
     }

    void setUnit(int  n)
    {
        this.unit=UnitName.values()[n];
    }        

    public String getImageName() {
        return ImageName;
    }
    
    public double getArmor() {
        return Armor;
    }

    public double getAttackDamage() {
        return AttackDamage;
    }

    public double getAttackRange() {
        return AttackRange;
    }

    public double getAttackSpeed() {
        return AttackSpeed;
    }

    public double getHealth() {
        return Health;
    }

    public double getMovementSpeed() {
        return MovementSpeed;
    }

    public int getRadius() {
        return Radius;
    }

    public int getUnitPrice() {
        return UnitPrice;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public UnitType[] getUnitTypes() {
        return unitTypes;
    }

      
    
    @Override
    public String toString() {
        String detail="Name "+unit.name();
        detail+="\nPrice "+unit.getUnitPrice();
        detail+="\nHealth "+unit.getHealth();
        detail+="\nArmor "+unit.getArmor();
        detail+="\nDamage "+unit.getAttackDamage();
        detail+="\nSpeed "+unit.getAttackSpeed();
        return detail;
    }
    
    
    
}


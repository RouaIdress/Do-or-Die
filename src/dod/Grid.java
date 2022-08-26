/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bcc
 */


public class Grid {

    public static int NUM_CELLS;
    public static int CELL_SIZE;
    private ArrayList<Unit> allUnits = new ArrayList<>();
    private ArrayList<CELL> cells = new ArrayList<>();
    Grid()
    {
        NUM_CELLS = 1000000;
        CELL_SIZE = NUM_CELLS^2;
        CELL cell = new CELL();
        cell.cellName = CELL.Cell.Bridge;
        cell.setX(30);
        cell.setY(7);
        if(cell.CellINRange())
        {
            cells.add(cell);
        }

    }

    public ArrayList<CELL> getCells() {
        return cells;
    }
    
    public ArrayList<Unit> getAllUnits() {
        return allUnits;
    }
    
    public void addUnit(Unit unit) {
        allUnits.add(unit);
    }
	/* تابع لاضافة الخلايا متل النهر والبحيرة وهالقصص*/
    void BuildCells(){
        int NUM_OF_EXTRA_CELLS = 3;

        for(int i =0;i<NUM_OF_EXTRA_CELLS;i++){
            CELL cell= new CELL();
            cell.cellName = CELL.Cell.values()[ThreadLocalRandom.current().nextInt(1,3)];
            System.out.println(cell.cellName);
            cell.setX(ThreadLocalRandom.current().nextInt(0,NUM_CELLS));
            System.out.println(cell.getX());
            cell.setY(ThreadLocalRandom.current().nextInt(0,NUM_CELLS));
            System.out.println(cell.getY());
            if(cell.squareIsOccupiedCELL(this.cells)&&cell.CellINRange()){
                    System.out.println("add new Cell");
                    cells.add(cell);
            }
            else {
                    System.out.println("this place is token");
            }
        }
    }
	/* هاد التابع ما استخدمناه بس بجيب كل الوحدات يلي حوالين الوحدة تبعي*/
    private ArrayList<Unit> GetAllUnitsInRange(Unit unit) {

        ArrayList<Unit> unitesINmyRange = new ArrayList<>();
        ArrayList<Unit> AllUnitsExceptMyUnit = new ArrayList<>();
        for (Unit units:this.allUnits) {
                if(units!=unit){
                        AllUnitsExceptMyUnit.add(units);
                }
        }
        for (Unit allUnit : AllUnitsExceptMyUnit) {
                if(UnitInThisRange(allUnit,unit.getPosition().getCenterX(),unit.getPosition().getCenterY(),unit.getUnitProperties(4).getPropertyValue()))
                        unitesINmyRange.add(allUnit);
        }
        return unitesINmyRange;
    }
	/* هاد تابع بشوف فيه اذا الوحد بهاد المجال يلي بدي ياه او لا */
    private boolean UnitInThisRange(Unit unit, int x, int y, double range){
        return unit.getPosition().getCenterX() + unit.getPosition().getRadius() <= x + range
                        && unit.getPosition().getCenterX() - unit.getPosition().getRadius() >= x - range
                        && unit.getPosition().getCenterY() + unit.getPosition().getRadius() <= y - range
                        && unit.getPosition().getCenterY() - unit.getPosition().getRadius() >= y - range;
    }
	/* تابع ليجبلي احداثيات ال Main Base */
    public  UnitPosition mainBase(){
        UnitPosition unitPosition = null;
        for (int i=0;i<allUnits.size();i++)
        {
            if(allUnits.get(i).getUnitType()==UnitType.MainBase)
            {
                unitPosition = allUnits.get(i).getPosition();
                i=allUnits.size();
            }
        }
        return unitPosition;
    }
	/* تابع بشوف اذا فيني اتحرك او لا يعني بس بشوف اذا المنطقة يلي رايحة عليها فيها وحدة او خلية او خارج المجال تبع لعبتي*/
    boolean AcceptUnitMovement(Unit unit, int x, int y) {
        Unit test = new Unit(this,x,y,unit.getAttackStrategy(),unit.getOwner(),unit.getMovement());
        test.getPosition().setRadius(unit.getPosition().getRadius());
        test.setUnitName(unit.getUnitName());
        test.setUnitType(unit.getUnitType());
        test.setCanAttack(unit.getCanAttack());
        test.setUnitProperties(unit.getUnitProperties());
        ArrayList<Unit> moveUnit = new ArrayList<>();
        for (Unit units:this.allUnits) {
                if(units!=unit){
                        moveUnit.add(units);
                }
        }
        return test.getPosition().squareIsOccupied(moveUnit)
                        &&ThereIsNoCellsInThisIndex(x,y)
                        &&test.getPosition().UnitInRange();
    }
	/* تابع بشوف اذا ما في خلية بالاندكس يلي رايحة عليه*/
    boolean ThereIsNoCellsInThisIndex(int X, int Y){
        boolean x = true;
        for (CELL cell:cells) {
            for (int i = cell.getX()-cell.getRange(); i <= cell.getX()+cell.getRange(); i++) {
                for (int j = cell.getY()-cell.getRange(); j <= cell.getY()+cell.getRange(); j++){
                    if(X==i&&Y==j){
                            x = false;
                    }
                }
            }
        }
        return x;
    }
	/* تابع بردلي الخلية الموجودة باندكس معين */
    CELL cellInThisIndex(int X,int Y){
        CELL myCell = null;
        for (CELL cell:cells) {
            for (int i = cell.getX()-cell.cellName.getRange(); i <= cell.getX()+cell.cellName.getRange(); i++) {
                for (int j = cell.getY()-cell.cellName.getRange(); j <= cell.getY()+cell.cellName.getRange(); j++){
                    if(X==i&&Y==j)
                        myCell = cell;
                }
            }
        }
        return myCell;
    }
	/* تابع بردلي وحدة موجودة باندكس معين */
    Unit unitInThisIndex(int X ,int Y){
        Unit myUnit = null;
        for (Unit unit : allUnits) {
            for (int i = unit.getPosition().getCenterX() - unit.getPosition().getRadius();
                i < unit.getPosition().getCenterX()+ unit.getPosition().getRadius(); i++) {
                for (int j = unit.getPosition().getCenterY() - unit.getPosition().getRadius();
                    j < unit.getPosition().getCenterY() + unit.getPosition().getRadius(); j++){
                    if(X==i&&Y==j)
                        myUnit = unit;
                }
            }
        }
        return myUnit;
    }
	/* تابع بردلي اذا بهاد الاندكس في خلية فشو نمطها*/
    CELL.Cell cellType(int x , int y){
        CELL.Cell cellName = null;
        if(!ThereIsNoCellsInThisIndex(x,y))
                cellName = cellInThisIndex(x,y).cellName;
        return cellName;
    }
    boolean IndexInRange(int X,int Y , int range){
        return X+range <= Grid.NUM_CELLS && X-range >= 0
                        && Y+range <= Grid.NUM_CELLS && Y-range >= 0;
    }
    void  show() {
        String[][] arena = new String[NUM_CELLS][NUM_CELLS];
        for (int i = 0; i < NUM_CELLS; i++) {
            for (int j = 0; j < NUM_CELLS; j++) {
                if (isEmpty(i, j)&&ThereIsNoCellsInThisIndex(i,j))
                    arena[i][j] = "#";
                else if(!isEmpty(i,j)){
                    arena[i][j] = "X";
                }
                else
                    arena[i][j] = "C";
            }

        }
        int uu = 0;
        for (int i = 0; i < NUM_CELLS; i++) {
            System.out.print(uu++);
            for (int j = 0; j < NUM_CELLS; j++) {

                System.out.print(arena[i][j]);
            }
            System.out.println();
        }
    }
    boolean isEmpty(int x ,int y){
        boolean z = true;
        for (Unit u : allUnits) {
            for (int i = u.getPosition().getCenterX()-u.getPosition().getRadius();i<=u.getPosition().getCenterX()+u.getPosition().getRadius();i++)
                for (int j =  u.getPosition().getCenterY()-u.getPosition().getRadius(); j <=u.getPosition().getCenterY()+u.getPosition().getRadius() ; j++) {
                    if(j==x&&i==y)
                        z = false;
                }
        }
        return z;
    }
    void DestroyBridge(int x,int y){
        boolean c = false;
        for (CELL cell:cells) {
            for (int i = cell.getX()-cell.getRange(); i <= cell.getX()+cell.getRange(); i++) {
                for (int j = cell.getY()-cell.getRange(); j <= cell.getY()+cell.getRange(); j++){
                    if(x==i&&y==j){
                        cells.remove(cell);
                        c = true;
                        break;
                    }
                }
            }
            if(c)break;
        }
    }
    
    public class GameOn {
        
        void allUnitsOn(double time) throws InterruptedException
        {
            Timer timer= new Timer(time,LocalTime.now().getSecond());
            timer.start();
            for(int i=1;i<allUnits.size();i++)
            {
                allUnits.get(i).setTime(timer);
                allUnits.get(i).start();
            }
            for(int i=0;i<allUnits.size();i++)
            {
                allUnits.get(i).join();
            }
            if(DoDGameManager.state==GameState.Paused)
            {
                Logger l=Logger.getLogger(Unit.class.getName());
                l.log(Level.INFO,"\n\t\t_______________________Deffende Won_______________________");
            }
            else if(DoDGameManager.state==GameState.AttackerWon)
            {
                Logger l=Logger.getLogger(Unit.class.getName());
                l.log(Level.INFO,"\n\t\t_______________________Attacker Won_______________________");
            }
            else if(DoDGameManager.state==GameState.DeffenderWon)
            {
                Logger l=Logger.getLogger(Unit.class.getName());
                l.log(Level.INFO,"\n\t\t_______________________Deffende Won_______________________");
            }
        }
    }
}

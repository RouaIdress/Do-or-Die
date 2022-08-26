package dod;
import java.util.logging.Logger;

public class MovementPlan extends Movement {
    private int x;
    private int y;
    Logger logger = Logger.getLogger(AttackerMovement.class.getName());

    public void move(Grid grid , Unit unit) {
        if(this.y > unit.getPosition().getCenterY()
                &&this.y - unit.getPosition().getCenterY()>= unit.getUnitProperties()[6].getPropertyValue()
                &&unit.get_prev().getPosition().getCenterY() != unit.getPosition().getCenterY()+ unit.getUnitProperties()[6].getPropertyValue()){
                if (IsCanMoveUp(grid, unit)) {
                    moveUp(unit, 1);
                } else if (IsThereARiverUp(grid, unit)) {
                    logger.info("there is a River Up the unit " + unit.getUnitName());
                    moveUp(unit, 0.5);
                } else if (IsThereAVallyUp(grid, unit)) {
                    logger.info("there is a Vally up the unit " + unit.getUnitName());
                    MoveAroundVally(grid, unit);
                } else if (IsThereUnitUp(grid, unit)) {
                    logger.info("there is a Unit up the unit " + unit.getUnitName());
                    MoveAroundUnit(grid, unit);
                } else if (IsThereABridgeUp(grid, unit)) {
                    logger.info("there is a Bridge up the unit " + unit.getUnitName() + " and it will Destroy it ...");
                    DestroyBridge(grid, unit.getPosition().getCenterX(), unit.getPosition().getCenterY() + (int) unit.getUnitProperties()[6].getPropertyValue());
                }
        }
        else if(this.y < unit.getPosition().getCenterY()
                &&unit.getPosition().getCenterY() - this.y >= unit.getUnitProperties()[6].getPropertyValue()
                && unit.get_prev().getPosition().getCenterY() != unit.getPosition().getCenterY() - unit.getUnitProperties()[6].getPropertyValue()){
                if (IsCanMoveDown(grid, unit)) {
                    moveDown(unit, 1);
                } else if (IsThereARiverDown(grid, unit)) {
                    logger.info("there is a river Down the unit " + unit.getUnitName());
                    moveDown(unit, 0.5);
                } else if (IsThereAVallyDown(grid, unit)) {
                    logger.info("there is a vally Down the unit " + unit.getUnitName());
                    MoveAroundVally(grid, unit);
                } else if (IsThereUnitDown(grid, unit)) {
                    logger.info("there is a unit Down the unit " + unit.getUnitName());
                    MoveAroundUnit(grid, unit);
                } else if (IsThereABridgeDown(grid, unit)) {
                    logger.info("there is a Bridge Down the unit " + unit.getUnitName() + " and it will Destroy it ...");
                    DestroyBridge(grid, unit.getPosition().getCenterX(), unit.getPosition().getCenterY() - (int) unit.getUnitProperties()[6].getPropertyValue());
                }
        }
        else {
            if(this.x >unit.getPosition().getCenterX()
                    &&unit.get_prev().getPosition().getCenterX() != unit.getPosition().getCenterX()+ (int)unit.getUnitProperties()[6].getPropertyValue()){
                if(this.x - unit.getPosition().getCenterX()>= unit.getUnitProperties()[6].getPropertyValue()) {
                    if (IsCanMoveRight(grid, unit)) {
                        moveRight(unit, 1);
                    } else if (IsThereARiverRight(grid, unit)) {
                        logger.info("there is a River Right the unit " + unit.getUnitName());
                        moveRight(unit, 0.5);
                    } else if (IsThereAVallyRight(grid, unit)) {
                        logger.info("there is a Vally Right the unit " + unit.getUnitName());
                        MoveAroundVally(grid, unit);
                    } else if (IsThereUnitRight(grid, unit)) {
                        logger.info("there is a Unit Right the unit " + unit.getUnitName());
                        MoveAroundUnit(grid, unit);
                    } else if (IsThereABridgeRight(grid, unit)) {
                        logger.info("there is a Bridge Right the unit " + unit.getUnitName() + " and it will Destroy it ...");
                        DestroyBridge(grid, unit.getPosition().getCenterX() + (int) unit.getUnitProperties()[6].getPropertyValue(), unit.getPosition().getCenterY());
                    }
                }
                else {
                    if(grid.AcceptUnitMovement(unit,this.x,this.y)){
                        moveThere(unit);
                    }
                }
            }
            else if(this.x < unit.getPosition().getCenterX()
                    &&unit.get_prev().getPosition().getCenterX() != unit.getPosition().getCenterX()- (int)unit.getUnitProperties()[6].getPropertyValue()){
                if( unit.getPosition().getCenterX() - this.x >= unit.getUnitProperties()[6].getPropertyValue()) {
                    if (IsCanMoveLeft(grid, unit)) {
                        moveLeft(unit, 1);
                    } else if (IsThereARiverLeft(grid, unit)) {
                        logger.info("there is a River Left the unit " + unit.getUnitName());
                        moveLeft(unit, 0.5);
                    } else if (IsThereAVallyLeft(grid, unit)) {
                        logger.info("there is a vally left the unit " + unit.getUnitName());
                        MoveAroundVally(grid, unit);
                    } else if (IsThereUnitLeft(grid, unit)) {
                        logger.info("there is a Unit left the unit " + unit.getUnitName());
                        MoveAroundUnit(grid, unit);
                    } else if (IsThereABridgeLeft(grid, unit)) {
                        logger.info("there is a Bridge left the unit " + unit.getUnitName() + " and it will Destroy it ...");
                        DestroyBridge(grid, unit.getPosition().getCenterX() - (int) unit.getUnitProperties()[6].getPropertyValue(), unit.getPosition().getCenterY());
                    }
                }
                else {
                    if(grid.AcceptUnitMovement(unit,this.x,this.y)){
                        moveThere(unit);
                    }
                }
            }
        }
    }
    void moveUp(Unit unit , double changeSpeed){
        Unit unit1 = new Unit(unit.getGrid(),unit.getPosition().getCenterX(),
                unit.getPosition().getCenterY(),null,null,null);
        unit.set_prev(unit1);
        int unitY = unit.getPosition().getCenterY();
        unit.getPosition().setCenterY(unitY +(int)(unit.getUnitProperties()[6].getPropertyValue()*changeSpeed));
        unit.set_next(unit);

        logger.info(unit.getUnitName()+" that owned by "+unit.getOwner()+" move up" +
                " and it's new position is ("+unit.getPosition().getCenterX()+","+unit.getPosition().getCenterY()+")");
    }
    void moveDown(Unit unit , double changeSpeed){
        Unit unit1 = new Unit(unit.getGrid(),unit.getPosition().getCenterX(),
                unit.getPosition().getCenterY(),null,null,null);
        unit.set_prev(unit1);
        int unitY = unit.getPosition().getCenterY();
        unit.getPosition().setCenterY(unitY-(int)(unit.getUnitProperties()[6].getPropertyValue()*changeSpeed));
        unit.set_next(unit);
        logger.info(unit.getUnitName()+" that owned by "+unit.getOwner()+" move down" +
                " and it's new position is ("+unit.getPosition().getCenterX()+","+unit.getPosition().getCenterY()+")");
    }
    void moveRight(Unit unit , double changeSpeed){
        Unit unit1 = new Unit(unit.getGrid(),unit.getPosition().getCenterX(),
                unit.getPosition().getCenterY(),null,null,null);
        unit.set_prev(unit1);
        unit.getPosition().setCenterX(unit.getPosition().getCenterX()+(int)(unit.getUnitProperties()[6].getPropertyValue()*changeSpeed));
        unit.set_next(unit);

        logger.info(unit.getUnitName()+" that owned by "+unit.getOwner()+" move Right" +
                " and it's new position is ("+unit.getPosition().getCenterX()+","+unit.getPosition().getCenterY()+")");
    }
    void moveLeft(Unit unit , double changeSpeed){
        Unit unit1 = new Unit(unit.getGrid(),unit.getPosition().getCenterX(),
                unit.getPosition().getCenterY(),null,null,null);
        unit.set_prev(unit1);
        unit.getPosition().setCenterX(unit.getPosition().getCenterX()-(int)(unit.getUnitProperties()[6].getPropertyValue()*changeSpeed));
        unit.set_next(unit);
        logger.info(unit.getUnitName()+" that owned by "+unit.getOwner()+" move left" +
                " and it's new position is ("+unit.getPosition().getCenterX()+","+unit.getPosition().getCenterY()+")");
    }
    void  moveThere(Unit unit){
        Unit unit1 = new Unit(unit.getGrid(),unit.getPosition().getCenterX(),
                unit.getPosition().getCenterY(),null,null,null);
        unit.set_prev(unit1);
        int unitY = this.y ;
        int unitX = this.x;
        unit.getPosition().setCenterY(unitY);
        unit.getPosition().setCenterX(unitX);
        logger.info(unit.getName()+" that owned by "+unit.getOwner()+" reach to it's destination " +
                " and it's new position is ("+unit.getPosition().getCenterX()+","+unit.getPosition().getCenterY()+")");
    }

    boolean IsCanMoveRight(Grid grid , Unit unit){
        return  grid.AcceptUnitMovement(unit, unit.getPosition().getCenterX() +(int)unit.getUnitProperties()[6].getPropertyValue(),
                unit.getPosition().getCenterY())
                &&unit.get_prev().getPosition().getCenterX() != unit.getPosition().getCenterX()+ (int)unit.getUnitProperties()[6].getPropertyValue();
    }
    boolean IsCanMoveLeft(Grid grid , Unit unit){
        return  grid.AcceptUnitMovement(unit, unit.getPosition().getCenterX() - (int) unit.getUnitProperties()[6].getPropertyValue(),
                unit.getPosition().getCenterY())
                &&unit.get_prev().getPosition().getCenterX() != unit.getPosition().getCenterX()- unit.getUnitProperties()[6].getPropertyValue();
    }
    boolean IsCanMoveUp(Grid grid , Unit unit){
        System.out.println("is can move Up");
        return  grid.AcceptUnitMovement(unit, unit.getPosition().getCenterX() ,
                unit.getPosition().getCenterY() + (int) unit.getUnitProperties()[6].getPropertyValue())
                &&unit.get_prev().getPosition().getCenterY() != unit.getPosition().getCenterY()+ unit.getUnitProperties()[6].getPropertyValue();
    }
    boolean IsCanMoveDown(Grid grid , Unit unit) {
        return grid.AcceptUnitMovement(unit, unit.getPosition().getCenterX(),
                unit.getPosition().getCenterY() - (int) unit.getUnitProperties()[6].getPropertyValue())
                && unit.get_prev().getPosition().getCenterY() != unit.getPosition().getCenterY() - unit.getUnitProperties()[6].getPropertyValue();
    }

    boolean IsThereUnitLeft(Grid grid , Unit unit) {
        return !IsCanMoveLeft(grid,unit)
                &&grid.IndexInRange(unit.getPosition().getCenterX()-(int)unit.getUnitProperties()[6].getPropertyValue()
                ,unit.getPosition().getCenterY(),unit.getPosition().getRadius())
                &&grid.ThereIsNoCellsInThisIndex(
                unit.getPosition().getCenterX()-(int)unit.getUnitProperties()[6].getPropertyValue(),
                unit.getPosition().getCenterY());
    }
    boolean IsThereUnitRight(Grid grid , Unit unit) {
        return !IsCanMoveRight(grid, unit)
                &&grid.IndexInRange(unit.getPosition().getCenterX()+(int)unit.getUnitProperties()[6].getPropertyValue()
                ,unit.getPosition().getCenterY(),unit.getPosition().getRadius())
                &&grid.ThereIsNoCellsInThisIndex(
                unit.getPosition().getCenterX()+(int)unit.getUnitProperties()[6].getPropertyValue(),
                unit.getPosition().getCenterY());
    }
    boolean IsThereUnitDown(Grid grid , Unit unit) {
        return !IsCanMoveDown(grid,unit)
                &&grid.IndexInRange(unit.getPosition().getCenterX()
                ,unit.getPosition().getCenterY()-(int)unit.getUnitProperties()[6].getPropertyValue(),unit.getPosition().getRadius())
                &&grid.ThereIsNoCellsInThisIndex(unit.getPosition().getCenterX(),
                unit.getPosition().getCenterY()-(int)unit.getUnitProperties()[6].getPropertyValue());
    }
    boolean IsThereUnitUp(Grid grid , Unit unit) {
        return !IsCanMoveUp(grid,unit)
                &&grid.IndexInRange(unit.getPosition().getCenterX()
                ,unit.getPosition().getCenterY()+(int)unit.getUnitProperties()[6].getPropertyValue(),unit.getPosition().getRadius())
                &&grid.ThereIsNoCellsInThisIndex(unit.getPosition().getCenterX(),
                unit.getPosition().getCenterY()+(int)unit.getUnitProperties()[6].getPropertyValue());
    }

    boolean IsThereARiverLeft(Grid grid , Unit unit){
        return grid.cellType(unit.getPosition().getCenterX() -
                (int) unit.getUnitProperties()[6].getPropertyValue(), unit.getPosition().getCenterY()) == CELL.Cell.River;
    }
    boolean IsThereARiverRight(Grid grid , Unit unit){
        return grid.cellType(unit.getPosition().getCenterX() +
                (int) unit.getUnitProperties()[6].getPropertyValue(), unit.getPosition().getCenterY()) == CELL.Cell.River;
    }
    boolean IsThereARiverDown(Grid grid , Unit unit){
        return grid.cellType(unit.getPosition().getCenterX(), unit.getPosition().getCenterY() -
                (int) unit.getUnitProperties()[6].getPropertyValue())
                == CELL.Cell.River;
    }
    boolean IsThereARiverUp(Grid grid , Unit unit){
        return grid.cellType(unit.getPosition().getCenterX(), unit.getPosition().getCenterY() +
                (int) unit.getUnitProperties()[6].getPropertyValue())
                == CELL.Cell.River;
    }
    //
    boolean IsThereAVallyLeft(Grid grid,Unit unit){
        return grid.cellType(unit.getPosition().getCenterX() -
                (int) unit.getUnitProperties()[6].getPropertyValue(), unit.getPosition().getCenterY()) == CELL.Cell.Valley;
    }
    boolean IsThereAVallyRight(Grid grid,Unit unit){
        return grid.cellType(unit.getPosition().getCenterX() +
                (int) unit.getUnitProperties()[6].getPropertyValue(), unit.getPosition().getCenterY()) == CELL.Cell.Valley;
    }
    boolean IsThereAVallyDown(Grid grid,Unit unit){
        return grid.cellType(unit.getPosition().getCenterX(), unit.getPosition().getCenterY() -
                (int) unit.getUnitProperties()[6].getPropertyValue())
                == CELL.Cell.Valley;
    }
    boolean IsThereAVallyUp(Grid grid,Unit unit){
        return grid.cellType(unit.getPosition().getCenterX(), unit.getPosition().getCenterY() +
                (int) unit.getUnitProperties()[6].getPropertyValue())
                == CELL.Cell.Valley;
    }

    boolean IsThereABridgeLeft(Grid grid,Unit unit){
        return grid.cellType(unit.getPosition().getCenterX() -
                (int) unit.getUnitProperties()[6].getPropertyValue(), unit.getPosition().getCenterY()) == CELL.Cell.Bridge;
    }
    boolean IsThereABridgeRight(Grid grid,Unit unit){
        return grid.cellType(unit.getPosition().getCenterX() +
                (int) unit.getUnitProperties()[6].getPropertyValue(), unit.getPosition().getCenterY()) == CELL.Cell.Bridge;
    }
    boolean IsThereABridgeDown(Grid grid,Unit unit){
        return grid.cellType(unit.getPosition().getCenterX(), unit.getPosition().getCenterY() -
                (int) unit.getUnitProperties()[6].getPropertyValue())
                == CELL.Cell.Bridge;
    }
    boolean IsThereABridgeUp(Grid grid,Unit unit){
        return grid.cellType(unit.getPosition().getCenterX(), unit.getPosition().getCenterY() +
                (int) unit.getUnitProperties()[6].getPropertyValue())
                == CELL.Cell.Bridge;
    }

    void MoveRightOrLeft(Grid grid,Unit unit){
        if(this.x >unit.getPosition().getCenterX()){
            if(IsCanMoveRight(grid,unit)){
                moveRight(unit,1);
            }
            else if(IsThereARiverRight(grid, unit))
                moveRight(unit,0.5);
            else if(!IsCanMoveRight(grid, unit)&&!IsThereARiverRight(grid, unit)){
                if(IsCanMoveLeft(grid,unit)){
                    moveLeft(unit,1);
                }
                else if(IsThereARiverLeft(grid,unit)){
                    moveLeft(unit,0.5);
                }
            }
        }
        else if(this.x <unit.getPosition().getCenterX()){
            if(IsCanMoveLeft(grid,unit)){
                moveLeft(unit,1);
            }
            else if(IsThereARiverLeft(grid, unit))
                moveLeft(unit,0.5);
            else if(!IsCanMoveLeft(grid, unit)&&!IsThereARiverLeft(grid, unit)){
                if(IsCanMoveRight(grid,unit)){
                    moveRight(unit,1);
                }
                else if(IsThereARiverRight(grid,unit)){
                    moveRight(unit,0.5);
                }
            }

        }
    }
    void MoveUpOrDown(Grid grid,Unit unit){
        if(this.y>unit.getPosition().getCenterY()){
            if(IsCanMoveUp(grid,unit)){
                moveUp(unit,1);
            }
            else if(IsThereARiverUp(grid, unit))
                moveUp(unit,0.5);
            else if(!IsCanMoveUp(grid, unit)&&!IsThereARiverUp(grid, unit)){
                if(IsCanMoveDown(grid,unit)){
                    moveDown(unit,1);
                }
                else if(IsThereARiverDown(grid,unit)){
                    moveDown(unit,0.5);
                }
            }
        }
        else if(this.y <unit.getPosition().getCenterY()){
            if(IsCanMoveDown(grid,unit)){
                moveDown(unit,1);
            }
            else if(IsThereARiverDown(grid, unit))
                moveDown(unit,0.5);
            else if(!IsCanMoveDown(grid, unit)&&!IsThereARiverDown(grid, unit)){
                if(IsCanMoveUp(grid,unit)){
                    moveUp(unit,1);
                }
                else if(IsThereARiverUp(grid,unit)){
                    moveUp(unit,0.5);
                }
            }

        }
    }
    void MoveAroundVally(Grid grid,Unit unit) {
        logger.info("the unit "+unit.getUnitName()+" will try to move around it so ...");
        if (this.y > unit.getPosition().getCenterY()+unit.getPosition().getRadius()) {
            if (IsThereAVallyUp(grid, unit)) {
                MoveRightOrLeft(grid, unit);
            }
        }
        else if (this.y  < unit.getPosition().getCenterY()-unit.getPosition().getRadius()) {
            if (IsThereAVallyDown(grid, unit)) {
                MoveRightOrLeft(grid,unit);
            }
        }
        else if (this.x  > unit.getPosition().getCenterX()+unit.getPosition().getRadius()){
            if(IsThereARiverRight(grid,unit)){
                MoveUpOrDown(grid,unit);
            }
        }
        else if (this.x  < unit.getPosition().getCenterX()-unit.getPosition().getRadius()){
            logger.info("move up or down");
            MoveUpOrDown(grid,unit);
        }
    }
    void MoveAroundUnit(Grid grid, Unit unit) {
        logger.info(" the unit "+unit.getUnitName()+" will try to move around it so ...");
        if (this.y > unit.getPosition().getCenterY()+unit.getPosition().getRadius()) {
            if (IsThereUnitUp(grid, unit)) {
                MoveRightOrLeft(grid, unit);
            }
        }
        else if (this.y < unit.getPosition().getCenterY()-unit.getPosition().getRadius()) {
            if (IsThereUnitDown(grid, unit)) {
                MoveRightOrLeft(grid,unit);
            }
        }
        else if (this.x > unit.getPosition().getCenterX()+unit.getPosition().getRadius()){
            if(IsThereUnitRight(grid,unit)){
                MoveUpOrDown(grid,unit);
            }
        }
        else if (this.x < unit.getPosition().getCenterX()-unit.getPosition().getRadius()){
            if(IsThereUnitLeft(grid,unit)){
                MoveUpOrDown(grid,unit);
            }
        }
    }
    void DestroyBridge(Grid grid,int x ,int y){
        logger.info("the Bridge is destroyed");
        grid.DestroyBridge(x,y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


//		boolean IsCanMoveInEmptyPlace(Grid grid,Unit unit){
//			return IsCanMoveUp(grid, unit)
//					|| IsCanMoveDown(grid, unit)
//					|| IsCanMoveLeft(grid, unit)
//					|| IsCanMoveRight(grid, unit);
//
//		}
//		boolean IsCanMoveInRiver(Grid grid,Unit unit){
//			return IsThereARiverUp(grid, unit)
//					|| IsThereARiverDown(grid, unit)
//					|| IsThereARiverLeft(grid, unit)
//					|| IsThereARiverRight(grid, unit);
//		}
//		boolean IsCanMove(Grid grid,Unit unit){
//			return IsCanMoveInEmptyPlace(grid,unit)&&IsCanMoveInRiver(grid,unit);
//		}
}




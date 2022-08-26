/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bcc
 */
public class ConsolePlayer extends Player {

    private Logger logger;
    
    ConsolePlayer(int teamid)
    {
        super(new ArrayList<>());
        logger=Logger.getLogger(ConsolePlayer.class.getName());
        teamId=teamid;
        coins=3000;
    }
    
    @Override
    public UnitPosition NewGame(Grid grid) {
        UnitPosition p=null;
        UnitName u=null;
        System.out.println("Money: "+coins);
        if(teamId==0)
        {
            System.out.println("Deffender Team");
        }
        else if(teamId==1)
        {
            System.out.println("Attacker Team");
        }
        for(UnitName unit:  UnitName.values())
        {
            if(((teamId==0 && unit.getUnitType()!= UnitType.Airplane)||(teamId==1 && unit.getUnitType()!=UnitType.Structure)) && unit.getUnitType()!= UnitType.MainBase)
            {
                System.out.println("ID: "+unit.ordinal());
                unit.setUnit(unit.ordinal());
                System.out.println(unit+"\n____________________________");
            }
        }
        int choice;
        do{
            System.out.println("enter id of the unit you want buy");
            Scanner s= new Scanner(System.in);
            choice=s.nextInt();
        }while(choice<0||choice>13||(teamId==0 && UnitName.values()[choice].getUnitType()== UnitType.Airplane)||(teamId==1 && UnitName.values()[choice].getUnitType()==UnitType.Structure) || UnitName.values()[choice].getUnitType()== UnitType.MainBase);
        
        if(coins-UnitName.values()[choice].getUnitPrice()>=0)//to make sure can buy it
        {
            this.setUnit(choice);
            this.buyUnit();
            p=zoom(0,0,1,choice,grid);
            System.out.println("Enter One to Creat you're own Plan or else to set this unit to default");
            Scanner s= new Scanner(System.in);
            choice=s.nextInt();
            while(choice==1)
            {
                System.out.println("Choose Order: \n1.Move to Squar X,Y\n2.Wait in Position N Seconds");
                s= new Scanner(System.in);
                choice=s.nextInt();
                if(choice==1)
                {
                    Order o=new Order(choice);
                    System.out.println("Enter X:");
                    s= new Scanner(System.in);
                    int x=s.nextInt();
                    System.out.println("Enter Y:");
                    s= new Scanner(System.in);
                    int y=s.nextInt();
                    o.setX(x);
                    o.setY(y);
                    this.addtoPlan(o);
                }
                else if(choice==2)
                {
                    Order o=new Order(choice);
                    System.out.println("Enter N:");
                    s= new Scanner(System.in);
                    int n=s.nextInt();
                    o.setSecondToWait(n);
                    this.addtoPlan(o);
                }
                System.out.println("Enter One to Keep Planning or else to exit");
                s= new Scanner(System.in);
                choice=s.nextInt();
            }
        }
        else
        {
            logger.log(Level.INFO, "can't buy this unit");
        }
        return p;
    }
    
    @Override
    public void LoadGame() {

    }
    
    UnitPosition zoom(int x,int y, int counter,int index,Grid grid)
    {
        int check=0;
        int curr=0;
        int prevx=x;
        int prevy=y;
        if(counter==1)
        {
            curr=100000;
        }
        else if(counter==2)
        {
            curr=10000;
        }
        else if(counter==3)
        {
            curr=1000;
        }
        else if(counter==4)
        {
            curr=100;
        }
        else if(counter==5)
        {
            curr=10;
        }
        else
        {
            curr=1;
        }
        System.out.println("x"+x);
        System.out.println("y"+y);
        
        for(int j=0;j<10;j++)
        {
            System.out.print("\t"+j*curr);
        }
        System.out.println();
        for(int i=0;i<10;i++)
        {
            System.out.print(i*curr+"\t");
            for(int j=0;j<10;j++)
            {
                for(int k=0;k<grid.getAllUnits().size();k++)
                {
                    check=0;
                    if(counter<5 && grid.getAllUnits().get(k).getPosition().getCenterX()>=((i*curr)+prevx) && grid.getAllUnits().get(k).getPosition().getCenterX()<((i*curr)+curr+prevx) && grid.getAllUnits().get(k).getPosition().getCenterY()>=((j*curr)+prevy) && grid.getAllUnits().get(k).getPosition().getCenterY()<((j*curr)+curr+prevy))
                    {
                        if(k==0)
                        {
                            System.out.print('B'+"\t");
                        }
                        else
                        {
                            System.out.print(k+"\t");
                        }
                        k=grid.getAllUnits().size();
                        check=1;                      
                    }
                    else if(counter==5)
                    {
                        if(grid.getAllUnits().get(k).getPosition().getCenterX()>=prevx && grid.getAllUnits().get(k).getPosition().getCenterX()<(100+prevx) && ((prevx+(i*curr))<(prevx+100)) && ((((i*curr)+prevx<=grid.getAllUnits().get(k).getPosition().getCenterX()) && (prevx+(i*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterX()-grid.getAllUnits().get(k).getPosition().getRadius())) || (((i*curr)+prevx>=grid.getAllUnits().get(k).getPosition().getCenterX()) && (prevx+(i*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterX()+grid.getAllUnits().get(k).getPosition().getRadius()))))
                        {
                            if(grid.getAllUnits().get(k).getPosition().getCenterY()>=prevy && grid.getAllUnits().get(k).getPosition().getCenterY()<(100+prevy) && (prevy+(j*curr))<(100+prevy) && ((((j*curr)+prevy<=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius())) || (((j*curr)+prevy>=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()))))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()>=(100+prevy) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(100+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()<prevy && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(100+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                        }
                        else if(grid.getAllUnits().get(k).getPosition().getCenterX()>=(100+prevx)&& (prevx+(i*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterX()-grid.getAllUnits().get(k).getPosition().getRadius()) && (prevx+(i*curr))<(prevx+100))
                        {
                            if(grid.getAllUnits().get(k).getPosition().getCenterY()>=prevy && grid.getAllUnits().get(k).getPosition().getCenterY()<(100+prevy) && (prevy+(j*curr))<(100+prevy) && ((((j*curr)+prevy<=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius())) || (((j*curr)+prevy>=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()))))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()>=(100+prevy) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(100+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()<prevy && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(100+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                        }
                        else if(grid.getAllUnits().get(k).getPosition().getCenterX()<prevx && (prevx+(i*curr))<(prevx+100)&& (prevx+(i*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterX()+grid.getAllUnits().get(k).getPosition().getRadius()))
                        {
                            if(grid.getAllUnits().get(k).getPosition().getCenterY()>=prevy && grid.getAllUnits().get(k).getPosition().getCenterY()<(100+prevy) && (prevy+(j*curr))<(100+prevy) && ((((j*curr)+prevy<=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius())) || (((j*curr)+prevy>=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()))))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()>=(100+prevy) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(100+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()<prevy && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(100+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                        }
                    }
                    else if(counter==6)
                    {
                        if(grid.getAllUnits().get(k).getPosition().getCenterX()>=prevx && grid.getAllUnits().get(k).getPosition().getCenterX()<(10+prevx) && ((prevx+(i*curr))<(prevx+10)) && ((((i*curr)+prevx<=grid.getAllUnits().get(k).getPosition().getCenterX()) && (prevx+(i*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterX()-grid.getAllUnits().get(k).getPosition().getRadius())) || (((i*curr)+prevx>=grid.getAllUnits().get(k).getPosition().getCenterX()) && (prevx+(i*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterX()+grid.getAllUnits().get(k).getPosition().getRadius()))))
                        {
                            if(grid.getAllUnits().get(k).getPosition().getCenterY()>=prevy && grid.getAllUnits().get(k).getPosition().getCenterY()<(10+prevy) && (prevy+(j*curr))<(10+prevy) && ((((j*curr)+prevy<=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius())) || (((j*curr)+prevy>=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()))))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()>=(10+prevy) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(10+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()<prevy && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(10+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                        }
                        else if(grid.getAllUnits().get(k).getPosition().getCenterX()>=(10+prevx)&& (prevx+(i*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterX()-grid.getAllUnits().get(k).getPosition().getRadius()) && (prevx+(i*curr))<(prevx+10))
                        {
                            if(grid.getAllUnits().get(k).getPosition().getCenterY()>=prevy && grid.getAllUnits().get(k).getPosition().getCenterY()<(10+prevy) && (prevy+(j*curr))<(10+prevy) && ((((j*curr)+prevy<=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius())) || (((j*curr)+prevy>=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()))))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()>=(10+prevy) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(10+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()<prevy && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(10+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                        }
                        else if(grid.getAllUnits().get(k).getPosition().getCenterX()<prevx && (prevx+(i*curr))<(prevx+10)&& (prevx+(i*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterX()+grid.getAllUnits().get(k).getPosition().getRadius()))
                        {
                            if(grid.getAllUnits().get(k).getPosition().getCenterY()>=prevy && grid.getAllUnits().get(k).getPosition().getCenterY()<(10+prevy) && (prevy+(j*curr))<(10+prevy) && ((((j*curr)+prevy<=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius())) || (((j*curr)+prevy>=grid.getAllUnits().get(k).getPosition().getCenterY()) && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()))))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()>=(10+prevy) && (prevy+(j*curr))>=(grid.getAllUnits().get(k).getPosition().getCenterY()-grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(10+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                            else if(grid.getAllUnits().get(k).getPosition().getCenterY()<prevy && (prevy+(j*curr))<=(grid.getAllUnits().get(k).getPosition().getCenterY()+grid.getAllUnits().get(k).getPosition().getRadius()) && (prevy+(j*curr))<(10+prevy))
                            {
                                if(k==0)
                                {
                                    System.out.print('B'+"\t");
                                }
                                else
                                {
                                    System.out.print(k+"\t");
                                } 
                                check=1;
                                k--;
                                j++;
                            }
                        }
                    }
                    if(j==10)
                    {
                        check=1;
                    }
                }
                if(check==0)
                {
                    System.out.print('*'+"\t");
                }
            }
            System.out.println();
        }
        if(counter==6)
        {
            UnitPosition p;
            do{
                System.out.println("enter one if you want to Zoom out");
                Scanner s= new Scanner(System.in);
                int choice=s.nextInt();
                if(choice==1)
                {
                    return null;
                }
                do{
                    System.out.println("enter position you want to Occupy\nx: ");
                    s= new Scanner(System.in);
                    x=s.nextInt();
                    System.out.println("\ny: ");
                    s= new Scanner(System.in);
                    y=s.nextInt();
                }while(x<0 || x>9 ||y<0|| y>9);
                p=new UnitPosition((x*curr)+prevx,(y*curr)+prevy,UnitName.values()[index].getRadius());
            }while(!p.squareIsOccupied(grid.getAllUnits()) ||  !p.UnitInRange());
            return p;
        }
        if(counter==5)
        {
            System.out.println("enter one if you want to Zoom out");
            Scanner s= new Scanner(System.in);
            int choice=s.nextInt();
            if(choice==1)
            {
                return null;
            }
        }
        do{
            System.out.println("enter position you want to zoom in\nx: ");
            Scanner s= new Scanner(System.in);
            x=s.nextInt();//less than 9
            System.out.println("\ny: ");
            s= new Scanner(System.in);
            y=s.nextInt();//less thsn 9
        }while(x<0 || x>9 ||y<0|| y>9);
        counter++;
        UnitPosition p=zoom(((x*curr)+prevx),((y*curr)+prevy),counter,index,grid);
        if(p==null && counter==6)
        {
            counter--;
            return zoom(prevx,prevy,counter,index,grid);
        }
        else if(p==null && counter==5)
        {
            counter--;
            return zoom(prevx,prevy,counter,index,grid);
        }
        return p;
    }
  
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Bcc
 */
public class DoDGameManager extends GameManager implements UnitDestroyObserver{

        static GameState state;
	private Grid grid;
	private Unit mainBase;
	static private int remainingAttackerUnits;
	private double remainingTime;
	private Team[] teams;
	private UnitFactory unitFactory;
        Timer timer;
        Logger logger;
        Button bquit;
        Button bnew;

        DoDGameManager(Stage stage,int type,int gameTime)
        {
            System.out.println(type);
            remainingAttackerUnits=0;
            logger= Logger.getLogger(DoDGameManager.class.getName());
            unitFactory= new UnitFactory();
            grid= new Grid();
            teams= new Team[2];
            Random random=new Random();
            int x,y;
            do{
                x= random.nextInt(1000000);
                y= random.nextInt(1000000);
            }while(!new UnitPosition(x,y,UnitName.MainBase.getRadius()).UnitInRange());
            mainBase= new Unit(grid,x,y,null,null,new DefenderMovement());
            grid.addUnit(mainBase);
            mainBase.setPosition(new UnitPosition(x,y,UnitName.MainBase.getRadius()));
            mainBase.setUnitType(UnitType.MainBase);
            
            int index=0;
            while(UnitName.values()[index].getUnitType()!=UnitType.MainBase)
            {
                index++;
            }
            mainBase.setUnitProperties(unitFactory.setProperties(index));
            mainBase.setUnitName(UnitName.values()[index].name());
            teams[0]= new DeffenderTeam();
            teams[1]= new AttackerTeam();

            if(type==0)
            {
                for(int i=0;i<1;i++)
                {
                    int choice=0;
                    Player player= new ConsolePlayer(0);
                    teams[0].addPlayer(player);
                    do{
                        UnitPosition position=player.NewGame(grid);
                        if(position!=null)
                        {
                            BuyUnit(player,position.getCenterX(),position.getCenterY(),player.getUnit());
                        }
                        logger.log(Level.INFO, "Enter one to continue buying, or else to exit");
                        Scanner s= new Scanner(System.in);
                        choice=s.nextInt();
                    }while(choice==1);
                }
                for(int i=0;i<1;i++)
                {
                    int choice=0;
                    Player player= new ConsolePlayer(1);
                    teams[1].addPlayer(player);
                    do{
                        UnitPosition position=player.NewGame(grid);
                        if(position!=null)
                        {
                            remainingAttackerUnits++;
                            BuyUnit(player,position.getCenterX(),position.getCenterY(),player.getUnit());
                        }
                        logger.log(Level.INFO, "Enter one to continue buying, or else to exit");
                        Scanner s= new Scanner(System.in);
                        choice=s.nextInt();
                    }while(choice==1);
                }  
                
            }
            else if(type==1)//GUI
            {
                stage.setScene(New());
                bnew.setOnAction((ActionEvent e) -> {
                stage.setScene(gridScene());
                    
                for(int i=0;i<1;i++)
                {
                    int choice=0;
                    Player player= new GUIPlayer(stage,0);
                    teams[0].addPlayer(player);
                    do{
                        UnitPosition position=player.NewGame(grid);
                        if(position!=null)
                        {
                            BuyUnit(player,position.getCenterX(),position.getCenterY(),player.getUnit());
                        }
                        
                    }while(choice==1);
                }
                for(int i=0;i<1;i++)
                {
                    int choice=0;
                    Player player= new GUIPlayer(stage,1);
                    teams[1].addPlayer(player);
                    do{
                        UnitPosition position=player.NewGame(grid);
                        if(position!=null)
                        {
                            remainingAttackerUnits++;
                            BuyUnit(player,position.getCenterX(),position.getCenterY(),player.getUnit());
                        }
                        logger.log(Level.INFO, "Enter one to continue buying, or else to exit");
                        Scanner s= new Scanner(System.in);
                        choice=s.nextInt();
                    }while(choice==1);
                }  
                }); 
                
                bquit.setOnAction((ActionEvent e) -> {
                    stage.close();
                });
            }
            remainingTime=gameTime;
            state=GameState.Running;
            //choose consol or gui

            Grid.GameOn game= grid.new GameOn();
            if(LocalTime.now().getMinute()+remainingTime>59)
            {
                remainingTime=(LocalTime.now().getMinute()+remainingTime)-59;
                try {
                    logger.log(Level.INFO,"remainingTime "+remainingTime);
                    game.allUnitsOn(remainingTime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DoDGameManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    logger.log(Level.INFO,"remainingTime "+LocalTime.now().getMinute()+remainingTime);
                    game.allUnitsOn(LocalTime.now().getMinute()+remainingTime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DoDGameManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        }
        
        static synchronized void setAttackerUnitsCounter()
        {
            remainingAttackerUnits--;
            if(remainingAttackerUnits==0)
            {
                state=GameState.DeffenderWon;
            }
        }
        
	public void OnUnitDestroy(Unit destroyedUnit) {//notify
                            
	}

	public void BuyUnit(Player player, int x, int y,int index) {
            grid.addUnit(unitFactory.CreateUnit(grid,x,y,player,index));
	}
        
        Scene New()
        {
            Pane root = new Pane();
            Image image = new Image("/image/warrr1.jpg"); 
            BackgroundImage backgroundimage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false)); 
            Background background = new Background(backgroundimage); 
            bnew=new Button("New Game");
            bnew.setStyle("-fx-background-color: transparent;");
            Font font = new Font(50); 
            bnew.setFont(font);
            
            bquit = new Button("Quit");
            bquit.setStyle("-fx-background-color: transparent;");
            bquit.setFont(font);

            bnew.setTranslateX(150);
            bnew.setTranslateY(700);
            bquit.setTranslateX(150);
            bquit.setTranslateY(800);
            root.getChildren().add(bnew);
            root.getChildren().add(bquit);
            root.setBackground(background);


//            bnew.setOnAction((ActionEvent e) -> {
////                s.setScene(Arena());
//                s.show();
//
//            }); 
//            bquit.setOnAction((ActionEvent e) -> {
//
//               s.close();
//
//            }); 
            Scene scenenew = new Scene(root, 2000, 1000);
            return scenenew;
    }
    Scene gridScene()
    {
        GridPane gridpane = new GridPane();
        ScrollPane root = new ScrollPane();
        final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
        for (int i = 0 ; i <100; i++)
         {
            for(int j=0 ; j<100 ; j++)
            {
              ImageView im  = Createimage(i,j);  
              GridPane.setHgrow(im,Priority.ALWAYS);  
              im.setOnMouseClicked(event1 ->{
              im.setImage(new Image(getClass().getResourceAsStream( "/image/rock.jpg")));

               }
             );
            zoomProperty.addListener((Observable arg0) -> {
            im.setFitWidth(zoomProperty.get() * 4);
            im.setFitHeight(zoomProperty.get() * 3);
            });
            gridpane.setMaxSize(i,j);
            gridpane.setGridLinesVisible(true);
            gridpane.add(im, i, j);
                 }   
                 }
            root.setContent(gridpane); 
            root.addEventFilter(ScrollEvent.ANY, (ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                 zoomProperty.set(zoomProperty.get() * 1.1);
             } else if (event.getDeltaY() < 0) {
                 zoomProperty.set(zoomProperty.get() / 1.1);
             }
            });
            Scene scenearena = new Scene(root, 1500, 700);
            return scenearena;
        }
    
        ImageView Createimage(int i,int j)
        { 
            CELL cell= new CELL();
            ImageView im =null;
            for(int k=0;k<grid.getCells().size();k++)
            {
                if(grid.getCells().get(k).cellName==cell.cellName.River)
                {
                    im = new ImageView("image/water1.jpg");
                }
                else if(grid.getCells().get(k).cellName==cell.cellName.Bridge)
                {
                    im = new ImageView("image/bridg.jpg");
                }
                else if(grid.getCells().get(k).cellName==cell.cellName.Valley)
                {
                    im = new ImageView("image/rock.jpg");
                }
                else
                {
                    im = new ImageView("image/herb1.jpg");
                }
            }
            im.setFitHeight(50);
            im.setFitWidth(50);
            return im;
        }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Bcc
 */
public class GUIPlayer extends Player {

    private Logger logger;
    Stage stage;
    GUIPlayer(Stage stage,int teamid)
    {
        super(new ArrayList<>());
        logger=Logger.getLogger(ConsolePlayer.class.getName());
        this.stage=stage;
        teamId=teamid;
        coins=3000;
    }
    
    @Override
    public UnitPosition NewGame(Grid grid) {
        stage.show();
        Stage shopStage= new Stage();
        int x=200;
        int y=200;
        ImageView im =null;
        Group group= new Group();
        Button Buy =new Button("Buy");
        Buy.setStyle("-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
        Buy.setPrefSize(250, 50);
        Buy.setOnAction((ActionEvent)->{
            
        }
        );
        for(int i=0;i<UnitName.values().length;i++)
        {
            if(((teamId==0 && UnitName.values()[i].getUnitType()!= UnitType.Airplane)||(teamId==1 &&  UnitName.values()[i].getUnitType()!=UnitType.Structure)) &&  UnitName.values()[i].getUnitType()!= UnitType.MainBase)
            {
                Object[] details=unitDetail(UnitName.values()[i]);
                im = new ImageView((String)details[0]);
                im.setTranslateX(x);
                im.setTranslateY(y);
                //rest of details
                Buy.setTranslateX(x);
                Buy.setTranslateY(y);
                group.getChildren().add(im);
                group.getChildren().add(Buy);
            }
        }
        
        
        Button startgame =new Button("Start Game");
        startgame.setStyle("-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff");
        startgame.setPrefSize(250, 50);
            startgame.setOnAction((ActionEvent)->{
            
        }
        );
        Scene scene=new Scene(group,700,700);
        shopStage.setScene(scene);
        return zoom();
    }

    Object[] unitDetail(UnitName unit)
    {
        Object[] details=new String[8];
        details[0]=unit.getImageName();
        details[1]=unit.name();
        details[2]=unit.getUnitPrice();
        details[3]=unit.getHealth();
        details[4]=unit.getArmor();
        details[5]=unit.getAttackDamage();
        details[6]=unit.getAttackSpeed();
        details[7]=unit.getRadius();
        return details;
    }
    
    @Override
    public void LoadGame() {

    }
    
    UnitPosition zoom()
    {
        return null;
    }
}

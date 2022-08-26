/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Bcc
 */
public class Main extends Application{

    Stage stage;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("zero for Console, one for GUI");
        Scanner s= new Scanner(System.in);
        int type=s.nextInt();
        if(type==0)
        {
            GameManager game=new DoDGameManager(null,type,1);
        }
        else if (type==1)
        {
            launch();
        }
        
    }
    
    @Override
    public void start(Stage s)  {  
        GameManager game=new DoDGameManager(s,1,1);
        s.setTitle("Main");
        s.show(); 
    }
    
}

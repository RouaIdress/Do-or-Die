/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.time.LocalTime;

/**
 *
 * @author Bcc
 */
public class SecondTimer extends Thread {
    
    int sec;
    boolean finished;
    
    SecondTimer(int sec)
    {
        this.sec=sec;
        finished=false;
    }
    
    @Override
    public void run()
    {
        LocalTime time=LocalTime.now();
        while(time.getSecond()!=sec)
        {
            time=LocalTime.now();
        }
        finished=true;
    }

    public boolean Finished() {
        return finished;
    }
    
}

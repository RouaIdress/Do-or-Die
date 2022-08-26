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
public class Timer extends Thread{
    double min;
    int sec;
    boolean finished;
    
    Timer(double min,int sec)
    {
        this.min=min;
        this.sec=sec;
        finished=false;
    }
    
    @Override
    public void run()
    {
        LocalTime time=LocalTime.now();
        while(time.getMinute()!=min)
        {
            time=LocalTime.now();
        }
        while(time.getSecond()<sec)
        {
            time=LocalTime.now();
        }
        finished=true;
    }

    public boolean Finished() {
        return finished;
    }
    
}

/* COMP2240 Assignment 1
 * File: LTR.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 15/8/24
 * Description: Lottery Scheduling Algorithm
 */

// PACKAGES //
import java.util.ArrayList;

public class LTR extends Scheduler 
{
    // CLASS VARIABLES //
    private ArrayList<Process> enterQueue;
    private ArrayList<Process> readyQueue = new ArrayList<Process>();
    private ArrayList<Process> finishedQueue = new ArrayList<Process>();
    private final int DISPATCHER;
    private int timer = 0;
    private String name = "LTR";

    // CONSTRUCTORS //
    //Pre-condition:
    //Post-condition:
    public LTR() 
    {
        enterQueue = new ArrayList<Process>();
        DISPATCHER = 0;
    }
    //Pre-condition:
    //Post-condition:
    public LTR(ArrayList<Process> enterQueue, int dispatcher)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = dispatcher;
    }

    // METHODS //
    //Pre-condition:
    //Post-condition:
    @Override
    public Process dispatch()
    {
        Process nextProcess = readyQueue.get(0);
        /*readyQueue.remove(0);
        timer += dispatcher;
        String dispatchLog = "T" + timer + ": " + nextProcess.getPID();
        dispatchLogs.add(dispatchLog);*/
        return nextProcess;
    }
    //Pre-condition:
    //Post-condition:
    @Override
    public void run() 
    {
        /*int t1;
        while(enterQueue.size() > finishedQueue.size())
        {
            admit();
            Process runningProcess = dispatch();
            t1 = timer;
            runningProcess.setWaitTime(t1);
            while(runningProcess.getSrvTime() > timer-t1)
            {
                timer++;
            }
            runningProcess.setTurnTime(timer-runningProcess.getArrTime());
            finishedQueue.add(runningProcess);
        }*/
    }
    
    // ACCESSORS //
    //Pre-condition:
    //Post-condition:
    @Override 
    public String getName()
    {
        return name;
    }
    //Pre-condition:
    //Post-condition:
    @Override
    public ArrayList<Process> getEnterQueue()
    {
        return enterQueue;
    }
    //Pre-condition:
    //Post-condition:
    @Override
    public ArrayList<String> getDispatchLogs()
    {
        return dispatchLogs;
    }
 
}
 

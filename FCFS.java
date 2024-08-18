/* COMP2240 Assignment 1
 * File: FCFS.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 15/8/24
 * Description: First-Come-First-Serve Scheduling Algorithm
 */

 // PACKAGES //
import java.util.ArrayList;

public class FCFS extends Scheduler 
{
    // CLASS VARIABLES //
    private ArrayList<Process> enterQueue;
    private ArrayList<Process> finishedQueue = new ArrayList<Process>();
    private final int DISPATCHER;
    private int timer = 0;
    private String name = "FCFS";

    // CONSTRUCTORS //
    //Pre-condition:
    //Post-condition:
    public FCFS() 
    {
        enterQueue = new ArrayList<Process>();
        DISPATCHER = 0;
    }
    //Pre-condition:
    //Post-condition:
    public FCFS(ArrayList<Process> enterQueue, int DISPATCHER)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = DISPATCHER;
    }

    // METHODS //
    //Pre-condition:
    //Post-condition:
    @Override
    public Process dispatch()
    {
        //Assume the first process has the earliest arrival time
        int earliestArrTime = enterQueue.get(0).getArrTime();
        int nextDispatchIndex = 0;
        for(int i = 0; i < enterQueue.size(); i++)
        {
            //If another process has arrived earlier
            if(enterQueue.get(i).getArrTime() < earliestArrTime)
            {
                earliestArrTime = enterQueue.get(i).getArrTime();
                nextDispatchIndex = i;
            }

            //If two process have the same arrival time
            if(enterQueue.get(i).getArrTime() == earliestArrTime)
            {
                int id1 = Integer.parseInt(enterQueue.get(i).getPID().substring(1));
                int id2 = Integer.parseInt(enterQueue.get(nextDispatchIndex).getPID().substring(1));

                if(id1 < id2)
                {
                    earliestArrTime = enterQueue.get(i).getArrTime();
                    nextDispatchIndex = i;
                }
            }
        }

        timer += DISPATCHER;
        Process nextDispatch = enterQueue.get(nextDispatchIndex);
        //Log the next running process's time and process ID
        String nextDispatchLog = "T" + timer + ": " + nextDispatch.getPID();
        dispatchLogs.add(nextDispatchLog);
        return nextDispatch;
    }
    //Pre-condition:
    //Post-condition:
    @Override
    public void run() 
    {
        int t1;
        while(enterQueue.size() != 0)
        {
            Process runningProcess = dispatch();
            t1 = timer;
            runningProcess.setWaitTime(t1);
            while(runningProcess.getSrvTime() > timer-t1)
            {
                timer++;
            }
            runningProcess.setTurnTime(timer-runningProcess.getArrTime());
            finishedQueue.add(runningProcess);
            enterQueue.remove(runningProcess);
        }
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
    public ArrayList<Process> getFinishedQueue()
    {
        return finishedQueue;
    }
    //Pre-condition:
    //Post-condition:
    @Override
    public ArrayList<String> getDispatchLogs()
    {
        return dispatchLogs;
    }

}

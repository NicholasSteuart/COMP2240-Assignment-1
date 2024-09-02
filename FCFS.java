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
    private ArrayList<Process> readyQueue = new ArrayList<Process>();
    private ArrayList<Process> finishedQueue = new ArrayList<Process>();
    private final int DISPATCHER;
    private int timer = 0;
    private String name = "FCFS";
    private boolean[] visited;

    // CONSTRUCTORS //
    //Pre-condition:
    //Post-condition:
    public FCFS() 
    {
        enterQueue = new ArrayList<Process>();
        DISPATCHER = 0;
        visited = new boolean[0];
    }
    //Pre-condition:
    //Post-condition:
    public FCFS(ArrayList<Process> enterQueue, int DISPATCHER)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = DISPATCHER;
        visited = new boolean[enterQueue.size()];
    }

    // METHODS //
    //Pre-condition:
    //Post-condition:
    @Override
    public Process dispatch()
    {
        admit();
        Process nextDispatch = findFCFS();
        timer += DISPATCHER;
        //Log the next running process's time and process ID
        String nextDispatchLog = "T" + timer + ": " + nextDispatch.getPID();
        dispatchLogs.add(nextDispatchLog);

        return nextDispatch;
    }
    //Pre-condition:
    //Post-condition:
    public void admit()
    {
        for(int i = 0; i < enterQueue.size(); i++)
        {
            if(enterQueue.get(i).getArrTime() <= timer && !visited[i])
            {
                readyQueue.add(enterQueue.get(i));
                visited[i] = true;
            } 
        }
    }
    //Pre-condition:
    //Post-condition:
    public Process findFCFS()
    {
        int earliestArrTime = Integer.MAX_VALUE;
        int nextDispatchIndex = 0;

        for(int i = 0; i < readyQueue.size(); i++)
        {
            //If another process has arrived earlier
            if(readyQueue.get(i).getArrTime() < earliestArrTime && !visited[i])
            {
                earliestArrTime = enterQueue.get(i).getArrTime();
                visited[i] = true;
                nextDispatchIndex = i;
            }

            //If two process have the same arrival time
            if(readyQueue.get(i).getArrTime() == earliestArrTime && !visited[i])
            {
                int id1 = Integer.parseInt(readyQueue.get(i).getPID().substring(1));
                int id2 = Integer.parseInt(readyQueue.get(nextDispatchIndex).getPID().substring(1));

                if(id1 < id2)
                {
                    earliestArrTime = enterQueue.get(i).getArrTime();
                    visited[i] = true;
                    nextDispatchIndex = i;
                }
            }
        }

        return readyQueue.get(nextDispatchIndex);
    }
    //Pre-condition:
    //Post-condition:
    @Override
    public void run() 
    {
        int t1;
        while(finishedQueue.size() != enterQueue.size())
        {
            Process runningProcess = dispatch();
            t1 = timer;
            while(runningProcess.getSrvTime() > timer-t1)
            {
                timer++;
            }
            runningProcess.setTurnTime(timer-runningProcess.getArrTime());
            runningProcess.setWaitTime(timer - runningProcess.getSrvTime());
            finishedQueue.add(runningProcess);
            readyQueue.remove(runningProcess);
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

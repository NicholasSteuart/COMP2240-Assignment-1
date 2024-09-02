/* COMP2240 Assignment 1
 * File: SRT.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 15/8/24
 * Description: Shortest Process Next Scheduling Algorithm
 */

// PACKAGES //
import java.util.ArrayList;

public class SRT extends Scheduler 
{
    // CLASS VARIABLES //
    private ArrayList<Process> enterQueue;
    private ArrayList<Process> readyQueue = new ArrayList<Process>();
    private ArrayList<Process> finishedQueue = new ArrayList<Process>();
    private final int DISPATCHER;
    private int timer = 0;
    private String name = "SRT";
    private boolean[] visited;

    // CONSTRUCTORS //
    //Pre-condition:
    //Post-condition:
    public SRT() 
    {
        enterQueue = new ArrayList<Process>();
        DISPATCHER = 0;
        visited = new boolean[0];
    }
    //Pre-condition:
    //Post-condition:
    public SRT(ArrayList<Process> enterQueue, int dispatcher)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = dispatcher;
        visited = new boolean[enterQueue.size()];
    }

    // METHODS //
    //Pre-condition:
    //Post-condition:
    @Override
    public Process dispatch()
    {   
        admit();   //Admit ready processes to readyQueue
        Process nextDispatch = findSRT();    //Finds process with Shortest Remaining Time in readyQueue
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
    public Process findSRT()
    {
        int shortestRemainingTime = Integer.MAX_VALUE;
        int nextDispatchIndex = 0;

        for(int i = 0; i < readyQueue.size(); i++)
        {
            if(shortestRemainingTime > readyQueue.get(i).getTimeRemaining())
            {
                shortestRemainingTime = readyQueue.get(i).getTimeRemaining();
                nextDispatchIndex = i;
            }
            if(shortestRemainingTime == readyQueue.get(i).getTimeRemaining())
            {
                int id1 = Integer.parseInt(readyQueue.get(i).getPID().substring(1));
                int id2 = Integer.parseInt(readyQueue.get(nextDispatchIndex).getPID().substring(1));

                if(id1 < id2)
                {
                    shortestRemainingTime = readyQueue.get(i).getTimeRemaining();
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
        while(finishedQueue.size() != enterQueue.size())
        {
            Process runningProcess = dispatch();

            runningProcess.setTimeRemaining(runningProcess.getTimeRemaining() - 1);  
            timer++; 

            if(runningProcess.getTimeRemaining() == 0)
            {
                runningProcess.setTurnTime(timer - runningProcess.getArrTime());
                runningProcess.setWaitTime(timer - runningProcess.getSrvTime());
                finishedQueue.add(runningProcess);
                readyQueue.remove(runningProcess);   
            }
            
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

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
    private ArrayList<Integer> randomNums = new ArrayList<Integer>();
    private final int DISPATCHER;
    private int timer = 0;
    private String name = "LTR";
    private boolean[] visited;

    // CONSTRUCTORS //
    //Pre-condition:
    //Post-condition:
    public LTR() 
    {
        enterQueue = new ArrayList<Process>();
        DISPATCHER = 0;
        visited = new boolean[0];
    }
    //Pre-condition:
    //Post-condition:
    public LTR(ArrayList<Process> enterQueue, int dispatcher, ArrayList<Integer> randomNums)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = dispatcher;
        this.randomNums = randomNums;
        visited = new boolean[enterQueue.size()];
    }

    // METHODS //
    //Pre-condition:
    //Post-condition:
    @Override
    public Process dispatch()
    {
        Process nextProcess = readyQueue.get(0);
        //int totalTickets = getTotalTickets();


        timer += DISPATCHER;
        String dispatchLog = "T" + timer + ": " + nextProcess.getPID();
        dispatchLogs.add(dispatchLog);
        return nextProcess;
    }
    //Pre-condition:
    //Post-condition:
    
    //Pre-condition:
    //Post-condition:
    @Override
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
    @Override
    public void run() 
    {
        
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
 

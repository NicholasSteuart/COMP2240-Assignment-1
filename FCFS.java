/* COMP2240 Assignment 1
 * File: FCFS.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 12/8/24
 * Description: First-Come-First-Serve Scheduling Algorithm
 */

 // PACKAGES //
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class FCFS implements Scheduler 
{
    // CLASS VARIABLES //
    private ArrayList<Process> processList;
    private ArrayList<Process> readyQueue = new ArrayList<Process>();
    private ArrayList<Process> finishedQueue = new ArrayList<Process>();
    private LinkedHashMap<Integer, String> dispatchTimes = new LinkedHashMap<Integer, String>();
    private int dispatcher;
    private int timer = 0;

    // CONSTRUCTORS //
    //Pre-condition:
    //Post-condition:
    public FCFS() 
    {
        processList = new ArrayList<Process>();
        dispatcher = 0;
    }
    //Pre-condition:
    //Post-condition:
    public FCFS(ArrayList<Process> processList, int dispatcher)
    {
        this.processList = processList;
        this.dispatcher = dispatcher;
    }

    // METHODS //
    //Pre-condition:
    //Post-condition:
    public void admit()
    {
        ArrayList<Process> admitQueue = new ArrayList<Process>();
        for(Process process: processList)
        {
            if(process.getArrTime() <= timer)
            {
                admitQueue.add(process);
            }
        }
        for(int i = 0; i < admitQueue.size(); i++)
        {
            int processID = Integer.parseInt(admitQueue.get(i).getPID().substring(0));
            for(int j = i + 1; j < admitQueue.size(); j++)
            {
                int processID2 = Integer.parseInt(admitQueue.get(j).getPID().substring(0));
                if(processID > processID2)
                {
                    Collections.swap(admitQueue, i, j);
                }
            }
        }
        for(Process process: admitQueue)
        {
            readyQueue.add(process);
            processList.remove(process);
        }
    }
    //Pre-condition:
    //Post-condition:
    public Process dispatch()
    {
        Process nextProcess = readyQueue.get(0);
        readyQueue.remove(0);
        timer += dispatcher;
        dispatchTimes.put(timer, nextProcess.getPID());
        return nextProcess;
    }
    //Pre-condition:
    //Post-condition:
    public void run() 
    {
        int t1;
        while(processList.size() != 0)
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
        }
        finishedQueue.sort(null);
        processList = finishedQueue;
    }
    
    // ACCESSORS //
    //Pre-condition:
    //Post-condition:
    public ArrayList<Process> getProcessList()
    {
        return processList;
    }
    //Pre-condition:
    //Post-condition:
    public LinkedHashMap<Integer, String> getDispatchTimes()
    {
        return dispatchTimes;
    }
}

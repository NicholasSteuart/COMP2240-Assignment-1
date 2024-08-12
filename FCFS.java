/* COMP2240 Assignment 1
 * File: FCFS.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 12/8/24
 * Description: First-Come-First-Serve Scheduling Algorithm
 */

 // PACKAGES //
 import java.util.ArrayList;

public class FCFS implements Scheduler 
{
    // CLASS VARIABLES //

    private ArrayList<Process> readyQueue;
    private ArrayList<Process> finishedQueue = new ArrayList<Process>();
    private int dispatcher;
    private int timer = 0;

    // CONSTRUCTORS //

    //Pre-condition:
    //Post-condition:
    public FCFS() 
    {
        readyQueue = new ArrayList<Process>();
        dispatcher = 0;
    }
    //Pre-condition:
    //Post-condition:
    public FCFS(ArrayList<Process> readyQueue, int dispatcher)
    {
        this.readyQueue = readyQueue;
        this.dispatcher = dispatcher;
    }

    // FUNCTIONS //

    //Pre-condition:
    //Post-condition:
    public Process dispatch(Process runningProcess, ArrayList<Process> readyQueue) 
    {
        int t1 = timer; 
        Process nextProcess = new Process();
    
        for(Process process: readyQueue)
        {
            //Avoiding infinite loop
            if(process.getPID() == runningProcess.getPID())
            {
                continue;
            }
            //ii.Dispatcher will not consider any process that arrives after t1
            if(process.getArrTime() > t1)
            {
                continue;
            }
            //iii. Not required for non-preemptive policy.

            //Implement iv.
        }

        timer += dispatcher;
        return nextProcess;
    }
    //Pre-condition:
    //Post-condition:
    public void run() 
    {
        
    }
}

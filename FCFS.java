/* COMP2240 Assignment 1
 * File: FCFS.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 5/9/24
 * Description: Provides functionality of a First-Come-First-Serve CPU Scheduling Algorithm 
 */

 // PACKAGES //

import java.util.ArrayList;

public class FCFS extends Scheduler 
{
    // CLASS VARIABLES //

    private ArrayList<Process> enterQueue;  //Stores Process objects that will be run in the CPU scheduling simulation
    private ArrayList<Process> readyQueue = new ArrayList<Process>();   //Stores ready Process objects
    private ArrayList<Process> finishedQueue = new ArrayList<Process>(); //Stores Process objects that have finished executing
    private final int DISPATCHER;   //Time taken for the dispatcher to choose the next process to run
    private int timer = 0;          //Time units of the simulation
    private String name = "FCFS";   //Name of the scheduling algorithm
    private boolean[] visited;      //Flags whether a Process object in the enterQueue has been admitted to the readyQueue or not 

    // CONSTRUCTORS //

    //PRE-CONDITION: No PRE-CONDITION
    //POST-CONDITION: Class variables enterQueue, DISPATCHER and visited instantiated with default values
    public FCFS() 
    {
        enterQueue = new ArrayList<Process>();
        DISPATCHER = 0;
        visited = new boolean[0];
    }
    //PRE-CONDITION: 
    //Parameter enterQueue must not be null and must contain at least one Process object
    //Parameter DISPATCHER must be zero or a positive integer
    //POST-CONDITION: 
    //Specialised Constructor instantiated with parameters enterQueue and DISPATCHER mutating their respective Class variables
    //and Class variable visited instantiated with a flag for each Process object in enterQueue
    public FCFS(ArrayList<Process> enterQueue, int DISPATCHER)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = DISPATCHER;
        visited = new boolean[enterQueue.size()];   
    }

    // METHODS //

    //PRE-CONDITION: FCFS constructor instantiated
    //POST-CONDITION: Returns the next Process object in the readyQueue to run based on the FCFS scheduling algorithm
    @Override
    public Process dispatch()
    {
        admit();    //Admit Process objects that are ready from the enterQueue
        Process nextDispatch = findFCFS(); //Find the next Process object in the readyQueue to run
        timer += DISPATCHER;    //Increment time taken to dispatch

        //Log the next running Process object's entry time in the CPU and it's ID
        String nextDispatchLog = "T" + timer + ": " + nextDispatch.getPID();
        dispatchLogs.add(nextDispatchLog);

        return nextDispatch;
    }
    //PRE-CONDITION: FCFS Constructor instantiated
    //POST-CONDITION: Process objects in the enterQueue that are ready to run are added to the readyQueue
    @Override
    public void admit()
    {
        for(int i = 0; i < enterQueue.size(); i++)
        {
            if(enterQueue.get(i).getArrTime() <= timer && !visited[i])  //IF Process object has arrived before the current time and has not already entered the readyQueue...
            {
                readyQueue.add(enterQueue.get(i));
                visited[i] = true; 
            } 
        }
    }
    //PRE-CONDITION: FCFS Constructor instantiated
    //POST-CONDITION: Returns the next Process object in the readyQueue to run based on the FCFS scheduling algorithm
    public Process findFCFS()
    {
        int earliestArrTime = Integer.MAX_VALUE;    //Keeps track of the earliest arrival time out of all the Process objects in the readyQueue
        int nextDispatchIndex = 0;  //Stores the index of the Process object in the readyQueue with the earliest arrival time

        for(int i = 0; i < readyQueue.size(); i++)
        {
            //If another Process object has arrived earlier, make current Process objects index the current next Process to dispatch
            if(readyQueue.get(i).getArrTime() < earliestArrTime && !visited[i])
            {
                earliestArrTime = enterQueue.get(i).getArrTime();
                visited[i] = true;
                nextDispatchIndex = i;
            }

            //IF the current next Dispatch Process object has the same earliest arrival time as the current Process object...
            if(readyQueue.get(i).getArrTime() == earliestArrTime && !visited[i])
            {
                //Check to see which Process object has the smaller pID
                int id1 = Integer.parseInt(readyQueue.get(i).getPID().substring(1));    //Current Process object's ID
                int id2 = Integer.parseInt(readyQueue.get(nextDispatchIndex).getPID().substring(1)); //The current earliest Process object's ID

                //IF the current Process object's ID is smaller than the current earliest arrived Process object's ID,
                //the current Process object is the current next Process to dispatch
                if(id1 < id2) 
                {
                    earliestArrTime = enterQueue.get(i).getArrTime();
                    visited[i] = true;
                    nextDispatchIndex = i;
                }
            }
        }

        return readyQueue.get(nextDispatchIndex);   //Return the earliest arrived Process object
    }
    //PRE-CONDITION: FCFS Constructor instantiated
    //POST-CONDITION: 
    //All Process object's in enterQueue run through the FCFS CPU scheduling algorithm 
    //finishedQueue populated with every enterQueue Process object
    @Override
    public void run() 
    {
        int t1; //Stores the time a process starts running
        //Runs until every Process object in enterQueue has finished executing
        while(finishedQueue.size() != enterQueue.size())
        {
            Process runningProcess = dispatch();    //Dispatch the next Process object to run
            t1 = timer; //Store current time

            //Run running Process object until it has finished executing
            while(runningProcess.getSrvTime() > timer-t1)
            {
                timer++;    
            }

            //Calculate turnaround time and wait time of the Process object
            runningProcess.setTurnTime(timer-runningProcess.getArrTime());
            runningProcess.setWaitTime(timer - (runningProcess.getArrTime() + runningProcess.getSrvTime()));
            //Update the readyQueue and finishedQueue
            finishedQueue.add(runningProcess);
            readyQueue.remove(runningProcess);
        }
    }
    
    // ACCESSORS //

    //PRE-CONDITION: FCFS Constructor instantiated
    //POST-CONDITION: Class variable name returned
    @Override 
    public String getName()
    {
        return name;
    }
    //PRE-CONDITION: FCFS Constructor instantiated
    //POST-CONDITION: Class variable enterQueue returned
    @Override
    public ArrayList<Process> getEnterQueue()
    {
        return enterQueue;
    }
    //PRE-CONDITION: FCFS Constructor instantiated
    //POST-CONDITION: Parent Class variable dispatchLogs returned
    @Override
    public ArrayList<String> getDispatchLogs()
    {
        return dispatchLogs;
    }
}

/* COMP2240 Assignment 1
 * File: SRT.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 5/9/24
 * Description: Provides functionality of a Shortest Remaining Time CPU Scheduling Algorithm 
 */

// PACKAGES //
import java.util.ArrayList;

public class SRT extends Scheduler 
{
    // CLASS VARIABLES //

    private ArrayList<Process> enterQueue; //Stores Process objects that will be run in the CPU scheduling simulation
    private ArrayList<Process> readyQueue = new ArrayList<Process>(); //Stores ready Process objects
    private ArrayList<Process> finishedQueue = new ArrayList<Process>(); //Stores Process objects that have finished executing
    private final int DISPATCHER;   //Time taken for the dispatcher to choose the next process to run
    private int timer = 0;          //Time units of the simulation
    private String name = "SRT";    //Name of the scheduling algorithm
    private boolean[] visited;      //Flags whether a Process object in the enterQueue has been admitted to the readyQueue or not 


    // CONSTRUCTORS //

    //PRE-CONDITION: No PRE-CONDITION
    //POST-CONDITION: Class variables enterQueue, DISPATCHER and visited instantiated with default values
    public SRT() 
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
    public SRT(ArrayList<Process> enterQueue, int dispatcher)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = dispatcher;
        visited = new boolean[enterQueue.size()];
    }

    // METHODS //

    //PRE-CONDITION: SRT constructor instantiated
    //POST-CONDITION: Returns the next Process object in the readyQueue to run based on the SRT scheduling algorithm
    @Override
    public Process dispatch()
    {   
        Process nextDispatch = findSRT();    //Finds Process object with Shortest Remaining Time in readyQueue
        timer += DISPATCHER; //Increment time taken to dispatch

        //Log the next running Process object's entry time in the CPU and it's ID
        String nextDispatchLog = "T" + timer + ": " + nextDispatch.getPID();
        dispatchLogs.add(nextDispatchLog);

        return nextDispatch;
    }
    //PRE-CONDITION: SRT Constructor instantiated
    //POST-CONDITION: Process objects in the enterQueue that are ready to run are added to the readyQueue
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
    //PRE-CONDITION: SRT Constructor instantiated
    //POST-CONDITION: Returns the next Process object in the readyQueue to run based on the SRT scheduling algorithm
    public Process findSRT()
    {
        int shortestRemainingTime = Integer.MAX_VALUE; //Keeps track of the Process object with the shortest remaining time in the readyQueue
        int nextDispatchIndex = 0; //Stores the index of the Process object in the readyQueue with the shortest remaining time

        for(int i = 0; i < readyQueue.size(); i++)
        {
            //If another Process object has a shorter remaining time, make current Process objects index the current next Process to dispatch
            if(shortestRemainingTime > readyQueue.get(i).getTimeRemaining())
            {
                shortestRemainingTime = readyQueue.get(i).getTimeRemaining();
                nextDispatchIndex = i;
            }
            //IF the current next Dispatch Process object has the same shortest remaining time as the current Process object...
            if(shortestRemainingTime == readyQueue.get(i).getTimeRemaining())
            {
                //Check to see which Process object has the smaller pID
                int id1 = Integer.parseInt(readyQueue.get(i).getPID().substring(1)); //Current Process object's ID
                int id2 = Integer.parseInt(readyQueue.get(nextDispatchIndex).getPID().substring(1)); //The current shortest remaining time Process object's ID

                //IF the current Process object's ID is smaller than the current shortest remaining time Process object's ID,
                //the current Process object is the current next Process to dispatch
                if(id1 < id2)
                {
                    shortestRemainingTime = readyQueue.get(i).getTimeRemaining();
                    nextDispatchIndex = i;
                }
            }
        }
        return readyQueue.get(nextDispatchIndex); //Return the shortest remaining time Process object
    }
    //PRE-CONDITION: SRT Constructor instantiated
    //POST-CONDITION: 
    //All Process object's in enterQueue run through the SRT CPU scheduling algorithm 
    //finishedQueue populated with every enterQueue Process object
    @Override
    public void run() 
    {
        admit();                             //Admit ready processes to readyQueue
        Process runningProcess = dispatch();
        //Runs until every Process object in enterQueue has finished executing
        while(finishedQueue.size() != enterQueue.size())
        {
            admit();   //Admit Process objects that are ready from the enterQueue

            //IF there is a Process object in the readyQueue with a shorter remaining time, preempt that Process object to run
            if(isPreempted(runningProcess))
            {
                runningProcess = dispatch();
            } 
            //Run running Process object for 1 time unit
            runningProcess.setTimeRemaining(runningProcess.getTimeRemaining() - 1);  
            timer++; 

            //IF runningProcess finishes executing during this timeSlice...
            if(runningProcess.getTimeRemaining() == 0)
            {
                //Calculate turnaround time and wait time of the Process object
                runningProcess.setTurnTime(timer - runningProcess.getArrTime());
                runningProcess.setWaitTime(timer - (runningProcess.getArrTime() + runningProcess.getSrvTime()));
                //Update the readyQueue and finishedQueue
                finishedQueue.add(runningProcess);
                readyQueue.remove(runningProcess);   
            }
        }
    }
    //PRE-CONDITION: 
    //SRT Constructor instantiated
    //runningProcess is not null
    //POST-CONDITION: Returns:
    //True IF runningProcess does not have the shortest remaining time out of all Process objects in the readyQueue
    //False IF it does OR is the only Process in the readyQueue

    public boolean isPreempted(Process runningProcess)
    {
        if(readyQueue.size() == 0)
        {
            return false;
        }
        else
        {
            if(runningProcess != findSRT())
            {
                return true;
            }
        }
        return false;
    }
    
    // ACCESSORS //

    //PRE-CONDITION: SRT Constructor instantiated
    //POST-CONDITION: Class variable name returned
    @Override 
    public String getName()
    {
        return name;
    }
    //PRE-CONDITION: SRT Constructor instantiated
    //POST-CONDITION: Class variable enterQueue returned
    @Override
    public ArrayList<Process> getEnterQueue()
    {
        return enterQueue;
    }
    //PRE-CONDITION: SRT Constructor instantiated
    //POST-CONDITION: Parent Class variable dispatchLogs returned
    @Override
    public ArrayList<String> getDispatchLogs()
    {
        return dispatchLogs;
    }

}
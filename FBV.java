/* COMP2240 Assignment 1
 * File: FBV.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 5/9/24
 * Description: Provides functionality of a Standard multi-level (3 levels) Feedback Scheduling Algorithm which uses Round Robin for it's 
 * lowest priority queue
 */

// PACKAGES //

import java.util.ArrayList;

public class FBV extends Scheduler 
{
    // CLASS VARIABLES //

    private ArrayList<Process> enterQueue; //Stores Process objects that will be run in the CPU scheduling simulation
    private ArrayList<Process> highPriorityQueue = new ArrayList<Process>(); //Stores ready Process objects with the highest priority (Priority = 1)
    private ArrayList<Process> medPriorityQueue = new ArrayList<Process>(); //Stores ready Process objects with the second highest priority (Priority = 2)
    private ArrayList<Process> lowPriorityQueue = new ArrayList<Process>(); //Stores ready Process objects with the lowest priority (Priority = 3)
    private ArrayList<Process> finishedQueue = new ArrayList<Process>(); //Stores Process objects that have finished executing

    private final int DISPATCHER;   //Time taken for the dispatcher to choose the next process to run
    private int timer = 0;          //Time units of the simulation
    private String name = "FBV";   //Name of the scheduling algorithm
    private boolean[] visited;      //Flags whether a Process object in the enterQueue has been admitted to the readyQueue or not 

    // CONSTRUCTORS //

    //PRE-CONDITION: No PRE-CONDITION
    //POST-CONDITION: Class variables enterQueue, DISPATCHER and visited instantiated with default values
    public FBV() 
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
    public FBV(ArrayList<Process> enterQueue, int dispatcher)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = dispatcher;
        visited = new boolean[enterQueue.size()];
    }

    // METHODS //

    //PRE-CONDITION: FBV constructor instantiated
    //POST-CONDITION: Returns the next Process object in the readyQueue to run based on what the next highest priority Process is 
    @Override
    public Process dispatch()
    {
        Process nextDispatch = new Process();

        if(!highPriorityQueue.isEmpty())    //There is atleast 1 high priority ready Process object
        {
            nextDispatch = highPriorityQueue.get(0);
        }
        else if(!medPriorityQueue.isEmpty()) //There is atleast 1 medium priority ready Process object and no high priority Process object ready
        {
            nextDispatch = medPriorityQueue.get(0);
        }
        else if(!lowPriorityQueue.isEmpty()) //There is atleast 1 low priority ready Process object and no high or medium priority Process object ready
        {
            nextDispatch = lowPriorityQueue.get(0);
        }

        timer += DISPATCHER; //Increment time taken to dispatch

        //Log the next running Process object's entry time in the CPU and it's ID
        String dispatchLog = "T" + timer + ": " + nextDispatch.getPID();
        dispatchLogs.add(dispatchLog);

        return nextDispatch;
    }
    //PRE-CONDITION: FBV Constructor instantiated
    //POST-CONDITION: Process objects in the enterQueue that are ready to run are added to the highPriorityQueue
    @Override
    public void admit()
    {
        for(int i = 0; i < enterQueue.size(); i++)
        {
            if(enterQueue.get(i).getArrTime() <= timer && !visited[i])
            {
                highPriorityQueue.add(enterQueue.get(i));
                visited[i] = true;
            } 
        }
    }
    //PRE-CONDITION: FBV Constructor instantiated
    //POST-CONDITION: 
    //All Process object's in enterQueue run through the FBV CPU scheduling algorithm 
    //finishedQueue populated with every enterQueue Process object
    @Override
    public void run() 
    {
        //Runs until every Process object in enterQueue has finished executing
        while(!highPriorityQueue.isEmpty() || !medPriorityQueue.isEmpty() || !lowPriorityQueue.isEmpty() || enterQueue.size() > finishedQueue.size())
        {
            admit(); //Admit Process objects that are ready from the enterQueue
            Process runningProcess = dispatch(); //Dispatch the next Process object to run

            if(runningProcess.getPriority() == 1) //IF running Process object is a high priority process...
            {
                executeProcess(runningProcess, 2); //Execute running Process object for time slice of 2 time units
            }
            else if(runningProcess.getPriority() == 2) //IF running Process object is a medium priority process...
            {
                executeProcess(runningProcess, 4); //Execute running Process object for time slice of 4 time units
            }
            else if(runningProcess.getPriority() == 3) //IF running Process object is a low priority process...
            {
                executeRoundRobin(runningProcess, 4); //Execute running Process object for a time slice of 4 time units in a Round Robin fashion
            }
        }   
    }
    //PRE-CONDITION: 
    //FBV Constructor instantiated
    //runningProcess not equal to null
    //timeSlice must be a non-negative integer
    //POST-CONDITION: 
    //runningProcess has executed for its allocated timeSlice, or
    //finished executing during it's timeSlice and populated the finishedQueue
    public void executeProcess(Process runningProcess, int timeSlice)
    {
        //IF runningProcess will not finish executing after receiving it's timeSlice...
        if(runningProcess.getTimeRemaining() > timeSlice)
        {
            //Execute runningProcess for allocated timeSlice
            runningProcess.setTimeRemaining(runningProcess.getTimeRemaining() - timeSlice);
            for(int i = 0; i < timeSlice; i++)
            {
                timer++;
                admit();    //Must be checked to maintain order of new Process objects becoming ready during the running Process timeSlice
            }
            
            //After the runningProcess receives it's timeSlice, Reduce it's Priority by one and place it in it's new priority queue
            if(runningProcess.getPriority() == 1)
            {
                runningProcess.setPriority(2);
                medPriorityQueue.add(runningProcess);
                highPriorityQueue.remove(runningProcess);
            }
            else if(runningProcess.getPriority() == 2)
            {
                runningProcess.setPriority(3);
                lowPriorityQueue.add(runningProcess);
                medPriorityQueue.remove(runningProcess);
            }
        }
        else    //IF runningProcess will finish during it's timeSlice...
        {
            for(int i = 0; i < runningProcess.getTimeRemaining(); i++)
            {
                timer++;
                admit();    //Same check as line 140
            }
            //Calculate turnaround time and wait time of the Process object
            runningProcess.setTurnTime(timer - runningProcess.getArrTime());
            runningProcess.setWaitTime(timer - (runningProcess.getArrTime() + runningProcess.getSrvTime()));
            //Remove runningProcess from it's particular priority queue
            if(runningProcess.getPriority() == 1)
            {
                finishedQueue.add(runningProcess);
                highPriorityQueue.remove(runningProcess);
            }
            else if(runningProcess.getPriority() == 2)
            {
                finishedQueue.add(runningProcess);
                medPriorityQueue.remove(runningProcess);
            }
        }

    }
    //PRE-CONDITION:
    //FBV Constructor instantiated
    //runningProcess not equal to null
    //timeSlice must be a non-negative integer
    //POST-CONDITION:
    //runningProcess has executed for its allocated timeSlice and added to the back of the low priority queue in a Round Robin fashion, or
    //runningProcess has executed for its allocated timeSlice and has been re-added to the high priority queue due to it starving in the low priority queue for more than 16 time units, or
    //finished executing during it's timeSlice and populated the finishedQueue

    public void executeRoundRobin(Process runningProcess, int timeSlice)
    {
        //Assign runningProcess to the first Process object in the low Priority Queue
        runningProcess = lowPriorityQueue.get(0);
        //Increment runningProcess starvationTime by the timeSlice
        runningProcess.setStarvationTime(runningProcess.getStarvationTime() + timeSlice);
        //IF runningProcess will not finish executing after receiving it's timeSlice...
        if(runningProcess.getTimeRemaining() > timeSlice)
        {
            //Execute runningProcess for allocated timeSlice
            runningProcess.setTimeRemaining(runningProcess.getTimeRemaining() - timeSlice);
            for(int i = 0; i < timeSlice; i++)
            {
                timer++;
                admit();    //Same check as line 140
            }

            //Priority Boost Process to Highest Priority Queue if it has spent more than 16 time units in the Low Priority Queue
            if(runningProcess.getStarvationTime() > 16)
            {
                runningProcess.setPriority(1);
                //Update priority queues
                highPriorityQueue.add(runningProcess);
                lowPriorityQueue.remove(runningProcess);
            }
        }
        else    //IF runningProcess will finish during it's timeSlice...
        {
            for(int i = 0; i < timeSlice; i++)
            {
                timer++;
                admit();    //Same check as line 140
            }
            //Calculate turnaround time and wait time of the Process object
            runningProcess.setTurnTime(timer - runningProcess.getArrTime());
            runningProcess.setWaitTime(timer - (runningProcess.getArrTime() + runningProcess.getSrvTime()));
            //Remove runningProcess from the low priority quuee
            finishedQueue.add(runningProcess);
            lowPriorityQueue.remove(runningProcess); 
        }
    }
   
    // ACCESSORS //

    //PRE-CONDITION: FBV Constructor instantiated
    //POST-CONDITION: Class variable name returned
    @Override 
    public String getName()
    {
        return name;
    }
    //PRE-CONDITION: FBV Constructor instantiated
    //POST-CONDITION: Class variable enterQueue returned
    @Override
    public ArrayList<Process> getEnterQueue()
    {
        return enterQueue;
    }
    //PRE-CONDITION: FBV Constructor instantiated
    //POST-CONDITION: Parent Class variable dispatchLogs returned
    @Override
    public ArrayList<String> getDispatchLogs()
    {
        return dispatchLogs;
    }
        
}
 


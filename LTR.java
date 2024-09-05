/* COMP2240 Assignment 1
 * File: LTR.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 5/9/24
 * Description: Provides functionality of a Lottery style Proportional-Share CPU Scheduling Algorithm
 */

// PACKAGES //

import java.util.ArrayList;

public class LTR extends Scheduler 
{
    // CLASS VARIABLES //

    private ArrayList<Process> enterQueue; //Stores Process objects that will be run in the CPU scheduling simulation
    private ArrayList<Process> readyQueue = new ArrayList<Process>(); //Stores ready Process objects
    private ArrayList<Process> finishedQueue = new ArrayList<Process>();  //Stores Process objects that have finished executing
    private ArrayList<Integer> randomNums = new ArrayList<Integer>(); //Stores the random numbers used to run the lottery
    private final int DISPATCHER;   //Time taken for the dispatcher to choose the next process to run
    private int timer = 0;          //Time units of the simulation
    private String name = "LTR";   //Name of the scheduling algorithm
    private boolean[] visited;      //Flags whether a Process object in the enterQueue has been admitted to the readyQueue or not 

    // CONSTRUCTORS //

    //PRE-CONDITION: No PRE-CONDITION
    //POST-CONDITION: Class variables enterQueue, DISPATCHER and visited instantiated with default values
    public LTR() 
    {
        enterQueue = new ArrayList<Process>();
        DISPATCHER = 0;
        visited = new boolean[0];
    }
    //PRE-CONDITION: 
    //Parameter enterQueue must not be null and must contain at least one Process object
    //Parameter DISPATCHER must be zero or a positive integer
    //Parameter randomNums must not be null
    //POST-CONDITION: 
    //Specialised Constructor instantiated with parameters enterQueue, DISPATCHER, and randomNums mutating their respective Class variables
    //and Class variable visited instantiated with a flag for each Process object in enterQueue
    public LTR(ArrayList<Process> enterQueue, int dispatcher, ArrayList<Integer> randomNums)
    {
        this.enterQueue = enterQueue;
        this.DISPATCHER = dispatcher;
        this.randomNums = randomNums;
        visited = new boolean[enterQueue.size()];
    }

    // METHODS //

    //PRE-CONDITION: LTR constructor instantiated
    //POST-CONDITION: Returns the next Process object in the readyQueue to run based on the LTR scheduling algorithm
    @Override
    public Process dispatch()
    {
        Process nextProcess = runLottery(getTotalTickets()); //Finds the next Process to run based on which process wins the lottery
        timer += DISPATCHER; //Increment time taken to dispatch

        //Log the next running Process object's entry time in the CPU and it's ID
        String dispatchLog = "T" + timer + ": " + nextProcess.getPID();
        dispatchLogs.add(dispatchLog);

        return nextProcess;
    }
    //PRE-CONDITION: LTR Constructor instantiated
    //POST-CONDITION: returns totalTickets
    public int getTotalTickets()
    {
        int totalTickets = 0;   //Value must be reset due to fluctuation of the ready queue's processes.
        for(Process process: readyQueue)
        {
            totalTickets += process.getTickets();
        }
        return totalTickets;
    }
    //PRE-CONDITION: LTR Constructor instantiated
    //POST-CONDITION: returns the next Process object to dispatch from the readyQueue based on which Process had the winning ticket
    public Process runLottery(int totalTickets)
    {
        int winningTicket = randomNums.get(0) % totalTickets;   //Due to randomNums value exceeding the totalTickets, we do modulus math to find the winning ticket
        randomNums.remove(0);   //Remove the randomNum used for this pass from the list
        int counter = 0;              //Tracks whether we have passed the Process object with the winning ticket
        Process nextProcess = null;   //Used to return the next Process object to dispatch
        for(Process process: readyQueue)
        {                                           
            counter += process.getTickets();     //Increment tickets by the current Process object's tickets   
            if(counter > winningTicket)          //IF the winning ticket number is less than the counter value, the current process is the winner
            {
                nextProcess = process;
                break;
            }
        }

        return nextProcess;
    }
    //PRE-CONDITION: LTR Constructor instantiated
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
    //PRE-CONDITION: LTR Constructor instantiated
    //POST-CONDITION: 
    //All Process object's in enterQueue run through the LTR CPU scheduling algorithm 
    //finishedQueue populated with every enterQueue Process object
    @Override
    public void run() 
    {
        //Runs until every Process object in enterQueue has finished executing
        while(finishedQueue.size() != enterQueue.size())
        {
            admit(); //Admit Process objects that are ready from the enterQueue
            Process runningProcess = dispatch(); //Dispatch the next Process object to run

            //IF runningProcess will not finish executing after receiving it's timeSlice...
            if(runningProcess.getTimeRemaining() > 3)
            {
                runningProcess.setTimeRemaining(runningProcess.getTimeRemaining() - 3);
                for(int i = 0; i < 3; i++)
                {
                    timer++;
                    admit(); //Must be checked to maintain order of new Process objects becoming ready during the running Process timeSlice
                }
                //runningProcess is now placed at the end of the readyQueue
                Process removed = readyQueue.remove(readyQueue.indexOf(runningProcess));
                readyQueue.add(removed);
            }
            else //IF runningProcess will finish during it's timeSlice...
            {
                for(int i = 0; i < runningProcess.getTimeRemaining(); i++)
                {
                    timer++;
                    admit(); //Same check as line 132
                }
                //Calculate turnaround time and wait time of the Process object
                runningProcess.setTurnTime(timer - runningProcess.getArrTime());
                runningProcess.setWaitTime(timer - (runningProcess.getArrTime() + runningProcess.getSrvTime()));
                //Update the readyQueue and finishedQueue
                finishedQueue.add(runningProcess);
                readyQueue.remove(runningProcess);
            }
        }
    }
    
    // ACCESSORS //

    //PRE-CONDITION: LTR Constructor instantiated
    //POST-CONDITION: Class variable name returned
    @Override 
    public String getName()
    {
        return name;
    }
    //PRE-CONDITION: LTR Constructor instantiated
    //POST-CONDITION: Class variable enterQueue returned
    @Override
    public ArrayList<Process> getEnterQueue()
    {
        return enterQueue;
    }
    //PRE-CONDITION: LTR Constructor instantiated
    //POST-CONDITION: Parent Class variable dispatchLogs returned
    @Override
    public ArrayList<String> getDispatchLogs()
    {
        return dispatchLogs;
    }
}
 

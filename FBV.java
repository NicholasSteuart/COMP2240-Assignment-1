/* COMP2240 Assignment 1
 * File: FBV.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 15/8/24
 * Description: Standard multi-level (3 levels) Feedback Scheduling Algorithm
 */

// PACKAGES //
import java.util.ArrayList;

public class FBV extends Scheduler 
{
    // CLASS VARIABLES //
    private ArrayList<Process> enterQueue;
    private ArrayList<Process> highPriorityQueue = new ArrayList<Process>();
    private ArrayList<Process> medPriorityQueue = new ArrayList<Process>();
    private ArrayList<Process> lowPriorityQueue = new ArrayList<Process>();
    private ArrayList<Process> finishedQueue = new ArrayList<Process>();

    private final int DISPATCHER;
    private int timer = 0;
    private String name = "FBV";
    private boolean[] visited;

    // CONSTRUCTORS //
    //Pre-condition:
    //Post-condition:
    public FBV() 
    {
        enterQueue = new ArrayList<Process>();
        DISPATCHER = 0;
        visited = new boolean[0];
    }
    //Pre-condition:
    //Post-condition:
    public FBV(ArrayList<Process> enterQueue, int dispatcher)
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
        Process nextDispatch = new Process();
        if(!highPriorityQueue.isEmpty())
        {
            nextDispatch = highPriorityQueue.get(0);
        }
        else if(!medPriorityQueue.isEmpty())
        {
            nextDispatch = medPriorityQueue.get(0);
        }
        else if(!lowPriorityQueue.isEmpty())
        {
            nextDispatch = lowPriorityQueue.get(0);
        }

        timer += DISPATCHER;
        String dispatchLog = "T" + timer + ": " + nextDispatch.getPID();
        dispatchLogs.add(dispatchLog);
        return nextDispatch;
    }
    //Pre-condition:
    //Post-condition:
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
    //Pre-condition:
    //Post-condition:
    @Override
    public void run() 
    {
        while(!highPriorityQueue.isEmpty() || !medPriorityQueue.isEmpty() || !lowPriorityQueue.isEmpty() || enterQueue.size() > finishedQueue.size())
        {
            admit();
            Process runningProcess = dispatch();
            if(runningProcess.getPriority() == 1)
            {
                executeProcess(runningProcess, 2);
            }
            else if(runningProcess.getPriority() == 2)
            {
                executeProcess(runningProcess, 4);
            }
            else if(runningProcess.getPriority() == 3)
            {
                executeRoundRobin(runningProcess, 4);
            }
        }   
    }
    //Pre-condition:
    //Post-condition:
    public void executeProcess(Process runningProcess, int timeSlice)
    {
        if(runningProcess.getTimeRemaining() > timeSlice)
        {
            runningProcess.setTimeRemaining(runningProcess.getTimeRemaining() - timeSlice);
            timer += timeSlice;
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
        else
        {
            timer += runningProcess.getTimeRemaining();
            runningProcess.setTurnTime(timer - runningProcess.getArrTime());
            runningProcess.setWaitTime(timer - (runningProcess.getArrTime() + runningProcess.getSrvTime()));
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
    //Pre-condition:
    //Post-condition:
    public void executeRoundRobin(Process runningProcess, int timeSlice)
    {
        runningProcess = lowPriorityQueue.get(0);
        if(runningProcess.getTimeRemaining() > timeSlice)
        {
            runningProcess.setTimeRemaining(runningProcess.getTimeRemaining() - timeSlice);
            timer += timeSlice;
        }
        else
        {
            timer += runningProcess.getTimeRemaining();
            runningProcess.setTurnTime(timer - runningProcess.getArrTime());
            runningProcess.setWaitTime(timer - (runningProcess.getArrTime() + runningProcess.getSrvTime()));
            finishedQueue.add(runningProcess);
            lowPriorityQueue.remove(runningProcess); 
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
 


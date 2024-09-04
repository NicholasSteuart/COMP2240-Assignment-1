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
        Process nextProcess = runLottery(getTotalTickets());

        timer += DISPATCHER;
        String dispatchLog = "T" + timer + ": " + nextProcess.getPID();
        dispatchLogs.add(dispatchLog);
        return nextProcess;
    }
    //Pre-condition:
    //Post-condition:
    public int getTotalTickets()
    {
        int totalTickets = 0;   //Value must be reset due to fluctuation of the ready queue's processes.
        for(Process process: readyQueue)
        {
            totalTickets += process.getTickets();
        }
        return totalTickets;
    }
    //Pre-condition:
    //Post-condition:
    public Process runLottery(int totalTickets)
    {
        int winningTicket = randomNums.get(0) % totalTickets;
        int counter = 0;
        Process nextProcess = null;
        for(Process process: readyQueue)
        {                                           //Original datafile1.txt tickets: 400, 100, 150, 200, 250
            counter += process.getTickets();        //Original datafile2.txt tickets: 16, 1, 7, 19, 10
            if(counter > winningTicket)
            {
                nextProcess = process;
                break;
            }
        }
        System.out.println("TIMER: " + timer + " RANDOMNUMBER: " + randomNums.get(0) + " TOTALTICKETS: " + totalTickets + " WINNINGTICKET: " + winningTicket + " WINNING PROCESS: " + nextProcess.getPID());
        randomNums.remove(0);

        return nextProcess;
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
        while(finishedQueue.size() != enterQueue.size())
        {
            admit();
            Process runningProcess = dispatch();
            if(runningProcess.getTimeRemaining() > 3)
            {
                runningProcess.setTimeRemaining(runningProcess.getTimeRemaining() - 3);
                for(int i = 0; i < 3; i++)
                {
                    timer++;
                    admit();
                }
                Process removed = readyQueue.remove(readyQueue.indexOf(runningProcess));
                readyQueue.add(removed);
            }
            else
            {
                for(int i = 0; i < runningProcess.getTimeRemaining(); i++)
                {
                    timer++;
                    admit();
                }
                runningProcess.setTurnTime(timer - runningProcess.getArrTime());
                runningProcess.setWaitTime(timer - (runningProcess.getArrTime() + runningProcess.getSrvTime()));
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
 

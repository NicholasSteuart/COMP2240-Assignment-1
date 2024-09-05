/* COMP2240 Assignment 1
 * File: Scheduler.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 5/9/24
 * Description: Parent Class of the scheduling algorithms that records and runs the CPU Scheduling algorithms
 * 
 */

// PACKAGES //

import java.util.ArrayList;

abstract class Scheduler 
{

    // CLASS VARIABLES //

    protected ArrayList<String> dispatchLogs = new ArrayList<String>(); //Stores logs of the time and Process object ID of when it was dispatched by a scheduler

    // ABSTRACT METHODS //

    abstract public Process dispatch();
    abstract public void admit();
    abstract public void run();
    abstract public String getName();
    abstract public ArrayList<Process> getEnterQueue();
    abstract public ArrayList<String> getDispatchLogs();

    // CONCRETE METHODS //

    //PRE-CONDITION: No PRE-CONDITION
    //POST-CONDITION: String dispatchLogs returned containing the logs of a Scheduler's dispatch
    public String printDispatchLogs()
    {
        String dispatchlogs = getName() + ":\n";
        for(String log: this.getDispatchLogs())
        {
            dispatchlogs += log + "\n";
        }
        dispatchlogs += "\n";
        resetDispatchLogs();    //Avoids the next Scheduler's dispatch logs containing this Scheduler's dispatch logs 
        return dispatchlogs;
    }
    //PRE-CONDITION: No PRE-CONDITION
    //POST-CONDITION: All items from dispatchLogs removed
    public void resetDispatchLogs()
    {
        dispatchLogs.clear();
    }
    //PRE-CONDITION: No PRE-CONDITION
    //POST-CONDITION: String returned containing the average turnTime or waitTime of a scheduler in 2 decimal place formatting
    public String getAverage(String dataType)
    {
        int sum = 0;
        for(Process process: this.getEnterQueue())
        {
            if(dataType == "turnTime")  //IF we are finding average turnaround time
            {
                sum += process.getTurnTime();
            }
            else   //Else we are calculating wait time. 
            {
                sum += process.getWaitTime();
            }
        }
        double average = (double)sum / this.getEnterQueue().size(); //Calculate average
        return String.format("%.2f", average);
    }
}

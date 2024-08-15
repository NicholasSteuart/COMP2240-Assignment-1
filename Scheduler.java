/* COMP2240 Assignment 1
 * File: Scheduler.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 15/8/24
 * Description: Abstract Class abstracting the scheduling algorithms required for the Assignment.
 */

// PACKAGES //
import java.util.ArrayList;

abstract class Scheduler 
{
    ArrayList<String> dispatchLogs = new ArrayList<String>();

    // ABSTRACT METHODS //
    abstract public void admit();
    abstract public Process dispatch();
    abstract public void run();

    abstract public String getName();
    abstract public ArrayList<Process> getFinishedQueue();
    abstract public ArrayList<String> getDispatchLogs();

    // CONCRETE METHODS //
    //Pre-condition:
    //Post-condition:
    public String printDispatchLogs()
    {
        String dispatchlogs = getName() + ":\n";
        for(String log: this.getDispatchLogs())
        {
            dispatchlogs += log + "\n";
        }
        dispatchlogs += "\n";
        return dispatchlogs;
    }
    //Pre-condition: 
    //Post-condition: 
    public String getAverage(String dataType)
    {
        int sum = 0;
        for(Process process: this.getFinishedQueue())
        {
            if(dataType == "turnTime") 
            {
                sum += process.getTurnTime();
            }
            else   //Wait Time
            {
                sum += process.getWaitTime();
            }
        }
        double average = (double)sum / this.getFinishedQueue().size();
        return String.format("%.2f", average);
    }
}

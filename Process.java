/* File: Process.java
* Author: Nicholas Steuart c3330826
* Date Created: 12/8/24
* Date Last Modified: 5/9/24
* Description: The Process class represents a process in a CPU scheduling simulation. 
* Each process contains essential information that is used by various 
* CPU scheduling algorithms to manage and prioritize execution order.
*/
public class Process 
{
    // CLASS VARIABLES //

    private String pID; //Process ID
    private int arrTime, srvTime, tickets, timeRemaining; //Arrival Time, ServeTime, Tickets, and Time Remaining before a process has finished executing
    private int turnTime = 0, waitTime = 0, priority = 1, starvationTime = 0; //Turnaround Time, Wait Time, Priority of the Process, and time before a process must be priority boosted

    // CONSTRUCTORS //

    //PRE-CONDITION: No PRE-CONDITION
    //POST-CONDITION: Class variables pID, arrTime, srvTime, tickets and timeRemaining instantiated with default values
    public Process()
    {
        pID = "";
        arrTime = 0;
        srvTime = 0;
        tickets = 0;
        timeRemaining = 0;
    }
    //PRE-CONDITION: 
    //Parameter pID must be a String of the from pn where n is a positive integer
    //Parameter arrTime must be a non-negative integer
    //Parameter srvTime must be a positive integer
    //Parameter tickets must be a positive integer
    //POST-CONDITION: Specialised Constructor instantiated with parameters pID, arrTime, srvTime and tickets mutating their respective Class variables
    public Process(String pID, int arrTime, int srvTime, int tickets) 
    {
        this.pID = pID;
        this.arrTime = arrTime;
        this.srvTime = srvTime;
        this.tickets = tickets;
        timeRemaining = srvTime;
    }

    // MUTATORS //

    //PRE-CONDITION: Process Constructor instantiated and Parameter pID must be a String of the form pn where n is a positive integer
    //POST-CONDITION: Class variable pID mutated by parameter pID
    public void setPID(String pID)
    {
        this.pID = pID;
    }
    //PRE-CONDITION: Process Constructor instantiated and Parameter arrTime must be a non-negative integer
    //POST-CONDITION: Class variable arrTime mutated by parameter arrTime
    public void setArrTime(int arrTime)
    {
        this.arrTime = arrTime;
    }
    //PRE-CONDITION: Process Constructor instantiated and Parameter srvTime must be a positive integer
    //POST-CONDITION: Class variable srvTime mutated by parameter srvTime
    public void setSrvTime(int srvTime)
    {
        this.srvTime = srvTime;
    }
    //PRE-CONDITION: Process Constructor instantiated and Parameter tickets must be a positive integer
    //POST-CONDITION: Class variable tickets mutated by parameter tickets
    public void setTickets(int tickets)
    {
        this.tickets = tickets;
    }
    //PRE-CONDITION: Process Constructor instantiated and Parameter turnTime must be a non-negative integer
    //POST-CONDITION: Class variable turnTime mutated by parameter turnTime
    public void setTurnTime(int turnTime)
    {
        this.turnTime = turnTime;
    }
    //PRE-CONDITION: Process Constructor instantiated and Parameter waitTime must be a non-negative integer
    //POST-CONDITION: Class variable waitTime mutated by parameter waitTime
    public void setWaitTime(int waitTime)
    {
        this.waitTime = waitTime;
    }
    //PRE-CONDITION: Process Constructor instantiated and Parameter timeRemaining must be a non-negative integer
    //POST-CONDITION: Class variable timeRemaining mutated by parameter timeRemaining
    public void setTimeRemaining(int timeRemaining)
    {
        this.timeRemaining = timeRemaining;
    }
    //PRE-CONDITION: Process Constructor instantiated and Parameter priority must be of the value 1, 2 or 3
    //POST-CONDITION: Class variable priority mutated by parameter priority
    public void setPriority(int priority)
    {
        this.priority = priority;
    }
    //PRE-CONDITION: Process Constructor instantiated and Parameter starvationTime must be a non-negative value 
    //POST-CONDITION: Class variable starvationTime mutated by parameter starvationTime
    public void setStarvationTime(int starvationTime)
    {
        this.starvationTime = starvationTime;
    }

    // ACCESSORS //

    //PRE-CONDITION: Process Constructor instantiated and pID must not be null
    //POST-CONDITION: Class variable pID returned
    public String getPID()
    {
        return pID;
    }
    //PRE-CONDITION: Process Constructor instantiated
    //POST-CONDITION: Class variable arrTime returned
    public int getArrTime()
    {
        return arrTime;
    }
    //PRE-CONDITION: Process Constructor instantiated
    //POST-CONDITION: Class variable srvTime returned
    public int getSrvTime()
    {
        return srvTime;
    }
    //PRE-CONDITION: Process Constructor instantiated
    //POST-CONDITION: Class variable tickets returned
    public int getTickets()
    {
        return tickets;
    }
    //PRE-CONDITION: Process Constructor instantiated and setTurnTime(turnTime) called at some point prior
    //POST-CONDITION: Class variable turnTime returned
    public int getTurnTime()
    {
        return turnTime;
    }
    //PRE-CONDITION: Process Constructor instantiated and setWaitTime(waitTime) called at some point prior
    //POST-CONDITION: Class variable waitTime returned
    public int getWaitTime()
    {
        return waitTime;
    }
    //PRE-CONDITION: Process Constructor instantiated 
    //POST-CONDITION: Class variable timeRemaining returned
    public int getTimeRemaining()
    {
        return timeRemaining;
    }
    //PRE-CONDITION: Process Constructor instantiated
    //POST-CONDITION: Class variable returned
    public int getPriority()
    {
        return priority;
    }
    //PRE-CONDITION: Process Constructor instantiated
    //POST-CONDITION: Class variable starvationTime returned
    public int getStarvationTime()
    {
        return starvationTime;
    }
}

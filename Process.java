/* File: Process.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 12/8/24
 * Description: Process Class. Stores information about a process.
 */
public class Process 
{
    // CLASS VARIABLES //

    private String pID;
    private int arrTime, srvTime, tickets, timeRemaining; 
    private int turnTime = 0, waitTime = 0;

    // CONSTRUCTORS //

    //Pre-condition:
    //Post-condition:
    public Process()
    {
        pID = "";
        arrTime = 0;
        srvTime = 0;
        tickets = 0;
        timeRemaining = 0;
    }
    //Pre-condition:
    //Post-condition:
    public Process(String pID, int arrTime, int srvTime, int tickets) 
    {
        this.pID = pID;
        this.arrTime = arrTime;
        this.srvTime = srvTime;
        this.tickets = tickets;
        timeRemaining = srvTime;
    }

    // MUTATORS //

    //Pre-condition:
    //Post-condition:
    public void setPID(String pID)
    {
        this.pID = pID;
    }
    //Pre-condition:
    //Post-condition:
    public void setArrTime(int arrTime)
    {
        this.arrTime = arrTime;
    }
    //Pre-condition:
    //Post-condition:
    public void setSrvTime(int srvTime)
    {
        this.srvTime = srvTime;
    }
    //Pre-condition:
    //Post-condition:
    public void setTickets(int tickets)
    {
        this.tickets = tickets;
    }
    //Pre-condition:
    //Post-condition:
    public void setTurnTime(int turnTime)
    {
        this.turnTime = turnTime;
    }
    //Pre-condition:
    //Post-condition:
    public void setWaitTime(int waitTime)
    {
        this.waitTime = waitTime;
    }
    //Pre-condition:
    //Post-condition:
    public void setTimeRemaining(int timeRemaining)
    {
        this.timeRemaining = timeRemaining;
    }
    // ACCESSORS //

    //Pre-condition:
    //Post-condition:
    public String getPID()
    {
        return pID;
    }
    //Pre-condition:
    //Post-condition:
    public int getArrTime()
    {
        return arrTime;
    }
    //Pre-condition:
    //Post-condition:
    public int getSrvTime()
    {
        return srvTime;
    }
    //Pre-condition:
    //Post-condition:
    public int getTickets()
    {
        return tickets;
    }
    //Pre-condition:
    //Post-condition:
    public int getTurnTime()
    {
        return turnTime;
    }
    //Pre-condition:
    //Post-condition:
    public int getWaitTime()
    {
        return waitTime;
    }
    //Pre-condition:
    //Post-condition:
    public int getTimeRemaining()
    {
        return timeRemaining;
    }

}

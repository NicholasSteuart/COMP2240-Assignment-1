/* COMP2240 Assignment 1
 * File: A1.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/8/24
 * Date Last Modified: 15/8/24
 * Description: MAIN file. Takes File I/O data and calculates Scheduling Algorithm simulations.
 */

// PACKAGES //
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class A1 
{
  public static void main(String[] args) throws FileNotFoundException
  {
    File file = new File(args[0]);
    Scanner sc = new Scanner(file);

    ArrayList<Process> data = new ArrayList<Process>();
    int dispatcher = 0;
    Process newProcess;

    // READING IN FILE //
    if(sc.hasNext("BEGIN"))
    {
      sc.next();
      if(sc.hasNext("DISP:"))
      {
        sc.next();
        dispatcher = sc.nextInt();
        sc.next();
      }
      do
      {
        String pID = "";
        int arrTime = 0, srvTime = 0, tickets = 0;
        if(sc.hasNext("PID:"))
        {
          sc.next();
          pID = sc.next();
        }
        if(sc.hasNext("ArrTime:"))
        {
          sc.next();
          arrTime = sc.nextInt();
        }
        if(sc.hasNext("SrvTime:"))
        {
          sc.next();
          srvTime = sc.nextInt();
        }
        if(sc.hasNext("Tickets:"))
        {
          sc.next();
          tickets = sc.nextInt();
        }
        newProcess = new Process(pID, arrTime, srvTime, tickets);
        //System.out.println("NEW PROCESS CREATED: PID: " + newProcess.getPID() + " ARRTIME: " + newProcess.getArrTime() + " SRVTIME: " + newProcess.getSrvTime() + " TICKETS: " + newProcess.getTickets());
        data.add(newProcess);
        sc.next();
      }
      while(!sc.hasNext("BEGINRANDOM"));
    }
    sc.close();


    FCFS fcfs = new FCFS(data, dispatcher);
    SRT srt = new SRT(data, dispatcher);
    FBV fbv = new FBV(data, dispatcher);
    LTR ltr = new LTR(data, dispatcher);

    // RUN SCHEDULING ALGORITHMS //
    fcfs.run();
    //srt.run();
    //fbv.run();
    //ltr.run();

    // OUTPUT //

    System.out.print(printResults(fcfs));
    System.out.print(printResults(srt));
    System.out.print(printResults(fbv));
    System.out.print(printResults(ltr));
    System.out.print(printSummary(fcfs, srt, fbv, ltr));

  }
  
  // METHODS //
  //Pre-condition:
  //Post-condition:
  public static String printResults(Scheduler scheduler)
  {
    String results = scheduler.printDispatchLogs();
    results += "Process  Turnaround Time  Waiting Time\n";
    for(Process process: scheduler.getFinishedQueue())
    {
      results += String.format("%-8s %-16s %s", process.getPID(), process.getTurnTime(), process.getWaitTime()) + "\n";
    }
    results += "\n";
    return results;
  }
  //Pre-condition:
  //Post-condition:
  public static String printSummary(Scheduler fcfs, Scheduler srt, Scheduler fbv, Scheduler ltr)
  {
    String summary = "Summary\nAlgorithm  Average Turnaround Time  Average Waiting Time\n";
    String padding = "%-10s %-24s %s";
    summary += String.format(padding, fcfs.getName(), fcfs.getAverage("turnTime"), fcfs.getAverage("waitTime") + "\n");
    summary += String.format(padding, srt.getName(), srt.getAverage("turnTime"), srt.getAverage("waitTime") + "\n");
    summary += String.format(padding, fbv.getName(), fbv.getAverage("turnTime"), fbv.getAverage("waitTime") + "\n");
    summary += String.format(padding, ltr.getName(), ltr.getAverage("turnTime"), ltr.getAverage("waitTime") + "\n");

    return summary;
  }
}
/* COMP2240 Assignment 1
 * File: A1.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/8/24
 * Date Last Modified: 15/8/24
 * Description: MAIN file. Takes File I/O data and calculates Scheduling Algorithm simulations.
 */

// PACKAGES //
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.ArrayList;

public class A1 
{
  public static void main(String[] args) throws Exception
  {
    File file = new File(args[0]);
    try (Scanner sc = new Scanner(file);)
    {       
      int dispatcher = 0;
      Process newProcess;

      ArrayList<Process> fcfsData = new ArrayList<Process>();
      ArrayList<Process> srtData = new ArrayList<Process>();
      ArrayList<Process> fbvData = new ArrayList<Process>();
      ArrayList<Process> ltrData = new ArrayList<Process>();
      ArrayList<Integer> randomNums = new ArrayList<Integer>();

      // READING IN FILE //
      if(sc.hasNext("BEGIN"))
      {
        sc.next();
        if(sc.hasNext("DISP:"))
        {
          sc.next();
          dispatcher = sc.nextInt();
          if(dispatcher < 0)
          {
            throw new Exception("DISP must be a non-negative Integer");
          }
          sc.next();
        }
      }
      int prevIndex = 0;
      do
      {
        String pID = "";
        int arrTime = 0, srvTime = 0, tickets = 0;
        if(sc.hasNext("PID:"))
        {
          sc.next();
          pID = sc.next();
          int currIndex = Integer.parseInt(pID.substring(1));
          if(pID.charAt(0) != 'p' || pID.charAt(1) <= 0 || prevIndex != currIndex-1)
          {
            throw new Exception("PID must be of the form pn where n is a positive integer");
          }
          prevIndex++;
        }
        if(sc.hasNext("ArrTime:"))
        {
          sc.next();
          arrTime = sc.nextInt();
          if(arrTime < 0)
          {
            throw new Exception("ArrTime must be a non-negative integer");
          }
        }
        if(sc.hasNext("SrvTime:"))
        {
          sc.next();
          srvTime = sc.nextInt();
          if(srvTime <= 0)
          {
            throw new Exception("SrvTime must be a positive integer");
          }
        }
        if(sc.hasNext("Tickets:"))
        {
          sc.next();
          tickets = sc.nextInt();
          if(tickets <= 0)
          {
            throw new Exception("Tickets must be a positive integer");
          }
        }
        //Implemented this way to avoid shallow copies of the data ArrayLists
        fcfsData.add(new Process(pID, arrTime, srvTime, tickets));
        srtData.add(new Process(pID, arrTime, srvTime, tickets));
        fbvData.add(new Process(pID, arrTime, srvTime, tickets));
        ltrData.add(new Process(pID, arrTime, srvTime, tickets));
        sc.next();
      }
      while(!sc.hasNext("BEGINRANDOM"));

      if(sc.hasNext("BEGINRANDOM"))
      {
        sc.next();
        while(!sc.hasNext("ENDRANDOM"))
        {
          randomNums.add(sc.nextInt());
        }
      }
      
      sc.close();

      // OUTPUT //

      FCFS fcfs = new FCFS(fcfsData, dispatcher);
      SRT srt = new SRT(srtData, dispatcher);
      FBV fbv = new FBV(fbvData, dispatcher);
      LTR ltr = new LTR(ltrData, dispatcher, randomNums);

      fcfs.run();
      srt.run();
      fbv.run();
      ltr.run();
      
      System.out.print(printResults(fcfs));
      System.out.print(printResults(srt));
      System.out.print(printResults(fbv));
      System.out.print(printResults(ltr));
      System.out.print(printSummary(fcfs, srt, fbv, ltr));
    } 
    catch(FileNotFoundException e)
    {
      System.out.println("File " + args[0] + "was not found");
      e.printStackTrace();
    }
  }

  // METHODS //
  //Pre-condition:
  //Post-condition:
  public static String printResults(Scheduler scheduler)
  {
    String results = scheduler.printDispatchLogs();
    results += "Process  Turnaround Time  Waiting Time\n";
    for(Process process: scheduler.getEnterQueue())
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
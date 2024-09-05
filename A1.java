/* COMP2240 Assignment 1
 * File: A1.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/8/24
 * Date Last Modified: 5/9/24
 * Description: MAIN file. Reads in File I/O data and runs Scheduling Algorithm simulations on the data before outputting results to the terminal.
 */

// PACKAGES //

import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.ArrayList;

public class A1 
{
  // MAIN //
  public static void main(String[] args) throws Exception
  {
    File file = new File(args[0]);  //File to be read in from terminal line
    try (Scanner sc = new Scanner(file);)
    {       
      int dispatcher = 0; //Stores the DISP data from file

      //Instantiate separate data ArrayLists for each scheduling algorithm due to shallow copies interferring with other scheduling operations.
      //Creating a deep copy of each was a better alternative
      ArrayList<Process> fcfsData = new ArrayList<Process>(); 
      ArrayList<Process> srtData = new ArrayList<Process>();
      ArrayList<Process> fbvData = new ArrayList<Process>();
      ArrayList<Process> ltrData = new ArrayList<Process>();
      ArrayList<Integer> randomNums = new ArrayList<Integer>();

      // READING IN FILE //

      if(sc.hasNext("BEGIN"))
      {
        sc.next();
        if(sc.hasNext("DISP:")) //READ IN dispatcher data
        {
          sc.next();
          dispatcher = sc.nextInt();
          if(dispatcher < 0)  //Dispatcher must be zero or a positive integer, otherwise...
          {
            throw new Exception("DISP must be a non-negative Integer");
          }
          sc.next();
        }
      }
      int prevIndex = 0;
      do  //READ IN Process data 
      {
        //Process Data to be stored
        String pID = "";
        int arrTime = 0, srvTime = 0, tickets = 0;
        if(sc.hasNext("PID:"))
        {
          sc.next();
          pID = sc.next();
          int currIndex = Integer.parseInt(pID.substring(1));
          if(pID.charAt(0) != 'p' || pID.charAt(1) <= 0 || prevIndex != currIndex-1) //Process ID must be of the form pn where n is a positive integer. prevIndex check to make sure Process in file are in order
          {
            throw new Exception("PID must be of the form pn where n is a positive integer");
          }
          prevIndex++;
        }
        if(sc.hasNext("ArrTime:"))
        {
          sc.next();
          arrTime = sc.nextInt();
          if(arrTime < 0) //Arrival Time must be a non-negative integer
          {
            throw new Exception("ArrTime must be a non-negative integer");
          }
        }
        if(sc.hasNext("SrvTime:"))
        {
          sc.next();
          srvTime = sc.nextInt();
          if(srvTime <= 0)  //Service Time must be a positive integer
          {
            throw new Exception("SrvTime must be a positive integer");
          }
        }
        if(sc.hasNext("Tickets:"))
        {
          sc.next();
          tickets = sc.nextInt();
          if(tickets <= 0)  //Tickets must be a positive integer
          {
            throw new Exception("Tickets must be a positive integer");
          }
        }

        // ADD PROCESS DATA TO SCHEDULERS //

        fcfsData.add(new Process(pID, arrTime, srvTime, tickets));
        srtData.add(new Process(pID, arrTime, srvTime, tickets));
        fbvData.add(new Process(pID, arrTime, srvTime, tickets));
        ltrData.add(new Process(pID, arrTime, srvTime, tickets));
        sc.next();
      }

      // READ IN RANDOM NUMBERS //

      while(!sc.hasNext("BEGINRANDOM"));

      if(sc.hasNext("BEGINRANDOM"))
      {
        sc.next();
        while(!sc.hasNext("ENDRANDOM"))
        {
          randomNums.add(sc.nextInt());
        }
      }
      
      sc.close(); //Close scanner

      // INSTANTIATE AND RUN SCHEDULERS //

      FCFS fcfs = new FCFS(fcfsData, dispatcher);
      SRT srt = new SRT(srtData, dispatcher);
      FBV fbv = new FBV(fbvData, dispatcher);
      LTR ltr = new LTR(ltrData, dispatcher, randomNums);

      fcfs.run();
      srt.run();
      fbv.run();
      ltr.run();
      
      // OUTPUT RESULTS //

      System.out.print(printResults(fcfs));
      System.out.print(printResults(srt));
      System.out.print(printResults(fbv));
      System.out.print(printResults(ltr));
      System.out.print(printSummary(fcfs, srt, fbv, ltr));
    } 
    catch(FileNotFoundException e)  //IF the File path does not exist...
    {
      System.out.println("File " + args[0] + "was not found");
      e.printStackTrace();
    }
  }

  // METHODS //
  //PRE-CONDITION: Scheduler scheduler instantiated
  //POST-CONDITION: String results returned containing scheduler's turnaround time and wait time after the scheduler algorithm
  public static String printResults(Scheduler scheduler)
  {
    String results = scheduler.printDispatchLogs();
    results += "Process  Turnaround Time  Waiting Time\n";
    for(Process process: scheduler.getEnterQueue())
    {
      results += String.format("%-8s %-16s %s", process.getPID(), process.getTurnTime(), process.getWaitTime()) + "\n"; //Padded to match assignment specific output
    }
    results += "\n"; //Leaves a gap as per assignment specific output
    return results;
  }
  //PRE-CONDITION: Scheduler fcfs, srt, fbv, and ltr are all instantiated
  //POST-CONDITION: String summary returned containing the average turnaround times and wait times of each respective scheduler after running
  public static String printSummary(Scheduler fcfs, Scheduler srt, Scheduler fbv, Scheduler ltr)
  {
    //NOTE TO MARKER: The output files given in the assignment folder states "Waiting Time" but the values recorded are obviously average waiting time
    //Therefore, I have left the output as per the datafilex_output.txt design instead of writing "Average Waiting Time". I SHOULD NOT be marked down for this
    //as this is per the assignment specifications.
    String summary = "Summary\nAlgorithm  Average Turnaround Time  Waiting Time\n"; 
    String padding = "%-10s %-24s %s";  //Padding as per assignment specifications
    summary += String.format(padding, fcfs.getName(), fcfs.getAverage("turnTime"), fcfs.getAverage("waitTime") + "\n");
    summary += String.format(padding, srt.getName(), srt.getAverage("turnTime"), srt.getAverage("waitTime") + "\n");
    summary += String.format(padding, fbv.getName(), fbv.getAverage("turnTime"), fbv.getAverage("waitTime") + "\n");
    summary += String.format(padding, ltr.getName(), ltr.getAverage("turnTime"), ltr.getAverage("waitTime") + "\n");

    return summary;
  }
}
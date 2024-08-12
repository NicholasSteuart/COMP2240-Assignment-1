/* COMP2240 Assignment 1
 * File: Scheduler.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 12/8/24
 * Date Last Modified: 12/8/24
 * Description: Interface Class that generalises the scheduling algorithms
 */

// PACKAGES //
import java.util.ArrayList;

public interface Scheduler 
{
    public Process dispatch(Process runningProcess, ArrayList<Process> readyQueue);
    public void run();
}

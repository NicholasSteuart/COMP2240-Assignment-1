/* COMP2240 Assignment 1
 * File: A1.java
 * Author: Nicholas Steuart c3330826
 * Date Created: 9/8/24
 * Date Last Modified: 12/8/24
 * Description: MAIN file. Takes File I/O data and calculates Scheduling Algorithm simulations.
 */

// PACKAGES //
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class A1 
{
  public static void main(String[] args) 
  {
    try 
    {
      File file = new File(args[0]);
      Scanner sc = new Scanner(file);

      while (sc.hasNextLine()) 
      {
        String data = sc.nextLine();
        System.out.println(data);
      }
      sc.close();
    } catch (FileNotFoundException e) 
    {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
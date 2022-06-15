// Programmer: Hannah Hendrickson
// Class: CS145 Hybrid01
// Date: 4/26/2022
// Assignment: Lab #3 Deck of Cards
// Purpose: This program allows the user to play
// 			a card game called "Oh Well".
// Notes: I used Eclipse IDE for this lab.
//	I used a Stack of Card objects for my deck of cards.
// 	Try/Catch block within userPlayCard and userBid
//	Switch/Case used in userPlayCard
//	I believe that the user can't crash the program.

package HHCardGame;

import java.util.Scanner;

public class HHDeckOfCards
{
	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		// instructions are given once
		Game.instruction();
		
		// user is prompted whether they'd like to continue
		System.out.println("Would you like to play?");
		
		while (inputYesOrNo(input))
		{
			// plays one game, 4 rounds
			Game.play();
			
			System.out.printf("%n%nWould you like to play again?%n");
		} // end of while loop
	} // end of main method

	// inputYesOrNo method, returns user's desire to play as a boolean
	   public static boolean inputYesOrNo(Scanner input)
	   {
	      char userResponse;
	      
	      do
	      {
	         System.out.print("Type \"y\" or \"n\": ");
	         userResponse = input.next().charAt(0);
	         input.nextLine();
	         
	         if (Character.toLowerCase(userResponse) == 'y')
	         {
	            return true;
	         }
	         else if (Character.toLowerCase(userResponse) == 'n')
	         {
	            return false;
	         }
	      } while (Character.toLowerCase(userResponse) != 'y' || Character.toLowerCase(userResponse) != 'n');
	      return true;
	   } // end of inputYesOrNo method
} // end of class HHOhHell

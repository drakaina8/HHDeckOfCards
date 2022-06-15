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

public class Card
{
	private final String face;
	private final String suit;
	
	// Card constructor, initializes card face and suit
	public Card(String cardFace, String cardSuit)
	{
		this.face = cardFace;
		this.suit = cardSuit;
	} // end of Card constructor
	
	// toString getter method, returns card as String
	public String toString()
	{
		return face + " of " + suit;
	} // end of toString
	
	// getFace getter method, returns face
	public String getFace()
	{
		return face;
	} // end of getFace
	
	// getSuit getter method, returns suit
	public String getSuit()
	{
		return suit;
	} // end of getSuit
	
} // end of class Card

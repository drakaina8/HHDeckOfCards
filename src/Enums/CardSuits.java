package Enums;

public enum CardSuits 
{
	Diamonds("Diamonds"), 
	Clubs("Clubs"), 
	Hearts("Hearts"), 
	Spades("Spades");
	
	final String suit;
	
	CardSuits (String suit)
	{
		this.suit = suit;
	} // end of CardSuits constructor
} // end of CardSuits enum

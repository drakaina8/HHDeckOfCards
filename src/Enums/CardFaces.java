package Enums;

public enum CardFaces 
{
	Two("Two"), 
	Three("Three"), 
	Four("Four"), 
	Five("Five"), 
	Six("Six"), 
	Seven("Seven"), 
	Eight("Eight"), 
	Nine("Nine"), 
	Ten("Ten"), 
	Jack("Jack"), 
	Queen("Queen"), 
	King("King"),
	Ace("Ace");
	
	final String face;
	//final int value;
	
	CardFaces (String face)
	{
		this.face = face;
	} // end of CardFaces constructor
} // end of CardFaces enum

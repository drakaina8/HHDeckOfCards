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

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import Enums.CardFaces;

public class Game 
{
	private static Stack<Card> deck;
	private static Stack<Card> garbageDeck;
	private static ArrayList<Card> userHand;
	private static ArrayList<Card> compHand;
	private static Card trumpCard;
	private static int userScore;
	private static int compScore;
	
	// gameInstruction method, introduces game and rules
	protected static void instruction()
	{
		System.out.printf("Welcome to Oh Well!%n%n");
		System.out.printf("This is a trick-taking card game%n"
						+ "Your goal is to bid on how many tricks%n"
						+ "or rounds you can take and then take that amount.%n%n"
						+ "Taking a trick is 1 point%n"
						+ "Taking the number of tricks you bid for is 10 points.%n%n");
		
		System.out.printf("The rules are as follows,%n"
						+ "~A card will be drawn each round, which will determine the trump suit%n");
						
		
		System.out.printf("~When the first player in a round plays a card,"
						 + "\tthe other players must follow suit if they can.%n"
						 + "~The trump suit beats the secondary trump suit which beats%n"
						 + "\tany other suit.%n"
						 + "~Higher numbered cards beat lower cards%n"
						 + "\tTwo is the lowest number, Ace is the highest%n%n");
		
		//System.out.printf("%nLet's play!%n%n");
	} // end of gameInstruction method
	
	// playGame method, the main game code
	public static void play()
	{
		deck = Game.populateDeck();
		garbageDeck = new Stack<Card>();
		userHand = new ArrayList<Card>();
		compHand = new ArrayList<Card>();
		int userBid = 0;
		int compBid = 0;
		
		// deck is shuffled twice for good measure
		shuffle(deck);
		// deals cards from deck to player and computer
		// This hand is used for all four rounds
		shuffle(deck);
		dealHand();
		trumpCard = deck.pop();
		String trumpSuit = trumpCard.getSuit();
		System.out.printf("%nThe trump suit is " + trumpSuit + "%n");
		userBid = userBid();
		compBid = compBid(userBid);
		System.out.printf("Your bid is " + userBid 
						 + ".%nThe computer's bid is " + compBid + ".%n%n");
		for (int roundNum = 1; roundNum <=4; roundNum++)
		{
			playRound(roundNum);
			garbageDeck.push(trumpCard);
			trumpCard = null;
			trumpCard = deck.pop();
		} // end of for loop
		scoreGame(userBid, compBid);
	} // end of playGame method

	//userPlayRound method, user plays a card
	private static void playRound(int roundNum)
	{
		Card userCard;
		Card compCard;
		String secondaryTrump;
		String trumpSuit = trumpCard.getSuit();
		
		System.out.printf("%n%nROUND " + roundNum);
		System.out.printf("%nIt's your turn!%n");
		System.out.printf("%nThe trump suit is " + trumpSuit + "%n");
		
		userCard = userPlayCard();
		secondaryTrump = userCard.getSuit();
		garbageDeck.push(userCard);
		userHand.remove(userCard);
		userHand.add(deck.pop());
		System.out.printf("You played the " 
						 + userCard.toString() + "%n%n");
		// computer randomly selects a card within the rules
		compCard = compPlayCard(secondaryTrump);
		garbageDeck.push(compCard);
		compHand.remove(compCard);
		compHand.add(deck.pop());
		System.out.printf("The computer played the " 
						 + compCard.toString() + "%n%n");
		scoreRound(userCard, compCard);
	} // end of userPlayRound

	//userPlayCard method, prints out user's cards and numbers them
	// returns user's Card selection
	private static Card userPlayCard()
	{
		int userSelection = -1;
		boolean cardSelected = false;

		// Initializes blank card to avoid NullPointerException
		// Will be populated with card from User's hand
		Card userCard = new Card("","");
		showHand(userHand);

		// Intakes user's card selection
		do
		{
			try
			{
				Scanner input = new Scanner(System.in);
				System.out.printf("%nPlease select the card you'd like to play %n"
						 + "by typing it's corresponding number: %n");
				userSelection = input.nextInt();
				input.nextLine();
			}
			catch (InputMismatchException e)
			{
				System.out.printf("Please input your selection as a number.%n");
			} // end of try/catch
			
			switch (userSelection)
			{
				case 1: userCard = userHand.get(0);
						cardSelected = true;
						break;
				case 2: userCard = userHand.get(1);
						cardSelected = true;
						break;
				case 3: userCard = userHand.get(2);
						cardSelected = true;
						break;
				case 4: userCard = userHand.get(3);
						cardSelected = true;
						break;
			} // end of switch case
		} while (!cardSelected); // end of while loop
		return userCard;
	} // end of userPlayCard
	
	//compPlayCard method, randomly selects a card
	// from the comp's hand and ensures it is within
	// the rules to play it, returns it
	public static Card compPlayCard(String secondaryTrump)
	{
		Card compCard = new Card("","");
		
		for (Card cards : compHand)
		{
			if (matchSuit(compHand, cards, secondaryTrump))
			{
				compCard = cards;
			} // end of if
			else
			{
				compCard = compHand.get(pickRandomNum());
			} // end of if/else
		} // end of for each loop
		
		return compCard;
	} // end of compPlayCard method
	
	// matchSuit method, ensures user picks card with trumpSuit
	// if they have one in their hand
	public static boolean matchSuit(ArrayList<Card> playerHand, 
									Card playerCard, String secondaryTrump)
	{
		boolean continueGame = false;
		
		// If there is a trumpSuit card in the player's hand
		// then it is determined whether the user played that
		if (sameSuitInHand(playerHand, secondaryTrump) &&
			playerCard.getSuit().equals(secondaryTrump))
		{
			continueGame = true;
		}
		else if (!sameSuitInHand(playerHand, secondaryTrump))
		{
			continueGame = true;
		}
		else
		{
			continueGame = false;
		} // end of if/else
		
		return continueGame;
	} // end of matchSuit method
	
	// sameSuitInHand method, checks if the user 
	// has any secondary trump suits
	public static boolean sameSuitInHand(ArrayList<Card> playerHand,
										String secondaryTrump)
	{
		boolean suitInHand = false;
		
		if (playerHand.get(0).getSuit().equals(secondaryTrump) ||
			playerHand.get(1).getSuit().equals(secondaryTrump) ||
			playerHand.get(2).getSuit().equals(secondaryTrump) ||
			playerHand.get(3).getSuit().equals(secondaryTrump))
		{
			suitInHand = true;
		} 
		else
		{
			suitInHand = false;
		} // end of if/else
		
		return suitInHand;
	} // end of sameSuitInHand method
	
	// dealHand method, deals four cards to a player
	public static void dealHand()
	{
		Card newUserCard;
		Card newCompCard;
		for (int i = 0; i <=3; i++)
		{
			newUserCard = deck.pop();
			userHand.add(newUserCard);
		} // end of for loop
		
		for (int i = 0; i <=3; i++)
		{
			newCompCard = deck.pop();
			compHand.add(newCompCard);
		} // end of for loop
	} // end of dealHand method
	
	// takeBid method, takes the user's bid and returns it
	public static int userBid()
	{
		int userBid = 0;
		boolean exceptionFlag = false;
		
		System.out.printf("%nHow many cards do you think will win from your hand?%n");
		showHand(userHand);
		do
		{
			try
			{
				Scanner input = new Scanner(System.in);
				System.out.printf("%nPlease enter your bid as a number: ");
				userBid = input.nextInt();
				input.nextLine();
				exceptionFlag = true;
			}
			catch (Exception e)
			{
				// empty because the code loops if exception is thrown
			} // end of try/catch
		} while (!exceptionFlag);
		return userBid;
	} // end of takeBid method
	
	// compBid method, determines computer bid based on user's
	public static int compBid(int userBid)
	{
		int compBid = pickRandomNum();
		
		// The computer's bid is randomly set between 0 & 3
		// loops until the total bids does not equal 4
		while (compBid + userBid == 4)
		{
			compBid = pickRandomNum();
		} // end of while
		return compBid;
	} // end of compBid
	
	// scoreRound method, determines score of cards played
	public static void scoreRound(Card userCard, Card compCard)
	{
		String trumpSuit = trumpCard.getSuit();
		String userSuit = userCard.getSuit();
		String compSuit = compCard.getSuit();
		
		// taking a trick is 1 point
		// taking your bid is 10 points
		// higher numbers beat lower numbers
		// trump suit beats secondary suit 
		// secondary trump suit beats other suits
		// I've made it so the user always goes first
		// so the computer either needs to play a trump suit
		// or a secondary trump suit card to win
		
		if ((compSuit.equals(trumpSuit) ||
			compSuit.equals(userSuit)) &&
			!equalOrGreaterFace(userCard, compCard))
		{
			// computer won round
			compScore += 1;
			System.out.printf("%n%n~~ The computer won this round ~~%n%n");
		}
		else
		{
			// user won round
			userScore += 1;
			System.out.printf("%n%n~~ You won this round ~~%n%n");
		} // end of if/else
		System.out.printf("The current scores are: %n"
						+ "User: " + userScore
						+ "%nComputer: " + compScore + "%n%n");
	} // end of scoreRound method
	
	// scoreGame method, determines if the bid was taken 
	public static void scoreGame(int userBid, int compBid)
	{
		if (userScore == userBid)
		{
			userScore += 10;
			System.out.printf("You took your bid of " + userBid + ".%n%n");
		}
		else
		{
			System.out.printf("You didn't take your bid of" + userBid + ".%n");
		} // end of if
		
		if (compScore == compBid)
		{
			compScore += 10;
			System.out.printf("%nThe computer took it's bid of " + compBid + ".%n%n");
		}
		else
		{
			System.out.printf("%nThe computer didn't take it's bid of " + compBid + ".%n");
		} // end of if
		System.out.printf("The current scores are: %n"
				+ "User: " + userScore
				+ "%nComputer: " + compScore);
		if (userScore > compScore)
		{
			System.out.printf("%n%n~~ You won the game ~~%n%n");
		}
		else if (userScore < compScore)
		{
			System.out.printf("%n%n~~ You lost the game ~~%n%n");
		}
		else
		{
			// this should not be possible
			System.out.printf("%n%n~~ The game was a tie ~~%n%n");
		} // end of if/else
	} // end of scoreGame method
	
	// isHigherFace method, determines what card has a higher number
	public static boolean equalOrGreaterFace(Card userCard, Card compCard)
	{
		boolean userFaceIsEqualOrGreater = false;
		String userFace = userCard.getFace();
		String compFace = compCard.getFace();
		int userFaceNum = 0;
		int compFaceNum = 0;
		int i = 0;
		CardFaces facesArray[] = CardFaces.values();
		
		for (CardFaces face : facesArray)
		{
			i++;
			if (userFace.equals(face.toString()))
			{
				userFaceNum = i;
			} // end of if
			
			if (compFace.equals(face.toString()))
			{
				compFaceNum = i;
			} // end of if
		} // end of for each loop
		if (userFaceNum >= compFaceNum)
		{
			userFaceIsEqualOrGreater = true;
		}
		return userFaceIsEqualOrGreater;
	} // end of isHigherFace method
	
	// populateDeck method, fills stack with 52 Card objects
	public static Stack<Card> populateDeck()
			throws StackOverflowError
	{
		deck = new Stack<Card>();

		String[] suitArray = {"Diamonds", "Clubs", "Hearts", "Spades"};
		
		String[] faceArray = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", 
							"Eight", "Nine", "Ten", "Jack", "Queen", "King"};

		for (String suit : suitArray)
		{
			for (String face: faceArray)
			{
				Card card = new Card(face, suit);
				deck.push(card);
			} // end of for loop
		} // end of for each loop
		
		return deck;
	} // end of deckInit method
	
	// shuffleDeck method, randomizes order of cards in deck
	public static void shuffle(Stack<Card> deck)
	{
		Collections.shuffle(deck);
	} // end of shuffleDeck method
	
	// showHand method, lists user's hand of cards
	// numbers them
	private static void showHand(ArrayList<Card> playerHand)
	{
		System.out.printf("%nHere are your cards: %n");
		
		int i = 1;
		for (Card cards : playerHand)
		{
			System.out.printf(i + ". " + cards + "%n");
			i++;
		} // end of for each loop
	} // end of showHand
	
	// showFullDeck method, used for testing whether
	// the deck was populated with cards correctly
	public static void showFullDeck(Stack<Card> deck)
	{
		for (Card cards : deck)
		{
			System.out.printf(cards + " %n");
		} // end of for each loop
	} // end of show method
	
	// return top element, else throw exception
	public static Card pop()
	{
		if (deck.isEmpty())
		{
			throw new NoSuchElementException("Stack is empty, cannot pop");
		} // end of if
		
		// remove and return top element of Stack
		return deck.remove(deck.size() - 1);
	} // end of pop method
	
	// pickRandomNum method, returns random number as int
    public static int pickRandomNum()
    {
        //Computer picks number randomly between 0 & 3
        Random rand = new Random();
        int randomNumber = rand.nextInt(4);
        return randomNumber;
    } // end of pickRandomNumber method
} // end of Game class

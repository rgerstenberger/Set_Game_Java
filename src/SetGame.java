import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

//Rob Gerstenberger


public class SetGame extends GraphicsProgram {

	//We are going to need these soon
	private RandomGenerator rGen = new RandomGenerator();
	private static final int ROWS = 3;
	private static final int COLUMNS = 2;
	private static final int CARDWIDTH = 120;
	private static final int CARDHEIGHT = 90;
	public static final int APPLICATION_WIDTH = CARDWIDTH * (COLUMNS + 1);
	public static final int APPLICATION_HEIGHT = CARDHEIGHT * (ROWS + 1);
	private static final int TOTALCARDS = ROWS * COLUMNS;
	
	private GLabel gameLabel = new GLabel("Score: 0", 20, CARDHEIGHT * (ROWS + 0.5));

	private int score = 0;
	private Card[] cardsClicked = new Card[3];
	private int counter = 0;
	private Color[] backColors = {Color.CYAN, Color.YELLOW, Color.PINK};
	

	
   //Create an array of cards
	private Card[] cardArray = new Card[TOTALCARDS];
	//all logic and creating cards is in run but could be split out to other methods
	//that would require a private class variable for the card array and does not necessarily add value with this little code
	
	//run, run, as fast as you can...
	public void run() 
	{
		
		add(gameLabel);
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		//In a for loop, loop through and create each card.  You will be passing in random
		//numbers between 1 and 3 for each of the parameters to the constructor
		
		int arrayIndex = 0;
		for(int row = 0; row < ROWS; row++){
			for(int col = 0; col < COLUMNS; col++)
			{
				//could have done this all within the constructor but this adds readability 
				int color = rGen.nextInt(1, 3);
				int shape = rGen.nextInt(1, 3);
				int shading = rGen.nextInt(1, 3);
				int num = rGen.nextInt(1, 3);
				double xLoc = col * (CARDWIDTH + 10) + 5;
				double yLoc = row * (CARDHEIGHT + 10) + 5;
				int x = (color * 3) + (shape * 9) + (shading * 27) + num - 39;
				String image = "images/" + x + ".gif";
				
				GImage myImage = new GImage(image);
				
				cardArray[arrayIndex] = new Card(color, shape, shading, num, xLoc, yLoc, myImage, CARDWIDTH, CARDHEIGHT, arrayIndex);
				add(cardArray[arrayIndex]);
				arrayIndex++;
			}
		}
		
		anyMatches();
		//Then loop through and call toString() on each Card.
		//not dependent on numCards just in case 
		//for(int row = 0; row < ROWS; row++){
			//for(int col = 0; col < COLUMNS; col++)
			//{
				//String s = cardArray[row][col].toString();
				//eventually this will be graphical representations instead of text
				//GLabel cardLabel = new GLabel(s, cardArray[row][col].getXLoc(), cardArray[row][col].getYLoc());
				//add(cardLabel);
				
				
			//}
		//}
		
		addMouseListeners();
		
	}//can't catch me I'm the gingerbread man!
	
	
	public void mouseClicked(MouseEvent e)
	{

		GObject maybeCard = getElementAt(e.getX(), e.getY());
		remove(gameLabel);
		gameLabel = new GLabel("Score: " + score, 20, CARDHEIGHT * (ROWS + 0.5));
		add(gameLabel);
		
		if(maybeCard == null) return;
		
		//check if card clicked is same as one already clicked
		for(int i = 0; i < 3; i++)
		{
			if(cardsClicked[i] == (Card)maybeCard) return;
		}
		
		//add card to card clicked array and highlight it, increment counter immediately
		cardsClicked[counter] = (Card)maybeCard;
		cardsClicked[counter].setBackgroundColor(backColors[counter]);
		counter = counter + 1;
		
		
		
		if(counter == 3)
		{			
			//check for match
			endTurn(checkForMatch(cardsClicked[0], cardsClicked[1], cardsClicked[2]));
		}
		
		
		return;
	}
	
	
	private void anyMatches()
	{
		//card1 increments on i rows and x columns
		//card 2 increments on j rows and y columns
		//card 3 increments on k rows and z columns
		
		
		for(int i = 0; i < TOTALCARDS; i++)
		{
			for(int j = 0; j < TOTALCARDS; j++)
			{
				for(int k = 0; k < TOTALCARDS; k++)
				{
					
					if(checkForMatch(cardArray[i], cardArray[j], cardArray[k]) && j != k && j != i && i != k)
					{
						println("at least one match");
						return;
					}
				}
			}
		}//end massive for loop
		
		println("no matches");
		System.exit(1);
		return;
	}
	
	private void endTurn(Boolean match)
	{
		//reset needed variables and replace cards
		counter = 0;
		//if match replace cards clicked with new ones
		if(match){
			//match
			println("Match!");
			for(int i = 0; i < cardsClicked.length; i++)
			{
				double newCardX = cardsClicked[i].getXLoc();
				double newCardY= cardsClicked[i].getYLoc();
				int color = rGen.nextInt(1, 3);
				int shape = rGen.nextInt(1, 3);
				int shading = rGen.nextInt(1, 3);
				int num = rGen.nextInt(1, 3);
				int index = cardsClicked[i].getIndex();
				int x = (color * 3) + (shape * 9) + (shading * 27) + num - 39;
				String image = "images/" + x + ".gif";
				
				GImage myImage = new GImage(image);
				
				cardArray[index] = new Card(color, shape, shading, num, newCardX, newCardY, myImage, CARDWIDTH, CARDHEIGHT, index);
				remove(cardsClicked[i]);
				cardsClicked[i] = null;
				add(cardArray[index]);
				
			}//end for
			score = score + 1;
			remove(gameLabel);
			gameLabel = new GLabel("Score: " + score + " Match!!!", 20, CARDHEIGHT * (ROWS + 0.5));
			add(gameLabel);
			
			anyMatches();
			
			return;
		} else {
			//not a match
			println("NOT A MATCH!!!!!");
			for(int i = 0; i < cardsClicked.length; i++)
			{
				cardsClicked[i].removeBorder();
				cardsClicked[i] = null;
			}//end for
			
			//Show Dialog
			remove(gameLabel);
			gameLabel = new GLabel("Score: " + score + " Not A Match!", 20, CARDHEIGHT * (ROWS + 0.5));
			add(gameLabel);
			return;
			
			
			
		}//end if
	}
	
	//check for match
	private Boolean checkForMatch(Card cardOne, Card cardTwo, Card cardThree)
	{
		
		int num = cardOne.getNum() + cardTwo.getNum() + cardThree.getNum();
		int color = cardOne.getTheColor() + cardTwo.getTheColor() + cardThree.getTheColor();
		int shape = cardOne.getShape() + cardTwo.getShape() + cardThree.getShape();
		int shading = cardOne.getShading() + cardTwo.getShading() + cardThree.getShading();
		
		int x = (num % 3) + (color % 3) + (shape % 3) + (shading % 3);
		
		if(x == 0) 
		{
			return true;
		}
		
		
		return false;
	}
}

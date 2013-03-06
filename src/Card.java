import java.awt.Color;
import java.awt.Image;

import acm.graphics.*;


public class Card extends GCompound{

	private int shape, shading, color, num;
	private double xLoc, yLoc;
	//private String cardImage;
	private GImage image;
	private double width;
	private double height;
	private int index;
	private static final double IMAGEWIDTH = 95;
	private static final double IMAGEHEIGHT = 62;
	private GRect border;
	//public constructor
	public Card(int color, int shape, int shading, int num, double xLoc, double yLoc, GImage cardImage, double width, double height, int index)
	{
		
		this.color = color;
		this.shape = shape;
		this.shading = shading;
		this.num = num;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.image = cardImage;
		this.width = width;
		this.height = height;
		this.index = index;
		
		
		setLocation(xLoc, yLoc);
		displayImage();
	}
	
	//getters and setters for private attributes
	public int getTheColor()
    {
        return color;
    }
	 
    public void setColor(int color)
    {
        this.color = color;
    }
   
    public int getShape()
    {
        return shape;
    }
    
    public void setShape(int shape)
    {
        this.shape = shape;
    }
   
    public int getShading()
    {
        return shading;
    }
    
    public void setShading(int shading)
    {
        this.shading = shading;
    }
   
    public int getNum()
    {
        return num;
    }
    
    public void setNum(int num)
    {
        this.num = num;
    }
    
    public double getXLoc()
    {
    	return xLoc;
    }
    
    public double getYLoc()
    {
    	return yLoc;
    }
    
    public int getIndex()
    {
    	return index;
    }
    
    
    private void displayImage()
    {
    	double imageX = (width - IMAGEWIDTH) / 2;
    	double imageY = (height - IMAGEHEIGHT) / 2;
    	image.setBounds(imageX, imageY, IMAGEWIDTH, IMAGEHEIGHT);
    	image.sendToFront();
    	add(image);
    }
    
    //Getters that return the corresponding string for their attribute
    private String getColorString()
    {
    	String colorString = "";
    	
    	switch(getTheColor())
    	{
    		case 1:
    			colorString = "Green";
    			break;
    		case 2:
    			colorString = "Red";
    			break;
    		case 3:
    			colorString = "Purple";
    			break;
    		//Should never hit default but if it does it will show this
    		default:
    			colorString = "";
    			break;
    	}
    	
    	return colorString;
    }
    
    private String getShapeString()
    {
    	String shapeString = "";
    	
    	switch(getShape())
    	{
    		case 1:
    			shapeString = "Diamond";
    			break;
    		case 2:
    			shapeString = "Squiggle";
    			break;
    		case 3:
    			shapeString = "Oval";
    			break;
    		//Should never hit default but if it does it will show this
    		default:
    			shapeString = "";
    			break;
    	}
    	
    	return shapeString;
    }
    
    private String getShadingString()
    {
    	String shadingString = "";
    	
    	switch(getShading())
    	{
    		case 1:
    			shadingString = "Solid";
    			break;
    		case 2:
    			shadingString = "Open";
    			break;
    		case 3:
    			shadingString = "Striped";
    			break;
    		//Should never hit default but if it does it will show this
    		default:
    			shadingString = "";
    			break;
    	}
    	
    	return shadingString;
    }
    
    public void setBackgroundColor(Color color)
    {
    	border = new GRect(5, 5, width - 10, height - 10);
    	border.setColor(color);
    	border.setFilled(true);
    	add(border);
    	border.sendToBack();
    }
       
    //not a match, reset
    public void removeBorder()
    {
    	remove(border);
    }
    
    //use getters to return a string the has all necessary information about the card
    public String toString()
    {
    	String s = "";
    	
    	s += getNum() + " ";
    	s += getShadingString() + " ";
    	s += getColorString() + " ";
    	s += getShapeString() + "(s)";
    	
    	return s;
    }
    

}

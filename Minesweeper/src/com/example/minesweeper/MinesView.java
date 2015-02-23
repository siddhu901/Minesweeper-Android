//  Author: Siddhartha Kakarla
//  Course Number: CSE 651
//  HOMEWORK-05

//  MinesView.java
//  Minesweeper
//
//  Created by Siddhartha Kakarla on 4/3/14.
//  Copyright (c) 2014 Siddhartha Kakarla. All rights reserved.
//

package com.example.minesweeper;

import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MinesView extends View 
{	
	float width,height;
	private final MainActivity myActivity;
	int selX,selY;
	GestureDetector mylistener;
	private final Rect selRect = new Rect();
	private int tempcount;
	private int flagcount;
	private String gamestatus="won";
	private int gamecount=0;
	private boolean bombClicked=false;
	static int Matrix[][]=new int[16][16];
	private int FlagMatrix[][]=new int[16][16];
	private int xarray[]=new int[50];
	private int yarray[]=new int[50];
	private boolean doubletap;
	private boolean singletap;
	private boolean draw;
	private String mines;
	private int mcount;
	
	//Constructor
	public MinesView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		mylistener = new GestureDetector(context,new GestureListener());
		// TODO Auto-generated constructor stub
		myActivity  = (MainActivity) context;
		initializeMatrix();
	}
	
	
	//To initiaze all the glabal variables
	public void initializeMatrix()
	{
	    int count=0;
	    tempcount=0;
	    flagcount=0;
	    gamecount=0;
	    gamestatus="won";
	    bombClicked=false;
	    this.Matrix= new int[16][16];
		FlagMatrix= new int[16][16];
	    Random r = new  Random();
	    for(int i=0;i <16;i++)
	    {
	        for(int p=0;p<16;p++)
	        {
	            this.Matrix[i][p]=-10;
	            FlagMatrix[i][p]=-1;
	        }
	    
	    }
	    
	    for (int it=0; it<50; it++)
	    {
	        xarray[it]=-1;
	        yarray[it]=-1;
	    }
	    
	    //Randomize the mines location
	    for(int j=0;j<50;j++)
	    {
	          int randx= r.nextInt(16);
	          int randy= r.nextInt(16);
	          for(int k=0;k<j;k++)
	          {
	                while(randx==xarray[k] && randy==yarray[k])
	                {
	                    randx= r.nextInt(16);
	                    randy= r.nextInt(16);
	                    break;
	                }
	                
	          }
	         
	           xarray[j]=randx;
	           yarray[j]=randy;
	           this.Matrix[randx][randy]= -9;
	           count++;
	     }
	    
	    mcount=count;
	    doubletap=false;
	    gamestatus="won";
	    
	}


	   protected void onDraw(Canvas canvas) 
	   {
	      // Draw the background...
	      Paint background = new Paint();
	      background.setColor(Color.WHITE);
	      canvas.drawRect(0, 0, getWidth(), getHeight(), background);
	      // Draw the board...
	      // Define colors for the grid lines
	      
	      width= getWidth()/16;
	      height= getHeight()/16;
	      
	      Paint dark = new Paint();
	      dark.setColor(Color.BLACK);
	      
	      Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
	      foreground.setTextSize(height * 0.75f);
	      foreground.setTextAlign(Paint.Align.CENTER);
	      FontMetrics fm = foreground.getFontMetrics();
	      
	      float x = width / 2;
	      // Centering in Y: measure ascent/descent first
	      float y = height / 2 - (fm.ascent + fm.descent) / 2;
	      
	     // To draw the major grid lines
	      for (int i = 0; i < 16; i++) 
	      {
	         canvas.drawLine(0, i * height, getWidth(), i * height,dark);
	         canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
	      }
	      
	      
	      if(bombClicked==true)
	      {
	           for(int it1=0;it1<16;it1++)
	           {
	               for(int it2=0;it2<16;it2++)
	               {
	                      if(Matrix[it1][it2]==-5)
	                      {
	                    	  Bitmap bitm = BitmapFactory.decodeResource(getResources(), R.drawable.sadbomb);
		                	  bitm=Bitmap.createScaledBitmap(bitm, (int)(width),(int)(height),false);
		                	  canvas.drawBitmap(bitm,(it1*width),(it2*height),null);
	                      }
	                   
	                      else if(Matrix[it1][it2]==-9)
	                      {
	                    	  Bitmap bitm = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
		                	  bitm=Bitmap.createScaledBitmap(bitm, (int)(width),(int)(height),false);
		                	  canvas.drawBitmap(bitm,(it1*width),(it2*height),null);
	           
	                      }
	               }
	               
	           }

	          
	           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
	            // set dialog message
	           
	            alertDialogBuilder.setMessage("Sorry, you lost!!!!").setCancelable(true).setPositiveButton("New Game",new DialogInterface.OnClickListener()
	                {
	            	
	                    public void onClick(DialogInterface dialog,int id) 
	                    {
	                    	/*initializeAgain();
	                    	invalidate();*/
	                    	initializeMatrix();
	                        invalidate();

	                    }
	                  });
	                AlertDialog alertDialog = alertDialogBuilder.create();
	                alertDialog.show();
	      }
	      
	      for(int it1=0;it1<16;it1++)
	      {
	          for(int it2=0;it2<16;it2++)
	          {
	              
	             /* if(Matrix[it1][it2]==-9)
	              {
	                  
	            	  Bitmap bitm = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
                	  bitm=Bitmap.createScaledBitmap(bitm, (int)(width),(int)(height),false);
                	  canvas.drawBitmap(bitm,(it1*width),(it2*height),null);
	                  
	              }*/

	              if(FlagMatrix[it1][it2]==-7)
	              {
	            	  
	            	  Bitmap bitm = BitmapFactory.decodeResource(getResources(), R.drawable.flagicon);
                	  bitm=Bitmap.createScaledBitmap(bitm, (int)(width),(int)(height),false);
                	  canvas.drawBitmap(bitm,(it1*width),(it2*height),null);
	              }
	              
	              if(Matrix[it1][it2]>0)
	              {
	            	  canvas.drawText(String.valueOf(Matrix[it1][it2]) , it1* width + x, it2 * height + y, foreground);
	                  
	              }
	              
	          }
	      }
	      
	      
	      
	      if(flagcount==50)
	      {
	          for(int it1=0;it1<16;it1++)
	          {
	              for(int it2=0;it2<16;it2++)
	              {
	                  if(Matrix[it1][it2]==-9)
	                  {
	                      if(FlagMatrix[it1][it2]!=-7)
	                      {
	                          gamestatus="Not won";
	                      }
	                      else
	                          gamestatus="Flagwon";
	                      
	                  }
	                  
	              }
	              
	          }

	          if(gamestatus.equals("Flagwon")) //isEqualToString:@"Flagwon"])
	          {
	              for(int it1=0;it1<16;it1++)
	              {
	                  for(int it2=0;it2<16;it2++)
	                  {
	                      if(Matrix[it1][it2]==-9)
	                      {
	                    	  Bitmap bitm = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
	                    	  bitm=Bitmap.createScaledBitmap(bitm, (int)(width),(int)(height),false);
	                    	  canvas.drawBitmap(bitm,(it1*width),(it2*height),null);
	                      }
	                  }
	              }

	            
	              AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
		            // set dialog message
		            alertDialogBuilder.setMessage("Congratulations, You won!!!!").setCancelable(true).setPositiveButton("New Game",new DialogInterface.OnClickListener()
		                {
		                    public void onClick(DialogInterface dialog,int id) 
		                    {
		                    	/*initializeAgain();
		                    	invalidate();*/
		                    	initializeMatrix();
		                        invalidate();

		                    }
		                  });
		                AlertDialog alertDialog = alertDialogBuilder.create();
		                alertDialog.show();
	              
	          }
	      }
	      
	      
	      
	      if(doubletap==true)
	      {
	          for(int it1=0;it1<16;it1++)
	          {
	              for(int it2=0;it2<16;it2++)
	              {
	                  if(Matrix[it1][it2]==-1)
	                  {
	                	  Bitmap bitm = BitmapFactory.decodeResource(getResources(), R.drawable.blankbutton);
                    	  bitm=Bitmap.createScaledBitmap(bitm, (int)(width),(int)(height),false);
                    	  canvas.drawBitmap(bitm,(it1*width),(it2*height),null);
	                  }
	                  
	                  if(Matrix[it1][it2]>0)
	                  {
	                	  canvas.drawText(String.valueOf(Matrix[it1][it2]) , it1* width + x, it2 * height + y, foreground);
	                  }
	               
	                  
	              }
	          }

	      }
	      
	      
	      for(int it1=0;it1<16;it1++)
	      {
	          for(int it2=0;it2<16;it2++)
	          {
	              if(Matrix[it1][it2]==-1 || Matrix[it1][it2]>0 || Matrix[it1][it2]==-9)
	              {
	                  tempcount++;//wingame=true;
	              }
	                  
	          }
	      }
	      
	      if(tempcount==256)
	      {
	          
	          for(int it1=0;it1<16;it1++)
	          {
	              for(int it2=0;it2<16;it2++)
	              {
	                 if(Matrix[it1][it2]==-9)
	                 {
	                	 Bitmap bitm = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
                   	  	bitm=Bitmap.createScaledBitmap(bitm, (int)(width),(int)(height),false);
                   	  	canvas.drawBitmap(bitm,(it1*width),(it2*height),null);
	                     
	                 }
	              }
	          }
	          
	         
	          AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
	            // set dialog message
	            alertDialogBuilder.setMessage("Congratulations, You won!! ! !").setCancelable(true).setPositiveButton("New Game",new DialogInterface.OnClickListener()
	                {
	                    public void onClick(DialogInterface dialog,int id) 
	                    {
	                    	/*initializeAgain();
	                    	invalidate();*/
	                    	initializeMatrix();
	                        invalidate();

	                    }
	                  });
	                AlertDialog alertDialog = alertDialogBuilder.create();
	                alertDialog.show();
            
	      }
	      
	   }
		
	   
	   @Override
		public boolean onTouchEvent(MotionEvent event)
		{
		   System.out.println("on touch event");
		     return mylistener.onTouchEvent(event);
		}
		
		
		   private void select(int x, int y) 
		   {
			      selX = Math.min(Math.max(x, 0), 8);
			      selY = Math.min(Math.max(y, 0), 8);
			      getRect(selX, selY, selRect);
		   }
		   
		   
		   private void getRect(int x, int y, Rect rect) 
		   {
			   rect.set((int) (x * width), (int) (y * height), (int) (x* width + width), (int) (y * height + height));
		   }
		   
		   //To trace user gestures
		   private class GestureListener extends GestureDetector.SimpleOnGestureListener 
		   {
				
				@Override
				public boolean onDown(MotionEvent e)
				{
					return true;
				}
				
				   @Override
				   public boolean onSingleTapConfirmed(MotionEvent e)
					{
					   
					   	int x = (int) (e.getX() / width);
						int y = (int) (e.getY() / height);
						
						 if(FlagMatrix[x][y]==-7)
					        {
					            FlagMatrix[x][y]=-8;
					            flagcount--;
					        }
					        else
					        {
					            if(Matrix[x][y]<0)
					            {
					                FlagMatrix[x][y]=-7;
					                flagcount++;
					            }
					        }
					        singletap=true;
					       invalidate();
					   return true;
					}
				   
				   
				   @Override
					public boolean onDoubleTapEvent(MotionEvent e)
					{
					   	int x = (int) (e.getX() / width);
						int y = (int) (e.getY() / height);
						
						if ( Matrix[x][y]==-9 || MinesView.Matrix[x][y]==-5)
				        {
							Matrix[x][y]=-5;
				            bombClicked=true;
				            invalidate();
				        }
				        else
				        {
				           // findMines(x,y);
				        	findMines(x,y);
				        	invalidate();
				            //[self find:self.row Mines:self.col];
				        }
						doubletap=true;
				    
						return true;
					}
					
				   //recursion
				   void findMines(int r,int c)
					{
					    int count1=0;
					    for(int i=r-1;i<= r+1;i++)
					    {
					        for(int j=c-1; j<= c+1;j++)
					        {
					            if((i==r && j==c))
					            {
					                continue;
					            }
					            if((i<0)||(i>15))
					            {
					                continue;
					            }
					            if((j<0)||(j>15))
					            {
					                continue;
					            }
					            else
					            {
					                if(Matrix[i][j]==-9)
					                {
					                    count1++;
					                }
					            }
					        }
					    }
					    
					    
					    if(count1 == 0)
					    {
					        Matrix[r][c] = -1;
					        if(r-1>=0 && Matrix [r-1][c] !=-1 && Matrix[r-1][c] !=-7)
					        {
					        	findMines(r-1,c);
					           
					            if(c+1 <=15 && Matrix[r-1][c+1] !=-1 && Matrix[r-1][c+1] !=-7)
					            {
					            	findMines(r-1,c+1);
					            	
					            }
					        }
					        if(c-1>=0 && Matrix[r][c-1] !=-1 && Matrix[r][c-1] !=-7)
					        {
					        	findMines(r,c-1);

					            if(r+1 <=15 && Matrix[r+1][c-1] !=-1 && Matrix[r+1][c-1] !=-7 )
					            { 
					            	findMines(r+1,c-1);

			
					            }
					        }
					        if(r-1>=0 && c-1 >=0 && Matrix[r-1][c-1] !=-1 && Matrix[r-1][c-1] !=-7)
					        {
					        	findMines(r-1,c-1);
	
					        }
					        if(c+1 <=15 && Matrix[r][c+1] !=-1 && Matrix[r][c+1] !=-7)
					        {
					        	findMines(r,c+1);

					        }
					        if(r+1 <=15 && c+1 <=15 && Matrix[r+1][c+1] !=-1 && Matrix[r+1][c+1] !=-7)
					        {
					        	findMines(r+1,c+1);

					        }
					        if(r+1 <=15 && Matrix[r+1][c] !=-1 && Matrix[r+1][c] !=-7)
					        {
					        	findMines(r+1,c);

					        }
					    }
					    else
					    {		        
					        Matrix[r][c]= count1;
					    }
					}
				   
		   }
		   		

}


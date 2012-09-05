import java.awt.*;

class GSchedCanvas extends Canvas
{
  // Some constants for defining the grid
  static final int daysInWeek = 7;        // Just in case this ever changes for some reason (ex: no more work on Sunday)
  static final int timeSlots = 30;        // Total time slots in a day
  static final int sunStarts = 6;         // which time slot sunday starts on
  static final int leftLabelSpace = 75;   // The space reserved on the left for labels
  static final int topLabelSpace = 15;    // The space reserved on the top for labels
  static final int xFluff = 2;            // The space between each grid block on the X axis
  static final int yFluff = 2;            // The space between each grid block on the Y axis
  
  int totalSlots;
  int xGrid;                              // Size of a block on X axis
  int yGrid;                              // Size of a block on Y axis
  int[] gridX1, gridY1, gridX2, gridY2;   // These store mouse-over data for each block
  boolean[] selected;                     // These store whether or not the timeSlot is selected
  boolean[] selectable;                   // These store whether or not the timeSlot can be selected
  int dailySelected[];
  
  boolean dragging;                       // In the act of dragging
  int dragStart, dragStop;                // These track highlight grid selections
   
  public GSchedCanvas()
  {
    totalSlots = daysInWeek * timeSlots;
  
    gridX1 = new int[totalSlots];
    gridY1 = new int[totalSlots];
    gridX2 = new int[totalSlots];
    gridY2 = new int[totalSlots];
    
    selected   = new boolean[totalSlots];
    selectable = new boolean[totalSlots];
    
    dailySelected = new int[totalSlots];
  }

  public void paint( Graphics g )
  {
    drawGrid( g );
  
    // Needless to say this is just for debugging
    // testMouseOverData( g );
  }
  
  private void drawGrid( Graphics g )
  {
    Dimension gridSize = this.getSize();
  
    int xSpace = gridSize.width - leftLabelSpace - (xFluff * daysInWeek);
    int ySpace = gridSize.height - topLabelSpace - (yFluff * timeSlots);
    xGrid = (int)(xSpace / daysInWeek);
    yGrid = (int)(ySpace / timeSlots);
    int curX = leftLabelSpace;
    int curY = topLabelSpace;
    
    // Everything (will need to fix Sunday).
    curX = leftLabelSpace;
    curY = topLabelSpace;
    g.setColor( new Color( 0, 0, 0 ) );
    for( int x = 0; x < daysInWeek; x++ )
    {
      dailySelected[x] = 0;
      for( int y = 0; y < timeSlots; y++ )
      {
        // Draw the box
        if ( selected[(x * timeSlots) + y] == false )
        {
          g.setColor( new Color( 255, 255, 255 ) );
          g.fillRect( curX, curY, xGrid, yGrid );
          g.setColor( new Color( 0, 0, 0 ) );
          g.drawRect( curX, curY, xGrid, yGrid );
        }  
        else
        {
          g.setColor( new Color( 255, 0, 0 ) );
          g.fillRect( curX, curY, xGrid, yGrid );
          g.setColor( new Color( 0, 0, 0 ) );
          g.drawRect( curX, curY, xGrid, yGrid );
          dailySelected[x]++;
        } 
               
        // Save mouseOver Data
        gridX1[(x * timeSlots) + y] = curX;
        gridY1[(x * timeSlots) + y] = curY;
        gridX2[(x * timeSlots) + y] = curX + xGrid;
        gridY2[(x * timeSlots) + y] = curY + yGrid;
        selectable[(x * timeSlots) + y] = true;

        // Move to the next row
        curY += (yGrid + yFluff);
      }
      // Move to the next column
      curY = topLabelSpace;
      curX += (xGrid + xFluff);
    }
    
    // Adjust Sunday
    curX = leftLabelSpace;
    curY = topLabelSpace;
    g.setColor( new Color( 255, 255, 255 ) );
    for( int y = 0; y < sunStarts; y++ )
    {
      g.drawRect( curX, curY, xGrid, yGrid );
      
      selectable[y] = false;
       
      curY += (yGrid + yFluff);
    }
    
    // Add Day Labels to the grid
    g.setColor( new Color( 0, 0, 0 ) );
    g.drawString( "Sun", leftLabelSpace + (int)(xGrid * 0.5), topLabelSpace - yFluff );    
    g.drawString( "Mon", leftLabelSpace + (int)(xGrid * 1.5), topLabelSpace - yFluff );    
    g.drawString( "Tue", leftLabelSpace + (int)(xGrid * 2.5), topLabelSpace - yFluff );    
    g.drawString( "Wed", leftLabelSpace + (int)(xGrid * 3.5), topLabelSpace - yFluff );    
    g.drawString( "Thr", leftLabelSpace + (int)(xGrid * 4.5), topLabelSpace - yFluff );    
    g.drawString( "Fri", leftLabelSpace + (int)(xGrid * 5.5), topLabelSpace - yFluff );    
    g.drawString( "Sat", leftLabelSpace + (int)(xGrid * 6.5), topLabelSpace - yFluff );    
    
    // Add Time Labels to the grid
    g.setColor( new Color( 0, 0, 0 ) );
    g.drawString( "0800 to 0830", 0, topLabelSpace + (int)(yGrid * 1) );
    g.drawString( "0830 to 0900", 0, topLabelSpace + (int)(yGrid * 2) + (yFluff * 1) );
    g.drawString( "0900 to 0930", 0, topLabelSpace + (int)(yGrid * 3) + (yFluff * 2) );
    g.drawString( "0930 to 1000", 0, topLabelSpace + (int)(yGrid * 4) + (yFluff * 3) );
    g.drawString( "1000 to 1030", 0, topLabelSpace + (int)(yGrid * 5) + (yFluff * 4) );
    
    g.drawString( "1030 to 1100", 0, topLabelSpace + (int)(yGrid * 6) + (yFluff * 5) );
    g.drawString( "1100 to 1130", 0, topLabelSpace + (int)(yGrid * 7) + (yFluff * 6) );
    g.drawString( "1130 to 1200", 0, topLabelSpace + (int)(yGrid * 8) + (yFluff * 7) );
    g.drawString( "1200 to 1230", 0, topLabelSpace + (int)(yGrid * 9) + (yFluff * 8) );
    g.drawString( "1230 to 1300", 0, topLabelSpace + (int)(yGrid * 10) + (yFluff * 9) );
      
    g.drawString( "1300 to 1330", 0, topLabelSpace + (int)(yGrid * 11) + (yFluff * 10) );
    g.drawString( "1330 to 1400", 0, topLabelSpace + (int)(yGrid * 12) + (yFluff * 11) );
    g.drawString( "1400 to 1430", 0, topLabelSpace + (int)(yGrid * 13) + (yFluff * 12) );
    g.drawString( "1430 to 1500", 0, topLabelSpace + (int)(yGrid * 14) + (yFluff * 13) );
    g.drawString( "1500 to 1530", 0, topLabelSpace + (int)(yGrid * 15) + (yFluff * 14) );
      
    g.drawString( "1530 to 1600", 0, topLabelSpace + (int)(yGrid * 16) + (yFluff * 15) );
    g.drawString( "1600 to 1630", 0, topLabelSpace + (int)(yGrid * 17) + (yFluff * 16) );
    g.drawString( "1630 to 1700", 0, topLabelSpace + (int)(yGrid * 18) + (yFluff * 17) );
    g.drawString( "1700 to 1730", 0, topLabelSpace + (int)(yGrid * 19) + (yFluff * 18) );
    g.drawString( "1730 to 1800", 0, topLabelSpace + (int)(yGrid * 20) + (yFluff * 19) );
      
    g.drawString( "1800 to 1830", 0, topLabelSpace + (int)(yGrid * 21) + (yFluff * 20) );
    g.drawString( "1830 to 1900", 0, topLabelSpace + (int)(yGrid * 22) + (yFluff * 21) );
    g.drawString( "1900 to 1930", 0, topLabelSpace + (int)(yGrid * 23) + (yFluff * 22) );
    g.drawString( "1930 to 2000", 0, topLabelSpace + (int)(yGrid * 24) + (yFluff * 23) );
    g.drawString( "2000 to 2030", 0, topLabelSpace + (int)(yGrid * 25) + (yFluff * 24) );
      
    g.drawString( "2030 to 2100", 0, topLabelSpace + (int)(yGrid * 26) + (yFluff * 25) );
    g.drawString( "2100 to 2130", 0, topLabelSpace + (int)(yGrid * 27) + (yFluff * 26) );
    g.drawString( "2130 to 2200", 0, topLabelSpace + (int)(yGrid * 28) + (yFluff * 27) );
    g.drawString( "2200 to 2230", 0, topLabelSpace + (int)(yGrid * 29) + (yFluff * 28) );
    g.drawString( "2230 to 2300", 0, topLabelSpace + (int)(yGrid * 30) + (yFluff * 29) );
  }
    
  // Debug ------------------------------------
  private void testMouseOverData( Graphics g )
  {
    for( int i = 0; i < totalSlots; i++ )
    {
      if ( selectable[i] == true )
      {
        g.setColor( new Color( 0, 255, 0 ) );
        g.drawRect( gridX1[i], gridY1[i], xGrid, yGrid );
      }
      else
      {
        g.setColor( new Color( 255, 0, 0 ) );
        g.drawRect( gridX1[i], gridY1[i], xGrid, yGrid );
      }
    }
  }
  // End Debug --------------------------------
  
  public void startSelection( int inX, int inY )
  {
    Graphics g = this.getGraphics();
    
    int i = getGrid( inX, inY );
    if ( i != -1 && selectable[i] == true )
    {
      dragStart = i;
      dragging = true;
    
      g.setColor( new Color( 0, 0, 255 ) );
      g.drawRect( gridX1[i], gridY1[i], xGrid, yGrid );
    }
    else
    {
      dragging = false;
    }
  }
  
  public void stopSelection( int inX, int inY )
  {    
    Graphics g = this.getGraphics();
    
    int i = getGrid( inX, inY );
    if ( i != -1 && dragging == true && selectable[i] == true )
    {
      dragStop = i;
      dragging = false;
    }  
    else
    {
      g.setColor( new Color( 0, 0, 0 ) );
      g.drawRect( gridX1[dragStart], gridY1[dragStart], xGrid, yGrid );
    
      dragging = false;
      return;
    }  
      
    // adjust the dragStop to put it in the same column if needed
    if( (int)(dragStart / timeSlots) % daysInWeek < (int)(dragStop / timeSlots) % daysInWeek )
      dragStop -= (timeSlots * ((int)(dragStop / timeSlots) % daysInWeek - (int)(dragStart / timeSlots) % daysInWeek));
    else if( (int)(dragStart / timeSlots) % daysInWeek > (int)(dragStop / timeSlots) % daysInWeek )
      dragStop += (timeSlots * ((int)(dragStart / timeSlots) % daysInWeek - (int)(dragStop / timeSlots) % daysInWeek));
  
    
    // swap if dragStop < dragStart
    if ( dragStop < dragStart )
    {
      i = dragStop;
      dragStop = dragStart;
      dragStart = i;
    }
        
    // Mass toggle
    for( i = dragStart; i <= dragStop; i++ )
    {
      if ( selected[i] == true )
      {
        selected[i] = false;
         
        g.setColor( new Color( 255, 255, 255 ) );
        g.fillRect( gridX1[i], gridY1[i], xGrid, yGrid );
        g.setColor( new Color( 0, 0, 0 ) );
        g.drawRect( gridX1[i], gridY1[i], xGrid, yGrid );
      }  
      else
      {
        selected[i] = true;
        
        g.setColor( new Color( 255, 0, 0 ) );
        g.fillRect( gridX1[i], gridY1[i], xGrid, yGrid );
        g.setColor( new Color( 0, 0, 0 ) );
        g.drawRect( gridX1[i], gridY1[i], xGrid, yGrid );
      }  
    }
  }
  
  public void setSelection( int inGrid, boolean inSet )
  {
    if ( selectable[inGrid] == true )
    {
      selected[inGrid] = inSet;
    }
  }
  
  public boolean getSelection( int inGrid )
  {
    return selected[inGrid];
  }
  
  private int getGrid( int inX, int inY )
  {
    // mouseOver bounds check to determine gridSlot
    for( int i = 0; i < totalSlots; i++ )
    {
      if ( (inX > gridX1[i] && inX < gridX2[i]) && (inY > gridY1[i] && inY < gridY2[i]) )
        return i;
    }
    return -1;
  }
}
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import BatSQL;
import BatWin;

public class GraphicSched
{
  Frame        mainFrame;
  Panel        mainPanel;
  Panel        topPanel;
  Panel        topRightPanel;
  Panel        botPanel;
  Panel        lowerBotPanel;
  Label        curAide;
  Choice       aideChoice;
  Button       updateButton;
  Button       revertButton;
  Button       exitButton;
  Button       helpButton;
  GSchedCanvas mainGrid;
  boolean      aideSelected;
  Label        loadingLabel;
   
  public GraphicSched()
  {
    mainFrame     = new Frame( "Graphical Schedule Maker" );
    mainPanel     = new Panel( new BorderLayout() );
    topPanel      = new Panel( new GridLayout( 1, 2 ) );
    topRightPanel = new Panel( new FlowLayout() );
    botPanel      = new Panel( new GridLayout( 2, 1, 0, 0 ) );
    lowerBotPanel = new Panel( new FlowLayout() );
    curAide       = new Label( "None                         " );
    loadingLabel  = new Label( "       " );
    aideChoice    = new Choice();
    updateButton  = new Button( "Update" );
    revertButton  = new Button( "Revert" );
    exitButton    = new Button( "Exit" );
    helpButton    = new Button( "Help" );
    mainGrid      = new GSchedCanvas();
    aideSelected  = false;
         
    // This makes the X in the corner work for closing the window
    mainFrame.addWindowListener(new WindowAdapter() 
    {
      public void windowClosing(WindowEvent e)
      {
        mainFrame.dispose();
      }
    });
    
    // This makes the aideChoice function properly
    aideChoice.addItemListener(new ItemListener() 
    {
      public void itemStateChanged( ItemEvent e )
      {
        if( aideChoice.getSelectedIndex() != 0 )
        {
          curAide.setText( aideChoice.getSelectedItem().substring(7) );
          aideSelected = true;
          setCurrentSchedule( aideChoice.getSelectedItem().substring(0,4) );
          mainGrid.repaint();
        }  
        else 
        {
          curAide.setText( "None" );
          aideSelected = false;
        }
      }
    });
    
    // This makes the updateButton function properly
    updateButton.addActionListener(new ActionListener() 
    {
      public void actionPerformed( ActionEvent e )
      {
        if (aideChoice.getSelectedIndex() != 0)
        {
          saveCurrentSchedule( aideChoice.getSelectedItem().substring(0,4) );
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
          	"You must select an aide first!");
        }
      }
    });
    
    // This makes the updateButton function properly
    revertButton.addActionListener(new ActionListener() 
    {
      public void actionPerformed( ActionEvent e )
      {
        if (aideChoice.getSelectedIndex() != 0)
        {
          setCurrentSchedule( aideChoice.getSelectedItem().substring(0,4) );
          mainGrid.repaint();
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
          	"You must select an aide first!");
        }
      }
    });


    // This makes the helpButton function properly
    helpButton.addActionListener(new ActionListener() 
    {
      public void actionPerformed( ActionEvent e )
      {
        // This is where a BatWin (or something of the sort) will pop
        // up and explain how the system works.  (Possibly prevent being
        // bitched at about how things work.)
        
        String msgs[] = { "Expanation of buttons:",
                          "Help Button - Loads this help box.",
                          "Update Button - Updates the schedule for the selected Aide.",
                          "Revert Button- Reverts to the current schedule before changes were made.",
                          "Exit Button - Quits the Graphical Schedule Maker without updating.",
                          "",
                          "Basic Instructions:",
                          "1 - Select an Aide from the drop down list in the upper left corner.",
                          "2 - The current schedule for the selected Aide will appear in the Schedule Grid.",
                          "3 - You may modify the schedule for the selected Aide using the left mouse",
                          "    button to toggle any time slot in the grid.",
                          "4 - When you are done making modifications you may update the database using the",
                          "    Update button at the bottom.",
                          "5 - If you don't want to save the modifications you made to the schedule for the",
                          "    selected Aide, you may use the Exit button or the Revert button."
                        };
        BatWin helpMe = new BatWin( "Graphical Schedule Maker Help", msgs);
      }
    });
    
    // This makes the exitButton function properly
    exitButton.addActionListener(new ActionListener() 
    {
      public void actionPerformed( ActionEvent e )
      {
        mainFrame.dispose();
      }
    });
    
    // This makes the GSchedCanvas function properly
    mainGrid.addMouseListener( new MouseAdapter() 
    {
      public void mousePressed( MouseEvent e )
      {
        if ( aideSelected == true )
          mainGrid.startSelection( e.getX(), e.getY() );
      }
      
      public void mouseReleased( MouseEvent e )
      {
        if ( aideSelected == true )
          mainGrid.stopSelection( e.getX(), e.getY() );
      }
            
    });
    
    // Some basic initialization stuff
    initAideChoice();
    
    // Setup the top part of the window
    topPanel.add( aideChoice );
    topRightPanel.add( new Label( "Current Aide: " ) );
    topRightPanel.add( curAide );
    topPanel.add( topRightPanel );
    mainPanel.add( topPanel, BorderLayout.NORTH );
    
    botPanel.add( loadingLabel );
      
    lowerBotPanel.add( helpButton );
    lowerBotPanel.add( updateButton );
    lowerBotPanel.add( revertButton );
    lowerBotPanel.add( exitButton );
    botPanel.add( lowerBotPanel );

    mainPanel.add( mainGrid, BorderLayout.CENTER );   
    mainPanel.add( botPanel, BorderLayout.SOUTH );
    mainFrame.add( mainPanel );
    
    mainFrame.setSize( new Dimension( 400, 500 ) );
    mainFrame.setLocation(50, 50); 
    mainFrame.show();
  }  
 
  private void initAideChoice()
  {
    // Build the aideChoice drop box.  
    // Data is formatted as XXXX - FName LName, where XXXX is the S account.
    // Usable indexes start at 1 and 0 must always be what it is below.
    
    // For now it's just full of junk (Mostly).
    // It will be loaded from the Database when I understand how.

    aideChoice.add( "Please select an Aide" );
        
    String q = "select USERID, FIRSTNAME, LASTNAME from aidelist";
    String aide;
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(q);
    
    try {
      boolean more = rs.next();
      while (more)
      {
        aide = rs.getString(1) + " - " + rs.getString(2);
        aide = aide + " " + rs.getString(3);
        aideChoice.add(aide);
        more = rs.next();
      }
    }
    catch (SQLException ex) {
      System.out.println(ex);
      System.out.println("initAideChoice");
      System.exit(0);
    }
    bSQL.disconnect();
    
  }
  
  // This sends the schedule of the currently
  // selected aide to the GSchedCanvas
  public void setCurrentSchedule( String inUser )
  {
    loadingLabel.setText( "Loading Schedule..." );
  
    String q = "select * from aidesched where userid='";
    q = q + inUser + "'";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(q);
    
    try 
    {
      rs.next();
      int i, slot;
      boolean slotSet;
      for (i = 2; i <= 205; i++)
      {
        slot = rs.getInt(i);
        if ( slot == 0 )
          slotSet = false;
        else
          slotSet = true;
        mainGrid.setSelection( i + 4, slotSet );
      } //end for loop slots
      loadingLabel.setText( "" );
   
    } //end try
    catch (SQLException ex) 
    {
      System.out.println(ex);
      System.out.println("setCurrentSchedule()");
      System.exit(0);
    }
    bSQL.disconnect();
  } 
  
  public void saveCurrentSchedule( String inUser )
  {
    loadingLabel.setText( "Saving Schedule..." );
    
    //write it all to the database
    String up = "update aidesched set ";
    int i;
    for (i = 6; i <= 209; i++)
    {
      up = up + "SLOT" + Integer.toString(i - 5);
      if ( mainGrid.getSelection( i ) == true )
        up = up + "=1, ";
      else 
        up = up + "=0, "; 
    }    
      
    up = up.substring(0, up.length() - 2);
    up = up + " where USERID='";
    up = up + inUser + "'";
      
    //now write all this to the database
    BatSQL bSQL = new BatSQL();
    bSQL.update(up);
    bSQL.disconnect();        
    
    loadingLabel.setText( "" );
  }
  
  public static void main ( String argv[] )
  {
    GraphicSched myGSched = new GraphicSched();
  }

}
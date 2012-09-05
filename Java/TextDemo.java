/*
 * Copyright (c) 1995-1997 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

/* hacked by ethan georgi */

import java.awt.*;
import java.applet.Applet;

public class TextDemo extends Applet {
    Label myLabel;
    TextField nickName;
    TextArea oldText, newText;
    Button sendButton, clearButton;

    public void init() {

        myLabel = new Label();
        myLabel.setText("Your NickName: ");
        nickName = new TextField(25);
        newText = new TextArea("", 10, 55, 1);
        newText.setEditable(true);
        oldText = new TextArea("", 10, 55, 1);
        oldText.setEditable(false);
        sendButton = new Button("Send");
        clearButton = new Button("Clear");

        //Add the label and field for the nickname
        add(myLabel);
        add(nickName);

        //Add the text area for the new text
        add(newText);

        //Add the buttons
        add(sendButton);
        add(clearButton); 

        //Add the text area for old text
        add(oldText);

        validate();
    }

    public boolean action(Event evt, Object arg) {
        Object target = evt.target;

        if (target == sendButton) {
          String text = newText.getText();
          String nick = nickName.getText();
          if (nick.length() == 0) {
            newText.appendText("\n\n You MUST have a NickName!");
          }
          else {
            oldText.appendText(nick + " says \n" + text + "\n\n");
            newText.setText("");
          }

        }
        else {
          newText.setText("");
        }

        newText.selectAll();
        return true;
    }
}

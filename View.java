import java.awt.*;
import javax.swing.*;

public class View extends JToolBar {
	
	private static final long serialVersionUID = 1L;

	public View(){
        components();
    }
    
    private void components(){
        
        String[] modesel = {"SCRIBBLEFREEHANDLINES", "STRAIGHTLINES", "RECT", "ELIPSE", "SQR", "CIR", "POLY","SEL","BLACK","RED", "GREEN","BLUE"};
        String[] buttons = {"Scribble", "StraightLines", "Rectangles", "Ellipses", "Square", "Circles", "Polygons","Select","Black", "Red", "Green", "Blue"};
        
        ButtonGroup con =new ButtonGroup();
        
        for(int i = 0; i < buttons.length; i++){
            buttons(con, buttons[i], modesel[i]);
        }
    }

    private void buttons(ButtonGroup con, String text, String typemo) {
        JToggleButton b = new JToggleButton(text);
        b.addActionListener(e -> action(typemo));
        con.add(b);
        add(b);
    }
    
    private void action(String bSelect){
        
        switch (bSelect) {
            
        	case "SCRIBBLEFREEHANDLINES":
        		Configure.mode = Configure.Mode.SCRIBBLEFREEHANDLINES;
        		break;
            case "STRAIGHTLINES":
                Configure.mode = Configure.Mode.STRAIGHTLINES;
                break;
            case "RECT":
                Configure.mode = Configure.Mode.RECT;
                break;
            case "ELIPSE":
                Configure.mode = Configure.Mode.ELIPSE;
                break;
            case "SQR":
                Configure.mode = Configure.Mode.SQR;
                break;
            case "CIR":
                Configure.mode = Configure.Mode.CIR;
                break;
            case "POLY":
                Configure.mode = Configure.Mode.POLY;
                break;
            case "SEL":
                Configure.mode = Configure.Mode.SEL;
                break;
            case "BLACK" : 
            	Configure.colr = Color.black;
            	break;
            case "RED": Configure.colr = Color.red;
                break;
            case "GREEN": Configure.colr = Color.green;
                break;
            case "BLUE": Configure.colr = Color.blue;
                break;
        }
    }
}
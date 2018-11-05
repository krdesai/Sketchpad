import java.awt.*;
import javax.swing.*;

public class ShowPane extends JFrame {
    
	private static final long serialVersionUID = 1L;

	public ShowPane(){
       setTitle("Sketch Pad");
       getContentPane().add(new View(), BorderLayout.SOUTH);
       getContentPane().add(new OtherView(), BorderLayout.EAST);
       pack();
    }
    
    private static class OtherView extends JScrollPane {

    	private static final long serialVersionUID = 1L;
		private DrawScreen ds = new DrawScreen();
        
		private OtherView() {
			JPanel jp = new JPanel();
			jp.add(ds);
			setViewportView(jp);
        }
    }
}
import java.awt.*;
public class Configure {
	
	public static Color colr = Color.black;
	public static Mode mode = Mode.SEL;
   
    static Dimension view = Toolkit.getDefaultToolkit().getScreenSize();
    public static Dimension thsize = new Dimension(900,900);
    
    public enum Mode {
    	SCRIBBLEFREEHANDLINES,STRAIGHTLINES,RECT,ELIPSE,SQR,CIR,POLY,SEL
    }
    
}
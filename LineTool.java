import java.awt.*;
import java.io.Serializable;
import java.awt.event.MouseEvent;

public class LineTool extends DrawItem implements Serializable{
    
	private static final long serialVersionUID = 1L;

    public LineTool(DrawItem item, boolean ID){
    	super(item, ID);
    	}
    
    public LineTool(DrawScreen ds) {
        super(Configure.colr, Configure.Mode.STRAIGHTLINES, ds);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        begin = new Point(e.getX(), e.getY());
        finish = begin;
        ds.repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        finish = new Point(e.getX(), e.getY());
        ds.repaint();
    }
    
    public void draw(Graphics graphics){
    	graphics.setColor(colrType);
        if(begin != null && finish != null) {
        	graphics.drawLine(begin.x, begin.y, finish.x, finish.y);
        }
    }
}
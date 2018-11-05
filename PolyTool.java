import java.awt.*;
import java.io.Serializable;
import java.awt.event.MouseEvent;

public class PolyTool extends DrawItem implements Serializable {
    
	private static final long serialVersionUID = 1L;
	public PolyTool(DrawScreen ds) {
        super(Configure.colr, Configure.Mode.POLY, ds);
    }
    
    public PolyTool(DrawItem item, boolean ID){ 
    	super(item, ID); 
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
        	int [] xPoints=new int[] {begin.x,(begin.x+finish.x)/2,finish.x,finish.x,begin.x};
        	int [] yPoints=new int[] {begin.y,(begin.y-finish.y)+begin.y,begin.y,finish.y,finish.y};
            graphics.drawPolygon(xPoints, yPoints, 5);
        }
    }
}
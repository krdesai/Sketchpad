import java.awt.*;
import java.io.Serializable;
import java.awt.event.MouseEvent;
import static java.lang.Math.abs;
import static java.lang.StrictMath.min;

public class EllipTool extends DrawItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public EllipTool(DrawScreen ds) {
        super(Configure.colr, Configure.Mode.ELIPSE, ds);
    }
    
    public EllipTool(DrawItem item, boolean ID){
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
        if(begin != null && finish != null){
            int xcord = min(begin.x, finish.x);
            int ycord = min(begin.y, finish.y);
            int hor = abs(finish.x - begin.x);
            int ver = abs(finish.y - begin.y);
            graphics.drawOval(xcord, ycord, hor, ver);
        }
    }
}
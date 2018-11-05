import java.awt.*;
import java.util.UUID;
import java.awt.event.*;
import static java.lang.Math.abs;
import static java.lang.StrictMath.min;

public class DrawItem implements MouseListener, MouseMotionListener {
	
    public Color colrType;
    protected int index;
    public UUID itemID;
    protected DrawScreen ds;
    protected Point begin;
    protected Point finish;
    protected Point originBegin;
    protected Point originEnd;
    protected Point originLoc;
    protected Point[] points;
    public Configure.Mode itemType;
    
    DrawItem(){}
    
    protected DrawItem(Color colr, Configure.Mode type, DrawScreen ds ){
        itemID = UUID.randomUUID();
        colrType = colr;
        itemType = type;
        this.ds = ds;
    }
    
    protected DrawItem(DrawItem item, boolean ID){
        if(ID) {
            this.itemID = UUID.randomUUID();}
        else {
            this.itemID = item.itemID;
        }
        
        this.colrType = item.colrType;
        this.itemType = item.itemType;
        this.ds = item.ds;
        
        this.begin = new Point();
        this.finish = new Point();
        begin.x = item.begin.x;
        begin.y = item.begin.y;
        finish.x = item.finish.x;
        finish.y = item.finish.y;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mouseDragged(MouseEvent e) {}
    
    @Override
    public void mouseMoved(MouseEvent e) {}
    
    public void draw(Graphics graphics){}
    
    public boolean isDrawn(){
        if(begin == null) 
        	{
        	return false;
        	}
        if(finish == null) {
        	return false;
        	}
        if(begin.x == finish.x && begin.y == finish.y) {
        	return false;}
        return true;
    }
    
    public void setMouse(Point originLoc){
        this.originLoc = originLoc;
        originBegin = new Point(begin.x, begin.y);
        originEnd = new Point(finish.x, finish.y);
    }
    
    public void drawOutline(Graphics graphics){
        int xcord = min(begin.x, finish.x);
        int ycord = min(begin.y, finish.y);
        int hor = abs(finish.x - begin.x);
        int ver = abs(finish.y - begin.y);
        Graphics2D graphics2 = (Graphics2D) graphics;
        Stroke oldStroke = graphics2.getStroke();
        graphics2.setStroke(new BasicStroke(5));
        graphics.setColor(Color.BLUE);
        graphics.drawRect(xcord,ycord,hor,ver);
        graphics2.setStroke(oldStroke);
    }
    
    public Rectangle getValues() {
        int xcord = min(begin.x, finish.x);
        int ycord = min(begin.y, finish.y);
        int hor = abs(finish.x - begin.x);
        int ver = abs(finish.y - begin.y);
        return new Rectangle(xcord,ycord, hor, ver);
    }
    
    public void move(Point newLoc){
        if(newLoc.x > 0 && newLoc.y > 0 && newLoc.x < Configure.thsize.width && newLoc.y < Configure.thsize.height){
        	begin.x = originBegin.x + newLoc.x - originLoc.x;
            finish.x = originEnd.x + newLoc.x - originLoc.x;
            begin.y = originBegin.y + newLoc.y - originLoc.y;
            finish.y = originEnd.y + newLoc.y - originLoc.y;
        }
    }
}
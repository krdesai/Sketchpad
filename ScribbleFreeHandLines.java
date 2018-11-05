import java.awt.*;
import java.util.UUID;
import java.util.ArrayList;
import java.io.Serializable;
import java.awt.event.MouseEvent;

public class ScribbleFreeHandLines extends DrawItem implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> X;
    private ArrayList<Integer> Y;
    
    public ScribbleFreeHandLines(DrawScreen ds){
        super(Configure.colr, Configure.Mode.SCRIBBLEFREEHANDLINES, ds);
        points = new Point[10000];
        index = 0;
    }
    
    public ScribbleFreeHandLines(DrawItem item, boolean ID){
        
        if(ID)
        {
        	this.itemID = UUID.randomUUID();      
        }
       else
        {
            this.itemID = item.itemID;
        }
        
        this.ds = item.ds;
        this.index = item.index;
        this.points = new Point[10000];
        this.itemType = item.itemType;
        this.colrType = item.colrType;
        
        for (int i = 0; i < this.index - 1; i++){
                this.points[i] = new Point();
                this.points[i].x = item.points[i].x;
                this.points[i].y = item.points[i].y;
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        points[index] = new Point(e.getX(), e.getY());
        begin = points[index];
        finish = points[index];
        index++;
        ds.repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        points[index] = new Point(e.getX(), e.getY());
        index++;
        ds.repaint();
    }
    
    public boolean isDrawn(){
        if(index > 0)
        	return true;
        else 
        	return false;
        }
    
    public void draw(Graphics graphics){
        graphics.setColor(colrType);
        for (int i = 0; i < index - 1; i++) {
            if(points[i] != null && points[i+1] != null) {
                graphics.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
        }
            }
    }
    
    public void drawOutline(Graphics graphics){
    	
        int leftPoint = 1000;
        int rightPoint = 0;
        int topPoint = 1000;
        int bottomPoint = 0;
    
        for (int i = 0; i < index - 1; i++){
            int xcord = points[i].x;
            int ycord = points[i].y;
            if(xcord < leftPoint)
                leftPoint = xcord;
            if(xcord > rightPoint)
                rightPoint = xcord;
            if(ycord < topPoint)
                topPoint = ycord;
            if(ycord > bottomPoint)
                bottomPoint = ycord;
        }
        
        Graphics2D graphics2 = (Graphics2D) graphics;
        Stroke oldStroke = graphics2.getStroke();
        graphics2.setStroke(new BasicStroke(5));
        graphics.setColor(Color.CYAN);
        graphics.drawRect(leftPoint, topPoint, (rightPoint-leftPoint), (bottomPoint-topPoint));
        graphics2.setStroke(oldStroke);
    }
    
    
    public Rectangle getBounds() {
        int topPoint = 1000;
        int bottomPoint = 0;
        int leftPoint = 1000;
        int rightPoint = 0;
        for (int i = 0; i < index - 1; i++){
            int xcord = points[i].x;
            int ycord = points[i].y;
            if(xcord < leftPoint)
                leftPoint = xcord;
            if(xcord > rightPoint)
                rightPoint = xcord;
            if(ycord < topPoint)
                topPoint = ycord;
            if(ycord > bottomPoint)
                bottomPoint = ycord;
        }
        
        return new Rectangle(leftPoint, topPoint, (rightPoint-leftPoint), (bottomPoint-topPoint));
    }
    
    public void setMouse(Point originLoc){
        this.originLoc = originLoc;
        X = new ArrayList<>();
        Y = new ArrayList<>();
        
        for(Point p : points){
            if(p != null) {
                X.add(p.x);
                Y.add(p.y);
            }
        }
    }
    
    public void move(Point newLoc){
        if(newLoc.x > 0 && newLoc.y > 0 && newLoc.x < Configure.thsize.width && newLoc.y < Configure.thsize.height){
            int i = 0;
            for(Point p : points){
                if(p != null){
                    p.x = X.get(i) + newLoc.x - originLoc.x;
                    p.y = Y.get(i) + newLoc.y - originLoc.y;
                    i++;
                }
            }
        }
    }
}
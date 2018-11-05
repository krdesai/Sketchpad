import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.LinkedList;

public class DrawScreen extends JToolBar implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
   
	private DrawItem thisItem;
	private boolean sl = false;
	private boolean selMany = false;
	private boolean duplicate = false;
    private final LinkedList<Action> moves;
    private final LinkedList<Action> goBack;
    private LinkedList<DrawItem> items = new LinkedList<>(); 
    private final LinkedList<DrawItem> un = new LinkedList<>();
    private LinkedList<DrawItem> copyItems = new LinkedList<>();
    private final LinkedList<DrawItem> del = new LinkedList<>();
    private LinkedList<DrawItem> selectItems = new LinkedList<>();
    private final LinkedList<DrawItem> oldItems = new LinkedList<>(); 
    private final LinkedList<DrawItem> newItems = new LinkedList<>(); 
   
    public DrawScreen() {
    	
    	moves = new LinkedList<>();
        goBack = new LinkedList<>();
        
        setPreferredSize(Configure.thsize);
        ButtonGroup group;
        group = new ButtonGroup();
        
        JToggleButton b = new JToggleButton("Remove");
        b.addActionListener(e-> del());
        group.add(b);
        add(b);
        
        JToggleButton b1 = new JToggleButton("Copy");
        b1.addActionListener(e-> copyItems());
        group.add(b1);
        add(b1);
    
        JToggleButton b2 = new JToggleButton("Paste");
        b2.addActionListener(e-> DupItems());
        group.add(b2);
        add(b2);
        
        JToggleButton b3 = new JToggleButton("Undo");
        b3.addActionListener(e -> undoEventHandler());
        group.add(b3);
        add(b3);
        
        JToggleButton b4 = new JToggleButton("Redo");
        b4.addActionListener(e-> redoEventHandler());
        group.add(b4);
        add(b4);
       
        JToggleButton b5 = new JToggleButton("Save As");
        b5.addActionListener(e-> saveSketch());
        group.add(b5);
        add(b5);
        
        addMouseListener(this);
        addMouseMotionListener(this);
    
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DrawItem oldIt : items){
        	oldIt.draw(g);
        }
        
        if(!selectItems.isEmpty()){
            for(DrawItem selected : selectItems){
                selected.drawOutline(g);
            }
        }
        
        switch(Configure.mode){
            case SCRIBBLEFREEHANDLINES: 
            case STRAIGHTLINES: 
            case RECT: 
            case ELIPSE: 
            case SQR: 
            case CIR:
            case POLY:  
            thisItem.draw(g);
                break;
            default:
                break;
        }
    }
    
    private void newItems(){
        switch(Configure.mode){
            case SEL:
                break;
            case SCRIBBLEFREEHANDLINES: 
            	thisItem = new ScribbleFreeHandLines(this);
                break;
            case STRAIGHTLINES: 
            	thisItem = new LineTool(this);
                break;
            case RECT: 
            	thisItem = new RectTool(this);
                break;
            case ELIPSE: 
            	thisItem = new EllipTool(this);
                break;
            case SQR: 
            	thisItem = new SqrTool(this);
                break;
            case CIR: 
            	thisItem = new CirTool(this);
                break;
            case POLY: 
            	thisItem = new PolyTool(this);
                break;
            default:
                thisItem = null;
        }
        if(Configure.mode == Configure.Mode.SEL){
            sl = true;
        }else{
            sl = false;
        }
    }
    
    private DrawItem copyItem(DrawItem item, boolean ID){
    	
        switch(item.itemType){
            case SCRIBBLEFREEHANDLINES: 
            	return new ScribbleFreeHandLines(item, ID);
            case STRAIGHTLINES: 
            	return new LineTool(item, ID);
            case RECT: 
            	return new RectTool(item, ID);
            case ELIPSE: 
            	return new EllipTool(item, ID);
            case SQR: 
            	return new SqrTool(item, ID);
            case CIR: 
            	return new CirTool(item, ID);
            case POLY: 
            	return new PolyTool(item, ID);
            default: return null;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	
    	if(sl){
            selectItems.clear();
            for (DrawItem oldIt : items){
                if(oldIt.getValues().contains(e.getPoint())){
                    selectItems.clear();
                    selectItems.push(oldIt);
                }
            }
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	
        if(sl && !selectItems.isEmpty()) {
            
            for(DrawItem s : selectItems){
                s.setMouse(e.getPoint());
            }
    
        }else if(sl){
            for (DrawItem oldIt : items){
                if(oldIt.getValues().contains(e.getPoint())) {
                	
                    selectItems.clear();
                    selectItems.push(oldIt);
                    
                    for(DrawItem selected : selectItems){
                        copyItems.push(copyItem(selected, false));
                        selected.setMouse(e.getPoint());
                    }
                }
            }
            if(selectItems.isEmpty()){
                selMany = true;
            }
            
        }else {
            selectItems.clear();
            thisItem.mousePressed(e);
        }
        repaint();
    }
    
    private enum Action {
    	DRAW, MOVE, DEL 
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
            
        selMany = false;
        if(!sl){
        	
            if(thisItem.isDrawn()) {
                items.addLast(thisItem);
                moves.push(Action.DRAW);
                newItems();
            }
        }
       repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        newItems();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!sl){
            if(thisItem.isDrawn()) {
                items.push(thisItem);
                moves.push(Action.DRAW);
                newItems();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(sl && selMany ){
            for (DrawItem oldIt : items){
                
                    if(!selectItems.contains(oldIt)){
                        selectItems.push(oldIt);
                        copyItems.push(copyItem(oldIt, false));
                }
                    else{
                    selectItems.remove(oldIt);
                    DrawItem rem = null;
                    for(DrawItem cpItem : copyItems){
                        if(oldIt.itemID == cpItem.itemID) {
                        	rem = cpItem;
                            }
                    }
                    if(rem != null){
                        copyItems.remove(rem);
                    }
                }
            }
            
        }else if(sl && !selectItems.isEmpty() ){
        
            
            for(DrawItem duplicate : copyItems){
                if(!oldItems.contains(duplicate)){
                    oldItems.push(duplicate);
                    moves.push(Action.MOVE);
                }
            }

            for(DrawItem sk : selectItems){
                sk.move(e.getPoint());
            }
            repaint();
        }else {
            thisItem.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
    private void del() {

        if(!selectItems.isEmpty()){
            for(DrawItem sl: selectItems){
                items.remove(sl);
                del.push(sl);
                moves.push(Action.DEL);
            }
            selectItems.clear();
            repaint();
        }
    }
    
    private void copyItems(){
        if(sl){
            copyItems.clear();
            for(DrawItem s : selectItems){
                copyItems.push(copyItem(s, true));
            }
            duplicate = true;
        }
    }
    
    private void DupItems(){
        if(duplicate){
            selectItems.clear();
            for(DrawItem cpItem : copyItems){
                cpItem.setMouse(new Point(0,0));
                cpItem.move(new Point(30,30));
                selectItems.push(cpItem);
                items.push(cpItem);
                moves.push(Action.DRAW);
                duplicate = false;
                repaint();
            }
        }
    }
    
    public void undoEventHandler() {
        if(!moves.isEmpty()){
        	
            Action lastEvent = moves.removeFirst();
            goBack.push(lastEvent);
            
            if(lastEvent == Action.DRAW && !items.isEmpty()){
                un.push(items.removeLast());
                selectItems.clear();
            }
            
            if(lastEvent == Action.MOVE && !oldItems.isEmpty()){
                selectItems.clear();
                selectItems.push(oldItems.removeFirst());
                
                DrawItem rem = null;
                for(DrawItem oldIt : items){

                    if(oldIt.itemID == selectItems.peek().itemID){
                    	rem = oldIt;
                    }
                }
                if(rem != null){
                    newItems.push(rem);
                    items.remove(rem);
                }
                items.push(selectItems.peek());
            }
            
            if(lastEvent == Action.DEL && !del.isEmpty()){
                items.push(del.removeFirst());
            }
            
        }
        repaint();
    }
    
    public void redoEventHandler() {
        if(!goBack.isEmpty()){
            Action lastUndoEvent = goBack.removeFirst();
            moves.offerFirst(lastUndoEvent);
            
            if(lastUndoEvent == Action.DRAW && !un.isEmpty()){
                items.push(un.removeFirst());
                selectItems.clear();
            }
        
            if(lastUndoEvent == Action.MOVE && !newItems.isEmpty()){
                selectItems.clear();
                selectItems.push(newItems.removeLast());
                
                DrawItem rem = null;
                for(DrawItem oldIt : items){
                    if(oldIt.itemID == selectItems.peek().itemID){
                    	rem = oldIt;
                    }
                }
                if(rem != null){
                    oldItems.push(rem);
                    items.remove(rem);
                }
                items.push(selectItems.peek());
            }
            
            if(lastUndoEvent == Action.DEL && !items.isEmpty()){
                del.push(items.removeFirst());
            }
        }
        repaint();
    }
    
    public void saveSketch() {
    	SaveFile file = new SaveFile();
    	ReadWritePackage rw = new ReadWritePackage();
        rw.allComps = items;
    	file.save(rw);
    }
    
}
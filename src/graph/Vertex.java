package graph;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

public class Vertex {
	private String _label;
    private boolean _isVisited = false;
    private Vertex _parent = null;
    private Point _location;
    private HashSet<Edge> _eList = new HashSet<>();
    private HashMap<Integer, Edge> _path; // vertex shouldn't have path
    private int _id;
    private int _cap;
    private String _type;
    private boolean _isSelected = false;
    
    public Vertex()
    {
    	
    }
    
	protected Vertex(int id, Point loc, String vertexLabel, String type, int capacity) {
        _label = vertexLabel;
        _type = type;
        _id = id;
        _cap = capacity;
        _location = loc;
    }

    protected Vertex(int id)
    {
    	_id = id;
    }
    
    protected Vertex(int id, String vertexLabel)
    {
    	_id = id;
    	_label = vertexLabel;
    }
    
	public int getX(){
        return _location.x;
    }
    
	public int getY(){
        return _location.y;
    }
    
	public String getLabel() {
        return (_label + " ");
    }
    
    /**
     *
     * @return 
     * returns name assigned to this vertex
     */
    
	public String GetName() {
        return _label;
    }
    
    
	public boolean visited(){
        return _isVisited;
    }
    
    
	public void setVisited(boolean visited){
        _isVisited = visited;
    }
    /**
     *
     * @return 
     * returns type of this vertex
     */
    
	public String getType() {
        return _type;
    }

    
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    /**
     *
     * @return 
     * returns the capacity of this vertex
     */
    
    public int getCapacity() {
        return _cap;
    }
    
    /**
     *
     * @param x
     * sets capacity of vertex to capacity
     */
    
    public void setCapacity(int capacity){
        _cap = capacity;
    }

    public void SetParent(Vertex v)
    {
    	_parent = v;
    }
    
    public Vertex getVertex(int id) {
        return this;
    }

    /**
     *
     * @return 
     * returns x,y coordinate of vertex
     */
    
    public Point getLocation() {
        return _location;
    }

    /**
     *
     * @return 
     * returns edges connected to this vertex
     */
    
    public HashSet<Edge> eList() {
        return _eList;
    }

    /**
     *
     * @return 
     * returns the parent of this vertex
     */
    
    public Vertex getParent() {
        return _parent;
    }

    
    public boolean getSelected() {
        return _isSelected;
    }
    
    
    public void setSelected(boolean sel) {
    	_isSelected = sel;
    }
}

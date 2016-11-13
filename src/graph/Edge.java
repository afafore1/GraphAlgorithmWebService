package graph;

public class Edge{

    private int _id;
    private Vertex _source;
    private Vertex _dest;
    private int _weight;
    private boolean _fail;
    private double _glowLevel = 0;
    private boolean _bidirectional = false;

    public Edge()
    {
    	
    }
    
    protected Edge(int id, Vertex source, Vertex dest, int weight, boolean fail) {
    	_id = id;
    	_source = source;
    	_dest = dest;
    	_weight = weight;
    	_fail = fail;
    }

    
    public boolean getBidirectional() {
        return _bidirectional;
    }

    
    public void setBidirectional(boolean bi) {
    	_bidirectional = bi;
    }

    
    public int getId() {
        return _id;
    }

    
    public Vertex getDest() {
        return _dest;
    }

    
    public Vertex getSource() {
        return _source;
    }

    
    public int getWeight() {
        return _weight;
    }
    
    
    public void setWeight(int w){
        _weight = w;
    }

    
    public String getConnections() {
        return _source.getId() + "," + _dest.getId();
    }

    
    public double getGlowLevel() {
        return _glowLevel;
    }

    
    public void glowDie(double decrement) {
        _glowLevel = Math.max(_glowLevel - decrement, 0);
    }

    
    public void setGlowLevel(double newGlow) {
        _glowLevel = newGlow;
    }

    
    public boolean isFailed() {
        return _fail;
    }

    
    public void setFailed(boolean bool) {
        _fail = bool;
    }

    
    public String toString() {
        return getId() + " " + _source.getName() + " " + _dest.getName() + " " + getWeight() + " " + isFailed();
    }
}
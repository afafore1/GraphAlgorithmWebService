package graph;

import java.awt.Point;
import java.util.HashMap;

public class Graph implements IGraph {

    private final HashMap<Integer, Vertex> _vertices;
    private final HashMap<Integer, Edge> _edges;
    //HashMap<Integer, Vertex> nodes;
    //private Queue<Vertex> queue;

    public Graph() {
        _vertices = new HashMap<>();
        _edges = new HashMap<>();
    }

    /* (non-Javadoc)
	 * @see graph.IGraph#AddVertex(int, java.awt.Point, java.lang.String, java.lang.String, int)
	 */
    @Override
	public void AddVertex(int id, Point location, String label, String type, int capacity)
    {
    	Vertex v = new Vertex(id, location, label, type, capacity);
    	_vertices.put(id, v);
    }
    
    /* (non-Javadoc)
	 * @see graph.IGraph#RemoveVertex(int)
	 */
    @Override
	public void RemoveVertex(int id)
    {
    	if(_vertices.containsKey(id))
    	{
    		_vertices.remove(id);
    	}
    }
    
    /* (non-Javadoc)
	 * @see graph.IGraph#AddEdge(int, graph.Vertex, graph.Vertex, int, boolean)
	 */
    @Override
	public void AddEdge(int id, Vertex source, Vertex destination, int weight, boolean fail, boolean isDirectional)
    {
    	Edge edge = new Edge(id, source, destination, weight, fail);
    	_edges.put(id, edge);
    	if(isDirectional)
    	{
    		edge.setBidirectional(true);
    		source.eList().add(edge);
    		destination.eList().add(edge);
    	}else
    	{
    		source.eList().add(edge);
    	}
    }
    
    /* (non-Javadoc)
	 * @see graph.IGraph#RemoveEdge(int)
	 */
    @Override
	public void RemoveEdge(int id)
    {
    	if(_edges.containsKey(id))
    	{
    		_edges.remove(id);
    	}
    }
    
    /* (non-Javadoc)
	 * @see graph.IGraph#GetVertices()
	 */
    @Override
	public HashMap<Integer, Vertex> GetVertices() {
        return _vertices;
    }

    /* (non-Javadoc)
	 * @see graph.IGraph#GetEdges()
	 */
    @Override
	public HashMap<Integer, Edge> GetEdges() {
        return _edges;
    }
}
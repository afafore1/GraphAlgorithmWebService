package graph;

import java.awt.Point;
import java.util.HashMap;

public interface IGraph {

	void AddVertex(int id, Point location, String label, String type, int capacity);

	void RemoveVertex(int id);

	void AddEdge(int id, Vertex source, Vertex destination, int weight, boolean fail, boolean isDirectional);

	void RemoveEdge(int id);

	HashMap<Integer, Vertex> GetVertices();

	HashMap<Integer, Edge> GetEdges();

}
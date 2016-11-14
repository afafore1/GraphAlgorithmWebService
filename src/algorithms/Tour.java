package algorithms;

import java.util.ArrayList;
import java.util.Collections;

import graph.Edge;
import graph.IGraph;
import graph.Vertex;

/**
 *
 * @author Ayomitunde
 */
public class Tour {
	private IGraph _graph;
	private ArrayList<Vertex> _tour = new ArrayList<>();
    private double _fitness = 0;
    private int _distance = 0;
    
	public Tour(IGraph graph)
	{
		_graph = graph;
	}

    public Tour() {
        for (int i = 0; i < _graph.GetVertices().size(); i++) {
            _tour.add(null);
        }
    }

    public Tour(ArrayList<Vertex> _tour) {
        this._tour = _tour;
    }

    public void generateIndividual() {
        _graph.GetVertices().keySet().stream().forEach((vertexIndex) -> {
            setVertex(vertexIndex, _graph.GetVertices().get(vertexIndex));
        });
        Collections.shuffle(_tour);
    }

    public boolean isSafe(Vertex s, Vertex d) {
        for (Edge e : s.eList()) {
            if (!_tour.contains(d) && e.getDest() == d) {
                return true;
            }
        }
        return false;
    }

    public Vertex getVertex(int vertexIndex) {
        return (Vertex) (_tour.get(vertexIndex));
    }

    public void setVertex(int _tourPosition, Vertex vertex) {
        _tour.set(_tourPosition, vertex);
        _fitness = 0;
        _distance = 0;
    }

    public int getDistance(Vertex source, Vertex destination) {
        for (Edge e : _graph.GetEdges().values()) {
            if ((e.getSource().equals(source) && e.getDest().equals(destination))
                    || (e.getDest().equals(source) && e.getSource().equals(destination))) {
                return e.getWeight();
            }
        }
        return -1;
    }

    public double getFitness() {
        if (_fitness == 0) {
            _fitness = 1 / (double) getTourDistance();
        }
        return _fitness;
    }

    public int getTourDistance() {
        if (_distance == 0) {
            for (int vertexIndex = 0; vertexIndex < _tourSize(); vertexIndex++) {
                Vertex fromVertex = getVertex(vertexIndex);
                Vertex destinationVertex;
                if (vertexIndex + 1 < _tourSize()) {
                    destinationVertex = getVertex(vertexIndex + 1);
                } else {
                    destinationVertex = getVertex(0);
                }
                int dist = getDistance(fromVertex, destinationVertex);
                if (dist != -1) { // means there is a connection
                    _distance += dist;
                }
            }
        }
        return _distance;
    }

    public int _tourSize() {
        return _tour.size();
    }

    public boolean containsVertex(Vertex vertex) {
        return _tour.contains(vertex);
    }

    @Override
    public String toString() {
        String geneString = "| ";
        for (int i = 0; i < _tourSize(); i++) {
            geneString += getVertex(i).getLabel() + " | ";
        }
        return geneString;
    }

    public ArrayList<Vertex> getTour() {
        return this._tour;
    }
}

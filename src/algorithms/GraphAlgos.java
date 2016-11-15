package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import graph.Edge;
import graph.IGraph;
import graph.Vertex;

public class GraphAlgos {
	enum Types {
	    Person,
	    Place,
	    City
	}
	
	IGraph _graph;
	boolean _pop = true;
	private ArrayList<Integer> _cutV;
	private ArrayList<Vertex> _failed;
	private HashMap<Vertex, Integer> _dist; 
//	private int source = -1; // these should be passed in
//    private int _dest = -1;
    private HashMap<Vertex, Vertex> _glowMap;
	
	
	public GraphAlgos(IGraph graph)
	{
		_graph = graph;
		_cutV = new ArrayList<>();
		_failed = new ArrayList<>();
	}
    /**
     *
     * @param u
     * @param visited
     * @param disc
     * @param low
     * @param parent
     * @param ap
     */
    public void APF(int u, int source, HashMap<Integer, Integer> visited, HashMap<Integer, Integer> disc, HashMap<Integer, Integer> low, HashMap<Integer, Integer> parent, HashMap<Integer, Integer> ap) {
    	int time = 0;
        int children = 0;
        visited.put(u, 0);
        time = ++time;
        disc.put(u, time);
        low.put(u, time);
        Iterator<Edge> i = _graph.GetVertices().get(u).eList().iterator();

        while (i.hasNext()) {
            int v = i.next().getId(); // v is current adj to u
            if (visited.get(v) == -1) {
                children++;
                parent.put(v, u);
                APF(v, source, visited, disc, low, parent, ap); // recursive for it
                int val = Math.min(low.get(u), low.get(v));
                low.put(u, val);

                if (u == source && children > 1) {
                    ap.put(u, 1);
                }
                // if u is not root and low value of one of its child is more than discovery value of u
                if (u != source && low.get(v) >= disc.get(u)) { // need a check for this if statement.. always marks beginning as a cut even when it's not
                    ap.put(u, 1);
                }
            } else if (v != parent.get(u)) {
                int val = Math.min(low.get(u), low.get(v));
                low.put(u, val);
            }
        }
    }

    public String AP(int source) {
        HashMap<Integer, Integer> visited = new HashMap<>();
        boolean cutExist = false;
        HashMap<Integer, Integer> disc = new HashMap<>();
        HashMap<Integer, Integer> low = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();
        HashMap<Integer, Integer> ap = new HashMap<>();

        Iterator<Integer> allNodes = _graph.GetVertices().keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            parent.put(key, -1);
            visited.put(key, -1);
            ap.put(key, 0);
        }

        allNodes = _graph.GetVertices().keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            if (visited.get(key) == -1) {
                APF(key, source, visited, disc, low, parent, ap);
            }
        }

        allNodes = _graph.GetVertices().keySet().iterator();
        StringBuilder sbBuilder = new StringBuilder();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            if (ap.get(key) == 1) {
                sbBuilder.append(key + " is a cut vertex");
                _cutV.add(key);
                cutExist = true;
            }
        }

        if (cutExist == false) {
        	sbBuilder.append("No cut vertex in Graph");
        }
        return sbBuilder.toString();
    }

    public String Dfs(Vertex source) {
        reset();
        StringBuilder sb = new StringBuilder();
        Stack<Vertex> stack = new Stack<>();
        HashSet<String> bconn = new HashSet<>();
        source.setVisited(true);
        source.SetParent(source);
        stack.push(source);
        while (!stack.isEmpty()) {
            Vertex current = stack.peek();
            sb.append("Considering element " + current.GetName());
            bconn.add(current.GetName());
            if (current.eList().isEmpty()) {
            	sb.append("Removing " + stack.pop());
            } else {
                for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                    Edge t = currentList.next();
                    if (!t.isFailed()) {
                        Vertex next = getConn(current, t);
                        if (next.visited() == false) { // visited just one at a time
                            sb.append("Pushing " + next.GetName());
                            stack.push(next);
                            next.SetParent(current);
                            next.setVisited(true);
                            break;
                        }
                        if (!currentList.hasNext()) {
                            Vertex backEdge = stack.pop();
                            sb.append("Back edge " + backEdge.GetName());
                        }
                    } else {
                        stack.pop();
                    }
                }
            }
        }
        sb.append("order is " + bconn);
        return sb.toString();
    }

    public String Bfs(Vertex source) {
        reset();
        StringBuilder sb = new StringBuilder();
        Queue<Vertex> queue = new LinkedList<>(); // FIFO [1,2
        source.setVisited(true); // marked as visited [0,1,2
        queue.add(source); // put into queue
        source.SetParent(source); // setShortestPath parent
        ArrayList<Integer> conn = new ArrayList<>();
        while (!queue.isEmpty()) { // source
            Vertex current = queue.poll(); // remove first 0
            conn.add(current.getId());
            for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                Edge t = currentList.next();
                Vertex next = getConn(current, t);
                if (!t.isFailed()) {
                    if (!next.visited()) {
                        next.setVisited(true);
                        queue.add(next);
                        next.SetParent(current);
                    }
                }
            }
        }
        sb.append("Order is " + conn);
        return sb.toString();
    }

    public String NearestNeighbor() {
        reset();
        StringBuilder sb = new StringBuilder();
        long startTime = System.currentTimeMillis();
        Stack<Vertex> stack = new Stack<>();
        // start with a random Node
        int random = (int) (Math.random() * _graph.GetVertices().size());
        Vertex startNode = _graph.GetVertices().get(random);
        startNode.setVisited(true);
        stack.push(startNode);
        while (!stack.isEmpty()) {
            startNode = nextPath(startNode, stack);
            if(startNode == null){
                printSolution(stack);
            }
        }
        long endTime = System.currentTimeMillis();
        
        sb.append(String.valueOf((endTime - startTime))+" ms");
        return sb.toString();
    }
    
    public void startGANN(Population pop){
        Tour [] tours = pop.getTours();
        int size = pop.populationSize() - 1;
        for(int i = 0; i < size; i++){
            Tour t = tours[i];
            t = GANearestNeighbor(t, size);
            pop.saveTour(i, t);
        }
    }

    public Tour GANearestNeighbor(Tour tour, int populationSize){
        reset();
        Stack<Vertex> stack = new Stack();
        int random = (int) (Math.random() * _graph.GetVertices().size());
        Vertex startNode = _graph.GetVertices().get(random);
        startNode.setVisited(true);
        stack.push(startNode);
        while (!stack.isEmpty()) {
            startNode = nextPath(startNode, stack);
            if(startNode == null){
                while(!stack.isEmpty()){
                    Vertex current = stack.pop();
                    tour.setVertex(populationSize, current);
                    populationSize--;
                }
            }
        }
        return tour;
    }
    
    public String printSolution(Stack<Vertex> stack){
    	StringBuilder sb = new StringBuilder();
    	_glowMap = new HashMap<>();
        Vertex last = stack.peek();
        int finalDistanceValue = 0;
        while(!stack.isEmpty()){
            Vertex current = stack.pop();
            Vertex next = stack.isEmpty() ? last : stack.peek();
            _glowMap.put(current, next);
            finalDistanceValue += GetEdgeWeight(current, next);
            sb.append("Vertex "+ current.GetName()+" -> ");
        }
        return sb.toString();
    }
    
    private Vertex nextPath(Vertex source, Stack<Vertex> stack) {
        Vertex n = null;
        int leastWeight = Integer.MAX_VALUE;
        for (Edge e : source.eList()) {
            if (e.getWeight() < leastWeight) {
                if (e.getDest().visited() == false) {
                    leastWeight = e.getWeight();
                    n = e.getDest();
                    _pop = false; // why is pop a global variable ?
                }
            }
        }
        if(n != null){
            n.setVisited(true);
            stack.push(n);
        }
        return n;
    }

    public boolean pathExist(Vertex source, Vertex dest) {
        _graph.GetVertices().values().stream().forEach((v) -> {
            v.setVisited(false);
        });
        Queue<Vertex> queue = new LinkedList<>(); // FIFO
        source.setVisited(true); // marked as visited
        queue.add(source); // put into queue
        while (!queue.isEmpty()) { // source
            Vertex current = queue.poll(); // remove first 
            for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                Edge t = currentList.next();
                if (!t.isFailed()) {
                    Vertex next = getConn(current, t);
                    if (next == dest) {
                        return true; // a path exist
                    } else if (next.visited() == false) {
                        next.setVisited(true);
                        queue.add(next);
                    }
                }
            }
        }
        return false;
    }

    public String disjointPathBfs(Vertex source) {
    	StringBuilder sb = new StringBuilder();
        _graph.GetVertices().values().stream().forEach((v) -> {
            v.setVisited(false);
        });
        HashMap<Vertex, Vertex> tempShortPath = new HashMap<>();
        Queue<Vertex> queue = new LinkedList<>(); // FIFO
        source.setVisited(true); // marked as visited
        queue.add(source); // put into queue
        source.SetParent(source); // setShortestPath parent
        ArrayList<Integer> conn = new ArrayList<>();
        while (!queue.isEmpty()) { // source
            Vertex current = queue.poll(); // remove first 
            conn.add(current.getId());
            for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                Edge t = currentList.next();
                Vertex next = getConn(current, t);
                if (!t.isFailed()) {
                    if (next.visited() == false) {
                        next.setVisited(true);
                        queue.add(next);
                        next.SetParent(current);
                    }
                }
            }
        }
        sb.append("Order is " + conn);
        return sb.toString();
    }

    public Edge getEdge(Vertex s, Vertex d) {
        for (Edge edge : _graph.GetEdges().values()) {
            if (edge.getSource() == s && edge.getDest() == d) {
                return edge;
            }
        }
        return null;
    }
    
    public int GetEdgeWeight(Vertex s, Vertex d){
        for(Edge edge : s.eList()){
            if(edge.getDest().equals(d)){
                return edge.getWeight();
            }
        }
        return -1;
    }

    public void disJointshortestPath(int v, int e) {
    	HashMap<Integer, HashMap<Vertex, Vertex>> disjointPaths = new HashMap<>();
    	HashMap<Vertex, Vertex> tempShortPath = new HashMap<>();
    	_glowMap = new HashMap<>();
    	int pathvalue = 0;
        while (pathExist(_graph.GetVertices().get(v), _graph.GetVertices().get(e)) == true) {
            System.out.println("Running ..");
            disjointPathBfs(_graph.GetVertices().get(v));
            System.out.println("done!\nStarting shortest path");
            for (int i = e; i >= 0; i = _graph.GetVertices().get(i).getParent().getId()) {
                if (i == v) {
                    break;
                }
                if (_graph.GetVertices().get(i).getParent().getId() != -1) {
                    Edge failedge = getEdge(_graph.GetVertices().get(i).getParent(), _graph.GetVertices().get(i));
                    if (failedge != null) {
                        failedge.setFailed(true);
                    }
                    tempShortPath.put(_graph.GetVertices().get(i).getParent(), _graph.GetVertices().get(i));
                }
            }
            _glowMap = (HashMap<Vertex, Vertex>) tempShortPath.clone();
            //Gui.graph();
            disjointPaths.put(pathvalue, tempShortPath);
            pathvalue++;
        }
        System.out.println("No path exist!");
        System.out.println("paths are " + disjointPaths);

    }

    /**
     *
     * @param s passed in vertex
     * @param e edge associated with vertex s
     * @return get destination of vertex passed in from edge
     */
    public Vertex getConn(Vertex s, Edge e) {
        if (e.getBidirectional()) {
            if (e.getSource() == s) {
                return e.getDest();
            } else {
                return e.getSource();
            }
        } else {
            return e.getDest();
        }
    }

    //dijsktra
    public void execute(Vertex source) {
        // get _graph.GetVertices() and edges from GUI
    	HashSet<Vertex> sNodes = new HashSet<>(); // settled _graph.GetVertices() will be placed in this setShortestPath
    	HashSet<Vertex> uSNodes = new HashSet<>(); // unsettled _graph.GetVertices() will be placed in this setShortestPath
    	HashMap<Vertex, Integer> dist = new HashMap<>(); // weight to get to node
        reset();
        dist.put(source, 0); // first setShortestPath source to 0
        uSNodes.add(source); // add source to unsettled _graph.GetVertices()
        while (uSNodes.size() > 0) { // do this until no more unsettled _graph.GetVertices()
            Vertex v = getMin(uSNodes); // we use min node from unsettled _graph.GetVertices() each time to process
            sNodes.add(v); // add it to settled _graph.GetVertices()
            uSNodes.remove(v); // remove it
            findMinDist(v, sNodes, uSNodes); // find min distance
        }
    }

    //settled _graph.GetVertices()
    public boolean isSettled(Vertex v, HashSet<Vertex> sNodes) {
        return sNodes.contains(v);
    }

    //returns weight
    public int getWeight(Vertex s, Vertex d) {
        for (Edge e : _graph.GetEdges().values()) {
            if (e.getSource() == s && e.getDest() == d || e.getSource() == d && e.getDest() == s) {
                return e.getWeight();
            }
        }
        return -1; // edge does not exist then
    }

    //getNeighbors
    public List<Vertex> getNeighbors(Vertex v, HashSet<Vertex> sNodes) {
        List<Vertex> neighbors = new ArrayList<>();
        HashSet<Edge> n = _graph.GetVertices().get(v.getId()).eList();
        Iterator<Edge> neighb = n.iterator();
        while (neighb.hasNext()) {
            Edge t = neighb.next();
            if (!_failed.contains(t.getDest())) {
                Vertex next = getConn(v, t);
                if (!isSettled(next, sNodes)) {
                    neighbors.add(next);
                }
            }
        }
        return neighbors;
    }

    // find min distance
    public void findMinDist(Vertex v, HashSet<Vertex> sNodes, HashSet<Vertex> uSNodes) {
        List<Vertex> neighbors = getNeighbors(v, sNodes);
        neighbors.stream().forEach((Vertex t) -> {
            int combWeight = GSD(v) + getWeight(v, t);
            /*getWeight*/ if (GSD(t) > combWeight) {
                _dist.put(t, GSD(v) + getWeight(v, t));
                t.SetParent(v);
                uSNodes.add(t);
            }
        });
    }

    public Vertex getMin(HashSet<Vertex> v) {
        Vertex min = null;
        for (Vertex vert : v) {
            if (min == null) {
                min = vert;
            } else if (GSD(vert) < GSD(min)) {
                min = vert;
            }
        }
        return min;
    }

    public int GSD(Vertex d) {
        Integer distance = _dist.get(d);
        if (distance == null) {
            return Integer.MAX_VALUE;
        } else {
            return distance;
        }
    }

    public ArrayList BfsSuggeest(Vertex source, int num) {
        reset();
        StringBuilder sb = new StringBuilder();
        Queue<Vertex> suggestQueue = new LinkedList<>(); // FIFO
        source.setVisited(true); // marked as visited
        suggestQueue.add(source); // put into queue
        source.SetParent(source);
        ArrayList<Integer> conn = new ArrayList<>();
        while (!suggestQueue.isEmpty()) { // source
            Vertex current = suggestQueue.poll(); // remove first 
            Iterator<Edge> currentList = current.eList().iterator();
            while (currentList.hasNext()) {
                Edge t = currentList.next();
                Vertex next = getConn(current, t);
                if (next.visited() == false) {
                    next.setVisited(true);
                    suggestQueue.add(next);
                    next.SetParent(current);
                    if (!source.eList().contains(t)) {
                        switch (num) {
                            case 0:
                                if (next.getType().equals(Types.Person.toString())) {
                                    if (conn.size() <= 5) {
                                        conn.add(next.getId());
                                    }
                                }
                                break;
                            case 1:
                                if (next.getType().equals(Types.Place.toString())) {
                                    if (conn.size() <= 5) {
                                        conn.add(next.getId());
                                    }
                                }
                                break;
                            case 2:
                                if (next.getType().equals(Types.City.toString())) {
                                    if (conn.size() <= 5) {
                                        conn.add(next.getId());
                                    }
                                }
                                break;
                            case 3:
                                if (next.getType().equals(Types.Place.toString())) {
                                    if (conn.isEmpty()) { // keep sorted always
                                        sb.append("adding " + next.getId());
                                        conn.add(next.getId());
                                    } else {
                                        conn.add(next.getId());
                                        for (int i = 1; i < conn.size(); i++) {
                                            for (int j = i; j > 0; j--) {
                                                if (_graph.GetVertices().get(conn.get(j)).getCapacity() < _graph.GetVertices().get(conn.get(j - 1)).getCapacity()) {
                                                    conn.add(j, conn.get(j - 1));
                                                    conn.add(j - 1, conn.get(j));
                                                }
                                            }
                                        }
                                    }
                                }
                            default:
                                break;
                        }

                    }
                }
            }
        }

        return conn;
    }

    public void shortestPath(int v, int e) {
    	StringBuilder sb = new StringBuilder();
    	HashMap<Vertex, Vertex> setShortestPath = new HashMap<>();
    	_glowMap = new HashMap<>();
        int capacity = 0;
        if (e == v) {
            sb.append(v + "-->" + v);
            return;
        }
        for (int i = e; i >= 0; i = _graph.GetVertices().get(i).getParent().getId()) {
            if (i == v) {
                break;
            }
            if (_graph.GetVertices().get(i).getParent().getId() != -1) {
                setShortestPath.put(_graph.GetVertices().get(i).getParent(), _graph.GetVertices().get(i));
                capacity += getWeight(_graph.GetVertices().get(i).getParent(), _graph.GetVertices().get(i));
            }
        }

        //Gui.setlblCapTransferred(String.valueOf(capacity));
//        if (capacity <= _graph.GetVertices().get(e).getCapacity()) {
//            Gui.setlblCapTransferredColor(Color.red);
//        } else {
//            Gui.setlblCapTransferredColor(Color.blue);
//        }
        _glowMap.clear();
        _glowMap = (HashMap<Vertex, Vertex>) setShortestPath.clone();
    }

    public HashMap<Vertex, Vertex> GetGlowMap()
    {
    	return _glowMap;
    }
    
    public ArrayList<Vertex> GetFailedVertices()
    {
    	return _failed;
    }
    
    public ArrayList<Integer> GetCutVertices()
    {
    	return _cutV;
    }
    
    public HashMap<Vertex, Integer> GetDist()
    {
    	return _dist;
    }
    
    public void FailVertex(int nodeKey)
    {
    	Vertex fail = _graph.GetVertices().get(nodeKey);
        if (_failed.contains(fail)) {
        	_failed.remove(fail);
        } else {
        	_failed.add(fail);
        }
        Iterator<Edge> e = fail.eList().iterator();
        while (e.hasNext()) {
            Edge next = e.next();
            next.setFailed(!next.isFailed()); //_set it to opposite of what it is
        }
    }
    
    public void AutoFailVertices(int source, int dest)
    {
    	if (source > -1 && dest > -1) {
            if (0.2 >= Math.random()) { // make probability of a node failure low
                int vertexSize = _graph.GetVertices().size();
                Vertex v = null;
                int rand = 0;
                while (v == null) {
                    rand = (int) (Math.random() * vertexSize);
                    v = _graph.GetVertices().get(rand);
                }
                if (!(rand == source || rand == dest)) { // do not fail source or destination
                    if (GetFailedVertices().contains(v)) {
                        GetFailedVertices().remove(v);
                    } else {
                        GetFailedVertices().add(v);
                    }

                    _glowMap.clear();
                    execute(_graph.GetVertices().get(source));
                    shortestPath(source, dest);
                }
            }
        }
    }
    
    public void reset() {
        for (Vertex v : _graph.GetVertices().values()) {
            v.setVisited(false);
        }
        for (Edge e : _graph.GetEdges().values()) {
            e.setFailed(false);
        }
    }

    public boolean isConnected(int source) {
        Vertex s = _graph.GetVertices().get(source);
        Bfs(s);
        Iterator<Vertex> vert = _graph.GetVertices().values().iterator();
        while (vert.hasNext()) {
            Vertex key = vert.next();
            if (!key.visited()) {
                return false;
            }
        }
        return true;
    }

//    public int hasPath(int v) {
//        return visited.get(v);
//    }
//
//    public int distTo(int v) {
//        return distTo.get(v);
//    }
}

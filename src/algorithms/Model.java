package algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import com.sun.javafx.geom.Edge;

import graph.Vertex;

public class Model {
    private static HashMap<Integer, Integer> connectionCache = new HashMap<>();
    private static Queue<Vertex> queue;
    private static Stack<Vertex> stack;
    private static String sim = null;
    private static boolean startSA = false;
    private static HashMap<Integer, Integer> distTo;
    private static HashMap<Integer, Integer> visited;
    private static HashMap<Integer, Integer> color;
    private static HashMap<Integer, Integer> fcolors;
    private static HashMap<Integer, Integer> greedyresult;
    private static HashMap<Vertex, Vertex> glowMap;
    private static HashSet<Integer> _colors2;
    private static HashSet<Integer> randomKeys;
    private static ArrayList<Integer> cutV;
    private static ArrayList<Vertex> failed;
    private static Color[] vertexColors;
    private static int _selectedNode = -1;
    private static int id = 0;
    private static int Edgeid = 0;
    private static int weight = 0;
    private static Integer maxColors = 0;
    private static int _source = -1;
    private static int _dest = -1;
    private static int _findNode = -1;
    private static String currentProject = null;
    private static boolean changesMade = false;
    private static boolean algCalled = false;
    private static double dotOffset = 0.0;
    private static Algorithms alg;
    private static int time = 0;
    private static int toolType = -1;
    private static HashMap<Integer, Vertex> vertices;
    private static ArrayList<Edge> edges;
    private static Queue<Vertex> suggestQueue;
    private static HashMap<Integer, HashSet<Integer>> nodes;
    private static HashMap<Vertex, Vertex> setShortestPath = new HashMap<>();
    private static Map<Integer, Integer> set;
    private static ArrayList<Integer> conn;
    private static HashSet<String> bconn;
    private static int pathvalue = 0;
    private static HashMap<Vertex, Vertex> tempShortPath;
    private static HashMap<Integer, HashMap<Vertex, Vertex>> disjointPaths = new HashMap<>();
    //for dijkstra
    private static int Capacity = 0;
    private static int node1 = -1;
    private static int node2 = -1;
    private static HashSet<Vertex> uSNodes; // unsettled
    private static HashSet<Vertex> sNodes; // settled
    private static HashMap<Vertex, Integer> dist; // distance
    private static int decreaseWeightEllapse = 0;
    //classes
    private static GraphifyGUI Gui;
    private static TSP_SA tsp_sa;
    private static TSP_GA tsp_ga;
    // distances
    private static int InitialDistanceValue = 0;
    private static int FinalDistanceValue = 0;
    
    //timer
    private static long startTime = 0;
    private static long endTime = 0;
    
    private static int counter = 0;
    

    private static final int TOOL_NONE = -1;
    private static final int TOOL_VERTEX = 0;
    private static final int TOOL_BIDIRECTIONAL = 1;
    private static final int TOOL_DIRECTIONAL = 2;
    private static final int ARR_SIZE = 8;
    private static int _SIZE_OF_NODE = 20;
	private Model()
	{
		
	}
}

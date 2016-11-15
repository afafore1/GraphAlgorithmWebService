# GraphAlgorithmAPI
This API can be used to run some graph algorithms such as 
- Breadth first search
- Depth first search
- Dijsktra
- Check Connectedness in Graphj

# How to use ?
All you need is access to the Graph/IGraph and GraphAlgos class.
The **Graph class** can be used to retrieve all vertices and edges. 
The graphAlgo class can be used to add vertices, edges e.t.c
Sample
``` IGraph graph = new Graph();
    GraphAlgos graphAlgo = new GraphAlgos(graph);
    graphAlgo.Dfs(Vertex source); // takes in source object (Vertex)
    graphAlgo.Bfs(Vertex source); // takes in source object (Vertex)
    graphAlgo.execute(Vertex source); // Dijkstra's algorithm
    graphAlgo.shortestPath(int source, int destination); // takes in source and destination id. Always call to get path after algorithm

class Graph {
  int nodes;
  int adjacencyMatrix[];
  
  Graph(int nodes) {
    this.nodes = nodes;
    this.adjacencyMatrix = new int[nodes * nodes];
  }
  
  boolean isEdgeBetween(int from, int to) {
    int row = from;
    int column = to;
      if(from < 0 || to < 0 || from > nodes || to > nodes) {
        throw new RuntimeException("Checking for invalid range on graph"); 
     }
     
     return this.adjacencyMatrix[row * nodes + column] > 0;
  }
  
  Graph copy() {
      Graph graph = new Graph(nodes);
      for(int i = 0; i < nodes * nodes; i++) {
       graph.adjacencyMatrix[i] = this.adjacencyMatrix[i]; 
      }
      return graph;
  }
  
  void reverseEdgeBetween(int from, int to) {
     int row = from;
     int column = to;
     if(from < 0 || to < 0 || from > nodes || to > nodes) {
        throw new RuntimeException("Removing non-existant edge from graph"); 
     }
   int temp = this.adjacencyMatrix[column * nodes + row];  
   this.adjacencyMatrix[column * nodes + row] = this.adjacencyMatrix[row * nodes + column];
   this.adjacencyMatrix[row * nodes + column] = temp;
    
  }
  
   void addEdgeBetween(int from, int to, int weight) {
     int row = from;
     int column = to;
     
     if(from < 0 || to < 0 || from > nodes || to > nodes ||  this.adjacencyMatrix[row * nodes + column] > 0) {
        throw new RuntimeException("Adding invalid edge to graph"); 
     }
     
     this.adjacencyMatrix[row * nodes + column] = weight;
   }
   
   void removeEdgeBetween(int from, int to) {
     int row = from;
     int column = to;
     if(from < 0 || to < 0 || from > nodes || to > nodes ||  this.adjacencyMatrix[row * nodes + column] == 0) {
        throw new RuntimeException("Removing non-existant edge from graph"); 
     }
     
     this.adjacencyMatrix[row * nodes + column] = 0;
   }
   
   
   List<Integer> getNeighbors(int node) {
       int row = node;
       int column = nodes;
       
       List<Integer> result = new ArrayList<Integer>();
       for(int i = 0; i < nodes; i++) {
         if(adjacencyMatrix[row * nodes + i] > 0) {
          result.add(i);
         }
       }
       
       return result;
   }
   
}
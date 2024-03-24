package DataStructures.Graph;

import java.util.*;

public class Graph<T> {
    private GraphType type;
    private Map<T,List<Edge>> adjacencyList;

    public Graph(GraphType type){
        this.type = type;
        adjacencyList = new HashMap<>();
    }

    public GraphType getGraphType(){
        return type;
    }

    public void addVertex(T vertex){
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void removeVertex(T vertex){
        adjacencyList.remove(vertex);
    }

    public Set<T> getVerticies(){
        return adjacencyList.keySet();
    }

    public void addEdge(T source, T destination, int weight){
        adjacencyList.get(source).add(new Edge(destination, weight));

        if(type == GraphType.UNDIRECTED)
            adjacencyList.get(destination).add(new Edge(source, weight));
    }

    public void removeEdge(T source, T destination){
        List<Edge> sourceEdges = adjacencyList.get(source);

        if(sourceEdges != null) 
            sourceEdges.removeIf(e -> e.destination.equals(destination));

        if(type == GraphType.UNDIRECTED){
            List<Edge> destinationEdges = adjacencyList.get(destination);
            if(destinationEdges != null) 
                destinationEdges.removeIf(e -> e.destination.equals(source));
        }
    }

    public List<Edge> getEdges(T vertex){
        return adjacencyList.get(vertex);
    }

    public List<T> DFS(T start){
        if(type != GraphType.DIRECTED){
            System.out.println("DFS Not Built For UNDIRECTED Graphs.");
            return null;
        } else{
            Stack<T> stack = new Stack<>();
            List<T> dfsPath = new ArrayList<>();
            Set<T> visited = new HashSet<>();

            stack.push(start);

            while(!stack.isEmpty()){
                T currVertex = stack.pop();
                if(!visited.contains(currVertex)){
                    visited.add(currVertex);
                    dfsPath.add(currVertex);
                }
                List<Edge> edgesFromCurr = adjacencyList.get(currVertex);
                edgesFromCurr.sort(Comparator.comparingInt(Edge::getWeight));
                for(Edge edge : edgesFromCurr.reversed()){
                    if(!visited.contains(edge.destination))
                        stack.push(edge.destination);
                }
            }

            return dfsPath;
        }
    }

    public List<T> BFS(T start){
        if(type != GraphType.DIRECTED){
            System.out.println("BFS Not Built For UNDIRECTED Graphs.");
            return null;
        } else{
            Queue<T> queue = new LinkedList<>();
            List<T> bfsPath = new ArrayList<>();
            Set<T> visited = new HashSet<>();

            queue.add(start);
            bfsPath.add(start);

            while(!queue.isEmpty()){
                T currVertex = queue.poll();
                List<Edge> edgesFromCurr = adjacencyList.get(currVertex);
                edgesFromCurr.sort(Comparator.comparingInt(Edge::getWeight));
                for(Edge edge : edgesFromCurr){
                    if(!visited.contains(edge.destination)){
                        queue.add(edge.destination);
                        visited.add(edge.destination);
                        bfsPath.add(edge.destination);
                    }
                }
            }

            return bfsPath;
        }
    }

    public void printTraversal(List<T> traversal){
        for(T str : traversal){
            System.out.print(str + " --> ");
        }
        System.out.println("Done");
    }

    class Edge {
        private T destination;
        private int weight;

        Edge(T destination, int weight){
            this.destination = destination;
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }
}

enum GraphType {
    DIRECTED,
    UNDIRECTED;
}
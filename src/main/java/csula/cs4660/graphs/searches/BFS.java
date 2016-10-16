package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * Breadth first search
 */
public class BFS implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
    	//System.out.println("soucr" + source + " diest " + dist);

		ArrayList<Edge> resultSet = new ArrayList<>();
		Set<Node> exploredSet = new HashSet<Node>();
		Set<Node> frontier = new HashSet<Node>();
		Queue<Node> queueOfNodes = new LinkedList<Node>();
		Map<Node,Node> parentNodes = new HashMap<Node,Node>();

		queueOfNodes.add(source);
		parentNodes.put(source,null);
		Node visitingNode = queueOfNodes.poll();
		//checks if the next node to be visted is a goal or not
		while((!visitingNode.equals(dist)) && (visitingNode != null)){
			if(!exploredSet.contains(visitingNode)){		
				exploredSet.add(visitingNode);
				frontier = new HashSet<Node>(graph.neighbors(visitingNode));
				for (Node eachNode : frontier) {
					queueOfNodes.add(eachNode);
					if(!parentNodes.containsKey(eachNode))
						parentNodes.put(eachNode,visitingNode);
				}				
			}
			visitingNode = queueOfNodes.poll();
		}
		Node parent;
		parent =  parentNodes.get(visitingNode);
		if(visitingNode.equals(dist)){
			while(parent != null){			
			resultSet.add(new Edge(parent,visitingNode,graph.distance(parent,visitingNode)));
			visitingNode = parent;
			parent = parentNodes.get(visitingNode);			
			}
			
		}	
		
//		for (Edge eachEdge : resultSet) {
//			System.out.println("print result");
//			System.out.println(eachEdge.getFrom()+ " "+ eachEdge.getTo() +"   " + eachEdge.getValue());
//		}
		Collections.reverse(resultSet);
		return  resultSet;
    }
}

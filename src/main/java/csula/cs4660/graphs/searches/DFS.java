package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * Depth first search
 */
public class DFS implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
    	System.out.println("soucr" + source + " diest " + dist);

		Stack<Node> stackOfNodes = new Stack<Node>();
		ArrayList<Edge> resultSet = new ArrayList<>();
		Set<Node> exploredSet = new HashSet<Node>();
		Set<Node> frontier = new HashSet<Node>();
		Map<Node,Node> parentNodes = new HashMap<Node,Node>();
		Boolean flag = false;

		Node vistingNode = source;
		stackOfNodes.push(source);
		parentNodes.put(source,null);

		while((!stackOfNodes.isEmpty()) && (!vistingNode.equals(dist)))
		{
			//visted			
			exploredSet.add(vistingNode);
			frontier = new HashSet<Node>(graph.neighbors(vistingNode));
			flag = false;
			if(frontier != null){
				for (Node eachNode : frontier) {
					if(!exploredSet.contains(eachNode)){
						stackOfNodes.push(eachNode);
						parentNodes.put(eachNode,vistingNode);
						flag = true;
						vistingNode = eachNode;
						break;
					}

				}
			}
			 
			if(!flag){ 
				stackOfNodes.pop();
				vistingNode = stackOfNodes.peek();
			}

		}
		
		Node parent;
		parent =  parentNodes.get(vistingNode);
		if(vistingNode.equals(dist)){
			while(parent != null){			
			resultSet.add(new Edge(parent,vistingNode,graph.distance(parent,vistingNode)));
			vistingNode = parent;
			parent = parentNodes.get(vistingNode);			
			}			
		}	
		
		for (Edge eachEdge : resultSet) {
			System.out.println("print result");
			System.out.println(eachEdge.getFrom()+ " "+ eachEdge.getTo() +"   " + eachEdge.getValue());
		}
		Collections.reverse(resultSet);
		return  resultSet;
    }
}

package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * As name, dijkstra search using graph structure
 */
public class DijkstraSearch implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
    	//System.out.println("soucr" + source + " diest " + dist);

		ArrayList<Edge> resultSet = new ArrayList<>();
		//unvisited node list
		Set<Node> exploredSet = new HashSet<Node>();
		Set<Node> allNodes = new HashSet<Node>();
		Set<Node> frontier = new HashSet<Node>();
		Map<Node,Node> previousNode = new HashMap<Node,Node>();
		Map<Node,Integer> distanceFromSource = new HashMap<Node,Integer>();
		int distance,newDistance ;

		Node visitngNode = source;
		previousNode.put(visitngNode,null);
		distanceFromSource.put(visitngNode,0);
		allNodes.add(visitngNode);

		while((visitngNode != null) && (!allNodes.isEmpty())){
			if(!exploredSet.contains(visitngNode)){
				exploredSet.add(visitngNode);
				allNodes.remove(visitngNode);
				frontier = new HashSet<Node>(graph.neighbors(visitngNode));
				for (Node eachNode : frontier) { 				
					if(previousNode.containsKey(eachNode)){
						distance = distanceFromSource.get(eachNode);
						newDistance = distanceFromSource.get(visitngNode) + graph.distance(visitngNode,eachNode);
						if(newDistance < distance){
							previousNode.put(eachNode,visitngNode);
							distanceFromSource.put(eachNode,newDistance);
						}
					}
					else{
						allNodes.add(eachNode);
						previousNode.put(eachNode,visitngNode);
						distanceFromSource.put(eachNode,graph.distance(visitngNode,eachNode) + distanceFromSource.get(visitngNode));
					}
				}
			}

			distance = Integer.MAX_VALUE;
			for (Map.Entry<Node,Integer> entry : distanceFromSource.entrySet())
			{
				if((entry.getValue() < distance) && (!exploredSet.contains(entry.getKey()))){
					distance = entry.getValue();
					visitngNode = entry.getKey();
				}
			}  
		}


		Node parent;
		parent =  previousNode.get(dist);
		visitngNode = dist;
		while(parent != null){			
			resultSet.add(new Edge(parent,visitngNode,graph.distance(parent,visitngNode)));
			visitngNode = parent;
			parent = previousNode.get(visitngNode);			
		}	

		Collections.reverse(resultSet);


		return resultSet;
    }
}

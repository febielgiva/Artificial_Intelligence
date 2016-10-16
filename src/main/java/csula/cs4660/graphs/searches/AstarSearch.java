package csula.cs4660.graphs.searches;

import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Perform A* search
 */
public class AstarSearch implements SearchStrategy {

	public AstarSearch(){

	}

	@Override
	public List<Edge> search(Graph graph, Node source, Node dist) {   

		ArrayList<Edge> resultSet = new ArrayList<>();
		Set<Node<Tile>> exploredSet = new HashSet<Node<Tile>>();
		Set<Node<Tile>> allNodes = new HashSet<Node<Tile>>();
		Set<Node> frontier = new HashSet<Node>();
		Map<Node<Tile>,Node<Tile>> previousNode = new HashMap<Node<Tile>,Node<Tile>>();
		Map<Node<Tile>,Integer> totalCost = new HashMap<Node<Tile>,Integer>();
		Map<Node<Tile>,Integer> gScore = new HashMap<Node<Tile>,Integer>();
		//distance to the target
		Map<Node<Tile>,Integer> hScore = new HashMap<Node<Tile>,Integer>();
		//distance to the next node

		List<Node<Tile>> nextLevelCheckNode;
		int movementCost = 1;

		int newTotalCost;
		int newGscore;
		int oldGscore;

		int comparingCost ;
		Node<Tile> visitngNode = source;
		previousNode.put(visitngNode,null);
		hScore.put(visitngNode,calculateHeuristic(visitngNode,dist));
		gScore.put(visitngNode,0);
		totalCost.put(visitngNode,hScore.get(source));
		allNodes.add(visitngNode);

		while((visitngNode != null) && (!visitngNode.equals(dist)) && (!allNodes.isEmpty())){
			System.out.println("visitn node    " + visitngNode.getData().getX() +" "+visitngNode.getData().getY()+" "+visitngNode.getData().getType());
			if(!exploredSet.contains(visitngNode)){
				exploredSet.add(visitngNode);
				allNodes.remove(visitngNode);
				frontier = new HashSet<Node>(graph.neighbors(visitngNode));
				for (Node<Tile> eachNode : frontier) {
					System.out.println("eachNode" + eachNode.getData().getX() +" "+eachNode.getData().getY()+" "+eachNode.getData().getType());
					if(!eachNode.getData().getType().equalsIgnoreCase("##")){
						//already explored//allNodes.containsKey(eachNode) will work
						if(previousNode.containsKey(eachNode)){
							//its not in closed list also
							if(!exploredSet.contains(eachNode)){
								newGscore = gScore.get(visitngNode) + movementCost;
								oldGscore = gScore.get(eachNode);
								if(newGscore < oldGscore){
									previousNode.put(eachNode,visitngNode);
									gScore.put(eachNode,newGscore);
									totalCost.put(eachNode,newGscore+hScore.get(eachNode));
								}
							}
						}
						//first time we are exploring it
						else{
							previousNode.put(eachNode,visitngNode);
							hScore.put(eachNode,calculateHeuristic(eachNode,dist));
							gScore.put(eachNode,gScore.get(visitngNode) + movementCost);
							newTotalCost = hScore.get(eachNode) + gScore.get(eachNode);
							totalCost.put(eachNode,newTotalCost);
							allNodes.add(eachNode);

						}
					}
				}		
			}

			//calculating next effective toatal cost
			comparingCost = 0;
			comparingCost = computeComparingCost(totalCost,exploredSet);

			nextLevelCheckNode = new ArrayList<Node<Tile>>();
			nextLevelCheckNode = getListOfNextPossibleNodes(comparingCost,totalCost,exploredSet);
			visitngNode = selectBestNextNode(nextLevelCheckNode,hScore);

//			if(visitngNode == null){
//				System.out.println("visitng node is null");
//				if(!allNodes.isEmpty()){
//					for (Node<Tile> eachAllNode : allNodes) {
//						comparingCost = 0;
//						comparingCost = computeComparingCost(totalCost,exploredSet);	
//						nextLevelCheckNode = new ArrayList<Node<Tile>>();
//						nextLevelCheckNode = getListOfNextPossibleNodes(comparingCost,totalCost,exploredSet);
//						System.out.println("visitn node" + visitngNode.getData().getX() +" "+visitngNode.getData().getY()+" "+visitngNode.getData().getType());
//						visitngNode = selectBestNextNode(nextLevelCheckNode,hScore);
//					}
//				}
//			}

		}
		System.out.println(previousNode.containsValue(dist));
		Node<Tile> parent;
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


	private List<Node<Tile>> getListOfNextPossibleNodes(int comparingCost, Map<Node<Tile>, Integer> totalCost,
			Set<Node<Tile>> exploredSet) {
		List <Node<Tile>>list = new ArrayList<Node<Tile>>();
		for(Node<Tile> eachNode:totalCost.keySet()){
			if((totalCost.get(eachNode) == comparingCost) && (!exploredSet.contains(eachNode))) {
				list.add(eachNode);
			}
		}
		return list;
	}

	private Node<Tile> selectBestNextNode(List<Node<Tile>> nextLevelCheckNode, Map<Node<Tile>, Integer> hScore) {
		int comparingCost = Integer.MAX_VALUE;
		Node<Tile> vistingNode = null;
		for (Node<Tile> eachNode : nextLevelCheckNode) {
			if(hScore.get(eachNode) < comparingCost){
				comparingCost = hScore.get(eachNode);
				vistingNode = eachNode;
			}
		}
		return vistingNode;
	}


	private int computeComparingCost(Map<Node<Tile>, Integer> totalCost, Set<Node<Tile>> exploredSet) {
		int comparingCost = Integer.MAX_VALUE;
		for (Map.Entry<Node<Tile>,Integer> entry : totalCost.entrySet())
		{
			if((entry.getValue() < comparingCost) && (!exploredSet.contains(entry.getKey()))){
				comparingCost = entry.getValue();
			}
		} 
		return comparingCost;
	}



	private Integer calculateHeuristic(Node<Tile> visitngNode, Node<Tile> dist) {
		int x = Math.abs(dist.getData().getX() - visitngNode.getData().getX());
		int y = Math.abs(dist.getData().getY() - visitngNode.getData().getY());
		return (x+y);
	}


}

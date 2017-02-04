package csula.cs4660.quizes;

import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.representations.Representation.STRATEGY;
import csula.cs4660.graphs.utils.Parser;
import csula.cs4660.quizes.models.DTO;
import csula.cs4660.quizes.models.PossibleRoute;
import csula.cs4660.quizes.models.State;
import csula.cs4660.quizes.models.DTO;

import java.util.*;

import com.google.common.annotations.VisibleForTesting;

/**
 * Here is your quiz entry point and your app
 */
public class App {

	static Graph graph;
	public static void main(String[] args) {
		BFSMyImplementation();
		
		//		graph = buildGraphs();
		//		parserData();
		DijkstraMyImplementation();
		ASearchImplementation();

	}

	private static void DijkstraMyImplementation() {
		int count = 0;
		//ArrayList<> resultSet = new ArrayList<>();
		//unvisited node list
		Set<State> exploredSet = new HashSet<State>();
		State[] frontier ;
		PriorityQueue queue = new PriorityQueue();
		Map<State,State> previousNode = new HashMap<State,State>();
		int distance,newDistance ;
		
		State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
		State destinationState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();
		count++;count++;
		previousNode.put(initialState,null);
		queue.enqueue(initialState, 0);
		State visitngNode;


		while(!queue.isEmpty()){
			//System.out.println(visitngNode);
			visitngNode = queue.dequeue();
			exploredSet.add(visitngNode);
			frontier = Client.getState(visitngNode.getId()).get().getNeighbors();
			count++;
			for (State eachNode : frontier) {		
				if(previousNode.containsKey(eachNode)){
						distance = queue.getValue(eachNode);	
						Optional<DTO> opt = Client.stateTransition(visitngNode.getId(), eachNode.getId());
						csula.cs4660.quizes.models.Event event = opt.get().getEvent();
						newDistance = queue.getValue(visitngNode) + event.getEffect();
						if(newDistance > distance){
							previousNode.put(eachNode,visitngNode);
							queue.enqueue(eachNode, newDistance);
						}
						
					}
					else{
						Optional<DTO> opt = Client.stateTransition(visitngNode.getId(), eachNode.getId());
						csula.cs4660.quizes.models.Event event = opt.get().getEvent();
						previousNode.put(eachNode,visitngNode);
						queue.enqueue(eachNode,event.getEffect() + queue.getValue(visitngNode));


					}
				}
			}
			
		
		int depth =0 ;
		ArrayList<Optional<DTO>> resultSet = new ArrayList<Optional<DTO>>();
		State parent;
		int effect = 0;
		parent =  previousNode.get(destinationState);
		visitngNode = destinationState;
		System.out.println(visitngNode.getId());
		while(parent != null){	
			//System.out.println(parent.getId());
			Optional<DTO> opt = Client.stateTransition(parent.getId(), visitngNode.getId());
			csula.cs4660.quizes.models.Event event = opt.get().getEvent();
			resultSet.add(opt);
			effect += event.getEffect();
			visitngNode = parent;
			parent = previousNode.get(visitngNode);		
			depth++;
		}	
		System.out.println("Total request"+count);
		System.out.println(depth);
		System.out.println(effect);
		Collections.reverse(resultSet);
		printResult(initialState,resultSet);

	}

	private static Graph buildGraphs() {
		Graph graph1 = new Graph(Representation.of(Representation.STRATEGY.ADJACENCY_LIST));
		return graph1;
	}

	private static void BFSMyImplementation() {
		State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
		State destinationState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();
		System.out.println(initialState);

		ArrayList<Optional<DTO>> resultSet = new ArrayList<Optional<DTO>>();
		Set<State> exploredSet = new HashSet<State>();
		State[] frontier ;
		Queue<State> queueOfNodes = new LinkedList<State>();
		Map<State,State> parentNodes = new HashMap<State,State>();

		queueOfNodes.add(initialState);
		parentNodes.put(initialState,null);
		State visitingNode = queueOfNodes.poll();
		//checks if the next node to be visted is a goal or not
		while((!visitingNode.equals(destinationState)) && (visitingNode != null)){
			if(!exploredSet.contains(visitingNode)){		
				exploredSet.add(visitingNode);
				frontier = Client.getState(visitingNode.getId()).get().getNeighbors();
				for (State eachNode : frontier) {
					
					queueOfNodes.add(eachNode);
					if(!parentNodes.containsKey(eachNode))
						parentNodes.put(eachNode,visitingNode);
				}				
			}
			visitingNode = queueOfNodes.poll();
		}
		State parent;
		parent =  parentNodes.get(visitingNode);
		int depth = 0;
		System.out.println(visitingNode.getId());
		if(visitingNode.equals(destinationState)){
			while(parent != null){	
				//System.out.println(parent.getId());
				Optional<DTO> opt = Client.stateTransition(parent.getId(), visitingNode.getId());
				csula.cs4660.quizes.models.Event event = opt.get().getEvent();
				resultSet.add(opt);
				visitingNode = parent;
				parent = parentNodes.get(visitingNode);		
				depth++;
			}

		}	

		System.out.println("BFS Depth to find the path is "+depth);
		Collections.reverse(resultSet);
		printResult(initialState,resultSet);


	}

	private static void printResult(State initialState, ArrayList<Optional<DTO>> resultSet) {
		int effect = 0;
		String previousState = initialState.getLocation().getName();
		for (Optional<DTO> eachOPT : resultSet) {
			csula.cs4660.quizes.models.Event event = eachOPT.get().getEvent();
			System.out.println(previousState + "  --->  " + eachOPT.get().getAction() + "     Effect  =  " +
					event.getEffect() + " // " +event.getName() + "  ( " + event.getDescription() + "  )"); 
			previousState = eachOPT.get().getAction();
			effect += event.getEffect();
		}
		System.out.println("Total Effect : " + effect );
		
	}

	private static Graph parserData() {
		Queue<State> frontier = new LinkedList<>();
		Set<State> exploredSet = new HashSet<>();

		State current = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();	
		frontier.add(current);
		int count =0 ;
		graph.addNode(new Node<State>(current));
		while(!frontier.isEmpty()){
			current = frontier.poll();
			for (State neighbor: Client.getState(current.getId()).get().getNeighbors()) {
				if(!exploredSet.contains(current)){
					frontier.add(neighbor);
					exploredSet.add(current);
					graph.addNode(new Node<State>(neighbor));
					Optional<DTO> opt = Client.stateTransition(current.getId(), neighbor.getId());
					csula.cs4660.quizes.models.Event event = opt.get().getEvent();
					graph.addEdge(new Edge(new Node<State>(current),new Node<State>(neighbor),event.getEffect()));
					count++;
				}
				else{
					if(!graph.adjacent(new Node<State>(current),new Node<State>(neighbor))){
						Optional<DTO> opt = Client.stateTransition(current.getId(), neighbor.getId());
						csula.cs4660.quizes.models.Event event = opt.get().getEvent();
						graph.addEdge(new Edge(new Node<State>(current),new Node<State>(neighbor),event.getEffect()));
					}
				}
			}
		}
		System.out.println(count);
		return graph;
	}
	
	
	
	
	////////////////////
	
	public static void  ASearchImplementation() {   
		
		
		State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
		State destinationState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();
		int count = 2;
		ArrayList<Optional<DTO>> resultSet = new ArrayList<Optional<DTO>>();
		Set<State> exploredSet = new HashSet<State>();
		Set<State> allNodes = new HashSet<State>();
		State[] frontier;
		Map<State,State> previousNode = new HashMap<State,State>();
		Map<State,Integer> totalCost = new HashMap<State,Integer>();
		Map<State,Integer> gScore = new HashMap<State,Integer>();
		//distance to the target
		Map<State,Integer> hScore = new HashMap<State,Integer>();
		//distance to the next node

		List<State> nextLevelCheckNode;
		int movementCost = 1;

		int newTotalCost;
		int newGscore;
		int oldGscore;

		int comparingCost ;
		State visitngNode = initialState;
		previousNode.put(visitngNode,null);
		hScore.put(visitngNode,0);
		gScore.put(visitngNode,0);
		totalCost.put(visitngNode,hScore.get(initialState));
		allNodes.add(visitngNode);

		while((visitngNode != null) && (!visitngNode.equals(destinationState)) && (!allNodes.isEmpty())){
			//System.out.println("visitn node    " + visitngNode.getData().getX() +" "+visitngNode.getData().getY()+" "+visitngNode.getData().getType());
			if(!exploredSet.contains(visitngNode)){
				exploredSet.add(visitngNode);
				allNodes.remove(visitngNode);
				frontier = Client.getState(visitngNode.getId()).get().getNeighbors();
				count++;
				for (State neighbor : frontier) {
					Optional<DTO> opt = Client.stateTransition(visitngNode.getId(), neighbor.getId());
					csula.cs4660.quizes.models.Event event = opt.get().getEvent();
				
					//System.out.println("eachNode" + eachNode.getData().getX() +" "+eachNode.getData().getY()+" "+eachNode.getData().getType());
					
						//already explored//allNodes.containsKey(eachNode) will work
						if(previousNode.containsKey(neighbor)){
							//its not in closed list also
							if(!exploredSet.contains(neighbor)){
								if(event.getEffect()>=0){
								movementCost = event.getEffect(); 
								newGscore = gScore.get(visitngNode) + movementCost;
								oldGscore = gScore.get(neighbor);
								if(newGscore < oldGscore){
									previousNode.put(neighbor,visitngNode);
									gScore.put(neighbor,newGscore);
									totalCost.put(neighbor,newGscore+hScore.get(neighbor));
								}
								}
								
							}
						}
					
						//first time we are exploring it
						else{
								previousNode.put(neighbor,visitngNode);
								movementCost = event.getEffect();
								hScore.put(neighbor,calculateHeuristic(visitngNode,neighbor,destinationState));
								gScore.put(neighbor,gScore.get(visitngNode) + movementCost);
								newTotalCost = hScore.get(neighbor) + gScore.get(neighbor);
								totalCost.put(neighbor,newTotalCost);
								allNodes.add(neighbor);
						}
				}		
			}

			//calculating next effective toatal cost
			comparingCost = 0;
			comparingCost = computeComparingCost(totalCost,exploredSet);

			nextLevelCheckNode = new ArrayList<State>();
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
		//System.out.println(previousNode.containsValue(dist));
		
		int depth =0 ;
		State parent;
		int effect = 0;
		parent =  previousNode.get(destinationState);
		visitngNode = destinationState;
		System.out.println(visitngNode.getId());
		while(parent != null){	
			
			Optional<DTO> opt = Client.stateTransition(parent.getId(), visitngNode.getId());
			csula.cs4660.quizes.models.Event event = opt.get().getEvent();
			resultSet.add(opt);
			effect += event.getEffect();
			System.out.println(parent.getId()+" effect : "+effect);
			visitngNode = parent;
			parent = previousNode.get(visitngNode);		
			depth++;
		}	
		System.out.println("Total Request"+count);
		System.out.println(depth);
		System.out.println(effect);
		Collections.reverse(resultSet);
		printResult(initialState,resultSet);

	}


	private static Integer calculateHeuristic(State visitngNode, State neighbor, State destinationState) {
//		Optional<DTO> opt = Client.stateTransition(visitngNode.getId(), visitngNode.getId());
//		csula.cs4660.quizes.models.Event event = opt.get().getEvent();
//		return event.getEffect();
		
		return BFS(neighbor,destinationState);
	}
	
	private static Map<State, Integer> nodesHeurisicValue = new HashMap<State, Integer>();


	private static Integer BFS(State neighbor, State destinationState) {
//		ArrayList<Optional<DTO>> resultSet = new ArrayList<Optional<DTO>>();
		
		if(nodesHeurisicValue !=  null){
			if(nodesHeurisicValue.containsKey(neighbor)){
				return nodesHeurisicValue.get(neighbor);
			}
		}
		
		
		
		
		Set<State> exploredSet = new HashSet<State>();
		State[] frontier ;
		Queue<State> queueOfNodes = new LinkedList<State>();
		Map<State,State> parentNodes = new HashMap<State,State>();

		queueOfNodes.add(neighbor);
		parentNodes.put(neighbor,null);
		State visitingNode = queueOfNodes.poll();
		//checks if the next node to be visted is a goal or not
		while(visitingNode != null){
			
			if(visitingNode.equals(destinationState)){
				break;
			}
			if(!exploredSet.contains(visitingNode)){		
				exploredSet.add(visitingNode);
				frontier = Client.getState(visitingNode.getId()).get().getNeighbors();
				for (State eachNode : frontier) {	
					queueOfNodes.add(eachNode);
					if(!parentNodes.containsKey(eachNode))
						parentNodes.put(eachNode,visitingNode);
				}				
			}
			visitingNode = queueOfNodes.poll();
		}
		State parent;
		if(visitingNode != null){
		parent =  parentNodes.get(visitingNode);
		int depth = 0;
//		System.out.println(visitingNode.getId());
		if(visitingNode.equals(destinationState)){
			while(parent != null){	
				if(nodesHeurisicValue !=  null){
					if(!nodesHeurisicValue.containsKey(parent)){
						nodesHeurisicValue.put(parent,depth);
					}
				}
//				//System.out.println(parent.getId());
//				Optional<DTO> opt = Client.stateTransition(parent.getId(), visitingNode.getId());
//				csula.cs4660.quizes.models.Event event = opt.get().getEvent();
//				resultSet.add(opt);
				visitingNode = parent;
				parent = parentNodes.get(visitingNode);		
				depth++;
				
			}

		}
		System.out.println(neighbor.getId() +"        "+depth);
		return depth;
		}
		else{
			System.out.println(neighbor.getId() +"        "+"0");
			return 0;
		}

		

	}

	private static List<State> getListOfNextPossibleNodes(int comparingCost, Map<State, Integer> totalCost,
			Set<State> exploredSet) {
		List <State>list = new ArrayList<State>();
		for(State eachNode:totalCost.keySet()){
			if((totalCost.get(eachNode) == comparingCost) && (!exploredSet.contains(eachNode))) {
				list.add(eachNode);
			}
		}
		return list;
	}

	private static State selectBestNextNode(List<State> nextLevelCheckNode, Map<State, Integer> hScore) {
		int comparingCost = Integer.MIN_VALUE;
		State vistingNode = null;
		for (State eachNode : nextLevelCheckNode) {
			if(hScore.get(eachNode) > comparingCost){
				comparingCost = hScore.get(eachNode);
				vistingNode = eachNode;
			}
		}
		return vistingNode;
	}


	private static int computeComparingCost(Map<State, Integer> totalCost, Set<State> exploredSet) {
		int comparingCost = Integer.MIN_VALUE;
		for (Map.Entry<State,Integer> entry : totalCost.entrySet())
		{
			if((entry.getValue() > comparingCost) && (!exploredSet.contains(entry.getKey()))){
				comparingCost = entry.getValue();
			}
		} 
		return comparingCost;
	}



//	private static Integer calculateHeuristic(State visitngNode, State dist) {
//		Optional<DTO> opt = Client.stateTransition(visitngNode.getId(), visitngNode.getId());
//		csula.cs4660.quizes.models.Event event = opt.get().getEvent();
////		int x = Math.abs(dist.getData().getX() - visitngNode.getData().getX());
////		int y = Math.abs(dist.getData().getY() - visitngNode.getData().getY());
//		return 1;
//	}




}




}

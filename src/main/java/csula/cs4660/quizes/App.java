package csula.cs4660.quizes;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.representations.Representation.STRATEGY;
import csula.cs4660.graphs.utils.Parser;
import csula.cs4660.quizes.models.DTO;
import csula.cs4660.quizes.models.PossibleRoute;
import csula.cs4660.quizes.models.State;

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



	}

	private static void DijkstraMyImplementation() {
		//ArrayList<> resultSet = new ArrayList<>();
		//unvisited node list
		Set<State> exploredSet = new HashSet<State>();
		State[] frontier ;
		PriorityQueue queue = new PriorityQueue();
		Map<State,State> previousNode = new HashMap<State,State>();
		int distance,newDistance ;
		
		State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
		State destinationState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();

		previousNode.put(initialState,null);
		queue.enqueue(initialState, 0);
		State visitngNode;


		while(!queue.isEmpty()){
			//System.out.println(visitngNode);
			visitngNode = queue.dequeue();
			exploredSet.add(visitngNode);
			frontier = Client.getState(visitngNode.getId()).get().getNeighbors();
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
						previousNode.put(eachNode,visitngNode);
						Optional<DTO> opt = Client.stateTransition(visitngNode.getId(), eachNode.getId());
						csula.cs4660.quizes.models.Event event = opt.get().getEvent();
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


}
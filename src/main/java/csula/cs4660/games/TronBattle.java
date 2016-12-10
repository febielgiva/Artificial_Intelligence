package csula.cs4660.games;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.*;
import java.util.*;


import com.google.common.base.Stopwatch;



/* *********************************************Coord Class********************************************* */
class Coord {
	final int x;
	final int y;


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	public Coord(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) return true;
		if (!(o instanceof Coord)) {
			return false;
		}

		Coord coord = (Coord) o;

		return  coord.x == x && coord.y == y;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + x;
		result = 31 * result + y;
		return result;
	}
}


class Tile {
	private final int x;
	private final int y;
	private final String type;

	public Tile(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Tile)) return false;

		Tile tile = (Tile) o;

		if (getX() != tile.getX()) return false;
		if (getY() != tile.getY()) return false;
		return true;
		//return getType() != null ? getType().equals(tile.getType()) : tile.getType() == null;

	}

	@Override
	public int hashCode() {
		int result = getX();
		result = 31 * result + getY();
		//result = 31 * result + (getType() != null ? getType().hashCode() : 0);
		return result;
	}
}

/* *********************************************Node Class********************************************* */

class Node<T> {
	private T data;

	public Node(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Node{" +
				"data=" + data +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Node)) return false;

		Node<?> node = (Node<?>) o;

		return getData() != null ? getData().equals(node.getData()) : node.getData() == null;

	}

	@Override
	public int hashCode() {
		return getData() != null ? getData().hashCode() : 0;
	}

	public void setData(T data) {
		this.data = data;

	}
}

/* *********************************************Edge Class********************************************* */

class Edge {
	private Node from;
	private Node to;
	private int value;

	public Edge(Node from, Node to, int value) {
		this.from = from;
		this.to = to;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Node getFrom() {
		return from;
	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public Node getTo() {
		return to;
	}

	public void setTo(Node to) {
		this.to = to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Edge)) return false;

		Edge edge = (Edge) o;

		if (getValue() != edge.getValue()) return false;
		if (getFrom() != null ? !getFrom().equals(edge.getFrom()) : edge.getFrom() != null)
			return false;
		return !(getTo() != null ? !getTo().equals(edge.getTo()) : edge.getTo() != null);

	}

	@Override
	public String toString() {
		return "Edge{" +
				"from=" + from +
				", to=" + to +
				", value=" + value +
				'}';
	}

	@Override
	public int hashCode() {
		int result = getFrom() != null ? getFrom().hashCode() : 0;
		result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
		result = 31 * result + getValue();
		return result;
	}
}

class MinMaxState {
    private int grid[][] = new int[20][30];
    private Node node;
    private int value;

    public MinMaxState(int grid[][],Node node, int value) {
        this.grid = grid;
        this.node = node;
        this.value = value;
    }

    public int[][] getGrid() {
        return grid;
    }
    
    public Node getNodeFromState() {
        return this.node;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof MinMaxState)) return false;
//
//        MinMaxState that = (MinMaxState) o;
//
//        ifgetGrid() == that.getGrid();
//
//    }

//    @Override
//    public int hashCode() {
//        return getGrid().hashCode();
//    }
}

/* *********************************************AdjacencyList Class********************************************* */

class AdjacencyList{
	private Map<Node, Collection<Edge>> adjacencyList;

	public AdjacencyList(File file) {

		BufferedReader br = null;
		Node fromNode;
		Node toNode;
		Edge eachEdge;
		Collection< Edge > collectionOfEdge ;
		int value = 0;
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new FileReader(file));
			int noOfVertices =Integer.parseInt(buffer.readLine().trim());
			String line;
			adjacencyList= new HashMap<Node,Collection<Edge>>();
			while ((line = buffer.readLine()) != null) {
				String[] vals = line.trim().split("\\:");
				fromNode = new Node<Integer>(Integer.parseInt(vals[0]));
				toNode   = new Node<Integer>(Integer.parseInt(vals[1]));
				value    = Integer.parseInt(vals[2]);
				eachEdge     = new Edge(fromNode, toNode, value);
				collectionOfEdge = new ArrayList<Edge>();
				if(adjacencyList == null){
					collectionOfEdge.add(eachEdge);
					adjacencyList.put(fromNode, collectionOfEdge);
				}
				else if(adjacencyList.containsKey(fromNode)){
					collectionOfEdge = adjacencyList.get(fromNode);
					collectionOfEdge.add(eachEdge);
					adjacencyList.put(fromNode, collectionOfEdge);
				}
				else{
					collectionOfEdge.add(eachEdge);
					adjacencyList.put(fromNode, collectionOfEdge);
				}


			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected AdjacencyList() {
		adjacencyList= new HashMap<Node,Collection<Edge>>();
	}

	public boolean adjacent(Node x, Node y) {

		if(adjacencyList.containsKey(x)){
			//System.out.println("node x is present" + x.getData() + y.getData());
			Collection< Edge > collectionOfEdge = new ArrayList<Edge>();
			collectionOfEdge = adjacencyList.get(x);
			if(collectionOfEdge != null){
				for (Iterator iterator = collectionOfEdge.iterator(); iterator.hasNext();) {
					Edge edge = (Edge) iterator.next();
					if(edge.getTo().equals(y)){
						return true;
					}
				}
			}
			return false;
		}
		else
			return false;

	}


	public List<Node> neighbors(Node x) {

		List<Node> expectedList= new ArrayList<Node>();
		if(adjacencyList.containsKey(x)){
			//System.out.println("x is present");
			Collection<Edge> collectionOfEdge = new ArrayList<Edge>();
			collectionOfEdge = adjacencyList.get(x);
			if(collectionOfEdge != null){
				for (Iterator iterator = collectionOfEdge.iterator(); iterator.hasNext();) {
					Edge edge = (Edge) iterator.next();
					expectedList.add(edge.getTo());
				}
			}
		}

		return expectedList;
	}

	public boolean addNode(Node x) {
		if(!adjacencyList.equals(null)){
			if((!adjacencyList.containsKey(x))){
				adjacencyList.put(x, null);
				return true;
			}
			else{

				return false;
			}
		}
		return false;
	}

	public boolean removeNode(Node x) {
		if(adjacencyList.containsKey(x)){
			adjacencyList.remove(x);
			for (Map.Entry<Node, Collection<Edge>> entry : adjacencyList.entrySet()) {
				Node key = entry.getKey();
				Collection<Edge> value = entry.getValue();
				for (Iterator iterator = value.iterator(); iterator.hasNext();) {
					Edge edge = (Edge) iterator.next();
					if(edge.getTo().equals(x)){
						iterator.remove();

					}
				}
			}

			return true;
		}
		else{
			return false;
		}

	}


	public boolean addEdge(Edge x) {

		if(adjacencyList.containsKey(x.getFrom())){
			//			Tile tile = (Tile) x.getFrom().getData();
			//			System.out.println("from node is present" + tile.getType()+" "+tile.getX() +" "+ tile.getY());
			//			System.out.println("to node is present" + x.getTo());

			Collection<Edge> collectionOfEdge = adjacencyList.get(x.getFrom());
			if(collectionOfEdge !=null){
				if(collectionOfEdge.contains(x)){
					return false;
				}
				else{
					collectionOfEdge.add(x);
					adjacencyList.put(x.getFrom(), collectionOfEdge);
					return true;

				}
			}
			else{

				collectionOfEdge = new ArrayList<Edge>();
				collectionOfEdge.add(x);
				adjacencyList.put(x.getFrom(), collectionOfEdge);
				return true;

			}

		}
		return false;

	}


	/*
	 * boolean flag = true;
		if(adjacencyList.containsKey(x.getFrom())){
			//System.out.println(x.getFrom());
			Collection<Edge> collectionOfEdge = adjacencyList.get(x.getFrom());
			if(collectionOfEdge !=null){
			if(collectionOfEdge.contains(x)){
				flag = false;
				return false;
			}
			}
			//			for (Iterator iterator = collectionOfEdge.iterator(); iterator.hasNext();) {
			//				Edge edge = (Edge) iterator.next();
			//				System.out.println(edge.getFrom() +" "+ edge.getTo());
			//				//if edge exits
			//				if(edge.getTo().equals(x.getTo())){
			//					System.out.println("return false");
			//					flag = false;
			//					return false;
			//				}
			//			}
			//no edge exist so add
			if(flag){	
				if(adjacencyList.containsKey(x.getTo()))
				{	
				//System.out.println("return true getTO already prsent");
				collectionOfEdge = adjacencyList.get(x.getFrom());
				if(collectionOfEdge !=null)
					collectionOfEdge.add(x);
				else{
					collectionOfEdge = new ArrayList<Edge>();
					collectionOfEdge.add(x);
				}
				adjacencyList.put(x.getFrom(), collectionOfEdge);
				return true;
				}
				else{
					//System.out.println("return true getTO not already prsent");
					collectionOfEdge = adjacencyList.get(x.getFrom());
					if(collectionOfEdge !=null)
						collectionOfEdge.add(x);
					else{
						collectionOfEdge = new ArrayList<Edge>();
						collectionOfEdge.add(x);
					}
					adjacencyList.put(x.getFrom(), collectionOfEdge);
					adjacencyList.put(x.getTo(),null);
					return true;
				}

			}
		}

		return false;
	 */


	public boolean removeEdge(Edge x) {
		if(adjacencyList.containsKey(x.getFrom())){
			Collection<Edge> collectionOfEdge = adjacencyList.get(x.getFrom());
			if(collectionOfEdge.contains(x)){
				collectionOfEdge.remove(x);
				adjacencyList.put(x.getFrom(),collectionOfEdge);
				return true;
			}
		}
		return false;
	}

	public int distance(Node from, Node to) {

		if(adjacencyList.containsKey(from)){
			Collection<Edge> collectionOfEdge = adjacencyList.get(from);
			for (Edge edge : collectionOfEdge) {
				if((edge.getFrom().equals(from)) &&(edge.getTo().equals(to))){
					return edge.getValue();
				}
			}
		}
		return 0;
	}

	public Optional<Node> getNode(int index) {
		List<Node> keys = new ArrayList<Node>(adjacencyList.keySet());		
		Node node = keys.get(index);
		return Optional.of(node);
	}

	public Optional<Node> getNode(Node node) {

		Iterator<Node> iterator = adjacencyList.keySet().iterator();
		Optional<Node> result = Optional.empty();
		while (iterator.hasNext()) {
			Node next = iterator.next();
			if (next.equals(node)) {
				result = Optional.of(next);
				break;
			}
		}
		return result;
	}


}


/* *********************************************Main Class********************************************* */

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class TronBattle {

	//graph 
	//static private Stopwatch timer;
	static AdjacencyList graph= new AdjacencyList();
	static Map <Coord,Tile> inputMap = new HashMap<Coord,Tile>();
	static List<Node<Tile>> enemyLocation = new ArrayList<Node<Tile>>();
	static int adjMatrix[][] = new int[20][30];
	static int floodFill[][] = new int[20][30];
	public static void main(String args[]) {
		//timer = Stopwatch.createStarted();
		//constructs a bidirectional graph
		constructGraph();
		//checkConstructedGraph();
		//19 3 19 3 27 18 27 18
		//19 3 18 3 27 18 26 18

		for (int i = 0; i < 20; i++) {
			Arrays.fill(adjMatrix[i], -1);
		}
		Scanner in = new Scanner(System.in);
		int currentX=0,currentY=0;


		// game loop
		while (true) {
			enemyLocation = new ArrayList<Node<Tile>>();

			int N = in.nextInt(); // total number of players (2 to 4).
			int P = in.nextInt(); // your player number (0 to 3).
			System.err.println(N+" "+P);

			for (int i = 0; i < N; i++) {
				int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
				int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
				int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
				int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)
				//if not -1 then store the enemy's name else -1
				System.err.println(X0+" "+Y0+" "+X1+" "+Y1+" ");
				if((X0 != -1) && (Y0 !=-1)){
				    if(P!=i){
					    enemyLocation.add(constructNode(Y1, X1,i+""));
				    }
					graph.getNode(constructNode(Y1,X1,null)).get().setData(new Tile(Y1, X1,i+""));
					graph.getNode(constructNode(Y1,X1,null)).get();
					adjMatrix[Y1][X1] = i;

				}
				else{
					//path -1;
					eraseNodeEntry(i);
				}
				if(P==i){
					currentX =X1;
					currentY= Y1;
				}


			}

			selectMethodToCrawl(constructNode(currentY, currentX,P+""));		
			//System.err.println("graph constructed in  " +timer.stop());
		}
	}



	/*
2 0
3
4
3
4
1
1
1
1*/

	private static void eraseNodeEntry(int gamerToDelete) {
		for(int i=0;i<20;i++){
			for(int j=0;j<30;j++){
				if(adjMatrix[i][j] == gamerToDelete){
				graph.getNode(constructNode(i,j,i+"")).get().setData(new Tile(i, j,null));
				adjMatrix[i][j] = -1;
				}
			}
		}
		
	}



	private static void selectMethodToCrawl(Node<Tile> player) {
		boolean enemyFollow= false;
		int nearestEnemyDistance = Integer.MAX_VALUE;
		Node<Tile> nearestEnemy = enemyLocation.get(0);
		for (Node<Tile> eachEnemy : enemyLocation) {
			int tempDistance = calculateDistanceOfEnemy(player,eachEnemy);
			if( tempDistance < 10){
				enemyFollow = true;
				if(nearestEnemyDistance > tempDistance){
					nearestEnemy = eachEnemy;
					nearestEnemyDistance = tempDistance;
				}
			}
		}
		if(enemyFollow){
			System.err.println("MinMax");
			System.err.println("Enemy"+((Tile)nearestEnemy.getData()).getY()+ "   "+
					((Tile)nearestEnemy.getData()).getX());

			MinMaxState bestMoveForPlayer = minmax(new MinMaxState(adjMatrix,player,0),new MinMaxState(adjMatrix,nearestEnemy, 0),5,true);
			System.err.println("best Move from MinMax"+((Tile)bestMoveForPlayer.getNodeFromState().getData()).getY()+ "   "+
					((Tile)bestMoveForPlayer.getNodeFromState().getData()).getX()+" value"+bestMoveForPlayer.getValue());
			
			
			
			bestMoveForPlayer = minmaxList.get(minmaxList.size()-2);			
			System.err.println("best Move from MinMax");
//			for (MinMaxState eachState : minmaxList) {
//				bestMoveForPlayer = minmaxList.get(minmaxList.size()-1);
//				System.err.println(((Tile)bestMoveForPlayer.getNodeFromState().getData()).getY()+ "   "+
//						((Tile)bestMoveForPlayer.getNodeFromState().getData()).getX()+" value  "+bestMoveForPlayer.getValue());
//			}
			
			System.err.println("best Move from MinMax"+((Tile)bestMoveForPlayer.getNodeFromState().getData()).getY()+ "   "+
					((Tile)bestMoveForPlayer.getNodeFromState().getData()).getX()+" value"+bestMoveForPlayer.getValue());
			minmaxList = new ArrayList<MinMaxState>();
			System.out.println(converEdgesToAction(player, bestMoveForPlayer.getNodeFromState()));
			
		}
		else{
			int maxDept = findLongestPath(player);
			System.err.println("longest Path"+max+"paths"+bestResult.size());
			System.err.println(((Tile)bestResult.get(1).getData()).getY() +"   "+ ((Tile)bestResult.get(1).getData()).getX());
			//		System.err.println(max+" "+bestResult.size());
			System.out.println(converEdgesToAction(player, bestResult.get(1)));
			result= new ArrayList<Node<Tile>>();
			visitedNodes = new ArrayList<Node<Tile>>();		
			bestResult = new ArrayList<Node<Tile>>();
			max = 0; dist =0 ;
		}

	}






	static List<MinMaxState> minmaxList = new ArrayList<MinMaxState>();
	private static MinMaxState minmax(MinMaxState playerState, MinMaxState enemyState,int depth, boolean max) {
		
		MinMaxState bestMoveNode = null,newNode,temp,updateNode,minMaxTemp;
		int bestMove = 0;
		if(max){
			List<MinMaxState> neighbours = new ArrayList<MinMaxState>();
			neighbours =  getValidNeighbors(playerState,enemyState);
			if((depth == 0) || neighbours.size() == 0){
				minMaxTemp = floodfill(playerState);
				return new MinMaxState(minMaxTemp.getGrid(),minMaxTemp.getNodeFromState(), minMaxTemp.getValue());
			}
			else{
				bestMove = Integer.MIN_VALUE;
				for (MinMaxState eachPlayerState : neighbours) {
					newNode = minmax(eachPlayerState,enemyState, depth-1, false);
					System.err.println(((Tile)newNode.getNodeFromState().getData()).getY()+ "   "+
					((Tile)newNode.getNodeFromState().getData()).getX()+" value  "+newNode.getValue());
					minmaxList.add(newNode);
					if(bestMove < newNode.getValue())
					{
						bestMove = newNode.getValue();
						bestMoveNode = newNode;
						
					}
					else if(bestMove == newNode.getValue()){
					    bestMoveNode = compareOpenPaths(bestMoveNode,newNode);
					}
					
				}
				updateNode =new MinMaxState(playerState.getGrid(),playerState.getNodeFromState(),bestMoveNode.getValue());
				return updateNode;
			}
		}
		else{
			List<MinMaxState> neighbours = new ArrayList<MinMaxState>();
			neighbours =  getValidNeighbors(enemyState,playerState);
			if((depth == 0) || neighbours.size() == 0){
				minMaxTemp = floodfill(enemyState);
				return new MinMaxState(minMaxTemp.getGrid(),minMaxTemp.getNodeFromState(), minMaxTemp.getValue());
			}
			else{
				bestMove = Integer.MAX_VALUE;
				for (MinMaxState eachEnemyState : neighbours) {
					newNode = minmax(playerState,eachEnemyState, depth-1, true);
					System.err.println(((Tile)newNode.getNodeFromState().getData()).getY()+ "   "+
							((Tile)newNode.getNodeFromState().getData()).getX()+" value  "+newNode.getValue());
					minmaxList.add(newNode);
					if(bestMove > newNode.getValue())
					{
						bestMove = newNode.getValue();
						bestMoveNode = newNode;	
					}
					else if(bestMove == newNode.getValue()){
					    bestMoveNode = compareOpenPaths(bestMoveNode,newNode);
					}
					
				}
				updateNode =new MinMaxState(enemyState.getGrid(),enemyState.getNodeFromState(),bestMoveNode.getValue());
				return updateNode;
			}
		}
	}



	private static MinMaxState compareOpenPaths(MinMaxState state1, MinMaxState state2) {
	    
	    
		int count1=0;
		int count2=0;
		for (Node<Tile> eachNode : graph.neighbors(state1.getNodeFromState())) {
			if((adjMatrix[eachNode.getData().getX()][eachNode.getData().getY()] ==-1)
					&& (state1.getGrid()[eachNode.getData().getX()][eachNode.getData().getY()] == -1)){
				count1++;
			}
		}
		for (Node<Tile> eachNode : graph.neighbors(state2.getNodeFromState())) {
			if((adjMatrix[eachNode.getData().getX()][eachNode.getData().getY()] ==-1)
					&& (state2.getGrid()[eachNode.getData().getX()][eachNode.getData().getY()] == -1)){
				count2+=1;
					if(count2 > count1){
					return state2;
			}
			}
		}
		
// 			if(count2 == count1){
// 			    	int maxDept1 = findLongestPath(state1.getNodeFromState());
// 			result= new ArrayList<Node<Tile>>();
// 			visitedNodes = new ArrayList<Node<Tile>>();		
// 			bestResult = new ArrayList<Node<Tile>>();
// 			max = 0; dist =0 ;
			
// 			int maxDept2 = findLongestPath(state2.getNodeFromState());
// 			result= new ArrayList<Node<Tile>>();
// 			visitedNodes = new ArrayList<Node<Tile>>();		
// 			bestResult = new ArrayList<Node<Tile>>();
// 			max = 0; dist =0 ;
// 			    if(maxDept1 > maxDept2){
// 			        return state1;
// 			    }
// 			    else{
// 			        return state2;
// 			    }
// 			}
			
				
		
		
		return state1;
	}



	private static List<MinMaxState> getValidNeighbors(MinMaxState gamer,MinMaxState oldGamer) {
		int adjMatrixTemp[][] = new int[20][30];		
		SortedMap <Integer,MinMaxState> treeMap = new TreeMap<Integer,MinMaxState>();
		List<MinMaxState>  neighours = new ArrayList<MinMaxState>();
		String playerName = null;
		for (Node<Tile> eachNode : graph.neighbors(gamer.getNodeFromState())) {
			if((adjMatrix[eachNode.getData().getX()][eachNode.getData().getY()] ==-1)
				&& (gamer.getGrid()[eachNode.getData().getX()][eachNode.getData().getY()] == -1)) {	
			 playerName = ((Tile)gamer.getNodeFromState().getData()).getType();
			 adjMatrixTemp = java.util.Arrays.stream(oldGamer.getGrid()).map(el -> el.clone()).toArray($ -> oldGamer.getGrid().clone());
			 adjMatrixTemp[eachNode.getData().getX()][eachNode.getData().getY()] =Integer.parseInt(playerName); 
			 treeMap.put(findOpenPath(eachNode),new MinMaxState(adjMatrixTemp, constructNode(eachNode.getData().getX(),eachNode.getData().getY(), playerName),0));
			}
		}
		
		for(Map.Entry<Integer,MinMaxState> entry : treeMap.entrySet()) {
			 neighours.add(entry.getValue());
			}
		Collections.reverse(neighours);
		return neighours;
	}



	private static Integer findOpenPath(Node<Tile> node) {
		int count1=0;
		for (Node<Tile> eachNode : graph.neighbors(node)) {
			if((adjMatrix[eachNode.getData().getX()][eachNode.getData().getY()] ==-1)){
				count1++;
			}
		}
		return count1;
	}



	private static MinMaxState  floodfill(MinMaxState gamerMax) {
		for (int i = 0; i < 20; i++) {
			Arrays.fill(floodFill[i], -1);
		}
		Node<Tile> gamerState = gamerMax.getNodeFromState();
		int max =0;
		Queue<Coord> queue = new LinkedList<Coord>();
		List<Coord> vistedNode = new LinkedList<Coord>();
		List<Coord> result = new LinkedList<Coord>();
		queue.add(new Coord(gamerState.getData().getX(),gamerState.getData().getY()));
		int value = 0;
		int xPos=0,yPos =0;
		Coord currentCoordniate = null;
		Coord targetCoordinate = null;
		xPos = gamerState.getData().getX();
		yPos = gamerState.getData().getY();
		System.err.println("FloodFill"+xPos+"  "+yPos);
		floodFill[xPos][yPos] = 0;
		
		while(!queue.isEmpty()){
			currentCoordniate = queue.poll();
			vistedNode.add(currentCoordniate);
			//push each neighbours 
			Coord newCoordniate;
			xPos = currentCoordniate.getX();
			yPos = currentCoordniate.getY();
			value = floodFill[xPos][yPos];
			if(checkValid(xPos-1,yPos,gamerState,gamerMax)){
				newCoordniate = new Coord(xPos-1,yPos);
				floodFill[xPos-1][yPos]=value+1+ findOpenPath(constructNode(xPos-1,yPos,null))  ;
				queue.add(newCoordniate);
				if(value+1>max){
					max = value+1;
					targetCoordinate = newCoordniate;
					result.add(targetCoordinate);
				}
			}
			if(checkValid(xPos+1,yPos,gamerState,gamerMax)){
				newCoordniate = new Coord(xPos+1,yPos);
				floodFill[xPos+1][yPos]=value+1+ findOpenPath(constructNode(xPos-1,yPos,null))  ;
				queue.add(newCoordniate);
				if(value+1>max){
					max = value+1;
					targetCoordinate = newCoordniate;
				}
			}
			if(checkValid(xPos,yPos-1,gamerState,gamerMax)){
				newCoordniate = new Coord(xPos,yPos-1);
				floodFill[xPos][yPos-1]=value+1+ findOpenPath(constructNode(xPos-1,yPos,null))  ;
				queue.add(newCoordniate);
				if(value+1>max){
					max = value+1;
					targetCoordinate = newCoordniate;
				}
			}
			if(checkValid(xPos,yPos+1,gamerState,gamerMax)){
				newCoordniate = new Coord(xPos,yPos+1);
				floodFill[xPos][yPos+1]=value+1+ findOpenPath(constructNode(xPos-1,yPos,null))  ;
				queue.add(newCoordniate);
				if(value+1>max){
					max = value+1;
					targetCoordinate = newCoordniate;
				}
			}
			
			
		}	
		
		if(targetCoordinate == null){
		    return new MinMaxState(gamerMax.getGrid(),gamerState,-1);
		}
		else{
		//System.err.println("FloodFill"+"current player coordniate"+gamerState.getData().getY()+"   "+gamerState.getData().getX());
		//System.err.println("Floodfill"+targetCoordinate.getY()+" "+targetCoordinate.getX()+" value"+floodFill[targetCoordinate.getX()][targetCoordinate.getY()]);
		    return new MinMaxState(gamerMax.getGrid(),gamerState,floodFill[targetCoordinate.getX()][targetCoordinate.getY()]);
		}
}




private static boolean checkValid(int x, int y, Node<Tile> player, MinMaxState gamerMax) {

	//if(x>=0 && x<20 && y>=0 && y<30 && (calculateDistanceOfEnemy(player,constructNode(x, y, null)) < 5)){
		if(x>=0 && x<20 && y>=0 && y<30){
		if((gamerMax.getGrid()[x][y] ==-1)  && (floodFill[x][y] == -1)){
			return true;
		}
	}		
	return false;
}




private static Integer calculateDistanceOfEnemy(Node<Tile> player, Node<Tile> enemey) {
	int x = Math.abs(enemey.getData().getX() - player.getData().getX());
	int y = Math.abs(enemey.getData().getY() - player.getData().getY());
	return (x+y);
}



static int max = 0,dist = 0;
static ArrayList <Node<Tile>> visitedNodes = new ArrayList<Node<Tile>>();
static ArrayList <Node<Tile>> result = new ArrayList<Node<Tile>>();
static ArrayList <Node<Tile>> bestResult = new ArrayList<Node<Tile>>();
static HashMap<Node<Tile>,List<Node<Tile>>> alreadyFoundPath = new HashMap<Node<Tile>,List<Node<Tile>>>();
static List<Node<Tile>> longestPath = new ArrayList<Node<Tile>>();
static boolean skipRecursivePaths = false;
private static int findLongestPath(Node<Tile> currentNode) {

	visitedNodes.add(currentNode);	
	result.add(currentNode);
	int maxDepth = 0;
	for(Node<Tile> eachNode : graph.neighbors(currentNode)){
		if (adjMatrix[eachNode.getData().getX()][eachNode.getData().getY()] ==-1) {
			if(!visitedNodes.contains(eachNode)) {
				//	            		if(alreadyFoundPath.containsKey(eachNode)){
				//	            			
				//	            		}
				maxDepth = Math.max(maxDepth, findLongestPath(eachNode));
				//	            	if(alreadyFoundPath.containsKey(currentNode)){
				//	            		alreadyFoundPath.get(currentNode).add(eachNode);
				//	            	}
			}
		}
	}

	dist = result.size();
	if(dist>max){
		bestResult.addAll(result);
		max = dist;
	}
	result.remove(currentNode);
	return maxDepth + 1;
}




//        Tile eachTile;
//
//        for(Node eachNode : graph.neighbors(currentNode)) {
//        	eachTile = (Tile)eachNode.getData();
//        	eachNode = graph.getNode(constructNode(eachTile.getX(),eachTile.getY(),null)).get();
//            if (((Tile)eachNode.getData()).getType() == null) {
//            	if(!visitedNodes.contains(eachNode)) {
////            		if(skipRecursivePaths){
////            		    return;
////            		    
////            		}
//            		//System.out.println(((Tile)eachNode.getData()).getX() + " "+((Tile)eachNode.getData()).getY());
//                    findLongestPath(eachNode);
//                    dist = visitedNodes.size();
//                }
//            }
//        }
//        
//        if(dist > max){
//        	max=dist;
//        	System.err.println("first time"+max);
//    		for (Node<Tile> eachNode : visitedNodes) {
//        		System.out.println(((Tile)eachNode.getData()).getX() + " "+((Tile)eachNode.getData()).getY());
//    		}
//    		result.addAll(visitedNodes);
////    		if(dist > 20){
////    		    skipRecursivePaths = true;
////    		    }
////    		    if(skipRecursivePaths){
////    		    return;
////    		    
////    		}
//        }
//        visitedNodes.remove(currentNode);







private static Node<Tile> constructNode(int y1, int x1,String value) {
	return new Node<Tile>(new Tile(y1,x1,value));
}


//constructs a bidirectional graph
private static void constructGraph() {

	for(int i=0;i<20;i++){
		for(int j=0;j<30;j++){
			inputMap.put(new Coord(i,j),new Tile(i,j,null));
			graph.addNode(new Node<Tile>(new Tile(i,j,null)));
		}
	}

	//			//add edges
	for (Map.Entry<Coord,Tile> entry : inputMap.entrySet())
	{
		pushEdges(entry.getValue(),"N");
		pushEdges(entry.getValue(),"S");
		pushEdges(entry.getValue(),"E");
		pushEdges(entry.getValue(),"W");
	}

}


static void pushEdges(Tile fromTile, String direction) {
	int x;
	int y;
	Coord newCoord;
	Tile newTile;
	switch(direction){
	case "N" : {
		x = fromTile.getX();
		y = fromTile.getY()-1;
		newCoord = new Coord(x,y);
		newTile =(Tile) inputMap.get(newCoord);
		if(inputMap.containsKey(newCoord)){
			graph.addEdge(new Edge(new Node<Tile>(fromTile),new Node<Tile>(newTile),1));
		}
		break;
	}
	case "S" : {
		x = fromTile.getX();
		y = fromTile.getY()+1;
		newCoord = new Coord(x,y);
		newTile =(Tile) inputMap.get(newCoord);
		if(inputMap.containsKey(newCoord)){
			graph.addEdge(new Edge(new Node<Tile>(fromTile),new Node<Tile>(newTile),1));
		}

		break;
	}
	case "E" : {

		x = fromTile.getX()+1;
		y = fromTile.getY();
		newCoord = new Coord(x,y);
		newTile = inputMap.get(newCoord);
		if(inputMap.containsKey(newCoord)){
			graph.addEdge(new Edge(new Node<Tile>(fromTile),new Node<Tile>(newTile),1));
		}

		break;
	}
	case "W" : {


		x = fromTile.getX()-1;
		y = fromTile.getY();
		newCoord = new Coord(x,y);
		newTile = inputMap.get(newCoord);	
		if(inputMap.containsKey(newCoord)){
			graph.addEdge(new Edge(new Node<Tile>(fromTile),new Node<Tile>(newTile),1));
		}
		break;
	}
	}

}



//To convert movement into action
public static String converEdgesToAction(Node<Tile> fromNode,Node<Tile> toNode) {
	Tile fromTile =null, toTile =null;
	fromTile = (Tile) fromNode.getData();
	toTile = (Tile) toNode.getData();
	if (fromTile.getY() > toTile.getY())
		return "LEFT";
	else if (fromTile.getY() < toTile.getY())
		return "RIGHT";
	else if (fromTile.getX() < toTile.getX())
		return "DOWN";
	else if (fromTile.getX() > toTile.getX())
		return "UP";


	return null;
}


private static void checkConstructedGraph() {
	System.out.println(graph.neighbors(new Node<Tile>(new Tile(5,1,null))).size());
	for(Node eachNode:graph.neighbors(new Node<Tile>(new Tile(5,1,null)))){
		System.out.println(((Tile)eachNode.getData()).getX()+"   "+((Tile)eachNode.getData()).getY());
	}
	System.out.println(graph.neighbors(new Node<Tile>(new Tile(0,1,null))).size());
	for(Node eachNode:graph.neighbors(new Node<Tile>(new Tile(0,1,null)))){
		System.out.println(((Tile)eachNode.getData()).getX()+"   "+((Tile)eachNode.getData()).getY());
	}
	System.out.println(graph.neighbors(new Node<Tile>(new Tile(19,0,null))).size());
	for(Node eachNode:graph.neighbors(new Node<Tile>(new Tile(19,0,null)))){
		System.out.println(((Tile)eachNode.getData()).getX()+"   "+((Tile)eachNode.getData()).getY());
	}
	System.out.println(graph.neighbors(new Node<Tile>(new Tile(0,0,null))).size());
	for(Node eachNode:graph.neighbors(new Node<Tile>(new Tile(0,0,null)))){
		System.out.println(((Tile)eachNode.getData()).getX()+"   "+((Tile)eachNode.getData()).getY());
	}
	System.out.println(graph.neighbors(new Node<Tile>(new Tile(19,29,null))).size());
	for(Node eachNode:graph.neighbors(new Node<Tile>(new Tile(19,29,null)))){
		System.out.println(((Tile)eachNode.getData()).getX()+"   "+((Tile)eachNode.getData()).getY());
	}

}


}

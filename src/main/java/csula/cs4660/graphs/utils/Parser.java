package csula.cs4660.graphs.utils;

import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * A quick parser class to read different format of files
 */
public class Parser {
	static Map <Coord,Tile> inputMap = new HashMap<Coord,Tile>();

	public static Graph readRectangularGridFile(Representation.STRATEGY graphRepresentation, File file) {		Graph graph = new Graph(Representation.of(graphRepresentation));
	// TODO: implement the rectangular file read and add node with edges to graph
	BufferedReader buffer;
	int row = 0;
	int value =0;
	int column = 0;
	String stringValue;
	int counter;
	List<String> lines = new ArrayList<String>();
	try {
		buffer = new BufferedReader(new FileReader(file));
		String eachLine ;
		while ((eachLine = buffer.readLine()) != null) {
			lines.add(eachLine.substring(1, eachLine.length() - 1));				
		}
		lines.remove(lines.size()-1);
		lines.remove(0);
	}
	catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (NumberFormatException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}


	row = lines.size();
	column  = lines.get(0).length()/2;


	//to add the nodes 
	for(int i = 0;i<row ;i++){
		counter = 0;
		for(int j = 0;j<column;j++){
			stringValue = lines.get(i).substring(counter,counter+2);
			counter+=2;
			inputMap.put(new Coord(j,i),new Tile(j,i,stringValue));
			graph.addNode(new Node<Tile>(new Tile(j,i,stringValue)));
		}
	}

//	//add edges
	for (Map.Entry<Coord,Tile> entry : inputMap.entrySet())
	{
		pushEdges(graph,entry.getValue(),"N",row,column);
		pushEdges(graph,entry.getValue(),"S",row,column);
		pushEdges(graph,entry.getValue(),"E",row,column);
		pushEdges(graph,entry.getValue(),"W",row,column);
	}

	return graph;
	}

	private static void pushEdges(Graph graph,Tile fromTile, String direction,int row,int column) {
		int x;
		int y;
		Coord newCoord;
		Tile newTile;
		switch(direction){
		case "N" : {
			if(fromTile.getY() -1 >= 0 ){
				x = fromTile.getX();
				y = fromTile.getY()-1;
				newCoord = new Coord(x,y);
				newTile =(Tile) inputMap.get(newCoord);
//				if(!newTile.getType().equals("##"))
					graph.addEdge(new Edge(new Node<Tile>(fromTile),new Node<Tile>(newTile),1));
			}
			break;
		}
		case "S" : {
			if(fromTile.getY() + 1 < row ){
				x = fromTile.getX();
				y = fromTile.getY()+1;
				newCoord = new Coord(x,y);
				newTile =(Tile) inputMap.get(newCoord);
//				if(!newTile.getType().equalsIgnoreCase("##"))
					graph.addEdge(new Edge(new Node<Tile>(fromTile),new Node<Tile>(newTile),1));
			}

			break;
		}
		case "E" : {
			if(fromTile.getX() + 1 < column ){
				x = fromTile.getX()+1;
				y = fromTile.getY();
				newCoord = new Coord(x,y);
				newTile = inputMap.get(newCoord);		
//				if(!newTile.getType().equalsIgnoreCase("##"))
					graph.addEdge(new Edge(new Node<Tile>(fromTile),new Node<Tile>(newTile),1));
			}

			break;
		}
		case "W" : {

			if(fromTile.getX() - 1 <= 0 ){
				x = fromTile.getX()-1;
				y = fromTile.getY();
				newCoord = new Coord(x,y);
				newTile = inputMap.get(newCoord);		
//				if(!newTile.getType().equalsIgnoreCase("##"))
					graph.addEdge(new Edge(new Node<Tile>(fromTile),new Node<Tile>(newTile),1));
			}
			break;
		}
		}

	}



	public static String converEdgesToAction(Collection<Edge> edges) {
		String actionsToTraceThePath = null;
		Tile fromTile =null, toTile =null;

		for (Edge eachEdge : edges) {
			fromTile = (Tile) eachEdge.getFrom().getData();
			toTile = (Tile) eachEdge.getTo().getData();
			if (fromTile.getY() > toTile.getY())
				actionsToTraceThePath.concat("N");
			else if (fromTile.getY() < toTile.getY())
				actionsToTraceThePath.concat("S");
			else if (fromTile.getX() < toTile.getX())
				actionsToTraceThePath.concat("E");
			else if (fromTile.getX() > toTile.getX())
				actionsToTraceThePath.concat("W");

		}

		return actionsToTraceThePath;
	}
}

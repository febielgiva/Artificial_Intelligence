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

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {   
    	List<Node> frontier = new ArrayList<Node>();
    	frontier = graph.neighbors(new);
    	Tile tile ;
    	tile = (Tile) source.getData();
    	for (Node node : frontier) {
    		tile = (Tile) node.getData();
			System.out.println(tile.getX() +"  "+tile.getY() +"  "+tile.getType()+ " ");
		}
    	return null;
    }
}

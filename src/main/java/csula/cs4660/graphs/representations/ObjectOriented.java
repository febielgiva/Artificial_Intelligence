package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Object oriented representation of graph is using OOP approach to store nodes
 * and edges
 *
 * TODO: Please fill the body of methods in this class
 */
public class ObjectOriented implements Representation {
	private Collection<Node> nodes;
	private Collection<Edge> edges;

	public ObjectOriented(File file) {
		nodes = new HashSet<Node>();
		edges = new ArrayList<Edge>();
		BufferedReader br = null;
		BufferedReader buffer;
		Node fromNode;
		Node toNode;
		int value = 0;
		try {
			buffer = new BufferedReader(new FileReader(file));
			int noOfVertices =Integer.parseInt(buffer.readLine().trim());
			String line;
			while ((line = buffer.readLine()) != null) {
				String[] vals = line.trim().split("\\:");
				fromNode = new Node<Integer>(Integer.parseInt(vals[0]));
				toNode   = new Node<Integer>(Integer.parseInt(vals[1]));
				value    = Integer.parseInt(vals[2]);
				nodes.add(fromNode);
				nodes.add(toNode);
				if(!edges.contains(new Edge(fromNode,toNode,value))){
					edges.add(new Edge(fromNode,toNode,value));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ObjectOriented() {
		nodes = new HashSet<Node>();
		edges = new ArrayList<Edge>();
	}

	@Override
	public boolean adjacent(Node x, Node y) {
		for (Iterator iterator = edges.iterator(); iterator.hasNext();) {
			Edge edge = (Edge) iterator.next();
			if(edge.getFrom().equals(x) && edge.getTo().equals(y)){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Node> neighbors(Node x) {	
		List<Node> expectedNodes= new ArrayList<Node>();
		if(nodes.contains(x)){
			for (Edge eachEdge : edges) {
				if(eachEdge.getFrom().equals(x)){		
					expectedNodes.add(eachEdge.getTo());
				}
			}
		}
		return expectedNodes;
	}

	@Override
	public boolean addNode(Node x) {
		if(nodes.contains(x)){
			return false;
		}
		else{
			nodes.add(x);
			return true;
		}
	}

	@Override
	public boolean removeNode(Node x) {
		if(nodes.contains(x)){
			nodes.remove(x);
			for (Iterator iterator = edges.iterator(); iterator.hasNext();) {
				Edge edge = (Edge) iterator.next();
				if(edge.getFrom().equals(x) || edge.getTo().equals(x)){		
					iterator.remove();
				}
			}
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean addEdge(Edge x) {

		if(nodes.contains(x.getFrom())){
			//already exsit
			if(edges.contains(x)){
				return false;
			}
			else{
				edges.add(x);
				return true;
			}
		}
		else{
			nodes.add(x.getFrom());
			edges.add(x);
			return true;
		}

	}

	@Override
	public boolean removeEdge(Edge x) {
		if(edges.contains(x)){
			edges.remove(x);
			return true;
		}
		else
			return false;
	}

	@Override
	public int distance(Node from, Node to) {
		for (Edge edge : edges) {
			if((edge.getFrom().equals(from)) &&(edge.getTo().equals(to))){
				return edge.getValue();
			}
		}
		return 0;
	}

	@Override
	public Optional<Node> getNode(int index) {
		return null;
	}
}

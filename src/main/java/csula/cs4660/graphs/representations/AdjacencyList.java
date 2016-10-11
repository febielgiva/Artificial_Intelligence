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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Adjacency list is probably the most common implementation to store the unknown
 * loose graph
 *
 * TODO: please implement the method body
 */
public class AdjacencyList implements Representation {

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

    }

	@Override
	public boolean adjacent(Node x, Node y) {

		if(adjacencyList.containsKey(x)){
			Collection< Edge > collectionOfEdge = new ArrayList<Edge>();
			collectionOfEdge = adjacencyList.get(x);
			for (Iterator iterator = collectionOfEdge.iterator(); iterator.hasNext();) {
				Edge edge = (Edge) iterator.next();
				if(edge.getTo().equals(y)){
					return true;
				}
			}
			return false;
		}
		else
			return false;

	}

	@Override
	public List<Node> neighbors(Node x) {

		List<Node> expectedList= new ArrayList<Node>();
		if(adjacencyList.containsKey(x)){
			Collection< Edge > collectionOfEdge = new ArrayList<Edge>();
			collectionOfEdge = adjacencyList.get(x);
			for (Iterator iterator = collectionOfEdge.iterator(); iterator.hasNext();) {
				Edge edge = (Edge) iterator.next();
				expectedList.add(edge.getTo());
			}
		}

		return expectedList;
	}

	@Override
	public boolean addNode(Node x) {
		if(adjacencyList.containsKey(x)){
			return false;
		}
		else{
			adjacencyList.put(x, null);
			return true;
		}
	}

	@Override
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

	@Override
	public boolean addEdge(Edge x) {
		boolean flag = true;
		if(adjacencyList.containsKey(x.getFrom())){
			System.out.println(x.getFrom());
			Collection<Edge> collectionOfEdge = adjacencyList.get(x.getFrom());
			if(collectionOfEdge.contains(x)){
				flag = false;
				return false;
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
				{	System.out.println("return true getTO already prsent");
				collectionOfEdge = adjacencyList.get(x.getFrom());
				collectionOfEdge.add(x);
				adjacencyList.put(x.getFrom(), collectionOfEdge);
				return true;
				}
				else{
					System.out.println("return true getTO not already prsent");
					collectionOfEdge = adjacencyList.get(x.getFrom());
					collectionOfEdge.add(x);
					adjacencyList.put(x.getFrom(), collectionOfEdge);
					adjacencyList.put(x.getTo(),null);
					return true;
				}

			}
		}

		return false;
	}

	@Override
	public boolean removeEdge(Edge x) {
		if(adjacencyList.containsKey(x.getFrom())){
			Collection<Edge> collectionOfEdge = adjacencyList.get(x.getFrom());
			if(collectionOfEdge.contains(x)){
				collectionOfEdge.remove(x);
				adjacencyList.put(x.getFrom(),collectionOfEdge);
				return true;
			}
//			for (Iterator iterator = collectionOfEdge.iterator(); iterator.hasNext();) {
//				Edge edge = (Edge) iterator.next();
//				System.out.println(edge.getFrom() +" "+ edge.getTo());
//				//if edge exits so remove
//				if(edge.getTo().equals(x.getTo())){
//					iterator.remove();
//					adjacencyList.put(x.getFrom(),collectionOfEdge);
//					return true;
//				}
//			}
		}
		return false;
	}

	@Override
	public int distance(Node from, Node to) {
		return 0;
	}

	@Override
	public Optional<Node> getNode(int index) {
		return null;
	}
    
}






package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import com.google.common.primitives.Ints;

/**
 * Adjacency matrix in a sense store the nodes in two dimensional array
 */
public class AdjacencyMatrix implements Representation {

	private Node[] nodes;
	private int[][] adjacencyMatrix;

	public AdjacencyMatrix(File file) {
//		int i = 0;
//		BufferedReader br = null;
//		BufferedReader buffer;
//		Node fromNode;
//		Node toNode;
//
//		int value = 0;
//		try {
//			buffer = new BufferedReader(new FileReader(file));
//			int noOfVertices =Integer.parseInt(buffer.readLine().trim());
//			String line;
//			nodes =  new Node[noOfVertices];
//			adjacencyMatrix = new int[noOfVertices][noOfVertices];
//			while ((line = buffer.readLine()) != null) {
//				String[] vals = line.trim().split("\\:");
//				fromNode = new Node<Integer>(Integer.parseInt(vals[0]));
//				if(!ArrayUtils.contains( nodes,fromNode))
//					nodes[i++] = fromNode;
//
//				toNode   = new Node<Integer>(Integer.parseInt(vals[1]));
//				if(!ArrayUtils.contains( nodes,toNode))
//					nodes[i++] = toNode;
//				
//				value    = Integer.parseInt(vals[2]);
//				adjacencyMatrix[ArrayUtils.indexOf(nodes,fromNode)][ArrayUtils.indexOf(nodes,toNode)] = value;
//
//				
//			}
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		try (Stream<String> stream = Files.lines(file.toPath())) {
            stream.forEach(line -> {
               if(line.split(":").length == 1){
            	   int totalNoOfNodes = Integer.parseInt(line);
            	   adjacencyMatrix = new int[totalNoOfNodes][totalNoOfNodes];
            	   nodes = new Node[totalNoOfNodes];
            	   for(int i =0; i< totalNoOfNodes; i++){
            		  nodes[i] = new Node(i);
            	   }
               }
               else{
            	   String[] fromToValue = line.split(":");
            	   adjacencyMatrix[Integer.parseInt(fromToValue[0])][Integer.parseInt(fromToValue[1])] = Integer.parseInt(fromToValue[2]);
            	   
               }
           });
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

//	private void printAdajcent() {
//		
//		System.out.println(adjacencyMatrix.length);
//		for(int i =0;i<adjacencyMatrix.length;i++){
//			for(int j =0;j<adjacencyMatrix.length;j++){
//				System.out.println("i = " + i + "    j =" + j +"     "+ adjacencyMatrix[i][j]);
//			}
//		}
//
//	}

//	private void printNode() {
//		for(int i =0;i<nodes.length;i++)
//			System.out.println(nodes[i] +"  " + i);
//
//	}

 public AdjacencyMatrix() {
	 adjacencyMatrix = new int[0][0];
	 nodes = new Node[0];
}

	@Override
	public boolean adjacent(Node x, Node y) {
//		printNode();
//		printAdajcent();
		//System.out.println("adajacent");
		if(adjacencyMatrix[ArrayUtils.indexOf(nodes,x)][ArrayUtils.indexOf(nodes,y)] != 0)
			return true;
		else
			return false;
		
		
	}

	@Override
	public List<Node> neighbors(Node x) {
		//System.out.println("neighboors");

		List<Node> expectedNodes= new ArrayList<Node>();

//		if(x.getData().getClass().isPrimitive()){
//			int count=0;
//			int expectedNodeData[] = new int[nodes.length] ;
//			int indexOfMainNode = ArrayUtils.indexOf(nodes,x);
//			for(int i = 0;i<adjacencyMatrix.length;i++){
//				if(adjacencyMatrix[indexOfMainNode][i] != 0){
//					expectedNodeData[count++]=(int) nodes[i].getData();
//				}
//			}
//			int[] nodes = Arrays.copyOf(expectedNodeData, count);
//			Arrays.sort(nodes);
//			for (int i = 0; i < count; i++) {
//				expectedNodes.add(new Node(nodes[i]));
//			}
//		}
//		else{
		int i =0;
		for(int eachValue : adjacencyMatrix[ArrayUtils.indexOf(nodes,x)]){
			if(eachValue != 0){
				expectedNodes.add(nodes[i]);
			}
			i++;
		}
		
		return expectedNodes;
	}

	@Override
	public boolean addNode(Node x) {
		//System.out.println("add node");
		if(!ArrayUtils.contains( nodes,x)){
			Node[] oldNodes = nodes;
			nodes = Arrays.copyOf(oldNodes, oldNodes.length+1);
			nodes[nodes.length-1] = x;
			
			
			int[][] oldAdjacencyMatrix = adjacencyMatrix;
  			adjacencyMatrix=new int[oldAdjacencyMatrix.length+1][oldAdjacencyMatrix.length+1];
 			for(int i =0;i<oldAdjacencyMatrix.length;i++){
 				for(int j =0;j<oldAdjacencyMatrix.length;j++){
 					adjacencyMatrix[i][j] = oldAdjacencyMatrix[i][j];
 				}
			}

//			int[][] oldAdjacencyMatrix = adjacencyMatrix;
//			adjacencyMatrix=new int[oldAdjacencyMatrix.length+1][oldAdjacencyMatrix.length+1];
//			adjacencyMatrix=Arrays.copyOf(oldAdjacencyMatrix, oldAdjacencyMatrix.length);
		
////			int array[] = new int[adjacencyMatrix.length];
//			for(int i = 0;i<adjacencyMatrix.length;i++){
//				adjacencyMatrix[ArrayUtils.indexOf(nodes,x)][i] = array[];
//			}
			
//			for(int i =0;i<oldAdjacencyMatrix.length;i++){
//				for(int j =0;j<oldAdjacencyMatrix.length;j++){
//					adjacencyMatrix[i][j] = oldAdjacencyMatrix[i][j];
//				}
//			}
//			printNode();
//			printAdajcent();
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean removeNode(Node x) {
		//System.out.println("remove node");
		int indexOfNode;
		if(ArrayUtils.contains( nodes,x)){
			indexOfNode = ArrayUtils.indexOf(nodes,x);
			for(int i=indexOfNode;i<nodes.length-1;i++){
				//System.out.println(nodes.length + "i = "+i+ "  old = " + nodes[i] + "   new =" + nodes[i+1]);
				nodes[i] = nodes[i+1];
			}

			Node[] oldNodes = nodes;
			nodes = Arrays.copyOf(oldNodes, nodes.length-1);

			int[][] oldAdjacencyMatrix = adjacencyMatrix;
			for(int i=indexOfNode;i<adjacencyMatrix.length-1;i++){
				for(int j=0;j<adjacencyMatrix.length;j++){
				adjacencyMatrix[i][j] = adjacencyMatrix[i+1][j];
				}
			}
			for(int i=0;i<oldAdjacencyMatrix.length;i++){
				for(int j=indexOfNode;j<oldAdjacencyMatrix.length-1;j++){
				adjacencyMatrix[i][j] = oldAdjacencyMatrix[i][j+1];
				}
			}
			oldAdjacencyMatrix = adjacencyMatrix;
			adjacencyMatrix=new int[oldAdjacencyMatrix.length-1][oldAdjacencyMatrix.length-1];
			for(int i =0;i<oldAdjacencyMatrix.length-1;i++){
				for(int j =0;j<oldAdjacencyMatrix.length-1;j++){
					adjacencyMatrix[i][j] = oldAdjacencyMatrix[i][j];
				}
			}
			//System.out.println("remove node");
//			printNode();
//			printAdajcent();
			return true;
		}
		else
			//System.out.println("remove node");
//			printNode();
//			printAdajcent();
			return false;
	}

	@Override
	public boolean addEdge(Edge x) {
		if(ArrayUtils.contains( nodes,x.getFrom())){
			if(ArrayUtils.contains( nodes,x.getTo())){
				if(adjacencyMatrix[ArrayUtils.indexOf(nodes,x.getFrom())][ArrayUtils.indexOf(nodes,x.getTo())] != 0){
					return false;
				}
				else
				{
					adjacencyMatrix[ArrayUtils.indexOf(nodes,x.getFrom())][ArrayUtils.indexOf(nodes,x.getTo())] = x.getValue();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeEdge(Edge x) {
		if(ArrayUtils.contains( nodes,x.getFrom()) && ArrayUtils.contains( nodes,x.getTo())){
			if(adjacencyMatrix[ArrayUtils.indexOf(nodes,x.getFrom())][ArrayUtils.indexOf(nodes,x.getTo())] != 0){
				adjacencyMatrix[ArrayUtils.indexOf(nodes,x.getFrom())][ArrayUtils.indexOf(nodes,x.getTo())] = 0;
				return true;
			}
		}
		return false;
	}

	@Override
	public int distance(Node from, Node to) {
		return adjacencyMatrix[ArrayUtils.indexOf(nodes,from)][ArrayUtils.indexOf(nodes,to)];

	}

	@Override
	public Optional<Node> getNode(int index) {
		return null;
	}

	@Override
	public Optional<Node> getNode(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

   
}

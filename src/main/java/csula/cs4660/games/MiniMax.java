package csula.cs4660.games;

import java.util.*;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

public class MiniMax {
	public static Node getBestMove(Graph graph, Node root, Integer depth, Boolean max) {
		List<Node> vistedNode = new ArrayList<Node>();
		Node bestMoveNode = null,newNode,temp,updateNode;
		int bestMove = 0;
		if((depth == 0) || (graph.neighbors(root).equals(null))){
			temp = root;
			return temp;
		}
		if(max){
			bestMove = Integer.MIN_VALUE;
			for (Node eachNode : graph.neighbors(root)) {
				newNode = getBestMove(graph, eachNode, depth-1, false);
//				if(((((MiniMaxState)eachNode.getData()).getIndex()) == ((MiniMaxState)newNode.getData()).getIndex()) && 
//						((((MiniMaxState)eachNode.getData()).getValue()) != ((MiniMaxState)newNode.getData()).getValue())){
//					
//				}
				if(bestMove < ((MiniMaxState)newNode.getData()).getValue())
				{
					bestMove = ((MiniMaxState)newNode.getData()).getValue();
					bestMoveNode = newNode;
				}
				
			}
			updateNode = new Node<MiniMaxState>(new MiniMaxState(((MiniMaxState)root.getData()).getIndex(), ((MiniMaxState)bestMoveNode.getData()).getValue()));			
			((MiniMaxState) graph.getNode(root).get().getData()).setValue(((MiniMaxState)updateNode.getData()).getValue());
			return updateNode;
		}
		else if(!max){
			bestMove = Integer.MAX_VALUE;
			
			for (Node eachNode : graph.neighbors(root)) {
				newNode = getBestMove(graph, eachNode, depth-1, true);
//				if(((((MiniMaxState)eachNode.getData()).getIndex()) == ((MiniMaxState)newNode.getData()).getIndex()) && 
//						((((MiniMaxState)eachNode.getData()).getValue()) != ((MiniMaxState)newNode.getData()).getValue())){
//					((MiniMaxState) graph.getNode(eachNode).get().getData()).setValue(((MiniMaxState)newNode.getData()).getValue());
//
//				
//				}
				if(bestMove > ((MiniMaxState)newNode.getData()).getValue())
				{
					bestMove = ((MiniMaxState)newNode.getData()).getValue();
					bestMoveNode = newNode;
				}
				
			}
			updateNode = new Node<MiniMaxState>(new MiniMaxState(((MiniMaxState)root.getData()).getIndex(), ((MiniMaxState)bestMoveNode.getData()).getValue()));
			((MiniMaxState) graph.getNode(root).get().getData()).setValue(((MiniMaxState)updateNode.getData()).getValue());
			return updateNode;
		}
		return null;
	}
}

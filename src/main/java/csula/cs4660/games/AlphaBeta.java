package csula.cs4660.games;

import java.util.ArrayList;
import java.util.List;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

public class AlphaBeta {
	public static Node getBestMove(Graph graph, Node root, Integer depth, Integer alpha, Integer beta, Boolean max) {
		List<Node> vistedNode = new ArrayList<Node>();
		Node bestMoveNode = null,newNode,temp,updateNode;

		if((depth == 0) || (graph.neighbors(root).equals(null))){
			return root;
		}
		if(max){
			bestMoveNode = new Node<>(new MiniMaxState(Integer.MIN_VALUE, Integer.MIN_VALUE));
			for (Node eachNode : graph.neighbors(root)) {
					newNode = getBestMove(graph, eachNode, depth-1,alpha,beta, false);
					if((((MiniMaxState)newNode.getData()).getValue()) > alpha)
					{
						alpha = ((MiniMaxState)newNode.getData()).getValue();
						//bestMoveNode = newNode;
						if(beta < alpha ){
							
							if(((MiniMaxState) bestMoveNode.getData()).getValue() > (((MiniMaxState)newNode.getData()).getValue())){
								bestMoveNode = newNode;
							}
							break;
						}
					}
					
					if(((MiniMaxState) bestMoveNode.getData()).getValue() < (((MiniMaxState)newNode.getData()).getValue())){
						bestMoveNode = newNode;
					}
			}
			updateNode = new Node<MiniMaxState>(new MiniMaxState(((MiniMaxState)root.getData()).getIndex(), ((MiniMaxState)bestMoveNode.getData()).getValue()));
			((MiniMaxState) graph.getNode(root).get().getData()).setValue(((MiniMaxState)updateNode.getData()).getValue());
			return updateNode;
		}
		else{
			bestMoveNode = new Node<>(new MiniMaxState(Integer.MAX_VALUE, Integer.MAX_VALUE));
			for (Node eachNode : graph.neighbors(root)) {
					newNode = getBestMove(graph, eachNode, depth-1,alpha,beta, true);
					if((((MiniMaxState)newNode.getData()).getValue()) < beta){
						beta = ((MiniMaxState)newNode.getData()).getValue();
						bestMoveNode = newNode;
						if(beta < alpha ){
							if(((MiniMaxState) bestMoveNode.getData()).getValue() > (((MiniMaxState)newNode.getData()).getValue())){
								bestMoveNode = newNode;
							}
							break;
						}
					}
					
					if(((MiniMaxState) bestMoveNode.getData()).getValue() > (((MiniMaxState)newNode.getData()).getValue())){
						bestMoveNode = newNode;
					}
			}
			
			

			updateNode = new Node<MiniMaxState>(new MiniMaxState(((MiniMaxState)root.getData()).getIndex(), ((MiniMaxState)bestMoveNode.getData()).getValue()));
			((MiniMaxState) graph.getNode(root).get().getData()).setValue(((MiniMaxState)updateNode.getData()).getValue());

			return updateNode;
		}
	}
}
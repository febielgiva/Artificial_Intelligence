package csula.cs4660.quizes;

import csula.cs4660.quizes.models.State;


class QueueNode{
	QueueNode next;
	State data;
	int value;
	public QueueNode(State data,int value){
		this.data = data;
		this.value = value;
		next = null;
	}
}

public class PriorityQueue{
	QueueNode head;
	
	public PriorityQueue(){
	head = null;
	}
	int getValue(State State){
		QueueNode currentState = head;
		while(currentState != null){
			if(currentState.data.equals(State)){
				return currentState.value;
			}
			currentState = currentState.next;
		}
		return -1;
	}
	void remove(State State){
		QueueNode currentState = head;
		while(currentState.next != null){
			if(currentState.next.data == State){
				currentState.next = currentState.next.next;
				return;
			}
			currentState = currentState.next;
		}
		if(head.data.equals(State)){
			head = head.next;
		}
	}
	void enqueue(State State, int value){
		QueueNode newState = new QueueNode(State, value); 
		if(head == null){
			head = newState;
		}
		else{
			QueueNode currentState = head;
			while(currentState.next != null && currentState.next.value > newState.value ){
				currentState = currentState.next;
			}
			if(head == currentState){
				if(head.value < newState.value){
					newState.next = head;
					head = newState;
				}
				else{
					newState.next = head.next;
					head.next = newState;
				}
			}else{
				if(currentState.value < newState.value){
					newState.next = currentState;
					currentState = newState;
				}
				else{
					newState.next = currentState.next;
					currentState.next = newState;
				}
			}
		}
	}
	State dequeue(){
		if(head != null){
		State data = head.data;
		head = head.next;
		return data;
		}
		
		return null;
	}
	boolean isEmpty(){
		return head == null;
	}
	
}

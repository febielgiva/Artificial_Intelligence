package csula.cs4660.quizes.models;

import java.util.ArrayList;
import java.util.List;

public class PossibleRoute {

	List<State> stateIDs = new ArrayList<State>();
	int depth;
	int effect;
	public PossibleRoute(){
		
	}
	
	
	public PossibleRoute(List<State> stateIDs, int depth, int effect) {
		super();
		this.stateIDs = stateIDs;
		this.depth = depth;
		this.effect = effect;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getEffect() {
		return effect;
	}
	public void setEffect(int effect) {
		this.effect = effect;
	}
	public List<State> getStateIDs() {
		return stateIDs;
	}
	public void setStateIDs(List<State> stateIDs) {
		this.stateIDs = stateIDs;
	}
	
	
	
}

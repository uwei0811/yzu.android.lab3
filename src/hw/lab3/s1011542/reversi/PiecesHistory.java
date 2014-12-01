package hw.lab3.s1011542.reversi;


import hw.lab3.s1011542.reversi.PiecesHistory.State.Step;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PiecesHistory {
	
	public static class State {
		public static class Step {
			PiecesPos pos;
			PiecesType type;
			public Step(PiecesPos pos,PiecesType type) {
				this.pos = pos;
				this.type = type;
			}
		}
		PiecesType player;
		List<Step> steps;
	
		public State(PiecesType p) {
			this.player = p;
			steps = new ArrayList<Step>();
		}
	}
	
	List<State> list = null;
	
	State state = null;
	
	public PiecesHistory() {
		list = new ArrayList<State>();
	}
	
	public void next(PiecesType p) {
		if( state != null)
			list.add(state);
		state = new State(p);
	}
	
	public void add(Step step) {
		state.steps.add(step);
	}
	
	public State back() {
		if( list.size() > 0) {
			State pre = list.get(list.size() -1 );
			list.remove(list.size() -1 );
			return pre;
		} else {
			return null;
		}
	}
	
	public List<State> getList() {
		return this.list;
	}
	
	public State getState() {
		return this.state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void setList(List<State> list ) {
		this.list = list;
	}
}

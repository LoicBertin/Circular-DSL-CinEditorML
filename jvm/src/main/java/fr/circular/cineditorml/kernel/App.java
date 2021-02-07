package fr.circular.cineditorml.kernel;

import fr.circular.cineditorml.kernel.behavioral.Instruction;
import fr.circular.cineditorml.kernel.generator.Visitable;
import fr.circular.cineditorml.kernel.behavioral.State;
import fr.circular.cineditorml.kernel.generator.Visitor;
import fr.circular.cineditorml.kernel.structural.Brick;
import fr.circular.cineditorml.kernel.structural.Clip;

import java.util.ArrayList;
import java.util.List;

public class App implements NamedElement, Visitable {

	private String name;
	private List<Clip> clips = new ArrayList<Clip>();

	/*private List<Brick> bricks = new ArrayList<Brick>();
        private List<State> states = new ArrayList<State>();
        private State initial;*/

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Clip> getClips() {
		return clips;
	}

	public void setClips(List<Clip> clips) {
		this.clips = clips;
	}
/*
	public List<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public State getInitial() {
		return initial;
	}

	public void setInitial(State initial) {
		this.initial = initial;
	}

 */

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.NamedElement;
import fr.circular.cineditorml.kernel.generator.Visitable;
import fr.circular.cineditorml.kernel.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class State implements NamedElement {

	private String name;
	private List<Action> actions = new ArrayList<Action>();
	private Transition transition;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public Transition getTransition() {
		return transition;
	}

	public void setTransition(Transition transition) {
		this.transition = transition;
	}

}

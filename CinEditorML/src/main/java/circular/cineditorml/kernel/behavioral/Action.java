package main.java.circular.cineditorml.kernel.behavioral;


import main.java.circular.cineditorml.kernel.generator.Visitable;
import main.java.circular.cineditorml.kernel.structural.Actuator;

public abstract class Action implements Visitable {

	private Actuator actuator;

	public int getNumberOfIteration() {
		return numberOfIteration;
	}

	public void setNumberOfIteration(int numberOfIteration) {
		this.numberOfIteration = numberOfIteration;
	}

	public void setNumberOfIteration(String numberOfIteration) {
		this.numberOfIteration = Integer.parseInt(numberOfIteration);
	}

	private int numberOfIteration;

	public Actuator getActuator() {
		return actuator;
	}

	public void setActuator(Actuator actuator) {
		this.actuator = actuator;
	}

}

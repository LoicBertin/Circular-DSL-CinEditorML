package fr.circular.cineditorml.kernel.behavioral;

import fr.circular.cineditorml.kernel.generator.Visitable;
import fr.circular.cineditorml.kernel.generator.Visitor;
import fr.circular.cineditorml.kernel.structural.SIGNAL;
import fr.circular.cineditorml.kernel.structural.Sensor;

import java.util.ArrayList;
import java.util.List;

public class Transition {

	private State next;
	private List<Sensor> sensors = new ArrayList<>();
	private SIGNAL value;
	private LOGICAL logical;


	public State getNext() {
		return next;
	}

	public void setNext(State next) {
		this.next = next;
	}

	public List<Sensor> getSensor() {
		return sensors;
	}

	public void setSensor(List<Sensor> sensor) {
		this.sensors = sensor;
	}

	public SIGNAL getValue() {
		return value;
	}

	public void setValue(SIGNAL value) {
		this.value = value;
	}


	public LOGICAL getLogical() {
		return logical;
	}

	public void setLogical(LOGICAL logical) {
		this.logical = logical;
	}

}

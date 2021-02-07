package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.NamedElement;
import fr.circular.cineditorml.kernel.generator.Visitable;

public abstract class Brick implements NamedElement {

	private String name;
	private int pin;

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
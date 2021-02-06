package fr.circular.cineditorml.kernel.structural;

import fr.circular.cineditorml.kernel.generator.Visitor;

public class Actuator extends Brick {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

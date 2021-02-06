package fr.circular.cineditorml.kernel.generator;

import fr.circular.cineditorml.kernel.App;
import fr.circular.cineditorml.kernel.behavioral.DigitalAction;
import fr.circular.cineditorml.kernel.behavioral.State;
import fr.circular.cineditorml.kernel.behavioral.ToneAction;
import fr.circular.cineditorml.kernel.behavioral.Transition;
import fr.circular.cineditorml.kernel.structural.Actuator;
import fr.circular.cineditorml.kernel.structural.Buzzer;
import fr.circular.cineditorml.kernel.structural.Sensor;

import java.util.HashMap;
import java.util.Map;

public abstract class Visitor<T> {

	public abstract void visit(App app);

	public abstract void visit(State state);
	public abstract void visit(Transition transition);
	public abstract void visit(DigitalAction action);
	public abstract void visit(ToneAction action);

	public abstract void visit(Actuator actuator);
	public abstract void visit(Buzzer buzzer);
	public abstract void visit(Sensor sensor);


	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String,Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}

}


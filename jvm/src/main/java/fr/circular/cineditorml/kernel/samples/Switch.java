package fr.circular.cineditorml.kernel.samples;

import fr.circular.cineditorml.kernel.App;
import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.Actuator;
import fr.circular.cineditorml.kernel.structural.Buzzer;
import fr.circular.cineditorml.kernel.structural.SIGNAL;
import fr.circular.cineditorml.kernel.structural.Sensor;
import fr.circular.cineditorml.kernel.generator.ToWiring;
import fr.circular.cineditorml.kernel.generator.Visitor;

import java.util.Arrays;

public class Switch {

	public static void main(String[] args) {

		// Declaring elementary bricks
		Sensor button = new Sensor();
		button.setName("button");
		button.setPin(9);

		Sensor button2 = new Sensor();
		button2.setName("button2");
		button2.setPin(10);

		Actuator buzzer = new Buzzer();
		buzzer.setName("buzzer");
		buzzer.setPin(13);

		// Declaring states
		State on = new State();
		on.setName("on");

		State off = new State();
		off.setName("off");

		// Creating actions
		ToneAction turnOnBuzzer = new ToneAction();
		turnOnBuzzer.setActuator(buzzer);
		turnOnBuzzer.setNote(NOTE.A3);

		ToneAction turnOffBuzzer = new ToneAction();
		turnOffBuzzer.setActuator(buzzer);
		turnOffBuzzer.setNote(NOTE.STOP);

		// Binding actions to states
		on.setActions(Arrays.asList(turnOnBuzzer));
		off.setActions(Arrays.asList(turnOffBuzzer));

		// Creating transitions
		Transition on2off = new Transition();
		on2off.setNext(off);
		on2off.setSensor(Arrays.asList(button, button2));
		on2off.setValue(SIGNAL.HIGH);
		on2off.setLogical(LOGICAL.AND);

		Transition off2on = new Transition();
		off2on.setNext(on);
		off2on.setSensor(Arrays.asList(button, button2));
		off2on.setValue(SIGNAL.HIGH);
		off2on.setLogical(LOGICAL.AND);

		// Binding transitions to states
		on.setTransition(on2off);
		off.setTransition(off2on);

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Switch!");
		theSwitch.setBricks(Arrays.asList(button, button2));
		theSwitch.setStates(Arrays.asList(on, off));
		theSwitch.setInitial(off);

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}

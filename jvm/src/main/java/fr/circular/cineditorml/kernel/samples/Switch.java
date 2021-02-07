package fr.circular.cineditorml.kernel.samples;

import fr.circular.cineditorml.kernel.App;
import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.*;
import fr.circular.cineditorml.kernel.generator.ToWiring;
import fr.circular.cineditorml.kernel.generator.Visitor;

import java.util.Arrays;

public class Switch {

	public static void main(String[] args) {

		/*
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
		 */

		TextClip clip1 = new TextClip();
		clip1.setName("clip1");
		clip1.setText("Les vacances de Noël");
		clip1.setBackgroundColor("(0,0,0)");

		DurationInstruction instruction1 = new DurationInstruction("10");
		instruction1.setClip(clip1);
		clip1.addInstruction(instruction1);

		VideoClip clip2 = new VideoClip();
		clip2.setName("clip2");
		clip2.setFile("../video/dj_rexma.mp4");

		VideoClip clip3 = new VideoClip();
		clip3.setName("clip3");
		clip3.setFile("../video/in_and_in_and_in.mp4");

		TextClip clip4 = new TextClip();
		clip4.setName("clip4");
		clip4.setText("Les vacances de Noël");
		clip4.setBackgroundColor("(0,0,0)");

		DurationInstruction instruction2 = new DurationInstruction("15");
		instruction2.setClip(clip4);
		clip4.addInstruction(instruction2);




		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Switch!");
		theSwitch.setClips(Arrays.asList(clip1,clip2,clip3,clip4));

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}

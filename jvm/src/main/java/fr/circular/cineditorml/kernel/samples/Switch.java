package fr.circular.cineditorml.kernel.samples;

import fr.circular.cineditorml.kernel.App;
import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.*;
import fr.circular.cineditorml.kernel.generator.ToWiring;
import fr.circular.cineditorml.kernel.generator.Visitor;

import java.util.Arrays;

public class Switch {

	public static void main(String[] args) {
		TextClip clip1 = new TextClip();
		clip1.setName("clip1");
		clip1.setText("Les vacances de Noël");
		clip1.setBackgroundColor("(0,0,0)");

		TextPositionInstruction positionInstruction = new TextPositionInstruction(POSITION.center);
		clip1.addInstruction(positionInstruction);

		DurationInstruction instruction1 = new DurationInstruction("10");
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
		clip4.addInstruction(instruction2);

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Switch");
		theSwitch.setClips(Arrays.asList(clip1,clip2,clip3,clip4));

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}

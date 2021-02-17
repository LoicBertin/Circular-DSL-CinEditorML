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

		TextPositionInstruction centeredPositionInstruction = new TextPositionInstruction(POSITION.CENTER);
		clip1.addInstruction(centeredPositionInstruction);

		DurationInstruction duration10Instruction = new DurationInstruction("10");
		clip1.addInstruction(duration10Instruction);

		ColorClip clip1_color = new ColorClip();
		clip1_color.setName("clip1_color");
		clip1_color.setColor(COLOR.BLACK);
		clip1_color.addInstruction(duration10Instruction);

		MergeClip clip1_merged = new MergeClip();
		clip1_merged.setName("clip1_merged");
		clip1_merged.addClip(clip1_color);
		clip1_merged.addClip(clip1);

		VideoClip clip2 = new VideoClip();
		clip2.setName("clip2");
		clip2.setFile("../video/dj_rexma.mp4");

		VideoClip clip3 = new VideoClip();
		clip3.setName("clip3");
		clip3.setFile("../video/in_and_in_and_in.mp4");

		TextClip clip4 = new TextClip();
		clip4.setName("clip4");
		clip4.setText("Les vacances de Noël");
		clip4.addInstruction(centeredPositionInstruction);

		ColorClip clip4_color = new ColorClip();
		clip4_color.setName("clip4_color");
		clip4_color.setColor(COLOR.BLACK);

		DurationInstruction duration15Instruction = new DurationInstruction("15");
		clip4.addInstruction(duration15Instruction);
		clip4_color.addInstruction(duration15Instruction);

		MergeClip clip4_merged = new MergeClip();
		clip4_merged.setName("clip4_merged");
		clip4_merged.addClip(clip4_color);
		clip4_merged.addClip(clip4);

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Switch");
		theSwitch.setClips(Arrays.asList(clip1_merged,clip2,clip3,clip4_merged));

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}

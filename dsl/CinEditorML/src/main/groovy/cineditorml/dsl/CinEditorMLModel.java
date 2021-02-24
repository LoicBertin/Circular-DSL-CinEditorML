package main.groovy.cineditorml.dsl;

import java.util.*;

import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.*;
import groovy.lang.Binding;
import fr.circular.cineditorml.kernel.App;
import fr.circular.cineditorml.kernel.generator.ToWiring;
import fr.circular.cineditorml.kernel.generator.Visitor;

public class CinEditorMLModel {
	private List<Clip> clips;
	private List<Clip> clipsToAccept;
	private String name;
	
	private Binding binding;
	
	public CinEditorMLModel(Binding binding) {
		this.clips = new ArrayList<Clip>();
		this.clipsToAccept = new ArrayList<Clip>();
		this.binding = binding;
	}

	public void createVideoFileClip(String name, String path) {
		VideoClip videoClip = new VideoClip();
		videoClip.setName(name);
		videoClip.setFile(path);
		this.binding.setVariable(name, videoClip);
		this.clipsToAccept.add(videoClip);
	}

	public void createSubClip(Clip clip, int from, int to, String name) {
		SubClip subClip = new SubClip();
		subClip.setClipName(clip.getName());
		subClip.setName(name);
		subClip.setFrom(from);
		subClip.setTo(to);
		this.binding.setVariable(name, subClip);
		this.clipsToAccept.add(subClip);
	}

	public void createTextClip(String name, String text) {
		TextClip textClip = new TextClip();
		textClip.setName(name);
		textClip.setText(text);
		this.binding.setVariable(name, textClip);
		this.clipsToAccept.add(textClip);
	}

	public void createTemporalTextClipWithTransparentBackground(Clip text, int from, int to, POSITION position, String name) {
		String temporalTextClip = "temporised".concat(name);
		this.createTextClip(temporalTextClip, " ");

		String transparentColorClipName = "transparentColorClip".concat(name);
		ColorClip colorClip = new ColorClip();
		colorClip.setName(transparentColorClipName);
		colorClip.addInstruction(new OpacityInstruction(0));
		colorClip.addInstruction(new DurationInstruction(to));
		this.binding.setVariable(colorClip.getName(), colorClip);
		this.clipsToAccept.add(colorClip);

		Clip textClip = (Clip)this.binding.getVariable(temporalTextClip);
		textClip.addInstruction(new DurationInstruction(from));
		// concatenate textClip text
		ConcatenateClip concatenateClip = new ConcatenateClip();
		concatenateClip.setName("concatenated".concat(name));
		concatenateClip.addClip(textClip);
		concatenateClip.addClip(text);
		concatenateClip.addInstruction(new PositionInstruction(position));
		this.binding.setVariable(concatenateClip.getName(), concatenateClip);
		this.clipsToAccept.add(concatenateClip);

		this.createMergeClip(colorClip, concatenateClip, "transparent".concat(name));
	}

	public void createColorClip(String name, COLOR color, int time) {
		ColorClip colorClip = new ColorClip();
		colorClip.setName(name);
		colorClip.setColor(color);
		colorClip.addInstruction(new DurationInstruction(time));
		this.binding.setVariable(name, colorClip);
		this.clipsToAccept.add(colorClip);
	}

	public void createMergeClip(Clip clip1, Clip clip2, String name) {
		MergeClip mergeClip = new MergeClip();
		mergeClip.setName(name);
		mergeClip.addClip(clip1);
		mergeClip.addClip(clip2);
		this.binding.setVariable(name, mergeClip);
		this.clipsToAccept.add(mergeClip);
	}

	public void addClip(Clip clip){
		this.clips.add(clip);
	}

	public void changeName(String name){
		this.name = name;
	}

	public void concatenateClips(ArrayList<Clip> clips, String name) {
		this.name = name;
		this.clips = clips;
	}
	
	@SuppressWarnings("rawtypes")
	public Object generateCode(String name, String path) {
		App app = new App();
		app.setName(name);
		app.setPath(path);
		app.setClips(this.clips);
		app.setClipsToAccept(this.clipsToAccept);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);
		
		return codeGenerator.getResult();
	}
}

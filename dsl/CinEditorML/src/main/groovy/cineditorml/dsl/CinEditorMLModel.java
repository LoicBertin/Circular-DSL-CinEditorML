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

	public void createSubClip(VideoClip clip, int from, int to, String name) {
		VideoSubClip video = new VideoSubClip();
		video.setVideoName(clip.getName());
		video.setName(name);
		video.setFrom(from);
		video.setTo(to);
		video.setFile(clip.getFile());
		this.binding.setVariable(name, video);
		this.clipsToAccept.add(video);
	}

	public void createTextClip(String name, String text, String time) {
		TextClip textClip = new TextClip();
		textClip.setName(name);
		textClip.setText(text);
		textClip.addInstruction(new DurationInstruction(time));
		this.binding.setVariable(name, textClip);
		this.clipsToAccept.add(textClip);
	}

	public void createColorClip(String name, COLOR color, String time) {
		ColorClip colorClip = new ColorClip();
		colorClip.setName(name);
		colorClip.setColor(color);
		colorClip.addInstruction(new DurationInstruction(time));
		this.binding.setVariable(name, colorClip);
		this.clipsToAccept.add(colorClip);
	}

	public void createMergeClip(Clip clip, TextClip text) {
		MergeClip mergeClip = new MergeClip();
		mergeClip.setName(clip.getName());
		mergeClip.addClip(clip);
		mergeClip.addClip(text);
		this.binding.setVariable(clip.getName(), mergeClip);
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
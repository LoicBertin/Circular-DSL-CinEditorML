package fr.circular.cineditorml.kernel;

import fr.circular.cineditorml.kernel.generator.Visitable;
import fr.circular.cineditorml.kernel.generator.Visitor;
import fr.circular.cineditorml.kernel.structural.Clip;

import java.util.ArrayList;
import java.util.List;

public class App implements NamedElement, Visitable {

	private String name;
	private List<Clip> clips = new ArrayList<Clip>();
	private List<Clip> clipsToAccept = new ArrayList<>();
	private String path;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Clip> getClips() {
		return clips;
	}

	public void setClips(List<Clip> clips) {
		this.clips = clips;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Clip> getClipsToAccept() {
		return clipsToAccept;
	}

	public void setClipsToAccept(List<Clip> clipsToAccept) {
		this.clipsToAccept = clipsToAccept;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

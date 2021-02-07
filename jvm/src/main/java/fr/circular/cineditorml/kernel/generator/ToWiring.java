package fr.circular.cineditorml.kernel.generator;

import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.*;
import fr.circular.cineditorml.kernel.App;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {

	public ToWiring() {
		this.result = new StringBuffer();
	}

	private void w(String s) {
		result.append(String.format("%s",s));
	}

	@Override
	public void visit(App app) {
		w("#Code generated from a CineditorML model \n");
		w("from moviepy.editor import *\n");
		w("from moviepy.video import *\n");

		for(Clip clip: app.getClips()){
			clip.accept(this);
		}

		w("result = concatenate_videoclips([");
		for(Clip clip : app.getClips()){
			w(clip.getName());
			if(app.getClips().indexOf(clip) != app.getClips().size()-1){
				w(",");
			}
		}
		w("])\n");
		w(String.format("result.write_videofile(\"../result_videos/%s.webm\",fps=25)", app.getName()));
	}

	@Override
	public void visit(Instruction instruction) {

	}

	@Override
	public void visit(DurationInstruction durationInstruction) {
		//txt_clip.with_duration(10)
		w(String.format(".set_duration(%s)", durationInstruction.getDuration()));
	}

	@Override
	public void visit(TextPositionInstruction positionInstruction) {
		//txt_clip.with_duration(10)
		w(String.format(".set_position(\"%s\")", positionInstruction.getPosition()));
	}



	@Override
	public void visit(Clip clip) {

	}

	@Override
	public void visit(TextClip textClip) {
		//txt_clip = ( TextClip("My Holidays 2013",fontsize=70,color='white')
		w(String.format("%s = TextClip(txt=\"%S\",fontsize=%s,color=\'%s\')", textClip.getName(),textClip.getText() ,textClip.getFontsize(), textClip.getColor()));
		for(Instruction instruction : textClip.getInstructions()){
			instruction.accept(this);
		}
		w("\n");
	}

	@Override
	public void visit(VideoClip videoClip) {
		w(String.format("%s = VideoFileClip(\"%s\")\n", videoClip.getName(), videoClip.getFile()));
		for(Instruction instruction : videoClip.getInstructions()){
			this.visit(instruction);
		}
	}

	@Override
	public void visit(ColorClip colorClip) {
		w(String.format("%s= ColorClip(size=(1920,1080), color=%s)", colorClip.getName(),colorClip.getColor()));
		for(Instruction instruction : colorClip.getInstructions()){
			instruction.accept(this);
		}
		w("\n");
	}

	@Override
	public void visit(MergeClip mergeClip) {
		for(Clip clip : mergeClip.getClips()) {
			clip.accept(this);
		}
			w(String.format("%s = CompositeVideoClip([", mergeClip.getName()));
		for(Clip clip : mergeClip.getClips()){
			w(clip.getName());
			if(mergeClip.getClips().indexOf(clip) != mergeClip.getClips().size()-1){
				w(",");
			}
		}
		w("])\n");
	}
}
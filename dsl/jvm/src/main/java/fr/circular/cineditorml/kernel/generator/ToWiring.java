package fr.circular.cineditorml.kernel.generator;

import fr.circular.cineditorml.kernel.behavioral.*;
import fr.circular.cineditorml.kernel.structural.*;
import fr.circular.cineditorml.kernel.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {
	private int  subNumber = 0;
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
		w("from moviepy.video import *\n\n");
		w("#cf: https://stackoverflow.com/questions/36667702/adding-subtitles-to-a-movie-using-moviepy\n");
		w("def annotate(clip, txt, position, txt_color='red', fontsize=50, font='Xolonium-Bold'):\n");
		w("	txtclip = TextClip(txt, fontsize=fontsize, font=font, color=txt_color)\n");
		w("	cvc = CompositeVideoClip([clip, txtclip.set_pos(position)])\n");
		w("	return cvc.set_duration(clip.duration)\n\n");

		w("#https://stackoverflow.com/questions/23407566/how-to-flatten-a-list-to-return-a-new-list-with-all-the-elements\n");
		w("def flatten(l,result = []):\n");
		w("	if isinstance(l, list):\n");
		w("		for i in l:\n");
		w("			flatten(i)\n");
    	w("	else:\n");
		w("		result.append(l)\n");
		w("	return result\n\n");
		for(Clip clip: app.getClipsToAccept()){
			clip.accept(this);
		}

		w("result = concatenate_videoclips(flatten([");
		for(Clip clip : app.getClips()){
			w(clip.getName());
			if(app.getClips().indexOf(clip) != app.getClips().size()-1){
				w(",");
			}
		}
		w("]))\n");
		w(String.format("result.write_videofile(\"%s/%s.webm\",fps=25, threads=4)", app.getPath(), app.getName()));
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
	public void visit(PositionInstruction positionInstruction) {
		//txt_clip.with_duration(10)
		w(String.format(".set_position(\"%s\")", positionInstruction.getPosition().position));
	}

	@Override
	public void visit(OpacityInstruction opacityInstruction) {
		//txt_clip.set_opacity(0)
		w(String.format(".set_opacity(%s)", opacityInstruction.getOpacity()));
	}



	@Override
	public void visit(Clip clip) {

	}

	@Override
	public void visit(TextClip textClip) {
		//txt_clip = ( TextClip("My Holidays 2013",fontsize=70,color='white')
		w(String.format("%s = TextClip(txt=\"%s\",fontsize=%s,color=\'%s\')", textClip.getName(),textClip.getText() ,textClip.getFontsize(), textClip.getColor()));
		for(Instruction instruction : textClip.getInstructions()){
			instruction.accept(this);
		}
		w("\n");
	}

	@Override
	public void visit(VideoClip videoClip) {
		w(String.format("%s = VideoFileClip(\"%s\")\n", videoClip.getName(), videoClip.getFile()));
		for(Instruction instruction : videoClip.getInstructions()){
			instruction.accept(this);
		}
	}

	@Override
	public void visit(SubClip subClip) {
		w(String.format("%s = %s.subclip(%s,%s)\n", subClip.getName(), subClip.getClipName(), subClip.getFrom(), subClip.getTo()));
		for(Instruction instruction : subClip.getInstructions()){
			instruction.accept(this);
		}
	}

	@Override
	public void visit(ColorClip colorClip) {
		w(String.format("%s= ColorClip(size=(1920,1080), color=%s)", colorClip.getName(),colorClip.getColor().color));
		for(Instruction instruction : colorClip.getInstructions()){
			instruction.accept(this);
		}
		w("\n");
	}

	@Override
	public void visit(MergeClip mergeClip) {
		w(String.format("%s = CompositeVideoClip([", mergeClip.getName()));
		for(Clip clip : mergeClip.getClips()){
			w(clip.getName());
			if(mergeClip.getClips().indexOf(clip) != mergeClip.getClips().size()-1){
				w(",");
			}
		}
		w("])");
		for(Instruction instruction : mergeClip.getInstructions()){
			instruction.accept(this);
		}
		w("\n");
	}

	@Override
	public void visit(ConcatenateClip concatenateClip) {
		w(String.format("%s = concatenate_videoclips([", concatenateClip.getName()));
		for(Clip clip : concatenateClip.getClips()){
			w(clip.getName());
			if(concatenateClip.getClips().indexOf(clip) != concatenateClip.getClips().size()-1){
				w(",");
			}
		}
		w("])");
		for(Instruction instruction : concatenateClip.getInstructions()){
			instruction.accept(this);
		}
		w("\n");
	}

	@Override
	public void visit(SubtitleClip subtitleClip) {
		ArrayList<Subtitle> subtitles = new ArrayList<Subtitle>();
		ArrayList<Subtitle> tempSubtitles = new ArrayList<Subtitle>();
		Subtitle finalSubtitle = null;
		if (subtitleClip.getSubtitles().size() > 1) {
			for (int i = 0; i < subtitleClip.getSubtitles().size(); i++) {
				System.out.println(subtitleClip.getSubtitles().get(i).getTo());
				if(subtitleClip.getSubtitles().get(i).getTo() == 9999){
					finalSubtitle = subtitleClip.getSubtitles().remove(i);
				}else{
					tempSubtitles.add(subtitleClip.getSubtitles().get(i));
				}
			}
			for (int i = 0; i < tempSubtitles.size() - 1; i++) {
				subtitles.add(tempSubtitles.get(i));
				if (tempSubtitles.get(i).getTo() != tempSubtitles.get(i + 1).getFrom()) {
					Subtitle subtitle = new Subtitle();
					subtitle.setTxt(" ");
					subtitle.setPosition(POSITION.BOTTOM);
					subtitle.setFrom(tempSubtitles.get(i).getTo());
					subtitle.setTo(tempSubtitles.get(i + 1).getFrom());
					subtitles.add(subtitle);
				}
				if (i == tempSubtitles.size() - 2) {
					subtitles.add(tempSubtitles.get(i + 1));
				}
			}
			subtitleClip.setSubtitles(subtitles);
		}
		String subtitleGroupName = "subs".concat(Integer.toString(subNumber));
		w(String.format("%s =[", subtitleGroupName));
		for (Subtitle subtitle: subtitleClip.getSubtitles()) {
			w(String.format("((%s, %s), '%s', '%s', '%s'),\n", subtitle.getFrom(), subtitle.getTo(), subtitle.getTxt(), subtitle.getPosition().position, subtitle.getColor() != null ? subtitle.getColor().color: "white"));
		}
		if(finalSubtitle == null){
			w(String.format("((%s, %s.duration), ' ', '%s', '%s')", subtitleClip.getSubtitles().get(subtitleClip.getSubtitles().size() - 1).getTo(), subtitleClip.getClip().getName(), POSITION.BOTTOM.position, "white"));
		}else{
			w(String.format("((%s, %s.duration - %s), ' ', '%s', '%s'),\n", subtitleClip.getSubtitles().get(subtitleClip.getSubtitles().size() - 1).getTo(), subtitleClip.getClip().getName(), finalSubtitle.getFrom() ,POSITION.BOTTOM.position, "white"));
			w(String.format("((%s.duration - %s, %s.duration), '%s', '%s', '%s')", subtitleClip.getClip().getName(),finalSubtitle.getFrom(),subtitleClip.getClip().getName(), finalSubtitle.getTxt(), finalSubtitle.getPosition().position, finalSubtitle.getColor() != null ? finalSubtitle.getColor().color: "white"));
		}

		w("]\n");
		//annotated_clips = [annotate(video.subclip(from_t, to_t), txt, position) for (from_t, to_t), txt, position in subs]
		w(String.format("%s = [annotate(%s.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in %s]\n", subtitleClip.getName(), subtitleClip.getClip().getName(), subtitleGroupName));
		this.subNumber++;
	}
}
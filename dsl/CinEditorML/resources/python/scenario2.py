#Code generated from a CineditorML model
from moviepy.editor import *
from moviepy.video import *
backgroundintroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(10)
textintroClip = TextClip(txt="INTRO TITLE",fontsize=70,color='white').set_position("center").set_duration(3)
temporisedintroClip = TextClip(txt=" ",fontsize=70,color='white').set_duration(4)
transparentColorClipintroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_opacity(0.0).set_duration(7)
concatenatedintroClip = concatenate_videoclips([temporisedintroClip,textintroClip]).set_position("center")
transparentintroClip = CompositeVideoClip([transparentColorClipintroClip,concatenatedintroClip])
introClip = CompositeVideoClip([backgroundintroClip,transparentintroClip])
clip1 = VideoFileClip("resources/video/alderamin 1.webm")
clip1a = clip1.subclip(23,107)
textclip1a_with_subtitle = TextClip(txt="SUBCLIP 1A SUBTITLE",fontsize=70,color='white').set_position("bottom").set_duration(10)
temporisedclip1a_with_subtitle = TextClip(txt=" ",fontsize=70,color='white').set_duration(0)
transparentColorClipclip1a_with_subtitle= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_opacity(0.0).set_duration(10)
concatenatedclip1a_with_subtitle = concatenate_videoclips([temporisedclip1a_with_subtitle,textclip1a_with_subtitle]).set_position("bottom")
transparentclip1a_with_subtitle = CompositeVideoClip([transparentColorClipclip1a_with_subtitle,concatenatedclip1a_with_subtitle])
clip1a_with_subtitle = CompositeVideoClip([clip1a,concatenatedclip1a_with_subtitle])
text76811 = TextClip(txt="SUBCLIP 1A SUBTITLE 2",fontsize=70,color='white').set_position("bottom").set_duration(10)
temporised76811 = TextClip(txt=" ",fontsize=70,color='white').set_duration(40)
transparentColorClip76811= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_opacity(0.0).set_duration(50)
concatenated76811 = concatenate_videoclips([temporised76811,text76811]).set_position("bottom")
transparent76811 = CompositeVideoClip([transparentColorClip76811,concatenated76811])
clip1a_with_subtitle = CompositeVideoClip([clip1a_with_subtitle,concatenated76811])
clip1b = clip1.subclip(121,141)
backgroundoutroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(10)
textoutroClip = TextClip(txt="THANKS FOR WATCHING",fontsize=70,color='white').set_position("center").set_duration(10)
outroClip = CompositeVideoClip([backgroundoutroClip,textoutroClip])
result = concatenate_videoclips([introClip,clip1a_with_subtitle,clip1b,outroClip])
result.write_videofile("resources/result_videos/scenario2.webm",fps=25, threads=4)

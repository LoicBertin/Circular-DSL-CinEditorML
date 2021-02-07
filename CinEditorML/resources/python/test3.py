#Code generated from a CineditorML model 
from moviepy.editor import *
from moviepy.video import *
clip1_color= ColorClip(size=(1920,1080), color=(0,0,0)).set_duration(10)
clip1 = TextClip(txt="LES VACANCES DE NOËL",fontsize=70,color='white').set_position("center").set_duration(10)
clip1_merged = CompositeVideoClip([clip1_color,clip1])
clip2 = VideoFileClip("../video/dj_rexma.mp4")
clip3 = VideoFileClip("../video/in_and_in_and_in.mp4")
clip4_color= ColorClip(size=(1920,1080), color=(0,0,0)).set_duration(15)
clip4 = TextClip(txt="LES VACANCES DE NOËL",fontsize=70,color='white').set_position("center").set_duration(15)
clip4_merged = CompositeVideoClip([clip4_color,clip4])
result = concatenate_videoclips([clip1_merged,clip2,clip3,clip4_merged])
result.write_videofile("../result_videos/Switch.webm",fps=25)
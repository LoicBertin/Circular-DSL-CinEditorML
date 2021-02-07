#Code generated from a CineditorML model 
from moviepy.editor import *
from moviepy.video import *
clip1 = TextClip(txt="LES VACANCES DE NOËL",fontsize=70,color='white').set_position("center").set_duration(10)
clip1_color= ColorClip(size=(1920,1080), color=(0,0,0)).set_duration(10)
clip1 = CompositeVideoClip([clip1_color, clip1])
clip2 = VideoFileClip("../video/dj_rexma.mp4")
clip3 = VideoFileClip("../video/in_and_in_and_in.mp4")
clip4 = TextClip(txt="LES VACANCES DE NOËL",fontsize=70,color='white').set_duration(15)
clip4_color= ColorClip(size=(1920,1080), color=(0,0,0)).set_duration(10)
clip4 = CompositeVideoClip([clip4_color, clip4])
result = concatenate_videoclips([clip1,clip2,clip3,clip4])
result.write_videofile("../result_videos/Switch.webm",fps=25)
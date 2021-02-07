from moviepy.editor import *
from moviepy.video import *
clip1 = TextClip("LES VACANCES DE NOËL",fontsize=70,color='black').set_duration(10)
clip1_color= ColorClip(size=(1920,1080), color=(0,0,0)).set_duration(10)
clip1 = CompositeVideoClip([clip1, clip1_color])
clip2 = VideoFileClip("../video/dj_rexma.mp4")
clip3 = VideoFileClip("../video/in_and_in_and_in.mp4")
clip4 = TextClip("LES VACANCES DE NOËL",fontsize=70,color='black').set_duration(15)
clip4_color= ColorClip(size=(1920,1080), color=(0,0,0)).set_duration(15)
clip4 = CompositeVideoClip([clip4, clip4_color])
result = concatenate_videoclips([clip1,clip2,clip3,clip4])
result.write_videofile("../video/result.webm",fps=25)
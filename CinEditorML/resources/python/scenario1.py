from moviepy.editor import *
from moviepy.video import *
clip1 = TextClip(txt="LES VACANCES DE NOËL",fontsize=70,color='black').set_duration(15)
clip1bis = ColorClip((1920,1080),color=(255,255,255)).set_duration(15)
#clip1.with_duration(10)
clip2 = VideoFileClip("../video/dj_rexma.mp4")
clip3 = VideoFileClip("../video/in_and_in_and_in.mp4")
clip4 = (TextClip("LES VACANCES DE NOËL",fontsize=70,color='black')).set_duration(10)
#clip4
temp1 = CompositeVideoClip([clip1, clip1bis])
result = concatenate_videoclips([temp1,clip2,clip3])
result.write_videofile("../video/result.webm",fps=25)

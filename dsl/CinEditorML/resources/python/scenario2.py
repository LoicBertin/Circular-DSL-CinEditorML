#Code generated from a CineditorML model
from moviepy.editor import *
from moviepy.video import *
introClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(10)
textIntro = TextClip(txt="INTRO TITLE",fontsize=70,color='white').set_duration(10).set_position("center")
introClip = CompositeVideoClip([introClip,textIntro])
clip1 = VideoFileClip("resources/video/dj_rexma.mp4")
clip1a = clip1.subclip(1,4)
clip1b = clip1.subclip(5,7)
outroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(10)
textOutro = TextClip(txt="THANKS FOR WATCHING",fontsize=70,color='white').set_duration(10).set_position("center")
outroClip = CompositeVideoClip([outroClip,textOutro])
result = concatenate_videoclips([introClip,clip1a,clip1b,outroClip])
result.write_videofile("resources/result_videos/scenario2.webm",fps=25)

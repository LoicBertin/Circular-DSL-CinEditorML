#Code generated from a CineditorML model
from moviepy.editor import *
from moviepy.video import *
introClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(10)
textIntro = TextClip(txt="INTRO TITLE",fontsize=70,color='white').set_duration(10).set_position("center")
introClip = CompositeVideoClip([introClip,textIntro])
clip1 = VideoFileClip("resources/video/dj_rexma.mp4")
clip2 = VideoFileClip("resources/video/in_and_in_and_in.mp4")
outroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(15)
textOutro = TextClip(txt="THANKS FOR WATCHING",fontsize=70,color='white').set_duration(15).set_position("center")
outroClip = CompositeVideoClip([outroClip,textOutro])
result = concatenate_videoclips([introClip,clip1,clip2,outroClip])
result.write_videofile("resources/result_videos/final.webm",fps=25)

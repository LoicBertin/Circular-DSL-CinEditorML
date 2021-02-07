from moviepy.editor import *
from moviepy.video import *

video = VideoFileClip("../video/dj_rexma.mp4")
video2 = VideoFileClip("../video/in_and_in_and_in.mp4")

# Make the text. Many more options are available.
txt_clip = ( TextClip(txt="My Holidays 2013",fontsize=70,color='white')
             .set_position('center')
             .set_duration(10) )

color_clip = ColorClip(size=(1920,1080), color=(0,0,0)).set_duration(10)

pre_video = CompositeVideoClip([color_clip, txt_clip])

result = concatenate_videoclips([pre_video, video, video2])
result.write_videofile("../video/myHolidays_edited.webm",fps=25) # Many options...
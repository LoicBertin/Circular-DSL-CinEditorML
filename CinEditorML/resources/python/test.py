from moviepy.editor import *

video = VideoFileClip("../video/dj_rexma.mp4")

# Make the text. Many more options are available.
test = TextClip("Test Text", fontsize = 70, color = 'white').set_position('center').set_duration(10)

result = CompositeVideoClip([video,test]) # Overlay text on video
result.write_videofile("myHolidays_edited.webm",fps=25) # Many options...
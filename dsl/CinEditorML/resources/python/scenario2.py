#Code generated from a CineditorML model
from moviepy.editor import *
from moviepy.video import *

#cf: https://stackoverflow.com/questions/36667702/adding-subtitles-to-a-movie-using-moviepy
def annotate(clip, txt, position, txt_color='red', fontsize=50, font='Xolonium-Bold'):
        txtclip = TextClip(txt, fontsize=fontsize, font=font, color=txt_color)
        cvc = CompositeVideoClip([clip, txtclip.set_pos(position)])
        return cvc.set_duration(clip.duration)

#https://stackoverflow.com/questions/23407566/how-to-flatten-a-list-to-return-a-new-list-with-all-the-elements
def flatten(l,result = []):
        if isinstance(l, list):
                for i in l:
                        flatten(i)
        else:
                result.append(l)
        return result

backgroundintroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(10)
subs0 =[((0, 4), ' ', 'bottom', 'white'),
((4, 7), 'Intro Title', 'center', 'white'),
((7, backgroundintroClip.duration), ' ', 'bottom', 'white')]
introClip = [annotate(backgroundintroClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs0]
clip1 = VideoFileClip("resources/video/alderamin 1.webm")
clip1a = clip1.subclip(23,107)
subs1 =[((0, 10), 'subclip 1a subtitle', 'bottom', 'white'),
((10, 40), ' ', 'bottom', 'white'),
((40, 50), 'subclip 1a subtitle 2', 'bottom', 'white'),
((50, clip1a.duration), ' ', 'bottom', 'white')]
clip1a_with_subtitle = [annotate(clip1a.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs1]
clip1b = clip1.subclip(121,141)
backgroundoutroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(10)
subs2 =[((0, 10), 'THANKS FOR WATCHING', 'center', 'white'),
((10, backgroundoutroClip.duration), ' ', 'bottom', 'white')]
outroClip = [annotate(backgroundoutroClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs2]
result = concatenate_videoclips(flatten([introClip,clip1a_with_subtitle,clip1b,outroClip]))
result.write_videofile("resources/result_videos/scenario2.webm",fps=25, threads=4)

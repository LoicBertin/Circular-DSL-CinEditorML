#Code generated from a CineditorML model
from moviepy.editor import *
from moviepy.video import *

#cf: https://stackoverflow.com/questions/36667702/adding-subtitles-to-a-movie-using-moviepy
def annotate(clip, txt, position, txt_color='red', fontsize=50, font='Xolonium-Bold'):
        txtclip = TextClip(txt, fontsize=fontsize, font=font, color=txt_color)
        cvc = CompositeVideoClip([clip, txtclip.set_pos(position)])
        return cvc.set_duration(clip.duration)


clip1 = VideoFileClip("resources/video/alderamin 1.webm")
clip1a = clip1.subclip(23,107)
subs96401 =[((0, 10), 'subclip 1a subtitle', 'bottom', 'white'),
((10, 84), ' ', 'bottom', 'white')
]
clip1a_with_subtitle = [annotate(clip1a.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs96401]
clip1b = clip1.subclip(121,141)
lst = [clip1a_with_subtitle, clip1b]

def flatten(l,result = []):
    if isinstance(l, list):
        for i in l:
            flatten(i)
    else:
        result.append(l)
    return result

flattened =  flatten(lst)
print(flattened)
result = concatenate_videoclips(flattened)
result.write_videofile("resources/result_videos/scenario2.webm",fps=25, threads=4)

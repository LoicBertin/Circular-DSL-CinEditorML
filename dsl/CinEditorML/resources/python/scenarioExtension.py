#Code generated from a CineditorML model
import numpy as np

from moviepy.editor import *
from moviepy.video import *
from moviepy.video.tools.segmenting import findObjects

screensize = (1920,1080)
rotMatrix = lambda a: np.array( [[np.cos(a),np.sin(a)],
                                 [-np.sin(a),np.cos(a)]] )
def vortex(screenpos,i,nletters):
    d = lambda t : 1.0/(0.3+t**8) #damping
    a = i*np.pi/ nletters # angle of the movement
    v = rotMatrix(a).dot([-1,0])
    if i%2 : v[1] = -v[1]
    return lambda t: screenpos+400*d(t)*rotMatrix(0.5*d(t)*a).dot(v)

s2 = TextClip(txt="I AM ANIMATED FUCK OFF",fontsize=70,color='white',font="Amiri-Bold").set_duration(10).set_position("center")
cvc = CompositeVideoClip( [s2.set_pos('center')],
                        size=screensize)

letters = findObjects(cvc)

def moveLetters(letters, funcpos):
    return [ letter.set_pos(funcpos(letter.screenpos,i,len(letters)))
              for i,letter in enumerate(letters)]

s2 =  CompositeVideoClip( moveLetters(letters, vortex), size = screensize).subclip(0,5)

result = concatenate_videoclips([s2])
result.write_videofile("resources/result_videos/scenario2.webm",fps=25)

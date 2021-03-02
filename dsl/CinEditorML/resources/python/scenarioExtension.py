#Code generated from a CineditorML model
import numpy as np
from moviepy.editor import *
from moviepy.video import *
from moviepy.video.tools.credits import credits1
from moviepy.video.tools.segmenting import findObjects

#cf: https://stackoverflow.com/questions/36667702/adding-subtitles-to-a-movie-using-moviepy
def annotate(clip, txt, position, txt_color='red', fontsize=50, font='Xolonium-Bold'):
        txtclip = TextClip(txt, fontsize=fontsize, font=font, color=txt_color)
        cvc = CompositeVideoClip([clip, txtclip.set_pos(position)])
        return cvc.set_duration(clip.duration)

screensize = (1920,1080)
def createCredits(collaborators, speed=200):
    text_file = open("Output.txt", "w")
    text_file.write(".blank 4\n..Collaborators :\n" + collaborators.replace(",","\n"))
    text_file.close()

    credits = credits1('Output.txt',3*1920/4)
    scrolling_credits = credits.set_pos(lambda t:('center',-1*speed*t))


    final = CompositeVideoClip([scrolling_credits], size=screensize)
    return final
rotMatrix = lambda a: np.array( [[np.cos(a),np.sin(a)],
                                 [-np.sin(a),np.cos(a)]] )
def vortex(screenpos,i,nletters):
    d = lambda t : 1.0/(0.3+t**8) #damping
    a = i*np.pi/ nletters # angle of the movement
    v = rotMatrix(a).dot([-1,0])
    if i%2 : v[1] = -v[1]
    return lambda t: screenpos+400*d(t)*rotMatrix(0.5*d(t)*a).dot(v)

def cascade(screenpos,i,nletters):
    v = np.array([0,-1])
    d = lambda t : 1 if t<0 else abs(np.sinc(t)/(1+t**4))
    return lambda t: screenpos+v*400*d(t-0.15*i)

def arrive(screenpos,i,nletters):
    v = np.array([-1,0])
    d = lambda t : max(0, 3-3*t)
    return lambda t: screenpos-400*v*d(t-0.2*i)

def vortexout(screenpos,i,nletters):
    d = lambda t : max(0,t) #damping
    a = i*np.pi/ nletters # angle of the movement
    v = rotMatrix(a).dot([-1,0])
    if i%2 : v[1] = -v[1]
    return lambda t: screenpos+400*d(t-0.1*i)*rotMatrix(-0.2*d(t)*a).dot(v)

#https://stackoverflow.com/questions/23407566/how-to-flatten-a-list-to-return-a-new-list-with-all-the-elements
def flatten(l,result = []):
        if isinstance(l, list):
                for i in l:
                        flatten(i)
        else:
                result.append(l)
        return result

def moveLetters(letters, funcpos):
    return [ letter.set_pos(funcpos(letter.screenpos,i,len(letters)))
              for i,letter in enumerate(letters)]

textIntro = TextClip(txt="MY SUPER TOOL",fontsize=70,color='white',font="Amiri-Bold").set_duration(10).set_position("center")
m729ff098 = CompositeVideoClip( [textIntro.set_pos('center')],
                        size=screensize)

letters = findObjects(m729ff098)

textIntro =  CompositeVideoClip( moveLetters(letters, cascade), size = screensize).subclip(0,5)


clip1 = VideoFileClip("resources/video/dj_rexma.mp4")
credit = createCredits("Bernard,Didier,Francois,Stephane,Loic,Guillaume", 300).subclip(0,4)
credit2 = createCredits("Bernard,Didier,Francois,Stephane,Loic,Guillaume,troll,guilhem,test", 200).subclip(0,10)
credit3 = createCredits("Bernard,Didier,Francois,Stephane", 100).subclip(0,9)
result = concatenate_videoclips(flatten([textIntro,clip1,credit,credit2,credit3]))
result.write_videofile("resources/result_videos/scenarioExtension.webm",fps=25, threads=4)

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

backgroundintroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(5)
subs0 =[((0, 5), 'Intro Title', 'center', 'white'),
((5, backgroundintroClip.duration), ' ', 'bottom', 'white')]
introClip = [annotate(backgroundintroClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs0]
textIntro = TextClip(txt="MA VIDÃ©O DE DSL",fontsize=70,color='white',font="Amiri-Bold").set_duration(5).set_position("center")
m11151fbd = CompositeVideoClip( [textIntro.set_pos('center')],
                        size=screensize)

letters = findObjects(m11151fbd)

textIntro =  CompositeVideoClip( moveLetters(letters, cascade), size = screensize).subclip(0,5)


clip1 = VideoFileClip("target/video/Alice's cast work.webm")
clip1a = clip1.subclip(23,43)
subs1 =[((0, 5), 'Le premier clip, le travail d'Alice', 'bottom', 'white'),
((5, 7), ' ', 'bottom', 'white'),
((7, 13), 'un travail trÃ¨s intÃ©rÃ©ssant !!', 'bottom', 'white'),
((13, clip1a.duration - 5), ' ', 'bottom', 'white'),
((clip1a.duration - 5, clip1a.duration), 'on va changer de clip', 'bottom', 'white')]
clip1a = [annotate(clip1a.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs1]
clip2 = VideoFileClip("target/video/Alice&BobHolidaysPart1.webm")
subs2 =[((0, 5), 'on va changer de clip', 'bottom', 'white'),
((5, clip2.duration), ' ', 'bottom', 'white')]
clip2 = [annotate(clip2.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs2]
credit = createCredits("Guillaume, Loic, Stephane, Virgile", 300).subclip(0,3)
result = concatenate_videoclips(flatten([introClip,textIntro,clip1a,clip2,credit]))
result.write_videofile("./scenarioVideo.webm",fps=25, threads=4)

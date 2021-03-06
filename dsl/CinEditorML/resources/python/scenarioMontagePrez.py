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

fullClipPrez = VideoFileClip("resources/video/prez.mp4")
backgroundintroClip= ColorClip(size=(1920,1080), color=(0, 0, 0)).set_duration(5)
subs0 =[((0, 5), 'Presentation of the CinEditorML language', 'center', 'red'),
((5, backgroundintroClip.duration), ' ', 'bottom', 'red')]
introClip = [annotate(backgroundintroClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs0]
backgroundintroClip2= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs1 =[((0, 5), 'How to create a colored background with text', 'center', 'red'),
((5, backgroundintroClip2.duration), ' ', 'bottom', 'red')]
introClip2 = [annotate(backgroundintroClip2.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs1]
createBackgroundClip = fullClipPrez.subclip(0,34)
subs2 =[((0, 10), 'The background can be the name of a clip \n or a color (BLACK, BLUE, GREEN, RED, etc )', 'bottom', 'red'),
((10, 11), ' ', 'bottom', 'red'),
((11, 18), 'The position can be TOP, BOTTOM, LEFT, RIGHT, CENTER', 'bottom', 'red'),
((18, 19), ' ', 'bottom', 'red'),
((19, 34), 'createClip {name} during {duration} with_background \n {color} with_text {text} at {position} \n from {startTime} to {endTime}', 'bottom', 'red'),
((34, createBackgroundClip.duration), ' ', 'bottom', 'red')]
createBackgroundClip = [annotate(createBackgroundClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs2]
backgroundintroClip3= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs3 =[((0, 5), 'How to add an animated text', 'center', 'red'),
((5, backgroundintroClip3.duration), ' ', 'bottom', 'red')]
introClip3 = [annotate(backgroundintroClip3.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs3]
createAnimatedTextClip = fullClipPrez.subclip(34,60)
subs4 =[((0, 10), 'The animation can be : CASCADE, VORTEX, VORTEXOUT, ARRIVE', 'bottom', 'red'),
((10, 12), ' ', 'bottom', 'red'),
((12, 26), 'text {text} named {name} during {duration} at \n {position} animated_with {animation}', 'bottom', 'red'),
((26, createAnimatedTextClip.duration), ' ', 'bottom', 'red')]
createAnimatedTextClip = [annotate(createAnimatedTextClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs4]
backgroundintroClip4= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs5 =[((0, 5), 'How to import a video from a file', 'center', 'red'),
((5, backgroundintroClip4.duration), ' ', 'bottom', 'red')]
introClip4 = [annotate(backgroundintroClip4.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs5]
importVideoClip = fullClipPrez.subclip(61,82)
subs6 =[((0, 10), 'You can use relative path or full path, \n make sure to point out the good directory \n in case of relative path', 'bottom', 'red'),
((10, 11), ' ', 'bottom', 'red'),
((11, 21), 'importVideoClip {pathOfTheVideo} named {name}', 'bottom', 'red'),
((21, importVideoClip.duration), ' ', 'bottom', 'red')]
importVideoClip = [annotate(importVideoClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs6]
backgroundintroClip5= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs7 =[((0, 5), 'How to cut a video imported', 'center', 'red'),
((5, backgroundintroClip5.duration), ' ', 'bottom', 'red')]
introClip5 = [annotate(backgroundintroClip5.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs7]
cutVideoClip = fullClipPrez.subclip(82,103)
subs8 =[((0, 10), 'Take care to not cut your video after the end !', 'bottom', 'red'),
((10, 11), ' ', 'bottom', 'red'),
((11, 21), 'subClipOf {nameOfTheOriginalVideo} from \n {startTime} to {endTime} named {newName}', 'bottom', 'red'),
((21, cutVideoClip.duration), ' ', 'bottom', 'red')]
cutVideoClip = [annotate(cutVideoClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs8]
backgroundintroClip6= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs9 =[((0, 5), 'How to add text on a clip', 'center', 'red'),
((5, backgroundintroClip6.duration), ' ', 'bottom', 'red')]
introClip6 = [annotate(backgroundintroClip6.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs9]
addTextClip1 = fullClipPrez.subclip(103,132)
subs10 =[((0, 7), 'Different way to time your text, \n here we use "during" which will add \n the text from 0 to the time set', 'bottom', 'red'),
((7, 8), ' ', 'bottom', 'red'),
((8, 13), 'Your time must not be longer than the duration of the video ', 'bottom', 'red'),
((13, 14), ' ', 'bottom', 'red'),
((14, 29), 'addText {text} on {nameOfTheClip} during {duration}', 'bottom', 'red'),
((29, addTextClip1.duration), ' ', 'bottom', 'red')]
addTextClip1 = [annotate(addTextClip1.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs10]
addTextClip2 = fullClipPrez.subclip(132,162)
subs11 =[((0, 7), 'Another way to add text is to use "from" "to"', 'bottom', 'red'),
((7, 8), ' ', 'bottom', 'red'),
((8, 13), 'Same here, make sure to not go too far ', 'bottom', 'red'),
((13, addTextClip2.duration - 15), ' ', 'bottom', 'red'),
((addTextClip2.duration - 15, addTextClip2.duration), 'addText {text} on {nameOfTheClip} from {startTime} to {endTime}', 'bottom', 'red')]
addTextClip2 = [annotate(addTextClip2.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs11]
addTextClip3 = fullClipPrez.subclip(180,208)
subs12 =[((0, 7), 'Another way to add text  is to use "backward"  \n which will add text before \n the end of the clip', 'bottom', 'red'),
((7, 8), ' ', 'bottom', 'red'),
((8, 13), 'You can use "and_on" to apply your text on multiple clips ', 'bottom', 'red'),
((13, 14), ' ', 'bottom', 'red'),
((14, 28), 'addText {text} on {nameOfTheClip} backward {duration} \n and_on {nameOfTheSecondClip} during {duration2}', 'bottom', 'red'),
((28, addTextClip3.duration), ' ', 'bottom', 'red')]
addTextClip3 = [annotate(addTextClip3.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs12]
backgroundintroClip7= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs13 =[((0, 5), 'How to add a credit clip at the end', 'center', 'red'),
((5, backgroundintroClip7.duration), ' ', 'bottom', 'red')]
introClip7 = [annotate(backgroundintroClip7.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs13]
addCreditClip = fullClipPrez.subclip(208,238)
subs14 =[((0, 6), 'Here you can generate a credit with the name of the people to credit', 'bottom', 'red'),
((6, 7), ' ', 'bottom', 'red'),
((7, 13), 'To create a new line on credit, you must separate the name with a coma', 'bottom', 'red'),
((13, 14), ' ', 'bottom', 'red'),
((14, 19), 'speed can be FAST, SLOW or NORMAL', 'bottom', 'red'),
((19, 20), ' ', 'bottom', 'red'),
((20, 30), 'makeCreditsWith {nameOfThePeople} named {name} at_speed {speed}', 'bottom', 'red'),
((30, addCreditClip.duration), ' ', 'bottom', 'red')]
addCreditClip = [annotate(addCreditClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs14]
backgroundintroClip8= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs15 =[((0, 5), 'How to concatenate your clips', 'center', 'red'),
((5, backgroundintroClip8.duration), ' ', 'bottom', 'red')]
introClip8 = [annotate(backgroundintroClip8.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs15]
makeVideoClip = fullClipPrez.subclip(238,288)
subs16 =[((0, 10), 'With this command you can specify all the clips you want to use in your final video', 'bottom', 'red'),
((10, 11), ' ', 'bottom', 'red'),
((11, 21), 'Take care of the order !', 'bottom', 'red'),
((21, 22), ' ', 'bottom', 'red'),
((22, 50), 'makeVideoClip {nameOfYourVideo} with {nameOfTheClip} then {nameOfTheClip} (then ...)', 'bottom', 'red'),
((50, makeVideoClip.duration), ' ', 'bottom', 'red')]
makeVideoClip = [annotate(makeVideoClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs16]
backgroundintroClip9= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs17 =[((0, 5), 'How to export your video', 'center', 'red'),
((5, backgroundintroClip9.duration), ' ', 'bottom', 'red')]
introClip9 = [annotate(backgroundintroClip9.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs17]
exportVideoClip = fullClipPrez.subclip(288,300)
subs18 =[((0, 4), 'This command is used to export your video on your computer', 'bottom', 'red'),
((4, 5), ' ', 'bottom', 'red'),
((5, 12), 'export {nameOfYourVideo} at {path}', 'bottom', 'red'),
((12, exportVideoClip.duration), ' ', 'bottom', 'red')]
exportVideoClip = [annotate(exportVideoClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs18]
backgroundintroClip10= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs19 =[((0, 5), 'The visualize button', 'center', 'red'),
((5, backgroundintroClip10.duration), ' ', 'bottom', 'red')]
introClip10 = [annotate(backgroundintroClip10.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs19]
visualizeButtonClip = fullClipPrez.subclip(300,308)
subs20 =[((0, 8), 'This button check if the spelling is good and \n show your python code on the top right window', 'bottom', 'red'),
((8, visualizeButtonClip.duration), ' ', 'bottom', 'red')]
visualizeButtonClip = [annotate(visualizeButtonClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs20]
backgroundintroClip11= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs21 =[((0, 5), 'The run button', 'center', 'red'),
((5, backgroundintroClip11.duration), ' ', 'bottom', 'red')]
introClip11 = [annotate(backgroundintroClip11.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs21]
runClip = fullClipPrez.subclip(320,340)
subs22 =[((0, 20), 'On the bottom right window, you can see the result \n of the execution of the python code', 'bottom', 'red'),
((20, runClip.duration), ' ', 'bottom', 'red')]
runClip = [annotate(runClip.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs22]
backgroundintroClip12= ColorClip(size=(1920,1080), color=(0, 0, 255)).set_duration(5)
subs23 =[((0, 5), 'The result Video', 'center', 'red'),
((5, backgroundintroClip12.duration), ' ', 'bottom', 'red')]
introClip12 = [annotate(backgroundintroClip12.subclip(from_t, to_t), txt, position, color) for (from_t, to_t), txt, position, color in subs23]
resultVideoFromScript = VideoFileClip("resources/video/scenarioVideoPresentation.webm")
result = concatenate_videoclips(flatten([introClip,introClip2,createBackgroundClip,introClip3,createAnimatedTextClip,introClip4,importVideoClip,introClip5,cutVideoClip,introClip6,addTextClip1,addTextClip2,addTextClip3,introClip7,addCreditClip,introClip8,makeVideoClip,introClip9,exportVideoClip,introClip10,visualizeButtonClip,introClip11,runClip,introClip12,resultVideoFromScript]))
result.write_videofile("resources/result_videos/scenarioVideo.webm",fps=25, threads=4)

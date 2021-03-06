importVideoClip "resources/video/prez.mp4" named "fullClipPrez"

createClip "introClip" during 5 with_background BLACK with_text "Presentation of the CinEditorML language" at CENTER from 0 to 5

createClip "introClip2" during 5 with_background BLUE with_text "How to create a colored background with text" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 0 to 34 named "createBackgroundClip"
addText "The background can be the name of a clip \\n or a color (BLACK, BLUE, GREEN, RED, etc )" on "createBackgroundClip" from 0 to 10
addText "The position can be TOP, BOTTOM, LEFT, RIGHT, CENTER" on "createBackgroundClip" from 11 to 18
addText "createClip {name} during {duration} with_background \\n {color} with_text {text} at {position} \\n from {startTime} to {endTime}" on "createBackgroundClip" from 19 to 34

createClip "introClip3" during 5 with_background BLUE with_text "How to add an animated text" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 34 to 60 named "createAnimatedTextClip"
addText "The animation can be : CASCADE, VORTEX, VORTEXOUT, ARRIVE" on "createAnimatedTextClip" from 0 to 10
addText "text {text} named {name} during {duration} at \\n {position} animated_with {animation}" on "createAnimatedTextClip" from 12 to 26

createClip "introClip4" during 5 with_background BLUE with_text "How to import a video from a file" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 61 to 82 named "importVideoClip"
addText "You can use relative path or full path, \\n make sure to point out the good directory \\n in case of relative path" on "importVideoClip" from 0 to 10
addText "importVideoClip {pathOfTheVideo} named {name}" on "importVideoClip" from 11 to 21

createClip "introClip5" during 5 with_background BLUE with_text "How to cut a video imported" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 82 to 103 named "cutVideoClip"
addText "Take care to not cut your video after the end !" on "cutVideoClip" from 0 to 10
addText "subClipOf {nameOfTheOriginalVideo} from \\n {startTime} to {endTime} named {newName}" on "cutVideoClip" from 11 to 21

createClip "introClip6" during 5 with_background BLUE with_text "How to add text on a clip" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 103 to 132 named "addTextClip1"
addText "Different way to time your text, \\n here we use \"during\" which will add \\n the text from 0 to the time set" on "addTextClip1" from 0 to 7
addText "Your time must not be longer than the duration of the video " on "addTextClip1" from 8 to 13
addText "addText {text} on {nameOfTheClip} during {duration}" on "addTextClip1" from 14 to 29

subClipOf "fullClipPrez" from 132 to 162 named "addTextClip2"
addText "Another way to add text is to use \"from\" \"to\"" on "addTextClip2" from 0 to 7
addText "Same here, make sure to not go too far " on "addTextClip2" from 8 to 13
addText "addText {text} on {nameOfTheClip} from {startTime} to {endTime}" on "addTextClip2" backward 15

subClipOf "fullClipPrez" from 180 to 208 named "addTextClip3"
addText "Another way to add text  is to use \"backward\"  \\n which will add text before \\n the end of the clip" on "addTextClip3" from 0 to 7
addText "You can use \"and_on\" to apply your text on multiple clips " on "addTextClip3" from 8 to 13
addText "addText {text} on {nameOfTheClip} backward {duration} \\n and_on {nameOfTheSecondClip} during {duration2}" on "addTextClip3" from 14 to 28

createClip "introClip7" during 5 with_background BLUE with_text "How to add a credit clip at the end" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 208 to 238 named "addCreditClip"
addText "Here you can generate a credit with the name of the people to credit" on "addCreditClip" from 0 to 6
addText "To create a new line on credit, you must separate the name with a coma" on "addCreditClip" from 7 to 13
addText "speed can be FAST, SLOW or NORMAL" on "addCreditClip" from 14 to 19
addText "makeCreditsWith {nameOfThePeople} named {name} at_speed {speed}" on "addCreditClip" from 20 to 30

createClip "introClip8" during 5 with_background BLUE with_text "How to concatenate your clips" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 238 to 288 named "makeVideoClip"
addText "With this command you can specify all the clips you want to use in your final video" on "makeVideoClip" from 0 to 10
addText "Take care of the order !" on "makeVideoClip" from 11 to 21
addText "makeVideoClip {nameOfYourVideo} with {nameOfTheClip} then {nameOfTheClip} (then ...)" on "makeVideoClip" from 22 to 50

createClip "introClip9" during 5 with_background BLUE with_text "How to export your video" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 288 to 300 named "exportVideoClip"
addText "This command is used to export your video on your computer" on "exportVideoClip" from 0 to 4
addText "export {nameOfYourVideo} at {path}" on "exportVideoClip" from 5 to 12

createClip "introClip10" during 5 with_background BLUE with_text "The visualize button" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 300 to 308 named "visualizeButtonClip"
addText "This button check if the spelling is good and \\n show your python code on the top right window" on "visualizeButtonClip" from 0 to 8

createClip "introClip11" during 5 with_background BLUE with_text "The run button" at CENTER from 0 to 5
subClipOf "fullClipPrez" from 320 to 340 named "runClip"
addText "On the bottom right window, you can see the result \\n of the execution of the python code" on "runClip" from 0 to 20

createClip "introClip12" during 5 with_background BLUE with_text "The result Video" at CENTER from 0 to 5
importVideoClip "resources/video/scenarioVideoPresentation.webm" named "resultVideoFromScript"

makeVideoClip "videoPresentation" with "introClip" then "introClip2" then "createBackgroundClip" then "introClip3" then "createAnimatedTextClip" then "introClip4" then "importVideoClip" then "introClip5" then "cutVideoClip" then "introClip6" then "addTextClip1" then "addTextClip2" then "addTextClip3" then "introClip7" then "addCreditClip" then "introClip8" then "makeVideoClip" then "introClip9" then "exportVideoClip" then "introClip10" then "visualizeButtonClip" then "introClip11" then "runClip" then "introClip12" then "resultVideoFromScript"

export "scenarioVideo" at "resources/result_videos"

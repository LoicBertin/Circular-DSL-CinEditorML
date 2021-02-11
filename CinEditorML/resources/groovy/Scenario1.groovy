text "Intro Title" named "textIntro" during "10" at CENTER
backgroundClip BLACK named "introClip" during "10"
addText "textIntro" on "introClip"

videoClip "resources/video/dj_rexma.mp4" named "clip1"
videoClip "resources/video/in_and_in_and_in.mp4" named "clip2"

text "THANKS FOR WATCHING" named "textOutro" during "15" at CENTER
backgroundClip BLACK named "outroClip" during "15"
addText "textOutro" on "outroClip"

makeVideoClip "final" with "introClip" then "clip1" then "clip2" then "outroClip"

export "final" at "/resources/video"
text "Intro Title" named "textIntro" during "10" at CENTER
backgroundClip BLACK named "introClip" during "10"
addText "textIntro" on "introClip"

videoClip "resources/video/dj_rexma.mp4" named "clip1"
subClipOf "clip1" from 1 to 4 named "clip1a"
subClipOf "clip1" from 5 to 7 named "clip1b"

text "THANKS FOR WATCHING" named "textOutro" during "10" at CENTER
backgroundClip BLACK named "outroClip" during "10"
addText "textOutro" on "outroClip"

makeVideoClip "scenario2" with "introClip" then "clip1a" then "clip1b" then "outroClip"


export "scenario2" at "resources/result_videos"
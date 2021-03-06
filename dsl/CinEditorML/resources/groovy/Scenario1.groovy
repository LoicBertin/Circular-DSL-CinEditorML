text "Chez Marex le 23/02/2021" named "textIntro" during 10 at CENTER
backgroundClip BLACK named "introClip" during 10
addText "textIntro" on "introClip"

importVideoClip "resources/video/dj_rexma.mp4" named "clip1"
importVideoClip "resources/video/in_and_in_and_in.mp4" named "clip2"

text "THANKS FOR WATCHING" named "textOutro" during 15 at CENTER
backgroundClip BLACK named "outroClip" during 15
addText "textOutro" on "outroClip"

makeVideoClip "scenario1" with "introClip" then "clip1" then "clip2" then "outroClip"

export "scenario1" at "resources/result_videos"
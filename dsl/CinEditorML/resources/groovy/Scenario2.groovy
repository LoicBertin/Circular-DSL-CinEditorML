createClip "introClip" during 10 with_background BLACK with_text "Intro Title" at CENTER from 4 to 7

videoClip "resources/video/alderamin 1.webm" named "clip1"
subClipOf "clip1" from 1 to 15 named "clip1a"
createClip "clip1a_with_subtitle" with_background "clip1a" with_text "subclip 1a subtitle" at BOTTOM from 7 to 12
text "subclip 30 secs after s1" named "s2" during 10 at LEFT

subClipOf "clip1" from 5 to 7 named "clip1b"
text "subclip 1b subtitle" named "s3" during 15 at RIGHT
addText "s3" on "clip1b"

createClip "outroClip" during 10 with_background BLACK with_text "THANKS FOR WATCHING" at CENTER from 1 to 9

makeVideoClip "scenario2" with "introClip" then "clip1a" then "clip1b" then "outroClip"


export "scenario2" at "resources/result_videos"
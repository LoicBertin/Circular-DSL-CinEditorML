createClip "introClip" during 10 with_background BLACK with_text "Intro Title" at CENTER from 0 to 10

importVideoClip "resources/video/alderamin 1.webm" named "clip1"
subClipOf "clip1" from 23 to 107 named "clip1a"
createClip "clip1a_with_subtitle" with_background "clip1a" with_text "subclip 1a subtitle" at BOTTOM from 0 to 10 and_with_text "subclip 1a subtitle 2" at BOTTOM from 40 to 50
//addText "subclip 1a subtitle 2" at BOTTOM from 40 to 50 on "clip1a_with_subtitle"

subClipOf "clip1" from 121 to 141 named "clip1b"
//createClip "clip1b_with_subtitle" with_background "clip1b" with_text "subclip 1b subtitle" at BOTTOM from -5 to 10

createClip "outroClip" during 10 with_background BLACK with_text "THANKS FOR WATCHING" at CENTER from 0 to 10

makeVideoClip "scenario2" with "introClip" then "clip1a_with_subtitle" then "clip1b" then "outroClip"


export "scenario2" at "resources/result_videos"
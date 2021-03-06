createClip "introClip" during 10 with_background BLACK with_text "Alice cast work" at CENTER from 0 to 10

importVideoClip "resources/video/Alice's cast work.webm" named "clip1"
subClipOf "clip1" from 23 to 107 named "clip1a"
createClip "clip1a_with_subtitle" with_background "clip1a" with_text "it is important to have an eye contact with your public" at BOTTOM from 0 to 10 and_with_text "do not forget to smile and show you want to be here" at BOTTOM from 40 to 50

subClipOf "clip1" from 121 to 141 named "clip1b"

addText "do not forget to tell stories to people" on "clip1a_with_subtitle" backward 5 and_on "clip1b" during 10

createClip "outroClip" during 10 with_background BLACK with_text "THANKS FOR WATCHING" at CENTER from 0 to 10

makeVideoClip "scenario2" with "introClip" then "clip1a_with_subtitle" then "clip1b" then "outroClip"


export "scenario2" at "resources/result_videos"
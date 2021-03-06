text "Alice & Bob Holidays !!!" named "textIntro" during 10 at CENTER
backgroundClip BLACK named "introClip" during 10
addText "textIntro" on "introClip"

importVideoClip "resources/video/Alice&BobHolidaysPart1.webm" named "clip1"
importVideoClip "resources/video/Alice&BobHolidaysPart2.webm" named "clip2"

text "THANKS FOR WATCHING" named "textOutro" during 15 at CENTER
backgroundClip BLACK named "outroClip" during 15
addText "textOutro" on "outroClip"

makeVideoClip "scenario1" with "introClip" then "clip1" then "clip2" then "outroClip"

export "scenario1" at "resources/result_videos"
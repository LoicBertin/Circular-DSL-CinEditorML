createClip "introClip" during 5 with_background BLACK with_text "Intro Title" at CENTER from 0 to 5

text "Ma vidéo de DSL" named "textIntro" during 5 at CENTER animated_with CASCADE

importVideoClip "target/video/Alice's cast work.webm" named "clip1"
subClipOf "clip1" from 23 to 43 named "clip1a"
addText "Le premier clip, le travail d'Alice" on "clip1a" during 5
addText "un travail très intéréssant !!" on "clip1a" from 7 to 13

importVideoClip "target/video/Alice&BobHolidaysPart1.webm" named "clip2"

addText "on va changer de clip" on "clip1a" backward 5 and_on "clip2" during 5

makeCreditsWith "Guillaume, Loic, Stephane, Virgile" named "credit" at_speed FAST

makeVideoClip "scenarioVideoPresentation" with "introClip" then "textIntro" then "clip1a" then "clip2" then "credit"

export "scenarioVideoPresentation" at "."

text "MY SUPER TOOL" named "textIntro" during 10 at CENTER animated_with CASCADE
importVideoClip "resources/video/dj_rexma.mp4" named "clip1"
makeCreditsWith "Bernard,Didier,Francois,Stephane,Loic,Guillaume" named "credit" at_speed FAST

makeVideoClip "scenarioExtension" with "textIntro" then "clip1" then "credit"
export "scenarioExtension" at "resources/result_videos"
text "MY SUPER TOOL" named "textIntro" during 10 at CENTER animated_with CASCADE
importVideoClip "resources/video/dj_rexma.mp4" named "clip1"
makeCreditsWith "Bernard,Didier,Francois,Stephane,Loic,Guillaume" named "credit" at_speed FAST
makeCreditsWith "Bernard,Didier,Francois,Stephane,Loic,Guillaume,troll,guilhem,test" named "credit2" at_speed NORMAL
makeCreditsWith "Bernard,Didier,Francois,Stephane" named "credit3" at_speed SLOW

makeVideoClip "scenarioExtension" with "textIntro" then "clip1" then "credit" then "credit2" then "credit3"
export "scenarioExtension" at "resources/result_videos"
sensor "button" pin 9
buzzer "buzzer" pin 13

state "begin" means buzzer plays C4 "for" "short" duration "3" "time(s)"
state "on" means buzzer plays STOP
state "finished" means buzzer plays C4 "for" "long" duration "1" "time(s)"
state "off" means buzzer plays STOP

initial off

from off to begin when button becomes high
from begin to on when button becomes low
from on to finished when button becomes high
from finished to off when button becomes low


export "Rendu1ScenarioExtension"
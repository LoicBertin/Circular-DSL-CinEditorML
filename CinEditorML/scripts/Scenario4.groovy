sensor "button" pin 9
actuator "led1" pin 12
buzzer "buzzer" pin 13

state "buzz" means buzzer plays C4
state "on" means buzzer plays STOP and led1 becomes high
state "off" means led1 becomes low

initial off

from off to buzz when button becomes high
from buzz to on when button becomes high
from on to off when button becomes high


export "Rendu1Scenario4"
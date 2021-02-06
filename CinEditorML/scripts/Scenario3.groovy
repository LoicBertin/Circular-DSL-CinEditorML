sensor "button" pin 9
actuator "led1" pin 12

state "on" means led1 becomes high
state "off" means led1 becomes low

initial off

from on to off when button becomes high
from off to on when button becomes high

export "Rendu1Scenario3"
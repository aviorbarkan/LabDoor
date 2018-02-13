import RPi.GPIO as gpio
import time
gpio.setwarnings(False)
gpio.setmode(gpio.BCM)
gpio.setup(17, gpio.OUT)

gpio.output(17,1)
time.sleep(3)
gpio.output(17,0)



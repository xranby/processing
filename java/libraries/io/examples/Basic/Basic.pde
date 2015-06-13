import processing.io.*;
boolean ledOn = false;

void setup() {
  // GPIO2 is physical pin 3 on the Raspberry Pi pin header, next to 5V power
  // GPIO3 is physical pin 5, next to Ground
  GPIO.pinMode(2, GPIO.INPUT);
  GPIO.pinMode(3, GPIO.OUTPUT);
  frameRate(1);
}

void draw() {
  // sense the input pin
  if (GPIO.digitalRead(2) == GPIO.HIGH) {
    fill(255, 255, 255);
  } else {
    fill(0, 0, 0);
  }
  ellipse(width/2, height/2, width*0.75, height*0.75);
  
  // and toggle a LED on the output pin
  if (ledOn) {
    GPIO.digitalWrite(3, GPIO.LOW);
  } else {
    GPIO.digitalWrite(3, GPIO.HIGH);
  }
  ledOn = !ledOn;
}

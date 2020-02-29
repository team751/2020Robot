#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BNO055.h>
#include <utility/imumaths.h>

Adafruit_BNO055 bno = Adafruit_BNO055();

const int switchTime = 500; // Debounce time in microseconds.
//const char queryKey = "q"
const String recalibrateStr = "c\n";

volatile double orientation = 0.0;

const boolean debug = false; // Debug flag.

void setup() {
  Serial.begin(9600);
  Serial.setTimeout(10);
  pinMode(3, INPUT_PULLUP);
  pinMode(2, INPUT_PULLUP);

  initializeIMU();
}

void loop() {
  orientation = getOrientation();
  
  sendData();

  if(Serial.available() > 0) {
    String recString = Serial.readString();
    
    if (recString == recalibrateStr) {
      recalibrate();
    }
  }
}

void sendData() {
  Serial.println(String(orientation) + "e");
}

void recalibrate() {
  if (debug) {
    Serial.println("Recalibrating... HOLD STILL");
  }
  initializeIMU();
}

/*
   Returns the current orientation in degrees
   debug true will print out verbose debug messages

   Note: A delay is reccommended if this function will be
    called in quick succession. No more than 100ms is neccessary
*/
float getOrientation() {
  imu::Vector<3> euler = bno.getVector(Adafruit_BNO055::VECTOR_EULER);
  if (debug) {
    Serial.println("Getting orientation (X)");
  }
  if (debug) {
    Serial.println("    " + String(euler.x()));
  }     
  return (float) euler.x();
}

/*
   Intitializes the IMU. MUST BE CALLED FIRST
   debug true will print out verbose debug messages
   Returns true for success, false for failure

   NOTE: Initializing or reinitializing the sensor RESETS THE POSITION TO 0.
         IF YOUR SENSOR WAS READING "340" AND YOU RECALIBRATE, 340 WILL NOW BE
         0, EVEN IF YOU DID NOT MOVE THE SENSOR.
*/
boolean initializeIMU() {
  if (debug) {
    Serial.println("Initializing BNO055");
  }
  if (!bno.begin()) {
    Serial.println("    Failed");
    return false;
  }
  if (debug) {
    Serial.println("    Success");
  }
  //bno.setExtCrystalUse(true);
  return true;
}

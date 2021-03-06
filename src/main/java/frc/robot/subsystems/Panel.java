package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.core751.wrappers.WCANSparkMax;

public class Panel extends SubsystemBase {

    public enum WheelColor {
        BLUE(240, 255, 255, true, "Blue"), GREEN(120, 255, 255, true, "Green"), 
        RED(0, 255, 255, true, "Red"), YELLOW(60, 255, 255, true, "Yellow"),
        UNKNOWN(300, 255, 255, false, "Unknown"), BETWEEN(200, 255, 255, false, "Between");

        public final int[] HSV;
        public String colorName;
        public boolean color;

        public static WheelColor getTargetColor() {
            String gameData;
            gameData = DriverStation.getInstance().getGameSpecificMessage();
            if (gameData.length() > 0) {
                switch (gameData.charAt(0)) {
                case 'B':
                    return BLUE;
                case 'G':
                    return GREEN;
                case 'R':
                    return RED;
                case 'Y':
                    return YELLOW;
                default:
                    return UNKNOWN;
                }
            } else {
                return UNKNOWN;
            }
        }

        private WheelColor(int h, int s, int v, boolean color, String colorName) {
            this.HSV = new int[] { h, s, v };
            this.color = color;
            this.colorName = colorName;
        }

        public int directionTo(WheelColor o) {
            if (this == UNKNOWN || this == BETWEEN)
                return 0;
            int res = this.ordinal() - o.ordinal();
            if (res == 2 || res == -2)
                return 1;
            return res;
        }

        public int colorDist(WheelColor o) {
            if (this == BLUE && o == YELLOW)
                return 1;
            if (this == YELLOW && o == BLUE)
                return -1;
            return this.ordinal() - o.ordinal();
        }

        @Override
        public String toString() {
            return colorName;
        }
    }

    public enum PositionState {
        UNKNOWN, UP, DOWN;
    }

    private ColorSensorV3 firstColorSensor;
    private ColorSensorV3 secondColorSensor;
    private ColorMatch colorMatcher;

    private WheelColor lastColor;
    private float rotations;
    private double passiveUpSpeed;

    // private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    // private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    // private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    // private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524,
    // 0.113);
    private final Color kBlueTarget = ColorMatch.makeColor(0.20, 0.46, 0.34);
    private final Color kGreenTarget = ColorMatch.makeColor(0.20, 0.53, 0.256);
    private final Color kRedTarget = ColorMatch.makeColor(0.43, 0.37, 0.19);
    private final Color kYellowTarget = ColorMatch.makeColor(0.29, 0.54, 0.17);

    private WPI_VictorSPX spinMotor;
    private WPI_VictorSPX positionMotor;

    private DigitalInput topLimitSwitch;
    private DigitalInput bottomLimitSwitch;

    public PositionState positionState;
    public PositionState lastPositionState;

    public Panel(Port firstPort, Port secondPort, int speedMotorID, int positionMotorID, int topSwitchPin, int bottomSwitchPin) {
        System.out.println("First port below");
        try {
            System.out.println("Port number =" + firstPort.value);
            this.firstColorSensor = new ColorSensorV3(firstPort);
        } catch (Exception e) {System.out.println(e);}

        System.out.println("second port below");
        try {
            System.out.println("Port number =" + secondPort.value);
            this.secondColorSensor = new ColorSensorV3(secondPort);
        } catch (Exception e) {System.out.println(e);}  
        this.rotations = 0;
        this.colorMatcher = new ColorMatch();
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);  
        this.lastColor = WheelColor.UNKNOWN;
        this.spinMotor = new WPI_VictorSPX(speedMotorID);
        this.positionMotor = new WPI_VictorSPX(positionMotorID);
        this.topLimitSwitch = new DigitalInput(topSwitchPin);
        this.bottomLimitSwitch = new DigitalInput(bottomSwitchPin);
        this.positionState = this.touchingBottom()?PositionState.DOWN:this.touchingTop()?PositionState.UP:PositionState.UNKNOWN;
        this.lastPositionState = this.positionState==PositionState.UNKNOWN?PositionState.DOWN:PositionState.UNKNOWN;
        SmartDashboard.putNumber("Passive Panel Speed", 0.25);
        SmartDashboard.putBoolean("Top Limit", false);
        SmartDashboard.putBoolean("Bottom Limit", false);
        this.passiveUpSpeed = SmartDashboard.getNumber("Passive Panel Speed", 0.1);

        //System.out.println(this.firstColorSensor);
        //System.out.println(this.secondColorSensor);
    }

    public WheelColor getColor() {
        if (Math.min(firstColorSensor.getProximity(), secondColorSensor.getProximity()) < Constants.proximityThreshhold) {
            this.lastColor = WheelColor.UNKNOWN;
            return WheelColor.UNKNOWN;
        }

       WheelColor a = this.getFirstColor();
       WheelColor b = this.getSecondColor();
       if (a == b) {
           if (a.color && this.lastColor.color)this.rotations += 0.125*a.colorDist(this.lastColor);
           this.lastColor = a;
           return a;
       }
       if (a == this.lastColor || b == this.lastColor) {
           return this.lastColor;
       }
       if (this.lastColor == WheelColor.UNKNOWN || this.lastColor == WheelColor.BETWEEN) {
            this.lastColor = WheelColor.BETWEEN;
            return WheelColor.BETWEEN;
       }
       
       return WheelColor.UNKNOWN;
    }

    public WheelColor getFirstColor() {
        Color firstDetectedColor = firstColorSensor.getColor();
        ColorMatchResult firstMatch = colorMatcher.matchClosestColor(firstDetectedColor);
        
        if (firstMatch.color == kRedTarget) {
            return WheelColor.BLUE;
        } else if (firstMatch.color == kBlueTarget) {
            return WheelColor.RED;
        } else if (firstMatch.color == kYellowTarget) {
            return WheelColor.GREEN;
        } else if (firstMatch.color == kGreenTarget) {
            return WheelColor.YELLOW;
        } else {
            return WheelColor.UNKNOWN;
        }
    }

    public double[] getAverageColors() {
        Color firstDetectedColor = firstColorSensor.getColor();
        Color secondDetectedColor = secondColorSensor.getColor();
        double[] ret = new double[]{firstDetectedColor.red + secondDetectedColor.red, firstDetectedColor.green + secondDetectedColor.green, firstDetectedColor.blue + secondDetectedColor.blue};
        
        ret[0] /=2;
        ret[1] /=2;
        ret[2] /=2;
        return ret;
    }

    public WheelColor getSecondColor() {
        Color secondDetectedColor = secondColorSensor.getColor();
        ColorMatchResult secondMatch = colorMatcher.matchClosestColor(secondDetectedColor);
        
        if (secondMatch.color == kRedTarget) {
            return WheelColor.BLUE;
        } else if (secondMatch.color == kBlueTarget) {
            return WheelColor.RED;
        } else if (secondMatch.color == kYellowTarget) {
            return WheelColor.GREEN;
        } else if (secondMatch.color == kGreenTarget) {
            return WheelColor.YELLOW;
        } else {
            return WheelColor.UNKNOWN;
        }
    }

    public float getRotations(){
        return this.rotations;
    }

    public void clearRotations(){
        this.rotations = 0;
    }

    public void setSpinMotor(double speed) {
        this.spinMotor.set(speed);
    }

    public double getPassiveUpSpeed() {
        this.passiveUpSpeed = SmartDashboard.getNumber("Passive Panel Speed", 0.1);
        return this.passiveUpSpeed;
    }

    public void stopSpinMotor() {
        this.spinMotor.stopMotor();
        this.spinMotor.set(0);
    }


    public boolean touchingBottom() {
        SmartDashboard.putBoolean("Bottom Limit", this.bottomLimitSwitch.get());

        return this.bottomLimitSwitch.get();
        //return SmartDashboard.getBoolean("Bottom Limit", false);
    }

    public boolean touchingTop() {
        SmartDashboard.putBoolean("Top Limit", this.topLimitSwitch.get());

        return this.topLimitSwitch.get();
        //return SmartDashboard.getBoolean("Top Limit", false);
    }

    public void setPositionMotor(double speed) {
        this.positionMotor.set(speed);
    }

    public PositionState updateState() {
        SmartDashboard.putString("State", this.positionState.name());
        if (touchingTop()) {
            if (this.positionState != PositionState.UP) {
                this.lastPositionState = this.positionState;
                this.positionState = PositionState.UP;
                return PositionState.UP;
            }
        }else if (touchingBottom()) {
            if (this.positionState != PositionState.DOWN) {
                this.lastPositionState = this.positionState;
                this.positionState = PositionState.DOWN;
                return PositionState.DOWN;
            }
        } else {
            if (this.positionState != PositionState.UNKNOWN) {
                this.lastPositionState = this.positionState;
                this.positionState = PositionState.UNKNOWN;
                return PositionState.UNKNOWN;
            }
        }
        return null;
    }

}
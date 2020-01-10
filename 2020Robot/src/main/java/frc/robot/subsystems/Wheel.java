package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Wheel extends SubsystemBase {

    public enum WheelColor {
        RED,
        GREEN,
        BLUE,
        YELLOW,
        UNKNOWN
    }
    
    private ColorSensorV3 colorSensor;
    private ColorMatch colorMatcher;

    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public Wheel(Port port) {
        this.colorSensor = new ColorSensorV3(port);
        this.colorMatcher = new ColorMatch();
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);  
    }

    public WheelColor getColor() {
        SmartDashboard.putNumber("Proximity", colorSensor.getProximity());
        if (colorSensor.getProximity() < Constants.proximityThreshhold) return WheelColor.UNKNOWN;

        Color detectedColor = colorSensor.getColor();

        String colorString;
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        

        if (match.color == kBlueTarget) {
            return WheelColor.BLUE;
        } else if (match.color == kRedTarget) {
            return WheelColor.RED;
        } else if (match.color == kGreenTarget) {
            return WheelColor.GREEN;
        } else if (match.color == kYellowTarget) {
            return WheelColor.YELLOW;
        } else {
            return WheelColor.UNKNOWN;
        }
    }
}
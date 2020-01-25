package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Wheel extends SubsystemBase {

    public enum WheelColor {
        RED(0, 255, 255),
        GREEN(120, 255, 255),
        BLUE(240, 255, 255),
        YELLOW(60, 255, 255),
        UNKNOWN(300, 255, 255);

        public int[] HSV;

        private WheelColor(int h, int s, int v) {
            this.HSV = new int[]{h, s, v};
        }
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
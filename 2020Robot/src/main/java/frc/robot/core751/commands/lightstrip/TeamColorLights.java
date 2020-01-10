package frc.robot.core751.commands.lightstrip;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.core751.subsystems.LightStrip;

public class TeamColorLights extends CommandBase{

    private LightStrip lightStrip;
    private AddressableLEDBuffer abuf;
    private Alliance alliance;
    private Color allianceColor;

    public TeamColorLights(LightStrip lightStrip) {
        this.lightStrip = lightStrip;

        addRequirements(lightStrip);

    }

    @Override
    public void initialize() {
        this.abuf = new AddressableLEDBuffer(lightStrip.length);
        this.alliance = DriverStation.getInstance().getAlliance();
        switch (alliance) {
            case Red:
                this.allianceColor = Color.kRed;
            break;
            case Blue:
                this.allianceColor = Color.kBlue;
            break;
            default:
                this.allianceColor = Color.kPurple;
            break;
        }

        for (int i = 0; i < lightStrip.length; i++) {
            this.abuf.setLED(i, this.allianceColor);
        }
        this.lightStrip.setData(this.abuf);
        this.lightStrip.start();
    }

    @Override
    public void execute() {
        //this.lightStrip.setData(this.abuf);
    }

    
}
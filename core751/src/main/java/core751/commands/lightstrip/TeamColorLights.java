package core751.commands.lightstrip;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import core751.subsystems.LightStrip;

public class TeamColorLights extends CommandBase{

    private LightStrip lightStrip;
    private Alliance alliance;
    private int[] allianceColor;

    public TeamColorLights(LightStrip lightStrip) {
        this.lightStrip = lightStrip;

        addRequirements(lightStrip);

    }

    @Override
    public void initialize() {
        this.alliance = DriverStation.getInstance().getAlliance();
        switch (alliance) {
            case Red:
                this.allianceColor = new int[]{0, 255, 255};
            break;
            case Blue:
                this.allianceColor = new int[]{240, 255, 255};
            break;
            default:
                this.allianceColor = new int[]{300, 255, 255};
            break;
        }

        for (int i = 0; i < lightStrip.length; i++) {
            this.lightStrip.setHSVWave(i, 2, this.allianceColor);
        }
        this.lightStrip.update();
        this.lightStrip.start();
    }

    @Override
    public void execute() {
        for (int i = 0; i < lightStrip.length; i++) {
            this.lightStrip.setHSVWave(i, 2, this.allianceColor);
        }
        this.lightStrip.update();
    }

    
}
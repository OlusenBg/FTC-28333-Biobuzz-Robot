package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static Follower create(HardwareMap h) {
        // TODO: Pedro Pathing has not been tuned yet (no localizer/drivetrain
        // tuner in TeamCode/.../pedro/procedures has been run on the real robot).
        // Returning null here used to let callers crash later with a confusing
        // NullPointerException deep inside Pedro. Fail loudly and clearly instead
        // until this is wired up for real:
        // return new Follower(Drivetrain, Localizer, Foresight);
        throw new UnsupportedOperationException(
                "Constants.create() is not implemented yet: run the Pedro Pathing " +
                "tuners (see teamcode/pedro/procedures) on the physical robot first, " +
                "then build a real Follower here with those measured values.");
    }
}
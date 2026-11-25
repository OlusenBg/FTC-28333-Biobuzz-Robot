package org.firstinspires.ftc.teamcode.Script.OpMode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Script.SubSystem.Drivetrain;
import org.firstinspires.ftc.teamcode.Script.SubSystem.Intake;
import org.firstinspires.ftc.teamcode.Script.SubSystem.ROBOT;
import org.firstinspires.ftc.teamcode.Script.SubSystem.Shooter;
import org.firstinspires.ftc.teamcode.Script.SubSystem.Stopper;

// NOTE: Pedro Pathing path-following is NOT set up yet (Constants.create()
// deliberately throws until the localizer/drivetrain tuners have been run on
// the real robot - see teamcode/pedro/procedures). This autonomous uses simple
// timed drive power instead of a tuned Follower so it doesn't crash on start.
// Replace the timed moves below with real Paths once Pedro Pathing is tuned.
@Autonomous(name = "Main Auto", group = "Auto")
public class Main_Auto extends LinearOpMode {

    @Override
    public void runOpMode() {
        ROBOT robot = new ROBOT();
        robot.init(hardwareMap);

        Drivetrain drivetrain = new Drivetrain(robot);
        Intake intake = new Intake(robot);
        Shooter shooter = new Shooter(robot);
        Stopper stopper = new Stopper(robot);

        telemetry.addData("Status", "Initialized & Ready (timed autonomous, no path following)");
        telemetry.update();

        waitForStart();
        if (opModeIsActive()) {

            // 1. Drive forward for 1 second
            drivetrain.drive(0.5, 0, 0);
            sleep(1000);
            drivetrain.drive(0, 0, 0);

            // 2. Spin up the shooter and wait for it to reach speed
            shooter.shooter_On();
            long shooterStart = System.currentTimeMillis();
            while (opModeIsActive() && !shooter.isReadyToShoot()
                    && System.currentTimeMillis() - shooterStart < 3000) {
                telemetry.addData("Shooter Velocity", shooter.getVelocity());
                telemetry.update();
            }

            // 3. Release the stopper to fire, then stop everything
            stopper.stopper_Off();
            sleep(1500);

            shooter.shooter_Off();
            stopper.stopper_On();
            intake.intake1_Off();
            intake.intake2_off();

            telemetry.addData("Status", "Autonomous Complete");
            telemetry.update();
        }
    }
}

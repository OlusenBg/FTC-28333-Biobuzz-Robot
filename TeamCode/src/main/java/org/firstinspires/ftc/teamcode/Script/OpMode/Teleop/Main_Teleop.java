package org.firstinspires.ftc.teamcode.Script.OpMode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Script.SubSystem.Drivetrain;
import org.firstinspires.ftc.teamcode.Script.SubSystem.Intake;
import org.firstinspires.ftc.teamcode.Script.SubSystem.ROBOT;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class Main_Teleop extends LinearOpMode {

    @Override
    public void runOpMode() {
        // 1. Initialize Robot Hardware
        ROBOT robot = new ROBOT();
        robot.init(hardwareMap);

        Drivetrain drivetrain = new Drivetrain(robot);
        Intake intake = new Intake(robot);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // 2. Wait for the driver to press PLAY
        waitForStart();

        // 3. TeleOp Loop
        while (opModeIsActive()) {

            // Drive's the robot using gamepad1's left stick and right for rotation
            drivetrain.drive(
                -gamepad1.left_stick_y,
                 gamepad1.left_stick_x,
                 gamepad1.right_stick_x
            );

            // Example: Press/Hold 'A' button to turn intake ON, release to turn OFF
            if (gamepad1.a) {
                intake.intake_On();
            }
            else if (gamepad1.b) {
                intake.intake_Reverse();
            }
            else {
                intake.intake_Off();
            }

            // Update telemetry data on Driver Station screen
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}

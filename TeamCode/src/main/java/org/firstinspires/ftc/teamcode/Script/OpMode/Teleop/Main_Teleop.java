package org.firstinspires.ftc.teamcode.Script.OpMode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Script.SubSystem.Drivetrain;
import org.firstinspires.ftc.teamcode.Script.SubSystem.Intake;
import org.firstinspires.ftc.teamcode.Script.SubSystem.ROBOT;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class Main_Teleop extends LinearOpMode {

    private boolean endgameRumbled = false;

    @Override
    public void runOpMode() {
        ROBOT robot = new ROBOT();
        robot.init(hardwareMap);

        Drivetrain drivetrain = new Drivetrain(robot);
        Intake intake = new Intake(robot);

        VoltageSensor batterySensor = hardwareMap.voltageSensor.iterator().next();

        telemetry.addData("Status", "Initialized & Ready");
        telemetry.update();

        waitForStart();

        resetRuntime(); // Reset match timer at Start
        long lastLoopTime = System.currentTimeMillis();

        while (opModeIsActive()) {
            long currentTime = System.currentTimeMillis();
            double loopMs = currentTime - lastLoopTime;
            lastLoopTime = currentTime;

            // 1. Drivetrain Control
            drivetrain.drive(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x
            );

            // 2. Intake Control & State Tracker
            String intakeState;
            if (gamepad1.a) {
                intake.intake_On();
                intakeState = "INTAKING (FWD)";
            } else if (gamepad1.b) {
                intake.intake_Reverse();
                intakeState = "REVERSING (OUT)";
            } else {
                intake.intake_Off();
                intakeState = "OFF";
            }

            // 3. Match Timer & Endgame Alert (30s remaining)
            double timeRemaining = Math.max(0, 120.0 - getRuntime());
            if (timeRemaining <= 30.0 && !endgameRumbled) {
                gamepad1.rumble(500); // Alert driver via rumble
                endgameRumbled = true;
            }

            // 4. Telemetry Output - Driver Station
            telemetry.addLine("=== DRIVER DASHBOARD ===");
            telemetry.addData("Match Time Left", "%.1f s %s", timeRemaining, timeRemaining <= 30 ? "[ENDGAME]" : "");
            telemetry.addData("Intake Status", intakeState);
            telemetry.addData("Battery Voltage", "%.2f V", batterySensor.getVoltage());

            telemetry.addLine("\n=== SYSTEM HEALTH ===");
            telemetry.addData("Loop Time", "%.1f ms (%.0f Hz)", loopMs, 1000.0 / Math.max(1, loopMs));

            telemetry.update();
        }
    }
}
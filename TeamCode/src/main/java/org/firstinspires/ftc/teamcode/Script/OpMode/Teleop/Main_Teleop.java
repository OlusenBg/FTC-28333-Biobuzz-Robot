package org.firstinspires.ftc.teamcode.Script.OpMode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Script.SubSystem.Drivetrain;
import org.firstinspires.ftc.teamcode.Script.SubSystem.Intake;
import org.firstinspires.ftc.teamcode.Script.SubSystem.ROBOT;
import org.firstinspires.ftc.teamcode.Script.SubSystem.Shooter;

@TeleOp(name = "Main TeleOp", group = "TeleOp")

public class Main_Teleop extends LinearOpMode {
    private boolean endgameRumbled = false;
    private boolean shooterToggled = false;
    private boolean lastYState = false;
    private boolean lastXState = false;
    private boolean stopperToggled = false;

    @Override
    public void runOpMode() {
        ROBOT robot = new ROBOT();
        robot.init(hardwareMap);
        Drivetrain drivetrain = new Drivetrain(robot);
        Intake intake = new Intake(robot);
        Shooter shooter = new Shooter(robot);
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


            // 2. Stopper Control
            String stopperState = "OFF";
            if (gamepad1.x && !lastXState){
                stopperToggled = !stopperToggled;
                if (stopperToggled) {
                    robot.stopper.setPosition(1.0);
                    stopperState = "ON";
                } else {
                    robot.stopper.setPosition(0.0);
                    stopperState = "OFF";
                }
            }
            lastXState = gamepad1.x;


            // 3. Intake Control & State Tracker
            String intakeState;
            if (gamepad1.a) {
                intake.intake_On();
                intakeState = "INTAKING (IN)";
            } else if (gamepad1.b) {
                intake.intake_Reverse();
                intakeState = "REVERSING (OUT)";
            } else {
                intake.intake_Off();
                intakeState = "OFF";
            }


            // --- 4. Combined Shooter Control (R2 Variable + Y Toggle) ---

            // A. Handle Y Toggle detection
            if (gamepad1.y && !lastYState){
                shooterToggled = !shooterToggled;
            }
            lastYState = gamepad1.y;

            // B. Apply Motor Output Priority
            if (gamepad1.right_trigger > 0.1){
                // 1. Trigger priority when squeezed
                shooter.setShooterSpeed(gamepad1.right_trigger);
            } else if (shooterToggled) {
                // 2. Otherwise, if Y toggled is ON, run at full speed
                shooter.shooter_On();
            } else {
                // 3. Otherwise, Turn Shooter Off
                shooter.shooter_Off();
            }


            // 5. Match Timer & Endgame Alert (30s remaining)
            double timeRemaining = Math.max(0, 120.0 - getRuntime());
            if (timeRemaining <= 30.0 && !endgameRumbled) {
                gamepad1.rumble(500); // Alert driver via rumble
                endgameRumbled = true;
            }

            // 6. Telemetry Output - Driver Station
            telemetry.addLine("=== DRIVER DASHBOARD ===");
            telemetry.addData("Match Time Left", "%.1f s %s", timeRemaining, timeRemaining <= 30 ? "[ENDGAME]" : "");
            telemetry.addData("Intake Status", intakeState);
            telemetry.addData("Stopper Status", stopperState);
            telemetry.addData("Battery Voltage", "%.2f V", batterySensor.getVoltage());
            telemetry.addData("Shooter Velocity", "%.0f / %.0f (Ready: %b)",
                    shooter.getVelocity(), shooter.TARGET_VELOCITY, shooter.isReadyToShoot());

            telemetry.addLine("\n=== SYSTEM HEALTH ===");
            telemetry.addData("Loop Time", "%.1f ms (%.0f Hz)", loopMs, 1000.0 / Math.max(1, loopMs));

            telemetry.update();
        }
    }
}
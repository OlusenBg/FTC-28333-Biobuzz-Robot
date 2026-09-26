package org.firstinspires.ftc.teamcode.Script.SubSystem;

public class Intake {

    private final ROBOT robot;
    private final double INTAKE_SPEED = 1.0;

    // Constructor: Accepts your ROBOT instance so it can access robot.intake
    public Intake(ROBOT robot) {
        this.robot = robot;
    }

    // Turns Intake On (INTAKE_SPEED)
    public void intake_On() {
        robot.intake.setPower(INTAKE_SPEED);
    }

    // Turns Intake Off (0)
    public void intake_Off() {
        robot.intake.setPower(0);
    }
}

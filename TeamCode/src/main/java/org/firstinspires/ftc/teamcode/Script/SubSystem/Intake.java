package org.firstinspires.ftc.teamcode.Script.SubSystem;

public class Intake {

    private final ROBOT robot;
    private final double INTAKE_SPEED = 1.0;

    // Constructor: Accepts your ROBOT instance so it can access robot.intake
    public Intake(ROBOT robot) {
        this.robot = robot;
    }

    // Turns Intake On (INTAKE_SPEED)
    public void intake1_On() {
        robot.intake1.setPower(INTAKE_SPEED);
    }

    public void intake2_On(){
        robot.intake2.setPower(INTAKE_SPEED);
    }

    // Turns Intake Off (0)
    public void intake1_Off() {
        robot.intake1.setPower(0);
    }

    public void intake2_off(){
        robot.intake2.setPower(0);
    }

    public void intake_Reverse() {
        robot.intake1.setPower(-INTAKE_SPEED);
        robot.intake2.setPower(-INTAKE_SPEED);
    }


}
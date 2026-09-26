package org.firstinspires.ftc.teamcode.Script.SubSystem;

public class Drivetrain {

    private final ROBOT robot;

    // Constructor: Passes the ROBOT instance to access drive motors
    public Drivetrain(ROBOT robot) {
        this.robot = robot;
    }

    /**
     * Mecanum wheel kinematics.
     *
     * @param y  Forward / Backward motion (typically -gamepad1.left_stick_y)
     * @param x  Strafe Left / Right motion (typically gamepad1.left_stick_x)
     * @param rx Rotation / Turning motion (typically gamepad1.right_stick_x)
     */
    public void drive(double y, double x, double rx) {
        // Multiplier for strafe friction correction (Mecanum wheels require slightly more power to strafe)
        double strafeX = x * 1.1;

        // Calculate the power it needs to send to each motor
        double frontLeftPower  = y + strafeX + rx;
        double backLeftPower   = y - strafeX + rx;
        double frontRightPower = y - strafeX - rx;
        double backRightPower  = y + strafeX - rx;

        // Normalize powers so no motor exceeds 1.0 (maintains correct drive proportions)
        double maxPower = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(backLeftPower),
                          Math.max(Math.abs(frontRightPower), Math.abs(backRightPower))));

        if (maxPower > 1.0) {
            frontLeftPower  /= maxPower;
            backLeftPower   /= maxPower;
            frontRightPower /= maxPower;
            backRightPower  /= maxPower;
        }

        // Apply calculated powers to the motors defined in ROBOT.java
        robot.drive_FL.setPower(frontLeftPower);
        robot.drive_BL.setPower(backLeftPower);
        robot.drive_FR.setPower(frontRightPower);
        robot.drive_BR.setPower(backRightPower);
    }

    // Stops the robot by setting all motor powers to 0
    public void stop() {
        robot.drive_FL.setPower(0);
        robot.drive_BL.setPower(0);
        robot.drive_FR.setPower(0);
        robot.drive_BR.setPower(0);
    }
}
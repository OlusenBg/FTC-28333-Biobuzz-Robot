package org.firstinspires.ftc.teamcode.Script.SubSystem;

public class Stopper {
    private final ROBOT robot;
    double blocking = 0.9; //TODO replace this with the actual block position
    double notBlocking = 0.0; // TODO replace this with the actual not blocking position

    public Stopper (ROBOT robot) {this.robot = robot;}

    public void stopper_On() {
        robot.stopper.setPosition(blocking);
    }

    public void stopper_Off() {
        robot.stopper.setPosition(notBlocking);
    }


}

package org.firstinspires.ftc.teamcode.Script.SubSystem;

public class Adjustable_Hood {
    private final ROBOT robot;
    double POSITION_DOWN = 0.0;
    double POSITION_UP = 1.0;

    public Adjustable_Hood(ROBOT robot){this.robot = robot;}
    public void setPOSITION_UP(){
        robot.adjustable_Hood.setPosition(POSITION_UP);
    }
    public void setPOSITION_DOWN(){
        robot.adjustable_Hood.setPosition(POSITION_DOWN);
    }
}
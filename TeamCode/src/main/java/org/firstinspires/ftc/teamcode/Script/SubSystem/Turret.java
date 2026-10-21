package org.firstinspires.ftc.teamcode.Script.SubSystem;

public class Turret {
    private final ROBOT robot;
    double POSITION_START = 0.0;     // TODO replace this with the actual start position
    double POSITION_END = 1.0;       // TODO replace this with the actual end position

    public Turret(ROBOT robot) {this.robot = robot;}
    public void turret_Left(){
        robot.turret_1.setPosition(POSITION_START);
        robot.turret_2.setPosition(POSITION_START);
    }
    public void turret_Right(){
        robot.turret_1.setPosition(POSITION_END);
        robot.turret_2.setPosition(POSITION_END);
    }
}
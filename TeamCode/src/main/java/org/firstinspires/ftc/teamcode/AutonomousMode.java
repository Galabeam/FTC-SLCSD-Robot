package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutonomousMode")
public class AutonomousMode extends LinearOpMode {
    // Motors - Control Hub
    /*0*/   private DcMotor MotorName;
    
    // Activation
    @Override
    public void runOpMode() {
        // Motors - Control Hub
        /*0*/   MotorName = hardwareMap.get(DcMotor.class,"MotorName");

        // Initialization
        waitForStart();
        if (opModeIsActive()) {
            // Run

        }
    }
}
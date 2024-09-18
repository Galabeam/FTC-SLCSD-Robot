package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOpMode")
public class TeleOpMode extends LinearOpMode {
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
            while (opModeIsActive()) {
                // Loop
                telemetry.update();
            }
        }
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "TeleOpMode")
public class TeleOpMode extends LinearOpMode {
    // Motors - Control Hub
    /*0*/   private DcMotor TestMotor;

    // Activation
    @Override
    public void runOpMode() {
        // Motors - Control Hub
        /*0*/   TestMotor = hardwareMap.get(DcMotor.class,"TestMotor");

        // Initialization
        waitForStart();
        if (opModeIsActive()) {
            // Run
            while (opModeIsActive()) {
                // Loop

                telemetry.update();

                // Test Motor
                TestMotor.setPower(gamepad1.left_stick_y);
            }
        }
    }
}
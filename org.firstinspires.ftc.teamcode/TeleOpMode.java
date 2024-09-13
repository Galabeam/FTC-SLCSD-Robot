package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "TeleOpMode")
public class TeleOpMode extends LinearOpMode {
// Motors - Control Hub
/*0*/   private DcMotor MotorName;
// Servos - Control Hub
/*0*/   private Servo ServoName;

    // Activation
    @Override
    public void runOpMode() {
// Motors - Control Hub
/*0*/   MotorName = hardwareMap.get(DcMotor.class,"MotorName");
// Servos - Control Hub
/*0*/   ServoName = hardwareMap.get(Servo.class,"ServoName");

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
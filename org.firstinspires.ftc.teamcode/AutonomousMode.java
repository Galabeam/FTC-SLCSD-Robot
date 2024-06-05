package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutonomousMode")
public class AutonomousMode extends LinearOpMode {
// Motors - Control Hub
/*0*/   private DcMotor LeftFront;
/*1*/   private DcMotor RightFront;
/*2*/   private DcMotor LeftBack;
/*3*/   private DcMotor RightBack;
// Motors - Expansion Hub
/*0*/   private DcMotor PixelFlipper;

        // Hold
        private void Hold(int Seconds) {
            sleep(Seconds * 1000);
        }

        // Wheels
        private void Wheels(double Axial, double Lateral, double Yaw) {
            double WheelPowerMax;
                
            // Axial = left_stick_y / move forward-backward
            // Lateral = left_stick_x / move left-right
            // Yaw = right_stick_x / turn left-right
                
            double LeftFrontPower   = Axial + Lateral + Yaw;
            double RightFrontPower  = Axial - Lateral - Yaw;
            double LeftBackPower    = Axial - Lateral + Yaw;
            double RightBackPower   = Axial + Lateral - Yaw;
                
            WheelPowerMax = Math.max(Math.abs(LeftFrontPower), Math.abs(RightFrontPower));
            WheelPowerMax = Math.max(WheelPowerMax, Math.abs(LeftBackPower));
            WheelPowerMax = Math.max(WheelPowerMax, Math.abs(RightBackPower));
                
            if (WheelPowerMax > 1.0) {
                LeftFrontPower  /= WheelPowerMax;
                RightFrontPower /= WheelPowerMax;
                LeftBackPower   /= WheelPowerMax;
                RightBackPower  /= WheelPowerMax;
            }
            LeftFront.setPower(LeftFrontPower);
            RightFront.setPower(RightFrontPower);
            LeftBack.setPower(LeftBackPower);
            RightBack.setPower(RightBackPower);
        }

    // Activation
    @Override
    public void runOpMode() {
// Motors - Control Hub
/*0*/   LeftFront   = hardwareMap.get(DcMotor.class,"LeftFront");
/*1*/   RightFront  = hardwareMap.get(DcMotor.class,"RightFront");
/*2*/   LeftBack    = hardwareMap.get(DcMotor.class,"LeftBack");
/*3*/   RightBack   = hardwareMap.get(DcMotor.class,"RightBack");
// Motors - Expansion Hub
/*0*/   PixelFlipper = hardwareMap.get(DcMotor.class,"PixelFlipper");
        // Hardware properties
        LeftFront.setDirection(DcMotor.Direction.REVERSE);
        RightFront.setDirection(DcMotor.Direction.FORWARD);
        LeftBack.setDirection(DcMotor.Direction.REVERSE);
        RightBack.setDirection(DcMotor.Direction.FORWARD);
        PixelFlipper.setDirection(DcMotor.Direction.FORWARD);

        // Initialization Code
        waitForStart();
        if (opModeIsActive()) {
            // Run Code

            // Axial = left_stick_y / move forward-backward
            // Lateral = left_stick_x / move left-right
            // Yaw = right_stick_x / turn left-right
            telemetry.update();
            Wheels(1,0,0);
            Hold(2);
            Wheels(0,0,0);
        }
    }
}
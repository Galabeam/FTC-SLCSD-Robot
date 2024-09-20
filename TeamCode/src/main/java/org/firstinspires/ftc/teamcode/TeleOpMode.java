package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "TeleOpMode")
public class TeleOpMode extends LinearOpMode {
    // Motors - Control Hub
    /*0*/   private DcMotor LeftFront;
    /*1*/   private DcMotor RightFront;
    /*2*/   private DcMotor LeftBack;
    /*3*/   private DcMotor RightBack;
    // Digital Devices - Control Hub
    /*0*/   private LED LED;

    // Activation
    @Override
    public void runOpMode() {
        // Motors - Control Hub
        /*0*/   LeftFront = hardwareMap.get(DcMotor.class,"LeftFront");
        /*1*/   RightFront = hardwareMap.get(DcMotor.class,"RightFront");
        /*2*/   LeftBack = hardwareMap.get(DcMotor.class,"LeftBack");
        /*3*/   RightBack = hardwareMap.get(DcMotor.class,"RightBack");
        // Digital Devices - Control Hub
        /*0*/   LED = hardwareMap.get(LED.class,"LED");

        // Hardware properties
        LeftFront.setDirection(DcMotor.Direction.REVERSE);
        RightFront.setDirection(DcMotor.Direction.FORWARD);
        LeftBack.setDirection(DcMotor.Direction.REVERSE);
        RightBack.setDirection(DcMotor.Direction.FORWARD);

        // Initialization
        waitForStart();
        if (opModeIsActive()) {
            // Run
            while (opModeIsActive()) {
                // Loop

                telemetry.update();

                // LED
                LED.enable(true);

                // Wheels
                double WheelPowerMax;

                double Axial    =-gamepad1.left_stick_y;
                double Lateral  = gamepad1.left_stick_x;
                double Yaw      = gamepad1.right_stick_x;

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
        }
    }
}
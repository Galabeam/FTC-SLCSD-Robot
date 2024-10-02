package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "TeleOpMode")
public class TeleOpMode extends LinearOpMode {
    // Motors - Control Hub
    /*0*/   private DcMotor LeftFront;
    /*1*/   private DcMotor RightFront;
    /*2*/   private DcMotor LeftBack;
    /*3*/   private DcMotor RightBack;
    // Motors - Expansion Hub
    /*0*/   private DcMotor LifterBelt;

    //                                                                                                                                                      
    @Override
    public void runOpMode() {
        // Motors - Control Hub
        /*0*/   LeftFront = hardwareMap.get(DcMotor.class,"LeftFront");
        /*1*/   RightFront = hardwareMap.get(DcMotor.class,"RightFront");
        /*2*/   LeftBack = hardwareMap.get(DcMotor.class,"LeftBack");
        /*3*/   RightBack = hardwareMap.get(DcMotor.class,"RightBack");
        // Motors - Expansion Hub
        /*0*/   LifterBelt = hardwareMap.get(DcMotor.class,"LifterBelt");

        // Hardware properties
        LeftFront.setDirection(DcMotor.Direction.REVERSE);
        LeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightFront.setDirection(DcMotor.Direction.FORWARD);
        RightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftBack.setDirection(DcMotor.Direction.REVERSE);
        LeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightBack.setDirection(DcMotor.Direction.FORWARD);
        RightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LifterBelt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status","I didn't blow up");
        telemetry.update();

        // Initialization
        waitForStart();
        if (opModeIsActive()) {
            // Run
            while (opModeIsActive()) {
                // Loop

                telemetry.addData("Status","I didn't blow up part 2");
                telemetry.update();

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

                // Lifter Belt & Spinner
                LifterBelt.setPower(((gamepad1.left_bumper ? 1 : 0) + ((gamepad1.right_bumper ? 1 : 0) * -1)) * 0.4);
            }
        }
    }
}
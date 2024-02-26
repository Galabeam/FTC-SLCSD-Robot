package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp")
public class TeleOpMode extends LinearOpMode {
// Motors
/*0*/   private DcMotor LeftWheel;
/*1*/   private DcMotor RightWheel;
/*2*/   private DcMotor ConeFlipper;
// Servos
/*0*/   private Servo BlueClaw;

    // Activation
    @Override
    public void runOpMode() {
// Motors
/*0*/   LeftWheel = hardwareMap.get(DcMotor.class,"LeftWheel");
/*1*/   RightWheel =hardwareMap.get(DcMotor.class,"RightWheel");
/*2*/   ConeFlipper=hardwareMap.get(DcMotor.class,"ConeFlipper");
// Servos
/*0*/   BlueClaw =  hardwareMap.get(Servo.class,"BlueClaw");
        // Hardware properties
        LeftWheel.setDirection(DcMotor.Direction.FORWARD);
        RightWheel.setDirection(DcMotor.Direction.FORWARD);
        
        // Initialization
        waitForStart();
        if (opModeIsActive()) {
            // Run
            while (opModeIsActive()) {
                // Loop
                telemetry.update();
                // Variables
                boolean Debug = false;
                if (gamepad1.guide) {
                    Debug = !Debug;
                }

                // Rear Wheels
                float LeftWheelPower = gamepad1.left_stick_x + (gamepad1.left_stick_y * -1);
                float RightWheelPower = gamepad1.left_stick_x + gamepad1.left_stick_y;
                float WheelPowerMax = Math.max(Math.abs(LeftWheelPower),Math.abs(RightWheelPower));
                if (WheelPowerMax > 1.0) {
                    LeftWheelPower /= WheelPowerMax;
                    RightWheelPower /= WheelPowerMax;
                }
                LeftWheel.setPower(LeftWheelPower);
                RightWheel.setPower(RightWheelPower);

                /* Sideways Wheel
                if (gamepad1.right_stick_x != 0) {
                    SidewaysWheel.setPower(gamepad1.right_stick_x);
                } else if (SidewaysWheel.getPower() != 0) {
                    SidewaysWheel.setPower(0);
                }
                */

                // Cone Flipper
                ConeFlipper.setPower(gamepad1.right_stick_y);

                // Blue Claw
                if (gamepad1.x) {
                    BlueClaw.setPosition(0.5);
                } else if (BlueClaw.getPosition() != 0) {
                    BlueClaw.setPosition(0);
                }
                // Debug
                if (Debug) {
                    // Wheel Power
                    telemetry.addLine("Wheel Power - Debug");
                    telemetry.addData("- Wheel Power Max","%",WheelPowerMax);
                    telemetry.addData("- Left Wheel Power","%",LeftWheelPower);
                    telemetry.addData("- Right Wheel Power","%",RightWheelPower);
                }
            }
        }
    }
}
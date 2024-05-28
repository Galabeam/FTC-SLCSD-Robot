package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp")
public class TeleOpMode extends LinearOpMode {
// Motors - Control Hub
/*0*/   private DcMotor LeftFront;
/*1*/   private DcMotor RightFront;
/*2*/   private DcMotor LeftBack;
/*3*/   private DcMotor RightBack;
// Servos - Control Hub
/*4*/   private Servo PixelClaw_R;
/*5*/   private Servo PixelClaw_L;
// Motors - Expansion Hub
/*0*/   private DcMotor PixelFlipper;

    // Activation
    @Override
    public void runOpMode() {
// Motors - Control Hub
/*0*/   LeftFront   = hardwareMap.get(DcMotor.class,"LeftFront");
/*1*/   RightFront  = hardwareMap.get(DcMotor.class,"RightFront");
/*2*/   LeftBack    = hardwareMap.get(DcMotor.class,"LeftBack");
/*3*/   RightBack   = hardwareMap.get(DcMotor.class,"RightBack");
// Servos - Control Hub
/*4*/   PixelClaw_R = hardwareMap.get(Servo.class,"PixelClaw_R");
/*5*/   PixelClaw_L = hardwareMap.get(Servo.class,"PixelClaw_L");
// Motors - Expansion Hub
/*0*/   PixelFlipper = hardwareMap.get(DcMotor.class,"PixelFlipper");
        // Hardware properties
        LeftFront.setDirection(DcMotor.Direction.REVERSE);
        RightFront.setDirection(DcMotor.Direction.FORWARD);
        LeftBack.setDirection(DcMotor.Direction.REVERSE);
        RightBack.setDirection(DcMotor.Direction.FORWARD);
        PixelFlipper.setDirection(DcMotor.Direction.FORWARD);
        
        // Initialization
        waitForStart();
        if (opModeIsActive()) {
            // Run
            while (opModeIsActive()) {
                // Loop
                telemetry.update();
                // Variables5
                boolean Debug = false;
                if (gamepad1.guide) {
                    Debug = !Debug;
                    while (gamepad1.guide) {
                        sleep(1000);
                    }
                }

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

                // Pixel Flipper
                PixelFlipper.setPower(Range.clip(gamepad1.right_trigger + gamepad1.left_trigger -1.0, 1.0));
                
                // Pixel Claw
                if (gamepad1.dpad_left) {
                    // outward
                    PixelClaw_L.setPosition(0.9);
                    PixelClaw_R.setPosition(0.6);
                } else if (gamepad1.dpad_right) {
                    // inward
                    PixelClaw_L.setPosition(0.775);
                    PixelClaw_R.setPosition(0.75);
                } 
                
                // Debug
                if (Debug) {
                    // Wheel Power
                    telemetry.addLine("Wheel Power - Debug");
                    telemetry.addData("- Max: ","%.1f",WheelPowerMax);
                    telemetry.addData("> LF: ","%.1f",LeftFrontPower);
                    telemetry.addData("> RF: ","%.1f",RightFrontPower);
                    telemetry.addData("> LB: ","%.1f",LeftBackPower);
                    telemetry.addData("> RB: ","%.1f",RightBackPower);
                }
            }
        }
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp")
public class TeleOpMode extends LinearOpMode {
// Motors
/*1*/   private DcMotor LeftWheel;
/*2*/   private DcMotor RightWheel;
// Built-in
        private VoltageSensor VoltageSensor;
        private double VoltageSensorThreshold = 15;

    // Activation
    @Override
    public void runOpMode() {
// Motors
/*1*/   LeftWheel =     hardwareMap.get(DcMotor.class,"LeftWheel");
/*2*/   RightWheel =    hardwareMap.get(DcMotor.class,"RightWheel");
// Built-in
        VoltageSensor = hardwareMap.voltageSensor.iterator().next();
        // Hardware properties
        HangerBottom.setDirection(DcMotor.Direction.REVERSE);
        
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
                float LeftWheelPower = gamepad1.left_stick_x + gamepad1.left_stick_y;
                float RightWheelPower = gamepad1.left_stick_x + (gamepad1.left_stick_y * -1);
                float WheelPowerMax = Math.max(Math.abs(LeftWheelPower),Math.abs(RightWheelPower));
                if (WheelPowerMax > 1.0) {
                    LeftWheelPower /= WheelPowerMax;
                    RightWheelPower /= WheelPowerMax;
                }
                LeftWheel.setPower(LeftWheelPower);
                RightWheel.setPower(RightWheelPower);

                // Sideways Wheel
                if (gamepad1.right_stick_x > 0) {
                    SidewaysWheel.setPower(gamepad1.right_stick_x);
                } else if (SidewaysWheel.getPower() != 0) {
                    SidewaysWheel.setPower(0);
                }

                // Debug
                if (Debug) {
                    double Voltage = VoltageSensor.getVoltage();
                    // Battery Percentage
                    telemetry.addLine("Battery");
                    telemetry.addData("- Percentage","%",((Voltage/15)*100));
                    telemetry.addData("- Voltage","%",Voltage);
                    // Wheel Power
                    telemetry.addLine("Wheel Power - Debug");
                    telemetry.addData("- Left Wheel Power","%",LeftWheelPower);
                    telemetry.addData("- Right Wheel Power","%",RightWheelPower);
                }
            }
        }
    }
}
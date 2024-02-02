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
/*3*/   private DcMotor HangerTop;
/*4*/   private DcMotor HangerBottom;
// Servos
/*1*/   private Servo DroneLauncher;
/*2*/   private Servo RampDeployer;
// USBs  
/*2.0*/ private WebcamName Camera;
// Built-in
        private VoltageSensor VoltageSensor;
        private double VoltageSensorThreshold = 15;

    // Activation
    @Override
    public void runOpMode() {
// Motors
/*1*/   LeftWheel =     hardwareMap.get(DcMotor.class,"LeftWheel");
/*2*/   RightWheel =    hardwareMap.get(DcMotor.class,"RightWheel");
/*3*/   HangerTop =     hardwareMap.get(DcMotor.class,"HangerTop");
/*4*/   HangerBottom =  hardwareMap.get(DcMotor.class,"HangerBottom");
// Servos
/*1*/   DroneLauncher = hardwareMap.get(Servo.class,"DroneLauncher");
/*2*/   RampDeployer =  hardwareMap.get(Servo.class,"RampDeployer");
// USBs  
/*2.0*/ Camera =        hardwareMap.get(WebcamName.class,"Camera");
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
                // Powers
                int HangerPower = 1;

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

                /* Sideways Wheel - R.I.P., my only contribution besides programming to this robot
                if (gamepad1.right_stick_x > 0) {
                    SidewaysWheel.setPower(gamepad1.right_stick_x);
                } else if (SidewaysWheel.getPower() != 0) {
                    SidewaysWheel.setPower(0);
                }*/

                // Hanger
                if (gamepad1.y) {
                    HangerTop.setPower(HangerPower * -1);
                    HangerBottom.setPower(HangerPower * -1);
                } else if (gamepad1.a) {
                    HangerTop.setPower(HangerPower);
                    HangerBottom.setPower(HangerPower);
                } else if (HangerTop.getPower() != 0 && HangerBottom.getPower() != 0) {
                    HangerTop.setPower(0);
                    HangerBottom.setPower(0);
                }

                // Airplane Launcher
                if (gamepad1.x) {
                    DroneLauncher.setPosition(0.5);
                } else if (DroneLauncher.getPosition() != 0) {
                    DroneLauncher.setPosition(0);
                }

                // Ramp Deployer
                if (gamepad1.b) {
                    RampDeployer.setPosition(0.5);
                } else if (RampDeployer.getPosition() != 0) {
                    RampDeployer.setPosition(0);
                }

                /* Wrong Buttons
                if(
                       !gamepad1.a
                    || !gamepad1.y
                    || !gamepad1.b
                    || !gamepad1.x
                    || !gamepad1.left_stick
                    || !gamepad1.right_stick) {
                    gamepad1.rumble(0.8, 0.8, 200);
                }*/

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
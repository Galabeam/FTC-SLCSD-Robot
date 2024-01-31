package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp")
public class TeleOpMode extends LinearOpMode {
    // Control Hub
    private DcMotor LeftWheel;
    private DcMotor RightWheel;
    private DcMotor SidewaysWheel;
    private DcMotor HangerPulleyTop;
    private Servo AirplaneLauncher;
    private Servo RampDeployer;
    // Expansion Hub
    private DcMotor HangerPulleyBottom;

    // Activation
    @Override
    public void runOpMode() {
        // Control Hub
        LeftWheel = hardwareMap.get(DcMotor.class, "LeftWheel");
        RightWheel = hardwareMap.get(DcMotor.class, "RightWheel");
        HangerPulleyTop = hardwareMap.get(DcMotor.class, "HangerPulleyTop");
        HangerPulleyBottom = hardwareMap.get(DcMotor.class, "HangerPulleyBottom");
        AirplaneLauncher = hardwareMap.get(Servo.class, "AirplaneLauncher");
        RampDeployer = hardwareMap.get(Servo.class, "RampDeployer");
        // Expansion Hub
        HangerPulleyBottom.setDirection(DcMotor.Direction.REVERSE);

        // Initialization
        waitForStart();
        if (opModeIsActive()) {
            // Run
            while (opModeIsActive()) {
                // Loop
                telemetry.update();
				// Powers
				int HangerPulleyPower = 1;

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
				telemetry.addLine("Wheel Power");
				telemetry.addData("- Left Wheel Power","%",LeftWheelPower);
				telemetry.addData("- Right Wheel Power","%",RightWheelPower);

                /* Sideways Wheel - R.I.P., my only invention
                if (gamepad1.right_stick_x > 0) {
                    SidewaysWheel.setPower(gamepad1.right_stick_x);
                }*/

                // Hanger Pulley
                if (gamepad1.y) {
                    HangerPulleyTop.setPower(HangerPulleyPower);
                    HangerPulleyBottom.setPower(HangerPulleyPower);
                } else if (gamepad1.a) {
                    HangerPulleyTop.setPower(HangerPulleyPower * -1);
                    HangerPulleyBottom.setPower(HangerPulleyPower * -1);
                } else if (HangerPulleyTop.getPower() != 0 && HangerPulleyBottom.getPower() != 0) {
                    HangerPulleyTop.setPower(0);
                    HangerPulleyBottom.setPower(0);
                }

                // Airplane Launcher
                if (gamepad1.x) {
                    AirplaneLauncher.setPosition(0.5);
                } else if (AirplaneLauncher.getPosition() != 0) {
                    AirplaneLauncher.setPosition(0);
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
            }
        }
    }
}
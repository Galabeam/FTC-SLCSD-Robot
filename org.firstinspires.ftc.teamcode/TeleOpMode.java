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

	// On OpMode Activation
	@Override
	public void runOpMode() {
		// Control Hub
		LeftWheel = hardwareMap.get(DcMotor.class, "LeftWheel");
		RightWheel = hardwareMap.get(DcMotor.class, "RightWheel");
		SidewaysWheel = hardwareMap.get(DcMotor.class, "SidewaysWheel");
		HangerPulleyTop = hardwareMap.get(DcMotor.class, "HangerPulleyTop");
		AirplaneLauncher = hardwareMap.get(Servo.class, "AirplaneLauncher");
		RampDeployer = hardwareMap.get(Servo.class, "RampDeployer");
		// Expansion Hub
		HangerPulleyBottom = hardwareMap.get(DcMotor.class, "HangerPulleyBottom");

		LeftWheel.setDirection(DcMotor.Direction.REVERSE);

		// Initialization Code
		waitForStart();
		if (opModeIsActive()) {
			// Run Code
			while (opModeIsActive()) {
				// Loop Code
				telemetry.update();

				// Rear Wheels
				// Left/Right
				if (gamepad1.left_stick_x > 0) {
					LeftWheel.setPower(gamepad1.left_stick_x);
					RightWheel.setPower(gamepad1.left_stick_x);
				}
				// Forward/Backward
				if (gamepad1.left_stick_y > 0) {
					LeftWheel.setPower(gamepad1.left_stick_y);
					RightWheel.setPower(gamepad1.left_stick_y);
				}

				// Sideways Wheel
				if (gamepad1.right_stick_x > 0) {
					SidewaysWheel.setPower(gamepad1.right_stick_x);
				}

				// Hanger Pulley
				if (gamepad1.y) {
					HangerPulleyTop.setPower(0.1);
				} else if (gamepad1.a) {
					HangerPulleyBottom.setPower(0.1);
				}

				// Airplane Launcher
				if (gamepad1.x) {
					AirplaneLauncher.setPosition(0.5);
				} else if (AirplaneLauncher.getPosition() != 0) {
					AirplaneLauncher.setPosition(0);
				}

				// Ramp Deployer
				if (gamepad1.b) {
					AirplaneLauncher.setPosition(0.5);
				} else if (AirplaneLauncher.getPosition() != 0) {
					AirplaneLauncher.setPosition(0);
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
				}/*
			}
		}
	}
}
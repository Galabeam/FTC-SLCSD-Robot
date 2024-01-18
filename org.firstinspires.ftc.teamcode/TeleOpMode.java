package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.ftccommon.SoundPlayer;

@TeleOp(name = "TeleOp")
public class TeleOpMode extends LinearOpMode {

  private AndroidSoundPool androidSoundPool;

  private DcMotor LeftWheel;
  private DcMotor RightWheel;
  private DcMotor SidewaysWheel;
  private DcMotor BroomSpinner;
  private DcMotor PixelScooper;
  private DcMotor ConveyorBelt;
  private Servo AirplaneLauncher;

  // On OpMode Activation
  @Override
  public void runOpMode() {

    androidSoundPool = new AndroidSoundPool();

    LeftWheel = hardwareMap.get(DcMotor.class, "LeftWheel");
    RightWheel = hardwareMap.get(DcMotor.class, "RightWheel");
    SidewaysWheel = hardwareMap.get(DcMotor.class, "SidewaysWheel");
    BroomSpinner = hardwareMap.get(DcMotor.class, "BroomSpinner");
    PixelScooper = hardwareMap.get(DcMotor.class, "PixelScooper");
    ConveyorBelt = hardwareMap.get(DcMotor.class, "ConveyorBelt");
    AirplaneLauncher = hardwareMap.get(Servo.class, "AirplaneLauncher");

    // Initialization Code
    waitForStart();
    androidSoundPool.initialize(SoundPlayer.getInstance());
    if (opModeIsActive()) {
      // Run Code
      while (opModeIsActive()) {
        // Loop Code
        telemetry.update();
        // Rear Wheels Forward/Backward
        LeftWheel.setPower(gamepad1.left_stick_y * -1);
        RightWheel.setPower(gamepad1.left_stick_y);
        // Rear Wheels Left/Right
        LeftWheel.setPower(gamepad1.left_stick_x);
        RightWheel.setPower(gamepad1.left_stick_x);
        // Sideways Wheel
        SidewaysWheel.setPower(gamepad1.right_stick_x);
        // Pixel Scooper
        PixelScooper.setPower(gamepad1.right_stick_y * -1);
        // Broom Spinner
        if (gamepad1.b) {
          BroomSpinner.setPower(0.3);
        } else if (gamepad1.x) {
          BroomSpinner.setPower(-0.3);
        } else if (BroomSpinner.getPower() != 0) {
          BroomSpinner.setPower(0);
        }
        // Conveyor Belt
        if (gamepad1.y) {
          ConveyorBelt.setPower(-0.75);
        } else if (ConveyorBelt.getPower() != 0) {
          ConveyorBelt.setPower(0);
        }
        // Airplane Launcher
        if (gamepad1.a) {
          AirplaneLauncher.setPosition(0.5);
        } else if (AirplaneLauncher.getPosition() != 0) {
          AirplaneLauncher.setPosition(0);
        }
        /* Wrong Buttons
        if (gamepad1.left_stick_button || gamepad1.right_stick_button || gamepad1.guide || gamepad1.start || gamepad1.back) {
          if (!SoundPlayer.CurrentlyPlaying) {
            androidSoundPool.play("RawRes:ss_alarm");
          }
        }*/
      }
    }
    
    androidSoundPool.close();
    
  }
}
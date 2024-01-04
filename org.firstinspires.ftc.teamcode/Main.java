package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.ftccommon.SoundPlayer;
import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;

@TeleOp(name = "Main")
public class Main extends LinearOpMode {

  //private DcMotor SidewaysWheel;
  private DcMotor LeftWheel;
  private DcMotor RightWheel;
  private DcMotor BroomSpinner;
  private DcMotor PixelScooper;
  private DcMotor ConveyorBelt;
  private Servo AirplaneLauncher;
  private Servo RampEngager;
  
  private AndroidSoundPool androidSoundPool;

  // On OpMode Activation
  @Override
  public void runOpMode() {
    //SidewaysWheel = hardwareMap.get(DcMotor.class, "SidewaysWheel");
    LeftWheel = hardwareMap.get(DcMotor.class, "LeftWheel");
    RightWheel = hardwareMap.get(DcMotor.class, "RightWheel");
    BroomSpinner = hardwareMap.get(DcMotor.class, "BroomSpinner");
    PixelScooper = hardwareMap.get(DcMotor.class, "PixelScooper");
    ConveyorBelt = hardwareMap.get(DcMotor.class, "ConveyorBelt");
    AirplaneLauncher = hardwareMap.get(Servo.class, "AirplaneLauncher");
    RampEngager = hardwareMap.get(Servo.class, "RampEngager");
    
    androidSoundPool = new AndroidSoundPool();

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
        // Pixel Scooper
        PixelScooper.setPower(gamepad1.right_stick_y * -1);
        // Broom Spinner
        if (gamepad1.b) {
          BroomSpinner.setPower(0.3);
        } else if (gamepad1.x) {
          BroomSpinner.setPower(-0.3);
        } else {
          BroomSpinner.setPower(0);
        }
        // Conveyor Belt
        if (gamepad1.y) {
          ConveyorBelt.setPower(-6);
        } else {
          ConveyorBelt.setPower(0);
        }
        // Airplane Launcher
        if (gamepad1.a) {
          AirplaneLauncher.setPosition(0.5);
        } else {
          AirplaneLauncher.setPosition(0);
        }
        // Ramp Engager
        boolean RampEngaged = false;
        if (gamepad1.dpad_left) {
          if (RampEngaged == false) {
            RampEngaged = true;
            RampEngager.setPosition(0.5);
          } else if (RampEngaged == true) {
            RampEngaged = false;
            RampEngager.setPosition(0);
          }
        }
        // Wrong Buttons
        if (gamepad1.left_stick_button || gamepad1.right_stick_button) {
          androidSoundPool.play("RawRes:ss_alarm");
        }
      }
    }
    
    androidSoundPool.close();
  }
}
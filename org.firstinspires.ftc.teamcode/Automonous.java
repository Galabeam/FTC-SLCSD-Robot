package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.ftccommon.SoundPlayer;

@Autonomous(name = "Automonous")
public class Automonous extends LinearOpMode {

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

    // Hold
    private void Hold(int Seconds) {
      sleep(Seconds * 1000)
    }

    // Rear Wheels
    private void Rear(float Power, String Direction) {
      if (Direction == "t") {
        LeftWheel.setPower(Power);
        RightWheel.setPower(Power);
      } else if (Direction == "v") {
        LeftWheel.setPower(Power);
        RightWheel.setPower(Power * -1);
      }
    }
    // Sideways Wheel
    private void Sideways(int Power) {
      SidewaysWheel.setPower(-1 * Power);
    }
    // Broom Spinner
    private void Broom(String Direction) {
      if (Direction == "f") {
        BroomSpinner.setPower(0.3);
      } else if (Direction == "b") {
        BroomSpinner.setPower(-0.3);
      }
    }
    // Conveyor Belt
    private void Belt() {
      ConveyorBelt.setPower(-0.75);
    }

    // Initialization Code
    waitForStart();
    if (opModeIsActive()) {
      // Run Code
      telemetry.update();
      Rear(-1,v);
      Hold(1.2);

      Rear(1,t);
      Hold(0.64);

      Rear(-1,v);
      Hold(1.7);
      
      Belt()
      Hold(5);
    }
  }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Automonous")
public class Automonous extends LinearOpMode {

  private DcMotor LeftWheel;
  private DcMotor RightWheel;
  //private DcMotor SidewaysWheel;
  private DcMotor BroomSpinner;
  private DcMotor PixelScooper;
  private DcMotor ConveyorBelt;
  private Servo AirplaneLauncher;
  
  private AndroidSoundPool androidSoundPool;

  // On OpMode Activation
  @Override
  public void runOpMode() {
    LeftWheel = hardwareMap.get(DcMotor.class, "LeftWheel");
    RightWheel = hardwareMap.get(DcMotor.class, "RightWheel");
    //SidewaysWheel = hardwareMap.get(DcMotor.class, "SidewaysWheel");
    BroomSpinner = hardwareMap.get(DcMotor.class, "BroomSpinner");
    PixelScooper = hardwareMap.get(DcMotor.class, "PixelScooper");
    ConveyorBelt = hardwareMap.get(DcMotor.class, "ConveyorBelt");
    AirplaneLauncher = hardwareMap.get(Servo.class, "AirplaneLauncher");

    // Initialization Code
    waitForStart();
    if (opModeIsActive()) {
      // Run Code
      telemetry.update();
      // Backward 0.8s
      Rear_ForwardBackward(-1);
      sleep(1200);
      // Turn Left 90deg
      Rear_LeftRight(1);
      sleep(640);
      // Forward 0.8s
      Rear_ForwardBackward(-1);
      sleep(1700);
      ConveyorBelt.setPower(-0.75);
      sleep(5000);
    }
  }

  /* Sideways Wheel - Left/Right
  private void Sideways_LeftRight(int Power) {
    SidewaysWheel.setPower(-1 * Power);
  }*/

  // Rear Wheels - Forward/Backward
  private void Rear_ForwardBackward(float Power) {
    LeftWheel.setPower(Power);
    RightWheel.setPower(Power * -1);
  }

  // Rear Wheels - Left/Right
  private void Rear_LeftRight(float Power) {
    LeftWheel.setPower(Power);
    RightWheel.setPower(Power);
  }

  // Broom Spinner - Forward
  private void BroomSpinner_Forward() {
    BroomSpinner.setPower(0.3);
  }

  // Conveyor Belt - Forward
  private void ConveyorBelt_Forward() {
    ConveyorBelt.setPower(0.5);
  }
}
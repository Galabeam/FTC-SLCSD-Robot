package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Automonous")
public class Automonous extends LinearOpMode {

  private DcMotor SidewaysWheel;
  private DcMotor LeftWheel;
  private DcMotor RightWheel;
  private DcMotor BroomSpinner;
  private DcMotor ConveyorBelt;

  // On OpMode Activation
  @Override
  public void runOpMode() {
    SidewaysWheel = hardwareMap.get(DcMotor.class, "SidewaysWheel");
    LeftWheel = hardwareMap.get(DcMotor.class, "LeftWheel");
    RightWheel = hardwareMap.get(DcMotor.class, "RightWheel");
    BroomSpinner = hardwareMap.get(DcMotor.class, "BroomSpinner");
    ConveyorBelt = hardwareMap.get(DcMotor.class, "ConveyorBelt");

    // Initialization Code
    waitForStart();
    if (opModeIsActive()) {
      // Run Code
      telemetry.update();
      // Forward 1.8s
      Rear_ForwardBackward(1);
      sleep(1800);
      Rear_ForwardBackward(0);
      sleep(500);
      // SidewaysLeft 1.5s
      Sideways_LeftRight(1);
      sleep(1500);
      Sideways_LeftRight(0);
      sleep(500);
      // Forward 1s
      Rear_ForwardBackward(1);
      sleep(1);
      Rear_ForwardBackward(0);
    }
  }

  // Sideways Wheel - Left/Right
  private void Sideways_LeftRight(int Power) {
    SidewaysWheel.setPower(-1 * Power);
  }

  // Rear Wheels - Forward/Backward
  private void Rear_ForwardBackward(float Power) {
    LeftWheel.setPower(Power * -1);
    RightWheel.setPower(Power);
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

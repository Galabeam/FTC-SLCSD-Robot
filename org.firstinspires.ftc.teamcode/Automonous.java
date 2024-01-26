package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Autonomous(name = "Autonomous")
public class Automonous extends LinearOpMode {

    private static final boolean USE_WEBCAM = true; // true for webcam, false for phone camera
    private static final int DESIRED_TAG_ID = -1; // Choose the tag you want to approach or set to -1 for ANY tag.
    private static final String TFOD_MODEL_ASSET = "CenterStage.tflite"; // only used for Android Studio
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/CenterStage.tflite";
    private static final String[] LABELS = { // labels recognized in model for TFOD (must be in training order!)
        "Pixel",
    };

    private VisionPortal visionPortal;
    private TfodProcessor tfod;
    private AprilTagProcessor aprilTag;
    private AprilTagDetection desiredTag = null; // Used to hold the data for a detected AprilTag

    // How close camera should get to target (inches)
    final double DESIRED_DISTANCE = 6.0;
    // Set the GAIN constants to control the relationship between the measured
    // position error, and how much power is
    // applied to the drive motors to correct the error.
    // Drive = Error * Gain Make these values smaller for smoother control, or
    // larger for a more aggressive response.
    final double SPEED_GAIN = 0.05; // Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.
                                    // (0.50 / 25.0)
    final double TURN_GAIN = 0.01; // Turn Control "Gain". eg: Ramp up to 25% power at a 25 degree error. (0.25 /
                                   // 25.0)

    final double MAX_AUTO_SPEED = 1; // Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_TURN = 1]
    ; // Clip the turn speed to this max value (adjust for your robot)

    private DcMotor LeftWheel; // leftback_drive
    private DcMotor RightWheel; // rightback_drive
    private DcMotor SidewaysWheel; // middle_drive
    private DcMotor BroomSpinner;
    private DcMotor PixelScooper;
    private DcMotor ConveyorBelt;
    private Servo AirplaneLauncher;
    private WebcamName FrontCamera; // Mister
    private WebcamName BackCamera; // Missus

    @Override
    public void runOpMode() {
        boolean targetFound = false; // Set to true when an AprilTag target is detected
        double drive = 0; // Desired forward power/speed (-1 to +1)
        double turn = 0; // Desired turning power/speed (-1 to +1)
        // Hardware
        LeftWheel = hardwareMap.get(DcMotor.class, "LeftWheel");
        RightWheel = hardwareMap.get(DcMotor.class, "RightWheel");
        SidewaysWheel = hardwareMap.get(DcMotor.class, "SidewaysWheel");
        BroomSpinner = hardwareMap.get(DcMotor.class, "BroomSpinner");
        PixelScooper = hardwareMap.get(DcMotor.class, "PixelScooper");
        ConveyorBelt = hardwareMap.get(DcMotor.class, "ConveyorBelt");
        AirplaneLauncher = hardwareMap.get(Servo.class, "AirplaneLauncher");
        FrontCamera = hardwareMap.get(WebcamName.class, "FrontCamera");
        BackCamera = hardwareMap.get(WebcamName.class, "BackCamera");
        // VisionPortal Initiation (TFOD/AprilTag)
        initVisionPortal(FrontCamera);
        initVisionPortal(BackCamera);
        setManualExposure(1, 1);

        // LeftWheel.setDirection(DcMotor.Direction.REVERSE);
        RightWheel.setDirection(DcMotor.Direction.REVERSE);
        PixelScooper.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the DS start button to be touched.
        telemetry.addData("mister & missus camera preview on/off", "3 dots, camera stream");
        telemetry.addData(">", "touch play to start TeleOpMode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                targetFound = false;
                desiredTag = null;
                List<AprilTagDetection> currentDetections = aprilTag.getDetections();
                for (AprilTagDetection detection : currentDetections) {
                    // Look to see if we have size info on this tag.
                    if (detection.metadata != null) {
                        // Check to see if we want to track towards this tag.
                        if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
                            // Yes, we want to use this tag.
                            targetFound = true;
                            desiredTag = detection;
                            break; // don't look any further.
                        } else {
                            // This tag is in the library, but we do not want to track it right now.
                            telemetry.addData("skipping", "Tag ID %d is not desired", detection.id);
                        }
                    } else {
                        // This tag is NOT in the library, so we don't have enough information to track
                        // to it.
                        telemetry.addData("don kno", "Tag ID %d is not in TagLibrary", detection.id);
                    }
                }
                if (targetFound) {
                    // Determine heading, range and Yaw (tag image rotation) error so we can use
                    // them to control the robot automatically.
                    double rangeError = (desiredTag.ftcPose.range - DESIRED_DISTANCE);
                    double headingError = desiredTag.ftcPose.bearing;
                    // Use the speed and turn "gains" to calculate how we want the robot to move.
                    drive = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
                    turn = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN);
                    telemetry.addData("Found", "ID %d (%s)", desiredTag.id, desiredTag.metadata.name);
                    telemetry.addData("Range", "%5.1f inches", desiredTag.ftcPose.range);
                    telemetry.addData("Bearing", "%3.0f inches", desiredTag.ftcPose.bearing);
                } else if (firstMove == true) {
                    drive = 0;
                    turn = 0;
                }
                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }
                // Telemetry
                visionPortalTelemetry();
                telemetry.update();
                // Move
                private void bool firstMove = false;
                if (drive != 0 && turn != 0) {
                    move(drive, turn);
                } else if (firstMove == false) {
                    move(1, 0);
                    hold(2);
                    move(0, 90);
                    firstMove = true;
                }
                // Prevent Explosions
                sleep(10);
            }
        }
    }
    // Functions
    public void hold(int seconds) {
        sleep((seconds * 1000));
    }

    public void move(double x, double yaw) {
        // Calculate wheel powers.
        double leftPower = x - yaw;
        double rightPower = x + yaw;
        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }
        LeftWheel.setPower(leftPower);
        RightWheel.setPower(rightPower);
    }

    private void initVisionPortal(WebcamName Camera) {
        // TFOD
        tfod = new TfodProcessor.Builder().build();
        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.90f);

        // AprilTag
        aprilTag = new AprilTagProcessor.Builder().build();
        // .setDrawAxes(false)
        // .setDrawCubeProjection(false)
        // .setDrawTagOutline(true)
        // .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
        // .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
        // .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
        aprilTag.setDecimation(2);

        // VisionPortal
        VisionPortal.Builder builder = new VisionPortal.Builder();
        // Set the camera
        builder.setCamera(Camera);
        //builder.setCameraResolution(new Size(1920, 1080));
        // Enable the RC preview (LiveView).
        // false - to omit camera monitoring.
        builder.enableLiveView(true);
        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        builder.setStreamFormat(VisionPortal.StreamFormat.MJPEG);
        // Choose whether LiveView stops if no processors enabled
        // true - monitor shows solid orange screen if no processors enabled
        // false - monitor shows camera view without annotations
        builder.setAutoStopLiveView(true);
        // Add processors
        builder.addProcessors(tfod, aprilTag);
        visionPortal = builder.build();
    }

    private void visionPortalTelemetry() {
        // TFOD
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData(String.format("I found %d Object(s) :D", currentRecognitions.size()));
        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2;
            double y = (recognition.getTop() + recognition.getBottom()) / 2;
            telemetry.addData("", " ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }

        // AprilTag
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData(String.format("I found %d AprilTag(s) :D", currentDetections.size()));
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x,
                        detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch,
                        detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range,
                        detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(
                        String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
    }

    private void setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls
        if (visionPortal == null) {
            return;
        }
        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("mister camera", "I'm trying to find it");
            telemetry.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("mister camera", "it's here");
            telemetry.update();
        }
        // Set camera controls unless we are stopping.
        if (!isStopRequested()) {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure((long) exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }
}

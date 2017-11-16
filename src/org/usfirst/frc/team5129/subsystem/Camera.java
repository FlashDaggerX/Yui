package org.usfirst.frc.team5129.subsystem;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5129.subsystem.meta.State;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class Camera extends Subsystem {

	private Thread vision;

	public Camera() {
		super();
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == State.RUNNING) {
			// This is a code example from WPI's Java examples.
			vision = new Thread(new Runnable() {
				@Override
				public void run() {
					UsbCamera camera = CameraServer.getInstance()
							.startAutomaticCapture();
					camera.setResolution(640, 480);
					CvSink cvSink = CameraServer.getInstance().getVideo();
					CvSource outputStream = CameraServer.getInstance()
							.putVideo("vision_def", 640, 480);

					Mat mat = new Mat();
					while (!Thread.interrupted()) {
						if (cvSink.grabFrame(mat) == 0) {
							outputStream.notifyError(cvSink.getError());
							continue;
						}
						Imgproc.rectangle(mat, new Point(100, 100), new Point(
								400, 400), new Scalar(255, 255, 255), 5);
						outputStream.putFrame(mat);
					}
				}
			});
			vision.setDaemon(true);
			vision.start();
		} else {
			if (vision.isAlive()) {
				try {
					vision.join();
				} catch (InterruptedException e) {
					DriverStation.reportError(
							"STATE=STOPPED:vision_subsys_cannot_join_main",
							true);
				}
			}
		}
	}

	@Override
	public boolean done() {
		if (vision.isAlive()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				DriverStation.reportError(
						"STATE=STALLED:vision_subsys_passed_sleep", true);
				return false;
			}
		}
		return true;
	}

	@Override
	public String getName() {
		return "CameraServer";
	}

	@Override
	public String getDescription() {
		return "Gets images from a USB Camera server and send them to the Dashboard.";
	}

}

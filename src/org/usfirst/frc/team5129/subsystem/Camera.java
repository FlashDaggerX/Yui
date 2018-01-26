package org.usfirst.frc.team5129.subsystem;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5129.meta.ControlSafety;
import org.usfirst.frc.team5129.meta.Subsystem;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class Camera extends Subsystem {

	private Thread vision;

	private volatile CvSource outputStream;

	public Camera() {
		super(null);
	}

	@Override
	public void complete(int i) {
		if (getMotorState() == ControlSafety.MotorState.RUNNING) {
			switch (i) {
			case 0x0:
				if (vision == null) {
					// This is a code example from WPI's Java examples.
					vision = new Thread(new Runnable() {
						@Override
						public void run() {
							UsbCamera camera = CameraServer.getInstance()
									.startAutomaticCapture();
							camera.setResolution(640, 480);
							CvSink cvSink = CameraServer.getInstance().getVideo();
							setOutputStream(CameraServer.getInstance().putVideo("vision_def",
									640, 480));
							Mat mat = new Mat();
							while (!Thread.interrupted()) {
								if (cvSink.grabFrame(mat) == 0) {
									getOutputStream().notifyError(cvSink.getError());
									continue;
								}
								Imgproc.rectangle(mat, new Point(100, 100),
										new Point(400, 400), new Scalar(255, 255, 255), 5);
								getOutputStream().putFrame(mat);
							}
						}
					});
					vision.setDaemon(true);
					vision.start();
				} else {
					DriverStation.reportError(
							"STATE=STARTED:vision_subsys_already_created", false);
				}
				break;
			case 0x1:
				if (vision.isAlive()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						DriverStation.reportError(
								"STATE=STALLED:vision_subsys_passed_sleep", true);
					}
				}
				break;
			}
		}
	}

	private synchronized void setOutputStream(CvSource source) {
		this.outputStream = source;
	}

	private synchronized CvSource getOutputStream() {
		return outputStream;
	}

	@Override
	public int getID() {
		return 0xa;
	}

}

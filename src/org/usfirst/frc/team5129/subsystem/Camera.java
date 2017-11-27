package org.usfirst.frc.team5129.subsystem;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5129.safety.MotorState;
import org.usfirst.frc.team5129.subsystem.meta.Subsystem;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class Camera extends Subsystem {

	private Thread vision;

	private volatile CvSource outputStream;

	public Camera() {
		super();
	}

	/*
	 * Functions:
	 * 
	 * [0 - Start Server] [1 - Join Thread]
	 */
	@Override
	public void complete(byte i) {
		if (getMotorState() == MotorState.RUNNING) {
			switch (i) {
				case 0:
					if (vision == null) {
						// This is a code example from WPI's Java examples.
						vision = new Thread(new Runnable() {
							@Override
							public void run() {
								UsbCamera camera = CameraServer.getInstance()
										.startAutomaticCapture();
								camera.setResolution(640, 480);
								CvSink cvSink = CameraServer.getInstance()
										.getVideo();
								setOutputStream(CameraServer.getInstance()
										.putVideo("vision_def", 640, 480));
								Mat mat = new Mat();
								while (!Thread.interrupted()) {
									if (cvSink.grabFrame(mat) == 0) {
										getOutputStream().notifyError(
												cvSink.getError());
										continue;
									}
									Imgproc.rectangle(mat, new Point(100, 100),
											new Point(400, 400), new Scalar(
													255, 255, 255), 5);
									getOutputStream().putFrame(mat);
								}
							}
						});
						vision.setDaemon(true);
						vision.start();
					} else {
						DriverStation.reportError(
								"STATE=STARTED:vision_subsys_already_created",
								false);
					}
					break;
				case 1:
					if (vision.isAlive()) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							DriverStation.reportError(
									"STATE=STALLED:vision_subsys_passed_sleep",
									true);
						}
					}
					break;
			}
		}
	}
	
	@Override
	public boolean onStall() {
		return true;
	}
	
	@Override
	public void onStop() {
		if (vision.isAlive()) {
			getOutputStream().free();
			try {
				vision.join();
			} catch (InterruptedException e) {
				DriverStation.reportError(
						"STATE=STOPPED:vision_subsys_cannot_join_main",
						true);
			}
		}
		vision = null;
		DriverStation.reportWarning(
				"STATE=STOPPED_?_STALLED:vision_subsys_thread_died", false);
	}
	
	@Override
	public void onTick() {

	}
	
	@Override
	public String getName() {
		return "CameraServer";
	}

	@Override
	public String getDescription() {
		return "Gets images from a USB Camera server and sends them to the Dashboard.";
	}

	private synchronized CvSource getOutputStream() {
		return outputStream;
	}

	private synchronized void setOutputStream(CvSource source) {
		this.outputStream = source;
	}

}

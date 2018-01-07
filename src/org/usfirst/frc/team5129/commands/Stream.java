package org.usfirst.frc.team5129.commands;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5129.robot.Robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Stream extends FDCommand {
	
	UsbCamera camera;
	CvSource outputStream;
	
	Thread vision;
	
	public Stream(Robot bot) {
		super(bot);
		requires(robot().getSubsystemBinder().getSubsystems()[1]);
		
		setName("Stream");
		setSubsystem("sCamera");
	}

	@Override
	public void initialize() {
		camera = robot().getHardwareBinder().getCameraServer();
		
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
		
		cancel();
	}
	
	@Override
	public void execute() {
		
	}
	
	@Override
	public void end() {
		
	}
	
	@Override
	protected boolean isFinished() {
		
		return false;
	}
	
	synchronized void setOutputStream(CvSource source) {
		this.outputStream = source;
	}

	synchronized CvSource getOutputStream() {
		return outputStream;
	}
	
}

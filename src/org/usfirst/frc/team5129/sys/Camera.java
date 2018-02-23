package org.usfirst.frc.team5129.sys;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5129.meta.SAuto;
import org.usfirst.frc.team5129.meta.SSystem;

public class Camera implements SSystem {

    private Thread vision;

    private volatile CvSource outputStream;

    @Override
    public void init() {
        if (vision == null) {
            // This is a code example from WPI's Java examples.
            vision = new Thread(() -> {
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
            });
            vision.setDaemon(true);
            vision.start();
        }
    }

    @Override
    public void execute(int i) {
        // Placeholder
    }

    @Override
    public void auto(SAuto i) {
        // Placeholder
    }

    @Override
    public void disable() {
        getOutputStream().setConnected(false); // Debug
    }

    private synchronized void setOutputStream(CvSource source) {
        this.outputStream = source;
    }

    private synchronized CvSource getOutputStream() {
        return outputStream;
    }


    @Override
    public String getName() {
        return "camera";
    }

}

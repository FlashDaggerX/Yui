package self.flashdaggerx.fake;

public class XboxController implements GenericHID {
    private String name;

    public XboxController(String name) {
        this.name = name;
    }

    @Override
    public double set(double i) {
        System.out.printf("%s: Power set to %f\n", name, i);
        return i;
    }

    @Override
    public void drive(double x, double y) {
        System.out.println(name + ": Driving under X" + x + " Y" +y);
    }
}

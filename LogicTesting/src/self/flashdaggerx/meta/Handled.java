package self.flashdaggerx.meta;

import self.flashdaggerx.fake.GenericHID;

public class Handled {
    private GenericHID controller;

    public Handled(GenericHID controller) {
        this.controller = controller;
    }

    protected GenericHID getController() {
        return controller;
    }
}

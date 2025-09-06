package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {

    public OBJ_Door() {

        name = "Door";
        try {
            image = ImageIO.read(new File("./res/objects/door.png"));
            System.out.println(image);

        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}

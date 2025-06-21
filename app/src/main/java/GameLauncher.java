
import controller.MainController;

public class GameLauncher {

    public static void main(String[] args) {
        // Launch everything on the Swing event thread
        MainController controller = new MainController();
        controller.startGame();
        }
}




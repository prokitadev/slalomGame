public class Main {

    public static void main(String[] args) {
        SlalomGameModel model = new SlalomGameModel();
        GUI view = new GUI();
        SlalomGameController controller = new SlalomGameController(model, view);

        controller.play();
    }
}

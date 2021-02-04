public enum Status {

    NO_PLACE_TO_MOVE("No place to move"),
    NULL("");

    private String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}

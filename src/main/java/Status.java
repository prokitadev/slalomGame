public enum Status {

    NO_PLACE_TO_MOVE("No place to move"),
    MISSED_GATE("Missed gate"),
    NULL("");

    private String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}

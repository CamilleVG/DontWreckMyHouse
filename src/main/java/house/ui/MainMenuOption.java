package house.ui;

public enum MainMenuOption {

    VIEW_RESERVATIONS_FOR_HOST(1, "View Reservations for Host", false),
    MAKE_RESERVATION(2, "Make a Reservation", false),
    EDIT_RESERVATION(3, "Edit a Reservation", false),
    CANCEL_RESERVATION(4, "Cancel a Reservation", false),
    EXIT(5, "Exit", false);

    private int value;
    private String message;
    private boolean hidden;

    private MainMenuOption(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static MainMenuOption fromValue(int value) {
        MainMenuOption[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            MainMenuOption option = var1[var3];
            if (option.getValue() == value) {
                return option;
            }
        }

        return EXIT;
    }

    public int getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}

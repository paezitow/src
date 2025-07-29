package model;

public enum GameStatsEnum {
    NON_STARTED("Não iniciado"),
    INCOMPLETE("Incompleto"),
    COMPLETE("Completo");

    private String label;

    GameStatsEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

package model;

public enum Team {
    HAN("한나라"), CHO("초나라");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public boolean isHan() {
        return this == HAN;
    }

    public Team next() {
        if (isHan()) {
            return CHO;
        }
        return HAN;
    }

    public String getName() {
        return name;
    }
}

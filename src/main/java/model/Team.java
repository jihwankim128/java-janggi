package model;

import model.piece.Piece;

public enum Team {
    HAN("한나라"), CHO("초나라");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public void validateAlly(Piece piece) {
        if (piece.isOtherTeam(this)) {
            throw new IllegalArgumentException(this.name + "의 기물이 아닙니다.");
        }
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

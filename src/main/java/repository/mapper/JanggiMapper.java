package repository.mapper;

import model.GameStatus;
import model.JanggiState;
import model.Team;
import model.piece.Cannon;
import model.piece.Chariot;
import model.piece.Elephant;
import model.piece.General;
import model.piece.Guard;
import model.piece.Horse;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Soldier;
import model.state.BigJang;
import model.state.BigJangDone;
import model.state.Finished;
import model.state.Running;

public class JanggiMapper {

    private JanggiMapper() {
    }

    public static Piece createPiece(String type, String teamName) {
        Team team = Team.valueOf(teamName);
        return switch (PieceType.valueOf(type)) {
            case CHARIOT -> new Chariot(team);
            case CANNON -> new Cannon(team);
            case HORSE -> new Horse(team);
            case ELEPHANT -> new Elephant(team);
            case SOLDIER -> new Soldier(team);
            case GUARD -> new Guard(team);
            case GENERAL -> new General(team);
        };
    }

    public static JanggiState createState(String statusName, String turnName) {
        Team turn = Team.valueOf(turnName);
        return switch (GameStatus.valueOf(statusName)) {
            case GameStatus.PLAYING -> new Running(turn);
            case GameStatus.BIG_JANG -> new BigJang(turn);
            case GameStatus.DONE -> new Finished(turn);
            case GameStatus.BIG_JANG_DONE -> new BigJangDone(turn);
        };
    }
}

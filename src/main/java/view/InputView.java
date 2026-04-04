package view;

import static view.formater.BoardFormatter.formatSymbol;
import static view.mapper.ViewMapper.FORMATION_DISPLAY_MAPPER;
import static view.mapper.ViewMapper.FORMATION_ORDER_MAPPER;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import model.Team;
import model.board.JanggiFormation;
import model.board.Position;
import model.piece.Piece;
import view.parser.InputParser;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final InputParser PARSER = new InputParser();

    public JanggiFormation readFormationByTeam(Team team) {
        System.out.printf("%n%s의 상차림을 선택해주세요.%n", team.getName());
        FORMATION_ORDER_MAPPER.forEach((order, formation) ->
                System.out.printf("%d. %s%n", order, FORMATION_DISPLAY_MAPPER.get(formation)));

        String input = SCANNER.nextLine();
        int order = PARSER.parseNumber(input);

        return Optional.ofNullable(FORMATION_ORDER_MAPPER.get(order))
                .orElseThrow(() -> new IllegalArgumentException("올바른 상차림을 선택해주세요."));
    }

    public Position readPiecePositionForMove(Team turn) {
        System.out.println();
        System.out.printf("[%s] 이동할 기물을 선택해주세요. (쉼표 기준으로 분리)%n", turn.getName());
        System.out.print("기물: ");
        return extractPosition();
    }

    private Position extractPosition() {
        List<String> tokens = PARSER.parseToken(SCANNER.nextLine(), ",");
        int row = PARSER.parseNumber(tokens.get(0));
        int col = PARSER.parseNumber(tokens.get(1));
        return new Position(row, col);
    }

    public Position readPiecePositionForArrange(Team turn, Piece piece) {
        System.out.println();
        System.out.printf("[%s] 기물 %s의 다음 위치를 선택해주세요. (쉼표 기준으로 분리)%n", turn.getName(), formatSymbol(piece));
        System.out.print("기물: ");
        return extractPosition();
    }
}

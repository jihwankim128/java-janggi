package view;

import model.JanggiFormation;
import model.Team;
import view.parser.NumberParser;

import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final NumberParser NUMBER_PARSER = new NumberParser();

    public JanggiFormation readFormationNumber(Team team, List<JanggiFormation> janggiFormations) {
        System.out.printf("%n%s의 상차림을 선택해주세요.%n", team.getName());
        for (JanggiFormation formation : janggiFormations) {
            System.out.printf("%d. %s%n", formation.getOrder(), formation.getFormation());
        }

        String input = SCANNER.nextLine();
        int order = NUMBER_PARSER.parse(input);
        return JanggiFormation.from(order);
    }
}

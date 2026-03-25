package view.parser;

public class NumberParser {
    public int parse(String input) {
        try {
            return Integer.parseInt(input.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력입니다. 수를 입력해주세요: " + input);
        }
    }
}

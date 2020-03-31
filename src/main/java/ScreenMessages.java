import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenMessages {

    private String messageToUser;
    private double result;

    public void welcomeMessage() {
        messageToUser = "Witaj w Eleven Questions To J\nRozpocznij grę[1] Wyjdź [2] Dodaj pytanie [3]";
        System.out.println(messageToUser);

    }

    public void byeByeMessage() {
        messageToUser = "Dziekuję za grę, następnym razem będzie lepiej !";
        System.out.println(messageToUser);

    }

    public void userResult() {
        UserResults userResults = new UserResults();
        MainClass mainClass = new MainClass();
        result = userResults.calculateResult(
                mainClass.getAllQuestionsGiven(),
                mainClass.getCorrectQuestionGivenByUser());

        String resultFormatted = String.format("%.2f", result);

        messageToUser = "Twój wynik to " + resultFormatted + " %";
        System.out.println(messageToUser);

    }

    public void showTheAnswer() {
        messageToUser = "Pokaż prawidłową odpowiedź [Wybierz [1] ] ";
        System.out.println(messageToUser);

    }


    public void answerValidation() {
        messageToUser = "Czy poprawnie odpowiedziałeś na pytanie ? \nTak [1] Nie [2] ";
        System.out.println(messageToUser);

    }

    public void questionToUser(int questionNumber) {
        MainClass mainClass = new MainClass();
        messageToUser = "Pytanie nr " + questionNumber + " z " + ((int) mainClass.getAllQuestionsGiven());
        System.out.println(messageToUser);

    }

    public void wrongCommand() {
        messageToUser = "Niewłaściwa komenda, wybierz ponownie";
        System.out.println(messageToUser);
    }

    public void addDateTimeStamp() {
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println("Data i godzina testu "
                + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}

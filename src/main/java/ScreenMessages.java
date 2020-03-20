
public class ScreenMessages {

    private String messageToUser;
    private double result;

    public String welcomeMessage() {
        messageToUser = "Witaj w Eleven Questions To J\nRozpocznij grę[1] Wyjdź [2]";
        System.out.println(messageToUser);
        return messageToUser;
    }

    public String byeByeMessage() {
        messageToUser = "Dziekuję za grę, następnym razem będzie lepiej !";
        System.out.println(messageToUser);
        return messageToUser;
    }

    public String userResult() {
       UserResults userResults = new UserResults();
       MainClass mainClass = new MainClass();
       result = userResults.calculateResult(
                mainClass.getAllQuestionsGiven(),
                mainClass.getCorrectQuestionGivenByUser());

       String resultFormatted = String.format("%.2f",result);

        messageToUser = "Twój wynik to " + resultFormatted + " %";
        System.out.println(messageToUser);
        return messageToUser;
    }

    public String showTheAnswer() {
        messageToUser = "Pokaż prawidłową odpowiedź [Wybierz [1] ] ";
        System.out.println(messageToUser);
        return messageToUser;
    }


    public String answerValidation() {
        messageToUser = "Czy poprawnie odpowiedziałeś na pytanie ? \nTak [1] Nie [2] ";
        System.out.println(messageToUser);
        return messageToUser;
    }

    public String questionToUser(int questionNumber) {
        messageToUser = "Pytanie nr "+ questionNumber;
        System.out.println(messageToUser);
        return messageToUser;
    }

    public String wrongCommand() {
        messageToUser = "Niewłaściwa komenda, wybierz ponownie";
        System.out.println(messageToUser);
        return messageToUser;
    }

}

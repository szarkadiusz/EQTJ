import com.sun.tools.javac.Main;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;


@Getter

public class MainClass {
    private double allQuestionsGiven = 11.0;
    private static double correctQuestionGivenByUser;
    private static String dataFromScanner;
    private static String userAnswer;
    private static String showTheAnswer;
    Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        ScreenMessages screenMessages = new ScreenMessages();
        UserResults userResults = new UserResults();


        do {
            dataFromScanner = getUserDecision();
            if ((!dataFromScanner.equals("1")) && (!dataFromScanner.equals("2"))) {
                screenMessages.wrongCommand();
            }
        }
        while ((!dataFromScanner.equals("1")) && (!dataFromScanner.equals("2")));
        {
            gameScenario();
        }

    }

    private static String getUserDecision() {
        ScreenMessages screenMessages = new ScreenMessages();
        MainClass mainClass = new MainClass();
        screenMessages.welcomeMessage();
        dataFromScanner = mainClass.scanner.next();
        return dataFromScanner;
    }

    private static void gameScenario() {
        ScreenMessages screenMessages = new ScreenMessages();
        if (dataFromScanner.equals("1")) {
            game();
        } else if (dataFromScanner.equals("2")) {
            endGame();
        }
    }

    private static void game() {
        ScreenMessages screenMessages = new ScreenMessages();
        MainClass mainClass = new MainClass();

        for (int i = 1; i < mainClass.allQuestionsGiven; i++) {

            screenMessages.questionToUser(i);
            getQuestionFromDB();

            screenMessages.showTheAnswer();
            getAnswerFromDB();

            screenMessages.answerValidation();
            isAnswerIsCorrect();

        }
        screenMessages.userResult();
        screenMessages.byeByeMessage();

    }

    private static void endGame() {
        ScreenMessages screenMessages = new ScreenMessages();
        screenMessages.byeByeMessage();
    }

    private static void getQuestionFromDB() {


        System.out.println("wyciagnij pytanie z mysql");
        sqlQuery("select Tresc_Pytania from eqfj_db where Numer_pytania=");

    }

    private static void getAnswerFromDB() {
        ScreenMessages screenMessages = new ScreenMessages();
        MainClass mainClass = new MainClass();
        do {
            showTheAnswer = mainClass.scanner.next();
            if (!showTheAnswer.equals("1")) {
                screenMessages.wrongCommand();
            }
        }
        while (!showTheAnswer.equals("1"));
        {
            System.out.println("wyciagnij odpowiedz z mysql");

        }
    }

    private static void isAnswerIsCorrect() {
        ScreenMessages screenMessages = new ScreenMessages();
        MainClass mainClass = new MainClass();
        do {
            userAnswer = mainClass.scanner.next();
            if ((!userAnswer.equals("1")) && (!userAnswer.equals("2"))) {
                screenMessages.wrongCommand();
            }
        }
        while ((!userAnswer.equals("1")) && (!userAnswer.equals("2")));
        {
            if (userAnswer.equals("1")) {
                correctQuestionGivenByUser++;
            }
            System.out.println("Odpowiedź zapisana");
        }
    }

    private static String sqlQuery(String commandForSqlQuery) {
        int randomQuestion=random.nextInt(102);


//commandForSqlQuery ="select * from eqfj_db where rowid ()=random.nextInt(102); " ;
        String returnStatement = "";
        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eqfj_db?useSSL=false", "root", "Aras12");

            Statement stmt = con.createStatement();

            StringBuilder sb = new StringBuilder();
            sb.append(commandForSqlQuery);
            sb.append(randomQuestion);
            ResultSet rs = stmt.executeQuery(String.valueOf(sb));

            while (rs.next()) {
                returnStatement = rs.getInt(1) + " " + rs.getString("Przykładowa_odpowiedz") + " " + rs.getString("Tresc_pytania");

            }

            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return returnStatement;
    }


    public double getCorrectQuestionGivenByUser() {
        return correctQuestionGivenByUser;
    }
}


import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
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

    public static void main(String[] args) throws SQLException, IOException {
        ScreenMessages screenMessages = new ScreenMessages();
        UserResults userResults = new UserResults();


        do {
            dataFromScanner = getUserDecision();
            if ((!dataFromScanner.equals("1")) && (!dataFromScanner.equals("2"))&& (!dataFromScanner.equals("3"))) {
                screenMessages.wrongCommand();
            }
        }
        while ((!dataFromScanner.equals("1")) && (!dataFromScanner.equals("2"))&& (!dataFromScanner.equals("3")));
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

    private static void gameScenario() throws SQLException, IOException {
        ScreenMessages screenMessages = new ScreenMessages();
        if (dataFromScanner.equals("1")) {
            game();
        } else if (dataFromScanner.equals("2")) {
            endGame();
        }else if (dataFromScanner.equals("3")){
            getNewEntryForDB();
        }
    }

    private static void game() {
        ScreenMessages screenMessages = new ScreenMessages();
        MainClass mainClass = new MainClass();

        for (int i = 1; i < mainClass.allQuestionsGiven + 1; i++) {

            int randomNumber = getRandomNumber();

            screenMessages.questionToUser(i);
            getQuestionFromDB(randomNumber);

            screenMessages.showTheAnswer();
            getAnswerFromDB(randomNumber);

            screenMessages.answerValidation();
            isAnswerIsCorrect();

        }
        screenMessages.userResult();
        screenMessages.addDateTimeStamp();
        screenMessages.byeByeMessage();

    }

    private static void endGame() {
        ScreenMessages screenMessages = new ScreenMessages();
        screenMessages.byeByeMessage();
    }

    private static void getQuestionFromDB(int randomNumberForQuestion) {


        sqlQuery("Tresc_pytania", randomNumberForQuestion);

    }

    private static void getAnswerFromDB(int randomNumberForAnswer) {
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

            sqlQuery("Przykładowa_odpowiedz", randomNumberForAnswer);

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

    private static String sqlQuery(String neededColumn, int neededRow) {
        MainClass mainClass = new MainClass();

        String returnStatement = "";
        String sqlQue = "select ? from eqfj_db where Numer_pytania=?";
        try {
            String url = "jdbc:mysql://localhost:3306/eqfj_db?useLegacyDatetimeCode=false&serverTimezone=UTC";
            Connection con = DriverManager.getConnection(url, "root", "Aras12");


            PreparedStatement preparedStatement = con.prepareStatement(sqlQue);
            preparedStatement.setString(1, neededColumn);
            preparedStatement.setInt(2, neededRow);


            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                returnStatement = rs.getInt(1) + " " + rs.getString("Przykładowa_odpowiedz") + " " + rs.getString("Tresc_pytania");
            }

            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return returnStatement;
    }

    private static int getRandomNumber() {

        int randomNum = random.nextInt(102);
        return randomNum;

    }

    public double getCorrectQuestionGivenByUser() {
        return correctQuestionGivenByUser;
    }

    private static void getNewEntryForDB() throws SQLException, IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ScreenMessages screenMessages = new ScreenMessages();


        screenMessages.receiveQuestion();
        String newQuestion = bufferedReader.readLine();

        screenMessages.receiveQuestionAnswer();
        String newAnswer = bufferedReader.readLine();

        PreparedStatement preparedStatement = null;

        String sqlQuery = "INSERT INTO eqfj_db (Tresc_pytania,Przykładowa_odpowiedz)VALUES (?,?)";
        try {
            String url = "jdbc:mysql://localhost:3306/eqfj_db?useLegacyDatetimeCode=false&serverTimezone=UTC";
            Connection con = DriverManager.getConnection(url, "root", "Aras12");
            preparedStatement = con.prepareStatement(sqlQuery);

            preparedStatement.setString(1, newQuestion);
            preparedStatement.setString(2, newAnswer);

           int rowsAdded = preparedStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            preparedStatement.close();
        }


        screenMessages.receiveQuestionSummary();
        getUserDecision();
        gameScenario();
    }
}

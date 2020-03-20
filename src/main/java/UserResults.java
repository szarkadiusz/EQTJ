public class UserResults {
    MainClass mainClass = new MainClass();


    protected double calculateResult(double allQuestionsGiven, double correctQuestionGivenByUser) {

        double userResult = ( correctQuestionGivenByUser/ allQuestionsGiven) * 100.0;
        return userResult;
    }


}

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    private String question;
    private String[] options;
    private int correctAnswer;

    public QuizQuestion(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(int selectedOption) {
        return selectedOption == correctAnswer;
    }
}

class QuizApp {
    private QuizQuestion[] questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private boolean timedOut;

    public QuizApp(QuizQuestion[] questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void start() {
        if (currentQuestionIndex < questions.length) {
            QuizQuestion currentQuestion = questions[currentQuestionIndex];
            displayQuestion(currentQuestion);

            // Create a timer to limit the time to answer.
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Time's up!");
                    timedOut = true;
                    displayResult();
                }
            }, 10000); // 10 seconds timer

            // User's choice input.
            int userChoice = getUserChoice();
            if (!timedOut) {
                if (currentQuestion.isCorrect(userChoice)) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect.");
                }
                displayResult();
            }
        } else {
            System.out.println("Quiz completed.");
            displayResult();
        }
    }

    private int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice < 0 || choice >= questions[currentQuestionIndex].getOptions().length) {
            System.out.println("Enter your choice (0-" + (questions[currentQuestionIndex].getOptions().length - 1) + "): ");
            choice = scanner.nextInt();
        }
        return choice;
    }

    private void displayQuestion(QuizQuestion question) {
        System.out.println(question.getQuestion());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println(i + ". " + options[i]);
        }
    }

    private void displayResult() {
        System.out.println("Current Score: " + score + " out of " + currentQuestionIndex);
        currentQuestionIndex++;
        start(); // Next question
    }
}

public class quizAppWithTimer{
    public static void main(String[] args) {
        QuizQuestion[] quizQuestions = new QuizQuestion[]{
            new QuizQuestion("What is the capital of France?", new String[]{"Paris", "London", "Berlin"}, 0),
            new QuizQuestion("Which planet is known as the Red Planet?", new String[]{"Mars", "Venus", "Earth"}, 0),
            new QuizQuestion("What is the largest mammal?", new String[]{"Elephant", "Blue Whale", "Giraffe"}, 1)
        };

        QuizApp quizApp = new QuizApp(quizQuestions);
        quizApp.start();
    }
}


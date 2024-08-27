import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
public class Kbc {
    public static void main(String[] args) throws Exception {
        Gui gu = new Gui();
    }
}
class Gui {
    int current = 0;
    ArrayList<Question> questions = new ArrayList<>();
    JLabel questionLabel;
    JRadioButton option1, option2, option3, option4;
    ButtonGroup optionsGroup;
    JLabel result;

    public Gui() throws Exception {
        JFrame frame = new JFrame("KBC Game");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        questionLabel = new JLabel("");
        questionLabel.setBounds(50, 20, 300, 30);


        option1 = new JRadioButton("");
        option1.setBounds(50, 60, 300, 30);


        option2 = new JRadioButton("");
        option2.setBounds(50, 90, 300, 30);


        option3 = new JRadioButton("");
        option3.setBounds(50, 120, 300, 30);


        option4 = new JRadioButton("");
        option4.setBounds(50, 150, 300, 30);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);


        JButton next = new JButton("Next");
        next.setBounds(160, 200, 80, 30);

        result = new JLabel("");
        result.setBounds(50, 240, 300, 30);

        frame.add(questionLabel);
        frame.add(option1);
        frame.add(option2);
        frame.add(option3);
        frame.add(option4);
        frame.add(next);
        frame.add(result);
        frame.setVisible(true);

        Connection c = DriverManager.getConnection("jdbc:mysql://@localhost:3306/KBC","root","Kaushik123jain@");
        if(c==null) {
            System.out.println("Connection refused");
        }else {
            System.out.println("Successful");
        }
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM QUESTIONS");
        while(rs.next()) {
            questions.add(new Question(rs.getString("QUESTION"),rs.getString("OPTION1"),rs.getString("OPTION2"),rs.getString("OPTION3"),rs.getString("OPTION4"),rs.getInt("CORRECT")));
        }
        if(!questions.isEmpty()) {
            displayQuestion(0);
        }

        next.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()) {
                    if(current<questions.size()-1) {
                        current++;
                        displayQuestion(current);
                    }

                }else {
                    System.exit(-1);
                }
            }
        });
    }

    public void displayQuestion(int index) {
        Question q = questions.get(index);
        questionLabel.setText(q.getQuestion());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());
        optionsGroup.clearSelection();
        result.setText("");
    }
    public boolean check() {
        Question q = questions.get(current);
        boolean isCorrect = false;
        if(option1.isSelected() && q.getCorrect()==1) isCorrect=true;
        if(option2.isSelected() && q.getCorrect()==2) isCorrect=true;
        if(option3.isSelected() && q.getCorrect()==3) isCorrect=true;
        if(option4.isSelected() && q.getCorrect()==4) isCorrect=true;

        if(isCorrect) {
            result.setText("Correct answer");
            return true;
        }else {
            result.setText("Incorrect");
            return false;
        }
    }
}

class Question{
    String Question;
    String Option1;
    String Option2;
    String Option3;
    String Option4;
    int correct;

    public Question(String Question,String option1,String option2,String option3,String option4,int correct) {
        this.Question = Question;
        this.Option1= option1;
        this.Option2=option2;
        this.Option3=option3;
        this.Option4=option4;
        this.correct=correct;
    }

    public String getQuestion() {
        return Question;
    }

    public String getOption1() {
        return Option1;
    }

    public String getOption2() {
        return Option2;
    }

    public String getOption3() {
        return Option3;
    }

    public String getOption4() {
        return Option4;
    }

    public int getCorrect() {
        return correct;
    }

}

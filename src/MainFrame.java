import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    JButton buttonLecture = new JButton();
    JButton buttonStudent = new JButton();
    JButton buttonTeacher = new JButton();

    MainFrame(){
        this.setTitle("MAIN");

        this.setLayout(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(120,120,120));
        this.setVisible(true);

        JPanel panelUp = new JPanel();
        panelUp.setBounds(0, 0, 800, 200);
        panelUp.setBackground(Color.GRAY);
        panelUp.setBorder(BorderFactory.createEtchedBorder());
        this.add(panelUp);

        setupButton(buttonLecture, "LECTURE", 0, 200);
        setupButton(buttonStudent, "STUDENT", 0, 300);
        setupButton(buttonTeacher, "TEACHER", 0, 400);

        this.add(buttonLecture);
        this.add(buttonStudent);
        this.add(buttonTeacher);

        JPanel panelBottom = new JPanel();
        panelBottom.setBounds(0, 500, 800, 200);
        panelBottom.setBackground(Color.GRAY);
        panelBottom.setBorder(BorderFactory.createEtchedBorder());
        this.add(panelBottom);

        JLabel labelVersion = new JLabel();
        labelVersion.setBounds(0, 705, 800, 50);
        labelVersion.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        labelVersion.setText("Version 1.0.0");
        labelVersion.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(labelVersion);
    }

    private void setupButton(JButton button, String text, int x, int y) {
        button.setBounds(x, y, 800, 100);
        button.setText(text);
        button.setFocusable(false);
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==buttonLecture){
            MainFrame.this.dispose();
            new LectureFrame();
        }
        if (e.getSource()==buttonStudent){
            MainFrame.this.dispose();
            new StudentFrame();
        }
        if (e.getSource()==buttonTeacher){
            MainFrame.this.dispose();
            new TeacherFrame();
        }
    }
}

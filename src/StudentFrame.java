import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class StudentFrame extends JFrame implements ActionListener {

    JButton buttonSubmit = new JButton();
    JButton buttonReturn = new JButton();
    JLabel labelStudentList = new JLabel();
    JTextArea textArea = new JTextArea();
    private JTextField textStudentId;
    private JTextField textStudentName;
    private JTextField textStudentSurname;
    private JTextField textStudentTerm;
    private JComboBox comboBoxLectures;
    private JSONArray jsonArray;
    private DefaultListCellRenderer listRenderer;
    JButton buttonRefresh = new JButton();
    String filePath = "students.json";
    String filePathToLecture = "lectures.json";
    String jsonContent = readJsonFile(filePath);
    String jsonContentLecture = readJsonFile(filePathToLecture);
    StudentFrame(){

        this.setTitle("STUDENT");

        this.setLayout(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(120,120,120));
        this.setVisible(true);


        JLabel labelForm = new JLabel();
        JLabel labelHeader = new JLabel();
        JLabel labelID = new JLabel();
        JLabel labelName = new JLabel();
        JLabel labelSurname = new JLabel();
        JLabel labelSection = new JLabel();
        JLabel labelLectures = new JLabel();

        setupLabel(labelForm, "Student Form", 0, -50, 800, 50);
        setupLabel(labelHeader, "Create Student", 0, 0, 400, 50);
        setupLabel(labelID, "ID", 0, 50, 100, 50);
        setupLabel(labelName, "Name", 0, 100, 100, 50);
        setupLabel(labelSurname, "Surname", 0, 150, 100, 50);
        setupLabel(labelSection, "Term", 0, 200, 100, 50);
        setupLabel(labelLectures, "Lectures", 0, 250, 100, 50);


        textStudentId = new JTextField();
        textStudentName = new JTextField();
        textStudentSurname = new JTextField();
        textStudentTerm = new JTextField();

        setupText(textStudentId,100, 50, 300, 50);
        setupText(textStudentName,100, 100, 300, 50);
        setupText(textStudentSurname, 100, 150, 300, 50);
        setupText(textStudentTerm, 100, 200, 300, 50);


        // TODO THAT SHOULD BE CHECKLIST INSTEAD OF COMBOBOX
        comboBoxLectures = new JComboBox();
        jsonArray = new JSONArray(jsonContentLecture);
        LecturesComboBox(jsonArray);
        comboBoxLectures.setBounds(100,300,300,50);
        comboBoxLectures.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        comboBoxLectures.setRenderer(listRenderer);
        this.add(comboBoxLectures);


        JLabel labelSearch = new JLabel();
        setupLabel(labelSearch, "Search", 400, 0, 400, 50);
        JTextField textSearch = new JTextField();
        setupText(textSearch, 400, 50, 400, 50);


        if (jsonContent != null){
            // parse JSON array from the file content
            jsonArray = new JSONArray(jsonContent);

            // display the information in the JLabel
            updateLabel(jsonArray);
        } else {
            labelStudentList.setText("Error reading teachers.json");
        }

        JLabel labelStudents = new JLabel();
        setupLabel(labelStudents, "List of Students", 400, 100, 350, 50);
        buttonRefresh = new JButton();
        String s = new String(Character.toChars(8634));
        setupButton(buttonRefresh, s, 750, 150, 50, 50);


        textArea.setPreferredSize(new Dimension(0,2000));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setAutoscrolls(true);
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
        textArea.setBorder(BorderFactory.createCompoundBorder(textArea.getBorder(), BorderFactory.createEmptyBorder(-15, 8, 8, 8)));


        JScrollPane scrollFrame = new JScrollPane(textArea);
        scrollFrame.setBounds(400, 200, 400, 550);
        this.add(scrollFrame);


        setupButton(buttonReturn,"Return", 0, 700, 200, 50);
        setupButton(buttonSubmit,"Submit", 200, 700, 200, 50);
    }

    private void setupLabel(JLabel label, String label_name, int x, int y, int width, int height){
        label.setText(label_name);
        label.setBounds(x, 50 + y, width, height);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 18));
        label.setBackground(Color.LIGHT_GRAY);
        label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        label.setOpaque(true);
        this.add(label);
    }

    private void setupText(JTextField text_field, int x, int y, int width, int height) {
        text_field.setBounds(x, 50 + y, width, height);
        text_field.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        this.add(text_field);
    }

    private void setupButton(JButton button, String text, int x, int y, int width, int height) {
        button.setText(text);
        button.setBounds(x, y, width, height);
        button.setFocusable(false);
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.addActionListener(this);
        this.add(button);
    }

    private String readJsonFile(String fileName){
        try {
            // Get the current working directory
            Path currentDirectory = Paths.get(System.getProperty("user.dir"));

            // Construct the full path by combining the current directory and the provided file name
            Path filePath = currentDirectory.resolve(fileName);
            // Check if the file exists
            if (!Files.exists(filePath)) {
                try {
                    // Attempt to create the file
                    Files.createFile(filePath);
                    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                    writer.println("[]");
                    writer.close();
                } catch (FileAlreadyExistsException e) {

                }
            }
            // Read the lines from the file
            List<String> lines = Files.readAllLines(filePath);

            // Join the lines and return the content
            return String.join("", lines);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void LecturesComboBox(JSONArray jsonArray) {
        // extract unique teacher names from the JSON array
        List<String> lectureList = new ArrayList<>();
        String empty = "";
        lectureList.add(empty);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject lecture = jsonArray.getJSONObject(i);
            String lectures = lecture.getString("lecture_name");
            if (!lectureList.contains(lectures)) {
                lectureList.add(String.valueOf(lectures));
            }
        }
        // populate the ComboBox with teacher names
        for (String lecture : lectureList) {
            comboBoxLectures.addItem(lecture);
        }
    }


    private void updateLabel(JSONArray jsonArray){


        for (int i=0; i < jsonArray.length(); i++){

            JSONObject student = jsonArray.getJSONObject(i);

            JSONArray lecture = student.getJSONArray("student_lecture");

            String labelText = "\nStudent " + (i+1) + "\n\n" +
                    "  ID:\t" + student.getInt("student_id") + "\n" +
                    "  Name:\t" + student.getString("student_name") + "\n" +
                    "  Surname:\t" + student.getString("student_surname") + "\n" +
                    "  Term:\t" + student.getInt("student_term") + "\n" +
                    "  Lectures:\t" + lecture + "\n";

            textArea.append(labelText);
            textArea.append("----------------------------------------------------");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==buttonSubmit){
            try {
                String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

                JSONArray arr = new JSONArray(jsonContent);

                int studentId = Integer.parseInt(textStudentId.getText());
                String studentName = textStudentName.getText();
                String studentSurname = textStudentSurname.getText();
                int studentSection = Integer.parseInt(textStudentTerm.getText());
                String studentLecture = String.valueOf(comboBoxLectures.getSelectedItem());

                JSONObject obj = new JSONObject();
                obj.put("student_id", studentId);
                obj.put("student_name", studentName);
                obj.put("student_surname", studentSurname);
                obj.put("student_section", studentSection);
                JSONArray lectureArr = new JSONArray();
                lectureArr.put(studentLecture);
                obj.put("student_lecture", lectureArr);

                arr.put(obj);

                // TODO arranging file at every write, that might be an optimization problem
                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    fileWriter.write(arr.toString(2)); // using 2 for indentation
                }
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading/writing JSON file", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid values for Student Id.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource()==buttonRefresh){
            new StudentFrame();
            StudentFrame.this.dispose();
        }

        if (e.getSource()==buttonReturn){
            StudentFrame.this.dispose();
            new MainFrame();
        }
    }
}

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.json.*;


public class LectureFrame extends JFrame implements ActionListener {
    JButton buttonSubmit = new JButton();
    JButton buttonReturn = new JButton();
    JLabel labelLectureList = new JLabel();
    JTextArea textArea = new JTextArea();
    private JTextField textLectureCode;
    private JTextField textLectureName;
    private JTextField textLectureTerm;
    private JComboBox comboBoxTeacher;
    private JSONArray jsonArray;
    private DefaultListCellRenderer listRenderer;
    JButton buttonRefresh = new JButton();
    String filePath = "lectures.json";
    String filePathToTeacher = "teachers.json";
    String jsonContent = readJsonFile(filePath);
    String jsonContentTeacher = readJsonFile(filePathToTeacher);

    LectureFrame(){
        this.setTitle("LECTURE");

        this.setLayout(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(120,120,120));
        this.setVisible(true);

        JLabel labelForm = new JLabel();
        JLabel labelHeader = new JLabel();
        JLabel labelCode = new JLabel();
        JLabel labelName = new JLabel();
        JLabel labelTerm = new JLabel();
        JLabel labelTeacher = new JLabel();

        setupLabel(labelForm, "Lecture Form", 0, -50, 800, 50);
        setupLabel(labelHeader, "Create Lecture", 0, 0, 400, 50);
        setupLabel(labelCode, "Code", 0, 50, 100, 50);
        setupLabel(labelName, "Name", 0, 100, 100, 50);
        setupLabel(labelTerm, "Term", 0, 150, 100, 50);
        setupLabel(labelTeacher, "Teacher", 0, 200, 100, 50);


        textLectureCode = new JTextField();
        textLectureName = new JTextField();
        textLectureTerm = new JTextField();

        setupText(textLectureCode,100, 50, 300, 50);
        setupText(textLectureName,100,100, 300, 50);
        setupText(textLectureTerm,100, 150, 300, 50);

        comboBoxTeacher = new JComboBox();
        jsonArray = new JSONArray(jsonContentTeacher);
        TeacherComboBox(jsonArray);
        comboBoxTeacher.setBounds(100,250,300,50);
        comboBoxTeacher.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        comboBoxTeacher.setRenderer(listRenderer);
        this.add(comboBoxTeacher);


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
            labelLectureList.setText("Error reading lectures.json");
        }


        JLabel labelLectures = new JLabel();
        setupLabel(labelLectures, "List of Lectures", 400, 100, 350, 50);
        buttonRefresh = new JButton();
        String s = new String(Character.toChars(8634));
        setupButton(buttonRefresh, s, 750, 150, 50, 50);


        //JPanel, "textArea" defined as global
        textArea.setPreferredSize(new Dimension(0,2000));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setAutoscrolls(true);
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
        textArea.setBorder(BorderFactory.createCompoundBorder(
        textArea.getBorder(), BorderFactory.createEmptyBorder(-15, 8, 8, 8)));


        JScrollPane scrollFrame = new JScrollPane(textArea);
        scrollFrame.setBounds(400, 200, 400, 550);
        this.add(scrollFrame);


        // button objects must be created as global
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

    private void setupText(JTextField textField, int x, int y, int width, int height) {
        textField.setBounds(x, 50 + y, width, height);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        this.add(textField);
    }

    private void clearText(JTextField textField){
        textField.setText("");
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

    // TODO COMBOBOX OFFER PREDEFINED TEACHERS, CREATE NEW TEXT AREA TO ADD NEW TEACHER
    private void TeacherComboBox(JSONArray jsonArray) {
        // Extract unique teacher names from the JSON array
        List<String> teacherList = new ArrayList<>();
        String empty = "";
        teacherList.add(empty);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject lecture = jsonArray.getJSONObject(i);
            String teacherName = lecture.getString("teacher_name");
            String teacherSurname = lecture.getString("teacher_surname");
            if (!teacherList.contains(teacherName)) {
                teacherList.add((teacherName) + " " + (teacherSurname));
            }
        }
        // Populate the ComboBox with teacher names
        for (String teacher : teacherList) {
            comboBoxTeacher.addItem(teacher);
        }
    }

    private void updateLabel(JSONArray jsonArray){


        for (int i=0; i < jsonArray.length(); i++){

            JSONObject lecture = jsonArray.getJSONObject(i);

            String labelText = "\nLecture - " + (i+1) + "\n\n" +
                    "  Code:\t" + lecture.getInt("lecture_code") + "\n" +
                    "  Name:\t" + lecture.getString("lecture_name") + "\n" +
                    "  Term:\t" + lecture.getInt("lecture_term") + "\n" +
                    "  Teacher:\t" + lecture.getString("lecture_teacher") + "\n";

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

                int lectureId = Integer.parseInt(textLectureCode.getText());
                String lectureName = textLectureName.getText();
                int lectureTerm = Integer.parseInt(textLectureTerm.getText());
                String lectureTeacher = String.valueOf(comboBoxTeacher.getSelectedItem());

                JSONObject obj = new JSONObject();
                obj.put("lecture_code", lectureId);
                obj.put("lecture_name", lectureName);
                obj.put("lecture_term", lectureTerm);
                obj.put("lecture_teacher", lectureTeacher);

                arr.put(obj);

                // TODO rewrites whole file at every action, might be a problem with big data
                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    fileWriter.write(arr.toString(2)); // Using 2 for indentation
                }
                JOptionPane.showMessageDialog(this, "Lecture added successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading/writing JSON file", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid values for Lecture Code and Lecture Term.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            clearText(textLectureCode);
            clearText(textLectureName);
            clearText(textLectureTerm);
        }

        if (e.getSource()==buttonRefresh){
            new LectureFrame();
            LectureFrame.this.dispose();
        }

        if (e.getSource()==buttonReturn){
            LectureFrame.this.dispose();
            new MainFrame();
        }
    }
}

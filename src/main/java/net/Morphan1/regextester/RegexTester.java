package net.Morphan1.regextester;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

public class RegexTester extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegexTester frame = new RegexTester();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public RegexTester() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JLabel lblRegexToTest = new JLabel("RegEx to test:");
        lblRegexToTest.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        
        JScrollPane scrollPane = new JScrollPane();
        
        final JCheckBox chckbxCaseInsensitive = new JCheckBox("Case insensitive");
        chckbxCaseInsensitive.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        
        final JCheckBox chckbxShowGroupMatches = new JCheckBox("Show group matches");
        chckbxShowGroupMatches.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        
        JLabel lblStringToTest = new JLabel("String to test against:");
        lblStringToTest.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        
        JScrollPane scrollPane_1 = new JScrollPane();
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        
        final JTextArea textArea_2 = new JTextArea();
        textArea_2.setEditable(false);
        tabbedPane.addTab("Matches", null, textArea_2, null);
        
        final JTextArea textArea_3 = new JTextArea();
        textArea_3.setEditable(false);
        tabbedPane.addTab("Errors", null, textArea_3, null);
        
        final JTextArea textArea_1 = new JTextArea();
        scrollPane_1.setViewportView(textArea_1);
        
        final JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        
        JButton btnNewButton = new JButton("Test");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (textArea.getText().isEmpty())
                    JOptionPane.showMessageDialog(RegexTester.this, "No RegEx specified!");
                else if (textArea_1.getText().isEmpty())
                    JOptionPane.showMessageDialog(RegexTester.this, "No test string given!");
                else {
                    try {
                        Pattern p = (chckbxCaseInsensitive.isSelected()
                                ? Pattern.compile(textArea.getText(), Pattern.CASE_INSENSITIVE)
                                : Pattern.compile(textArea.getText()));
                        Matcher m = p.matcher(textArea_1.getText());
                        String matches = "";
                        while (m.find()) {
                            int f = (chckbxShowGroupMatches.isSelected()
                                        ? m.groupCount() : 0);
                            for (int i = 0; i <= f; i++) {
                                matches += m.group(i) + "\n";
                            }
                        }
                        if (matches.equals("")) {
                            JOptionPane.showMessageDialog(RegexTester.this, "No matches found.");
                        }
                        textArea_2.setText(matches);
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(RegexTester.this, "Error detected. Please check Error tab.");
                        textArea_3.setText(e.toString());
                    }
                }
            }
        });
        btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(lblRegexToTest)
                            .addGap(18)
                            .addComponent(chckbxCaseInsensitive)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(chckbxShowGroupMatches))
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                        .addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                        .addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                        .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                        .addComponent(lblStringToTest))
                    .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblRegexToTest, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                        .addComponent(chckbxCaseInsensitive)
                        .addComponent(chckbxShowGroupMatches, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblStringToTest)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addComponent(btnNewButton)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
    }
}

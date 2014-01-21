package net.Morphan1.regextester;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

// Interact event imports
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Swing imports -- Layouts first
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

// RegEx imports
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexTester extends JFrame {

    private static final long serialVersionUID = 1L;

    // Keep all fonts here to avoid constructing a bunch of pointless objects
    private static final Font defaultFont = new Font("Comic Sans MS", Font.PLAIN, 18);
    private static final Font smallFont = new Font("Comic Sans MS", Font.PLAIN, 13);

    // Main content pane to store all the components
    private final JPanel contentPane = new JPanel();

    // These should be pretty self-explanatory
    private final JTextArea testRegex = new JTextArea();
    private final JTextArea testString = new JTextArea();

    // Bottom tabs -- most likely going to remove
    private final JTextArea matchesTab = new JTextArea();
    private final JTextArea errorTab = new JTextArea();

    // Main application launcher
    public static void main(String[] args) {
        // Start RegexTester after any random processes that probably won't exist(?)
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

    // Create the frame
    public RegexTester() {

        // Set properties of the RegexTester window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);

        // Set properties of the content pane
        contentPane.setBorder(new javax.swing.border.EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Label for the testRegex box
        JLabel lblRegexToTest = new JLabel("RegEx to test:");
        lblRegexToTest.setFont(defaultFont);

        // Checkboxes for optional parameters
        final JCheckBox chckbxCaseInsensitive = new JCheckBox("Case insensitive");
        chckbxCaseInsensitive.setFont(smallFont);
        final JCheckBox chckbxShowGroupMatches = new JCheckBox("Show group matches");
        chckbxShowGroupMatches.setFont(smallFont);

        // Label for the testString box
        JLabel lblStringToTest = new JLabel("String to test against:");
        lblStringToTest.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));

        // Create highlighter for RegEx errors
        final Highlighter hlite = new DefaultHighlighter();
        final Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);

        // Add key listener to testString
        testString.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                try {
                    Pattern p = (chckbxCaseInsensitive.isSelected()
                            ? Pattern.compile(testRegex.getText(), Pattern.CASE_INSENSITIVE)
                            : Pattern.compile(testRegex.getText()));
                    Matcher m = p.matcher(testString.getText());
                    String matches = "";
                    while (m.find()) {
                        int f = (chckbxShowGroupMatches.isSelected()
                                ? m.groupCount() : 0);
                        for (int i = 0; i <= f; i++) {
                            matches += m.group(i) + "\n";
                        }
                    }
                    matchesTab.setText(matches);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(RegexTester.this, "Error detected. Please check Error tab.");
                    errorTab.setText(e.toString());
                }
            }
        });

        // Put testString into a scroll pane
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setViewportView(testString);

        // Add key listener to testRegex
        testRegex.addKeyListener(new KeyAdapter() {
            int start = 0;
            Object highlighting = null;
            @Override
            public void keyReleased(KeyEvent event) {
                try {
                    Pattern p = (chckbxCaseInsensitive.isSelected()
                            ? Pattern.compile(testRegex.getText(), Pattern.CASE_INSENSITIVE)
                            : Pattern.compile(testRegex.getText()));
                    Matcher m = p.matcher(testString.getText());
                    String matches = "";
                    while (m.find()) {
                        int f = (chckbxShowGroupMatches.isSelected()
                                ? m.groupCount() : 0);
                        for (int i = 0; i <= f; i++) {
                            matches += m.group(i) + "\n";
                        }
                    }
                    matchesTab.setText(matches);
                    if (highlighting != null) hlite.changeHighlight(highlighting, 0, 0);
                    start = 0;
                } catch (PatternSyntaxException e) {
                    try {
                        if (testRegex.getCaretPosition() < start) {
                            if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) start--;
                            else if (event.getKeyChar() != KeyEvent.CHAR_UNDEFINED) start++;
                        }
                        if (highlighting == null)
                            highlighting = hlite.addHighlight(start = e.getIndex(), e.getIndex()+1, painter);
                        else
                            hlite.changeHighlight(highlighting, start==0?start=e.getIndex():start, e.getIndex()+1);
                    } catch (Exception ex) {
                        // This should never happen
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(RegexTester.this, "Error detected. Please check Error tab.");
                    errorTab.setText(e.toString());
                }
            }
        });

        // Put testRegex into a scroll pane
        testRegex.setHighlighter(hlite);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(testRegex);

        // Add tabs to a tabbedPane
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        matchesTab.setEditable(false);
        tabbedPane.addTab("Matches", null, matchesTab, null);
        errorTab.setEditable(false);
        tabbedPane.addTab("Errors", null, errorTab, null);

        // Create button and give it a click listener
        JButton btnNewButton = new JButton("Test");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (testRegex.getText().isEmpty())
                    JOptionPane.showMessageDialog(RegexTester.this, "No RegEx specified!");
                else if (testString.getText().isEmpty())
                    JOptionPane.showMessageDialog(RegexTester.this, "No test string given!");
                else {
                    try {
                        Pattern p = (chckbxCaseInsensitive.isSelected()
                                ? Pattern.compile(testRegex.getText(), Pattern.CASE_INSENSITIVE)
                                : Pattern.compile(testRegex.getText()));
                        Matcher m = p.matcher(testString.getText());
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
                        matchesTab.setText(matches);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(RegexTester.this, "Error detected. Please check Error tab.");
                        errorTab.setText(e.toString());
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

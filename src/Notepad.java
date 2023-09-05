import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.*;

public class Notepad extends JFrame implements ActionListener {

    private JTextArea area;
    private JScrollPane scpane;
    String text = "";

    public Notepad() {

        super("Notepad");
        setSize(1950, 1050);

        setLayout(new BorderLayout());

        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("icons/notepad.png"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);

        JMenuBar menuBar = new JMenuBar(); // Create a menu bar

        JMenu file = new JMenu("File"); // Create a "File" menu

        JMenuItem newdoc = new JMenuItem("New"); // Create a "New" menu item
        // Define a keyboard shortcut (Ctrl + N)
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newdoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open"); // Create an "Open" menu item
        // Define a keyboard shortcut (Ctrl + O)
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save"); // Create a "Save" menu item
        // Define a keyboard shortcut (Ctrl + S)
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print"); // Create a "Print" menu item
        // Define a keyboard shortcut (Ctrl + P)
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit"); // Create an "Exit" menu item
        // Define a keyboard shortcut (Esc)
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);

        JMenu edit = new JMenu("Edit"); // Create an "Edit" menu

        JMenuItem copy = new JMenuItem("Copy"); // Create a "Copy" menu item
        // Define a keyboard shortcut (Ctrl + C)
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste"); // Create a "Paste" menu item
        // Define a keyboard shortcut (Ctrl + V)
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut"); // Create a "Cut" menu item
        // Define a keyboard shortcut (Ctrl + X)
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem selectall = new JMenuItem("Select All"); // Create a "Select All" menu item
        // Define a keyboard shortcut (Ctrl + A)
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectall.addActionListener(this);

        JMenu about = new JMenu("Help"); // Create a "Help" menu

        JMenuItem notepad = new JMenuItem("About Notepad"); // Create an "About Notepad" menu item
        notepad.addActionListener(this);

        area = new JTextArea(); // Create a text area
        JTextArea area = new JTextArea();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(10, 15, 0, 0));

        scpane = new JScrollPane(area); // Create a scroll pane for the text area
        scpane.setBorder(BorderFactory.createEmptyBorder());

        setJMenuBar(menuBar); // Set the menu bar
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(about);

        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectall);

        about.add(notepad);

        add(scpane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("New")) {
            area.setText(""); // Clear the text area

        } else if (ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser("D:/Java"); // Create a file chooser dialog
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
            chooser.addChoosableFileFilter(restrict);

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                try {
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    area.read(br, null);
                    br.close();
                    area.requestFocus();
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        } else if (ae.getActionCommand().equals("Save")) {
            final JFileChooser SaveAs = new JFileChooser(); // Create a file chooser for saving
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".txt"); // Add .txt extension
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("Print")) {
            try {
                area.print(); // Print the contents of the text area
            } catch (Exception e) {
            }
        } else if (ae.getActionCommand().equals("Exit")) {
            System.exit(0); // Exit the application
        } else if (ae.getActionCommand().equals("Copy")) {
            text = area.getSelectedText(); // Copy selected text to the clipboard
        } else if (ae.getActionCommand().equals("Paste")) {
            area.insert(text, area.getCaretPosition()); // Paste text from the clipboard
        } else if (ae.getActionCommand().equals("Cut")) {
            text = area.getSelectedText(); // Copy selected text to the clipboard
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd()); // Delete the selected text
        } else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll(); // Select all text in the text area
        } else if (ae.getActionCommand().equals("About Notepad")) {
            new About().setVisible(true); // Show the "About Notepad" dialog

        }
    }

    public static void main(String[] args) {
        new Notepad(); // Create an instance of the Notepad class
    }
}

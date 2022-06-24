import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class CBForm extends JFrame{
    private JPanel cbPanel;
    private JTextField tfLocation;
    private JButton bSelector;
    private JScrollPane filePane;
    private JPanel cb2Pane;
    private JTextPane tpFilePath;
    private JButton bOK;
    private JButton bCancel;
    private ArrayList<String> fNames = new ArrayList<>();
    private ArrayList<String> folderNames = new ArrayList<>();
    private ArrayList<String> directories = new ArrayList<>();
    static String fPath;
    StyledDocument doc = tpFilePath.getStyledDocument();
    final JFileChooser fc = new JFileChooser();
    JFrame form = new JFrame();

    public CBForm(){
        //Set Up
        form.setContentPane(cbPanel);
        form.setTitle("Clean Bot");
        form.setSize(300,70);
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.setVisible(true);}//End Constructor temp

    //Action listeners
    class directoryListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fc.showOpenDialog(form);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = new File(fc.getSelectedFile().toString());
                fPath = String.valueOf(file);
                tfLocation.setText(fPath);
                CleanBot.getFileNames(fPath, fNames);
                CleanBot.getFileExt(fNames,folderNames);
                form.setSize(300, 300);
                setFolderList();
            }else{tfLocation.setText("No Directory was selected");}//End else

        }
    }//end directory
    class okListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> successList = new ArrayList<>();
            ArrayList<String> failureList = new ArrayList<>();


            if(folderNames.isEmpty()){tpFilePath.setText("No folders will be created");
            }
            else{tpFilePath.setText("Folders to be created");
                addListToDocument(folderNames,doc);
                CleanBot.createFolder(folderNames,fPath);
                addMessageToDocument("List of folders in directory",doc);
                CleanBot.getDirectories(directories,fPath);
            }//End else
            try {
                CleanBot.moveFiles(fPath,folderNames,successList,failureList);
                CleanBot.moveFiles(fPath,directories,successList,failureList);
            } catch (IOException ex) {
                ex.printStackTrace();
            }//End Try Catch
            addMessageToDocument("Files moved successfully",doc);
            addListToDocument(successList,doc);
            addMessageToDocument("Files moved unsuccessfully",doc);
            addListToDocument(failureList,doc);

        }
    }// End Ok Listener
    class cancelListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            tpFilePath.setText("  ");
            tfLocation.setText("  ");
        }
    }//End cancel listener

    //Methods
    private void setFolderList(){
            try{
                for(String s:folderNames){
                    doc.insertString(doc.getLength(), "\n" + s,null);
                    for(String t:fNames){
                        if(s.equals(CleanBot.whatIsExtension(t))){
                            doc.insertString(doc.getLength(),"\n" +"  - " +t,null);
                        }//end if statement
                    }//end inner for
                }//end for
            }catch (BadLocationException ex){ex.printStackTrace();}
    }//End method
    protected void setbOK(){
        bOK.addActionListener(new okListener());
    }//End method
    protected void setFileChooser(){bSelector.addActionListener(new directoryListener());}//End method
    protected void setCancel(){bCancel.addActionListener(new cancelListener());}//end method
    private static void addListToDocument(ArrayList<String> listToBeAdded,StyledDocument doc){
        for(String s:listToBeAdded){
            try{doc.insertString(doc.getLength(),"\n" +"  - "+s,null);
            }catch (BadLocationException ex){ex.printStackTrace();}
        }
    }//End method
    private static void addMessageToDocument(String messageToBeAdded,StyledDocument doc){
        try{doc.insertString(doc.getLength(), "\n" + messageToBeAdded,null);
        }catch (BadLocationException ex){ex.printStackTrace();}
    }//End method

}

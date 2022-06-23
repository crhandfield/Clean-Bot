import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
public class CleanBot {
	protected static void getFileNames(String filePath,ArrayList<String> fNames){
		File directory = new File(filePath);
		File[] filesArray = directory.listFiles();
		Arrays.sort(filesArray);
		for (File file : filesArray) {
			if (file.isFile()) {
				String f = file.getName();
				if(!fNames.contains(f)){fNames.add(f);}//End if 2 scope
			} //End if 1 scope
		}//End Loop Scope
	}//End Method
	protected static void getDirectories(ArrayList<String> directories, String filePath){
		File directory = new File(filePath);
		File[] filesArray = directory.listFiles();
		Arrays.sort(filesArray);
		for(File file:filesArray){
			if(file.isDirectory()){
				String f = file.getName();
				if(!directories.contains(f)){directories.add(f);}//End second if
			}//End if
		}
	}//end method to get directories
	protected static String whatIsExtension(String fileName) {
		String fN = fileName.substring(fileName.lastIndexOf(".")+1);
		return fN;
	}//End Method IS COMPLETE
	protected static void getFileExt(ArrayList<String> fileList,ArrayList<String> folderList){
		for(String s: fileList){
			String extension = whatIsExtension(s);
			if(!folderList.contains(extension)){folderList.add(extension);}//end if scope
		}
	}//End method
	protected static void createFolder(ArrayList<String> folderList, String filePath) {
		for(String f: folderList){
			File f1 = new File(filePath + "\\\\" + f);
			boolean bool = f1.mkdir();
		}
	}//End of Method Scope Completed
	protected static void moveFiles(String filePath, ArrayList<String> fileExtensions,ArrayList<String> successList,ArrayList<String> failureList) throws IOException {
		File directory = new File(filePath);
		File[] filesArray = directory.listFiles();
		Arrays.sort(filesArray);
		for (File file : filesArray) {
			if (file.isFile()) {
				String fn = whatIsExtension(file.getName());
				for(String f:fileExtensions) {
					if(f.equals(fn)) {
						Path temp = Files.move(Paths.get(filePath +"\\\\"+ file.getName()), Paths.get(filePath + "\\\\" + f +"\\\\" + file.getName()));
						if (temp != null) {
							successList.add(file.getName());//Adds to the success list to print out
						} else {
							failureList.add(file.getName());//Adds to the failure list
						}//end if 3 scope
					}//End of If 2 Scope
				}//End for 2 scope
			}//End if 1 Scope
		}// End for 1 scope
	}//End method Scope tested in another file

	
	
	public static void main(String[] args) throws IOException {
		CBForm form = new CBForm();
		form.setFileChooser();
		form.setbOK();
		form.setCancel();
	}//For Executing main code
}

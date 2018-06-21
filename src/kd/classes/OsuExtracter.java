package kd.classes;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import kd.classes.graphics.OsuExtracterGui;

public class OsuExtracter {
	
	public static void main(String[] args) {
		new OsuExtracter();
	}
	
	public OsuExtracter() {
		
		 try {
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		
			new OsuExtracterGui(findOsu());
	
		
		/*try {
			FileManager.getInstance().ExtractSongs("C:\\Users\\K_D\\AppData\\Local\\osu!\\Songs", "C:\\Users\\K_D\\Music");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	private String findOsu() {
	    String dir = System.getenv("LOCALAPPDATA");
	    
	    Path p = Paths.get(dir+"\\osu!");
	    if(Files.exists(p)){
	    	return p.toString();
	    }
	    return null;
		
	}

}

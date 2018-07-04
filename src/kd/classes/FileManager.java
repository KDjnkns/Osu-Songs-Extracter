package kd.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileManager {
	
	private static FileManager instance = new FileManager();
	
	private FileManager() {};

	public static FileManager getInstance() {
		return instance;
	}
	
	public void ExtractSongs(String osupath, String userpath, boolean rewrite) throws FileNotFoundException {
		
		Thread output = new Thread("Output") {
			
			public void run() {
				System.out.println("Rewrite files: " + rewrite);
				if(osupath == null) {
					System.out.println("Osu! path is not correct!");
					System.out.println("If you believe, that this is an error, send us a log (More in the 'Info')");
					return;
				}
				
				if(userpath == null) {
					System.out.println("Music path is not correct!");
					System.out.println("If you believe, that this is an error, send us a log (More in the 'Info')");
					return;
				}
				
				File osufolder = new File(osupath);
				File musicfolder = new File(userpath);
				
				if(!osufolder.exists()) {
					System.out.println("Osu! folder does not exists!");
					System.out.println("If you believe, that this is an error, send us a log (More in the 'Info')");
					return;
				}
				if(!musicfolder.exists()) {
					System.out.println("Music folder does not exists!");
					System.out.println("If you believe, that this is an error, send us a log (More in the 'Info')");
					return;
				}
				
				if(!(osufolder.isDirectory()) || !(musicfolder.isDirectory())) {
					System.out.println("Paths are not correct!");
					System.out.println("If you believe, that this is an error, send us a log (More in the 'Info')");
					return;
				}
				File bms;
				File[] folders = osufolder.listFiles();
				
				for(File f : folders) {
					
					File[] songfiles = f.listFiles(new FilenameFilter() {
					    public boolean accept(File dir, String name) {
					        return name.endsWith("mp3");
					    }
					});
					
					System.out.println("Folder: " + f.getName());
					
					String musicname = properName(f.getName().split(" "));
					
					if(songfiles.length == 0) {
						System.out.println("  No music files");
						continue;
					}
					
					bms = getTheBiggestOne(songfiles);
					System.out.println("  Music file: " + bms.getName());
					
					try {
						copyFile(bms, new File(musicfolder.getAbsolutePath() + "\\" + musicname + ".mp3"), rewrite);
					} catch (IOException e) {
						e.printStackTrace();
					}
						
				}
				System.out.println("Files were copied!");
			}
		};
		output.start();
	}
	
	private File getTheBiggestOne(File[] files) {
		File ms = files[0];
		for(int i = 1;i < files.length; i++) {
			if(ms.length() < files[i].length()) {
				ms = files[i];
			}	
		}
		return ms;
	}
	
	private void copyFile(File source, File dest, boolean rewrite) throws IOException {
		if(dest.exists() && rewrite == false) {
			System.out.println("Not copied: " + dest);
			return;
		}
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	    System.out.println("Copied: " + dest);
	}
	
	private String properName(String[] names) {
		String name = "";
		int i = 1;
		while(i != names.length-1) {
			name += " " + names[i];
			i++;
		}
		name += names[i];
			
		return name;
	}
	
	
}

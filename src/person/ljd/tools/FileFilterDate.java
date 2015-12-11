package person.ljd.tools;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

public class FileFilterDate implements FileFilter{
	Date date_input = null;
	
	public FileFilterDate(Date date_input) {
		this.date_input = date_input;
	}

	@Override
	public boolean accept(File f) {
		if(f.getName().startsWith(".")){
			return false;
		}
		if(f.isDirectory() && f.getName().equalsIgnoreCase("classes")){
			return false;
		}
		if(f.isDirectory() && f.getName().equalsIgnoreCase("templates_c")){
			return false;
		}
		//目录
		if(f.isDirectory()){
			return true;
		}
		//文件
		if(f.getName().endsWith(".class")){
			return false;
		}
		if(f.getName().endsWith(".bak")){
			return false;
		}
		if(f.lastModified()>date_input.getTime()){
			return true;
		}else{
			return false;
		}
	}
}

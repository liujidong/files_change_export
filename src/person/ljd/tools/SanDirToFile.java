package person.ljd.tools;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Date;
public class SanDirToFile 
{
	//list 是一个递归方法，列出每个目录下的文件名
	public static void list(String path, String file,BufferedWriter buf,Date date_input){
		String fullname = path + File.separator + file;
		//当前目录
		File current = new File(fullname);
		if (!current.exists()) {
			System.out.print(file + " not exists.");
			return;
		}
		//获取当前目录下文件和子目录列表
		File[] sons = current.listFiles(new FileFilterDate(date_input));
		for (int i = 0; i < sons.length; i++) {
			File f = new File(fullname + File.separator + sons[i].getName());
			if (f.isDirectory()) {	//如果是子目录成员
				//递归调用，进入子目录
				list(fullname, sons[i].getName(),buf,date_input);
			} else {
				try {
					print(sons[i].getAbsolutePath(), buf);
				} catch (Exception e) {
					System.out.println("文件写入出错！");
					e.printStackTrace();
				}
			}
		}
	}
	public static void print(String abPath, BufferedWriter buf) throws Exception {
		buf.write(abPath);
		buf.newLine();
	}
	}

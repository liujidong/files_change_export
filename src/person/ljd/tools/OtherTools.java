package person.ljd.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class OtherTools {
	/**
	 * 列出src目录(根据日期)，并存储在生成的一个文本文件中
	 * @param srcPath
	 */
	public static void listSrc(String srcPath,String dateStr){
		try {
			String outFileFull = srcPath+"_"+dateStr+"_tree.txt";
			BufferedWriter bufferout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileFull),"UTF-8"));
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yy-MM-ddHHmm");
			Date date_input = sdf2.parse(dateStr);
			
			SanDirToFile.list("", srcPath, bufferout,date_input);
			bufferout.flush();
			bufferout.close();
		} catch (Exception e) {
			System.out.println("err at listSrc");
			e.printStackTrace();
		}
	} 
	/*
	private static boolean isWindows(){
		if(System.getProperty("os.name").indexOf("Windows")>=0){
			return true;
		}else{
			return false;
		}
	}
	*/
	public static void copyDirs(String srcPath,String dateStr) {
		try {
			//读入src的目录文件
			File f = new File(srcPath+"_"+dateStr+"_tree.txt");
			FileReader in = new FileReader(f);
			BufferedReader buffer = new BufferedReader(in);
			
			String pathAbs_srcLine = null;
			String dir_current = new File(srcPath).getName();
			while((pathAbs_srcLine=buffer.readLine())!=null){
				String distFilePath = pathAbs_srcLine.replace(dir_current, dir_current+"_"+dateStr);
				copyFile(pathAbs_srcLine, distFilePath);
			}
			in.close();
			
			//f.delete();	//用完删除
			//输出classes目录文件
			/*
			String outFileFull = f.getAbsolutePath();
			if(isWindows()){
				String cmd = "cmd.exe tree /f "+srcPath+"_"+dateStr+" >>"+outFileFull;
				System.out.println(cmd);
				Runtime runtime = Runtime.getRuntime();
				runtime.exec(cmd);
			}
			*/
			int result = JOptionPane.showConfirmDialog(null, "操作成功，是否查看？","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if (result==JOptionPane.YES_OPTION){
				java.awt.Desktop.getDesktop().open(new File(srcPath).getParentFile());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处理内部类的class
	 * 例如
	 * （MorningMeetingTask.class
	 *   MorningMeetingTask$FileRepeatTaskEnd.class
	 *   MorningMeetingTask$1.class
	 * ）
	 * @param classesTargetPath
	 * @return
	 */
	public static Map<String,String> getClassesTesks(String classesSourcePath,String classesTargetPath){
		File mainFile = new File(classesSourcePath);
		File dir = new File(mainFile.getParent());
		File[] files = dir.listFiles();
		String mainName = mainFile.getName().replace(".class", "");
		Map<String,String> map = new HashMap<String, String>();
		File dirTaget = new File(classesTargetPath).getParentFile();
		for (File file : files) {
			if(file.getName().indexOf(mainName)>=0){
				map.put(file.getAbsolutePath(), dirTaget+File.separator+file.getName());
			}
		}
		return map;
	}

	public static void copyFile(String classesSourcePath,String classesTargetPath) throws Exception{
		FileInputStream input=new FileInputStream(classesSourcePath);
		File tFile = new File(classesTargetPath);
		tFile.getParentFile().mkdirs();
		FileOutputStream output=new FileOutputStream(classesTargetPath);
		int size=input.available();	//檢查輸入的字節數
		System.out.println("可輸入的字節數為："+size);
		for(int i=0;i<size;i++) 
		{
			output.write(input.read());	//拷貝到另一個文件
		}
		input.close();
		output.flush();
		output.close();
	}
}

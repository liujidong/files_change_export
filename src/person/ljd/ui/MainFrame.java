package person.ljd.ui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.qt.datapicker.DatePicker;

import person.ljd.tools.OtherTools;

//import person.ljd.tools.OtherTools;


public class MainFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	//JTextField tx_date = new JTextField(20);
	ObservingTextField tx_date = new ObservingTextField();
	
	JButton btn_date;
	
	JFileChooser chooser;
	
	JTextField tf_src;
	JButton src_but;
	
	JButton btnOk;
	
	JLabel lbl_rs;
	
	JComboBox jh;
	JComboBox jm;
	//布局
	private void createLayout(JPanel p){
		Container c = this.getContentPane();
		this.setTitle("导出改变的文件");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		c.setLayout(new BorderLayout());
		
		//p.setBorder(BorderFactory.createTitledBorder("-"));
		p.setLayout(new GridLayout(5,1));
		p.add(new JLabel("<html><b>根据日期导出相应目录下改变的文件</b><br/>"),0);
		
		c.add(p,BorderLayout.CENTER);
		
		StringBuffer note = new StringBuffer();
		note.append("by 刘继东2014-11-23<br>");
		note.append("liujidong2010@gmail.com<br>");
		JLabel bottom = new JLabel("<html>"+note.toString()+"</html>");
		bottom.setBorder(BorderFactory.createLoweredBevelBorder());
		c.add(bottom,BorderLayout.SOUTH);
		
		chooser = new JFileChooser(".");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	//添加控件
	private void createComponent(JPanel p){
		JPanel tr1 = new JPanel();
			tr1.add(new JLabel("日期:"));
			tx_date.setBounds(140, 230,  110, 23);
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			Date date = new Date();
			tx_date.setText(sdf.format(date));
			tr1.add(tx_date);
			jh = new JComboBox();
			for (int i = 0; i <= 23; i++) {
				jh.addItem(i);
				if(date.getHours()==i){
					jh.setSelectedItem(i);
				}
			}
			//jh.setSelectedItem(anObject);
			jm = new JComboBox();
			for (int i = 0; i <= 59; i++) {
				jm.addItem(i);
				if(date.getMinutes()==i){
					jm.setSelectedItem(i);
				}
			}
			tr1.add(jh);
			tr1.add(jm);
			//tr1.add(new JLabel("<html><font color='red'>(yyyy-MM-dd)</font></html>"));
			//tr1.add(new JLabel("----------------------------->当前时间"));
		btn_date = new JButton("选择");
		btn_date.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DatePicker dp = new DatePicker(tx_date, Locale.CHINA);
                    // previously selected date
                    Date selectedDate = dp.parseDate(tx_date.getText());
                    dp.setSelectedDate(selectedDate);
                    dp.start(tx_date);
                };
       });  
		tr1.add(btn_date);
		p.add(tr1,1);
		JPanel tr2 = new JPanel();
			JLabel src_lbl = new JLabel("目录:");
			tr2.add(src_lbl);
			tf_src = new JTextField(30);
			tr2.add(tf_src);
			src_but = new JButton("选择");
			tr2.add(src_but);
		p.add(tr2,2);
		
		btnOk = new JButton("确定");	
		//btnOk.setEnabled(false);
		p.add(btnOk,3);
		
		lbl_rs = new JLabel();
		p.add(lbl_rs,4);
		
		pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width-this.getWidth())/2, (d.height-this.getHeight())/2);
	}
	//增加事件
	private void addFuncs(){
		src_but.addActionListener(this);
		btnOk.addActionListener(this);
	}
	
	public MainFrame(){
		JPanel p = new JPanel();
		createLayout(p);
		createComponent(p);
		addFuncs();
	}
	public void actionPerformed(ActionEvent e) {
		Object source  = e.getSource();
		String info = "";
		if(source == src_but){
			int result = chooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				tf_src.setText(chooser.getSelectedFile().toString());
			}
			//info= check1(result);
		}else if(source == btnOk){
			if(!"".equals(tx_date.getText()) && !"".equals(tf_src.getText())){
				String dateStr = tx_date.getText()+jh.getSelectedItem()+jm.getSelectedItem();
				OtherTools.listSrc(tf_src.getText(),dateStr);
				OtherTools.copyDirs(tf_src.getText(), tx_date.getText());
				info = "<html><font color='green'>ok！查看路径"+tf_src.getText()+"_"+tx_date.getText()+"！</font>";
			}else{
				info = "<html><font color='red'>请输入日期和项目路径！</font>";
			}
		}
		lbl_rs.setText(info);
	}
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
		//System.out.println(System.getProperty("os.name"));
	}

}

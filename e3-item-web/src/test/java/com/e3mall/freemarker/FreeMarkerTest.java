package com.e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {
	
	@Test
	public void testFreeMarker() throws Exception {
		//创建一个模板文件
		//创建一个Configuration对象  
		Configuration configuration = new Configuration(Configuration.getVersion());
		//设置模板文件保存的目录
		configuration.setDirectoryForTemplateLoading(new File("E:\\maven-workspace\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		//设置模板文件的编码格式  一般是utf-8 
		configuration.setDefaultEncoding("utf-8");
		//加载一个模板文件  创建一个模板对象
		//Template template = configuration.getTemplate("hello.ftl");//括号中填入模板名字  表示加载一个模板 
		Template template = configuration.getTemplate("student.ftl");
		//创建一个数据集  可以是pojo 也可以是map  一般用map
		Map data = new HashMap();
		
		//创建测试pojo对象
		Student student = new Student(1, "李华", 18, "尹集乡");
		
		//添加list对象
		List<Student> list = new ArrayList<Student>();
		list.add(new Student(1, "李华", 18, "尹集乡"));
		list.add(new Student(2, "小明", 18, "尹集乡"));
		list.add(new Student(3, "小花", 18, "尹集乡"));
		list.add(new Student(4, "小月", 18, "尹集乡"));
		
		//添加一个日期类型
		data.put("date", new Date());
		//添加一个空值
		data.put("val", null);
		data.put("stuList", list);
		data.put("student", student);
		data.put("hello", "hello  freeMerker!");
		//创建一个Writer 指定输出路径及文件名
//		Writer out = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\TestJavaFile\\freemarker\\hello.txt"));
		Writer out = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\TestJavaFile\\freemarker\\student.html"));
		//生成静态页面
		template.process(data, out);
		//关闭流
		out.close();
		
	}
	
	
}

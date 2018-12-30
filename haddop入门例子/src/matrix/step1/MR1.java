package matrix.step1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import java.io.IOException;

/**
 * @author hyc
 * 思路：
 * 1.将整个右矩阵载入分布式缓存
 * 2.将左矩阵的行作为Map的输入
 * 3.在Map执行之前将缓存的右矩阵以行为单位放入List
 * 4.在Map计算时从List中取出所有行分别与输入行相乘
 */


//input file
//matrix1.txt
//1	1_1,2_2,3_-2,4_0
//2	1_3,2_3,3_4,4_-3
//3	1_-2,2_0,3_2,4_3
//4	1_5,2_3,3_-1,4_2
//5	1_-4,2_2,3_0,4_2
//
//matrix2.txt
//1	1_0,2_3,3_-1,4_2,5_-3
//2	1_1,2_3,3_5,4_-2,5_-1
//3	1_0,2_1,3_4,4_-1,5_2
//4	1_-2,2_2,3_-1,4_1,5_2


//完成矩阵的转置
public class MR1 {
	// 输入相对路径
	private static String inPath = "/user/hyc/matrix/matrix_input/matrix2.txt";
	// 输出相对路径
	private static String outPath = "/user/hyc/matrix/matrix_output1";
	// hdfs地址
	private static String hdfs = "hdfs://localhost:9000";

	public int run() {
		try {
			//创建job配置类
			Configuration conf = new Configuration();
			//设置hdfs的地址
			conf.set("fs.default.name", hdfs);
			//创建一个job实例
			Job job = Job.getInstance(conf, "step1");
			
			//设置job的主类
			job.setJarByClass(MR1.class);
			
			//设置Mapper类和Reducer类
			job.setMapperClass(Mapper1.class);
			job.setReducerClass(Reducer1.class);
			
			//设置Mapper输出的类型
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			
			//设置Reducer输出的类型
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);
			
			FileSystem fs = FileSystem.get(conf);
			//设置输入和输出路径
			Path inputPath = new Path(inPath);
			if (fs.exists(inputPath)) {
				FileInputFormat.addInputPath(job, inputPath);
			}
			Path outputPath = new Path(outPath);
			fs.delete(outputPath,true);
			FileOutputFormat.setOutputPath(job, outputPath);
			
			return job.waitForCompletion(true)?1:-1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		int result = -1;
		result = new MR1().run();
		if (result == 1) {
			System.out.println("step1 运行成功!");
		} else if (result==-1){
			System.out.println("step1 运行失败!");
		}
	}
}
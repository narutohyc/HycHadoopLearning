package matrix.step2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

//完成矩阵相乘
public class MR2 {
	// 将step1输出的转置矩阵作为全局缓存 part-r-00000
	private static String cache = "/user/hyc/matrix/matrix_output1/part-r-00000";
	// 输入相对路径
	private static String inPath = "/user/hyc/matrix/matrix_input/matrix1.txt";
	// 输出相对路径
	private static String outPath = "/user/hyc/matrix/matrix_output2";
	// hdfs地址
	private static String hdfs = "hdfs://localhost:9000";

	public int run() {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.default.name", hdfs);
			Job job = Job.getInstance(conf, "step2");
			
			// 添加分布式缓存文件
			job.addCacheFile(new URI(cache + "#matrix2"));//取別名matrix2
			
			job.setJarByClass(MR2.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			job.setMapperClass(Mapper2.class);
			job.setReducerClass(Reducer2.class);
			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);

			FileSystem fs = FileSystem.get(conf);
			Path inputPath = new Path(inPath);
			if (fs.exists(inputPath)) {
				FileInputFormat.addInputPath(job, inputPath);
			}
			Path outputPath = new Path(outPath);
			fs.delete(outputPath);
			FileOutputFormat.setOutputPath(job, outputPath);

			return job.waitForCompletion(true)?1:-1;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException|InterruptedException|URISyntaxException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = new MR2().run();
		if (result == 1) {
			System.out.println("step2 运行成功");
		} else {
			System.out.println("step2 运行失败");
		}
	}

}
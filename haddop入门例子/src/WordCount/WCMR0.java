package WordCount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WCMR0 {
	// 输入相对路径
	private static String inPath = "/user/hyc/WordCount/input";
	// 输出相对路径
	private static String outPath = "/user/hyc/WordCount/output";
	// hdfs地址
	private static String hdfs = "hdfs://localhost:9000";

	public int run() {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.default.name", hdfs);
			Job job = Job.getInstance(conf, "step1");
			job.setJarByClass(WCMR0.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);

			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			job.setMapperClass(WCMapper.class);
			job.setReducerClass(WCReduce.class);
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

			try {
				if (job.waitForCompletion(true)) {
					return 1;
				} else {
					return -1;
				}
			} catch (InterruptedException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int result = new WCMR0().run();
		if (result == 1) {
			System.out.println("step1 运行成功!");
		} else {
			System.out.println("step1 运行失败!");
		}
	}

}
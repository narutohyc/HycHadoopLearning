package WordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class WCMR1 {
	public static void main(String[] args) throws Exception {
//		hdfs://localhost:9000/user/hyc/input hdfs://localhost:9000/user/hyc/output
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length < 2) {
			System.err.println("Usage: wordcount <in> [<in>...] <out>");
			System.exit(2);
		}
		Job job = Job.getInstance(conf, "word count");
		job.setJar("./bin/WordCount.class"); // 根据自己的目录设置
		job.setMapperClass(WCMapper.class);
		job.setCombinerClass(WCReduce.class);
		job.setReducerClass(WCReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		for (int i = 0; i < otherArgs.length - 1; ++i) {
			FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
		}
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
		job.waitForCompletion(true);
		System.out.println("sucess");
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

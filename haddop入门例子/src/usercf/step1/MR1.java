package usercf.step1;

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
import unitl.HadoopUnitl;

/**
 * 
 * @author hyc
 * 
 * MapReduce步骤（UserCF）
 * 
 * 1.根据用户行为列表构建评分矩阵
 *   输入：用户ID，物品ID，分值
 *   输出：用户ID（行）-物品ID（列）-分值
 *  
 * 2.利用评分矩阵，构建用户与用户的相似度矩阵
 *   输入：步骤1的输出
 *   缓存：步骤1的输出 （输出和缓存是相同的文件）
 *   输出：用户ID（行）-用户ID（列）-相似度
 *   
 * 3.将评分矩阵转置
 *   输入：步骤1的输出
 *   输出：物品ID（行）-用户ID（列）-分值
 * 
 * 4.用户与用户相似度矩阵*评分矩阵
 *   输入：步骤2的输出
 *   缓存：步骤3的输出
 *   输出：用户ID（行）-物品ID（列）-分值
 *   
 * 5.根据评分矩阵，将步骤4的输出中，用户已经有过行为的商品评分置为0
 *   输入：步骤4的输出
 *   缓存：步骤1的输出
 *   输出：用户ID（行）-物品ID（列）-分值（最终的推荐列表）
 */

//生成评分矩阵
public class MR1 {
	// 输入相对路径
	private static String inPath = "/user/hyc/usercf/usercf_input/usercf.txt";
	// 输出相对路径
	private static String outPath = "/user/hyc/usercf/usercf_output1";

	private static String result = outPath + "/part-r-00000";
	// hdfs地址
	private static String hdfs = "hdfs://localhost:9000";

	public int run() {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.default.name", hdfs);
			Job job = Job.getInstance(conf, "step1");

			job.setJarByClass(MR1.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			job.setMapperClass(Mapper1.class);
			job.setReducerClass(Reducer1.class);
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
					HadoopUnitl.cat(conf, result);
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
		int result = new MR1().run();
		if (result == 1) {
			System.out.println("step1 运行成功");
		} else {
			System.out.println("step1 运行失败");
		}
	}
}
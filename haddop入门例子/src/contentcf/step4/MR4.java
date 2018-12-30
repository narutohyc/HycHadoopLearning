package contentcf.step4;
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
import java.net.URI;
import unitl.HadoopUnitl;

public class MR4 {
    //输入相对路径
    private static String inPath = "/user/hyc/contentcf/contentcf_output3";
    //输出相对路径
    private static String outPath = "/user/hyc/contentcf/contentcf_output4";
    //将step1输出的转置矩阵作为全局缓存
    private static String cache = "/user/hyc/contentcf/contentcf_input2/itemuser.txt";
    private static String result = outPath + "/part-r-00000";
    //hdfs地址
    private static String hdfs = "hdfs://localhost:9000";

    public int run () {
        try {
            Configuration conf = new Configuration();
            conf.set("fs.default.name", hdfs);
            Job job = Job.getInstance(conf, "step4");

            job.addCacheFile(new URI(cache + "#ContentCF_itemUserScore2"));
            job.setJarByClass(MR4.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setMapperClass(Mapper4.class);
            job.setReducerClass(Reducer4.class);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int result = new MR4().run();
        if (result == 1) {
            System.out.println("step4 运行成功");
        } else {
            System.out.println("step4 运行失败");
        }
    }
}
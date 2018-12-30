package WordCount;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WCMapper extends Mapper<Object, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	private String wStr;
	private String regEx = "[,.?;!]";
	private Pattern pattern = Pattern.compile(regEx);

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer itr = new StringTokenizer(value.toString());
		while (itr.hasMoreTokens()) {
			Matcher m = pattern.matcher(itr.nextToken());
			wStr = m.replaceAll("");
			if (wStr.length() < 6) {
				continue;
			}
			word.set(wStr);
			context.write(word, one);
		}
	}
}
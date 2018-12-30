package matrix.step1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class Reducer1 extends Reducer<Text, Text, Text, Text> {//<ReducerKey,ReducerValue,OutputKey,OutputValue>
    private Text outKey = new Text();
    private Text outValue = new Text();
    /**
     * key:列号
     * value:[行号_值,行号_值,行号_值,行号_值...]
     */
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for (Text text : values) {
        	//text:行号_值
            sb.append(text + ",");
        }
        //将末尾的","去掉
        String line = null;
        if (sb.toString().endsWith(",")) {
            line = sb.substring(0, sb.length() - 1);
        }
        outKey.set(key);
        outValue.set(line);
        context.write(outKey, outValue);
    }
}
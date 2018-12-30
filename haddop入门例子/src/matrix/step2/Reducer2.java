package matrix.step2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class Reducer2 extends Reducer<Text, Text, Text, Text> {
    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for (Text text : values) {
            sb.append(text + ",");
        }
        String line = null;
        if (sb.toString().endsWith(",")) {
            line = sb.substring(0, sb.length() - 1);
        }
        outKey.set(key);//行
        outValue.set(line);//列_值,列_值,列_值,列_值...
        context.write(outKey, outValue);
    }
}
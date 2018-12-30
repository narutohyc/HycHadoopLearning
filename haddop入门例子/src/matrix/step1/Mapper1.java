package matrix.step1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class Mapper1 extends Mapper<LongWritable, Text, Text, Text> {//<MapperKey,MapperValue,OutputKey,OutputValue>

    private Text outKey = new Text();
    private Text outValue = new Text();
    
    /**
     * key:1
     * value:1 1_0,2_3,3_-1,4_2,5_-3
     */
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {//<MapperKey,MapperValue,context>
        String[] rowAndLine = value.toString().split("\t");

        String row = rowAndLine[0];
        String[] lines = rowAndLine[1].split(",");
        
        //["1_0","2_3","3_-1","4_2","5_-3"]
        for (int i = 0; i < lines.length; i++) {
            String column = lines[i].split("_")[0];
            String valueStr = lines[i].split("_")[1];
            //key:列号 value:行号_值
            outKey.set(column);
            outValue.set(row + "_" + valueStr);
            context.write(outKey, outValue);
        }
    }
}

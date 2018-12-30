package matrix.step2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mapper2 extends Mapper<LongWritable, Text, Text, Text> {
	private Text outKey = new Text();
	private Text outValue = new Text();

	private List<String> cacheList = new ArrayList();
	
	//Mapper类的初始化方法
	//在map方法之前执行且执行一次
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		super.setup(context);
		//通过输入流将全局缓存中的右侧矩阵读入List<String>中
		FileReader fr = new FileReader("matrix2");//别名matrix2，和之前定义的一致
		BufferedReader br = new BufferedReader(fr);
		
		/**
		 * 每行格式
		 * 行 tab 列_值,列_值,列_值,列_值...
		 */		
		String line = null;
		while ((line = br.readLine()) != null) {
			cacheList.add(line);
		}
		fr.close();
		br.close();
	}
	/**
	 * key:行号
	 * value:行 tab 列_值,列_值,列_值,列_值...
	 */
	@Override
	public void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException {
		//行
		String[] rowAndLine = text.toString().split("\t");
		//列_值 (数组)
		String rowMatrix1 = rowAndLine[0];
		String[] columnValues1 = rowAndLine[1].split(",");
		for (String line : cacheList) {
			//右侧矩阵的行 line
			//格式:行 tab 列_值,列_值,列_值,列_值...
			String rowMatrix2 = line.toString().split("\t")[0];
			String[] columnValues2 = line.toString().split("\t")[1].split(",");

			//矩阵两行相乘得到的结果
			int result = 0;
			//遍历左侧矩阵的第一行的每一列
			for (String columnValue : columnValues1) {
				String column1 = columnValue.split("_")[0];
				String value1 = columnValue.split("_")[1];
				//遍历右侧矩阵每一行的每一列
				for (String column2 : columnValues2) {
					//左矩阵的列号需要等于右矩阵的列号
					if (column2.startsWith(column1 + "_")) {
						String value2 = column2.split("_")[1];
						//将两列的值相乘，并累加
						result += Integer.valueOf(value1) * Integer.valueOf(value2);
					}
				}
			}
			//result是结果矩阵的某元素，坐标为 行:rowMatrix1，列:rowMatrix2(因为右侧矩阵已经转置过)
			outKey.set(rowMatrix1);
			outValue.set(rowMatrix2 + "_" + result);
			//輸出格式 key:行  value:列_值
			context.write(outKey, outValue);
		}
	}
}

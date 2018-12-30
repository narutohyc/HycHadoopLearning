package usercf.job;

import usercf.step1.MR1;
import usercf.step2.MR2;
import usercf.step3.MR3;
import usercf.step4.MR4;
import usercf.step5.MR5;

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

//input file
//usercf.txt
//A,1,1
//A,3,5
//A,4,3
//B,2,3
//B,5,3
//C,1,5
//C,6,10
//D,1,10
//D,5,5
//E,3,5
//E,4,1
//F,2,5
//F,3,3
//F,6,1

public class JobRunner {
	public static void main(String[] args) {
		int status1 = -1;
		int status2 = -1;
		int status3 = -1;
		int status4 = -1;
		int status5 = -1;

		status1 = new MR1().run();
		if (status1 == 1) {
			System.out.println("step1运行成功 ，开始运行step2");
			status2 = new MR2().run();
		} else {
			System.out.println("step1运行失败");
		}

		if (status2 == 1) {
			System.out.println("step2运行成功 ，开始运行step3");
			status3 = new MR3().run();
		} else {
			System.out.println("step2运行失败");
		}

		if (status3 == 1) {
			System.out.println("step3运行成功 ，开始运行step4");
			status4 = new MR4().run();
		} else {
			System.out.println("step3运行失败");
		}

		if (status4 == 1) {
			System.out.println("step4运行成功 ，开始运行step5");
			status5 = new MR5().run();
		} else {
			System.out.println("step1运行失败");
		}

		if (status5 == 1) {
			System.out.println("step5运行成功 ，程序结束");
		} else {
			System.out.println("step5运行失败");
		}

	}

}
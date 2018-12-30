package contentcf.job;

import contentcf.step1.MR1;
import contentcf.step2.MR2;
import contentcf.step3.MR3;
import contentcf.step4.MR4;

/**
 * @author hyc
 * 
 * MapReduce步骤（UserCF）
 * 
 * 1.将ItemProfile转置
 *   输入：用户ID（行）-物品ID（列）-0或1
 *   输出：物品ID（行）-用户ID（列）-0或1
 * 
 * 2.ItemUser（评分矩阵）*步骤1的输出
 *   输入：步骤2的输入
 *   缓存：步骤1的输出
 *   输出：用户ID（行）标签ID（列）-分值
 *   
 * 3.Cos<步骤1的输入，步骤2的输出>
 *   输入：步骤1的输入
 *   缓存：步骤2的输出
 *   输出：用户ID（行）-物品ID（列）-相似度
 *   
 * 4.将之前用户已经有过行为的置为0
 *   输入：步骤3的输出
 *   缓存：步骤2的输入
 *   输出：用户ID（行）-物品ID（列）-相似度
 *
 */

//input file
//
//itemprofile.txt
//I1	1_1,4_1,5_1,7_1
//I2	2_1,4_1,7_1,9_1
//I3	2_1,3_1,6_1,8_1,9_1
//I4	1_1,3_1,4_1,5_1
//I5	2_1,4_1,7_1,8_1
//
//itemuser.txt
//U1	I1_1,I5_5
//U2	I2_4,I4_1
//U3	I2_5,I3_3,I5_1

public class JobRunner {
    public static void main(String[] args) {
        int status1 = -1;
        int status2 = -1;
        int status3 = -1;
        int status4 = -1;

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
            System.out.println("step4运行成功 ，运行结束");
        } else {
            System.out.println("step4运行失败");
        }
    }
}
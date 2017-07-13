package com.boomhe.imageloader;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        // 给定一个整数数组，返回两个数字的索引，使它们相加到一个特定的目标。
        int[] temp = {1,3,4,5,7,8};

        int[] ints = twoSum(temp, 12);
        System.out.println("目标数字111" + ints[0]);
        System.out.println("目标数字222" + ints[1]);
    }


    public int[] twoSum(int[] nums, int target) {
        int[] temp = nums;
        int length = temp.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - 1; j++) {
                int one = temp[i];
                int two = temp[j + 1];
                if (one + two == target) {
                    // 等于目标值 返回即可
                    temp[0] = temp[i];
                    temp[1] = temp[j + 1];
                    return temp;
                }
            }
        }
        return temp;
    }
}
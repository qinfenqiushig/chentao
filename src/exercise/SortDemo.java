/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package exercise;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author chentao
 * @version $Id: SortDemo.java, v 0.1 2016年2月26日 下午2:17:30 chentao Exp $
 */
public class SortDemo {

    public static void main(String[] args) {
        int[] arr = generate(10, 20);
        //        sort1(arr);
        //        sort2(arr);
        sort3(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
        if (check(arr, 10)) {
            System.out.println("YES!");
        }
    }

    /**
     * 冒泡排序
     *
     * @param arr
     */
    public static void sort1(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            boolean flage = false;
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                    flage = true;
                }
            }
            if (!flage) {
                break;
            }
        }
    }

    /**
     * 插入排序
     *
     * @param arr
     */
    public static void sort2(int[] arr) {
        int len = arr.length;
        int i = 1;
        for (; i < len; i++) {
            int key = arr[i];
            int j = i - 1;
            for (; j >= 0 && arr[j] > key; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = key;
        }
    }

    /**
     * 快速排序
     *
     * @param arr
     * @param low
     * @param heigh
     */
    public static void sort3(int[] arr, int low, int heigh) {
        int key = arr[low];
        int l = low;
        int h = heigh;
        while (l < h) {

            while (l < h && arr[h] > key) {
                h--;
            }
            if (l < h) {
                int t = arr[l];
                arr[l] = arr[h];
                arr[h] = t;
                l++;
            }

            while (l < h && arr[l] < key) {
                l++;
            }
            if (l < h) {
                int t = arr[l];
                arr[l] = arr[h];
                arr[h] = t;
                h--;
            }
        }
        if (l > low)
            sort3(arr, low, l - 1);
        if (h < heigh)
            sort3(arr, h + 1, heigh);
    }

    public static int[] generate(int num, int max) {
        Random rand = new Random();
        int[] arr = new int[num];
        boolean[] flages = new boolean[max];
        int index;
        for (int i = 0; i < num; i++) {
            do {
                index = rand.nextInt(max);
            } while (flages[index]);
            flages[index] = true;
            arr[i] = index;
        }
        return arr;
    }

    public static boolean check(int[] arr, int n) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (n == arr[mid]) {
                return true;
            }
            if (n < arr[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }
}

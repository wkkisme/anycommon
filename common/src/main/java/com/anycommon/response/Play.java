package com.anycommon.response;

public class Play {

    public static void main(String[] args) {

        int[] nums={1,1,2};
        System.out.println(single(nums));

    }
    public static int single(int[] nums){
        if (nums.length <= 0){
            throw new RuntimeException("参数有误");
        }
        if (nums.length == 1){

            return  nums[0];
        }
        int single = 0;

        for(int num: nums){
            single ^= num;
        }
        return  single;
    }

}

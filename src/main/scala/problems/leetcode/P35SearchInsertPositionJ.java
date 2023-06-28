package problems.leetcode;

public class P35SearchInsertPositionJ {
    public static void main(String[] args) {
        P35SearchInsertPositionJ problem = new P35SearchInsertPositionJ();
        System.out.println("1: " + problem.searchInsertOLogN(new int[]{1,2,3,4,5,6}, 2));
        System.out.println("2: " + problem.searchInsertOLogN(new int[]{}, 2));
        System.out.println("3: " + problem.searchInsertOLogN(new int[]{1,3,4,5,6}, 2));
        System.out.println("4: " + problem.searchInsertOLogN(new int[]{2,3,4,5,6}, 2));
        System.out.println("5: " + problem.searchInsertOLogN(new int[]{1,2}, 2));
        System.out.println("6: " + problem.searchInsertOLogN(new int[]{1,2}, 0));
        System.out.println("7: " + problem.searchInsertOLogN(new int[]{1,2}, 3));
        System.out.println("8: " + problem.searchInsertOLogN(new int[]{1,4,6,7,8,9}, 6));
    }

    public int searchInsertOLogN(int[] nums, int target) {
        if(nums.length == 0) return 0;
        int pointer = nums.length / 2;
        int left = 0;
        int right = nums.length - 1;
        if(target == nums[left]) return left;
        if(target < nums[left]) return left;
        if(target == nums[right]) return right;
        if(target > nums[right]) return right + 1;
        if((right - left) == 1) {
            return right;
        }
        while(true) {
            if(target == nums[pointer]) {
                return pointer;
            } else if(target < nums[pointer]) {
                right = pointer;
                if((right - left) == 1) {
                    return right;
                }
                pointer = (right + left) / 2;
            } else if(target > nums[pointer])  {
                left = pointer;
                if((right - left) == 1) {
                    return right;
                }
                pointer = (right + left) / 2;
            }

        }
    }


    public int searchInsertSimple(int[] nums, int target) {
        int result = 0;
        if(nums.length == 0) {
            return result;
        }
        boolean isInArray = false;
        for(int i = 0; i < nums.length; i++) {
            if(target == nums[i]) {
                isInArray = true;
                result = i;
                break;
            }
        }
        if(isInArray) {
            return result;
        } else {
            for(int i = 0; i < nums.length; i++) {
                if(target < nums[i]) {
                    return i;
                }
            }
            return nums.length;
        }
    }
}

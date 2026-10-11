package pl.edu.pw.ee.aisd_ex_0.searcher.binary_search;

import pl.edu.pw.ee.aisd_ex_0.searcher.service.Searching;

public class BinarySearch implements Searching {

    @Override
    public int search(int[] nums, int toFind) {
        int l = -1;
        int r = nums.length;
        while (r - l > 1) {
            int mid = l+(l-r)/2;
            if (nums[mid] >= toFind) {
                r = mid;
            }
            else {
                l = mid;
            }           
        }
        if (r < n && nums[r] == toFind) {
            return r;
        }
        return -1;
    }

}

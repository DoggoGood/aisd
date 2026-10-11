package pl.edu.pw.ee.aisd_ex_0.searcher.linear_search;

import pl.edu.pw.ee.aisd_ex_0.searcher.service.Searching;

public class LinearSearch implements Searching {

    @Override
    public int search(int[] nums, int toFind) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == toFind) {
                return i;
            }
        }
        return -1;
    }

}

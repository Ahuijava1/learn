package com.learn.algoritem.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LetterCombinations
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 *  给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * @author zhengchaohui
 * @date 2020/8/26 17:09
 */
public class LetterCombinations {

    private List<String> result = new ArrayList<>();
    Map<String, String> map = new HashMap<String, String>(){{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};
    public static void main(String[] args) {
        new LetterCombinations().letterCombinations("23");
    }

    public List<String> letterCombinations(String digits) {
        if ("".equals(digits)) {
            return result;
        }

        String[] nums = digits.split("");
        dfs(nums, 0, "");
        return result;
    }

    private void dfs(String[] nums, int curr, String res) {
        if (curr >= nums.length) {
            result.add(res);
        } else {
            String str = map.get(nums[curr]);
            String[] strs = str.split("");
            curr++;
            for (String s : strs) {
                dfs(nums, curr, res + s);
            }
        }
    }
}

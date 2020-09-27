# leetcode

## 算法

### [剑指 Offer 06. 从尾到头打印链表](https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/)

描述：

```json
输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

示例 1：

输入：head = [1,3,2]
输出：[2,3,1]

限制：
0 <= 链表长度 <= 10000

```



解答（栈解决）：

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int[] reversePrint(ListNode head) {
        if(head == null)
            return new int[0];
      
       // 看题目，要的是值，不是反转后的链表
        
        // 栈的解法
        Stack<Integer> stack = new Stack<Integer>();
        ListNode node = head;
        // 遍历链表，加入到栈中
        while(node != null){
            stack.push(node.val);
            node = node.next;
        }

        // 将栈中的元素逐个弹出
        int size = stack.size();
        int[] ret = new int[size];
        for(int i = 0; i <  size; i++){
            ret[i] = stack.pop();
        }

        return ret;
    }
}
```

### 102 二叉树的层序遍历

```java
/**
 * Definition for a binary tree node.
 * 递归 time O(N), space O(N)
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * } 
 */
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root != null) {
            level(root, 0);
        }
        return result;
    }

    private void level(TreeNode node, int level){
        // 说明是新加入的
        if (result.size() == level){
            result.add(new ArrayList());
        }

        result.get(level).add(node.val);

        if (node.left != null) {
            level(node.left, level + 1);
        }
        if (node.right != null) {
            level(node.right, level + 1);
        }
    }
}
```

```java
/**
 * Definition for a binary tree node.
 * 迭代，用队列实现
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        // 
        while (!queue.isEmpty()) {
            // 这里的size刚好是下一层的所有节点的数量
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                } 
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(list);
        }
        return result;
    }
}
```



## 数据库

### [181. 超过经理收入的员工](https://leetcode-cn.com/problems/employees-earning-more-than-their-managers/)（简单）

描述：

```
Employee 表包含所有员工，他们的经理也属于员工。每个员工都有一个 Id，此外还有一列对应员工的经理的 Id。
+----+-------+--------+-----------+
| Id | Name  | Salary | ManagerId |
+----+-------+--------+-----------+
| 1  | Joe   | 70000  | 3         |
| 2  | Henry | 80000  | 4         |
| 3  | Sam   | 60000  | NULL      |
| 4  | Max   | 90000  | NULL      |
+----+-------+--------+-----------+
给定 Employee 表，编写一个 SQL 查询，该查询可以获取收入超过他们经理的员工的姓名。在上面的表格中，Joe 是唯一一个收入超过他的经理的员工。

+----------+
| Employee |
+----------+
| Joe      |
+----------+
```

解答：

```mysql
# Write your MySQL query statement below
# 连表是肯定的了
select e1.Name as Employee from Employee e1 
        left join Employee e2 
        on e1.ManagerId = e2.Id 
        where e1.Salary > e2.Salary;
```

知识：

左连接、右连接、左外连接、右外连接、内连接、自然连接

### [182. 查找重复的电子邮箱](https://leetcode-cn.com/problems/duplicate-emails/)（简单）

描述：

```
编写一个 SQL 查询，查找 Person 表中所有重复的电子邮箱。
示例：
+----+---------+
| Id | Email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+
根据以上输入，你的查询应返回以下结果：

+---------+
| Email   |
+---------+
| a@b.com |
+---------+
说明：所有电子邮箱都是小写字母。

```

解答：

```mysql
# Write your MySQL query statement below
select Email from Person  group by Email having count(Email) > 1;

# 或者
select Email from (
    select Email, count(Email) as num from Person group by Email
) as temp where temp.num > 1;
```

```

```


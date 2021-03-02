package tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**二叉树的序列化和反序列化*/
public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return serializeRecursive(root, new StringBuilder()).toString();
    }

    // 使用先序遍历
    // 这里使用其他三种遍历方式也可以，只是需要修改对应解码的顺序
    public StringBuilder serializeRecursive(TreeNode root, StringBuilder sb) {
        if (root != null) {
            sb.append(root.val).append(",");
            serializeRecursive(root.left, sb);
            serializeRecursive(root.right, sb);
        } else {
            sb.append("None,");
        }
        return sb;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] strs = data.split(",");
        List<String> stringList = new LinkedList<>(Arrays.asList(strs));
        return deserializeRecursive(stringList);
    }

    public TreeNode deserializeRecursive(List<String> stringList) {
        if (stringList.get(0).equals("None")) {
            stringList.remove(0);
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(stringList.get(0)));
        stringList.remove(0);
        node.left = deserializeRecursive(stringList);
        // 因为在上一次的递归中会删掉 stringList 中的第一个元素，因此输入的参数与上一次调用不一样
        node.right = deserializeRecursive(stringList);
        return node;
    }
}

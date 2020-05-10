package sets;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomPrinter {

    // printing tree wasn't part of assignment, so this code is just copied from:
    // https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
    // to make it easier to debug and see effects

    public static <T extends Comparable<?>> void printNode(Node root) {
        int maxLevel = maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }
    private static <T extends Comparable<?>> void printNodeInternal(java.util.List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
        printWhitespaces(firstSpaces);
        java.util.List<Node> newNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node != null) {
                String color = (node.getColor() != null ? (node.getColor() == Color.RED ? "R" : "B") : "");
                System.out.print(node.getValue() + color);
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");
        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }
                if (nodes.get(j).getLeft() != null)
                    System.out.print("/");
                else
                    printWhitespaces(1);
                printWhitespaces(i + i - 1);
                if (nodes.get(j).getRight() != null)
                    System.out.print("\\");
                else
                    printWhitespaces(1);
                printWhitespaces(endgeLines + endgeLines - i);
            }
            System.out.println("");
        }
        printNodeInternal(newNodes, level + 1, maxLevel);
    }
    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }
    private static <T extends Comparable<?>> int maxLevel(Node node) {
        if (node == null)
            return 0;
        return Math.max(maxLevel(node.getLeft()), maxLevel(node.getRight())) + 1;
    }
    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }
        return true;
    }
    
}

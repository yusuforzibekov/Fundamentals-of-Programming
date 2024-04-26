package com.epam.rd.autocode.collection.tree;

import java.util.Objects;
import java.util.Optional;

public class BinaryTree {
    private static final String INDENT = "-~-";
    private static final String EOL = System.lineSeparator();
    private Node root;
    private int size;

    private static final class Node {
        Integer e;
        Node left;
        Node right;

        Node(Integer e) {
            this.e = e;
        }
    }

    public BinaryTree() {
    }

    public BinaryTree(Integer... elements) {
        if (elements == null) {
            throw new NullPointerException("Input array should not be null.");
        }
        for (Integer element : elements) {
            add(element);
        }
    }

    public boolean add(Integer element) {
        Objects.requireNonNull(element);
        Node newNode = new Node(element);
        if (root == null) {
            root = newNode;
            size = 1;
            return true;
        }
        Node currentNode = root;
        Node parent = null;
        while (currentNode != null) {
            int cmp = element.compareTo(currentNode.e);
            if (cmp == 0) {
                return false;
            } else if (cmp < 0) {
                parent = currentNode;
                currentNode = currentNode.left;
            } else {
                parent = currentNode;
                currentNode = currentNode.right;
            }
        }
        if (element.compareTo(parent.e) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        size++;
        return true;
    }

    public void addAll(Integer... ar) {
        Objects.requireNonNull(ar);
        for (Integer element : ar) {
            add(element);
        }
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        toStringHelper(root, sb);
        return "[" + sb.substring(0, sb.length() - 2) + "]";
    }

    private void toStringHelper(Node node, StringBuilder sb) {
        if (node.left != null) {
            toStringHelper(node.left, sb);
        }
        sb.append(node.e).append(", ");
        if (node.right != null) {
            toStringHelper(node.right, sb);
        }
    }

    public Optional<Integer> remove(Integer element) {
        Objects.requireNonNull(element);
        if (root == null) {
            return Optional.empty();
        }
        Node parent = null;
        Node currentNode = root;
        while (currentNode != null && !element.equals(currentNode.e)) {
            parent = currentNode;
            if (element.compareTo(currentNode.e) < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        if (currentNode == null) {
            return Optional.empty();
        }
        Optional<Integer> result = Optional.of(currentNode.e);
        if (currentNode.left == null && currentNode.right == null) {
            if (parent == null) {
                root = null;
            } else if (currentNode == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            size--;
        } else if (currentNode.left != null && currentNode.right == null) {
            if (parent == null) {
                root = currentNode.left;
            } else if (currentNode == parent.left) {
                parent.left = currentNode.left;
            } else {
                parent.right = currentNode.left;
            }
            size--;
        } else if (currentNode.left == null && currentNode.right != null) {
            if (parent == null) {
                root = currentNode.right;
            } else if (currentNode == parent.left) {
                parent.left = currentNode.right;
            } else {
                parent.right = currentNode.right;
            }
            size--;
        } else {
            Node minNode = currentNode.right;
            Node minParent = null;
            while (minNode.left != null) {
                minParent = minNode;
                minNode = minNode.left;
            }
            currentNode.e = minNode.e;
            if (minNode == currentNode.right) {
                currentNode.right = minNode.right;
            } else {
                minParent.left = minNode.right;
            }
            size--;
        }
        return result;
    }

    public int size() {
        return size;
    }

    String asTreeString() {
        StringBuilder sb = new StringBuilder();
        asTreeString(sb, root, 0);
        return sb.toString();
    }

    private void asTreeString(StringBuilder sb, Node node, int k) {
        if (node == null) {
            return;
        }
        asTreeString(sb, node.right, k + 1);
        sb.append(INDENT.repeat(k));
        sb.append(String.format("%3s", node.e)).append(EOL);
        asTreeString(sb, node.left, k + 1);
    }
}
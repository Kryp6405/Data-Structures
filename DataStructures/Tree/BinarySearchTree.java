package DataStructures.Tree;

import java.util.List;

import DataStructures.LinkedList.Queue;

public class BinarySearchTree<K extends Comparable<K>,V> extends BinaryTree<K,V>{
    int size;

    public BinarySearchTree(){
        super();
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        size = 0;
        root = null;
    }

    public int size(){
        return size;
    }

    public int height(){
        return heightHelper(root);
    }

    private int heightHelper(Node node){
        if(node == null)
            return -1;

        return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
    }

    public void insert(K key, V val){
        Node newNode = new Node(key, val);

        if(root == null){
            root = newNode;
            size++;
        }
        else{
            insertHelper(root, newNode);
        }
    }

    private void insertHelper(Node node, Node newNode){
        if (node.key.compareTo(newNode.key) > 0){
            if (node.left == null){
                node.left = newNode;
                size++;
            } else
                insertHelper(node.left, newNode);
        } else if (node.key.compareTo(newNode.key) < 0){
            if (node.right == null){
                node.right = newNode;
                size++; 
            } else 
                insertHelper(node.right, newNode);
        } else
            node.val = newNode.val;
    }

    public V find(K key){
        if(root == null)
            return null;
        else
            return findHelper(root, key);
    } 

    private V findHelper(Node node, K key){
        if(node == null)
            return null;
        else if(node.key.compareTo(key) > 0)
            return findHelper(node.left, key);
        else if(node.key.compareTo(key) < 0)
            return findHelper(node.right, key);
        else
            return node.val;
    }

    public V delete(K key){
        if(root == null)
            return null;
        else 
            return deleteHelper(root, null, key);
    }

    private V deleteHelper(Node node, Node parent, K key){
        if(node == null)
            return null;
        else if(node.key.compareTo(key) > 0)
            return deleteHelper(node.left, node, key);
        else if(node.key.compareTo(key) < 0)
            return deleteHelper(node.right, node, key);
        else{
            V ret_val = node.val;
            if(node.left == null && node.right == null){ //Case 1: Leaf Node
                if(parent != null){
                    if(parent.left == node)
                        parent.left = null;
                    else
                        parent.right = null; 
                } else
                    root = null;
            } else if (node.left != null && node.right == null){ //Case 2a: Left Child Node Exists
                if(parent != null){
                    if(parent.left == node)
                        parent.left = node.left;
                    else
                        parent.right = node.left; 
                } else
                    root = node.right;
            } else if (node.left == null && node.right != null){ //Case 2b: Right Child Node Exists
                if(parent != null){
                    if(parent.left == node)
                        parent.left = node.right;
                    else
                        parent.right = node.right; 
                } else
                    root = node.right;
            } else if (node.left != null && node.right != null){ //Case 3: Both Children Nodes Exist
                Node successor = findMinimum(node.right);
                node.key = successor.key;
                node.val = successor.val;

                deleteHelper(node.right, node, node.key);
            }

            return ret_val;
        }
    }   

    public void rotateRight(K key){
        rotateRightHelper(root, null, key);
    }

    private void rotateRightHelper(Node node, Node parent, K key){
        if(node == null)
            return;
        else if(node.key.compareTo(key) > 0)
            rotateRightHelper(node.left, node, key);
        else if(node.key.compareTo(key) < 0)
            rotateRightHelper(node.right, node, key);
        else{
            if(node.left == null)
                return;

            Node newX = node.left;
            node.left = newX.right;
            newX.right = node;

            if (parent == null) 
                root = newX;
            else if (parent.left == node)
                parent.left = newX;
            else
                parent.right = newX;
        }
    }

    public void rotateLeft(K key){
        rotateLeftHelper(root, null, key);
    }

    private void rotateLeftHelper(Node node, Node parent, K key){
        if(node == null)
            return;
        else if(node.key.compareTo(key) > 0)
            rotateLeftHelper(node.left, node, key);
        else if(node.key.compareTo(key) < 0)
            rotateLeftHelper(node.right, node, key);
        else{
            if(node.right == null)
                return;

            Node newX = node.right;
            node.right = newX.left;
            newX.left = node;

            if (parent == null) 
                root = newX;
            else if (parent.left == node)
                parent.left = newX;
            else
                parent.right = newX;
        }
    }

    public K minimum(){
        return findMinimum(root).key;
    }

    public V valAtMinimum(){
        return findMinimum(root).val;
    }

    private Node findMinimum(Node node){
        if(node == null)
            return null;
        
        while (node.left != null) 
            node = node.left;
        return node;
    }

    public K maximum(){
        return findMaximum(root).key;
    }

    public V valAtMaximum(){
        return findMaximum(root).val;
    }

    private Node findMaximum(Node node){
        if(node == null)
            return null;
        
        while (node.right != null) 
            node = node.right;
        return node;
    }

    public void preOrderList(Node node, List<Node> order) {
        if (node == null) 
            return;
        order.add(node);
        preOrderList(node.left, order);
        preOrderList(node.right, order);
    }

    public void inOrderList(Node node, List<Node> order) {
        if (node == null) 
            return;
        inOrderList(node.left, order);
        order.add(node);
        inOrderList(node.right, order);
    }

    public void postOrderList(Node node, List<Node> order) {
        if (node == null) 
            return;
        postOrderList(node.left, order);
        postOrderList(node.right, order);
        order.add(node);
    }

    public void levelOrderList(Node node, List<Node> order){
        if(node == null)
            return;
        
        //Our own Queue implementation from LinkedList directory
        Queue<Node> queue = new Queue<>();
        queue.add(root);

        while(!queue.isEmpty()){
            Node curr = queue.poll();
            order.add(curr);

            if(curr.left != null)
                queue.add(curr.left);
            if(curr.right != null)
                queue.add(curr.right);
        }
    }
}   

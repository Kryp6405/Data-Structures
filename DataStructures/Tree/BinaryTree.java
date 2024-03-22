package DataStructures.Tree;

import DataStructures.LinkedList.Queue;

public class BinaryTree<K,V>{
    Node root;

    public BinaryTree(){
        root = null;
    }
    
    public void preOrder(){
        if(root == null)
            System.out.println("Empty Tree");
        else{
            preOrderHelper(root);
            System.out.println("Finished");
        }

    }

    private void preOrderHelper(Node node){
        if(node != null){
            System.out.print("["+node.key+", "+node.val+"] --> ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    public void inOrder(){
        if(root == null)
            System.out.println("Empty Tree");
        else{
            inOrderHelper(root);
            System.out.println("Finished");
        }
    }

    private void inOrderHelper(Node node){
        if(node != null){
            preOrderHelper(node.left);
            System.out.print("["+node.key+", "+node.val+"] --> ");
            preOrderHelper(node.right);
        }
    }

    public void postOrder(){
        if(root == null)
            System.out.println("Empty Tree");
        else{
            postOrderHelper(root);
            System.out.println("Finished");
        }
    }

    private void postOrderHelper(Node node){
        if(node != null){
            preOrderHelper(node.left);
            preOrderHelper(node.right);
            System.out.print("["+node.key+", "+node.val+"] --> ");
        }
    }    

    public void levelOrder(){
        if(root == null)
            System.out.println("Empty Tree");
        else{
            //Our own Queue implementation from LinkedList directory
            Queue<Node> queue = new Queue<>();
            queue.add(root);

            while(!queue.isEmpty()){
                Node curr = queue.poll();
                System.out.print("["+curr.key+", "+curr.val+"] --> ");

                if(curr.left != null)
                    queue.add(curr.left);
                if(curr.right != null)
                    queue.add(curr.right);
            }

            System.out.println("Finished");
        }
    }

    class Node {
        K key;
        V val;
        Node left, right;

        Node(){}
        Node(K key, V val){ this.key = key; this.val = val; }
        Node(K key, V val, Node left, Node right){ 
            this.key = key;
            this.val = val; 
            this.left = left; 
            this.right = right;
        }
    }
}

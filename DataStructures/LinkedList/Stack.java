package DataStructures.LinkedList;

public class Stack<T> {
    private DoubleLL<T> stack;

    public Stack(){
        stack = new DoubleLL<>();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public int size(){
        return stack.size();
    }

    public void push(T val){
        stack.add(val);
    }

    public T pop(){
        return stack.remove(stack.size()-1);
    }

    public T peek(){
        return stack.get(stack.size()-1);
    }

    public DoubleLL<T> toList(){
        return stack;
    }

    public String toString(){
        return stack.toString();
    }
}

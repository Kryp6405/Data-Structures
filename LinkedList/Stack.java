package DataStructures.LinkedList;

public class Stack<T> {
    private SingleLL<T> stack;

    public Stack(){
        stack = new SingleLL<>();
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

    public String toString(){
        return stack.toString();
    }
}

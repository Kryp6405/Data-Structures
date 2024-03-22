package DataStructures.LinkedList;

public class Queue<T> {
    private DoubleLL<T> queue;

    public Queue(){
        queue = new DoubleLL<>();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public int size(){
        return queue.size();
    }

    public void add(T val){
        queue.add(val);
    }

    public T peek(){
        if(queue.isEmpty())
            return null;
        return queue.get(0);
    }

    public T element(){
        return queue.get(0);
    }

    public T poll(){
        if(queue.isEmpty())
            return null;
        return queue.remove(0);
    }

    public T remove(){
        return queue.remove(0);
    }
    

}

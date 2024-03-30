package DataStructures.LinkedList;

public class DoubleLL<T> {
    int size;
    Node head, tail;

    public DoubleLL(){
        size = 0;
        head = tail = null;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return this.size;
    }

    public void add(T val){     
        Node newNode = new Node(val);

        if (head == null && tail == null) {
            head = tail = newNode;
            head.prev = tail.next = null;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }

        size++;
    }

    public void add(int loc, T val){
        if(loc < 0 || loc > size)
            throw new IndexOutOfBoundsException();
        else{
            Node newNode = new Node(val);

            if(loc == 0){
                newNode.next = head;
                if(head != null)
                    head.prev = newNode;
                head = newNode;
                if(size == 0)
                    tail = newNode;
            } else if(loc == size){
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            } else{
                Node curr = head;

                for(int i = 0; i < loc; i++)
                    curr = curr.next;
                curr.prev.next = newNode;
                newNode.prev = curr.prev;
                newNode.next = curr;
                curr.prev = newNode;
            }

            size++;
        }
    }

    public T remove(int loc){
        if(loc < 0 || loc >= size)
            throw new IndexOutOfBoundsException();
        else{
            T remove_val;
            if(loc == 0){
                remove_val = head.val;
                head = head.next;
                if(head != null)
                    head.prev = null;
                else
                    tail = null;
            } else if(loc == (size - 1)){          
                remove_val = tail.val;
                tail = tail.prev;
                if(tail != null)
                    tail.next = null;
                else 
                    head = null;
            } else {
                Node curr = head;

                for(int i = 0; i < loc; i++)
                    curr = curr.next;
                remove_val = curr.val;
                curr.next.prev = curr.prev;
                curr.prev.next = curr.next;
            }

            size--;
            return remove_val;
        }
    }

    public void removeAll(int start, int end){
        if(start < 0 || start >= size || end > size || end < start)
            throw new IndexOutOfBoundsException();
        else if(start == 0) { 
            for(int i = 0; i <= end && head != null; i++) 
                head = head.next;

            if(head != null)
                head.prev = null;
            else
                tail = null;
        } else{
            Node startNode = head;
            for (int i = 0; i < start - 1; i++) {
                startNode = startNode.next;
            }
            Node endNode = startNode;
            for (int i = start - 1; i < end && endNode != null; i++) { 
                endNode = endNode.next;
            }

            startNode.next = endNode;
            if (endNode != null) 
                endNode.prev = startNode;
            else 
                tail = startNode; 

        }

        size -= (end - start);
    }

    public T get(int loc){
        if(loc < 0 || loc >= size())
            throw new IndexOutOfBoundsException();
        else{
            T ret_val;

            if(loc == 0)
                ret_val = head.val;
            else if(loc == size - 1){
                ret_val = tail.val;
            } else{
                Node curr = head; 

                for(int i = 0; i < loc; i++){
                    curr = curr.next;
                }
                ret_val = curr.val;
            }

            return ret_val;
        }
    }

    public T set(int loc, T val){
        if(loc < 0 || loc >= size())
            throw new IndexOutOfBoundsException();
        else{
            T prev_val;

            if(loc == 0){
                prev_val = head.val;
                head.val = val;
            }
            else if(loc == size - 1){
                prev_val = tail.val;
                tail.val = val;
            } else{
                Node curr = head; 

                while(curr != null && loc-- > 0){
                    curr = curr.next;
                }
                prev_val = curr.val;
                curr.val = val;
            }

            return prev_val;
        }
    }

    public boolean contains(T val){
        Node curr = head;

        while(curr != null){
            if(!curr.val.equals(val))
                curr = curr.next;
            else 
                return true;
        }

        return false;
    }

    public void reverse(){
        for(int i = size - 2; i >= 0; i--){
            add(get(i));
            remove(i);
        }
    }

    public void deleteAll(){
        size = 0;
        head = tail = null;
    }

    public int indexOf(T val){
        if(!contains(val))
            return -1;
        else{
            for(int i = 0; i < size; i++){
                if(get(i).equals(val)) 
                    return i;
            }

            return -1;
        }
    }

    public String toString(){
        if(size == 0)
            return "Empty List";

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i = 0; i < size-1; i++){
            sb.append(get(i)+", ");
        }
        sb.append(get(size-1)+"]");

        return sb.toString();
    }

    class Node{
        T val;
        Node prev, next;

        Node(){}
        Node(T val){ this.val = val; }
        Node(T val, Node prev, Node next){ 
            this.val = val; 
            this.prev = prev; 
            this.next = next;
        }
    }    
}

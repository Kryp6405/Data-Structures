package DataStructures.LinkedList;

public class SingleLL<T> {
    int size;
    Node head;

    public SingleLL(){
        size = 0;
        head = null;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return this.size;
    }

    public void add(T val){     
        Node newNode = new Node(val);

        if (head == null) {
            head = newNode;
        } else {
            Node curr = head;

            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
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
                head = newNode;
            } else if(loc == size){
                Node curr = head;

                while(curr.next != null) {
                    curr = curr.next;
                }
                curr.next = newNode;
            } else{
                Node curr = head, prev = null;

                while(curr.next != null && loc-- > 0) {
                    prev = curr;
                    curr = curr.next;
                }
                prev.next = newNode;
                newNode.next = curr;
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
            } else if(loc == (size - 1)){
                Node curr = head;
                while(curr.next != null){
                    curr = curr.next;
                }
                remove_val = curr.val;
                curr = null;
            } else {
                Node prev = null, curr = head;
                while(curr != null && loc-- > 0){
                    prev = curr;
                    curr = curr.next;
                }
                remove_val = curr.val;
                curr = curr.next;
                prev.next = curr;
            }

            size--;
            return remove_val;
        }
    }

    public void removeAll(int start, int end){
        if(start < 0 || start >= size() || end > size() || end < start)
            throw new IndexOutOfBoundsException();
        else{
            Node prev = null, curr = head;
            int cnt = 0;
            while(curr != null && cnt++ != start){
                prev = curr;
                curr = curr.next;
            }
            for(int i = start; i < end; i++){
                curr = curr.next;
            }
            prev.next = curr;

            size -= (end - start);
        }
    }

    public T get(int loc){
        if(loc < 0 || loc >= size())
            throw new IndexOutOfBoundsException();
        else{
            T ret_val;

            if(loc == 0)
                ret_val = head.val;
            else if(loc == size - 1){
                Node curr = head;

                while(curr.next != null){
                    curr = curr.next;
                }
                ret_val = curr.val;
            } else{
                Node curr = head; 

                while(curr != null && loc-- > 0){
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
                Node curr = head;

                while(curr.next != null){
                    curr = curr.next;
                }
                prev_val = curr.val;
                curr.val = val;
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

    public void reverse() {
        for(int i = size - 2; i >= 0; i--){
            add(get(i));
            remove(i);
        }
    }

    public void deleteAll(){
        size = 0;
        head = null;
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
        for(int i = 0; i < size-1; i++){
            sb.append("["+get(i)+"] --> ");
        }
        sb.append("["+get(size-1)+"]");

        return sb.toString();
    }

    class Node{
        T val;
        Node next;

        Node(){}
        Node(T val){ this.val = val; }
        Node(T val, Node next){ this.val = val; this.next = next;}
    }    
}

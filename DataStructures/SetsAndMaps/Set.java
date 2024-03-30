package DataStructures.SetsAndMaps;

import DataStructures.LinkedList.DoubleLL;

public class Set<T> {
    private DoubleLL<T> set;

    public Set(){
        set = new DoubleLL<>();
    }

    public int size(){
        return set.size();
    }

    public boolean isEmpty(){
        return set.isEmpty();
    }

    public boolean contains(T val){
        return set.contains(val);
    }

    public Object[] toArray(){
        Object[] arr = new Object[set.size()];
        for(int i = 0; i < set.size(); i++)
            arr[i] = set.get(i);

        return arr;
    }

    public boolean add(T val){
        if(!set.contains(val)){
            set.add(val);
            return true;
        }

        return false;
    }

    public boolean remove(T val){
        if(set.contains(val)){
            set.remove(set.indexOf(val));
            return true;
        }

        return false;
    }

    public void clear(){
        set.deleteAll();
    }

    public boolean addAll(Set<T> other){
        Object[] arr = other.toArray();
        int oldSize = set.size();

        for(int i = 0; i < arr.length; i++)
            add((T) arr[i]);

        if(oldSize != set.size())
            return true;
        return false;
    }

    public boolean removeAll(Set<T> other){
        Object[] arr = toArray();
        int oldSize = set.size();

        for(int i = 0; i < arr.length; i++)
            if(!other.contains((T) arr[i]))
                remove((T) arr[i]);

        if(oldSize != set.size())
            return true;
        return false;
    }

    public String toString(){
        if(set.size() == 0)
            return "Empty List";

        StringBuffer sb = new StringBuffer();
        sb.append("<");
        for(int i = 0; i < set.size()-1; i++){
            sb.append(set.get(i)+", ");
        }
        sb.append(set.get(set.size()-1)+">");

        return sb.toString();
    } 
}

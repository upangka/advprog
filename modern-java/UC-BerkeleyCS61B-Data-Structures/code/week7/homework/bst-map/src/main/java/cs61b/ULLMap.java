package cs61b;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * 使用链表实现Map
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/26
 */
public class ULLMap<K,V> implements Map16B<K,V>{

    private class Entry{
        private K key;
        private V value;
        private Entry next;

        public Entry(){
            this(null,null,null);
        }
        public Entry(K key, V value, Entry next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        Entry get(K sk){
            if(sk != null && sk.equals(key)){
                return this;
            }
            if(next == null){
                return null;
            }
            return next.get(sk);
        }

    }

    private Entry sentinel;
    private int size;

    public ULLMap(){
        sentinel = new Entry();
    }

    @Override
    public void put(K key, V value) {

        var entry = sentinel.get(key);
        if(entry == null){
            sentinel.next = new Entry(key,value,sentinel.next);
            size += 1;
        }else{
            entry.value = value;
        }

    }

    @Override
    public V get(K key) {
        var current = sentinel.next;
        while(current != null){
            if(current.key.equals(key)){
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        var current = sentinel.next;
        while(current != null){
            if(current.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        sentinel.next = null;
        size = 0;
    }

    private class ULLMapIterator implements Iterator<K>{
        private Entry current = sentinel;

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public K next() {
            current = current.next;
            return current.key;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new ULLMapIterator();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ","{","}");
        for(var key: this){
            sj.add(key + ":" + get(key));
        }
        return sj.toString();
    }
}

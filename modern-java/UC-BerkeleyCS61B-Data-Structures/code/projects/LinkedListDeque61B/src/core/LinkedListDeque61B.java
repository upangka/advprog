///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package core;

import java.util.List;

/**
 * LinkedListDeque61B
 */
public class LinkedListDeque61B<T> implements Deque61B<T>{
    private final Node<T> sentinel;
    private int size;

    private static class Node<T>{
        private Node<T> prev;
        private T item;
        private Node<T> next;
    }

    public LinkedListDeque61B(){
        var node = new Node<T>();
        this.sentinel = node;
        this.sentinel.prev = node;
        this.sentinel.item = null;
        this.sentinel.next = node;
        
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        
    }

    @Override
    public void addLast(T item) {
        
    }

    @Override
    public List<T> toList() {
        
        throw new UnsupportedOperationException("Unimplemented method 'toList'");
    }

    @Override
    public boolean isEmpty() {
        
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public int size() {
        
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    @Override
    public T getFirst() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getFirst'");
    }

    @Override
    public T getLast() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getLast'");
    }

    @Override
    public T removeFirst() {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeFirst'");
    }

    @Override
    public T removeLast() {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeLast'");
    }

    @Override
    public T get(int index) {
        
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public T getRecursive(int index) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getRecursive'");
    }

    
}
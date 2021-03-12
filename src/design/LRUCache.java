package design;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private Map<Integer, DeListNode> cache;
    private DeListNode dummyHead;
    private DeListNode dummyTail;
    private int capacity;
    int size;

    class DeListNode {
        int key;
        int value;
        DeListNode next;
        DeListNode prev;

        DeListNode() {
        }

        DeListNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        cache = new HashMap<>();
        dummyHead = new DeListNode();
        dummyTail = new DeListNode();
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    public void addToHead(DeListNode node) {
        dummyHead.next.prev = node;
        node.next = dummyHead.next;
        dummyHead.next = node;
        node.prev = dummyHead;
        cache.put(node.key, node);
        size++;
    }

    public void moveToHead(DeListNode node) {
        removeNode(node);
        addToHead(node);
    }

    public void removeNode(DeListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        cache.remove(node.key);
        size--;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            DeListNode node = cache.get(key);
            moveToHead(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        DeListNode node = cache.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            node = new DeListNode(key, value);
            addToHead(node);
            if (size > capacity) {
                removeNode(dummyTail.prev);
            }
        }
    }
}


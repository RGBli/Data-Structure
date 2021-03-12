package design;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 用队列实现栈
 * P225
 */
public class MyStack {
    private Deque<Integer> q1;
    private Deque<Integer> q2;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        q1.offer(x);
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        int size = q1.size();
        for (int i = 0; i < size - 1; i++) {
            q2.offer(q1.poll());
        }
        int res = q1.poll();
        while (!q2.isEmpty()) {
            q1.offer(q2.poll());
        }
        System.out.println(q1);
        return res;
    }

    /**
     * Get the top element.
     */
    public int top() {
        int size = q1.size();
        for (int i = 0; i < size - 1; i++) {
            q2.offer(q1.poll());
        }
        int res = q1.peek();
        q2.offer(q1.poll());
        while (!q2.isEmpty()) {
            q1.offer(q2.poll());
        }
        return res;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return q1.isEmpty();
    }
}
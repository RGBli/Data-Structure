package design;

import java.util.Deque;
import java.util.LinkedList;

/**用栈实现队列
 * P232*/
public class MyQueue {
    private Deque<Integer> s1;
    private Deque<Integer> s2;

    /** Initialize your data structure here. */
    public MyQueue() {
        this.s1 = new LinkedList<>();
        this.s2 = new LinkedList<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        if (s1.isEmpty()) {
            s1.push(x);
        } else {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            s1.push(x);
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        return s1.pop();
    }

    /** Get the front element. */
    public int peek() {
        return s1.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return s1.isEmpty();
    }
}
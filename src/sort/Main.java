package sort;

public class Main {
    private static int[] arr = {1,8,4,3,7,2,9,6,5};
    private static int n = arr.length;

    // 冒泡排序
    public static void bubble(int[] arr) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }


    // 选择排序
    public static void select(int[] arr) {
        for (int i = 0; i < n; i++) {
            int minIndex = i;
            for (int j = i; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }
    }


    /*堆排序*/
    // 创建初始堆
    private static void heapSort(int[] arr) {
        // 创建堆
        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, arr.length);
        }

        // 调整堆结构+交换堆顶元素与末尾元素
        for (int i = arr.length - 1; i > 0; i--) {
            //将堆顶元素与末尾元素进行交换
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            //重新对堆进行调整
            adjustHeap(arr, 0, i);
        }
    }

    // 调整堆
    private static void adjustHeap(int[] arr, int start, int end) {
        int tmp = arr[start];
        int lChild = 2 * start + 1;
        while (lChild < end) {
            int rChild = lChild + 1;
            if (rChild < end && arr[lChild] < arr[rChild]) {
                lChild++;
            }
            if (tmp < arr[lChild]) {
                arr[start] = arr[lChild];
                start = lChild;
                lChild = 2 * lChild + 1;
            } else {
                break;
            }
        }
        arr[start] = tmp;
    }


    public static void main(String[] args) {
        heapSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}

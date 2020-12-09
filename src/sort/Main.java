package sort;

public class Main {
    private static int[] arr = {1,6,4,3,7,2,9,8,5};
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
        for (int i = arr.length - 1; i >= 0; i--) {
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


    // 快速排序
    public static void quickSort(int arr[], int left, int right) {
        // 递归出口，这里不能写成 left != right
        if (left < right) {
            int i, j, tmp;
            tmp = arr[left];
            i = left;
            j = right;
            while (i != j) {
                // 这里没有等于号，而且不要忘了i!=j的大条件
                while (arr[j] > tmp && i != j){
                    j--;
                }
                arr[i] = arr[j];

                while (arr[i] < tmp && i != j) {
                    i++;
                }
                arr[j] = arr[i];
            }
            arr[i] = tmp;     // tmp的操作类似插入排序
            quickSort(arr, left, i - 1);
            quickSort(arr, i + 1, right);
        }
    }


    public static void main(String[] args) {
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}

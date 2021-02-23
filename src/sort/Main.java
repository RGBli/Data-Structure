package sort;

public class Main {
    private static int[] arr = {1, 6, 4, 3, 7, 2, 9, 8, 5};
    private static int n = arr.length;

    /*简单插入排序*/
    public static void insert(int[] arr) {
        // i 是无序区的第一个元素
        for (int i = 1; i < n; i++) {
            int tmp = arr[i];
            // j 是有序区的最后一个元素
            int j = i - 1;
            while (j >= 0 && arr[j] > tmp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = tmp;
        }
    }


    /*冒泡排序*/
    public static void bubble(int[] arr) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }


    /*选择排序*/
    public static void select(int[] arr) {
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
    }


    /*堆排序*/
    private static void heapSort(int[] arr) {
        // 创建堆，如果是顺序排序就创建大根堆，逆序排序就创建小根堆
        // 因为堆是完全二叉树，所以第一个非叶子节点在 length / 2 - 1
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            // 从第一个非叶子结点从右至左，从下至上调整结构
            adjustHeap(arr, i, arr.length);
        }

        // 交换堆顶元素与末尾元素 + 调整堆结构
        for (int i = arr.length - 1; i >= 0; i--) {
            // 将堆顶元素与末尾元素进行交换
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            // 重新对堆进行调整
            adjustHeap(arr, 0, i);
        }
    }

    // 调整堆，调整的范围是 [start, end)
    private static void adjustHeap(int[] arr, int start, int end) {
        int tmp = arr[start];
        // 位置为 i 的左孩子节点在 2 * i + 1
        int lChild = 2 * start + 1;
        while (lChild < end) {
            int rChild = lChild + 1;
            // 找到左右孩子中大的那个的 index
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


    /*快速排序*/
    public static void quickSort(int[] arr, int left, int right) {
        // 递归出口
        if (left < right) {
            int i, j, tmp;
            // 取基准值
            tmp = arr[left];
            i = left;
            j = right;
            while (i < j) {
                // 先 j 后 i
                while (arr[j] > tmp && i < j) {
                    j--;
                }
                arr[i] = arr[j];

                while (arr[i] < tmp && i < j) {
                    i++;
                }
                arr[j] = arr[i];
            }
            arr[i] = tmp;
            // 此时小于 arr[i] 的元素都在 i 的左边，大于 arr[i] 的元素都在 i 的右边
            quickSort(arr, left, i - 1);
            quickSort(arr, i + 1, right);
        }
    }


    /*归并排序*/
    public static void mergeSort(int[] arr, int low, int high) {
        // 递归出口，与快排类似
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(arr, low, mid);
            mergeSort(arr, mid + 1, high);
            // 左右归并
            merge(arr, low, mid, high);
        }
    }

    // 合并左右两个有序数组
    // 左数组的范围是 [low, mid], 右数组的范围是 [mid + 1, high]
    public static void merge(int[] arr, int low, int mid, int high) {
        // 创建临时动态数组
        int[] tmp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            // 归并排序是稳定排序，这里一定是小于等于
            // 因为 i 在 j 的前面，稳定排序后也应该在 j 的前面
            if (arr[i] <= arr[j]) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            tmp[k++] = arr[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            tmp[k++] = arr[j++];
        }
        // 把新数组中的数覆盖 arr 数组
        /* for (int x = 0; x < tmp.length; x++) {
            arr[low + x] = tmp[x];
        }*/
        // 使用 System.arrayCopy() 更简洁
        System.arraycopy(tmp, 0, arr, low, tmp.length);
    }


    public static void main(String[] args) {
        //mergeSort(arr, 0, arr.length - 1);
        heapSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}

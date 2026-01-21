package phase4_heap;

public class MinHeap {

    private Episode[] heap;
    private int size;

    public MinHeap(int capacity) {
        heap = new Episode[capacity];
        size = 0;
    }

    /* درج اپیزود جدید */
    public void insert(String id, int priority) {
        Episode newEp = new Episode(id, priority);
        heap[size] = newEp;
        int current = size;
        size++;

        // بالا کشیدن (Heapify Up)
        while (current > 0) {
            int parent = (current - 1) / 2;
            if (heap[current].priority < heap[parent].priority) {
                swap(current, parent);
                current = parent;
            } else {
                break;
            }
        }
    }

    /* حذف کمترین عنصر */
    public Episode extractMin() {
        if (size == 0)
            return null;

        Episode min = heap[0];
        heap[0] = heap[size - 1];
        size--;

        heapifyDown(0);
        return min;
    }

    /* حذف اپیزود خاص */
    public boolean delete(String id) {
        int index = findIndex(id);
        if (index == -1)
            return false;

        heap[index] = heap[size - 1];
        size--;

        heapifyDown(index);
        return true;
    }

    /* مرتب‌سازی هیپ */
    public Episode[] heapSort() {
        Episode[] result = new Episode[size];
        int originalSize = size;

        for (int i = 0; i < result.length; i++) {
            result[i] = extractMin();
        }

        size = originalSize;
        return result;
    }

    /* نمایش هیپ */
    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i].id + "(" + heap[i].priority + ") ");
        }
        System.out.println();
    }

    /* ----------------- توابع کمکی ----------------- */

    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap[left].priority < heap[smallest].priority)
                smallest = left;

            if (right < size && heap[right].priority < heap[smallest].priority)
                smallest = right;

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private int findIndex(String id) {
        for (int i = 0; i < size; i++) {
            if (heap[i].id.equals(id))
                return i;
        }
        return -1;
    }

    private void swap(int i, int j) {
        Episode temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}

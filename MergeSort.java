public class MergeSort {
    static void merge(int index1, int index2, int index3, int index4, long[] a, long[] s) { //把下标分组
        if (index2 != index1) {
            int mid = (index1 + index2) / 2;
            merge(index1, mid, mid + 1, index2, a, s);
        }
        if (index3 != index4) {
            int mid = (index3 + index4) / 2;
            merge(index3, mid, mid + 1, index4,a,s);
        }
        int p = index1;
        int q = index3;
        int k = index1;
        while (p <= index2 && q <=index4) {
            if (a[p] <= a[q]) {//a[k++]=a1[p++]
                s[k] = a[p];
                p++;
                k++;
            } else {
                s[k] = a[q];
                q++;
                k++;
            }
        }
        while (p <= index2) {
            s[k++] = a[p++];
        }
        while (q <=index4) {
            s[k++] = a[q++];
        }
        for (int i = index1; i <=index4; i++) {
            a[i]=s[i];
        }
    }
}

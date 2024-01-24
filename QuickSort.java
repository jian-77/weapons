public class QuickSort {
    static void quicksort(int left,int right,long[]a){
        if (right-left>=1) {
            int index1 = left;
            int index2 = right;
            long temp = a[index1];
            while (index1 < index2) {
                while (a[index2] > temp) {
                    index2--;
                }
                if (index1 == index2) break;
                if (a[index1] <= temp) {
                    index1++;
                } else if (a[index1] > temp) {
                    long swap = a[index2];
                    a[index2] = a[index1];
                    a[index1] = swap;
                }
            }
            long swap = a[left];
            a[left] = a[index1];
            a[index1] = swap;
            quicksort(left, index1 - 1, a);
            quicksort(index1+ 1, right, a);
        }
    }
    //字典序quicksort compare方法可以自定义
    static void quicksortlex(int left,int right,long[]a){
        if (right-left>=1) {
            int index1 = left;
            int index2 = right;
            long temp = a[index1];
            while (index1 < index2) {
                while (compare(a[index2],temp)==true) {
                    index2--;
                }
                if (index1 == index2) break;
                if (compare(a[index1],temp)==false){
                    index1++;
                } else if (compare(a[index1],temp)==true) {
                    long swap = a[index2];
                    a[index2] = a[index1];
                    a[index1] = swap;
                }
            }
            long swap = a[left];
            a[left] = a[index1];
            a[index1] = swap;
            quicksort(left, index1 - 1, a);
            quicksort(index1+ 1, right, a);
        }
    }
    static boolean compare(long x,long y) {
        String a=String.valueOf(x);
        String b=String.valueOf(y);
        int result=a.compareTo(b);
        if (result==0||result<0){
            return false;
        }
        else return true;
    }
}

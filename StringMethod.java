public class StringMethod {
        public static int[] nextArray(String s) {
            int n = s.length();
            char[] c = s.toCharArray();
            int[] next = new int[n];
            next[0] = 0;
            int j = 0;//P里面的指针
            for (int i = 1; i < n; i++) {
                while (j != 0 && c[i] != c[j]) {
                    j = next[j - 1];
                }
                if (c[i] == c[j]) {
                    next[i] = j + 1;
                    j++;
                } else next[i] = 0;
            }
      return next;
    }
    //FSA
    public static void FSA(String s) {
        char[] c = s.toCharArray();
        int m = s.length();
        int x = 0;
        int[][] transfer = new int[m][26];
        transfer[0][(int) c[0] - 97] = 1;
        for (int j = 1; j < m; j++) {
            for (int i = 0; i < 26; i++) {
                if ((int) c[j] - 97 == i) {
                    transfer[j][i] = j + 1;
                } else {
                    transfer[j][i] = transfer[x][i];
                }
            }
            x = transfer[x][(int) c[j] - 97];
        }
    }
}

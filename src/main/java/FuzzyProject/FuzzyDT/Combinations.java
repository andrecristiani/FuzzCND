package main.java.FuzzyProject.FuzzyDT;

import java.util.Enumeration;

public class Combinations implements Enumeration {
    private Object[] inArray;
    private int n;
    private int m;
    private int[] index;
    private boolean hasMore = true;

    public Combinations(Object[] inArray, int m) throws CombinatoricException {
        this.inArray = inArray;
        this.n = inArray.length;
        this.m = m;
        Combinatoric.check(this.n, m);
        this.index = new int[m];

        for(int i = 0; i < m; this.index[i] = i++) {
        }

    }

    public boolean hasMoreElements() {
        return this.hasMore;
    }

    private void moveIndex() {
        int i = this.rightmostIndexBelowMax();
        if (i >= 0) {
            int var10002 = this.index[i]++;

            for(int j = i + 1; j < this.m; ++j) {
                this.index[j] = this.index[j - 1] + 1;
            }
        } else {
            this.hasMore = false;
        }

    }

    public Object nextElement() {
        if (!this.hasMore) {
            return null;
        } else {
            Object[] out = new Object[this.m];

            for(int i = 0; i < this.m; ++i) {
                out[i] = this.inArray[this.index[i]];
            }

            this.moveIndex();
            return out;
        }
    }

    private int rightmostIndexBelowMax() {
        for(int i = this.m - 1; i >= 0; --i) {
            if (this.index[i] < this.n - this.m + i) {
                return i;
            }
        }

        return -1;
    }
}

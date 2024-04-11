package org.example.landoflustrous.util;

import java.util.ArrayList;
import java.util.List;

public class Pair<L, R> {
    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public static <L, R> List<Pair<L, R>> generatePairs(List<L> list1, List<R> list2, int n) {
        List<Pair<L, R>> result = new ArrayList<>();
        int sum = 0;
        while (result.size() < n && sum < list1.size() + list2.size() - 1) {
            for (int i = 0; i <= sum; i++) {
                int j = sum - i;
                if (i < list1.size() && j < list2.size()) {
                    result.add(new Pair<>(list1.get(i), list2.get(j)));
                    if (result.size() >= n) {
                        return result;
                    }
                }
            }
            sum++;
        }
        return result;
    }

}



package org.example.juc.forkjoin.countedcompleter;

import org.junit.Test;

import java.util.concurrent.CountedCompleter;

public class MapReduceDemo {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3};
        //方法一.
        Integer result = new MapRed<>(null, array, (a) -> a + 2, (a, b) -> a + b, 0, array.length).invoke();
        System.out.println("方法一result:" + result);
        //方法二我就不抄了,就在官方注释上.
//        result = new MapReducer<>(null, array, (a) -> a + 1
//                , (a, b) -> a + b, 0, array.length, null).invoke();
//        System.out.println("方法二result:" + result);
    }


    /**
     * 第一种map reduce方式,很好理解.
     *
     * @param <E>
     */
    private static class MapRed<E> extends CountedCompleter<E> {
        final E[] array;
        final MyMapper<E> mapper;
        final MyReducer<E> reducer;
        final int lo, hi;
        MapRed<E> sibling;//兄弟节点的引用
        E result;

        MapRed(CountedCompleter<?> p, E[] array, MyMapper<E> mapper,
               MyReducer<E> reducer, int lo, int hi) {
            super(p);
            this.array = array;
            this.mapper = mapper;
            this.reducer = reducer;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        public void compute() {
            if (hi - lo >= 2) {
                int mid = (lo + hi) >>> 1;
                MapRed<E> left = new MapRed(this, array, mapper, reducer, lo, mid);
                MapRed<E> right = new MapRed(this, array, mapper, reducer, mid, hi);
                left.sibling = right;
                right.sibling = left;
                //只挂起右任务
                setPendingCount(1);
                right.fork();
                //直接运算左任务.
                left.compute();
            } else {
                if (hi > lo)
                    result = mapper.apply(array[lo]);
                //它会依次调用onCompletion.并且是自己调自己或completer调子,
                //且只有左右两个子后完成的能调成功(父任务的挂起数达到0).
                tryComplete();
            }
        }

        @Override
        public void onCompletion(CountedCompleter<?> caller) {
            //忽略自己调自己.
            if (caller != this) {
                //参数是子任务.
                MapRed<E> child = (MapRed<E>) caller;
                MapRed<E> sib = child.sibling;
                //设置父的result.
                if (sib == null || sib.result == null)
                    result = child.result;
                else
                    result = reducer.apply(child.result, sib.result);
            }
        }

        @Override
        public E getRawResult() {
            return result;
        }
    }

    //mapper和reducer简单的不能再简单.
    @FunctionalInterface
    private static interface MyMapper<E> {
        E apply(E e);
    }

    @FunctionalInterface
    private static interface MyReducer<E> {
        E apply(E a, E b);
    }

}

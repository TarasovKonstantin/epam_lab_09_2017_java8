package part1.exercise;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.StreamSupport;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[][] array;
    private int end;
    private int start;


    public RectangleSpliterator(int[][] array, int start, int end) {
       super(end - start, Spliterator.IMMUTABLE
                          | Spliterator.ORDERED
                          | Spliterator.SIZED
                          | Spliterator.SUBSIZED
                          | Spliterator.NONNULL);
        this.array = array;
        this.start = start;
        this.end = end;

    }

    public RectangleSpliterator(int[][] array) {
        this(array,0, (int) checkArrayAndCalcEstimatedSize(array));
    }

    private static long checkArrayAndCalcEstimatedSize(int[][] array) {
        if (Objects.isNull(array))
            throw new RuntimeException("array equals null");
        return array.length * array[0].length;
    }

    @Override
    public OfInt trySplit() {

        int length = end - start;
        if(length < 2){
            return null;
        }

        int middle = start + length/2;
        RectangleSpliterator res = new RectangleSpliterator(array, start, middle);
        start = middle;
        return res;
    }

    @Override
    public long estimateSize() {
        return end - start;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {

        if(start < end){
            int i = array[start / array[0].length][start % array[0].length];
            ++start;
            action.accept(i);
            return true;}
        return false;
        }


    public static void main(String[] args) {
        int[][]initArr=new int[][]{{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
        long res= StreamSupport.intStream(new RectangleSpliterator(initArr), true)
                .asLongStream()
                .sum();
        System.out.println(res);

    }

}

class A {

    protected String val;

    A() {
        setVal();
    }

    public void setVal() {
        val = "A";
    }
}

class B extends A {

    @Override
    public void setVal() {
        val = "B";
    }

    public static void main(String[] args) {
        System.out.println(new B().val);

    }
}
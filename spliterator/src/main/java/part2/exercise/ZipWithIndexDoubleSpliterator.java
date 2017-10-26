package part2.exercise;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

public class ZipWithIndexDoubleSpliterator extends Spliterators.AbstractSpliterator<IndexedDoublePair> {


    private final OfDouble inner;
    private int currentIndex;

    public ZipWithIndexDoubleSpliterator(OfDouble inner) {
        this(0, inner);
    }

    private ZipWithIndexDoubleSpliterator(int firstIndex, OfDouble inner) {
        super(inner.estimateSize(), inner.characteristics());
        currentIndex = firstIndex;
        this.inner = inner;
    }

    @Override
    public int characteristics() {
        // TODO
        //throw new UnsupportedOperationException();
        return inner.characteristics();
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedDoublePair> action) {
        // TODO
        //throw new UnsupportedOperationException();
        return inner.tryAdvance((DoubleConsumer) doubleConsumer -> {
            IndexedDoublePair pair = new IndexedDoublePair(currentIndex, doubleConsumer);
            currentIndex = currentIndex++;
            action.accept(pair);
        });
    }

    @Override
    public void forEachRemaining(Consumer<? super IndexedDoublePair> action) {
        // TODO
        //throw new UnsupportedOperationException();
        inner.forEachRemaining((DoubleConsumer) doubleConsumer -> {
            IndexedDoublePair pair = new IndexedDoublePair(currentIndex, doubleConsumer);
            currentIndex = currentIndex++;
            action.accept(pair);
        });
    }

    @Override
    public Spliterator<IndexedDoublePair> trySplit() {
        // TODO
        if (inner.hasCharacteristics(Spliterator.SUBSIZED)) {
            OfDouble splitResult = inner.trySplit();
            if (splitResult == null) {
                return null;
            }
            ZipWithIndexDoubleSpliterator spliterator = new ZipWithIndexDoubleSpliterator(currentIndex, splitResult);
            currentIndex += splitResult.estimateSize();
            return spliterator;
        } else {
            return super.trySplit();
        }
    }

    @Override
    public long estimateSize() {
        // TODO
        //throw new UnsupportedOperationException();
        return inner.estimateSize();
    }
}

package fastutil;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentLongSortedSet implements LongSortedSet {

    ConcurrentSkipListSet<Long> back = new ConcurrentSkipListSet<>();

    @Override
    public LongBidirectionalIterator iterator(long fromElement) {
        return null;
    }

    @Override
    public int size() {
        return back.size();
    }

    @Override
    public boolean isEmpty() {
        return back.isEmpty();
    }

    @Override
    public LongBidirectionalIterator iterator() {
        return null;
    }


    @Override
    public Object[] toArray() {
        return back.toArray();
    }


    @Override
    public <T> T[] toArray( T[] ts) {
        return null;
    }

    @Override
    public boolean containsAll( Collection<?> collection) {
        return back.containsAll(collection);
    }

    @Override
    public boolean addAll( Collection<? extends Long> collection) {
        return back.addAll(collection);
    }

    @Override
    public boolean removeAll( Collection<?> collection) {
        return back.removeAll(collection);
    }

    @Override
    public boolean retainAll( Collection<?> collection) {
        return back.retainAll(collection);
    }

    @Override
    public void clear() {
        back.clear();
    }

    @Override
    public boolean add(long key) {
        return back.add(key);
    }

    @Override
    public boolean contains(long key) {
        return back.contains(key);
    }

    @Override
    public long[] toLongArray() {
        return new long[0];
    }

    @Override
    public long[] toArray(long[] a) {
        return new long[0];
    }

    @Override
    public boolean addAll(LongCollection c) {
        return back.addAll(c);
    }

    @Override
    public boolean containsAll(LongCollection c) {
        return back.containsAll(c);
    }

    @Override
    public boolean removeAll(LongCollection c) {
        return back.removeAll(c);
    }

    @Override
    public boolean retainAll(LongCollection c) {
        return back.retainAll(c);
    }

    @Override
    public boolean remove(long k) {
        return back.remove(k);
    }

    @Override
    public LongSortedSet subSet(long fromElement, long toElement) {
        return new LongAVLTreeSet(back.subSet(fromElement,toElement));
    }

    @Override
    public LongSortedSet headSet(long toElement) {
        return new LongAVLTreeSet(back.headSet(toElement));
    }

    @Override
    public LongSortedSet tailSet(long fromElement) {
        return new LongAVLTreeSet(back.tailSet(fromElement));
    }

    @Override
    public LongComparator comparator() {
        return null;
    }

    @Override
    public long firstLong() {
        return back.first();
    }

    @Override
    public long lastLong() {
        return back.last();
    }
}

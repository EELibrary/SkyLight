package fastutil;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Int2ObjectConcurrentHashMap<V> implements Int2ObjectMap<V> {

    Map<Integer, V> backing;

    public Int2ObjectConcurrentHashMap() {
        backing = new ConcurrentHashMap<Integer, V>();
    }

    @Override
    public V get(int key) {
        return backing.get(key);
    }

    @Override
    public boolean isEmpty() {
        return backing.isEmpty();
    }

    @Override
    public boolean containsValue(Object value) {
        return backing.containsValue(value);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends V> m) {
        backing.putAll(m);
    }

    @Override
    public int size() {
        return backing.size();
    }

    @Override
    public void defaultReturnValue(V rv) {
        throw new NotImplementedException("MCMT - Not implemented");
    }

    @Override
    public V defaultReturnValue() {
        return null;
    }

    @Override
    public ObjectSet<Map.Entry<Integer, V>> entrySet() {
        return (ObjectSet<Map.Entry<Integer, V>>) backing.entrySet();
    }

    @Override
    public ObjectSet<Entry<V>> int2ObjectEntrySet() {
        return null;
    }


    @Override
    public IntSet keySet() {
        return new WrappingIntSet(backing.keySet());
    }
    public static class WrappingIntSet implements IntSet {

        Set<Integer> backing;

        public WrappingIntSet(Set<Integer> backing) {
            this.backing = backing;
        }

        @Override
        public boolean add(int key) {
            return backing.add(key);
        }

        @Override
        public boolean rem(int key) {
            return backing.remove(key);
        }

        @Override
        public boolean contains(int key) {
            return backing.contains(key);
        }

        @Override
        public int[] toIntArray() {
            return backing.stream().mapToInt(i -> i).toArray();
        }

        @Override
        public int[] toIntArray(int[] a) {
            if (a.length >= size()) {
                return null;
            } else {
                return toIntArray();
            }
        }

        @Override
        public int[] toArray(int[] a) {
            return toIntArray(a);
        }

        @Override
        public boolean addAll(IntCollection c) {
            return backing.addAll(c);
        }

        @Override
        public boolean containsAll(IntCollection c) {
            return backing.containsAll(c);
        }

        @Override
        public boolean removeAll(IntCollection c) {
            return backing.removeAll(c);
        }

        @Override
        public boolean retainAll(IntCollection c) {
            return backing.retainAll(c);
        }

        @Override
        public int size() {
            return backing.size();
        }

        @Override
        public boolean isEmpty() {
            return backing.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return backing.contains(o);
        }

        @Override
        public Object[] toArray() {
            return backing.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return backing.toArray(a);
        }

        @Override
        public boolean add(Integer integer) {
            return backing.add(integer);
        }

        @Override
        public boolean remove(Object o) {
            return backing.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return backing.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends Integer> c) {
            return backing.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return backing.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return backing.retainAll(c);
        }

        @Override
        public void clear() {
            backing.clear();
        }

        @Override
        public IntIterator iterator() {
            return new WrappingIntIterator(backing.iterator());
        }
        static class WrappingIntIterator implements IntIterator {

            Iterator<Integer> backing;

            public WrappingIntIterator(Iterator<Integer> backing) {
                this.backing = backing;
            }

            @Override
            public boolean hasNext() {
                return backing.hasNext();
            }

            @Override
            public int nextInt() {
                return backing.next();
            }

            @Override
            public int skip(int n) {
                return 0;
            }

            @Override
            public Integer next() {
                return backing.next();
            }

            @Override
            public void remove() {
                backing.remove();
            }

        }
        @Override
        public IntIterator intIterator() {
            return (IntIterator) backing.iterator();
        }

        @Override
        public boolean remove(int k) {
            return backing.remove(k);
        }

    }
    @Override
    public ObjectCollection<V> values() {
        return null;
    }

    @Override
    public boolean containsKey(int key) {
        return backing.containsKey(key);
    }

    @Override
    public V put(int key, V value) {
        return backing.put(key, value);
    }

    @Override
    public V put(Integer key, V value) {
        return backing.put(key, value);
    }

    @Override
    public V get(Object key) {
        return backing.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return backing.containsKey(key);
    }

    @Override
    public V remove(Object key) {
        return backing.remove(key);
    }

    @Override
    public V remove(int key) {
        return backing.remove(key);
    }

    @Override
    public void clear() { backing.clear(); }
}

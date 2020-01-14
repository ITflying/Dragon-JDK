package java.lang.string;

import com.sun.istack.internal.NotNull;
// todo: replace it when develop the stream
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * An sequence for char
 *
 * @author
 * @date 2020/1/3
 **/
public interface CharSequence {
    /**
     * return the length of the char sequence
     *
     * @return
     */
    int length();

    /**
     * return the value at specified index of char sequence
     *
     * @param index the index of the value to be returned
     * @return the specified char value
     * @throws IndexOutOfBoundsException
     */
    char charAt(int index);

    /**
     * return the value between the start index and end index of char sequence
     *
     * @param start the start index, inclusive
     * @param end   the end index, exclusive
     * @return the specified sub sequence
     * @throws IndexOutOfBoundsException
     */
    CharSequence subSequence(int start, int end);

    /**
     * returns a string containing the characters in this sequence in the same order as this sequence
     *
     * @return
     */
    @Override
    @NotNull
    public String toString();

    /**
     * return a stream of zero-extending the values from this sequenct
     *
     * @return
     */
    public default IntStream chars() {
        // PrimitiveIterator.OfInt:int iterator
        class CharIterator implements PrimitiveIterator.OfInt {
            int cur = 0;

            @Override
            public boolean hasNext() {
                return cur < length();
            }

            @Override
            public int nextInt() {
                if (hasNext()) {
                    return charAt(cur++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        }

        return StreamSupport.intStream(() ->
                        Spliterators.spliterator(
                                new CharIterator(),
                                length(),
                                Spliterator.ORDERED),
                Spliterator.SUBSIZED | Spliterator.SIZED | Spliterator.ORDERED,
                false
        );
    }

    /**
     * Returns a stream of code point values from this sequence
     *
     * @return an IntStream of Unicode code points from this sequence
     */
    public default IntStream codePoints() {
        class CodePointIterator implements PrimitiveIterator.OfInt {
            int cur = 0;

            @Override
            public void forEachRemaining(IntConsumer block) {
                final int length = length();
                int i = cur;
                try {
                    while (i < length) {
                        char c1 = charAt(i++);
                        if (!Character.isHighSurrogate(c1) || i >= length) {
                            block.accept(c1);
                        } else {
                            char c2 = charAt(i);
                            if (Character.isLowSurrogate(c2)) {
                                i++;
                                block.accept(Character.toCodePoint(c1, c2));
                            } else {
                                block.accept(c1);
                            }
                        }
                    }
                } finally {
                    cur = i;
                }
            }

            @Override
            public boolean hasNext() {
                return cur < length();
            }

            @Override
            public int nextInt() {
                final int length = length();

                if (cur >= length) {
                    throw new NoSuchElementException();
                }
                char c1 = charAt(cur++);
                if (Character.isHighSurrogate(c1) && cur < length) {
                    char c2 = charAt(cur);
                    if (Character.isLowSurrogate(c2)) {
                        cur++;
                        return Character.toCodePoint(c1, c2);
                    }
                }
                return c1;
            }
        }

        return StreamSupport.intStream(() ->
                        Spliterators.spliteratorUnknownSize(new CodePointIterator(),
                                Spliterator.ORDERED),
                Spliterator.ORDERED,
                false);
    }
}

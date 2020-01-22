package java.lang.string;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * A thread-safe, mutable sequence of character.
 *
 * @author
 * @date 2020/1/14
 **/
public class StringBuffer
        extends AbstractStringBuilder
        implements java.io.Serializable, CharSequence {
    /**
     * A cache of the last value returned by toString.
     * Cleared whenever the StringBuffer is modified.
     * Transient mean that cannot be serializable
     */
    private transient char[] toStringCache;

    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     **/
    static final long serialVersionUID = 3388685877147921107L;

    /**
     * Constructs a string buffer with no characters in it and an initial capacity of 16 characters.
     */
    public StringBuffer() {
        super(16);
    }

    /**
     * Constructs a string buffer with no characters in it and the specified initial capacity
     *
     * @param capacity
     */
    public StringBuffer(int capacity) {
        super(capacity);
    }

    /**
     * Constructs a string buffer initialized to the contents of the specified string.
     *
     * @param str
     */
    public StringBuffer(String str) {
        super(str.length() + 16);
        append(str);
    }

    /**
     * Constructs a string buffer that contains the same characters as the specified {@code CharSequence}.
     *
     * @param seq
     */
    public StringBuffer(CharSequence seq) {
        this(seq.length() + 16);
        append(seq);
    }

    @Override
    public synchronized int length() {
        return count;
    }

    @Override
    public synchronized int capacity() {
        return value.length;
    }

    @Override
    public synchronized void ensureCapacity(int minimumCapacity) {
        super.ensureCapacity(minimumCapacity);
    }

    @Override
    public synchronized void trimToSize() {
        super.trimToSize();
    }

    @Override
    public synchronized void setLength(int newLength) {
        toStringCache = null;
        super.setLength(newLength);
    }

    @Override
    public synchronized char charAt(int index) {
        if (index < 0 || index >= count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }

    @Override
    public synchronized int codePointAt(int index) {
        return super.codePointAt(index);
    }

    @Override
    public synchronized int codePointBefore(int index) {
        return super.codePointBefore(index);
    }

    @Override
    public synchronized int codePointCount(int beginIndex, int endIndex) {
        return super.codePointCount(beginIndex, endIndex);
    }

    @Override
    public synchronized int offsetByCodePoints(int index, int codePointOffset) {
        return super.offsetByCodePoints(index, codePointOffset);
    }

    @Override
    public synchronized void getChars(int srcBegin, int srcEnd, char[] dst,
                                      int dstBegin) {
        super.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    @Override
    public synchronized void setCharAt(int index, char ch) {
        if (index < 0 || index >= count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        toStringCache = null;
        value[index] = ch;
    }

    @Override
    public synchronized StringBuffer append(String str) {
        toStringCache = null;
        super.append(str);
        return this;
    }

    @Override
    public synchronized StringBuffer append(StringBuffer sb) {
        toStringCache = null;
        super.append(sb);
        return this;
    }

    /**
     * @since 1.8
     */
    @Override
    synchronized StringBuffer append(AbstractStringBuilder asb) {
        toStringCache = null;
        super.append(asb);
        return this;
    }

    @Override
    public synchronized StringBuffer append(CharSequence s) {
        toStringCache = null;
        super.append(s);
        return this;
    }

    @Override
    public synchronized StringBuffer append(CharSequence s, int start, int end) {
        toStringCache = null;
        super.append(s, start, end);
        return this;
    }

    @Override
    public synchronized StringBuffer append(char[] str) {
        toStringCache = null;
        super.append(str);
        return this;
    }

    @Override
    public synchronized StringBuffer append(char[] str, int offset, int len) {
        toStringCache = null;
        super.append(str, offset, len);
        return this;
    }

    @Override
    public synchronized StringBuffer append(boolean b) {
        toStringCache = null;
        super.append(b);
        return this;
    }

    @Override
    public synchronized StringBuffer append(char c) {
        toStringCache = null;
        super.append(c);
        return this;
    }

    @Override
    public synchronized StringBuffer append(int i) {
        toStringCache = null;
        super.append(i);
        return this;
    }


    @Override
    public synchronized StringBuffer appendCodePoint(int codePoint) {
        toStringCache = null;
        super.appendCodePoint(codePoint);
        return this;
    }

    @Override
    public synchronized StringBuffer append(long lng) {
        toStringCache = null;
        super.append(lng);
        return this;
    }

    @Override
    public synchronized StringBuffer append(float f) {
        toStringCache = null;
        super.append(f);
        return this;
    }

    @Override
    public synchronized StringBuffer append(double d) {
        toStringCache = null;
        super.append(d);
        return this;
    }


    @Override
    public synchronized StringBuffer delete(int start, int end) {
        toStringCache = null;
        super.delete(start, end);
        return this;
    }

    @Override
    public synchronized StringBuffer deleteCharAt(int index) {
        toStringCache = null;
        super.deleteCharAt(index);
        return this;
    }

    @Override
    public synchronized StringBuffer replace(int start, int end, String str) {
        toStringCache = null;
        super.replace(start, end, str);
        return this;
    }

    @Override
    public synchronized String substring(int start) {
        return substring(start, count);
    }

    @Override
    public synchronized CharSequence subSequence(int start, int end) {
        // todo:because of the string is not impletement CharSequence,string cann`t be return type
        return null;
//        return super.substring(start,end);
    }

    @Override
    public synchronized String substring(int start, int end) {
        return super.substring(start, end);
    }

    @Override
    public synchronized StringBuffer insert(int index, char[] str, int offset,
                                            int len) {
        toStringCache = null;
        super.insert(index, str, offset, len);
        return this;
    }

    @Override
    public synchronized StringBuffer insert(int offset, Object obj) {
        toStringCache = null;
        super.insert(offset, String.valueOf(obj));
        return this;
    }

    @Override
    public synchronized StringBuffer insert(int offset, String str) {
        toStringCache = null;
        super.insert(offset, str);
        return this;
    }

    @Override
    public synchronized StringBuffer insert(int offset, char[] str) {
        toStringCache = null;
        super.insert(offset, str);
        return this;
    }

    @Override
    public StringBuffer insert(int dstOffset, CharSequence s) {
        super.insert(dstOffset, s);
        return this;
    }

    @Override
    public synchronized StringBuffer insert(int dstOffset, CharSequence s,
                                            int start, int end) {
        toStringCache = null;
        super.insert(dstOffset, s, start, end);
        return this;
    }

    @Override
    public StringBuffer insert(int offset, boolean b) {
        super.insert(offset, b);
        return this;
    }

    @Override
    public synchronized StringBuffer insert(int offset, char c) {
        toStringCache = null;
        super.insert(offset, c);
        return this;
    }


    @Override
    public StringBuffer insert(int offset, int i) {
        super.insert(offset, i);
        return this;
    }

    @Override
    public StringBuffer insert(int offset, long l) {
        super.insert(offset, l);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuffer insert(int offset, float f) {
        super.insert(offset, f);
        return this;
    }

    @Override
    public StringBuffer insert(int offset, double d) {
        super.insert(offset, d);
        return this;
    }


    @Override
    public int indexOf(String str) {
        return super.indexOf(str);
    }


    @Override
    public synchronized int indexOf(String str, int fromIndex) {
        return super.indexOf(str, fromIndex);
    }


    @Override
    public int lastIndexOf(String str) {
        return lastIndexOf(str, count);
    }

    @Override
    public synchronized int lastIndexOf(String str, int fromIndex) {
        return super.lastIndexOf(str, fromIndex);
    }


    @Override
    public synchronized StringBuffer reverse() {
        toStringCache = null;
        super.reverse();
        return this;
    }

    public synchronized String toString() {
        if (toStringCache == null) {
            toStringCache = Arrays.copyOfRange(value, 0, count);
        }
        // todo: string
//        return new String(toStringCache, true);
        return null;
    }

    private static final java.io.ObjectStreamField[] serialPersistendFields = {
            new java.io.ObjectStreamField(("value"), char[].class),
            new java.io.ObjectStreamField("count", Integer.TYPE),
            new java.io.ObjectStreamField("shared", Integer.TYPE),
    };

    private synchronized void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        java.io.ObjectOutputStream.PutField field = s.putFields();
        field.put("value", value);
        field.put("count", count);
        field.put("shared", false);
        s.writeFields();
    }

    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
        java.io.ObjectInputStream.GetField fields = s.readFields();
        value = (char[]) fields.get("value", null);
        count = fields.get("count", 0);
    }
}

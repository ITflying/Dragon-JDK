package java.lang.string;


/**
 * A variable character sequence
 * No-Thread safe
 *
 * @author
 * @date 2020/1/3
 **/
public final class StringBuilder
        extends AbstractStringBuilder
        implements java.io.Serializable, CharSequence {
    // region setting

    /**
     * use serialVersionUID for interoperability
     */
    static final long seriaVersionUID = 4383685877147921099L;

    /**
     * Constructs a string builder with no characters in it and an initial capacity of 16 characters
     */
    public StringBuilder() {
        super(16);
    }

    /**
     * Constructs a string builder with no characters in it and an inital capactiy specifed by the {@code capacity} argument
     *
     * @param capacity the initial capacity
     */
    public StringBuilder(int capacity) {
        super(capacity);
    }

    /**
     * Constructs a string builder initialized to the contents of the specified string
     *
     * @param str the initial contents of the buffer
     */
    public StringBuilder(String str) {
        super(str.length() + 16);
        append(str);
    }

    /**
     * Constructs a string builder that contains the same characters as the specified CharSequence
     *
     * @param seq the sequence to copy
     */
    public StringBuilder(CharSequence seq) {
        this(seq.length() + 16);
        append(seq);
    }

    // endregion

    /// region public function

    /**
     * Appends the specified {@code object} to this sequence
     *
     * @param obj the object wait convert to  copy
     */
    public StringBuilder(Object obj) {
        append(String.valueOf(obj));
    }

    @Override
    public StringBuilder append(String str) {
        super.append(str);
        return this;
    }

    /**
     * Appends the specified {@code StringBuffer} to this sequence
     *
     * @param sb
     * @return
     */
    public StringBuilder append(StringBuilder sb) {
        super.append(sb);
        return this;
    }

    @Override
    public StringBuilder append(CharSequence s) {
        super.append(s);
        return this;
    }

    @Override
    public StringBuilder append(CharSequence s, int start, int end) {
        super.append(s, start, end);
        return this;
    }

    @Override
    public StringBuilder append(char[] s) {
        super.append(s);
        return this;
    }

    @Override
    public StringBuilder append(char[] s, int offset, int len) {
        super.append(s, offset, len);
        return this;
    }

    @Override
    public StringBuilder append(boolean b) {
        super.append(b);
        return this;
    }

    @Override
    public StringBuilder append(char c) {
        super.append(c);
        return this;
    }

    @Override
    public StringBuilder append(int i) {
        super.append(i);
        return this;
    }

    @Override
    public StringBuilder append(long lng) {
        super.append(lng);
        return this;
    }

    @Override
    public StringBuilder append(float f) {
        super.append(f);
        return this;
    }

    @Override
    public StringBuilder append(double d) {
        super.append(d);
        return this;
    }

    @Override
    public StringBuilder appendCodePoint(int codePoint){
        super.appendCodePoint(codePoint);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder delete(int start, int end) {
        super.delete(start, end);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder deleteCharAt(int index) {
        super.deleteCharAt(index);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder replace(int start, int end, String str) {
        super.replace(start, end, str);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int index, char[] str, int offset,
                                int len) {
        super.insert(index, str, offset, len);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, Object obj) {
        super.insert(offset, obj);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, String str) {
        super.insert(offset, str);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, char[] str) {
        super.insert(offset, str);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int dstOffset, CharSequence s) {
        super.insert(dstOffset, s);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int dstOffset, CharSequence s,
                                int start, int end) {
        super.insert(dstOffset, s, start, end);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, boolean b) {
        super.insert(offset, b);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, char c) {
        super.insert(offset, c);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, int i) {
        super.insert(offset, i);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, long l) {
        super.insert(offset, l);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, float f) {
        super.insert(offset, f);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public StringBuilder insert(int offset, double d) {
        super.insert(offset, d);
        return this;
    }

    @Override
    public int indexOf(String str) {
        return super.indexOf(str);
    }

    @Override
    public int indexOf(String str, int fromIndex) {
        return super.indexOf(str, fromIndex);
    }

    @Override
    public int lastIndexOf(String str) {
        return super.lastIndexOf(str);
    }

    @Override
    public int lastIndexOf(String str, int fromIndex) {
        return super.lastIndexOf(str, fromIndex);
    }

    @Override
    public StringBuilder reverse() {
        super.reverse();
        return this;
    }

    @Override
    public String toString() {
        // Create a copy, don't share the array
        return new String(value, 0, count);
    }

    // endregion
    
    // region private 

    /**
     * save the state of the {@code StringBuilder} instatnce to a stream
     */
    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(count);
        s.writeObject(value);
    }

    /**
     * readObject is called to restore the state of the StringBuffer from a stream
     */
    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        count = s.readInt();
        value = (char[]) s.readObject();
    }

    // endregion


}

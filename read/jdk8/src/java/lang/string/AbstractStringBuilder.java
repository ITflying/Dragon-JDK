package java.lang.string;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * This is an abstract class for stringbuiler
 *
 * @author
 * @date 2020/1/3
 **/
abstract class AbstractStringBuilder implements Appendable, CharSequence {
    // region config

    char[] value;

    int count;

    AbstractStringBuilder() {
    }

    AbstractStringBuilder(int capacity) {
        value = new char[capacity];
    }

    // endregion 

    /**
     * returns the length of char sequenct
     *
     * @return the lenght of the sequence of characters crr
     */
    @Override
    public int length() {
        return count;
    }

    /**
     * returns the current capacity
     *
     * @return
     */
    public int capacity() {
        return value.length;
    }

    /**
     * enusres that the capacity is at least equal to hte specifed minimum
     *
     * @param minimumCapacity thie minimum desired cap
     */
    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > 0) {
            ensureCapacityInternal(minimumCapacity);
        }
    }

    /**
     * for positive values of minimumCapacity
     *
     * @param minimumCapacity
     */
    private void ensureCapacityInternal(int minimumCapacity) {
        if (minimumCapacity - value.length > 0) {
            value = Arrays.copyOf(value, newCapacity(minimumCapacity));
        }
    }

    /**
     * The maximum size of array to allocate
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Return a capacity at least as large as the given minimum capacity
     *
     * @param minCapacity the desired minimum capacity
     * @return
     */
    private int newCapacity(int minCapacity) {
        int newCapacity = (value.length << 1) + 2;
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0)
                ? hugeCapacity(minCapacity)
                : newCapacity;
    }

    /**
     * return the hug num of capacity
     *
     * @param minCapacity the desired minimum capacity
     * @return
     */
    private int hugeCapacity(int minCapacity) {
        if (Integer.MAX_VALUE - minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ? minCapacity : MAX_ARRAY_SIZE;
    }

    /**
     * attempts to reduce storage used for the charater sequence
     */
    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }

    /**
     * Sets the length of the character sequence
     *
     * @param newLength the new length
     */
    public void setLength(int newLength) {
        if (newLength < 0) {
            throw new StringIndexOutOfBoundsException(newLength);
        }
        ensureCapacityInternal(newLength);

        if (count < newLength) {
            Arrays.fill(value, count, newLength, '\0');
        }

        count = newLength;
    }

    /**
     * Returns the value in the sequence at the specified index
     *
     * @param index the index of the desired value
     * @return the value the specified index
     */
    @Override
    public char charAt(int index) {
        if ((index < 0) || (index) > count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }

    /**
     * returns the character at the specified index
     *
     * @param index
     * @return
     */
    public int codePointAt(int index) {
        if ((index < 0) || (index) > count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        // todo：String结束后就是基础类型，再之后就是Integer等包装类型
//        return Character.codePointAtImpl(value, index, count);
        return 0;
    }

    /**
     * returns the character before the specified index.
     *
     * @param index the index following the code point that should be returned
     * @return the unicode code pint value before the given index
     */
    public int codePointBefore(int index) {
        int i = index - 1;
        if ((i < 0) || (i) > count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        // todo：String结束后就是基础类型，再之后就是Integer等包装类型
//        return Character.codePointBeforeImpl(value, index, 0);
        return 0;
    }

    /**
     * returns the number of Unicode code points in the specified text range of this sequence
     *
     * @param beginIndex the index to the first of the text range
     * @param endIndex   the index to the last of the text range
     * @return th number of Unicode code points in the specified text range
     */
    public int codePointCount(int beginIndex, int endIndex) {
        if (beginIndex < 0 || endIndex > count || beginIndex > endIndex) {
            throw new StringIndexOutOfBoundsException();
        }
        // todo：String结束后就是基础类型，再之后就是Integer等包装类型
//        return Character.codePointCountImpl(value, index, count);
        return 0;
    }

    /**
     * return the index within this sequence that is offset from the given code pints
     *
     * @param index           the index to be offset
     * @param codePointOffset the offset in code points
     * @return the index within this sequence
     */
    public int offsetByCodePoints(int index, int codePointOffset) {
        if ((index < 0) || (index) > count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        // todo：String结束后就是基础类型，再之后就是Integer等包装类型
//        return Character.offsetByCodePointsImpl();
        return 0;
    }

    /**
     * characters are copied from this sequence into the destination character array
     *
     * @param srcBegin start copying at this offset
     * @param srcEnd   stop copying at this offset
     * @param dst      the array to copy the data into
     * @param dstBegin offset into {@code dst}
     */
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        if (srcBegin < 0) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcEnd < 0 || srcEnd < count) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        }
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    /**
     * The character at the specified index is set to {@code ch}.
     *
     * @param index the index of the character to modify
     * @param ch    the new character
     */
    public void setCharAt(int index, char ch) {
        if (index < 0 || index > count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        value[index] = ch;
    }

    /**
     * Appends the string representation of the {@code Object} argument
     *
     * @param obj an {@code Object}
     * @return a reference to this object
     */
    public AbstractStringBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }

    /**
     * Appends th specified string to this character sequence
     *
     * @param str a string
     * @return a reference to this object
     */
    public AbstractStringBuilder append(String str) {
        if (str == null) {
            return appendNull();
        }
        int len = str.length();
        ensureCapacityInternal(count + len);
        str.getChars(0, len, value, count);
        count += len;
        return this;
    }

    public AbstractStringBuilder append(StringBuffer sb) {
        if (sb == null) {
            return appendNull();
        }
        int len = sb.length();
        ensureCapacityInternal(count + len);
        sb.getChars(0, len, value, count);
        count += len;
        return this;
    }

    AbstractStringBuilder append(AbstractStringBuilder asb) {
        if (asb == null) {
            return appendNull();
        }
        int len = asb.length();
        ensureCapacityInternal(count + len);
        asb.getChars(0, len, value, count);
        count += len;
        return this;
    }

    // documentation in subclasses because of synchro difference
    @Override
    public AbstractStringBuilder append(CharSequence s) {
        if (s == null) {
            return appendNull();
        }
//        if (s instanceof String){
//            return this.append((String) s);
//        }
        if (s instanceof AbstractStringBuilder) {
            return this.append((AbstractStringBuilder) s);
        }
        return this.append(s, 0, s.length());
    }

    private AbstractStringBuilder appendNull() {
        int c = count;
        ensureCapacityInternal(c + 4);
        final char[] value = this.value;
        value[c++] = 'n';
        value[c++] = 'u';
        value[c++] = 'l';
        value[c++] = 'l';
        count = c;
        return this;
    }

    /**
     * Appends a subsequence of the specified {@code CharSequence} to this sequence
     *
     * @param s     the sequence to append
     * @param start the starting index of the subsequence to be appended
     * @param end   the end index of the subsequence to be appended
     * @return a reference to this object
     */
    @Override
    public AbstractStringBuilder append(CharSequence s, int start, int end) {
        // todo:等stringbuilder好了
        if (s == null) {
//            s = new StringBuilder("null");
        }
        return null;
    }

    /**
     * Appends the string representation of the char array argument to this sequence
     *
     * @param str the characters to be appended
     * @return
     */
    public AbstractStringBuilder append(char[] str) {
        int len = str.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(str, 0, value, count, len);
        count += len;
        return this;
    }

    /**
     * Appends the string representation of a subarray of the char array argument to this sequence
     *
     * @param str    the characters to be appended
     * @param offset the index of the first char to append
     * @param len    the number of char to append
     * @return a reference to this object
     */
    public AbstractStringBuilder append(char str[], int offset, int len) {
        if (len > 0) {
            ensureCapacityInternal(count + len);
        }
        System.arraycopy(str, offset, value, count, len);
        count += len;
        return this;
    }

    /**
     * Appends the string representation of the boolean argument to this sequence
     *
     * @param b a boolean
     * @return  a reference to this object
     */
    public AbstractStringBuilder append(boolean b) {
        if (b) {
            ensureCapacityInternal(count + 4);
            value[count++] = 't';
            value[count++] = 'r';
            value[count++] = 'u';
            value[count++] = 'e';
        } else {
            ensureCapacityInternal(count + 5);
            value[count++] = 'f';
            value[count++] = 'a';
            value[count++] = 'l';
            value[count++] = 's';
            value[count++] = 'e';
        }
        return this;
    }

    /**
     * Appends the string representation of the char argument to this sequence
     *
     * @param c a char
     * @return  a reference to this object
     */
    @Override
    public AbstractStringBuilder append(char c) {
        ensureCapacityInternal(count + 1);
        value[count++] = c;
        return this;
    }

    /**
     * Appends the string representation of the argument to this sequencee
     *
     * @param i an int
     * @return  a reference to this object
     */
    public AbstractStringBuilder append(int i) {
        if (i == Integer.MIN_VALUE) {
            append("-2147483648");
        }
        // todo
//        int appendedLength = (i < 0) ? Integer.stringSize(-i) + 1
//                : Integer.stringSize(i);
//        int spaceNeeded = count + appendedLength;
//        ensureCapacityInternal(spaceNeeded);
//        Integer.getChars(i, spaceNeeded, value);
//        count = spaceNeeded;
        return this;
    }

    /**
     * Appends the string representation of the long argument to this sequence
     *
     * @param l a {@code long}
     * @return  a reference to this object
     */
    public AbstractStringBuilder append(long l) {
        if (l == Long.MIN_VALUE) {
            append("-9223372036854775808");
            return this;
        }
        // todo
//        int appendedLength = (1 < 0)
        return this;
    }
    
    /**
     * Appends the string representation of the float argument to this sequence
     *
     * @param f a {@code float}
     * @return  a reference to this object
     */
    public AbstractStringBuilder append(float f) {
        // todo
//        FloatingDecimal.appendTo(f, this);
        return this;
    }

    /**
     * Appends the string representation of the double argument to this sequence
     *
     * @param d a {@code double}
     * @return  a reference to this object
     */
    public AbstractStringBuilder append(double d) {
        // todo
//        FloatingDecimal.appendTo(f, this);
        return this;
    }

    /**
     * Removes the characters in a substring of this sequence
     * @param start The beginning index, inclusive
     * @param end   The ending index, exclusive
     * @return
     */
    public AbstractStringBuilder delete(int start, int end){
        if (start < 0){
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > count){
            end = count;
        }
        if (start > end){
            throw new StringIndexOutOfBoundsException();
        }
        int len = end - start;
        if (len > 0){
            System.arraycopy(value, start+len, value, start,count-end);
            count -= len;
        }
        return this;
    }

    /**
     * Appends the string representation of the codePoint argument to this sequence
     * @param codePoint a Unicode code point
     * @return a reference to this object
     * @throws IllegalAccessException if the specified codePoint isn't  a vlid Unicode code point
     */
    public AbstractStringBuilder appendCodePoint(int codePoint){
        final int count = this.count;
        
        if (Character.isBmpCodePoint(codePoint)){
            ensureCapacityInternal(count + 1);
            value[count] = (char) codePoint;
            this.count = count + 1;
        }else if (Character.isValidCodePoint(codePoint)){
            ensureCapacityInternal(count + 2);
            // TODO: 2020/1/10  
//            Character.toSurrogates(codePoint, value, count);
            this.count = count + 2;
        }else{
            throw new IllegalArgumentException();
        }
        return this;
    }

    /**
     * Remove the char ath the specified position in this sequence
     * @param index index of this sequence to remove
     * @return This object
     */
    public AbstractStringBuilder deleteCharAt(int index){
        if (index < 0 || index >= count){
            throw new StringIndexOutOfBoundsException(index);
        }
        System.arraycopy(value, index+1, value, index, count-index-1);
        count--;
        return this;
    }

    /**
     * Replaces the characters in a substring of this sequence with characters in the specified
     * @param start The beginning index, inclusive
     * @param end   The ending index,exclusive
     * @param str   String that will repace previous contents
     * @return This object
     */
    public AbstractStringBuilder replace(int start, int end, String str){
        if (start < 0){
            throw new StringIndexOutOfBoundsException(start);
        }
        if (start > count){
            throw new StringIndexOutOfBoundsException(("start > length()"));
        }
        if (start > end){
            throw new StringIndexOutOfBoundsException("start > end");
        }
        
        if (end > count){
            end = count;
        }
        int len = str.length();
        int newCount = count + len - (end - start);
        ensureCapacityInternal(newCount);
        
        System.arraycopy(value, end ,value, start + len,count - end);
        // todo
//        str.getChars(value, start);
        count = newCount;
        return this;
    }
    
    /**
     * Returns a new string that contains a subsequence of characters currently contained in this character sequence
     * @param start The beginning index, inclusvie
     * @return The new string
     */
    public String substring(int start){
//        return substring(start, count);
        return null;
    }

    /**
     * returns a new character sequence that is a subsequence of this sequence
     * @param start the start index, inclusive
     * @param end   the end index, exclusive
     * @return the specified subsequence
     */
    // todo
    @Override
    public CharSequence subSequence(int start, int end){
        return null;
//        return substring(start, end);
    }

    /**
     * returns a new string that contains a subsequence of character currently contained in this sequence
     * @param start the start index, inclusive
     * @param end   the end index, exclusive
     * @return the new String
     */
    public String substring(int start, int end){
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > count) {
            throw new StringIndexOutOfBoundsException(end);
        }
        if (start > end) {
            throw new StringIndexOutOfBoundsException(end - start);
        }
        return new String(value, start, end - start);
    }

    /**
     * inserts the string representation of a subarray of the array argument into this sequencee
     * @param index   position at which to insert subarray
     * @param str     a char array
     * @param offset  the index of the first char in subarray to be inserted
     * @param len     the number of char in the subarray to be inserted
     * @return This object
     */
    public AbstractStringBuilder insert(int index,char[] str,int offset,int len){
        if (index < 0 || index > length()){
            throw new StringIndexOutOfBoundsException(index);
        }
        if (offset < 0 || len < 0 || offset > str.length - len){
            throw new StringIndexOutOfBoundsException("offset " + offset + ", len"+len+", str.length"+str.length);
        }
        ensureCapacityInternal(count + len);
        System.arraycopy(value, index, value, index + len, count - index);
        System.arraycopy(str, offset, value, index, len);
        count +=len;
        return this;
    }

    /**
     * Inserts the string representation of the argument into this character sequence
     * @param offset the offset
     * @param obj    an object
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int offset, Object obj){
        return insert(offset, String.valueOf(obj));
    }

    /**
     * inserts the string into the character sequence
     * @param offset the offset
     * @param str    a string
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int offset, String str){
        if (offset < 0 || offset > length()){
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (str == null){
            str = "null";
        }
        int len = str.length();
        ensureCapacityInternal(count + len);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        // TODO: 2020/1/10  
//        str.getChars(value, offset);
        count += len;
        return this;
    }

    /**
     * insert the string representation of the char array argument into this sequence
     * @param offset the offset
     * @param str    a character array
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int offset, char[] str) {
        if (offset < 0 || offset > length()){
            throw new StringIndexOutOfBoundsException(offset);
        }
        int len = str.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        // TODO: 2020/1/10  
//        str.getChars(value, offset);
        count += len;
        return this;
    }

    /**
     * Inserts the specified charsequence into this sequence
     * @param dstOffset the offset
     * @param s         the sequence to be inserted
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int dstOffset, CharSequence s ){
//        if (s == null){
//            s="null";
//        }
//        if (s instanceof String){
//            return this.insert(dstOffset, (String) s);
//        }
        return this.insert(dstOffset, s, 0, s.length());
    }

    /**
     * inserts a subsequence of the specified CharSequence into this sequence
     * @param dstOffset the offset in this sequence
     * @param s         the sequence to be inserted
     * @param start     the starting index of the subsequence to be inserted
     * @param end       the ending index of the subsequence to be inserted
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int dstOffset, CharSequence s, 
                                        int start, int end){
        if (s == null){
            // TODO: 2020/1/10  
//            s = "null";
        }
        if (dstOffset < 0 || dstOffset > this.length()){
            throw new IndexOutOfBoundsException("dstOffset"+dstOffset);
        }
        if (start < 0 || end < 0 || start < end || end > s.length()){
            throw new IndexOutOfBoundsException(
                    "start " + start + ", end " + end + ", s.length() "
                            + s.length());
        }
        int len = end - start;
        ensureCapacityInternal(count + len);
        System.arraycopy(value, dstOffset, value, dstOffset + len,
                count - dstOffset);
        for (int i =start;i<end;i++){
            value[dstOffset++] = s.charAt(i);
        }
        count += len;
        return this;
    }

    /**
     * Inserts the string representation of the argument into this sequence
     * @param offset the offset
     * @param b      a {@code boolean}
     * @return       a reference to this object
     */
    public AbstractStringBuilder insert(int offset, boolean b){
        return insert(offset, String.valueOf(b));
    }

    /**
     * Inserts the string representation of the {@code char} argument into this sequence
     * @param offset    the offset
     * @param c         a {@code char}
     * @return  a reference to this object
     */
    public AbstractStringBuilder insert(int offset, char c){
        ensureCapacityInternal(count + 1);
        System.arraycopy(value, offset, value, offset+1,count-offset);
        value[offset] = c;
        count += 1;
        return this;
    }

    /**
     * Inserts the string representation of the {@code int} argument into this sequence
     * @param offset the offset
     * @param i      a {@code int}
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int offset,int i){
        return insert(offset, String.valueOf(i));
    }

    /**
     * Inserts the string representation of the {@code long} argument into this sequence
     * @param offset the offset
     * @param l      a {@code long}
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int offset, long l){
        return insert(offset,String.valueOf(l));
    }

    /**
     * Inserts the string representation of the {@code float} argument into this sequence
     * @param offset the offset
     * @param f      a {@code float}
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int offset, float f){
        return insert(offset,String.valueOf(f));
    }

    /**
     * Inserts the string representation of the {@code double} argument into this sequence
     * @param offset the offset
     * @param d      a {@code double}
     * @return a reference to this object
     */
    public AbstractStringBuilder insert(int offset, double d){
        return insert(offset,String.valueOf(d));
    }

    /**
     * return the index within this string of the first occurrence of th specified substring
     * @param str any string
     * @return  
     */
    public int indexOf(String str){
        return indexOf(str, 0);
    }

    /**
     * return the index within this string of the first occurence of th specified substring, starting of the first occurence of the specified substring
     * @param str       any string
     * @param fromIndex the index from which to start the search
     * @return the index within this string of the first occurence of the specified substring, starting at the specified index
     */
    public int indexOf(String str, int fromIndex){
        // TODO: 2020/1/10  
//        return String.valueOf(value, 0, count, str, fromIndex);
        return 0;
    }

    /**
     * returns the index within this string of the rightmost occurrence of the specified substring
     * @param str  the substring to search for
     * @return     the first character of the last such substring is returned
     */
    public int lastIndexOf(String str){
        return lastIndexOf(str, count);
    }

    /**
     * returns the index within this string of the last occurrence of the specified substring
     * @param str           the substring to search for
     * @param fromIndex     the index to start the search from
     * @return              the first character of the last such substring is returned
     */
    public int lastIndexOf(String str, int fromIndex){
        // TODO: 2020/1/10  
//        return String.lastIndexOf(value, 0, count, str, fromIndex);
        return 0;
    }
    
    public AbstractStringBuilder reverse(){
        boolean hasSurrogates = false;
        int n = count - 1;
        for (int j = (n - 1) >>1;j >=0; j--){
            int k = n -j;
            char cj = value[j];
            char ck = value[k];
            value[j] = ck;
            value[k] = cj;
            if (Character.isSurrogate(cj) || Character.isSurrogate(ck)){
                hasSurrogates = true;
            }
        }
        if (hasSurrogates){
            reverseAllValidSurrogatePairs();
        }
        return this;
    }
    
    private void reverseAllValidSurrogatePairs(){
        for (int i = 0;i<count - 1;i++){
            char c2 = value[i];
            if (Character.isLowSurrogate(c2)){
                char c1 = value[i+1];
                if (Character.isHighSurrogate(c1)){
                    value[i++] = c1;
                    value[i] = c2;
                }
            }
        }
    }

    /**
     * returns a string representing the data in this sequence
     * @return
     */
    @Override
    public abstract String toString();

    /**
     * needed by {@code String} for the contentEquals method
     * @return
     */
    final char[] getValue(){return value;}
}


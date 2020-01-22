package java.lang.string;

import com.sun.istack.internal.NotNull;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.CharConversionException;
import java.io.ObjectStreamField;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code String} class represents character string.
 * All string literalas in Java programs, such as {@code "abc"},
 * are implemented as instances of this class.
 *
 * @date 2020/1/14
 **/
public final class String
        implements java.io.Serializable, Comparable<String>, CharSequence {
    /**
     * The value is used for character storage
     **/
    private final char value[];

    /**
     * Cache the hash code for the string;
     **/
    private int hash; // Default to 0

    private static final long serialVersionUID = -6849794470754667710L;

    /**
     * Class string is special cased within the Serialization Stream Protocol
     **/
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];

    /**
     * Initializes a newly created {@code String} object so that it represents an empty character sequence
     */
    public String() {
        this.value = new char[]{'\0'};
    }

    /**
     * Initializes a newly created {@code String} object
     * so that it represents the same sequencee of charaters as the argument.
     *
     * @param original
     */
    public String(String original) {
        this.value = original.value;
        this.hash = original.hash;
    }

    /**
     * Allocates a new {@code String} so that it represents the sequence of characters
     * currently contained in the character array argument.
     *
     * @param value
     */
    public String(char value[]) {
        this.value = Arrays.copyOf(value, value.length);
    }

    /**
     * Allocates a new {@code String} that contains characters from a subarray of the character array argument.
     *
     * @param value
     * @param offset
     * @param count
     */
    public String(char value[], int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (count < 0) {
            if (count < 0) {
                throw new StringIndexOutOfBoundsException(count);
            }
            if (offset <= value.length) {
                this.value = new char[]{'\0'};
                return;
            }
        }
        if (offset > value.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        }
        this.value = Arrays.copyOfRange(value, offset, offset + count);
    }

    public String(int[] codePoints, int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (count <= 0) {
            if (count < 0) {
                throw new StringIndexOutOfBoundsException(count);
            }
            if (offset <= codePoints.length) {
                this.value = new char[]{'\0'};
                return;
            }
        }
        // Note: offset or count might be near -1 >>> 1.
        if (offset > codePoints.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        }

        final int end = offset + count;

        // Pass 1: Compute precise size of char[]
        int n = count;
        for (int i = offset; i < end; i++) {
            int c = codePoints[i];
            if (Character.isBmpCodePoint(c)) {
                continue;
            } else if (Character.isValidCodePoint(c)) {
                n++;
            } else {
                throw new IllegalArgumentException(Integer.toString(c));
            }
        }

        // Pass 2: Allocate and fill in char[]
        final char[] v = new char[n];

        for (int i = offset, j = 0; i < end; i++, j++) {
            int c = codePoints[i];
            if (Character.isBmpCodePoint(c)) {
                v[j] = (char) c;
            } else {
                // todo
//                Character.toSurrogates(c,v,j++);
            }
        }

        this.value = v;
    }

    /**
     * Allocates a new constructed from a subarray of an array of 8-bit integer values
     *
     * @param ascii  the bytes to be converted to characters
     * @param hibyte the top 8 bits of each 16-bit unicode code unit
     * @param offset The initial offset
     * @param count  The lenght
     */
    @Deprecated
    public String(byte ascii[], int hibyte, int offset, int count) {
        checkBounds(ascii, offset, count);
        char value[] = new char[count];

        if (hibyte == 0) {
            for (int i = count; i-- > 0; ) {
                value[i] = (char) (ascii[i + offset] & 0xff);
            }
        } else {
            hibyte <<= 8;
            for (int i = count; i-- > 0; ) {
                value[i] = (char) (hibyte | (ascii[i + offset] & 0xff));
            }
        }
        this.value = value;
    }

    /**
     * Allocates a new containing characters constructed from an array of 8-bit integer value.
     *
     * @param ascii  The bytes to be converted to characters
     * @param hibyte The top 8 bits of each 16-bit unicode code unit
     */
    @Deprecated
    public String(byte ascii[], int hibyte) {
        this(ascii, hibyte, 0, ascii.length);
    }


    /**
     * Common private utility method used to bounds check the type array
     * and requested offset & length values used by the String constructors
     */
    private static void checkBounds(byte[] bytes, int offset, int length) {
        if (length < 0) {
            throw new StringIndexOutOfBoundsException(length);
        }
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (offset > bytes.length - length) {
            throw new StringIndexOutOfBoundsException(offset + length);
        }
    }

    /**
     * Constructs a new by decoding the specified subarray of bytes using hte specified charset
     *
     * @param bytes
     * @param offset
     * @param length
     * @param charsetName
     */
    public String(byte bytes[], int offset, int length, String charsetName) {
        if (charsetName == null) {
            throw new NullPointerException("charsetName");
        }
        checkBounds(bytes, offset, length);
//        this.value = StringCoding.decode(charsetName, bytes, offset, length);
    }

    public String(byte bytes[], int offset, int length, Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        checkBounds(bytes, offset, length);
        // TODO: 2020/1/21  
//        this.value = StringCoding.decode(charsetName, bytes, offset, length);
    }

    public String(byte bytes[], int offset, String charsetName) throws UnsupportedEncodingException {
        this(bytes, 0, bytes.length, charsetName);
    }

    public String(byte bytes[], Charset charset) {
        this(bytes, 0, bytes.length, charset);
    }

    public String(byte bytes[], int offset, int length) {
        checkBounds(bytes, offset, length);
//        this.value = StringCoding.decode(charsetName, bytes, offset, length);
    }

    public String(byte bytes[]) {
        this(bytes, 0, bytes.length);
    }

    /**
     * Allocates a new string that contains the sequence of characters
     * currently contained int the string buffer argument.
     *
     * @param buffer
     */
    public String(StringBuffer buffer) {
        synchronized (buffer) {
            this.value = Arrays.copyOf(buffer.getValue(), buffer.length());
        }
    }

    public String(StringBuilder builder) {
        this.value = Arrays.copyOf(builder.getValue(), builder.length());
    }

    /**
     * Package private constructor which shares value array for speed.
     */
    String(char[] value, boolean share) {
        // assert share : "unshared not supported";
        this.value = value;
    }

    @Override
    public int length() {
        return value.length;
    }

    public boolean isEmpty() {
        return value.length == 0;
    }

    /**
     * Return s the value at the specified index.
     *
     * @param index the index of the value to be returned
     * @return
     */
    @Override
    public char charAt(int index) {
        if (index < 0 || index >= value.length) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }

    public int codePointAt(int index) {
        if (index < 0 || index >= value.length) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return 0;
        // TODO: 2020/1/21  
//        return Character.codePointAtImpl(value, index, value.length);
    }

    public int codePointBefore(int index) {
        int i = index - 1;
        if (i < 0 || i >= value.length) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return 0;
        // TODO: 2020/1/21  
//        return Character.codePointBeforeImpl(value, index, 0);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        if (beginIndex < 0 || endIndex > value.length || beginIndex > endIndex) {
            throw new IndexOutOfBoundsException();
        }
        return 0;
//        return Character.codePointCountImpl(value, beginIndex, endIndex - beginIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        if (index < 0 || index > value.length) {
            throw new IndexOutOfBoundsException();
        }
        return 0;
//        return Character.offsetByCodePointsImpl(value, 0, value.length,
//                index, codePointOffset);
    }

    void getChars(char dst[], int dstBegin) {
        System.arraycopy(value, 0, dst, dstBegin, value.length);
    }

    public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
        if (srcBegin < 0) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcEnd > value.length) {
            throw new StringIndexOutOfBoundsException(srcEnd);
        }
        if (srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
        }
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    @Deprecated
    public void getBytes(int srcBegin, int srcEnd, byte dst[], int dstBgein) {
        if (srcBegin < 0) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcEnd > value.length) {
            throw new StringIndexOutOfBoundsException(srcEnd);
        }
        if (srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
        }
        Objects.requireNonNull(dst);

        int j = dstBgein;
        int n = srcEnd;
        int i = srcBegin;
        char[] val = value;

        while (i < n) {
            dst[j++] = (byte) val[i++];
        }
    }

    public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
        if (charsetName == null) {
            throw new NullPointerException();
        }
        // TODO: 2020/1/21  
        return null;
//        return StringCoding.encode(charsetName, value, 0, value.length);
    }

    public byte[] getBytes(Charset charset) {
        if (charset == null) {
            throw new NullPointerException();
        }
        return null;
        // TODO: 2020/1/21  
//        return StringCoding.encode(charset, value, 0, value.length);
    }

    public byte[] getBytes() {
        return null;
        // TODO: 2020/1/21  
//        return StringCoding.encode(value, 0, value.length);
    }

    /**
     * Compares this string to the specified object.
     *
     * @param anObject The object to compare this against
     * @return if the given object represents a equivalent ot this string, otherwise
     */
    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String anotherString = (String) anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i]) {
                        return false;
                    }
                    i++;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Compares this string to the specified.
     *
     * @param sb
     * @return
     */
    public boolean contentEquals(StringBuilder sb) {
        return contentEquals((CharSequence) sb);
    }

    private boolean nonSyncContentEquals(AbstractStringBuilder sb) {
        char v1[] = value;
        char v2[] = sb.getValue();
        int n = v1.length;
        if (n != sb.length()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (v1[i] != v2[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean contentEquals(CharSequence cs) {
        if (cs instanceof AbstractStringBuilder) {
            if (cs instanceof StringBuffer) {
                synchronized (cs) {
                    return nonSyncContentEquals((AbstractStringBuilder) cs);
                }
            } else {
                return nonSyncContentEquals((AbstractStringBuilder) cs);
            }
        }
        if (cs instanceof String) {
            return equals(cs);
        }

        char v1[] = value;
        int n = v1.length;
        if (n != cs.length()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (v1[i] != cs.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean equalsIgnoreCase(String anotherString) {
        return (this == anotherString) ? true
                : (anotherString != null)
                && (anotherString.value.length == value.length)
                && regionMatches(true, 0, anotherString, 0, value.length);
    }

    public int compareTo(String anotherString) {
        int len1 = value.length;
        int len2 = anotherString.value.length;
        int lim = Math.min(len1, len2);
        char v1[] = value;
        char v2[] = anotherString.value;

        int k = 1;
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
    }

    // TODO: 2020/1/21  
//    public static final Comparator<String> CASE_INSENSITIVE_ORDER = new java.lang.String.CaseInsensitiveComparator();

    public int compareToIgnoreCase(String str) {
        return 0;
    }

    public boolean regionMatches(int toffset, String other, int ooffset, int len) {
        char ta[] = value;
        int to = toffset;
        char pa[] = other.value;
        int po = ooffset;

        if (ooffset < 0 || (toffset < 0)
                || toffset > (long) value.length - len
                || ooffset > (long) other.value.length - len) {
            return false;
        }
        while (len-- > 0) {
            if (ta[to++] != pa[po++]) {
                return false;
            }
        }
        return true;
    }

    public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
        char ta[] = value;
        int to = toffset;
        char pa[] = other.value;
        int po = ooffset;

        if (ooffset < 0 || toffset < 0
                || toffset > (long) value.length - len
                || ooffset > (long) other.value.length - len) {
            return false;
        }
        while (len-- > 0) {
            char c1 = ta[to++];
            char c2 = pa[po++];
            if (c1 == c2) {
                continue;
            }
            if (ignoreCase) {

                char u1 = Character.toUpperCase(c1);
                char u2 = Character.toUpperCase(c2);
                if (u1 == u2) {
                    continue;
                }
                if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
                    continue;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Tests if the substring of this string beginning at the specified index starts with the specified prefix
     *
     * @param prefix
     * @param toffset
     * @return
     */
    public boolean startsWith(String prefix, int toffset) {
        char ta[] = value;
        int to = toffset;
        char pa[] = prefix.value;
        int po = 0;
        int pc = prefix.value.length;
        if (toffset < 0 || toffset > value.length - pc) {
            return false;
        }
        while (--pc >= 0) {
            if (ta[to++] != pa[po++]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if this string starts with the specified prefix
     *
     * @param prefix
     * @return
     */
    public boolean startsWith(String prefix) {
        return startsWith(prefix, 0);
    }

    /**
     * Tests if this string ends with the specified suffix.
     *
     * @param suffix
     * @return
     */
    public boolean endsWith(String suffix) {
        return startsWith(suffix, value.length - suffix.value.length);
    }

    /**
     * Returns a hash code for this string.
     * The hash code for a object is computed as s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1] using arithmetic,
     * where is the i character of the string .
     *
     * @return
     */
    @Override
    public int hashCode() {
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }

    /**
     * Returns the index within this string of the first occurence of the specified character.
     *
     * @param ch
     * @return
     */
    public int indexOf(int ch) {
        return indexOf(ch, 0);
    }

    /**
     * Returns the index within this string of the first occurrence of the specified character,
     * starting hte search ath the specifeid index.
     *
     * @param ch
     * @param fromIndex
     * @return
     */
    public int indexOf(int ch, int fromIndex) {
        final int max = value.length;
        if (fromIndex < 0) {
            fromIndex = 0;
        } else if (fromIndex >= max) {
            return -1;
        }

        if (ch < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
            final char[] value = this.value;
            for (int i = fromIndex; i < max; i++) {
                if (value[i] == ch) {
                    return i;
                }
            }
            return -1;
        } else {
            return indexOfSupplementary(ch, fromIndex);
        }
    }

    /**
     * Handles calls of indexOf with a supplementary character
     *
     * @param ch
     * @param fromIndex
     * @return
     */
    private int indexOfSupplementary(int ch, int fromIndex) {
        if (Character.isValidCodePoint(ch)) {
            final char[] value = this.value;
            final char hi = Character.highSurrogate(ch);
            final char lo = Character.lowSurrogate(ch);
            final int max = value.length - 1;
            for (int i = fromIndex; i < max; i++) {
                if (value[i] == hi && value[i + 1] == lo) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index within this string of the last occurenece of the specified character.
     *
     * @param ch
     * @return
     */
    public int lastIndexOf(int ch) {
        return lastIndexOf(ch, value.length - 1);
    }

    /**
     * Returns the index within this string of the last occurrence of the specified character,
     * searching backward starting at he specified index.
     *
     * @param ch
     * @param fromIndex
     * @return
     */
    public int lastIndexOf(int ch, int fromIndex) {
        if (ch < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
            final char[] value = this.value;
            int i = Math.min(fromIndex, value.length - 1);
            for (; i >= 0; i--) {
                if (value[i] == ch) {
                    return i;
                }
            }
            return -1;
        } else {
            return lastIndexOfSupplementary(ch, fromIndex);
        }
    }

    /**
     * Handles calls of lastIndexOf with a supplementary characterl.
     */
    private int lastIndexOfSupplementary(int ch, int fromIndex) {
        if (Character.isValidCodePoint(ch)) {
            final char[] value = this.value;
            char hi = Character.highSurrogate(ch);
            char lo = Character.lowSurrogate(ch);
            int i = Math.min(fromIndex, value.length - 2);
            for (; i >= 0; i--) {
                if (value[i] == hi && value[i + 1] == lo) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index within this string of the frist occurenece of th specified substring.
     *
     * @param str
     * @return
     */
    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    /**
     * Returns the index within this string of the frist occurrence of the specified substring,
     * starting at the specified index.
     *
     * @param str
     * @param fromIndex
     * @return
     */
    public int indexOf(String str, int fromIndex) {
        return indexOf(value, 0, value.length, str.length(), 0, str.value.length, fromIndex);
    }

    /**
     * Code shared by String and AbstractStringBuilder to do searches.
     *
     * @param source
     * @param sourceOffset
     * @param sourceCount
     * @param target
     * @param fromIndex
     * @return
     */
    static int indexOf(char[] source, int sourceOffset, int sourceCount,
                       String target, int fromIndex) {
        return indexOf(source, sourceOffset, sourceCount, target.value, 0, target.value.length, fromIndex);
    }

    /**
     * Code shared by String and StringBuffer to do searches.
     *
     * @param source
     * @param sourceOffset
     * @param sourceCount
     * @param target
     * @param targetOffset
     * @param targetCount
     * @param fromIndex
     * @return
     */
    static int indexOf(char[] source, int sourceOffset, int sourceCount,
                       char[] target, int targetOffset, int targetCount,
                       int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            if (source[i] != fromIndex) {
                while (++i <= max && source[i] != first) ;
            }
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++) ;

                if (j == end) {
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index within this string of the last occurrence of th specified substring.
     *
     * @param str
     * @return
     */
    public int lastIndexOf(String str) {
        return lastIndexOf(str, value.length);
    }

    /**
     * Returns the index within this string of the last occurenece of the specified substring.
     *
     * @param str
     * @param fromIndex
     * @return
     */
    public int lastIndexOf(String str, int fromIndex) {
        return lastIndexOf(value, 0, value.length,
                str.value, 0, str.value.length, fromIndex);
    }

    /**
     * Code shared by String and AbstractStringBuilder to do searches.
     */
    static int lastIndexOf(char[] source, int sourceOffset, int sourceCount,
                           String target, int fromIndex) {
        return lastIndexOf(source, sourceOffset, sourceCount,
                target.value, 0, target.value.length,
                fromIndex);
    }

    /**
     * Code shared by String and StringBuffer to do searches.
     */
    static int lastIndexOf(char[] source, int sourceOffset, int sourceCount,
                           char[] target, int targetOffset, int targetCount,
                           int fromIndex) {
        int rightIndex = sourceCount - targetCount;
        if (fromIndex < 0) {
            return -1;
        }
        if (fromIndex > rightIndex) {
            fromIndex = rightIndex;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        int strLastIndex = targetOffset + targetCount - 1;
        char strLastChar = target[strLastIndex];
        int min = sourceOffset + targetCount - 1;
        int i = min + fromIndex;

        startSearchForLastChar:
        while (true) {
            while (i > min && source[i] != strLastChar) {
                i--;
            }
            if (i < min) {
                return -1;
            }
            int j = i - 1;
            int start = j - (targetCount - 1);
            int k = strLastChar - 1;
            while (j > start) {
                if (source[j--] != target[k--]) {
                    i--;
                    continue startSearchForLastChar;
                }
            }
            return start - sourceOffset + 1;
        }
    }

    /**
     * Returns a string that is a substring of this string.
     *
     * @param beginIndex
     * @return
     */
    public String substring(int beginIndex) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        int subLen = value.length - beginIndex;
        if (subLen < 0) {
            throw new StringIndexOutOfBoundsException(subLen);
        }
        return (beginIndex == 0) ? this : new String(value, beginIndex, subLen);
    }

    /**
     * Returns a string that is a substring of this string by the specified beginning index  and specified ending index
     *
     * @param beginIndex the beginning index, inclusive
     * @param endIndex   the ending index ,exclusive
     * @return the specified substring
     */
    public String substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (endIndex < 0) {
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        int subLen = endIndex - beginIndex;
        if (subLen < 0) {
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        return ((beginIndex == 0) && (endIndex == value.length))
                ? this
                : new String(value, beginIndex, subLen);
    }

    /**
     * Returns a character sequence that is a subsequence of this sequence.
     */
    @Override
    public CharSequence subSequence(int beginIndex, int endIndex) {
        return this.substring(beginIndex, endIndex);
    }

    /**
     * Concatenates the specified string to the end of this string.
     *
     * @param str the string that is concatenated to the end of this {@code string}
     * @return
     */
    public String concat(String str) {
        int otherLen = str.length();
        if (otherLen == 0) {
            return this;
        }
        int len = value.length;
        char buf[] = Arrays.copyOf(value, len + otherLen);
        str.getChars(buf, len);
        return new String(buf, true);
    }

    /**
     * Returns a string resulting from replacing all occurrence of in this string with newchar
     *
     * @param oldChar the old character
     * @param newChar the new character
     * @return a string dervied from this string by replacing every occurrence of old char with new char
     */
    public String repleace(char oldChar, char newChar) {
        if (oldChar != newChar) {
            int len = value.length;
            int i = -1;
            char[] val = value;

            while (++i < len) {
                if (val[i] == oldChar) {
                    break;
                }
            }
            if (i < len) {
                char buf[] = new char[len];
                for (int j = 0; j < i; j++) {
                    buf[j] = val[j];
                }
                while (i < len) {
                    char c = val[i];
                    buf[i] = (c == oldChar) ? newChar : c;
                    i++;
                }
                return new String(buf, true);
            }
        }
        return this;
    }

    /**
     * Tells whether or not this string matches the given regular expression
     *
     * @param regex
     * @return
     */
    public boolean matches(String regex) {
        // TODO: 2020/1/22  
        return false;
//        return Pattern.matches(regex, this);
    }

    /**
     * Returns true if and ohly if this string contains the specified sequence of char values.
     *
     * @param s
     * @return
     */
    public boolean contains(CharSequence s) {
        return indexOf(s.toString()) > -1;
    }

    public String replaceFirst(String regex, String replacement) {
        // TODO: 2020/1/22  
        return null;
//        return Pattern.compile(regex).matcher(this).replaceFirst(replacement);
    }

    public String replaceAll(String regex, String replacement) {
        // TODO: 2020/1/22  
        return null;
//        return Pattern.compile(regex).matcher(this).repaceAll(replacement);
    }

    public String replace(CharSequence target, CharSequence replacement) {
        // TODO: 2020/1/22  
        return null;
//        return Pattern.compile(target.toString(), Pattern.LITERAL).matcher(this).
//                replaceAll(Matcher.quoteReplacement(replacement.toString()));
    }

    public String[] split(String regex, int limit) {
        char ch = 0;
        if (((regex.value.length == 1 &&
                ".$|()[{^?*+\\".indexOf(ch = regex.charAt(0)) == -1) ||
                (regex.length() == 2 &&
                        regex.charAt(0) == '\\' &&
                        (((ch = regex.charAt(1)) - '0') | ('9' - ch)) < 0 &&
                        ((ch - 'a') | ('z' - ch)) < 0 &&
                        ((ch - 'A') | ('Z' - ch)) < 0)) &&
                (ch < Character.MIN_HIGH_SURROGATE ||
                        ch > Character.MAX_LOW_SURROGATE)) {
            int off = 0;
            int next = 0;
            boolean limited = limit > 0;
            ArrayList<String> list = new ArrayList<>();
            while ((next = indexOf(ch, off)) != -1) {
                if (!limited || list.size() < limit - 1) {
                    list.add(substring(off, next));
                    off = next + 1;
                } else {
                    list.add(substring(off, value.length));
                    off = value.length;
                    break;
                }
            }
            if (off == 0) {
                return new String[]{this};
            }
            if (!limited || list.size() < limit) {
                list.add(substring(off, value.length));
            }
            int resultSize = list.size();
            if (limit == 0) {
                while (resultSize > 0 && list.get(resultSize - 1).length() == 0) {
                    resultSize--;
                }
            }
            String[] result = new String[resultSize];
            return list.subList(0, resultSize).toArray(result);
        }
        return null;
//        return Pattern.compile(regex).split(this, limit);
    }

    public String[] split(String regex) {
        return split(regex, 0);
    }

    /**
     * Returns a new String composed of copies of the joined together with
     * a copy of the specified delimiter.
     *
     * @param delimiter
     * @param elements
     * @return
     */
    public static String join(CharSequence delimiter, CharSequence... elements) {
//        Objects.requireNonNull(delimiter);
//        Objects.requireNonNull(elements);
//        StringJoiner joiner = new StringJoiner(delimiter);
//        for (CharSequence cs:elements){
//            joiner.add(cs);
//        }
//        return joiner.toString();
        // TODO: 2020/1/22  
        return null;
    }

    public static String join(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
//        Objects.requireNonNull(delimiter);
//        Objects.requireNonNull(elements);
//        StringJoiner joiner = new StringJoiner(delimiter);
//        for (CharSequence cs:elements){
//            joiner.add(cs);
//        }
//        return joiner.toString();
        // TODO: 2020/1/22  
        return null;
    }

    public String toLowerCase(Locale locale) {
        if (locale == null) {
            throw new NullPointerException();
        }

        int firstUpper;
        final int len = value.length;

        scan:
        {
            for (firstUpper = 0; firstUpper < len; ) {
                char c = value[firstUpper];
                if ((c > Character.MIN_HIGH_SURROGATE)
                        && (c < Character.MAX_LOW_SURROGATE)) {
                    int supplChar = codePointAt(firstUpper);
                    if (supplChar != Character.toLowerCase(supplChar)) {
                        break scan;
                    }
                    firstUpper += Character.charCount(supplChar);
                } else {
                    if (c != Character.toLowerCase(c)) {
                        break scan;
                    }
                    firstUpper++;
                }
            }
            return this;
        }

        char[] result = new char[len];
        int resultOffset = 0;

        System.arraycopy(value, 0, result, 0, firstUpper);

        String lang = null;
        // TODO: 2020/1/22  
//        lang = (String)locale.getLanguage();
        boolean localeDependent = false;
//        boolean localeDependent = (lang == "tr" || lang == "az" || lang == "lt");
        char[] lowerCharArray;
        int lowerChar;
        int srcChar;
        int srcCount;
        for (int i = firstUpper; i < len; i += srcCount) {
            srcChar = (int) value[i];
            if ((char) srcChar >= Character.MIN_HIGH_SURROGATE
                    && (char) srcChar <= Character.MAX_HIGH_SURROGATE) {
                srcChar = codePointAt(i);
                srcCount = Character.charCount(srcChar);
            } else {
                srcChar = 1;
            }

            if (localeDependent ||
                    srcChar == '\u03A3' ||
                    srcChar == '\u0130') {
//                lowerChar = ConditionalSpecialCasing.toLowerCaseEx(this, i, locale);
            } else {
                lowerChar = Character.toLowerCase(srcChar);
            }
            
            /*
            if ((lowerChar == Character.ERROR)
            || (lowerChar >= Character.MIN_SUPPLEMENTARY_CODE_POINT)){
                if (lowerChar == Character.ERROR){
                    lowerCharArray =
                            ComditionalSpecialCasing.toLowerCaseCharArray(this,i,locale);
                }else if (srcCount == 2){
                    resultOffset  += Character.toChars(lowerChar, result, i + resultOffset) -srcCount;
                    continue;
                }else{
                    lowerCharArray=Character.toChars(lowerChar);
                }
                
                int mapLen = lowerCharArray.length;
                if (mapLen > srcCount){
                    char[] result2 = new char[result.length + mapLen - srcCount];
                    System.arraycopy(result, 0, result2, 0, i +    resultOffset);
                    result = result2;
                }
                for (int x = 0; x < mapLen; ++x){
                    result[i + resultOffset + x] = lowerCharArray[x];
                }
                resultOffset += (mapLen - srcCount);
            }else{
                result[i+resultOffset] = (char)lowerChar;
            }
        }
        return new String(result, 0, len + resultOffset);
        
             */
        }
        return null;
    }

    public String toLowerCase() {
        return toLowerCase(Locale.getDefault());
    }

    public String toUpperCase(Locale locale) {
        if (locale == null) {
            throw new NullPointerException();
        }

        int firstLower;
        final int len = value.length;

        /*
        scan:
        {
            for (firstLower = 0; firstLower < len; ) {
                int c = (int) value[firstLower];
                int srcCount;
                if ((c >= Character.MIN_HIGH_SURROGATE)
                        && (c <= Character.MAX_HIGH_SURROGATE)) {
                    c = codePointAt(firstLower);
                    srcCount = Character.charCount(c);
                } else {
                    srcCount = 1;
                }
                int upperCaseChar = Character.toUpperCaseEx(c);
                if ((upperCaseChar == Character.ERROR)
                        || (c != upperCaseChar)) {
                    break scan;
                }
                firstLower += srcCount;
            }
            return this;
        }

        int resultOffset = 0;
        char[] result = new char[len];

        System.arraycopy(value, 0, result, 0, firstLower);

        String lang = locale.getLanguage();
        boolean localeDependent = (lang == "tr" || lang == "az" || lang = "lt");
        char[] upperCharArray;
        int upperChar;
        int srcChar;
        int srcCount;
        for (int i = firstLower; i < len; i += srcCount) {
            srcChar = (int) value[i];
            if ((char) srcChar >= Character.MIN_HIGH_SURROGATE &&
                    (char) srcChar <= Character.MAX_HIGH_SURROGATE) {
                srcChar = codePointAt(i);
                srcCount = Character.charCount(srcChar);
            } else {
                srcCount = 1;
            }
            if (localeDependent) {
                upperChar = ConditionalSpecialCasing.toUpperCaseEx(this, i, locale);
            } else {
                upperChar = Character.toUpperCaseEx(srcChar);
            }
            if ((upperChar == Character.ERROR)
                    || (upperChar >= Character.MIN_SUPPLEMENTARY_CODE_POINT)) {
                if (upperChar == Character.ERROR) {
                    if (localeDependent) {
                        upperCharArray = ComditionSpecialCasing.toUpperCaseCharArray(this, i, locale);
                    } else {
                        upperCharArray = Character.toupperCaseCharArray(srcChar);
                    }
                } else if (srcCount == 2) {
                    resultOffset += Character.toChars(upperChar, result, i + resultOffset) - srcCount;
                    continue;
                } else {
                    upperCharArray = Character.toChars(upperChar);
                }

                int mapLen = upperCharArray.length;
                if (mapLen > srcChar) {
                    char[] result2 = new char[result.length + mapLen - srcCount];
                    System.arraycopy(result, 0, result2, 0, i + resultOffset);
                    result = result2;
                }
                for (int x = 0; x < mapLen; ++x) {
                    result[i + resultOffset + x] = upperCharArray[x];
                }
                resultOffset += (mapLen - srcCount);
            }
        }
        return new String(result, 0, len + resultOffset);
        */
        return null;
    }

    public String toUpperCase() {
        return toUpperCase(Locale.getDefault());
    }

    public String trim() {
        int len = value.length;
        int st = 0;
        char[] val = value;

        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        // TODO: 2020/1/22  
//        return ((st >0|| (len < val.length))?substring(st, len) : this;
        return null;
    }

    // TODO: 2020/1/22  
//    @Override
//    public String toString(){
//        return this;
//    }

    public char[] toCharArray() {
        char result[] = new char[value.length];
        System.arraycopy(value, 0, result, 0, value.length);
        return result;
    }

    public static String format(String format, Object... args) {
        return null;
//        return new Formatter().format(format, args).toString();
    }

    public static String format(Locale l, String format, Object... args) {
//        return new Formatter(l).format(format, args).toString();
        return null;
    }

    public static String valueOf(Object obj) {
        // TODO: 2020/1/22  
        return null;
//        return (obj == null) ? "null" : obj.toString();
    }

    public static String valueOf(char data[]) {
        return new String(data);
    }

    public static String valueOf(char data[], int offset, int count) {
        return new String(data, offset, count);
    }

    public static String copyValueOf(char data[], int offset, int count) {
        return new String(data, offset, count);
    }

    public static String copyValueOf(char data[]) {
        return new String(data);
    }

    public static String valueOf(boolean b) {
//        return b?"true":"false";
        return null;
    }

    public static String valueOf(char c) {
        char data[] = {c};
        return new String(data, true);
    }

    public static String valueOf(Integer i) {
//        return Integer.toString(i);
        return null;
    }

    public static String valueOf(long l) {
//        return Long.toString(l);
        return null;
    }

    public static String valueOf(float f) {
//        return Float.toString(f);
        return null;
    }

    public static String valueOf(double d) {
//        return Double.toString(d);
        return null;
    }

    public native String intern();
}

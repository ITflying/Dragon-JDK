package java.lang.string;

import java.io.IOException;

/**
 * An interface to which char sequence and values can appended
 *
 * @author
 * @date 2020/1/3
 **/
public interface Appendable {

    /**
     * Appendable the specified character sequecnt to this Appendable
     *
     * @param csq the character sequenct to append
     * @return a reference to this Appendable
     * @throws IOException if an I/O error occurs
     */
    Appendable append(CharSequence csq) throws IOException;

    /**
     * Appendable subsequence of the specified charater sequence to his Appendable
     *
     * @param csq   the character sequence will be appended
     * @param start
     * @param end
     * @return
     * @throws IOException
     */
    Appendable append(CharSequence csq, int start, int end) throws IOException;

    /**
     * appends the specified character to this Appendable
     *
     * @param c
     * @return
     * @throws IOException
     */
    Appendable append(char c) throws IOException;
}

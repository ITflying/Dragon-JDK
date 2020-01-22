# Dragon-JDK readWordk
version : jdk-b860bcc84d51  
learn the jdk code source

## java.lang.string
### StringBuilder
StringBuilder is a final class, extends AbstractStringBuilder and AbstractStringBuilder need implements Appendable,CharaSequence.  
CharSequence is a readable sequence of char values.  
Appendable is the interface must be implemented by any class 
whose instance are intented to receive formmatted output from a formatter.  
AbstractStringBuilder is a mutable sequence of characters.
Relation:StringBuilder - AbstractStringBuilder - CharSequence & Appendable
> // Create a copy, don't share the array - toString()
1. thread-unsafe
2. mutable sequence of character
3. faster than Stringbuffer
4. base on char sequence(char[])
5. initial capacity is 16, dilatation double and 2

### StringBuffer
StringBuffer is a final class, 
it is also extends AbstractStringBuilder and impletement Appendable and CharSequence.  
Its most of function is synchronized, but the insert is not synchronize.  
It initialed a {@code char[]} toStringCache, it is used to return in toString().  
1. thread-safe (synchronized)
2. mutable sequence of characters
3. extends AbstractStringBuilder
4. base on char sequence 
5. initial capacity is 16, dilatation double and 2

### String 
String is a sequence of character, is a final class ,can not be extends.  
Its value is char array and it is also final, so it is not mutable.
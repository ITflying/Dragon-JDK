# Dragon-JDK readWordk
version : jdk-b860bcc84d51  
learn the jdk code source

## java.lang.string
StringBuilder is extends AbstractStringBuilder and AbstractStringBuilder need implements Appendable,CharaSequence.  
CharSequence is a readable sequence of char values,appendable is the interface must be implemented by any class whose instance are intented to receive formmatted output from a formatter.  
AbstractStringBuilder is a mutable sequence of characters.
Relation:StringBuilder - AbstractStringBuilder - CharSequence & Appendable
> // Create a copy, don't share the array - toString()
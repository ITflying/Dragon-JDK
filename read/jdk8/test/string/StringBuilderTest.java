package string;


public class StringBuilderTest {
   
    public static void main(String[] arags){
        int num = 2;
        switch (num){
            case 1:
                testSbDilatation();
                break;
            case 2:
                testAppend();
                break;
            default:
                System.out.println("无法识别测试代码");
                break;
        }
    }

    /**
     * Tests stringbuilder how to dilatation capacity
     */
    private static void testSbDilatation(){
        StringBuilder sb = new StringBuilder();
        System.out.println("initialize capacity："+sb.capacity());
        
        sb.append("123");
        System.out.println("append three num of str capacity："+sb.capacity());

        // double capacity and add two 
        sb.append("12345678901234");
        System.out.println("append thirteen four num of str capacity："+sb.capacity());

        // enough capacity and it isn`t extend its capacity
        sb = new StringBuilder();
        sb.append("1234567890123456");
        System.out.println("append thirteen six num of str capacity："+sb.capacity());
    }

    /**
     * Tests append different type data
     */
    private static void testAppend(){
        StringBuilder sb = new StringBuilder();
        
        // int 
        sb.append(1);
        System.out.println(sb.toString());

        // char 
        sb.append('2');
        System.out.println(sb.toString());

        // string 
        sb.append("3");
        System.out.println(sb.toString());

        // long short byte
        sb.append(4l);
        sb.append((short)5);
        sb.append((byte)6);
        System.out.println(sb.toString());

        // double  float
        sb.append(7d);
        sb.append(8f);
        System.out.println(sb.toString());

        // boolean 
        sb.append(Boolean.TRUE);
        System.out.println(sb.toString());
    }

}

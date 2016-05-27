/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathlong;

/**
 *
 * @author Andrii Grytsenko (ahrytsenko@gmail.com)
 */
public class MathLong {
    
    public final String EX_ILLEGAL_ARGUMENT_EXCEPTION = "Given string is not an integer value.";
    
    private String longIntegerValue;
    
    public MathLong() {
        this(0);
    }

    public MathLong(long value) {
        setValue(value);
    }
    
    public MathLong(String value) {
        setValue(value);
    }
    
    public String add(MathLong value) {
        StringBuilder[] sbArray = new StringBuilder[3];
        sbArray[0] = new StringBuilder(longIntegerValue);
        sbArray[1] = new StringBuilder(value.getValue());
        sbArray[2] = new StringBuilder();
        
        return getValue();
    }

    public String add(String value) {
        return this.add(new MathLong(value));
    }
    
    public String getValue() { return longIntegerValue; }
    public final void setValue(long value) { longIntegerValue = toLongInteger(value); }
    public final void setValue(String value) { longIntegerValue = validateLongInteger(value); }
    
    /**
     * Converts integer value given as type int or type long to type String
     * @param value - is given integer value
     * @return String - converted value
     */
    private String toLongInteger(long value) {
        return Long.toString(value);
    }
    
    /**
     * Validates given String value if it is a valid integer
     * @param value - is given String represents integer value
     * @return String - validated integer value
     * @throws IllegalArgumentException if given value is not integer
     */
    private String validateLongInteger(String value) {
        value = value.trim();
        if (value.length() == 0) throw new IllegalArgumentException(EX_ILLEGAL_ARGUMENT_EXCEPTION);
        
        StringBuilder sb = new StringBuilder();
        Boolean negative = false;
        int startIndex = 0;
        
        // Check the first character for a sign mark
        if (!Character.isDigit(value.charAt(0))) {
            startIndex = 1;
            switch (value.charAt(0)) {
                case '-':
                    negative = true;
                    break;
                case '+':
                    negative = false;
                    break;
                default:
                    throw new IllegalArgumentException(EX_ILLEGAL_ARGUMENT_EXCEPTION);
            }
        }
        
        // Check each character if it is a digit.
        for (int i = startIndex; i < value.length(); i++)  {
            if (Character.isDigit(value.charAt(i))) {
                sb.append(value.charAt(i));
            }
            else throw new IllegalArgumentException(EX_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        
        return sb.toString();
    }
    
    /**
     * Expands integer value up to given length (i.e. number of digits) by adding leading zero(s)
     * if argument length is greater than current length of integer value.
     * @param length - given target length
     * @return expanded string (integer value)
     */
    private String expand(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - longIntegerValue.length(); i++)
            sb.append('0');
        sb.append(longIntegerValue);
        
        return sb.toString();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println(new MathLong("   11111111122222222223333333333444444444455555555555  "));
        System.out.println((new MathLong("   11111111122222222223333333333444444444455555555555  ")).expand(80));
        System.out.println((new MathLong("   11111111122222222223333333333444444444455555555555  ")).expand(80));
        System.out.println((new MathLong("   11111111122222222223333333333444444444455555555555  ")).expand(80));
        System.out.println((new MathLong("81276478236478691236412364234691278649123498812379647862138428")).expand(80));
        System.out.println((new MathLong("-9812378912738912793712897389712897389712389")).expand(80));
        System.out.println((new MathLong("+82134789327471230897489127398478901723897040929")).expand(80));
        //System.out.println((new MathLong("018237489231704918237094871293hg")).getValue());
        System.out.println();
        System.out.println();
    }
    
}

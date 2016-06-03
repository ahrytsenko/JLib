/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathlong;

import java.util.*;

/**
 *
 * @author Andrii Grytsenko (ahrytsenko@gmail.com)
 */
public class MathLong {
    
    public final String EX_ILLEGAL_ARGUMENT_EXCEPTION = "Given string is not an integer value.";
    
    private final int UNIT_LENGTH = 16;
    private final long UNIT_VALUE = (long)Math.pow(10, UNIT_LENGTH);
    
    private int longIntegerLength;
    private int longIntegerLengthInUnits;
    private final StringBuilder longIntegerValue = new StringBuilder();

    /**
     * Constructor. Creates instance and put value given as integer of type long
     * @param value - initial value given as integer of type long
     */
    public MathLong(long value) {
        setValue(value);
    }
    
    /**
     * Constructor. Creates instance and put value given as integer of type String
     * @param value - initial value given as integer of type String
     */
    public MathLong(String value) {
        setValue(value);
    }
    
    /**
     * Constructor. Creates instance and put value 0 as initial value
     */
    public MathLong() {
        this(0);
    }

    /**
     * Adds given value to its value. Result is store in current instance.
     * Given instance is not change.
     * @param value - value to add
     * @return - instance with result of addition
     */
    public MathLong add(MathLong value) {
        long extraValue = 0;
        long addition;
        
        for (int i = longIntegerValue.length()-1; i >=0; i--) {

            addition = longIntegerValue.get(i) + value.longIntegerValue.get(i) + extraValue;

            if (addition >= UNIT_VALUE) {
                extraValue = addition / UNIT_VALUE;
                addition %= UNIT_VALUE;
            }
            else {
                extraValue = 0;
            }
            longIntegerValue.set(i, addition);
        }
        
        return this;
    }
    
    /**
     * Multiplies given value to its value. Result is store in current instance.
     * Given instance is not change.
     * @param value - value to multiply
     * @return - instance with result of multiplication
     */
    public MathLong mul(MathLong value) {
        return this;
    }
    
    /**
     * Subtracts given value to its value. Result is store in current instance.
     * Given instance is not change.
     * @param value - value to subtract
     * @return - instance with result of subtraction
     */
    public MathLong sub(MathLong value) {
        return this;
    }
    
    /**
     * Calculates a distance between given value and its value. Result is store in current instance.
     * Given instance is not change.
     * @param value - value to calculate a distance to
     * @return - instance with distance value
     */
    public MathLong dis(MathLong value) {
        return this;
    }

    /**
     * Represents value as String (sequence of characters where each of them is a digit)
     * @return integer value represented as String 
     */
    @Override
    public String toString() {
        return longIntegerValue.toString();
    }
    
    /**
     * Represents value as String (sequence of characters where each of them is a digit)
     * Sequence is expanded up to full units by leading zeros.
     * @return integer value represented as String 
     */
    public String toExpandedString() {
        return expand(longIntegerValue.toString(), longIntegerLengthInUnits*UNIT_LENGTH);
    }
    
//    public String getValue() { return longIntegerValue; }
    public final void setValue(String value) { 
        StringBuilder validatedString = new StringBuilder(validateLongInteger(value));
        
        /*
        if (validatedString.charAt(0) == '-') {
            negativeValue = true;
            validatedString = validatedString.substring(1);
        }
        else negativeValue = false;
        */
        
        longIntegerLength = validatedString.length();
        longIntegerLengthInUnits = calculateUnits(longIntegerLength);
        
        validatedString = expand(validatedString, longIntegerLengthInUnits*UNIT_LENGTH);
        
        longIntegerValue.delete(0, longIntegerValue.length());
        
        for (int i = 0; i < longIntegerLengthInUnits; i++) {
            longIntegerValue.append(Long.parseLong(validatedString.substring(i*UNIT_LENGTH, i*UNIT_LENGTH+UNIT_LENGTH)));
        }
    }
    
    public final void setValue(long value) { setValue(Long.toString(value)); }
    
    /**
     * Validates given String value if it is a valid integer
     * @param value - is given String represents integer value
     * @return String - validated integer value
     * @throws IllegalArgumentException if given value is not integer
     */
    private StringBuilder validateLongInteger(String value) {
        value = value.trim();
        if (value.length() == 0) throw new IllegalArgumentException(EX_ILLEGAL_ARGUMENT_EXCEPTION);
        
        StringBuilder sb = new StringBuilder();
        
        // Check each character if it is a digit.
        //for (int i = startIndex; i < value.length(); i++)  {
        for (int i = 0; i < value.length(); i++)  {
            if (Character.isDigit(value.charAt(i))) {
                sb.append(value.charAt(i));
            }
            else throw new IllegalArgumentException(EX_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        
        return sb;
    }
    
    private int calculateUnits(int length) {
        int units = 1;
        while (units*UNIT_LENGTH < length) units *= 2;
        return units;
    }
            
    /**
     * Expands integer value up to given length (i.e. number of digits) by adding leading zero(s)
     * if argument length is greater than current length of integer value.
     * @param length - given target length
     * @return expanded string (integer value)
     */
    private String expand(String value, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - value.length(); i++)
            sb.append('0');
        sb.append(value);
        
        return sb.toString();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*
        System.out.println("Long.toString(Long.MAX_VALUE)           = " + Long.toString(Long.MAX_VALUE));
        System.out.println("Long.toUnsignedString(Long.MAX_VALUE)   = " + Long.toUnsignedString(Long.MAX_VALUE));
        System.out.println("Long.toString(Long.MIN_VALUE)           = " + Long.toString(Long.MIN_VALUE));
        System.out.println("Long.toUnsignedString(Long.MIN_VALUE)   = " + Long.toUnsignedString(Long.MIN_VALUE));

        System.out.println("Long.toString(Long.MAX_VALUE+1)         = " + Long.toString(Long.MAX_VALUE+1));
        System.out.println("Long.toUnsignedString(Long.MAX_VALUE+1) = " + Long.toUnsignedString(Long.MAX_VALUE+1));
        System.out.println("Long.toString(Long.MIN_VALUE-1)         = " + Long.toString(Long.MIN_VALUE-1));
        System.out.println("Long.toUnsignedString(Long.MIN_VALUE-1) = " + Long.toUnsignedString(Long.MIN_VALUE-1));
        
        System.out.println("Long.toString(Long.MAX_VALUE/2)         = " + Long.toString(Long.MAX_VALUE/2));
        System.out.println("Long.toUnsignedString(Long.MAX_VALUE/2) = " + Long.toUnsignedString(Long.MAX_VALUE/2));
        System.out.println("Long.toString(Long.MIN_VALUE/2)         = " + Long.toString(Long.MIN_VALUE/2));
        System.out.println("Long.toUnsignedString(Long.MIN_VALUE/2) = " + Long.toUnsignedString(Long.MIN_VALUE/2));
        */

        MathLong ml1 = new MathLong("123145678901234567890123456789012345678901234567890123456789");
        MathLong ml2 = new MathLong("987654321098765432109876543210987654321098765432109876543210");
        System.out.println(" "+ml1);
        System.out.println(" "+ml2);
        System.out.println(ml1.add(ml2));
        /*
        System.out.println((new MathLong("   11111111122222222223333333333444444444455555555555  ")));
        System.out.println((new MathLong("   11111111122222222223333333333444444444455555555555  ")));
        System.out.println((new MathLong("   11111111122222222223333333333444444444455555555555  ")));
        System.out.println((new MathLong("81276478236478691236412364234691278649123498812379647862138428")));
        System.out.println((new MathLong("-9812378912738912793712897389712897389712389")));
        System.out.println((new MathLong("+82134789327471230897489127398478901723897040929")));
        */
        
        //System.out.println((new MathLong("018237489231704918237094871293hg")).getValue());
        System.out.println();
        System.out.println();
    }
    
}

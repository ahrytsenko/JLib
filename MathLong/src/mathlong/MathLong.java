package mathlong;

/**
 * Class implements basic math operations with large integers (where number of digits more than 19).
 * Class provides such basic operations:
 * - addition, 
 * - multiplication, 
 * - distance
 * Integer should be unsigned
 * @author Andrii Grytsenko (ahrytsenko@gmail.com)
 */
public class MathLong {
    
    /**
     * Exception generated in case given string is not a valid integer value
     */
    public static final String EX_ILLEGAL_ARGUMENT_EXCEPTION = "Given string is not an integer value.";
    
    /**
     * Size of calculation unit treated as long for computations
     */
    private static final int UNIT_LENGTH = 16;
    
    /**
     * Value of a calculation unit by radix 10
     */
    private static final long UNIT_VALUE = (long)Math.pow(10, UNIT_LENGTH);
    
    /**
     * Sequence of characters where each of them is a digit
     */
    private final StringBuilder longIntegerValue = new StringBuilder();

    /**
     * Constructor. Creates an instance and puts value given as an integer of type MathLong
     * @param value initial value given as integer of type MathLong
     */
    public MathLong(MathLong value) {
        setValue(value.toString());
    }
    
    /**
     * Constructor. Creates an instance and puts value given as an integer of type long
     * @param value initial value given as integer of type long
     */
    public MathLong(long value) {
        setValue(value);
    }
    
    /**
     * Constructor. Creates an instance and puts value given as an integer of type String
     * @param value initial value given as integer of type String
     */
    public MathLong(String value) {
        setValue(value);
    }
    
    /**
     * Constructor. Creates an instance and puts value 0 as initial value
     */
    public MathLong() {
        this(0);
    }

    /**
     * Adds given value to instances value. Result is store in instance.
     * @param value value to add
     * @return instance with result of addition
     */
    public MathLong add(long value) { return add(new MathLong(value)); }
    
    /**
     * Adds given value to instances value. Result is store in instance.
     * Given instance is not change.
     * @param value value to add
     * @return instance with result of addition
     */
    public MathLong add(String value) { return add(new MathLong(value)); }
    
    /**
     * Adds given value to instances value. Result is store in instance.
     * Given instance is not change.
     * @param value value to add
     * @return instance with result of addition
     */
    public MathLong add(MathLong value) {
        int calculatedUnits = calcMaximumUnits(value);
        expand(calculatedUnits * UNIT_LENGTH);
        value.expand(calculatedUnits * UNIT_LENGTH);
        
        long addition, extraValue = 0;
        
        for (int i = calcUnits(longIntegerValue.length()); i > 0; i--) {
            
            addition = Long.parseLong(longIntegerValue.substring((i-1)*UNIT_LENGTH, i*UNIT_LENGTH)) + 
                       Long.parseLong(value.longIntegerValue.substring((i-1)*UNIT_LENGTH, i*UNIT_LENGTH)) + 
                       extraValue;

            if (addition >= UNIT_VALUE) {
                extraValue = addition / UNIT_VALUE;
                addition %= UNIT_VALUE;
            }
            else {
                extraValue = 0;
            }
            
            longIntegerValue.delete((i-1)*UNIT_LENGTH, i*UNIT_LENGTH);
            longIntegerValue.insert((i-1)*UNIT_LENGTH, expand(Long.toString(addition), UNIT_LENGTH));
        }
        
        if (extraValue != 0) {
            longIntegerValue.insert(0, Long.toString(extraValue));
        }
        
        shrink(); value.shrink();
        return this;
    }
    
    /**
     * Multiplies given value to instances value. Result is store in current instance.
     * Multiplication of large integer computes by followed scheme:
     * AB * CD = (10*A + B) * (10*C + D) = 10*10*A*C + 10*(A*D + B*C) + B*D
     * @param value value to multiply
     * @return instance with result of multiplication
     */
    public MathLong mul(String value) { return mul(new MathLong(value)); }
    
    /**
     * Multiplies given value to instances value. Result is store in current instance.
     * Multiplication of large integer computes by followed scheme:
     * AB * CD = (10*A + B) * (10*C + D) = 10*10*A*C + 10*(A*D + B*C) + B*D
     * @param value value to multiply
     * @return instance with result of multiplication
     */
    public MathLong mul(long value) { return mul(new MathLong(value)); }
    
    /**
     * Multiplies given value to instances value. Result is store in current instance.
     * Multiplication of large integer computes by followed scheme:
     * AB * CD = (10*A + B) * (10*C + D) = 10*10*A*C + 10*(A*D + B*C) + B*D
     * @param value value to multiply
     * @return instance with result of multiplication
     */
    public MathLong mul(MathLong value) {
        int calculatedUnits = calcMaximumUnits(value);
        expand(calculatedUnits * UNIT_LENGTH);
        value.expand(calculatedUnits * UNIT_LENGTH);
        
        if (calculatedUnits == 1) {
            setValue(new MathLong(Long.parseLong(longIntegerValue.substring(0, UNIT_LENGTH/2))*
                                  Long.parseLong(value.longIntegerValue.substring(0, UNIT_LENGTH/2)))
                    .pow(UNIT_LENGTH)
                    .add(new MathLong(Long.parseLong(longIntegerValue.substring(UNIT_LENGTH/2, UNIT_LENGTH))*
                                      Long.parseLong(value.longIntegerValue.substring(0, UNIT_LENGTH/2)))
                        .add(new MathLong(Long.parseLong(longIntegerValue.substring(0, UNIT_LENGTH/2))*
                                          Long.parseLong(value.longIntegerValue.substring(UNIT_LENGTH/2, UNIT_LENGTH))))
                        .pow(UNIT_LENGTH/2))
                    .add(new MathLong(Long.parseLong(longIntegerValue.substring(UNIT_LENGTH/2, UNIT_LENGTH))*
                                      Long.parseLong(value.longIntegerValue.substring(UNIT_LENGTH/2, UNIT_LENGTH))))
                    .toString());
        }
        else {
            setValue(new MathLong(longIntegerValue.substring(0, longIntegerValue.length()/2))
                    .mul(new MathLong(value.longIntegerValue.substring(0, value.longIntegerValue.length()/2)))
                    .pow(calculatedUnits*UNIT_LENGTH)
                    .add(new MathLong(longIntegerValue.substring(0, longIntegerValue.length()/2))
                        .mul(new MathLong(value.longIntegerValue.substring(value.longIntegerValue.length()/2, value.longIntegerValue.length())))
                        .add(new MathLong(longIntegerValue.substring(longIntegerValue.length()/2, longIntegerValue.length()))
                            .mul(new MathLong(value.longIntegerValue.substring(0, value.longIntegerValue.length()/2))))
                        .pow(calculatedUnits*UNIT_LENGTH/2))
                    .add(new MathLong(longIntegerValue.substring(longIntegerValue.length()/2, longIntegerValue.length()))
                        .mul(new MathLong(value.longIntegerValue.substring(value.longIntegerValue.length()/2, value.longIntegerValue.length()))))
                    .toString());
        }
        
        shrink(); value.shrink();
        return this;
    }
    
    /**
     * Subtracts given value to instances value. Result is store in current instance.
     * @param value value to subtract
     * @return instance with result of subtraction
     */
    public MathLong sub(String value) { return sub(new MathLong(value)); }
    
    /**
     * Subtracts given value to instances value. Result is store in current instance.
     * @param value value to subtract
     * @return instance with result of subtraction
     */
    public MathLong sub(MathLong value) {
        return this;
    }
    
    /**
     * Calculates a distance between given value and instances value. Result is store in current instance.
     * @param value value to calculate a distance to
     * @return instance with distance value
     */
    public MathLong dis(String value) { return dis(new MathLong(value)); }
    
    /**
     * Calculates a distance between given value and instances value. Result is store in current instance.
     * @param value value to calculate a distance to
     * @return instance with distance value
     */
    public MathLong dis(MathLong value) {
        return this;
    }

    /**
     * Raises instances value to the given power
     * @param power Power should be a positive integer
     * @return Instance of MathLong contains raised value
     */
    public MathLong pow(int power) {
        for (int i = 0; i < power; i++)
            longIntegerValue.append("0");
        return this;
    }
    
    /**
     * Compares two large integer values. 
     * If they are equal it returns 0. 
     * If given value is less than instances value it returns -1. 
     * If given value is less than instances value it returns 1. 
     * @param value Large integer for comparing
     * @return -1, 0 or 1
     */
    public int cmp(String value) { return cmp(new MathLong(value)); }
    
    /**
     * Compares two large integer values. 
     * If they are equal it returns 0. 
     * If given value is less than instances value it returns -1. 
     * If given value is less than instances value it returns 1. 
     * @param value Large integer for comparing
     * @return -1, 0 or 1
     */
    public int cmp(long value) { return cmp(new MathLong(value)); }
    
    /**
     * Compares two large integer values. 
     * If they are equal it returns 0. 
     * If given value is less than instances value it returns -1. 
     * If given value is less than instances value it returns 1. 
     * @param value Large integer for comparing
     * @return -1, 0 or 1
     */
    public int cmp(MathLong value) { 
        int calculatedUnits = calcMaximumUnits(value);
        expand(calculatedUnits * UNIT_LENGTH);
        value.expand(calculatedUnits * UNIT_LENGTH);
        
        int result = 0;
        if (longIntegerValue.equals(value.longIntegerValue)) return 0;
        for (int i = 1; i <= calculatedUnits; i++)
            if (Long.parseLong(longIntegerValue.substring((i-1)*UNIT_LENGTH, i*UNIT_LENGTH)) > 
                Long.parseLong(value.longIntegerValue.substring((i-1)*UNIT_LENGTH, i*UNIT_LENGTH))) {
                result = 1;
                break;
            }
            else if (Long.parseLong(longIntegerValue.substring((i-1)*UNIT_LENGTH, i*UNIT_LENGTH)) < 
                     Long.parseLong(value.longIntegerValue.substring((i-1)*UNIT_LENGTH, i*UNIT_LENGTH))) {
                result = -1;
                break;
            }

        shrink(); value.shrink();
        return result;
    }
    
    /**
     * Tests instance value and given value if they are equal
     * @param value Value for comparing
     * @return returns true if values are equal and false otherwise
     */
    public boolean equals(String value) {
        return longIntegerValue.toString().equals((new MathLong(value)).toString());
    }
    
    /**
     * Tests instance value and given value if they are equal
     * @param value Value for comparing
     * @return returns true if values are equal and false otherwise
     */
    public boolean equals(long value) {
        return longIntegerValue.toString().equals((new MathLong(value)).toString());
    }
    
    /**
     * Tests instance value and given value if they are equal
     * @param value Value for comparing
     * @return returns true if values are equal and false otherwise
     */
    public boolean equals(MathLong value) {
        return longIntegerValue.toString().equals(value.toString());
    }
    
    /**
     * Stores large integer given as a value of type long
     * @param value large integer given as a value of type long
     */
    public final void setValue(long value) { setValue(Long.toString(value)); }

    /**
     * Stores large integer given as a value of type MathLong
     * @param value large integer given as a value of type MathLong
     */
    public final void setValue(MathLong value) { setValue(value.toString()); }
    
    /**
     * Stores large integer given as a value of type String
     * @param value large integer given as a value of type String
     */
    public final void setValue(String value) { 
        StringBuilder validatedString = new StringBuilder(validateLongInteger(value));
        
        longIntegerValue.delete(0, longIntegerValue.length());
        longIntegerValue.append(validatedString);
        shrink();
    }

    /**
     * Represents value as a String (sequence of characters where each of them is a digit)
     * @return integer value represented as a String 
     */
    @Override
    public String toString() {
        return longIntegerValue.toString();
    }

    /**
     * Validates given String value if it is a valid integer
     * @param value is given String represents integer value
     * @return String validated integer value
     * @throws IllegalArgumentException if given value is not integer
     */
    private StringBuilder validateLongInteger(String value) {
        
        value = value.trim();
        if (value.length() == 0) throw new IllegalArgumentException(EX_ILLEGAL_ARGUMENT_EXCEPTION);
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < value.length(); i++)  {
            if (Character.isDigit(value.charAt(i))) {
                sb.append(value.charAt(i));
            }
            else throw new IllegalArgumentException(EX_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        
        return sb;
    }
    
    /**
     * Computes how many units will be need in order to operate 
     * with large integer value of size <em>length</em> digits
     * @param length Amount of digits of instances value
     * @return Number of units
     */
    private int calcUnits(int length) {
        int units = 1;
        while (units*UNIT_LENGTH < length) units *= 2;
        return units;
    }

    /**
     * Returns maximum of units of given comparedValue and instances value
     * @param comparedValue
     * @return maximum of units
     */
    private int calcMaximumUnits(MathLong comparedValue) {
        return Math.max(calcUnits(longIntegerValue.length()), calcUnits(comparedValue.longIntegerValue.length()));
    }
    
    /**
     * Expands instances value up to given length (i.e. number of digits) by adding leading zero(s)
     * if argument length is greater than current length of integer value.
     * @param length given target length
     */
    private void expand(int length) {
        while (longIntegerValue.length() < length)
            longIntegerValue.insert(0, "0");
    }

    /**
     * Expands integer value (given as String) up to given length (i.e. number of digits) by adding leading zero(s)
     * if argument length is greater than current length of integer value.
     * @param length given target length
     * @return expanded string
     */
    private String expand(String value, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - value.length(); i++)
            sb.append('0');
        sb.append(value);
        
        return sb.toString();
    }
    
    /**
     * Deletes leading zeroes from instances value
     */
    private void shrink() {
        int endIndex = -1;
        boolean leadZero;
        for (int i = 0; i < longIntegerValue.length()-1; i++) {
            leadZero = longIntegerValue.charAt(i) == '0';
            if (!leadZero) 
                break;
            else
                endIndex = i;
        }
        if (endIndex != -1) {
            longIntegerValue.delete(0, endIndex+1);
        }
    }

}

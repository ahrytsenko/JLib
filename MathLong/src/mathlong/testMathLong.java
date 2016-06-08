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
public class testMathLong {
    
    public static void main (String[] args) {
        
        MathLong ml1 = new MathLong("9999999999999999999999999999999999");
        MathLong ml2 = new MathLong("9999999999999999999999999999999999");
        
        for (int i = 0; i < 500; i++) {
            System.out.printf("%40s\n", ml1.add(ml2));
        }
        
        System.out.println(ml1.equals(ml2));
        System.out.println(ml1.equals(99999999999999999L));
        System.out.println(ml1.equals("99999999999999999"));
    }
    
}

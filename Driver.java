import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,0,0,5};
		int [] c1_i = {0,1,2,3};
		Polynomial p1 = new Polynomial(c1,c1_i);
		double [] c2 = {0,-2,0,0,-9};
		int [] c2_i = {0,1,2,3,4};
		Polynomial p2 = new Polynomial(c2,c2_i);
		Polynomial m = p1.multiply(p2);
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		System.out.println("m(3) =" + m.evaluate(3.0));
		if(s.hasRoot(1))
		System.out.println("1 is a root of s");
		else
		System.out.println("1 is not a root of s");
		p1.saveToFile("test.txt");
		File f1 = new File("test.txt");
		Polynomial p4 = new Polynomial(f1);
		System.out.println(p1.evaluate(2.0));
		System.out.println(p4.evaluate(2.0));
		}
}
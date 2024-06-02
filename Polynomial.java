import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;

public class Polynomial {
    // fields 
    double[] coefficients;
    int[] exponents;  
    
    // constructors 
    public Polynomial() {
        this.coefficients = new double[]{0.0};
        this.exponents = new int[]{0}; 
    }
    
    public Polynomial(double[] input, int[] exp) {
        this.coefficients = input;
        this.exponents = exp; 
    }
    
    public Polynomial(File givenFile){
        try (BufferedReader reading = new BufferedReader(new FileReader(givenFile))) {
            String line = reading.readLine();
            int length = findingLengthOfFile(givenFile);
            double[] coefficientstoadd = new double[length];
            int[] exponentstoadd = new int[length];
            boolean neg = false;
            int position = 0;

            String[] parts = line.split("(?<=[+=-])|(?=[+=-])");
            for (int i = 0; i < parts.length; i++){
                if (parts[i].equals("+")) {
                    neg = false;
                } else if (parts[i].equals("-")) {
                    neg = true;
                } else {
                    String[] sections = parts[i].split("x");
                    coefficientstoadd[position] = Double.parseDouble(sections[0]);
                    if (parts[i].contains("x")) {
                        if (sections.length > 1){
                            exponentstoadd[position] = Integer.parseInt(sections[1]);
                        } else {
                            exponentstoadd[position] = 1;
                        }
                    } else { 
                        exponentstoadd[position] = 0;
                    }
                    if (neg) {
                        coefficientstoadd[position] *= -1;
                    }
                    position++;
                }
            }
            this.exponents = exponentstoadd;
            this.coefficients = coefficientstoadd;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private int findingLengthOfFile(File givenFile) {
        int count = 0;
        try (BufferedReader reading = new BufferedReader(new FileReader(givenFile))) {
            String line;
            while ((line = reading.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (i == 0 && line.charAt(i) != '-') {
                        count++;
                    } else if (line.charAt(i) == '+' || line.charAt(i) == '-') {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    // helper function 
    /*private void sorting(){
        for (int i = 0; i< this.coefficients.length; i++){
            for (int j = i + 1; j < this.coefficients.length - 1; j++){
                if (this.exponents[j] < this.exponents[i]){
                    double switch_d = this.coefficients[i];
                    int switch_e = this.exponents[i];
                    this.coefficients[i] = this.coefficients[j];
                    this.coefficients[j] = switch_d;
                    this.exponents[i] = this.exponents[j];
                    this.exponents[j] = switch_e;
                }
            }
        }
    }*/

    private int length(Polynomial input){
        int count_similar = 0;
        int count_different = 0;
        for (int i=0; i < this.coefficients.length ; i++){
            int similar = 0;
            for (int j = 0; j < input.coefficients.length; j++){
                if (this.exponents[i] == input.exponents[j]){
                    similar = 1;
                    break;
                }
            }
            if (similar == 1) count_similar++;
            if (similar == 0) count_different++;
        }
        for (int i = 0; i < input.coefficients.length ; i++){
            int similar = 0;
            for (int j = 0 ; j < this.coefficients.length; j++){
                if (this.exponents[j] == input.exponents[i]){
                    similar = 1;
                    break;
                }
            }
            if (similar == 0) count_different++;
        }
        return count_similar + count_different;
    }

    // functions
    public Polynomial add(Polynomial input){
        int len = this.length(input);
        double[] coe = new double[len];
        int[] exp = new int[len];

        int sofarlen = 0;
        for (int i = 0; i < this.exponents.length; i++){
            double inputvalue = 0.0;
            for (int j = 0; j< input.exponents.length; j++){
                if (input.exponents[j] == this.exponents[i]){
                    inputvalue = input.coefficients[j];
                    break;
                }
            }
            exp[sofarlen] = this.exponents[i];
            coe[sofarlen] = inputvalue + this.coefficients[i];
            sofarlen++;
        }
        
        for (int i = 0; i < input.exponents.length; i++){
            int similar = 0;
            for (int j = 0; j < this.exponents.length; j++){
                if (this.exponents[j] == input.exponents[i]){
                    similar = 1;
                    break;
                }
            }
            if (similar == 0){
                exp[sofarlen] = input.exponents[i];
                coe[sofarlen] = input.coefficients[i];
                sofarlen++;
            }
        }

        return new Polynomial(coe, exp);
    }

    public Polynomial multiply(Polynomial input){
        Polynomial big_helper = new Polynomial();
        for (int i = 0; i < input.exponents.length; i++){
            for (int j = 0; j < this.exponents.length ; j++){
                double[] coe_help = {this.coefficients[j] * input.coefficients[i]};
                int[] expo_help = {this.exponents[j] + input.exponents[i]};
                Polynomial helper_this = new Polynomial(coe_help, expo_help);
                big_helper = big_helper.add(helper_this);
            }
        }
        return big_helper;
    }

    public double evaluate(double input){
        double results = 0.0;
        for (int i = 0; i < this.coefficients.length; i++){
            results += this.coefficients[i] * Math.pow(input, this.exponents[i]);
        }
        return results;
    }

    public boolean hasRoot(double input){
        double results = evaluate(input);
        return results == 0.0;
    }

    public void saveToFile(String filename){
		try (FileWriter file_update = new FileWriter(filename)){
			String helper = ""; // Initialize helper with an empty string
			for (int i = 0; i < this.coefficients.length; i ++){
				if (this.exponents[i] != 0){
					helper = helper + (int) this.coefficients[i] +  "x" + String.valueOf(this.exponents[i]);
				}
				else{
					helper = helper + (int) this.coefficients[i];
				}
				if ((i+1) < this.coefficients.length){helper = helper + "+";}
			}
			file_update.write(helper);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
}

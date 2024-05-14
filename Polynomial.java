public class Polynomial{
	//fields 
	double coefficents [];
	
	//constructor 
	public Polynomial() {
		this.coefficents = new double[]{0.0};
	}
	
	public Polynomial(double input[]) {
		this.coefficents = input;
	}
	
	public Polynomial add(Polynomial input){
		int size = 0;
		if (this.coefficents.length <= input.coefficents.length) {size = input.coefficents.length;}
		else {size = this.coefficents.length;}
		
		double [] var = new double [size];
		
		for (int i = 0; i < size; i++){
			if (i < this.coefficents.length) {
				var[i] = var[i] + this.coefficents[i];
			}
			if (i < input.coefficents.length) {
				var[i] = var[i] + input.coefficents[i];
			}
		}
		
		Polynomial return_value = new Polynomial(var);
		return return_value;
	}
		
	public double evaluate(double input){
		double results = 0.0;
		for (int i = 0; i < this.coefficents.length; i++) {
			if (i == 0) {
				results = results + this.coefficents[0];
			}
			else {
				results = results + (this.coefficents[i] * Math.pow(input, i));
			}
		}
		return results;
	}
	
	public boolean hasRoot(double input) {
		double results = evaluate(input);
		if (results == 0.0) {
			return true;
		}
		return false;
	}
}
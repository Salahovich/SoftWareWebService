package cs.software.project.service;


public class Wallet {

	double balance;
	
	
	public void deposit(double money) {
		balance += money;
	}
	public boolean withdraw(double money) {
		if(money > balance) {
			System.out.println("There's no enough money.");
			return false;
		}
		balance -= money;
		return true;
	}
	public double getBalance() {
		return this.balance;
	}
	public void displayBalance() {
		System.out.println("your balance = " + balance +"$");
	}
}

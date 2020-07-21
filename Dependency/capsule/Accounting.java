package capsul;

public class Accounting {

	
	private int balance; //잔고
	private int salePrice; //매출
	private int expences; //지출
	
	public Accounting() {
		
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getExpences() {
		return expences;
	}

	public void setExpences(int expences) {
		this.expences = expences;
	}
	

	// 매출등록을 담당하는 메서드
	public int registerSales(int payPrice) {
		balance += payPrice; //잔고
		salePrice += payPrice; //매출의 양
		return balance;
	}
	
	//지출등록을 담당하는 메서드
	public boolean registerExpences(int expencesPrice) {
		
		if(balance > expencesPrice) {
			balance -= expencesPrice;
			expences += expencesPrice;
			return true;
		}else{
			return false;
		}
	}
	
	
}

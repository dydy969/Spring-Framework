package capsul;

public class Coffee {

	private String name;
	private int salesPrice;
	private int perchacePrice;
	private int stock;
	private int safetyStock;
	private int salesCount;
	// 반품 카운트
	private int refundCount;
	
	private Accounting accounting;
	
	public Coffee(){}
	
	public Coffee(Accounting accounting) {
		this.accounting = accounting;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(int salesPrice) {
		this.salesPrice = salesPrice;
	}

	public int getPerchacePrice() {
		return perchacePrice;
	}

	public void setPerchacePrice(int perchacePrice) {
		this.perchacePrice = perchacePrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(int safetyStock) {
		this.safetyStock = safetyStock;
	}

	public int getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}

	public Accounting getAccounting() {
		return accounting;
	}

	public void setAccounting(Accounting accounting) {
		this.accounting = accounting;
	}
	
	//판매 등록
	public int registerSales(int salesCnt /*판매개수 */) {
		int payPrice = deductStock(salesCnt, salesPrice);
		
		if(payPrice > 0) {
			salesCount +=salesCnt;
			
		}
		return payPrice;
	}
	
	//재고 환불 추가
	public int registerRefund(int refundCnt) {
		//환불갯수 * 판매가
		int expencesPrice = addStock(refundCnt, salesPrice);
		
		if(expencesPrice > 0) {
			expencesPrice -= refundCnt;
			return expencesPrice;
		
		} else {
			
			return refundCnt;
		}
	}
	
	public int registerRetrun(int refundCnt) {

		
		
		return deductStock(refundCnt, perchacePrice);
	}
	
	
	//재고 차감 
	private int deductStock(int coffeeCnt, int price) {
		
		secureSafetyStock();
		
		if(stock > coffeeCnt) {
			stock -= coffeeCnt;
			return price * coffeeCnt;
		} else if(addStock(coffeeCnt, perchacePrice ) > 0){
			stock -= coffeeCnt;
			return price * coffeeCnt;
		} else {
			salesCount -= coffeeCnt;
			return 0;
		}
	}
	
	//재고 추가
	private int addStock(int coffeeCnt, int price) {
		
		int expencesPrice = coffeeCnt * price;
		if(accounting.registerExpences(expencesPrice)) {
			System.out.println(" * 재고를 " + coffeeCnt + "개 추가 합니다");
			stock += coffeeCnt;
			return expencesPrice;
		}else {
			System.out.println(" * 잔고가 부족해 재고를 추가하지 못했습니다");
			return 0;
		}
	}
	
	//안전재고 확보
	private void secureSafetyStock() {
		
		if(stock < safetyStock) {
			System.out.println("안전 재고가 부족해 재고를 확보합니다");
			int needCnt = safetyStock * 2;
			addStock(needCnt, perchacePrice);
		}
	}
}

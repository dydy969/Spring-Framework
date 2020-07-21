package capsul;

public class Sales {

      private Accounting accounting;
      
      public Sales() {}
      
      public Sales(Accounting accounting) {
         
         this.accounting = accounting;
      }
      //제품 판매
      public int sellProduct(Coffee coffee, int salesCnt) {
         
         //커피 객체에 판매를 등록하고 결제금액을 반환받는다.
         int payPrice = coffee.registerSales(salesCnt);
         
         // 결제금액이 0보다 크면 결제가 정상적으로 진행되었다는 뜻이다.
         if(payPrice > 0) {
            //계좌 객체에 매출을 등록한다.
            accounting.registerSales(payPrice);
         }
         
         //결제금액을 반환한다.
         return payPrice;
      }
      
      //제품 환불
      public int refundProduct(Coffee coffee, int refundCnt) {
         
         //커피 객체를 환불 등록하고 환불금액을 반환받는다.
         int refundPrice = coffee.registerRefund(refundCnt);
         
         //환불금액이 0보다 크면 결제가 정상적으로 진행되었다는 뜻
         if(refundPrice > 0) {
            //계좌 객체 
            accounting.registerExpences(refundPrice);
            
         }
         
         return refundPrice;
      }
      
      public int returnProduct(Coffee coffee, int returnCnt) {
    	  int returnPrice = coffee.registerRetrun(returnCnt);
    	  if(returnPrice > 0) {
    		  accounting.registerSales(returnPrice);
    	  }
    	  return returnPrice;
      }
}

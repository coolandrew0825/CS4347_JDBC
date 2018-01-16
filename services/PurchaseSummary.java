package cs4347.jdbcProject.ecomm.services;

/**
 * This is a DTO (Data Transfer Object) that is 
 * used to return the results of the purchase summary
 * operation. 
 */
public class PurchaseSummary
{
	public float minPurchase;
	public float maxPurchase;
	public float avgPurchase;
	
	public void minPurchase(float minPurchase) {
		this.minPurchase = minPurchase;
	}
	
	public void maxPurchase(float maxPurchase) {
		this.maxPurchase = maxPurchase;
	}
	
	public void avgPurchase(float avgPurchase) {
		this.avgPurchase = avgPurchase;
	}
}

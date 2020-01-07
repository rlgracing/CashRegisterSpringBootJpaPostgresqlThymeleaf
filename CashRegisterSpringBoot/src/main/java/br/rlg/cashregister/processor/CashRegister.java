package br.rlg.cashregister.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.rlg.cashregister.processor.campaign.CouponsCampaign;
import br.rlg.cashregister.processor.campaign.DiscountsCampaign;
import br.rlg.cashregister.processor.model.Discount;
import br.rlg.cashregister.processor.model.Item;
import br.rlg.cashregister.processor.model.Product;
import br.rlg.cashregister.util.UnitType;

@Component
public class CashRegister {
	
	private List<Product> products = new ArrayList<Product>();
	private List<Item> items = new ArrayList<Item>();
	private Map<Integer, Integer> itemsCounter = new HashMap<Integer, Integer>();
	private CouponsCampaign couponsCampaign;
	private DiscountsCampaign discountsCampaign;
	
	public CashRegister() {
		
		//Setting default products in stock
		products.addAll(Arrays.asList(new Product(1, "Boxes of Cheerios", 6.99, UnitType.EACH),
										new Product(2, "Fuji Apples", 2.49, UnitType.LB),
										new Product(3, "Coca-Cola", 5, UnitType.LT),
										new Product(4, "Cookies", 4.12, UnitType.EACH),
										new Product(5, "Beer pack 12", 12.5, UnitType.EACH),
										new Product(6, "Carrots", 1.49, UnitType.LB),
										new Product(7, "Oranges", 5.5, UnitType.LB)
									));

		//Setting default coupon campaign: 5 dollars coupon each 50 dollars purchase
		couponsCampaign = new CouponsCampaign(50, 5);
		
		//Setting default discount campaign: products 1 and 3 with discount if buying more than 2 items
		discountsCampaign = new DiscountsCampaign();
		discountsCampaign.addCampaign(new Discount(1, 2, 25));
		discountsCampaign.addCampaign(new Discount(3, 2, 50));
		
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void setCouponsCampaign(CouponsCampaign couponsCampaign) {
		this.couponsCampaign = couponsCampaign;
	}

	public CouponsCampaign getCouponsCampaign() {
		return couponsCampaign;
	}

	public void setDiscountsCampaign(DiscountsCampaign discountsCampaign) {
		this.discountsCampaign = discountsCampaign;
	}

	public DiscountsCampaign getDiscountsCampaign() {
		return discountsCampaign;
	}

	public Product getProduct(int productId) {
		
		return products.stream()
						.filter(product -> product.getId() == productId)
						.findFirst()
						.orElse(null);
	}

	public void addItem(Item item) {
		
		items.add(item);
		
		int productID = item.getProduct().getId();
		int count = 0;
		
		if(itemsCounter.get(productID) != null) {
			if(item.getProduct().getUnitType().equals(UnitType.EACH) || item.getProduct().getUnitType().equals(UnitType.LT))
				count = itemsCounter.get(productID) + new Double(item.getQuantity()).intValue();
			else
				count = itemsCounter.get(productID) + 1;
		} else {
			if(item.getProduct().getUnitType().equals(UnitType.EACH) || item.getProduct().getUnitType().equals(UnitType.LT))
				count = new Double(item.getQuantity()).intValue();
			else
				count = 1;
		}
		
		itemsCounter.put(item.getProduct().getId(), count);
	}

	public void addItems(Item... item) {
		
		for(Item itemx : Arrays.asList(item)) {
			
			this.addItem(itemx);
		}
	}
	
	public double getSubTotalAmount() {
		
		return items.stream()
					.mapToDouble(item -> item.getItemPrice())
					.sum();

	}
	
	public Item getItem(int productID) {
		
		return items.stream()
					.filter(item -> item.getProduct().getId() == productID)
					.findFirst()
					.get();
	}
	
	public List<Item> getAllItems() {
		
		return items;
	}
	
	public int getCoupons() {

		if(couponsCampaign != null) {
			
			return couponsCampaign.getCoupons(getTotalAmount());
		}
		
		return 0;
	}
	
	public Map<Product, Double> getDiscountItems() {

		Map<Product, Double> discountsMap = new HashMap<Product, Double>();
		if(itemsCounter != null) {
			
			for (Iterator<Integer> iterator = itemsCounter.keySet().iterator(); iterator.hasNext();) {
				int productID = iterator.next();
				
				Discount discounts = discountsCampaign.getDiscounts(productID);
				
				if(discounts != null && itemsCounter.get(productID) >= discounts.getQuantity()) {
					
					Item item = getItem(productID);
					
					if(item != null) {
						Product product = item.getProduct();
						
						double discountValue = (product.getPrice() * (discounts.getDiscountPercentage()/100.0) * itemsCounter.get(productID));

						discountsMap.put(product,  discountValue);
					}
				}
			}
		}
		
		return discountsMap;
	}

	public double getTotalDiscountAmount() {
		
		return getDiscountItems().entrySet()
								.stream()
								.mapToDouble(entry -> entry.getValue())
								.sum();
	}
	
	public double getTotalAmount() {
		
		return getSubTotalAmount() - getTotalDiscountAmount();
	}
	
	public Map<Integer, Integer> getItemsCounter() {
		
		return itemsCounter;
	}
}

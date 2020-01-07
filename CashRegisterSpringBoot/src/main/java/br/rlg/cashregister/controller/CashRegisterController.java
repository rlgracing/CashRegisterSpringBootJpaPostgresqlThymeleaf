package br.rlg.cashregister.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.rlg.cashregister.controller.model.CouponsView;
import br.rlg.cashregister.controller.model.DiscountItemView;
import br.rlg.cashregister.controller.model.ItemView;
import br.rlg.cashregister.controller.model.ProductView;
import br.rlg.cashregister.processor.CashRegister;
import br.rlg.cashregister.processor.campaign.DiscountsCampaign;
import br.rlg.cashregister.processor.model.Item;
import br.rlg.cashregister.processor.model.Product;
import br.rlg.cashregister.util.UnitType;

@Controller
public class CashRegisterController {

	@Autowired
	private CashRegister cashRegister;
	
	@GetMapping("/admin")
	public ModelAndView admin() {

		ModelAndView mv = new ModelAndView("admin");
		
		List<ProductView> productsView = cashRegister.getProducts().stream()
																	.map(product -> new ProductView(product.getId(),
																									product.getDescription(),
																									String.format("%.2f",product.getPrice()),
																									product.getUnitType()))
																	.collect(Collectors.toList());

		mv.addObject("products", productsView);
		
		return mv;
	}

	@PostMapping("/addProduct")
	public ModelAndView addProduct(@RequestParam("productId") String productId,
								@RequestParam("description") String description,
								@RequestParam("price") String price,
								@RequestParam("unitType") String unitType) {
		
		ModelAndView mv = new ModelAndView("admin");

		List<Product> products = cashRegister.getProducts();
		
		try {
			
			products.add(new Product(Integer.parseInt(productId),
									description,
									Double.parseDouble(price),
									UnitType.EACH));
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		List<ProductView> productsView = cashRegister.getProducts().stream()
				.map(product -> new ProductView(product.getId(),
												product.getDescription(),
												String.format("%.2f",product.getPrice()),
												product.getUnitType()))
				.collect(Collectors.toList());

		mv.addObject("products", productsView);
		
		return mv;
	}

	@GetMapping("/")
	public ModelAndView user() {

		ModelAndView mv = new ModelAndView("index");

		mv.addObject("subTotal", String.format("%.2f", 0.0));
		mv.addObject("totalDiscount", String.format("%.2f", 0.0));
		mv.addObject("total", String.format("%.2f", 0.0));
		mv.addObject("coupons", new CouponsView(0, 0, 0));
		
		cashRegister = new CashRegister();
		
		return mv;
	}

	@PostMapping("/getProduct")
	public ModelAndView getProduct(@RequestParam("productId") String productId) {
		
		ModelAndView mv = new ModelAndView("index");
		
		int productIdInt = 0;
		
		try {
			
			productIdInt = Integer.parseInt(productId);

			Product product = cashRegister.getProduct(productIdInt);
			
			if(product != null) {

				mv.addObject("product", new ProductView(product.getId(),
														product.getDescription(),
														String.format("%.2f", product.getPrice()),
														product.getUnitType()));

			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return addModelViewObjects(cashRegister, mv);
	}
	
	@PostMapping("/addItem")
	public ModelAndView addItem(@RequestParam("productId") String productId, @RequestParam("quantity") String quantity) {

		ModelAndView mv = new ModelAndView("index");
		
		try {
			
			int productIdInt = Integer.parseInt(productId);

			Product product = cashRegister.getProduct(productIdInt);
			
			if(product != null) {

				double quantityDouble = Double.parseDouble(quantity);
				
				if(quantityDouble > 0) {

					cashRegister.addItem(new Item(product, quantityDouble));
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return addModelViewObjects(cashRegister, mv);
	}
	
	private ModelAndView addModelViewObjects(CashRegister cashRegister, ModelAndView mv) {
		
 		mv.addObject("subTotal", String.format("%.2f", cashRegister.getSubTotalAmount()));
		mv.addObject("totalDiscount", String.format("%.2f", cashRegister.getTotalDiscountAmount()));
		mv.addObject("total", String.format("%.2f", cashRegister.getTotalAmount()));
		
		CouponsView couponsView = new CouponsView(	cashRegister.getCoupons(),
													cashRegister.getCouponsCampaign().getMoneyOff(),
													cashRegister.getCouponsCampaign().getThreshould());
		
		mv.addObject("coupons", couponsView);
		
		List<Item> items = cashRegister.getAllItems();
		
		List<ItemView> itemsView = items.stream()
										.map(item -> new ItemView(item.getProduct().getId(), 
																	item.getProduct().getDescription(), 
																	String.format("%.2f", item.getProduct().getPrice()), 
																	item.getProduct().getUnitType(), 
																	item.getQuantity(), 
																	String.format("%.2f", item.getItemPrice())))
										.collect(Collectors.toList());

		mv.addObject("items", itemsView);

		Map<Product, Double> discountItems = cashRegister.getDiscountItems();
		DiscountsCampaign discountCampaign = cashRegister.getDiscountsCampaign();
		Map<Integer, Integer> itemsCounter = cashRegister.getItemsCounter();
		
		List<DiscountItemView> discountItemsView	= discountItems.entrySet()
																.stream()
																.map(entry -> new DiscountItemView(entry.getKey().getId(), 
																									entry.getKey().getDescription(), 
																									discountCampaign.getDiscounts(entry.getKey().getId()).getDiscountPercentage(), 
																									itemsCounter.get(entry.getKey().getId()), 
																									String.format("%.2f", entry.getValue())))
																.collect(Collectors.toList());

		mv.addObject("discountItems", discountItemsView);

		return mv;
	}
}

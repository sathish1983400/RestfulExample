package com.mkyong.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.mkyong.Product;

@Path("/json/product")
public class JSONServiceSathish {

	

		@GET
		@Path("/get")
		@Produces("application/json")
		public Product getProductInJSON() {

			Product product = new Product();
			product.setName("iPad 3");
			product.setQty(999);

			return product;

		}
	
}

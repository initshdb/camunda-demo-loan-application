package org.demo.camunda.loan_application.external;

public class Product {
	private String id;
	private String name;
	private ProductData data;

	public Product() {
	}

	public Product(String id, String name, ProductData data) {
		this.id = id;
		this.name = name;
		this.data = data;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductData getData() {
		return data;
	}

	public void setData(ProductData data) {
		this.data = data;
	}

	// Nested class
	public static class ProductData {
		private String color;
		private int capacityGB;

		public ProductData() {
		}

		public ProductData(String color, int capacityGB) {
			this.color = color;
			this.capacityGB = capacityGB;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public int getCapacityGB() {
			return capacityGB;
		}

		public void setCapacityGB(int capacityGB) {
			this.capacityGB = capacityGB;
		}
	}
}

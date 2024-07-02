import { request } from "../util/ApiFunction";

export const loadProducts = async function () {
    try {
      
      const response = await request("GET", `/api/products`);
      
      if (response.status === 200) {
          console.log("API Response Data:", response.data); // Kiểm tra phản hồi của API
          return response.data;
      } else {
          throw new Error("Failed to load products items");
      }
    } catch (error) {
        console.error("Error loading products items:", error);
        throw error;
    }
  };
  export const loadProductById = async function (productId) {
    try {
      
      const response = await request("GET", `/api/products/${productId}`);
      
      if (response.status === 200) {
          console.log("API Response Data:", response.data); // Kiểm tra phản hồi của API
          return response.data;
      } else {
          throw new Error("Failed to load products items");
      }
    } catch (error) {
        console.error("Error loading products items:", error);
        throw error;
    }
  };
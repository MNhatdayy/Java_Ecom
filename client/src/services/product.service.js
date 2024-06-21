import { request } from "../util/ApiFunction";

const getListProduct = async function getListProduct() {
	const respone = await request("GET", "products");
	if (respone.status === 200) {
		return respone.data;
	} else {
		return null;
	}
};

const createProduct = async function createProduct(data) {
	const respone = await request("POST", "products", { data });
	if (respone.status === 200) {
		return respone.data;
	} else {
		console.log("error");
	}
};

const getProduct = async function getProduct(id) {
	const respone = await request("GET", `products/${id}`);
	if (respone.status === 200) {
		console.log(respone.data);
	} else {
		console.log("error");
	}
};

const updateProduct = async function updateProduct(data) {
	const respone = await request("PUT", "products", data);
	if (respone.status === 200) {
		console.log(respone.data);
	} else {
		console.log("error");
	}
};

const deleteProduct = async function deleteProduct(id) {
	const respone = await request("DELETE", "products", id);
	if (respone.status === 200) {
		console.log(respone.data);
	} else {
		console.log("error");
	}
};

const ProductServices = {
	getListProduct,
	getProduct,
	createProduct,
	updateProduct,
	deleteProduct,
};

export default ProductServices;

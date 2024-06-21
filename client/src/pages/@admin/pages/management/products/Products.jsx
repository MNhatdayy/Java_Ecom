import "./products.scss";
import ProductServices from "../../../../../services/product.service";
import AuthServices from "../../../../../services/auth.service";

import { useEffect, useState } from "react";

const Products = () => {
	const [products, setProducts] = useState([]);
	const [token, setToken] = useState([]);
	useEffect(() => {
		const fetch = async () => {
			const productsData = await ProductServices.getListProduct();
			setProducts(productsData);

			const tokenTemp = await AuthServices.login("congthien", "123");
			setToken(tokenTemp);
		};
		fetch();
	}, []);

	return (
		<>
			<h1>Management Products</h1>
			{products.map((product) => (
				<h1 key={product.id}> {product.name} </h1>
			))}

			{token}
		</>
	);
};

export default Products;

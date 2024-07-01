import { useParams } from "react-router-dom";
import "./shopcategory.scss";
import { useEffect, useState } from "react";

const ShopCategory = () => {
	const params = useParams();
	const [category, setCategory] = useState(params["*"].substring(8));
	useEffect(() => {
		setCategory(params["*"].substring(8));
	}, [category, params]);

	return (
		<>
			<h1>shop category {category}</h1>
		</>
	);
};

export default ShopCategory;

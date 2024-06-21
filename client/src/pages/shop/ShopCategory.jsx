import { useParams } from "react-router-dom";

const ShopCategory = () => {
	const params = useParams();
	console.log(params);
	return (
		<>
			<h1>shop category</h1>
		</>
	);
};

export default ShopCategory;

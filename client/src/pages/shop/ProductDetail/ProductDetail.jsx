import { useParams } from "react-router-dom";
import "./productdetail.scss";

const ProductDetail = () => {
	const params = useParams();
	return (
		<>
			<h1>Product Detail {params}</h1>
		</>
	);
};

export default ProductDetail;

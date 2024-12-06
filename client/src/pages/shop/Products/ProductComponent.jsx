/* eslint-disable react/prop-types */
import "./productComponent.scss";
import { Button } from "antd";
import { SearchOutlined, ShoppingCartOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import { addToCart } from "../../../services/CartController";
import { useNavigate } from "react-router-dom";
const ProductComponent = ({ product }) => {
	const navigate = useNavigate();
	const handleAddToCart = (cartId, quantity) => {
		addToCart(cartId, quantity)
			.then((response) => {
				console.log("Cart updated:", response);
				navigate("/shop/cart");
			})
			.catch((error) => {
				console.error("Error updating cart:", error);
				// Xử lý lỗi nếu có
			});
	};
	return (
		<>
			<div className="product--card">
				<div className="product--img">
					<img
						loading="lazy"
						src={`http://localhost:8099${product.imageUrl}`}
						alt=""
					/>
					<div className="info">
						<div className="actions">
							<Button size="large" type="primary" shape="circle">
								<Link to={`/shop/product/${product.id}`}>
									<SearchOutlined />
								</Link>
							</Button>
							<Button
								size="large"
								type="primary"
								shape="circle"
								onClick={() => handleAddToCart(product.id, 1)}>
								<ShoppingCartOutlined />
							</Button>
						</div>
					</div>
				</div>
				<div className="product--description">
					<p className="product--type">KEYCAP</p>
					<p className="product--name"> Name: {product.name} </p>
					<p className="product--price">
						{/* <span className="price-sale">{product.priceSale}</span> */}

						<span className="price-sale">
							{new Intl.NumberFormat("vi-VN", {
								style: "currency",
								currency: "VND",
							}).format(product.price)}
						</span>
					</p>
				</div>
			</div>
		</>
	);
};

export default ProductComponent;

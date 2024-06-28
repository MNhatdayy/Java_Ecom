/* eslint-disable react/prop-types */
import "./productComponent.scss";
import { Button } from "antd";
import { SearchOutlined, ShoppingCartOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
const ProductComponent = ({ product }) => {
	return (
		<>
			<div className="product--card">
				<div className="product--img">
					<img
						loading="lazy"
						src="https://bizweb.dktcdn.net/thumb/large/100/484/752/products/gmk-analog-dreams-digital-nightmares-1688822432975-182787ab-1a77-441b-a409-e932b8c8cf1a.jpg?v=1719248236107"
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
								icon={<ShoppingCartOutlined />}
							/>
						</div>
					</div>
				</div>
				<div className="product--description">
					<p className="product--type">KEYCAP</p>
					<p className="product--name"> Name: {product.name} </p>
					<p className="product--price">
						<span className="price-sale">{product.priceSale}</span>

						<span className="price">{product.price}</span>
					</p>
				</div>
			</div>
		</>
	);
};

export default ProductComponent;

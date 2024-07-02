import { useParams } from "react-router-dom";
import "./productdetail.scss";
import { Breadcrumb, Button, InputNumber } from "antd";
import Slider from "react-slick";
import { useEffect, useState } from "react";
import { loadProductById } from "../../../services/HomeController";
import { addToCart } from "../../../services/CartController";

var settings = {
	dots: true,
	infinite: true,
	speed: 500,
	slidesToShow: 1,
	slidesToScroll: 1,
};

const ProductDetail = () => {
	const { id } = useParams();
	const [productDetail, setProductDetail] = useState("");
	const [quantity, setQuantity] = useState(1);
	const [itemBread, setItem] = useState([]);
	const onChange = (value) => {
		setQuantity(value);
	};
	const handleAddToCart = (cartId, quantity) => {
		addToCart(cartId, quantity)
			.then((response) => {
				console.log("Cart updated:", response);
			})
			.catch((error) => {
				console.error("Error updating cart:", error);
				// Xử lý lỗi nếu có
			});
	};
	useEffect(() => {
		const fetchCartItems = async () => {
			try {
				const data = await loadProductById(id);
				setProductDetail(data || []);
				setItem([
					{
						title: "Home",
					},
					{
						title:
							productDetail.category?.name || "Unknown Category",
						href: `/shop/product/${
							productDetail.category?.name || "unknown"
						}`,
					},
				]);
			} catch (error) {
				console.error("Error loading cart items:", error);
			}
		};
		fetchCartItems();
	}, [id, itemBread]);

	return (
		<>
			<div className="product--detail--wrapper">
				<div className="breadcrumb">
					<Breadcrumb separator=">" items={itemBread} />
				</div>
				<div className="product--detail--container">
					<div className="product--detail">
						<div className="product--slide">
							<Slider {...settings}>
								{/* {productDetail.Images.map((ele) => {
									<div className="product--slide--img">
										<img
											width="100%"
											src={`http://localhost:8099${ele}`}
											alt="img"
										/>
									</div>;
								})} */}
							</Slider>
							<div className="product--slide--img">
								<img
									width="100%"
									src={`http://localhost:8099${productDetail.imageUrl}`}
									alt="img"
								/>
							</div>
						</div>

						<div className="product--info">
							<h3 className="product--name">
								{productDetail.name}
							</h3>
							<p className="product--vendor">
								{productDetail.description}
							</p>
							<p className="product--price">
								{new Intl.NumberFormat("vi-VN", {
									style: "currency",
									currency: "VND",
								}).format(productDetail.price)}
							</p>
							{/* <p className="product--version">
								Version: Bridge75
							</p> */}

							<div className="product--actions">
								<InputNumber
									min={1}
									max={10}
									defaultValue={1}
									onChange={onChange}
								/>
								<Button
									block
									className="btn-add-cart"
									href="/cart"
									onClick={() =>
										handleAddToCart(
											productDetail.id,
											quantity
										)
									}>
									Add to cart
								</Button>
							</div>
							<div className="product--buy">
								<Button
									type="primary"
									size="large"
									shape="round"
									block>
									Buy now
								</Button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</>
	);
};

export default ProductDetail;

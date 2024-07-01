import { useParams } from "react-router-dom";
import "./productdetail.scss";
import { Breadcrumb, Button, InputNumber } from "antd";
import { Tabs } from "antd";
import Slider from "react-slick";
var settings = {
	dots: true,
	infinite: true,
	speed: 500,
	slidesToShow: 1,
	slidesToScroll: 1,
};

const items = [
	{
		key: "1",
		label: "Infomation",
		children: ` Chất liệu: ABS Double-Shot`,
	},
	{
		key: "2",
		label: "Payment guide",
		children: (
			<div className="payment-guide">
				<h4>Hướng dẫn mua hàng:</h4>
				<ul>
					<li>
						Truy cập vào link bán hàng trên web MOKB và chọn sản
						phẩm cần mua
					</li>
					<li>Điều chỉnh số lượng sản phẩm muốn mua theo ý muốn</li>
					<li>Chọn "thêm vào giỏ hàng" hoặc "Mua ngay"</li>
				</ul>
			</div>
		),
	},
];

const onChange = (value) => {
	console.log("changed", value);
};

const onChangeTabs = (key) => {
	console.log(key);
};

const ProductDetail = () => {
	const { id } = useParams();

	const productDetail = {
		id: id,
		name: "Bridge75 - Bàn phím cơ nhôm gaming 3 mode",
		price: "1.740.000₫",
		vendor: "Shortcut Studio",
		thumbnail:
			"https://bizweb.dktcdn.net/thumb/1024x1024/100/484/752/products/bridge75-black-no-rgb-1714960892028.jpg?v=1714967409093",
		listImg: [
			"https://bizweb.dktcdn.net/thumb/1024x1024/100/484/752/products/bridge75-black-no-rgb-1714960892028.jpg?v=1714967409093",
			"https://bizweb.dktcdn.net/thumb/1024x1024/100/484/752/products/bridge75-white-plus-ver-1714960927140.jpg?v=1714967409093",
			"https://bizweb.dktcdn.net/thumb/1024x1024/100/484/752/products/bridge75-cream-plus-1714960939334.jpg?v=1714967409093",
			"https://bizweb.dktcdn.net/thumb/1024x1024/100/484/752/products/bridge75-pink-plus-ver-1714960950334.jpg?v=1714967409093",
		],
		category: {
			id: 1,
			name: "Kit",
		},
	};

	return (
		<>
			<div className="product--detail--wrapper">
				<div className="breadcrumb">
					<Breadcrumb
						separator=">"
						items={[
							{
								title: "Home",
							},
							{
								title: "Kit",
								href: "/shop/product/kit",
							},
						]}
					/>
				</div>
				<div className="product--detail--container">
					<div className="product--detail">
						<div className="product--slide">
							<Slider {...settings}>
								{productDetail.listImg.map((value, index) => {
									return (
										<div
											key={index}
											className="product--slide--img">
											<img
												width="100%"
												src={value}
												alt="img"
											/>
										</div>
									);
								})}
							</Slider>
						</div>

						<div className="product--info">
							<h3 className="product--name">
								{productDetail.name}
							</h3>
							<p className="product--vendor">
								{productDetail.vendor}
							</p>
							<p className="product--price">
								{productDetail.price}
							</p>
							<p className="product--version">
								Version: Bridge75
							</p>

							<div className="product--actions">
								<InputNumber
									min={1}
									max={10}
									defaultValue={1}
									onChange={onChange}
								/>
								<Button block className="btn-add-cart">
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
					<div className="product--detail--description">
						<Tabs
							defaultActiveKey="1"
							centered
							items={items}
							onChange={onChangeTabs}
						/>
					</div>
				</div>
			</div>
		</>
	);
};

export default ProductDetail;

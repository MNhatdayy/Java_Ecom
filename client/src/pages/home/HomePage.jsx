import "./home.scss";
import { Button, Layout } from "antd";
import Slider from "react-slick";

const { Sider, Content } = Layout;

import ProductComponent from "../shop/Products/ProductComponent";
const siderStyle = {
	backgroundColor: "white",
};

const layoutStyle = {
	borderRadius: 8,
	width: "100%",
	backgroundColor: "white",
};

const contentStyle = {
	minHeight: 100,
	color: "#fff",
	backgroundColor: "white",
};

var settings = {
	dots: true,
	infinite: true,
	speed: 500,
	slidesToShow: 1,
	slidesToScroll: 1,
};

const HomePage = () => {
	const dataProduct = [
		{
			id: 1,
			name: "Kit Neo65",
			price: 3500000,
			priceSale: 300000,

			description: "Layout 65% from NEO Keyboard",
			thumbnail: "/images/products/neo.jpg",
		},
		{
			id: 2,
			name: "Kit Neo80",
			price: 3500000,
			priceSale: 3000000,

			description: "Layout 80 from NEO Keyboard",
			thumbnail: "/images/products/neo.jpg",
		},
		{
			id: 2,
			name: "Kit Neo80",
			price: 3500000,
			priceSale: 2500000,

			description: "Layout 80 from NEO Keyboard",
			thumbnail: "/images/products/neo.jpg",
		},
		{
			id: 2,
			name: "Kit Neo80",
			price: 3500000,
			priceSale: 1900000,

			description: "Layout 80 from NEO Keyboard",
			thumbnail: "/images/products/neo.jpg",
		},
		{
			id: 2,
			name: "Kit Neo80",
			price: 3500000,
			priceSale: 3200000,
			description: "Layout 80 from NEO Keyboard",
			thumbnail: "/images/products/neo.jpg",
		},
		{
			id: 2,
			name: "Kit Neo80",
			price: 3500000,
			priceSale: 1900000,

			description: "Layout 80 from NEO Keyboard",
			thumbnail: "/images/products/neo.jpg",
		},
	];
	return (
		<div>
			<Layout>
				<Layout style={layoutStyle}>
					<Sider width="100%" style={siderStyle}>
						<Slider {...settings}>
							<div className="img-slide">
								<img
									width="100%"
									src="https://bizweb.dktcdn.net/100/484/752/themes/920128/assets/slider_3.jpg?1717509348028"
									alt=""
								/>
							</div>
							<div className="img-slide">
								<img
									width="100%"
									src="https://bizweb.dktcdn.net/100/484/752/products/bridge75-render-1-1714960856873.jpg?v=1714967409093"
									alt=""
								/>
							</div>
							<div className="img-slide">
								<img
									width="100%"
									src="https://bizweb.dktcdn.net/thumb/1024x1024/100/484/752/products/qk65v2-classic-case-1-1716989514613.jpg?v=1716989518583"
									alt=""
								/>
							</div>
						</Slider>
					</Sider>
				</Layout>
				<Content style={contentStyle}>
					<div className="container">
						<div className="service--wrapper">
							<div className="service--card">
								<div className="service--img">
									<i className="fa-solid fa-truck"></i>
								</div>
								<div className="service--info">
									<h4 className="service--title">
										Free Shipping
									</h4>
									<p className="service--description">
										Free shipping on all order
									</p>
								</div>
							</div>
							<div className="service--card">
								<div className="service--img">
									<i className="fa-solid fa-clock"></i>
								</div>
								<div className="service--info">
									<h4 className="service--title">
										Support Service
									</h4>
									<p className="service--description">
										Fast & Careful
									</p>
								</div>
							</div>
							<div className="service--card">
								<div className="service--img">
									<i className="fa-solid fa-credit-card"></i>
								</div>
								<div className="service--info">
									<h4 className="service--title">VN Pay</h4>
									<p className="service--description">
										Smart pay
									</p>
								</div>
							</div>
							<div className="service--card">
								<div className="service--img">
									<i className="fa-solid fa-tags"></i>
								</div>
								<div className="service--info">
									<h4 className="service--title">
										Order Discount
									</h4>
									<p className="service--description">
										Vouncher, sales, gift
									</p>
								</div>
							</div>
						</div>

						<div className="sale--wrapper">
							<div className="title flip-animation">
								<span>S</span>
								<span>A</span>
								<span>L</span>
								<span>E</span>
								<span>S</span>
							</div>

							<div className="list-product">
								{dataProduct.map((value, index) => {
									return (
										<ProductComponent
											key={index}
											product={value}
										/>
									);
								})}
							</div>
							<div className="actions">
								<Button shape="round" type="primary">
									View more details
								</Button>
							</div>
						</div>

						<div className="gb--wrapper">
							<div className="title flip-animation">
								<span>I</span>
								<span>N</span>
								<span>S</span>
								<span>T</span>
								<span>O</span>
								<span>C</span>
								<span>K</span>
							</div>

							<div className="list-product">
								{dataProduct.map((value, index) => {
									return (
										<ProductComponent
											key={index}
											product={value}
										/>
									);
								})}
							</div>
							<div className="actions">
								<Button shape="round" type="primary">
									View more details
								</Button>
							</div>
						</div>
					</div>
				</Content>
			</Layout>
		</div>
	);
};

export default HomePage;

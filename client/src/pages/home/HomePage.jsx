import "./home.scss";
import { Layout } from "antd";
import Slider from "react-slick";

const { Sider, Content } = Layout;
const siderStyle = {
	// lineHeight: "120px",
	backgroundColor: "white",
};

const layoutStyle = {
	borderRadius: 8,
	width: "100%",
	height: "100vh",
	backgroundColor: "white",
};

const contentStyle = {
	textAlign: "center",
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
	return (
		<div>
			<Layout>
				<Layout style={layoutStyle}>
					<Sider width="100%" style={siderStyle}>
						<Slider {...settings}>
							<div>
								<img
									width="100%"
									src="https://bizweb.dktcdn.net/100/484/752/themes/920128/assets/slider_3.jpg?1717509348028"
									alt=""
								/>
							</div>
							<div>
								<img
									width="100%"
									src="https://bizweb.dktcdn.net/100/484/752/products/bridge75-render-1-1714960856873.jpg?v=1714967409093"
									alt=""
								/>
							</div>
							<div>
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
					<div className="sale">
						<h1>Sale Product</h1>
					</div>
					<div className="list product">
						<h1>List product Product</h1>
					</div>
				</Content>
			</Layout>
		</div>
	);
};

export default HomePage;

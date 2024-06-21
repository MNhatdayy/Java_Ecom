/* eslint-disable no-unused-vars */
// import { useState } from 'react'
import "./App.scss";

import { Layout, ConfigProvider } from "antd";

const { Footer, Content } = Layout;

const layoutStyle = {
	overflow: "hidden",
	width: "100%",
	height: "100vh",
	maxWidth: "calc(100% - 8px)",
};

const headerStyle = {
	textAlign: "center",
	color: "#fff",
	width: "100%",
	height: "70px",
	lineHeight: "70px",
	backgroundColor: "white",
};

const contentStyle = {
	textAlign: "center",
	minHeight: 120,
	lineHeight: "120px",
	color: "#fff",
	backgroundColor: "white",
};

const footerStyle = {
	textAlign: "center",
	color: "#fff",
	backgroundColor: "white",
};

import {
	BrowserRouter as Router,
	Routes,
	Route,
	Navigate,
} from "react-router-dom";

import Login from "./@auth/components/login/Login.jsx";
import Register from "./@auth/components/register/Register.jsx";

import LayoutAdmin from "./pages/@admin/layout/LayoutAdmin.jsx";
import Dashboard from "./pages/@admin/pages/dashboard/Dashboard.jsx";
import User from "./pages/@admin/pages/management/users/Users.jsx";
import Categories from "./pages/@admin/pages/management/categories/Categories.jsx";
import Invoices from "./pages/@admin/pages/management/invoices/Invoices.jsx";
import Products from "./pages/@admin/pages/management/products/Products.jsx";

import LayoutShop from "./pages/layout/LayoutShop.jsx";
import HomePage from "./pages/home/HomePage.jsx";
import ShopCategory from "./pages/shop/ShopCategory.jsx";

import AuthServices from "./services/auth.service.js";
import ProductDetail from "./pages/shop/ProductDetail/ProductDetail.jsx";
import Liked from "./pages/liked/Liked.jsx";
import Cart from "./pages/cart/Cart.jsx";

function App() {
	// const [count, setCount] = useState(0)
	AuthServices.login();
	const role = AuthServices.parseToken().role;
	return (
		<ConfigProvider
			theme={{
				token: {
					// Seed Token
					colorPrimary: "#00b96b",
					borderRadius: 2,

					// Alias Token
					colorBgContainer: "#f6ffed",
				},
			}}>
			<div className="full">
				<Router>
					<Routes>
						{role === "1" && (
							<Route path="/admin/*" element={<LayoutAdmin />}>
								<Route index element={<Dashboard />} />
								<Route
									path="dashboard"
									element={<Dashboard />}
								/>
								<Route path="user" element={<User />} />
								<Route path="product" element={<Products />} />
								<Route
									path="category"
									element={<Categories />}
								/>
								<Route path="invoice" element={<Invoices />} />
							</Route>
						)}
						<Route path="/shop/*" element={<LayoutShop />}>
							<Route index element={<HomePage />} />
							<Route path="" element={<HomePage />} />
							<Route path="product">
								<Route path="kit" element={<ShopCategory />} />
								<Route
									path="keycap"
									element={<ShopCategory />}
								/>
								<Route
									path="switch"
									element={<ShopCategory />}
								/>
								<Route
									path="asscessories"
									element={<ShopCategory />}
								/>
							</Route>
						</Route>
						<Route path="" element={<LayoutShop />}>
							<Route index element={<HomePage />} />
							<Route path="" element={<HomePage />} />
							<Route path="liked" element={<Liked />} />
							<Route path="cart" element={<Cart />} />
						</Route>
						<Route path="/auth/*">
							<Route path="login" element={<Login />} />
							<Route path="register" element={<Register />} />
						</Route>

						<Route exact path="/" element={<HomePage />} replace />
					</Routes>
				</Router>
			</div>
		</ConfigProvider>
	);
}

export default App;

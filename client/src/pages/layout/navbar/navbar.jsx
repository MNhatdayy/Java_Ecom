/* eslint-disable no-unused-vars */
import "./navbar.scss";
import { useState } from "react";
import { Button, Input } from "antd";

import { Menu } from "antd";
import { Link } from "react-router-dom";

const { Search } = Input;

const onSearch = (value, _e, info) => console.log(info?.source, value);

const menu = [
	{
		key: "home",
		label: <Link to="/shop">Home</Link>,
	},
	{
		label: "Products",

		children: [
			{
				label: <Link to="/shop/product/kit">Kit</Link>,
				key: "kit",
			},
			{
				label: <Link to="/shop/product/keycap">Keycap</Link>,
				key: "keycap",
			},
		],
	},
	{
		key: "groupbuy",
		label: <Link to="/shop/group-buy">Group Buy</Link>,
	},
	{
		key: "contact",
		label: <Link to="/contact">Contact</Link>,
	},
];

const MenuStyle = {
	backgroundColor: "white",
	fontSize: 15,
	fontWeight: 500,
};

const NavbarComponent = () => {
	const [current, setCurrent] = useState("home");
	const onClick = (e) => {
		console.log("click ", e);
		setCurrent(e.key);
	};
	return (
		<div className="navbar--wrapper">
			<div className="navbar">
				<div className="navbar--logo">
					<i className="fa fa-cloud" aria-hidden="true"></i>
					<span>FloneStore</span>
				</div>
				<div className="navbar--menu">
					<Menu
						style={MenuStyle}
						onClick={onClick}
						selectedKeys={[current]}
						mode="horizontal"
						items={menu}
					/>
				</div>
				<div className="navbar--actions">
					<div className="navbar--search"></div>
					<div className="navbar--auth">
						<Link to={"/auth/login"}>
							<Button type="primary" shape="round">
								Login
							</Button>
						</Link>
					</div>
					<div className="navbar--liked">
						<Link to={"/liked"}>
							<i className="fa fa-heart"></i>
						</Link>
					</div>
					<div className="navbar--cart">
						<Link to={"/cart"}>
							<i className="fa fa-cart-shopping"></i>
						</Link>
					</div>
				</div>
			</div>
		</div>
	);
};

export default NavbarComponent;

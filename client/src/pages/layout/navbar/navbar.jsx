/* eslint-disable no-unused-vars */
import "./navbar.scss";
import { useState, useEffect } from "react";
import { Button, Input } from "antd";

import { Menu } from "antd";
import { Link, useNavigate } from "react-router-dom";

import { logout, parseToken } from "../../../services/AuthController";

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
	const [isLoggedIn, setLogin] = useState(false);
	const [userName, setUsername] = useState("");
	const [current, setCurrent] = useState("home");
	const navigate = useNavigate();
	const onClick = (e) => {
		setCurrent(e.key);
	};

	const logOut = () => {
		logout();
		setLogin(false);
		setUsername("");
		return navigate("/");
	};

	useEffect(() => {
		const tokenInfo = parseToken();
		if (tokenInfo !== null) {
			setLogin(true);
			setUsername(tokenInfo.username);
		}
	}, []);

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
						{isLoggedIn ? (
							<>
								<div className="user--wrapper">
									<p className="username">
										Hi, <span>{userName}</span>
									</p>

									<Button
										type="primary"
										size="small"
										onClick={logOut}
										shape="round">
										<i
											className="fa fa-arrow-circle-right"
											aria-hidden="true"></i>
									</Button>
								</div>
							</>
						) : (
							<Link to={"/auth/login"}>
								<Button type="primary" shape="round">
									Login
								</Button>
							</Link>
						)}
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

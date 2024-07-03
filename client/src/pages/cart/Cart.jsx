
import React, { useEffect, useState } from "react";
import "./cart.scss";
import {
	deleteCart,
	loadCartItems,
	updateCart,
} from "../../services/CartController";
import { Button, InputNumber, Space, Table } from "antd";

const Cart = () => {
	const [cartItems, setCartItems] = useState([]);


	useEffect(() => {
		const fetchCartItems = async () => {
			try {
				const data = await loadCartItems();
				console.log("Fetched cart items:", data);

				setCartItems(data || []);

				setCartItems(
					data.map((ele) => ({
						id: ele.id,
						name: ele.product.name,
						quantity: ele.quantity,
						imageUrl: ele.product.imageUrl,
						price: ele.product.price,
					})) || []
				);
			} catch (error) {
				console.error("Error loading cart items:", error);
			}
		};
		fetchCartItems();
	}, []);

	const totalPrice = cartItems.reduce(
		(price, item) => price + item.quantity * item.product.price, 0
	);

	const handleUpdateQuantity = (cartId, newQuantity) => {
		if (newQuantity === 0) {
			handleDeleteItem(cartId);
		} else {
			updateCart(cartId, newQuantity)
				.then(response => {
					console.log("Cart updated:", response);
					const updatedItems = cartItems.map(item => {
						if (item.id === cartId) {
							return { ...item, quantity: newQuantity };
						}
						return item;
					});
					setCartItems(updatedItems);
				})
				.catch(error => {
					console.error("Error updating cart:", error);
				});
		}
	};

	const handleDeleteItem = (cartId) => {
		deleteCart(cartId)
			.then(response => {
				if (response === 204) {
					console.log("Xóa sản phẩm thành công!!", response);
					const updatedItems = cartItems.filter(item => item.id !== cartId);
					setCartItems(updatedItems);
				} else {
					console.log('Đã hủy');
				}
			})
			.catch(error => {
				console.log('Lỗi khi xóa sản phẩm', error);
			});
	};

	const handleCheckout = () => {
		navigate('/order', { state: { cartItems } });
	};

	return (
		<>
			<div className='full'>
				<div id='container'>
					<Layout style={layoutStyle}>
						<Header style={headerStyle}>
							<HeaderComponent />
						</Header>
						<Content style={contentStyle}>
							<h1 style={h1Style}>Giỏ hàng của bạn</h1>
							<ContainerStyle>
								<RowStyled gutter={[8, 8]}>
									<ColStyled span={4}><TextStyled>Ảnh</TextStyled></ColStyled>
									<ColStyled span={6}><TextStyled>Tên</TextStyled></ColStyled>
									<ColStyled span={4}><TextStyled>Đơn giá</TextStyled></ColStyled>
									<ColStyled span={6}><TextStyled>Số lượng</TextStyled></ColStyled>
									<ColStyled span={4}><TextStyled>Thao tác</TextStyled></ColStyled>
								</RowStyled>
							</ContainerStyle>
							{cartItems.map((item) => {
								return (
									<ContainerStyle key={item.id}>
										<RowStyled gutter={[8, 8]}>
											<ColStyled span={4}>
												<ImageStyled src={`http://localhost:8099/images/${item.product.imageUrl}`} />
											</ColStyled>
											<ColStyled span={6}><TextStyled>{item.product.name}</TextStyled></ColStyled>
											<ColStyled span={4}><TextStyled>{new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(item.product.price)}</TextStyled></ColStyled>
											<ColStyled span={6}>
												<Button type='link' icon={<MinusOutlinedStyled />} onClick={() => handleUpdateQuantity(item.id, item.quantity - 1)} />
												<TextStyled> {item.quantity} </TextStyled>
												<Button type='link' icon={<PlusOutlinedStyled />} onClick={() => handleUpdateQuantity(item.id, item.quantity + 1)} />
											</ColStyled>
											<ColStyled span={4}><Button danger size='large' type='link' onClick={() => handleDeleteItem(item.id)}>Xóa</Button></ColStyled>
										</RowStyled>
									</ContainerStyle>
								);
							})}
							<h2 style={h2Style}>Tổng tiền: {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalPrice)}</h2>
							<Button type="primary" style={{ marginTop: '16px' }} onClick={handleCheckout}>Checkout</Button>
						</Content>
						<Footer style={footerStyle}>
							<FooterComponent />
						</Footer>
					</Layout>
				</div>
			</div>
		</>
		fetchCartItems();
	}, []);

	const handleUpdateQuantity = async (newQuantity, cartId) => {
		if (newQuantity === 0) {
			handleDeleteItem(cartId);
			return;
		}
		try {
			const response = await updateCart(cartId, newQuantity);
			console.log("Cart updated:", response);
			const updatedItems = cartItems.map((item) =>
				item.id === cartId ? { ...item, quantity: newQuantity } : item
			);
			setCartItems(updatedItems);
		} catch (error) {
			console.error("Error updating cart:", error);
		}
	};

	const handleDeleteItem = async (cartId) => {
		try {
			const response = await deleteCart(cartId);
			if (response === 204) {
				console.log("Successfully deleted product:", response);
				const updatedItems = cartItems.filter(
					(item) => item.id !== cartId
				);
				setCartItems(updatedItems);
			} else {
				console.log("Delete action was cancelled");
			}
		} catch (error) {
			console.error("Error deleting product:", error);
		}
	};

	const columns = [
		{ title: "Name", dataIndex: "name", key: "name" },
		{
			title: "Quantity",
			dataIndex: "quantity",
			key: "quantity",
			render: (text, record) => (
				<InputNumber
					min={0}
					value={record.quantity}
					onChange={(value) => handleUpdateQuantity(value, record.id)}
				/>
			),
		},
		{
			title: "Image",
			dataIndex: "imageUrl",
			key: "imageUrl",
			render: (text, record) => (
				<img
					src={`http://localhost:8099${record.imageUrl}`}
					alt={record.name}
					style={{
						width: "100px",
						height: "100px",
						objectFit: "contain",
					}}
				/>
			),
		},
		{ title: "Price", dataIndex: "price", key: "price" },
		{
			title: "Action",
			key: "action",
			render: (text, record) => (
				<Space size="middle">
					<Button onClick={() => handleDeleteItem(record.id)}>
						Delete
					</Button>
				</Space>
			),
		},
	];

	const totalPrice = cartItems.reduce(
		(price, item) => price + item.quantity * item.price,
		0
	);

	return (
		<div className="full">
			<div id="container">
				<div className="cart--wrapper">
					<h3>Your Cart</h3>
					<div className="cart--container">
						<div className="cart--list">
							<Table
								columns={columns}
								dataSource={cartItems}
								rowKey="id"
								pagination={false}
								className="align-items-center-table"
							/>
						</div>
						<div className="cart--total">
							<div className="price">
								<p>Total:</p>
								<p>
									{" "}
									{new Intl.NumberFormat("vi-VN", {
										style: "currency",
										currency: "VND",
									}).format(totalPrice)}
								</p>
							</div>
							<div className="actions">
								<Button type="primary" shape="round" block>
									Check out
								</Button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};
export default Cart;

import './cart.scss';

import { Button, Layout } from 'antd';
import { Row } from 'antd';
import { Col } from 'antd';
import { Image } from "antd";
import { PlusOutlined } from '@ant-design/icons';
import { MinusOutlined } from '@ant-design/icons';
import styled from 'styled-components';
import HeaderComponent from '../layout/HeaderComponent';
import FooterComponent from '../layout/FooterComponent';

import { deleteCart, loadCartItems, updateCart } from '../../services/CartController';
import { useEffect, useState } from 'react';
const { Header, Footer, Content } = Layout;


const headerStyle = {
	color: "#fff",
	height: 70,
	backgroundColor: "white",
};
const contentStyle = {
	minHeight: 100,
	color: "#fff",
	marginTop: 24,
	// backgroundColor: "dark",
};

const footerStyle = {
	color: "#fff",
	backgroundColor: "#4096ff",
};
const layoutStyle = {
	borderRadius: 8,
	width: "100%",
};
const h1Style = {
	fontSize: '25pt',
	marginBottom: '20px'
}
const h2Style = {
	color: 'black',
	fontSize: '22pt',
	marginBottom: '20px'
}
const ButtonStyled = styled(Button)`
	background-color: black;
	margin-bottom: 20px;
	color: white;
	float: right;
	margin-right: 20px;
	
`;
const ContainerStyle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  
`;

const RowStyled = styled(Row)`
    
    margin-bottom: 20px;
    
    display: flex;

    width: 80%;
    
`;
const ColStyled = styled(Col)`
    background-color: #f0f0f0;
    padding: 20px;
    
`;
const TextStyled = styled.span`
  color: black; /* Đổi màu văn bản thành màu đỏ */
  font-size: 20px;
`;
const ImageStyled = styled(Image)`

`
const PlusOutlinedStyled = styled(PlusOutlined)`
    color: black;
    border: 1px solid #ccc;
    font-size: 20px;
    transition: transform 0.2s ease-in-out; /* Hiệu ứng chuyển động */
  
    &:hover {
        transform: scale(1.2); /* Phóng to khi hover */
    }
`
const MinusOutlinedStyled = styled(MinusOutlined)`
    color: black;
    border: 1px solid #ccc;
    font-size: 20px;
    transition: transform 0.2s ease-in-out; /* Hiệu ứng chuyển động */
  
    &:hover {
        transform: scale(1.2); /* Phóng to khi hover */
    }
`
const Cart = () => {
	const [cartItems, setCartItems] = useState([]);
	useEffect(() => {
		const fetchCartItems = async () => {
			try {
				const data = await loadCartItems();
				console.log("Fetched cart items:", data); // Check fetched data
				setCartItems(data || []); // Ensure cartItems is an array
			} catch (error) {
				console.error("Error loading cart items:", error);
			}
		};
	
		fetchCartItems();
	}, []); 
	
    //const [items, setItems] = useState(cartItems);
	
	
	const totalPrice = cartItems.reduce(
		(price, item) => price + item.quantity * item.product.price, 0
	);
	const handleUpdateQuantity = (cartId, newQuantity) => {
        if(newQuantity === 0){
			handleDeleteItem(cartId);
		}
		updateCart(cartId, newQuantity)
            .then(response => {
                console.log("Cart updated:", response);
                // Cập nhật số lượng sản phẩm trong giỏ hàng thành công
                const updatedItems = cartItems.map(item => {
                    if (item.id === cartId) {
                        return { ...item, quantity: newQuantity };
                    }
                    return item;
                });
				
                setCartItems(updatedItems); // Cập nhật giỏ hàng trên giao diện
				
            })
            .catch(error => {
                console.error("Error updating cart:", error);
                // Xử lý lỗi nếu có
            });
    };
	const handleDeleteItem = (cartId) => {
		deleteCart(cartId)
			.then(response => {
				// Xóa sản phẩm khỏi danh sách cartItems sau khi xóa thành công
				if(response === 204){
					console.log("Xóa sản phẩm thành công!!", response);
					const updatedItems = cartItems.filter(item => item.id !== cartId);
					setCartItems(updatedItems); // Cập nhật lại giỏ hàng trên giao diện
				}
				else{
					console.log('Đã hủy');
				}
				
				
				
				
			})
			.catch(error => {
				console.log('Lỗi khi xóa sản phẩm', error);
			})
	}
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
									<ColStyled span={4}>
									<TextStyled>Ảnh</TextStyled>
									</ColStyled>
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
												<ImageStyled src={`http://localhost:8099/images/${item.product.imageUrl}`}></ImageStyled>
											</ColStyled>
											<ColStyled span={6}><TextStyled>{item.product.name}</TextStyled></ColStyled>
											<ColStyled span={4}><TextStyled>{new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(item.product.price)}</TextStyled></ColStyled>
											<ColStyled span={6}>
												<Button type='link' icon={<MinusOutlinedStyled />} onClick={() => handleUpdateQuantity(item.id, item.quantity - 1)} />
													<TextStyled> {item.quantity} </TextStyled>
												<Button type='link' icon={<PlusOutlinedStyled />} onClick={() => handleUpdateQuantity(item.id, item.quantity + 1)} />
												
											</ColStyled>
											<ColStyled span={4}><Button danger size='large' type='link' onClick={() => handleDeleteItem(item.id)} >Xóa</Button></ColStyled>
										</RowStyled>
									</ContainerStyle>
								);
							})}
							<h2 style={h2Style}>Tổng tiền: {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalPrice)}</h2>
							<ButtonStyled>Check Out</ButtonStyled>
						</Content>
						<Footer style={footerStyle}>
							<FooterComponent />
						</Footer>
					</Layout>

				</div>
			</div>
			
		</>
			
		
		
	);
};

export default Cart;

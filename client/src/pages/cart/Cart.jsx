
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
const Cart = ({ cartItems, addToCart, deleteFromCart, checkOut, removeFromCart }) => {
	
	const handleAdd = (item) => {
		addToCart(item);
	  };
	
	  const handleDelete = (item) => {
		deleteFromCart(item);
	  };
	
	  const handleCheckOut = () => {
		checkOut(cartItems);
	  };
	
	  const handleRemove = (item) => {
		removeFromCart(item);
	  };
    console.log(cartItems);
	const totalPrice = cartItems.reduce(
		(price, item) => price + item.quantity * item.product.price, 0
	);
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
												<ImageStyled src='https://deep-image.ai/_next/static/media/app-info-generator.bf08e63e.webp'></ImageStyled>
											</ColStyled>
											<ColStyled span={6}><TextStyled>{item.product.name}</TextStyled></ColStyled>
											<ColStyled span={4}><TextStyled>{item.product.price}</TextStyled></ColStyled>
											<ColStyled span={6}>
												<Button type='link' icon={<MinusOutlinedStyled />} onClick={() => handleDelete(item)}/>
													<TextStyled> {item.quantity} </TextStyled>
												<Button type='link' icon={<PlusOutlinedStyled />} onClick={() => handleAdd(item)}/>
												
											</ColStyled>
											<ColStyled span={4}><Button danger size='large' type='link' onClick={() => handleRemove(item)}>Xóa</Button></ColStyled>
										</RowStyled>
									</ContainerStyle>
								);
							})}
							<h2 style={h2Style}>Tổng tiền: ${totalPrice}.00</h2>
							<Button onClick={() => handleCheckOut(cartItems)}>Check Out</Button>
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

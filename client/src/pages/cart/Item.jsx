
import { Row } from 'antd';
import { Col } from 'antd';
import React from 'react';
export default function Item() {
  const colStyle = {
    backgroundColor: '#f0f0f0', // Đặt màu nền cho cột
    padding: '20px', // Đặt padding cho cột
    border: '1px solid #ddd' // Đặt viền cho cột
  };
  const containerStyle = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    
  };

  const rowWrapperStyle = {
      width: '60%' // Đặt chiều rộng cho Row, bạn có thể điều chỉnh giá trị này
  };
  const rowStyle = {
    height: '150px' // Đặt chiều cao cho Row, bạn có thể điều chỉnh giá trị này
  };
  const imgStyle = {
    width: '100%', // Đặt chiều rộng của ảnh bằng chiều rộng của Col
    height: '100%', // Đặt chiều cao của ảnh bằng chiều cao của Col
    objectFit: 'cover' // Đảm bảo ảnh bao phủ toàn bộ không gian của Col
  };
  return (
      <div style={containerStyle}>
        <div style={rowWrapperStyle}>
          <Row gutter={[8, 8]} style={rowStyle}>
            <Col span={8} style={colStyle}>
              <img src='https://deep-image.ai/_next/static/media/app-info-generator.bf08e63e.webp' style={imgStyle}></img>
            </Col>
            <Col span={16} style={colStyle}></Col>
          </Row>
        </div>
        
        
        
      </div>
      
    )
  }
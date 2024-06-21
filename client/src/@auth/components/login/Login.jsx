import { Link } from "react-router-dom";
import "./login.scss";

import { Button, Checkbox, Form, Input } from "antd";

const login = async (values) => {
	console.log(values.username);
	console.log(values.password);
	console.log(values.remember);
};

const Login = () => {
	return (
		<div className="wrapper">
			<h3>Welcome to my shop</h3>

			<Form
				name="basic"
				labelCol={{
					span: 8,
				}}
				wrapperCol={{
					span: 16,
				}}
				style={{
					maxWidth: 600,
				}}
				initialValues={{
					remember: true,
				}}
				autoComplete="off"
				onFinish={login}>
				<Form.Item
					label="Username"
					name="username"
					rules={[
						{
							required: true,
							message: "Please input your username!",
						},
					]}>
					<Input />
				</Form.Item>

				<Form.Item
					label="Password"
					name="password"
					rules={[
						{
							required: true,
							message: "Please input your password!",
						},
					]}>
					<Input.Password />
				</Form.Item>

				<Form.Item
					name="remember"
					valuePropName="checked"
					wrapperCol={{
						offset: 2,
						span: 16,
					}}>
					<Checkbox>Remember me</Checkbox>
				</Form.Item>

				<div className="button--wrapper">
					<Button
						type="primary"
						htmlType="submit"
						shape="round"
						block>
						Login
					</Button>

					<Link to={"/auth/register"} width="100%">
						<Button htmlType="button" shape="round" block>
							Register
						</Button>
					</Link>
				</div>
				<div className="button--back">
					<Link to={"/shop"} width="100%">
						<Button htmlType="button" shape="round" block>
							Back to shopping
						</Button>
					</Link>
				</div>
			</Form>
		</div>
	);
};

export default Login;

import "./register.scss";

import { Button, Form, Input } from "antd";
import { Link } from "react-router-dom";

const register = async (values) => {
	console.log(values.username);
	console.log(values.password);
};

const Register = () => {
	return (
		<div className="wrapper">
			<h3>Welcome to my shop</h3>
			<p className="sub-title">Register to shopping</p>
			<Form
				name="basic"
				labelCol={{
					span: 10,
				}}
				wrapperCol={{
					span: 16,
				}}
				style={{
					maxWidth: 800,
				}}
				initialValues={{
					remember: true,
				}}
				autoComplete="off"
				onFinish={register}>
				<Form.Item
					label="Username"
					name="username"
					labelAlign="left"
					rules={[
						{
							required: true,
							message: "Please input your username!",
						},
					]}>
					<Input />
				</Form.Item>

				<Form.Item
					label="Email"
					name="email"
					labelAlign="left"
					rules={[
						{
							required: true,
							message: "Please input your email!",
						},
					]}>
					<Input />
				</Form.Item>
				<Form.Item
					label="Phone"
					name="phone"
					labelAlign="left"
					rules={[
						{
							required: true,
							message: "Please input your phone!",
						},
					]}>
					<Input />
				</Form.Item>

				<Form.Item
					label="Password"
					name="password"
					labelAlign="left"
					rules={[
						{
							required: true,
							message: "Please input your password!",
						},
					]}>
					<Input.Password />
				</Form.Item>
				<Form.Item
					label="Confirm Password"
					name="confirmPassword"
					labelAlign="left"
					rules={[
						{
							required: true,
							message: "Please input your password!",
						},
					]}>
					<Input.Password />
				</Form.Item>
				<div className="button--wrapper">
					<Button
						type="primary"
						htmlType="submit"
						shape="round"
						block>
						Register
					</Button>

					<Link to={"/auth/login"} width="100%">
						<Button htmlType="button" shape="round" block>
							Back to login
						</Button>
					</Link>
				</div>
			</Form>
		</div>
	);
};

export default Register;

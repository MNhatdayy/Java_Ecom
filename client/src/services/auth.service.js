import { getAuthToken, request, setAuthToken } from "../util/ApiFunction";

import { jwtDecode } from "jwt-decode";

const login = async function (username, password) {
	const respone = await request("POST", "products/login", {
		username,
		password,
	});
	if (respone.status === 200) {
		setAuthToken(respone.data);
		return respone.data;
	} else {
		return null;
	}
};

const register = async function (username, password) {
	const respone = await request("POST", "products/register", {
		username,
		password,
	});
	if (respone.status === 200) {
		setAuthToken(respone.data);
		return respone.data;
	} else {
		return null;
	}
};

const restorePasswordByMail = async function (email) {
	const respone = await request("POST", "products/restore", {
		email,
	});
	if (respone.status === 200) {
		setAuthToken(respone.data);
		return respone.data;
	} else {
		return null;
	}
};

const parseToken = () => {
	try {
		const token = getAuthToken();
		const decoded = jwtDecode(token);
		const id = decoded.Id;
		const role = decoded.Role;
		const username = decoded.Username;

		return { id, role, username };
	} catch (error) {
		console.error("Invalid token:", error);
		return null;
	}
};

const AuthServices = {
	login,
	register,
	restorePasswordByMail,
	parseToken,
};

export default AuthServices;

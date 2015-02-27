// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.joy.auth;

import jodd.madvoc.meta.Action;

/**
 * Authentication action, usually extended by login action.
 * Defines three actions: login, logout and register.
 * These actions are just dummy 'hooks' so Madvoc can catch them and
 * invoke interceptor. Methods itself will not be invoked.
 * <p>
 * Usually <code>LoginAction</code> extends this class.
 */
public abstract class AuthAction {

	public static final String LOGIN_ACTION_PATH = "/j_login";
	public static final String LOGOUT_ACTION_PATH = "/j_logout";
	public static final String REGISTER_ACTION_PATH = "/j_register";

	public static final String LOGIN_USERNAME = "j_username";
	public static final String LOGIN_PASSWORD = "j_password";
	public static final String LOGIN_TOKEN = "j_token";
	public static final String LOGIN_SUCCESS_PATH = "j_path";

	public static final String ALIAS_INDEX = "<index>";
	public static final String ALIAS_LOGIN = "<login>";
	public static final String ALIAS_LOGIN_NAME = "login";
	public static final String ALIAS_ACCESS_DENIED = "<accessDenied>";
	public static final String ALIAS_ACCESS_DENIED_NAME = "accessDenied";

	/**
	 * Login hook.
	 */
	@Action(value = LOGIN_ACTION_PATH, method = "POST")
	public final void login() {
	}

	/**
	 * Logout hook.
	 */
	@Action(LOGOUT_ACTION_PATH)
	public final void logout() {
	}

	/**
	 * Register hook.
	 */
	@Action(REGISTER_ACTION_PATH)
	public final void register() {
	}

}

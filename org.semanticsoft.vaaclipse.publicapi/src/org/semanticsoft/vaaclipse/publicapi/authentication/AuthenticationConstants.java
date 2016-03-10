package org.semanticsoft.vaaclipse.publicapi.authentication;

/**
 * @author rushan
 *
 */
public class AuthenticationConstants {

	/**
	 * For e4 context. User id as string.
	 */
	public static final String USER_ID = "userId";

	/**
	 * For e4 context. Needs to implement IUser.
	 */
	public static final String USER = "user";

	public static class Events {

		public static class Authentication {
			public static final String name = "vaaclipseAuthentication";
			public static final String userClass = "userClass";
			/**
			 * Is sent before the logout of the current session.
			 */
			public static final String PRE_LOGOUT = "session/prelogout";
			/**
			 * Logs out the current session.
			 */
			public static final String LOGOUT = "session/logout";
		}
	}
}

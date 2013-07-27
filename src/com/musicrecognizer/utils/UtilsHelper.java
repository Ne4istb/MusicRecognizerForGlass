package com.musicrecognizer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;

import android.util.Log;

public class UtilsHelper {

	public static final String KEY_USER_NAME = "user_name";

	/**
	 * Is the debug mode on
	 */
	public static final boolean DEBUG = true;

	/**
	 * Debug tag
	 */
	public static final String LOG_TAG = "FellowTravelers";

	/**
	 * Prints exception if debug is on
	 * 
	 * @param exception
	 */
	public static final void printException(Throwable e) {

		if (DEBUG) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints the line if debug is on
	 * 
	 * @param line
	 */
	public static final void println(String line) {

		if (DEBUG) {
			Log.d(LOG_TAG, line);
		}
	}

	public static void CopyStream(InputStream is, OutputStream os) {

		final int buffer_size = 1024;
		try {

			byte[] bytes = new byte[buffer_size];
			for (;;) {

				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * Convert stream to string
	 * 
	 * @param inputStream
	 *            response stream
	 * @return response string
	 * @throws IOException
	 */
	public static String convertStreamToString(InputStream inputStream)
			throws IOException {

		if (inputStream != null) {

			StringBuilder sb = new StringBuilder();
			String line;
			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"));
				while ((line = reader.readLine()) != null) {

					sb.append(line).append("\n");
				}
			} finally {

				inputStream.close();
			}

			return sb.toString();
		} else {

			return "";
		}
	}

	/**
	 * Encodes string to UTF-8 charset
	 * 
	 * @param string
	 *            to encode
	 * @return encoded string
	 */
	@SuppressWarnings("deprecation")
	public static String encode(String value) {

		return URLEncoder.encode(value);
	}

	/**
	 * 
	 * @param text
	 * @return md5 hashcode
	 */
	public static String MD5(String text) {

		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(text.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}

			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {

			UtilsHelper.printException(e);
		}

		return null;
	}
}

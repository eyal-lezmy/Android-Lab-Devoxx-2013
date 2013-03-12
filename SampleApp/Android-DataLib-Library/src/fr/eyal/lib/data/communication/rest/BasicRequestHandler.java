/*
 * Copyright (C) 2012 Eyal LEZMY (http://www.eyal.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.eyal.lib.data.communication.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Default {@link RequestHandler} used by {@link BasicHttpClient}. It is
 * intended to be used for simple requests with small amounts of data only (a
 * few kB), as it does no buffering, chunking, streaming, etc. Only character
 * set supported is UTF-8. Only {@link String} content is supported. All
 * responses are treated as {@link String}s. This class is abstract so that
 * it can be easily extended in an anonymous inner class when constructing
 * a client.
 * 
 * @author David M. Chandler
 */
public abstract class BasicRequestHandler implements RequestHandler {

	private final RequestLogger logger;

	/**
	 * Constructs a handler with default logger.
	 */
	public BasicRequestHandler() {
		this(new ConsoleRequestLogger());
	}

	/**
	 * Constructs a handler with supplied logger.
	 * 
	 * @param logger
	 */
	public BasicRequestHandler(final RequestLogger logger) {
		this.logger = logger;
	}

	@Override
	public HttpURLConnection openConnection(final String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection uc = (HttpURLConnection) url.openConnection();
		return uc;
	}

	@Override
	public void prepareConnection(final HttpURLConnection urlConnection, final HttpRESTMethod httpMethod, final String contentType) throws IOException {
		// Configure connection for request method
		urlConnection.setRequestMethod(httpMethod.getMethodName());
		urlConnection.setDoOutput(httpMethod.getDoOutput());
		urlConnection.setDoInput(httpMethod.getDoInput());
		if (contentType != null) {
			urlConnection.setRequestProperty("Content-Type", contentType);
		}
		// Set additional properties
		urlConnection.setRequestProperty("Accept-Charset", UTF8);
	}

	@Override
	public OutputStream openOutput(HttpURLConnection urlConnection)
			throws IOException {
		return urlConnection.getOutputStream();
	}

	@Override
	public void writeStream(final OutputStream out, final byte[] content) throws IOException {
		out.write(content);
	}

	@Override
	public InputStream openInput(HttpURLConnection urlConnection)
			throws IOException {
		return urlConnection.getInputStream();
	}

	@Override
	public byte[] readStream(final InputStream in) throws IOException {
		int nRead;
		byte[] data = new byte[16384];
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		while ((nRead = in.read(data)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	@Override
	public boolean onError(final HttpRequestException e) {
		HttpResponse res = e.getHttpResponse();
		if (logger.isLoggingEnabled()) {
			logger.log("BasicRequestHandler.onError got");
			e.printStackTrace();
		}
		if (res != null) {
			int status = res.getStatus();
			if (status > 0) {
				// Perhaps a 404, 501, or something that will be fixed later
				return true;
			}
		}
		// Connection refused, host unreachable, etc.
		return false;
	}

}

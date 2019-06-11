/*
 * Copyright 2019 InterlockLedger Network
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.interlockledger.iltags.io;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This filtered input stream creates a view of the underlying input stream by
 * imposing a limit to the maximum number of bytes in the view.
 *  
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class SubInputStream extends FilterInputStream {
	
	private long size;
	
	private boolean closeOnClose;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param in The underlying input stream.
	 * @param size The size of the view.
	 * @param closeOnClose Flag that controls the behavior of close() method. If
	 * true the close on the underlying input stream will be called, otherwise 
	 * close() will left the underlying input stream open. 
	 */
	public SubInputStream(InputStream in, long size, boolean closeOnClose) {
		super(in);
		this.closeOnClose = closeOnClose;
		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be negative.");
		}
		this.size = size;
	}
	
	public long remaining() {
		return this.size;
	}
	
	@Override
	public int read() throws IOException {
		
		if (this.size > 0) {
			int r = super.read();
			if(r < 0) {
				throw new EOFException("Unexpected end of the underlying stream.");
			}
			this.size--;
			return r;
		} else {
			return -1;
		}
	}

	@Override
	public int read(byte[] b) throws IOException {
		return this.read(b, 0, b.length);
	}
	
	@Override
	public int available() throws IOException {
		return (int)Math.min(super.available(), this.size);
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		
		if (this.size > 0) {
			len = (int)Math.min(len, this.size);
			int read = super.read(b, off, len); 
			if (read < 0) {
				throw new EOFException("Unexpected end of the underlying stream.");
			} else {
				this.size -= read;
				return read;
			}
		} else {
			return -1;
		}
	}

	@Override
	public long skip(long n) throws IOException {
		n = Math.min(n, this.size);
		long skipped = super.skip(n);
		this.size -= skipped;
		return skipped;
	}
	
	/**
	 * Seek the end of the sub stream.
	 *  
	 * @return true if the end of the stream was reached or false otherwise.
	 * @throws IOException In case of error.
	 */
	public void end() throws IOException {
		
		while (this.size > 0) {
			if (this.skip(this.size) == 0) {
				this.read(); // Force a single byte skip to check the end of file.
			}
		}
	}
	
	@Override
	public void close() throws IOException {

		if (this.closeOnClose) {
			super.close();
		}
	}

	@Override
	public synchronized void mark(int readlimit) {
	}

	@Override
	public synchronized void reset() throws IOException {
		throw new IOException("Mark not supported.");
	}
	
	@Override
	public boolean markSupported() {
		return false;
	}

	/**
	 * Checks if close() will close the filtered stream. 
	 * 
	 * @return true if the filtered stream will be closed or false otherwise.
	 */
	public boolean isCloseOnClose() {
		return closeOnClose;
	}
}

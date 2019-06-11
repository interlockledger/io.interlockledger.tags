package io.interlockledger.iltags.io;

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
			this.size--;
			return super.read();
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
		return (int)Math.min(this.available(), this.size);
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		
		len = (int)Math.min(len, this.size);
		int read = super.read(b, off, len); 
		this.size -= read;
		return read;
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
	 * @throws IOException In case of error.
	 */
	public void end() throws IOException {
		long count = this.size;
		while (this.size > 0) {
			if (count < 0) {
				throw new IOException("Unable to reach the end of the stream.");
			}
			this.skip(this.size);
			count--;
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

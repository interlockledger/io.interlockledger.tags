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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class implements a test OutputStream.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.16
 */
public class TestOutputStream extends FilterOutputStream {

	private boolean closeUsed;
	
	private boolean forError;
	
	private boolean flushUsed;
	
	public TestOutputStream(OutputStream out) {
		super(out);
	}

	@Override
	public void write(byte[] b) throws IOException {
		if (this.forError) {
			throw new IOException();
		}
		super.write(b);
	}
	
	@Override
	public void write(int b) throws IOException {
		if (this.forError) {
			throw new IOException();
		}
		super.write(b);
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if (this.forError) {
			throw new IOException();
		}
		super.write(b, off, len);
	}
	
	@Override
	public void flush() throws IOException {
		if (this.forError) {
			throw new IOException();
		}
		this.flushUsed = true;
		super.flush();
	}

	@Override
	public void close() throws IOException {
		this.closeUsed = true;
		super.close();
	}
	
	public void setForError(boolean forError) {
		this.forError = forError;
	}

	public boolean isCloseUsed() {
		return closeUsed;
	}

	public boolean isFlushUsed() {
		return flushUsed;
	}
}

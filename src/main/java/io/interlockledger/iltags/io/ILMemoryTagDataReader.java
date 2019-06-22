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

import java.nio.ByteBuffer;

import io.interlockledger.iltags.ILTagException;

/**
 * This class implements the ILTagDataReader for memory.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.18
 */
public class ILMemoryTagDataReader extends ILBaseTagDataReader {

	private ByteBuffer buffer;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param buff The input buffer. It will share the same underlying
	 * bytes but the position and limits will be independent.
	 */
	public ILMemoryTagDataReader(ByteBuffer buff) {
		this.buffer = buff.duplicate();
	}
	
	/**
	 * Creates a new instance of this class. 
	 * 
	 * @param data The input buffer.
	 */
	public ILMemoryTagDataReader(byte [] data) {
		this(data, 0, data.length);
	}
	
	/**
	 * Creates a new instance of this class. 
	 * 
	 * @param data The input buffer.
	 * @param offs The initial offset.
	 * @param size The number of bytes.
	 */
	public ILMemoryTagDataReader(byte [] data, int offs, int size) {
		this.buffer = ByteBuffer.wrap(data, offs, size);		
	}

	@Override
	protected byte readByteCore() throws ILTagException {
		
		if (this.buffer.hasRemaining()) {
			return this.buffer.get();
		} else {
			throw new ILTagNotEnoughDataException("No more bytes to read.");
		}
	}

	@Override
	protected void readBytesCore(byte[] v, int off, int size) throws ILTagException {

		if (size <= this.buffer.remaining()) {
			this.buffer.get(v, off, size);
		} else {
			throw new ILTagNotEnoughDataException("No more bytes to read.");
		}
	}

	@Override
	protected void skipCore(long n) throws ILTagException {
		
		if (n < 0) {
			throw new IllegalArgumentException("Invalid n.");
		}
		
		long maxOffset = (long)this.buffer.position() + this.buffer.remaining();
		long newOffset = this.buffer.position() + n;
		if (newOffset > maxOffset) {
			throw new ILTagNotEnoughDataException("Not enough bytes to skip.");
		}
		this.buffer.position((int)(this.buffer.position() + n));
	}
}

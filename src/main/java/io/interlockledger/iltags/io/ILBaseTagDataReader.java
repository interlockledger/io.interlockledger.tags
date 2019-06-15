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
import java.nio.ByteOrder;
import java.util.Stack;

import io.interlockledger.iltags.ILTagException;
import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.ilint.ILIntException;

/**
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public abstract class ILBaseTagDataReader implements ILTagDataReader {

	private final ByteBuffer tmp;
	
	private final Stack<Long> limits = new Stack<Long>();
	
	private long remaining = -1;
	
	protected ILBaseTagDataReader() {
		tmp = ByteBuffer.allocate(8);
		tmp.order(ByteOrder.BIG_ENDIAN);		
	}
	
	protected void consumeBytes(long n) throws ILTagNotEnoughDataException {
	
		if (this.isLimited()) {
			if (n > this.remaining) {
				throw new ILTagNotEnoughDataException("Unexpected end of data.");
			}
			this.remaining -= n;
		}
	}

	@Override
	public byte readByte() throws ILTagException {
		consumeBytes(1);
		return this.readByteCore();
	}
	
	protected abstract byte readByteCore() throws ILTagException;

	@Override
	public short readShort() throws ILTagException {
		readBytes(tmp, 2);
		return tmp.getShort();
	}

	@Override
	public int readInt() throws ILTagException {
		readBytes(tmp, 4);
		return tmp.getInt();
	}

	@Override
	public long readLong() throws ILTagException {
		readBytes(tmp, 8);
		return tmp.getLong();

	}

	@Override
	public float readFloat() throws ILTagException {
		readBytes(tmp, 4);
		return tmp.getFloat();

	}

	@Override
	public double reatDouble() throws ILTagException {
		readBytes(tmp, 8);
		return tmp.getDouble();
	}

	@Override
	public void readBytes(byte[] v) throws ILTagException {
		readBytes(v, 0, v.length);
	}

	private void readBytes(ByteBuffer tmp, int size) throws ILTagException {
		readBytes(tmp.array(), 0, size);
		tmp.rewind();
	}

	
	@Override
	public void readBytes(byte[] v, int off, int size) throws ILTagException {
		consumeBytes(size);
		readBytesCore(v, off, size);
	}
	
	protected abstract void readBytesCore(byte[] v, int off, int size) throws ILTagException;
	
	@Override
	public long readILInt() throws ILTagException {
		try {
			return ILIntCodec.decode(this, ILTagDataReaderHandler.INSTANCE);
		} catch(ILIntException e) {
			throw new ILTagException(e);
		}
	}

	@Override
	public void skip(long n) throws ILTagException {
		consumeBytes(n);
		skipCore(n);
	}
	
	protected abstract void skipCore(long n) throws ILTagException;
	
	public void pushLimit(long newLimit) {
		
		if (this.isLimited()) {
			if (newLimit > this.remaining) {
				throw new IllegalArgumentException("");
			}
		}
		this.limits.push(this.remaining);
		this.remaining = newLimit;
	}

	public void popLimit(boolean checkRemaining) throws ILTagException {
		
		if (this.limits.size() == 0) {
			throw new IllegalStateException("No limits to pop.");
		}
		if ((checkRemaining) && (this.getRemaining() > 0)) {
			throw new ILTagTooMuchDataException(String.format("Unexpected %1$d bytes in the stream.", this.getRemaining()));
		}
		this.remaining = this.limits.pop();
	}
	
	public long getRemaining() {
		return this.remaining;
	}
	
	public boolean isLimited() {
		return remaining >= 0;
	}
}

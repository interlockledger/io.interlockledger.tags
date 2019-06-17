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
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import io.interlockledger.iltags.ILTagException;
import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.ilint.ILIntException;

/**
 * Base class for ILTagDataWriter implementations. It provides
 * the implementation for most of the methods of the interface, simplifying
 * the implementation of the subclasses.
 *
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public abstract class ILBaseTagDataWriter implements ILTagDataWriter {

	protected static Charset UTF8 = Charset.forName("utf-8");
	
	private final ByteBuffer tmp;
	
	private long offset = 0;
	
	/**
	 * Creates a new instance of this class.
	 */
	protected ILBaseTagDataWriter() {
		tmp = ByteBuffer.allocate(9);
		tmp.order(ByteOrder.BIG_ENDIAN);
	}
	
	/**
	 * Updates the current offset.
	 * 
	 * @param size The number of bytes to add.
	 */
	protected void updateOffset(int size) {
		this.offset += size;
	}
	
	@Override
	public void writeByte(byte v) throws ILTagException {
		writeByteCore(v);
		updateOffset(1);
	}
	
	/**
	 * Writes a byte. This method is called by writeByte(byte) before
	 * the update of the offset.
	 * 
	 * @param v The value to be written.
	 * @throws ILTagException In case of error.
	 */
	protected abstract void writeByteCore(byte v) throws ILTagException;
	
	@Override
	public void writeShort(short v) throws ILTagException {
		tmp.rewind();
		tmp.putShort(v);
		this.writeBytes(tmp);
	}

	@Override
	public void writeInt(int v) throws ILTagException {
		tmp.rewind();
		tmp.putInt(v);
		this.writeBytes(tmp);
	}

	@Override
	public void writeLong(long v) throws ILTagException {
		tmp.rewind();
		tmp.putLong(v);
		this.writeBytes(tmp);
	}

	@Override
	public void writeFloat(float v) throws ILTagException {
		tmp.rewind();
		tmp.putFloat(v);
		this.writeBytes(tmp);

	}

	@Override
	public void writeDouble(double v) throws ILTagException {
		tmp.rewind();
		tmp.putDouble(v);
		this.writeBytes(tmp);
	}

	@Override
	public void writeBytes(byte[] v) throws ILTagException {
		this.writeBytes(v, 0, v.length);
	}

	private void writeBytes(ByteBuffer tmp) throws ILTagException {
		this.writeBytes(tmp.array(), 0, tmp.position());
	}
	
	@Override
	public void writeBytes(byte[] v, int off, int size) throws ILTagException {
		this.writeBytesCore(v, off, size);
		this.updateOffset(size);
	}

	/**
	 * Writes bytes. This method is called by writeBytes(byte,int,int) before
	 * the update of the offset.
	 * 
	 * @param v The value to be written.
	 * @param off The offset.
	 * @param size The size.
	 * @throws ILTagException In case of error.
	 */
	protected abstract void writeBytesCore(byte[] v, int off, int size) throws ILTagException;
	
	@Override
	public void writeILInt(long v) throws ILTagException {
		
		tmp.rewind();
		try {
			ILIntCodec.encode(v, tmp);
			this.writeBytes(tmp);
		} catch (ILIntException e) {
			throw new ILTagException(e.getMessage(), e);
		}
	}

	@Override
	public long getOffset() {
		return offset;
	}
	
	@Override
	public void writeString(CharSequence v) throws ILTagException {
		ByteBuffer out = UTF8.encode(CharBuffer.wrap(v));
		this.writeBytes(out.array(), 0, out.limit());
	}
}

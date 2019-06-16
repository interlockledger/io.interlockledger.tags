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

import io.interlockledger.iltags.ILTagException;
import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.ilint.ILIntException;

/**
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public abstract class ILBaseTagDataWriter implements ILTagDataWriter {

	private final ByteBuffer tmp;
	
	private long offset = 0;
	
	protected ILBaseTagDataWriter() {
		tmp = ByteBuffer.allocate(8);
		tmp.order(ByteOrder.BIG_ENDIAN);
	}
	
	protected void updateOffset(int size) {
		this.offset += size;
	}
	
	@Override
	public void writeByte(byte v) throws ILTagException {
		updateOffset(1);
		writeByteCore(v);
	}
	
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
		this.updateOffset(size);
		this.writeBytesCore(v, off, size);
	}

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
}

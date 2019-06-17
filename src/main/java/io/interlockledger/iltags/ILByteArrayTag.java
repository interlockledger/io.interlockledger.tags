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
package io.interlockledger.iltags;

import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard byte array tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.12
 */
public class ILByteArrayTag extends ILTag {
	
	private byte [] value;

	public ILByteArrayTag(long id) {
		super(id);
	}

	public ILByteArrayTag() {
		this(ILStandardTags.TAG_BYTE_ARRAY);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		
		if (value != null) {
			out.writeBytes(this.value);
		} else {
			throw new IllegalStateException("Value not set.");
		}
	}

	@Override
	public long getValueSize() {

		if (value != null) {
			return this.value.length;
		} else {
			throw new IllegalStateException("Value not set.");
		}
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in)
			throws ILTagException {
		if ((tagSize < 0) || (tagSize > Integer.MAX_VALUE)) {
			throw new ILTagException("Invalid byte array size.");
		}
		int size = (int)tagSize;
		this.value = new byte[size];
		in.readBytes(this.value);
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
}

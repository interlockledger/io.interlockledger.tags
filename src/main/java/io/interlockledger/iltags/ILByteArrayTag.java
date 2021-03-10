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

import java.util.Arrays;

import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard byte array tag but can also be used to
 * implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.12
 */
public class ILByteArrayTag extends ILTag {

	private byte[] value;

	public ILByteArrayTag() {
		this(ILStandardTags.TAG_BYTE_ARRAY.ordinal());
	}

	public ILByteArrayTag(long id) {
		super(id);
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in) throws ILTagException {
		this.value = readRawBytes(tagSize, in);
	}

	public byte[] getValue() {
		return value;
	}

	@Override
	protected int getValueHashCode() {
		if (this.getValue() != null) {
			return Arrays.hashCode(this.getValue());
		} else {
			return 0;
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
	protected boolean sameValue(ILTag other) {
		ILByteArrayTag t = (ILByteArrayTag) other;
		return Arrays.equals(this.getValue(), t.getValue());
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {

		if (value != null) {
			out.writeBytes(this.value);
		} else {
			throw new IllegalStateException("Value not set.");
		}
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
}

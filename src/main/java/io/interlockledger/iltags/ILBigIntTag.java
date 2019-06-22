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

import java.math.BigInteger;

import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard big integer tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.17
 */
public class ILBigIntTag extends ILTag {

	private BigInteger value;
	
	public ILBigIntTag() {
		this(ILStandardTags.TAG_BINT.ordinal());
	}
	
	public ILBigIntTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		if (this.value == null) {
			throw new IllegalStateException("Value not set.");
		}
		out.writeBytes(this.value.toByteArray());
	}

	@Override
	public long getValueSize() {
		if (this.value == null) {
			throw new IllegalStateException("Value not set.");	
		}
		return getBigIntegerSize(this.value);
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in) throws ILTagException {
		this.value = new BigInteger(this.readRawBytes(tagSize, in));
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}
	
	/**
	 * Returns the size of a BigInteger in bytes.
	 * 
	 * @param value The value.
	 * @return The size of the BigInteger in bytes when represented as
	 * a complement of 2.
	 * @since 2019.06.18
     */
	public static int getBigIntegerSize(BigInteger value) {

		return (value.bitLength() + 8) / 8;
	}
	
	
	public static byte [] serializeBigInteger(BigInteger value) {
		return value.toByteArray();
	}
	
	@Override
	protected boolean sameValue(ILTag other) {
		ILBigIntTag t = (ILBigIntTag)other;
		return this.getValue().equals(t.getValue());
	}
	
	@Override
	protected int getValueHashCode() {
		if (this.getValue() != null) {
			return this.getValue().hashCode();
		} else {
			return 0;
		}
	}
}

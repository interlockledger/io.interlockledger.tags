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

import java.math.BigDecimal;
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
public class ILBigDecimalTag extends ILTag {

	private BigDecimal value;
	
	public ILBigDecimalTag() {
		this(ILStandardTags.TAG_BDEC);
	}
	
	public ILBigDecimalTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		if (this.value == null) {
			throw new IllegalStateException("Value not set.");
		}
		out.writeInt(this.value.scale());
		out.writeBytes(ILBigIntTag.serializeBigInteger(this.value.unscaledValue()));
	}

	@Override
	public long getValueSize() {
		if (this.value == null) {
			throw new IllegalStateException("Value not set.");	
		}
		return 4 + ILBigIntTag.getBigIntegerSize(this.value.unscaledValue());
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in) throws ILTagException {
		int scale = in.readInt();
		byte [] unscaled = readRawBytes(tagSize - 4, in);
		this.value = new BigDecimal(new BigInteger(unscaled), scale);	
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	@Override
	protected boolean sameValue(ILTag other) {
		ILBigDecimalTag t = (ILBigDecimalTag)other;
		return this.getValue().equals(t.getValue());
	}
}

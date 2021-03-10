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
 * This class implements the standard float 64 array tag but can also be used to
 * implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILBinary64Tag extends ILFixedSizeTag {

	private double value;

	public ILBinary64Tag() {
		this(ILStandardTags.TAG_BINARY64.ordinal());
	}

	public ILBinary64Tag(long id) {
		super(id, 8);
	}

	@Override
	protected void deserializeValueCore(ILTagFactory factory, ILTagDataReader in) throws ILTagException {
		this.value = in.readDouble();
	}

	public double getValue() {
		return value;
	}

	@Override
	protected int getValueHashCode() {
		return (int) Double.doubleToLongBits(this.getValue());
	}

	@Override
	protected boolean sameValue(ILTag other) {
		ILBinary64Tag t = (ILBinary64Tag) other;
		return this.getValue() == t.getValue();
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		out.writeDouble(this.value);
	}

	public void setValue(double value) {
		this.value = value;
	}
}

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
 * This class implements the standard float 32 array tag but can also be used to
 * implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILBinary32Tag extends ILFixedSizeTag {

	private float value;

	public ILBinary32Tag() {
		this(ILStandardTags.TAG_BINARY32.ordinal());
	}

	public ILBinary32Tag(long id) {
		super(id, 4);
	}

	@Override
	protected void deserializeValueCore(ILTagFactory factory, ILTagDataReader in) throws ILTagException {
		this.value = in.readFloat();
	}

	public float getValue() {
		return value;
	}

	@Override
	protected int getValueHashCode() {
		return Float.floatToIntBits(this.getValue());
	}

	@Override
	protected boolean sameValue(ILTag other) {
		ILBinary32Tag t = (ILBinary32Tag) other;
		return this.getValue() == t.getValue();
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		out.writeFloat(this.value);
	}

	public void setValue(float value) {
		this.value = value;
	}
}

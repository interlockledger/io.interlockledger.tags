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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class implements the standard float 64 array tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILBinary64Tag extends ILFixedSizeTag {

	private double value;

	public ILBinary64Tag() {
		this(ILTagStandardTags.TAG_BINARY32);
	}
	
	public ILBinary64Tag(long id) {
		super(id, 4);
	}

	@Override
	protected void deserializeValueCore(ILTagFactory factory, DataInputStream in) throws ILTagException, IOException {
		this.value = in.readDouble();
	}

	@Override
	protected void serializeValue(DataOutputStream out) throws ILTagException, IOException {
		out.writeDouble(this.value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}

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
 * This class implements the standard float 128 array tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILBinary128Tag extends ILFixedSizeTag {

	private byte [] value;

	public ILBinary128Tag() {
		this(ILTagStandardTags.TAG_BINARY32);
	}
	
	public ILBinary128Tag(long id) {
		super(id, 16);
		this.value = new byte[16]; 
	}

	@Override
	protected void deserializeValueCore(ILTagFactory factory, DataInputStream in) throws ILTagException, IOException {
		in.readFully(this.value);
	}

	@Override
	protected void serializeValue(DataOutputStream out) throws ILTagException, IOException {
		out.write(this.value);
	}

	public byte [] getValue() {
		return value;
	}

	public void setValue(byte [] value) {
		this.value = value;
	}
}
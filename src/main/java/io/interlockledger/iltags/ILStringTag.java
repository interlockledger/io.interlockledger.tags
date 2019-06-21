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
import io.interlockledger.iltags.io.UTF8Utils;

/**
 * This class implements the String ILTag.
 *
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class ILStringTag extends ILTag {

	public String value;
	
	public ILStringTag() {
		this(ILStandardTags.TAG_STRING);
	}
	
	public ILStringTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {

		if (this.value == null) {
			throw new IllegalStateException("Value not set.");	
		}
		out.writeString(this.value);
	}

	@Override
	public long getValueSize() {
		if (this.value == null) {
			throw new IllegalStateException("Value not set.");	
		}
		return UTF8Utils.getEncodedSize(this.value); 
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in) throws ILTagException {
		this.value = in.readString(tagSize);	
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	protected boolean sameValue(ILTag other) {
		ILStringTag t = (ILStringTag)other;
		
		if (this.getValue() == t.getValue()) {
			return true;
		}
		if (this.getValue() == null) {
			return false;
		}
		return this.getValue().equals(t.getValue());
	}
}

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard tag array tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILTagSequenceTag extends ILTag {

	private ArrayList<ILTag> value = new ArrayList<ILTag>();

	public ILTagSequenceTag() {
		this(ILStandardTags.TAG_ILTAG_SEQ);
	}
	
	public ILTagSequenceTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {

		for (ILTag t: this.value) {
			t.serialize(out);
		}
	}

	@Override
	public long getValueSize() {
		long size;
		
		size = 0;
		for (ILTag t: this.value) {
			size += t.getTagSize();
		}
		return size;
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in)
			throws ILTagException {
		this.value.clear();
		while(in.getRemaining() > 0) {
			this.value.add(factory.deserialize(in));
		}		
	}

	public List<ILTag> getValue() {
		return this.value;
	}
	
	public void setValue(Collection<ILTag> value) {
		this.value.clear();
		this.value.addAll(value);
	}
}

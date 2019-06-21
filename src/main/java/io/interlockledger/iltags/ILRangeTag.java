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


import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard range tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.18
 */
public class ILRangeTag extends ILTag {
	
	private long start;
	
	private int range;

	public ILRangeTag() {
		this(ILStandardTags.TAG_RANGE);
	}

	public ILRangeTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		out.writeILInt(this.getStart());
		out.writeShort((short)this.getRange());
	}

	@Override
	public long getValueSize() {
		return ILIntCodec.getEncodedSize(this.getStart()) + 2;
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in) throws ILTagException {
		this.start = in.readILInt();
		this.range = in.readShort() & 0xFFFF;		
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		if ((range < 0) || (range > 0xFFFF)) {
			throw new IllegalArgumentException("The range must be avalue between 0 and 65535.");
		}
		this.range = range;
	}
	
	public long getEnd() {
		return this.start + this.range;
	}
	
	@Override
	protected boolean sameValue(ILTag other) {
		ILRangeTag t = (ILRangeTag)other;
		return (this.getStart() == t.getStart()) &&
				(this.getRange() == t.getRange());
	}
}

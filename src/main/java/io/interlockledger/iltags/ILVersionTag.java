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
 * This class implements the standard version tag but can also be used to
 * implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.18
 */
public class ILVersionTag extends ILFixedSizeTag {

	private int major;
	private int minor;
	private int revision;
	private int build;

	public ILVersionTag() {
		this(ILStandardTags.TAG_VERSION.ordinal());
	}

	public ILVersionTag(long id) {
		super(id, 4 * 4);
	}

	@Override
	protected void deserializeValueCore(ILTagFactory factory, ILTagDataReader in) throws ILTagException {
		this.major = in.readInt();
		this.minor = in.readInt();
		this.revision = in.readInt();
		this.build = in.readInt();
	}

	public int getBuild() {
		return build;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getRevision() {
		return revision;
	}

	@Override
	protected int getValueHashCode() {
		return (this.getMajor() << 24) + (this.getMinor() << 16) + (this.getRevision() << 8) + this.getBuild();
	}

	@Override
	protected boolean sameValue(ILTag other) {
		ILVersionTag t = (ILVersionTag) other;
		return (this.getMajor() == t.getMajor()) && (this.getMinor() == t.getMinor())
				&& (this.getRevision() == t.getRevision()) && (this.getBuild() == t.getBuild());
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		out.writeInt(this.major);
		out.writeInt(this.minor);
		out.writeInt(this.revision);
		out.writeInt(this.build);
	}

	public void setBuild(int build) {
		this.build = build;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public void setValue(int major, int minor, int revision, int build) {
		this.major = major;
		this.minor = minor;
		this.revision = revision;
		this.build = build;
	}
}

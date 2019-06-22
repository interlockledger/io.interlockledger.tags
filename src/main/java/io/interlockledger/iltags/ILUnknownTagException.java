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

/**
 * This exception is used to report an unknown tag.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.17
 */
public class ILUnknownTagException extends ILTagException {

	private static final long serialVersionUID = 1L;
	
	private final long tagId;

	public ILUnknownTagException(long tagId) {
		super(String.format("Unknown tag %1$X.", tagId));
		this.tagId = tagId;
	}

	public long getTagId() {
		return tagId;
	}	
}

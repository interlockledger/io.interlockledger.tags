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
 * This class implements the standard OID tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.18
 */
public class ILOIDTag extends ILILIntArrayTag {

	public ILOIDTag() {
		this(ILStandardTags.TAG_OID);
	}

	public ILOIDTag(long id) {
		super(id);
	}
}

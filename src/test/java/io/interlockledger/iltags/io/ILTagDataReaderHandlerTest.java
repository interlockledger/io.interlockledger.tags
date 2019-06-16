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
package io.interlockledger.iltags.io;

import static org.junit.Assert.*;

import org.junit.Test;

import io.interlockledger.iltags.ilint.ILIntException;

public class ILTagDataReaderHandlerTest {
	
	@Test
	public void testInstance() {
		assertNotNull(ILTagDataReaderHandler.INSTANCE);
	}

	@Test
	public void testGet() throws Exception {
		ILBaseTagDataReaderTest.TestTagDataReader r = new ILBaseTagDataReaderTest.TestTagDataReader();
		
		for (int i = 0; i < 256; i++) {
			assertEquals((i + 1) &0xFF, ILTagDataReaderHandler.INSTANCE.get(r));
		}
	}

	@Test(expected = ILIntException.class)
	public void testGetFail() throws Exception {
		ILBaseTagDataReaderTest.TestTagDataReader r = new ILBaseTagDataReaderTest.TestTagDataReader();
		
		r.pushLimit(0);
		ILTagDataReaderHandler.INSTANCE.get(r);
	}
}

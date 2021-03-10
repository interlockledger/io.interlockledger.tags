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
package io.interlockledger.iltags.ilint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class OutputStreamHandlerTest {

	@Test
	public void testInstance() {

		assertNotNull(OutputStreamHandler.INSTANCE);
	}

	@Test
	public void testWrite() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		for (int i = 0; i < 256; i++) {
			OutputStreamHandler.INSTANCE.write(i, out);
		}

		byte[] bin = out.toByteArray();
		assertEquals(256, bin.length);
		for (int i = 0; i < 256; i++) {
			assertEquals(i, bin[i] & 0xFF);
		}

	}

}

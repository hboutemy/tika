/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.parser.multiple;

import java.util.Arrays;
import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;

/**
 * Tries multiple parsers in turn, until one succeeds.
 * 
 * Can optionally keep Metadata from failed parsers when
 *  trying the next one, depending on the {@link MetadataPolicy}
 *  chosen.
 *
 * @since Apache Tika 1.18
 */
public class FallbackParser extends AbstractMultipleParser {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5844409020977206167L;

    /**
     * The different Metadata Policies we support (all)
     */
    public static final List<MetadataPolicy> allowedPolicies =
            Arrays.asList(MetadataPolicy.values());


    public FallbackParser(MediaTypeRegistry registry, MetadataPolicy policy,
                           List<Parser> parsers) {
        super(registry, policy, parsers);
    }
    public FallbackParser(MediaTypeRegistry registry, MetadataPolicy policy,
                          Parser... parsers) {
        super(registry, policy, parsers);
    }

    @Override
    protected boolean parserCompleted(Parser parser, Metadata metadata,
            ContentHandler handler, Exception exception) {
        // If there was no exception, abort further parsers
        if (exception == null) return false;
        
        // Record the details of this exception in the metadata
        // TODO Share logic with the Recursive Parser Wrapper
        
        // Have the next parser tried
        return true;
    }
}


/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//collections/src/test/org/apache/commons/collections/primitives/adapters/Attic/TestFloatListIteratorListIterator.java,v 1.2 2003/04/13 23:26:09 rwaldhoff Exp $
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.commons.collections.primitives.adapters;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.collections.iterators.TestListIterator;
import org.apache.commons.collections.primitives.ArrayFloatList;
import org.apache.commons.collections.primitives.FloatList;

/**
 * @version $Revision: 1.2 $ $Date: 2003/04/13 23:26:09 $
 * @author Rodney Waldhoff
 */
public class TestFloatListIteratorListIterator extends TestListIterator {

    // conventional
    // ------------------------------------------------------------------------

    public TestFloatListIteratorListIterator(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestFloatListIteratorListIterator.class);
    }

    // collections testing framework
    // ------------------------------------------------------------------------

    public Object makeObject() {
        return makeFullIterator();
    }
    
    public ListIterator makeEmptyListIterator() {
        return FloatListIteratorListIterator.wrap(makeEmptyFloatList().listIterator());
    }
    
    public ListIterator makeFullListIterator() {
        return FloatListIteratorListIterator.wrap(makeFullFloatList().listIterator());
    }

    protected FloatList makeEmptyFloatList() {
        return new ArrayFloatList();
    }
    
    protected FloatList makeFullFloatList() {
        FloatList list = makeEmptyFloatList();
        float[] elts = getFullElements();
        for(int i=0;i<elts.length;i++) {
            list.add((float)elts[i]);
        }
        return list;
    }
    
    public float[] getFullElements() {
        return new float[] { (float)0, (float)1, (float)2, (float)3, (float)4, (float)5, (float)6, (float)7, (float)8, (float)9 };
    }
    
    // tests
    // ------------------------------------------------------------------------

    
    public void testNextHasNextRemove() {
        float[] elements = getFullElements();
        Iterator iter = makeFullIterator();
        for(int i=0;i<elements.length;i++) {
            assertTrue(iter.hasNext());
            assertEquals(new Float(elements[i]),iter.next());
            if(supportsRemove()) {
                iter.remove();
            }
        }        
        assertTrue(! iter.hasNext() );
    }

    public void testEmptyIterator() {
        assertTrue( ! makeEmptyIterator().hasNext() );
        try {
            makeEmptyIterator().next();
            fail("Expected NoSuchElementException");
        } catch(NoSuchElementException e) {
            // expected
        }
        if(supportsRemove()) {
            try {
                makeEmptyIterator().remove();
                fail("Expected IllegalStateException");
            } catch(IllegalStateException e) {
                // expected
            }
        }        
    }

    public void testRemoveBeforeNext() {
        if(supportsRemove()) {
            try {
                makeFullIterator().remove();
                fail("Expected IllegalStateException");
            } catch(IllegalStateException e) {
                // expected
            }
        }        
    }

    public void testRemoveAfterRemove() {
        if(supportsRemove()) {
            Iterator iter = makeFullIterator();
            iter.next();
            iter.remove();
            try {
                iter.remove();
                fail("Expected IllegalStateException");
            } catch(IllegalStateException e) {
                // expected
            }
        }        
    }

}

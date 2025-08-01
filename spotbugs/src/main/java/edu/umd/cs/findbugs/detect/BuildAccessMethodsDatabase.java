/*
 * SpotBugs - Find Bugs in Java programs
 * Copyright (C) 2025, Guillaume Toison
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package edu.umd.cs.findbugs.detect;

import org.apache.bcel.Const;

import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.NonReportingDetector;
import edu.umd.cs.findbugs.SourceLineAnnotation;
import edu.umd.cs.findbugs.ba.AccessMethodDatabase;
import edu.umd.cs.findbugs.classfile.Global;
import edu.umd.cs.findbugs.classfile.MethodDescriptor;

/**
 *
 */
public class BuildAccessMethodsDatabase extends BytecodeScanningDetector implements NonReportingDetector {
    private AccessMethodDatabase database;

    /**
     * @param bugReporter Unused, but we need that argument so the constructor can be called via reflection
     */
    public BuildAccessMethodsDatabase(BugReporter bugReporter) {
        database = new AccessMethodDatabase();

        Global.getAnalysisCache().eagerlyPutDatabase(AccessMethodDatabase.class, database);
    }

    @Override
    public void sawOpcode(int seen) {
        if (seen == Const.INVOKESTATIC && getMethodDescriptorOperand().isAccessMethod()) {
            MethodDescriptor callerMethod = getMethodDescriptor();
            MethodDescriptor accessMethod = getMethodDescriptorOperand();
            SourceLineAnnotation sourceLineAnnotation = SourceLineAnnotation.fromVisitedMethod(this);

            database.addAccessMethod(accessMethod, callerMethod, sourceLineAnnotation);
        }
    }
}

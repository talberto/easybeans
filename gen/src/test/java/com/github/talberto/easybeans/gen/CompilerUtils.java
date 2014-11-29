/**
 * Copyright 2014 Tomas Rodriguez 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 */

package com.github.talberto.easybeans.gen;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class CompilerUtils {

  public static boolean compile(SimpleJavaFileObject pFileObject, File pOutputDir) throws IOException {
    /* Creating dynamic java source code file object */
    JavaFileObject javaFileObjects[] = new JavaFileObject[] { pFileObject };

    /* Instantiating the java compiler */
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    /**
     * Retrieving the standard file manager from compiler object, which is used to provide basic building block for
     * customizing how a compiler reads and writes to files.
     * 
     * The same file manager can be reopened for another compiler task. Thus we reduce the overhead of scanning through
     * file system and jar files each time
     */
    StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);

    stdFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(pOutputDir));
    
    /* Prepare a list of compilation units (java source code file objects) to input to compilation task */
    Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObjects);

    /* Prepare any compilation options to be used during compilation */
    // In this example, we are asking the compiler to place the output files under bin folder.
    String[] compileOptions = new String[] { };
    Iterable<String> compilationOptions = Arrays.asList(compileOptions);

    /* Create a diagnostic controller, which holds the compilation problems */
    //DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

    /* Create a compilation task from compiler by passing in the required input objects prepared above */
    CompilationTask compilerTask = compiler.getTask(null, stdFileManager, null, compilationOptions, null, compilationUnits);

    // Perform the compilation by calling the call method on compilerTask object.
    return compilerTask.call();
  }
}

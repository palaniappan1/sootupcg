package org.example;

import sootup.callgraph.CallGraphAlgorithm;
import sootup.callgraph.ClassHierarchyAnalysisAlgorithm;
import sootup.callgraph.RapidTypeAnalysisAlgorithm;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.model.SourceType;
import sootup.core.signatures.MethodSignature;
import sootup.java.bytecode.frontend.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.JavaIdentifierFactory;
import sootup.java.core.types.JavaClassType;
import sootup.java.core.views.JavaView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        if(args.length <= 1){
            System.err.println("Need two parameters, but only one given..");
            return;
        }
        AnalysisInputLocation inputLocation1 =
                new JavaClassPathAnalysisInputLocation(
                        args[0]);
        AnalysisInputLocation inputLocation2 =
                new JavaClassPathAnalysisInputLocation(
                    "lib/rt.jar", SourceType.Library);
        ArrayList<AnalysisInputLocation> analysisInputLocations = new ArrayList<>();
        analysisInputLocations.add(inputLocation1);
        analysisInputLocations.add(inputLocation2);
        JavaView view = new JavaView(analysisInputLocations);
        CallGraphAlgorithm cg;
        if(args[1].equals("CHA")) {
            cg = new ClassHierarchyAnalysisAlgorithm(view);
        }
        else {
            cg = new RapidTypeAnalysisAlgorithm(view);
        }
        String s = cg.initialize().exportAsDot();
        String fileName = args[0].substring(args[0].lastIndexOf('/') + 1).replace(".jar", "");
        fileName = fileName + "_" + args[1] + ".txt";
        s = s.replace("strict digraph ObjectGraph {", "").replace("}","");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
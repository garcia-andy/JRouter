package jrouterfx.Annotations;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import javax.tools.*;
import javax.lang.model.*;
import javax.lang.model.type.*;
import javax.lang.model.element.*;
import javax.annotation.processing.*;


import java.util.stream.Collectors;
import javax.tools.Diagnostic.Kind;
import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("jrouterfx.Annotations.Hook")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class HookProcessor extends AbstractProcessor {

    @Override
    public boolean process(
        Set<? extends TypeElement> annotations, 
        RoundEnvironment roundEnv
    ) {
    
        Set<? extends Element> annotatedElements 
        = roundEnv.getElementsAnnotatedWith(Hook.class);

        for(Element element : annotatedElements){
            if( !element.getKind().isClass() ) continue;
            PackageElement pkg = (PackageElement) element.getEnclosingElement();
            
            try {

                Filer filer = processingEnv.getFiler();
                JavaFileObject builderClass = filer.createSourceFile(
                    pkg.toString() + "." +
                    element.getSimpleName().toString() + "Impl"
                );

                try(OutputStream out = builderClass.openOutputStream()){
                        
                    out.write((
                        "\n package " + pkg.getQualifiedName().toString() + ";\n"
                    ).getBytes());
                    out.write((
                        "public class " 
                        + element.getSimpleName() 
                        + "Impl {"
                    ).getBytes());
                    for(Element child : element.getEnclosedElements()){
                        if( 
                                !child.getKind().isExecutable() 
                            ||  !child.getModifiers().contains(Modifier.PUBLIC)
                            ||  (child.getSimpleName().toString().contains("<init>") )
                        ) continue;

                        ExecutableType method = 
                        ((ExecutableType)child.asType());
                        
                        out.write((
                            "\npublic static " +
                            method.getReturnType().toString() + " use"
                            + child.getSimpleName()
                            + "(" + method.getParameterTypes() + "){}"
                            + "\n"
                        ).getBytes());

                    }
                    out.write("\n".getBytes());
                    out.write("}".getBytes());   
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }
    
}

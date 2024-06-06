package io.github.andy030124;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class FolderParser {

    public static ArrayList<String> printDir(
        String contentPath, 
        String AbsoluteStart, 
        String ends
    ){ return printDir(contentPath, AbsoluteStart, ends, null); }

    public static boolean constraits(
        Path fpath,
        String absoluteRootPath,
        String prefix,
        String pathStarts,
        String nameStarts,
        String ends
    ){
        String abs = fpath.toString();
        String rel = abs.replace(absoluteRootPath, "");
        String name = fpath.getFileName().toString();
        return (
            (
                absoluteRootPath != null 
                && !abs.startsWith(absoluteRootPath)
            )||(
                prefix != null
                && !rel.startsWith(prefix)
            )||(
                pathStarts != null
                && !abs.startsWith(pathStarts)
            )||(
                nameStarts != null
                && !name.startsWith(nameStarts)
            )||(
                name.startsWith("_") && name.endsWith("_" + ends)
            )||(
                ends != null 
                && !abs.endsWith(ends)
            )
        );
    }

    private static ArrayList<String> pdir_impl(
        String absoluteRootPath,
        String prefix,
        String pathStarts,
        String nameStarts,
        String ends
    ){
        absoluteRootPath = 
        (absoluteRootPath.endsWith("/")
            ? absoluteRootPath 
            : (absoluteRootPath + "/")
        );

        prefix = 
        (prefix == null
            ? "" 
            : (prefix)
        );

        File folder = new File( absoluteRootPath + prefix );
        File[] listOfFiles = folder.listFiles();
        
        ArrayList<String> verifiedFiles = new ArrayList<String>();
        if(listOfFiles == null) return verifiedFiles;

        for(int i=0; i < listOfFiles.length; i++){
            File f = listOfFiles[i];
            Path fpath = f.toPath();
            if (f.isFile()){
                if(constraits(
                    fpath,absoluteRootPath,prefix,
                    pathStarts,nameStarts,ends
                )) continue;

                String abs = fpath.toString();
                String rel = abs.replace(
                    (pathStarts == null)? "":pathStarts, ""
                ).replace(
                    (ends == null)? "":ends, ""
                );
                verifiedFiles.add(rel);
            }else{
                verifiedFiles.addAll(
                    pdir_impl(
                        fpath.toString(), prefix, pathStarts, nameStarts, ends
                    )
                );
            }
        }
        return verifiedFiles;
    }

    public static ArrayList<String> printDir(
        String contentPath, 
        String AbsoluteStart, 
        String ends,
        String NameStart
    ){
        contentPath = 
            (contentPath.endsWith("/")
                ? contentPath 
                : (contentPath + "/")
            );
        return pdir_impl(
            contentPath,null,AbsoluteStart,NameStart,ends
        );
    }


}

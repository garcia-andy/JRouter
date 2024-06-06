package io.github.andy030124;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

/*
*   Parser folders and files utilities (static access)
*   @author Andy Garcia
*   @version 0.1, 2024/5/6
*/ 
public class FolderParser {

    /*
     * Optional interface more small params
     * @param contentPath: String of absolute route to the folder
     * @param AbsoluteStart: String for startsWith required in contentPath
     * @param ends: String for endsWith
     * @return ArrayList<String> with relatives filesNames
     */
    public static ArrayList<String> printDir(
        String contentPath, 
        String AbsoluteStart, 
        String ends
    ){ return printDir(contentPath, AbsoluteStart, ends, null); }

    /*
     * Verify if a Path have all constraits
     * @param fpath: Path of file to verify
     * @param prefix: Prefix required in the relative path to file
     * @param pathStarts: Absolute Path starts with
     * @param nameStarts: Relative Path starts with
     * @param ends: Absolute/Relative Path ends with
     * @return boolean: true if pass all test
     */
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

    /*
     * Private implementation of directory and files parser
     */
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

    /*
     * Principal Interface for use full featured directories parser
     * @param contentPath String of absolute route to the folder
     * @param AbsoluteStart String for startsWith required in contentPath
     * @param ends String for endsWith
     * @param NameStart String for verify the name of files starts with
     * @return ArrayList<String> with relatives filesNames
     */
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

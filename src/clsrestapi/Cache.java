/*
 * Copyright 2017 Ken Lowrie <ken@klowrie.net>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package clsrestapi;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Ken Lowrie <ken@klowrie.net>
 */
public class Cache {
    private Path cacheDir;
    
    public Cache(String cacheDir) throws IOException{
        this.cacheDir = Paths.get(cacheDir);
        
        if( !Files.isDirectory(this.cacheDir)){
            //System.out.println("Directory does not exist");
            
            File tmpFile = new File(this.cacheDir.normalize().toString());
            try {
                tmpFile.mkdirs();
            } catch(SecurityException E){
                
            }
        }
        if (!Files.isDirectory(this.cacheDir)){
            throw new IOException("Unable to create or access the cache directory: " + this.cacheDir.toString());
        }
    }
    
    public Path getPath(){
        return cacheDir;
    }
    
    public String getDir(){
        return cacheDir.toString();
    }
    
    public boolean itemExists(String itemName){
        Path item = Paths.get(this.cacheDir.toString(),itemName);
        
        return Files.exists(item);
    }
    
    public String absolutePath(String itemName){
        return Paths.get(this.cacheDir.toString(),itemName).toString();
    }
    
    public boolean flush() {
        boolean retVal = true;
        
        try{
            rm(false);
        } catch (IOException E){
            retVal = false;
        }
        return retVal;
    }
    
    public boolean remove() {
        boolean retVal = true;
        
        try{
            rm(true);
        } catch (IOException E){
            retVal = false;
        }
        return retVal;
    }
    
    public boolean removeItem(String itemName){
        boolean retVal = false;
        Path item = Paths.get(this.cacheDir.toString(),itemName);
        
        if( Files.exists(item)){
            try{
                Files.delete(item);
                retVal = true;
            }catch (IOException E){

            }
        }
        return retVal;
    }
    
    private void rm(boolean rf) throws IOException {
        Files.walkFileTree(cacheDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                if (dir.normalize().toAbsolutePath().equals(cacheDir.normalize().toAbsolutePath())){
                    if (rf){
                        Files.delete(dir);
                    }
                } else {
                    Files.delete(dir);
        }
                return FileVisitResult.CONTINUE;
            }
        });
    }    
}

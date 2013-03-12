/*
 * Copyright (C) 2012 Eyal LEZMY (http://www.eyal.fr)
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

package fr.eyal.lib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.eyal.lib.data.service.DataLibService;
import fr.eyal.lib.data.service.ServiceHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * 
 * This class help to manage the storage access (internal, external, files and cache).<br>
 * This class can be used statically through the its different functions but can also be 
 * used as a singleton. The singleton is useful when you want to access the internal storage
 * that use {@link Context} functions ({@link Context#getCacheDir()} or 
 * {@link Context#openFileInput(String)}, ...). <br>
 * Once the singleton is initialized (thanks to {@link FileManager#getInstance(Context)}), 
 * it is ready to use everywhere you need, even if you don't have access to the execution's context.
 * <br><br>
 * If you use the Android DataLib, this class is initialized on the {@link ServiceHelper} and the 
 * {@link DataLibService} constructors.
 * So it is ready to use everywhere on the application if you use the project's ServiceHelper or DataManager
 * or if you use add your project DataLib service on you Manifest.xml.
 * 
 * @author Eyal LEZMY
 */
public class FileManager {
	
	
    private static final String TAG = FileManager.class.getSimpleName();

    private static FileManager sInstance = null;

    private static final String DEFAULT_EXTENSION = ".jpg";
    private static final String NOMEDIA_FILENAME = ".nomedia";

    private static StringBuilder sStringBuilder = new StringBuilder();

    private Context mContext;    
    
    /**
     * Get the instance of the {@link NetflixDataManager}
     * 
     * @param context The context of execution. Any Context can be put here, the application context
     * will be automatically used for the {@link NetflixDataManager}
     * 
     * @return Returns the singleton
     */
    public static FileManager getInstance(final Context context) {
        synchronized (FileManager.class) {
			if (sInstance == null) {
            	sInstance = new FileManager(context.getApplicationContext());
        	}
		}
        return sInstance;
    }

    /**
     * Get the instance of the {@link FileManager}
     *  
     * @return Returns the singleton <b>only if</b> the instance have already been create by the call to 
     * <code>{@linkplain FileManager#getInstance(Context)}</code>. If it has not been called, this 
     * function returns <b>null</b>
     */
    public static FileManager getInstance() {
		if (sInstance == null) {
        	return null;
    	}
        return sInstance;
    }


    protected FileManager(final Context context){
    	mContext = context;
    }

    
    /**
     * Returns whether a file exists on the sdcard
     * 
     * @param filename the file name
     * @param packageName the name of the package
     * @return whether a file exists on the sdcard
     */
    public static boolean isFileOnSd(final String filename, final String packageName) {

        final String fullFilename = getExternalStorageFileDirectory(packageName) + filename;
        final File file = new File(fullFilename);
        return file.isFile();
    }

    /**
     * Get the full path for a given package name
     * 
     * @param packageName the name of the package
     * @return the full path for a given package name
     */
    private static String getExternalStorageFileDirectory(final String packageName) {
        final StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separatorChar + "Android" + File.separatorChar + "data" + File.separatorChar);
        sb.append(packageName);
        sb.append(File.separatorChar + "files" + File.separatorChar);
        return sb.toString();
    }

    /**
     * Save a Bitmap in the default application directory into SD card
     * 
     * @param filename the file name.
     * @param packageName the name of the package
     * @param bitmap the Bitmap to save
     * @param compressionQuality the quality for compression. Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting.
     * 
     * @return returns the file created path or <code>null</code> if an error occurs
     */
    public static String saveOnSdCard(final String fileName, final String packageName, final Bitmap bitmap, final int compressionQuality) {

    	if(bitmap == null)
    		return null;
    	
        final String directory = getExternalStorageFileDirectory(packageName);
        final File imageFile = new File(directory, fileName);
        final File imageDir = new File(directory);
        if (!imageDir.isDirectory()) {
            imageDir.mkdirs();
            final File noMediaFile = new File(directory, NOMEDIA_FILENAME);
            try {
                noMediaFile.createNewFile();
            } catch (final IOException e) {
                Out.e(TAG, "Error while creating .nomedia file", e);
                return null;
            }
        }

        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            if (getFileExtension(fileName).equals(DEFAULT_EXTENSION)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, fos);
            } else {
                bitmap.compress(Bitmap.CompressFormat.PNG, compressionQuality, fos);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        String filePath = directory + File.pathSeparator + fileName;
        return filePath;
    }

    /**
     * Save a Bitmap in the default application directory into the internal storage
     * 
     * @param filename the file name
     * @param bitmap the Bitmap to save
     * @param compressionQuality the quality for compression. Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
     * 
     * @return returns <code>true</code> if the storage has been successful or <code>false</code> if an error occurred
     */
    public boolean saveInInternalStorage(final String filename, final Bitmap bitmap, final int compressionQuality) {
    	return saveInInternalStorage(mContext, filename, bitmap, compressionQuality);
    }

    /**
     * Save a Bitmap in the default application directory into the internal
     * storage
     * 
     * @param context the application context
     * @param filename the file name
     * @param bitmap the Bitmap to save
     * @param compressionQuality the quality for compression. Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
     * 
     * @return returns <code>true</code> if the storage has been successful or <code>false</code> if an error occurred
     */
    public static boolean saveInInternalStorage(final Context context, final String filename, final Bitmap bitmap, final int compressionQuality) {

    	if(bitmap == null)
    		return false;
    	
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            
            if (getFileExtension(filename).equals(DEFAULT_EXTENSION)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, fos);
            } else {
                bitmap.compress(Bitmap.CompressFormat.PNG, compressionQuality, fos);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Save a Bitmap in the default application directory into the internal cache storage
     * 
     * @param directory the child directory where to put the file. This parameter can contains {@link File#separator} character
     * @param filename the file name
     * @param bitmap the Bitmap to save
     * @param compressionQuality the quality for compression. Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
     * 
     * @return returns the file created path or <code>null</code> if an error occurs
     */
    public String saveInInternalCache(final String filename, final String extension, final Bitmap bitmap, final int compressionQuality) {
    	return saveInInternalCache(mContext, filename, extension, bitmap, compressionQuality);
    }

    
    /**
     * Save a Bitmap in the default application directory into the internal cache storage
     * 
     * @param context the application context
     * @param directory the child directory where to put the file. This parameter can contains {@link File#separator} character
     * @param filename the file name
     * @param bitmap the Bitmap to save
     * @param compressionQuality the quality for compression. Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
     * 
     * @return returns the file created path or <code>null</code> if an error occurs
     */
    public static String saveInInternalCache(final Context context, final String filename, final String extension, final Bitmap bitmap, final int compressionQuality) {
    	return FileManager.saveInInternalCache(context, null, filename, extension, bitmap, compressionQuality);
    }


    /**
     * Save a Bitmap in the default application directory into the internal cache storage
     * 
     * @param directory the child directory where to put the file. This parameter can contains {@link File#separator} character
     * @param filename the file name
     * @param bitmap the Bitmap to save
     * @param compressionQuality the quality for compression. Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
     * 
     * @return returns the file created path or <code>null</code> if an error occurs
     */
    public String saveInInternalCache(final String directory, final String filename, final String extension, final Bitmap bitmap, final int compressionQuality) {
    	return FileManager.saveInInternalCache(mContext, directory, filename, extension, bitmap, compressionQuality);
    }

    /**
     * Save a Bitmap in the default application directory into the internal cache storage
     * 
     * @param context the application context
     * @param directory the child directory where to put the file. This parameter can contains {@link File#separator} character
     * @param filename the file name
     * @param bitmap the Bitmap to save
     * @param compressionQuality the quality for compression. Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
     * 
     * @return returns the file created path or <code>null</code> if an error occurs
     */
    public static String saveInInternalCache(final Context context, final String directory, final String filename, String extension, final Bitmap bitmap, final int compressionQuality) {

    	if(bitmap == null)
    		return null;
    	
    	File cacheDir = getInternalCacheDirectory(directory, context);
    	
    	//we open the new file
    	final File cacheFile = new File(cacheDir, filename);
        try {
        	cacheFile.createNewFile();
        } catch (final IOException e) {
            Out.e(TAG, "Error while creating cache file " + filename, e);
            return null;
        }
    	
        //we fill and save the file
        FileOutputStream fos = null;
        try {
        	fos = new FileOutputStream(cacheFile);

        	if(extension == null || extension.length() == 0)
        		extension = getFileExtension(filename);
        		
            if (extension.compareToIgnoreCase(DEFAULT_EXTENSION) == 0) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, fos);
            } else {
                bitmap.compress(Bitmap.CompressFormat.PNG, compressionQuality, fos);
            }
            
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
            
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        
        return cacheFile.getAbsolutePath();
    }

    
	/**
     * Returns a Bitmap image from device SDCard or null if image not found.
     * This function reach the cache directory of the SD card, i-e
     * Android/data/<package>/file/ folder.
     * 
     * @param filename the file name for this Bitmap.
     * @param packageName the name of the package
     * @return the bitmap save on the SDCard
     */
    public static Bitmap getPictureFromSDCard(final String filename, final String packageName) {
        return getPictureFromSDCard("", filename, packageName);
    }

    /**
     * Returns a Bitmap image from device SDCard or null if image not found.
     * This function reach the cache directory of the SD card, i-e
     * Android/data/<package>/file/ folder.
     * 
     * @param path the path starting from the cache directory of the application
     *            (without separator '/' character)
     * @param filename the file name for this Bitmap.
     * @param packageName the name of the package
     * @return the bitmap save on the SDCard
     */
    public static Bitmap getPictureFromSDCard(final String path, final String filename, final String packageName) {
        return getPictureFromSDCard(path, filename, packageName, false);
    }

    /**
     * Returns a Bitmap image from device SDCard or null if image not found.
     * This function reach the cache directory of the SD card, i-e
     * Android/data/<package>/file/ folder.
     * 
     * @param path the path starting from the cache directory of the application
     *            (without separator '/' character)
     * @param filename the file name for this Bitmap.
     * @param packageName the name of the package
     * @param hasSpecialPath add "external_sd" to the file path
     * @return the bitmap save on the SDCard
     */
    public static Bitmap getPictureFromSDCard(final String path, final String filename, final String packageName, final boolean hasSpecialPath) {

        sStringBuilder.setLength(0);
        sStringBuilder.append(Environment.getExternalStorageDirectory());
        if (hasSpecialPath) {
            sStringBuilder.append(File.separatorChar + "external_sd");
        }
        sStringBuilder.append(File.separatorChar + "Android" + File.separatorChar + "data" + File.separatorChar);
        sStringBuilder.append(packageName);
        sStringBuilder.append(File.separatorChar + "files" + File.separatorChar);
        if (path != null && path != "") {
            sStringBuilder.append(path + File.separatorChar);
        }
        sStringBuilder.append(filename);

        final String fullPath = sStringBuilder.toString();

        Bitmap returnBitmap = null;
        final File picture = new File(fullPath);
        if (picture.isFile()) {
            returnBitmap = BitmapFactory.decodeFile(fullPath);
        }

        return returnBitmap;
    }

    /**
     * Returns a Bitmap image from internal storage or null if image not found.
     * 
     * @param directory the directory following the cache directory. This parameter can contains {@link File#separator} character
     * @param path the file path for this Bitmap.
     * @param options {@link BitmapFactory.Options} to use for the {@link BitmapFactory#decodeFile(String, android.graphics.BitmapFactory.Options)} function
     * 
     * @return the bitmap save on the internal storage
     */
    public Bitmap getPictureFromInternalCache(final String directory, final String filename, BitmapFactory.Options options) {
    	return FileManager.getPictureFromInternalCache(directory, filename, mContext, options);
    }

    /**
     * Returns the file's path from internal storage or null if image not found.
     * 
     * @param directory the directory following the cache directory. This parameter can contains {@link File#separator} character
     * @param path the file path for this file.
     * @return the bitmap save on the internal storage
     */
    public String getPathFromInternalCache(final String directory, final String filename) {
    	
    	return FileManager.getInternalCacheDirectory(directory, mContext).getAbsolutePath() + File.separator + filename;
    }

    
    /**
     * Returns a Bitmap image from internal storage or null if image not found.
     * 
     * @param directory the directory following the cache directory. This parameter can contains {@link File#separator} character
     * @param path the file path for this Bitmap.
     * @param context the application context
     * @return the bitmap save on the internal storage
     */
    public static Bitmap getPictureFromInternalCache(final String directory, final String filename, final Context context) {
    	return FileManager.getPictureFromInternalCache(directory, filename, context, null);
    }


    /**
     * Returns a Bitmap image from internal storage or null if image not found.
     * 
     * @param directory the directory following the cache directory. This parameter can contains {@link File#separator} character
     * @param path the file path for this Bitmap.
     * @param context the application context
     * @param options {@link BitmapFactory.Options} to use for the {@link BitmapFactory#decodeFile(String, android.graphics.BitmapFactory.Options)} function
     * 
     * @return the bitmap save on the internal storage
     */
    public static Bitmap getPictureFromInternalCache(final String directory, final String filename, final Context context, BitmapFactory.Options options) {

    	File cacheDir = getInternalCacheDirectory(directory, context);
    	
//    	options.inSampleSize = 1;
//    	Out.d("", "POSTER " + opts.inSampleSize);
        return BitmapFactory.decodeFile(cacheDir.getAbsolutePath() + File.separator + filename, options);
    }

    
	private static File getInternalCacheDirectory(final String directory, final Context context) {
		//we get the root cache folder
    	File cacheDir = context.getCacheDir();

    	//we eventually go through the specified directory
    	if(directory != null){
    		cacheDir = new File(cacheDir.getAbsoluteFile() + File.separator + directory);
    		if(!cacheDir.isDirectory())
    			cacheDir.mkdirs();
    	}
		return cacheDir;
	}


    
    /**
     * Returns a Bitmap image from internal storage or null if image not found.
     * 
     * @param path the file path for this Bitmap.
     * @param options {@link BitmapFactory.Options} to use for the {@link BitmapFactory#decodeFile(String, android.graphics.BitmapFactory.Options)} function
     * 
     * @return the bitmap save on the internal storage
     */
    public static Bitmap getPictureFromFile(final String path, BitmapFactory.Options options) {
//    	BitmapFactory.Options options = new BitmapFactory.Options();
//    	options.inSampleSize = 1;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * Returns a Bitmap image from internal storage or null if image not found.
     * 
     * @param filename the file name for this Bitmap.
     * @return the bitmap save on the internal storage
     */
    public Bitmap getPictureFromInternalStorage(final String filename) {
    	return FileManager.getPictureFromInternalStorage(filename, mContext);
    }

    /**
     * Returns a Bitmap image from internal storage or null if image not found.
     * 
     * @param filename the file name for this Bitmap.
     * @param context the application context
     * @return the bitmap save on the internal storage
     */
    public static Bitmap getPictureFromInternalStorage(final String filename, final Context context) {

        try {
            final FileInputStream fis = context.openFileInput(filename);
            return BitmapFactory.decodeStream(fis);

        } catch (final FileNotFoundException e) {
            return null;
        }
    }
    

    public static void deletePrefixedFilesFromDirectoryInSDCard(final String packageName, final String prefix) {
        final String path = getExternalStorageFileDirectory(packageName);

        final File directory = new File(path);
        if (directory != null) {
            final File[] files = directory.listFiles();
            if (files != null) {

                final int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    final String filename = files[i].getName();

                    if (filename.length() >= prefix.length() && filename.substring(0, prefix.length()).equals(prefix)) {
                        files[i].delete();
                    }
                }
            }
        }
    }

    public static void deletePrefixedFilesFromDirectoryInInternalStorage(final Context context, final String prefix) {
        final File directory = context.getFilesDir();
        if (directory != null) {
            final File[] files = directory.listFiles();
            if (files != null) {
                final int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    final String filename = files[i].getName();

                    if (filename.length() >= prefix.length() && filename.substring(0, prefix.length()).equals(prefix)) {
                        files[i].delete();
                    }
                }
            }
        }
    }

    /**
     * Get the file extension
     * 
     * @param filename the file name
     * @return the file extension
     */
    public static String getFileExtension(final String filename) {
        String extension;
        final int dotPos = filename.lastIndexOf(".");
        if (dotPos != -1 || (dotPos != filename.length() - 1)) {
            extension = filename.substring(dotPos);
        } else {
            extension = DEFAULT_EXTENSION;
        }
        return extension;
    }

    /**
     * Get the file name
     * 
     * @param fullpath the full path
     * @return the file name
     */
    public static String getFileName(final String fullpath) {
        final int dotPos = fullpath.lastIndexOf(File.separatorChar);
        if (dotPos != -1 || (dotPos != fullpath.length() - 1)) {
            return fullpath.substring(dotPos + 1);
        } else {
            return fullpath;
        }
    }
}

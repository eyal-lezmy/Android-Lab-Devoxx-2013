// Start of user code fr.eyal.datalib.sample.netflix.data.model.newreleases.ItemNewRelease. DO NOT MODIFY THE GENERATED COMMENTS
package fr.eyal.datalib.sample.netflix.data.model.newreleases;

import java.io.File;
import java.lang.ref.SoftReference;

import fr.eyal.datalib.sample.netflix.data.model.movieimage.MovieImage;
import fr.eyal.datalib.sample.netflix.fragment.MovieItem;
import fr.eyal.lib.util.FileManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;

public class ItemNewRelease extends ItemNewReleaseBase implements MovieItem{

    private static final String TAG = ItemNewRelease.class.getSimpleName();

    /**
     * Image's reference
     */
    public MovieImage image = null;

    /**
     * Image's url of the Top100 item
     */
    public String imageUrl = null;

    /**
     * Image's name of the Top100 item
     */
    public String imageName = null;


    
    public ItemNewRelease() {
        super();
    }

    public ItemNewRelease(final Parcel in) {
        super(in);
    }

    public ItemNewRelease(final long id) {
        super(id);
    }
    
    @Override
    public Bitmap getPoster(boolean forceCache){
    	if(image == null || image.image == null)
    		return null;
    	
    	Bitmap result = image.image.get();
    	
    	if(result == null && forceCache){
    		
    		BitmapFactory.Options options = new BitmapFactory.Options();
    		
    		image.image = new SoftReference<Bitmap>(FileManager.getPictureFromFile(image.imagePath, options));
    		result = image.image.get();
    	}
    	
    	return result;
    }

    @Override
    public String getPosterPath(){
    	if(image == null)
    		return null;

    	return image.imagePath;
    }
    
    @Override
    public String getPosterName(){
    	if(image == null)
    		return null;
    	
    	//we assume the name is not supposed to change
    	if(imageName != null)
    		return imageName;

    	String[] elements = image.imagePath.split(File.separator);
    	imageName = elements[elements.length-1];
    	return imageName;
    }
    
    
    @Override
    public String getImageUrl(){
    	
    	if(imageUrl == null){
    		String[] content = description.split("<img src=\"");
    		content = content[1].split("\"");
    		
    		String result = content[0];
    		result = result.replace("/small/", "/ghd/");
    		imageUrl = result;
    	}
    	return imageUrl;
    }

	@Override
	public String getLabel(int position) {
		return title;
	}
    
	@Override
	public void setImage(MovieImage newImage) {
		image = newImage;
	}

	@Override
	public MovieImage getImage() {
		return image;
	}


}
// End of user code

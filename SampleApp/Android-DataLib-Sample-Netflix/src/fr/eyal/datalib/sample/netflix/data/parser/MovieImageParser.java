package fr.eyal.datalib.sample.netflix.data.parser;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.movieimage.*;
import fr.eyal.lib.data.service.model.ComplexOptions;
// Start of user code MovieImageParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code



public class MovieImageParser implements GenericHandler {

	@SuppressWarnings("unused")
    private static final String TAG = MovieImageParser.class.getSimpleName();

	MovieImage movieImage;
	ComplexOptions complexOptions;

	public MovieImageParser(ComplexOptions options){
		movieImage = new MovieImage();
		complexOptions = options;
	}
	
	@Override
	public ResponseBusinessObject getParsedData() {
		return movieImage;
	}

	@Override
	public void parse(Object content) {
		
		BitmapFactory.Options options = null;
		if(complexOptions != null)
			options = (BitmapFactory.Options) complexOptions.getBitmapOptions();
		
		byte[] data = (byte[]) content;
		movieImage.image = new SoftReference<Bitmap>(BitmapFactory.decodeByteArray(data, 0, data.length, options));
	}
	

// Start of user code MovieImageParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}



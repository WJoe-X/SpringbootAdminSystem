package lib.sixzeroseven.admin.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StorageProperties {

    /**
     * Folder location for storing files
     */
	@Value("${picture.upload-path}")
    private String pictureLocation;

	

	public String getpictureLocation() {
        return pictureLocation;
    }

    public void setpictureLocation(String pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

}

package org.southplast.calculation.shrinkage.core.images;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ImageFactory {
   static Map<String, Image> imageCache = new HashMap<String, Image>();
   static Map<String, ImageDescriptor> imageDescriptorCache = 
		   				new HashMap<String, ImageDescriptor>();

   public static Image getImage(String imageName) {
      if (!imageCache.containsKey(imageName)) {
         imageCache.put(imageName, getDescriptor(imageName).createImage());
      }
      return imageCache.get(imageName);
   }

   public static ImageDescriptor getDescriptor(String imageName) {
      if (!imageDescriptorCache.containsKey(imageName)) {
         URL url = ImageFactory.class.getResource(imageName);
         imageDescriptorCache.put(imageName, ImageDescriptor.createFromURL(url));
      }
      return imageDescriptorCache.get(imageName);
   }

}

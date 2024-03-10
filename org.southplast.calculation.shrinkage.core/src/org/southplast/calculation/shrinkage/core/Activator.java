package org.southplast.calculation.shrinkage.core;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.hsqldb.Server;
import org.osgi.framework.BundleContext;
/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.southplast.calculation.shrinkage.core"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private static  BundleContext context;
	private  Server hsqlServer = null;
	
	
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		setContext(context); 	
		
		 hsqlServer = new Server();

         // HSQLDB prints out a lot of informations when
         // starting and closing, which we don't need now.
         // Normally you should point the setLogWriter
         // to some Writer object that could store the logs.
         hsqlServer.setLogWriter(null);
         hsqlServer.setSilent(true);

         // The actual database will be named 'xdb' and its
         // settings and data will be stored in files
         // testdb.properties and testdb.script
         hsqlServer.setDatabaseName(0, "xdb");
         // hsqlServer.setDatabasePath(0, "file:db\\shrinkagedb");
         hsqlServer.setDatabasePath(0,"file:d:\\develop\\shrinkage\\db\\shrinkagedb");

         // Start the database!
         hsqlServer.start();
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		
		 // Closing the server
        if (hsqlServer != null) {
            hsqlServer.stop();
        }
		
		super.stop(context);				
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static BundleContext getContext() {
		return context;
	}

	public static void setContext(BundleContext context) {
		Activator.context = context;
	}
}

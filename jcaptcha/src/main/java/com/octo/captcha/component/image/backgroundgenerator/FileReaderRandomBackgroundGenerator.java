/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.backgroundgenerator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.octo.captcha.CaptchaException;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

/**
 * <p>File reader background generator that return a random image (JPEG ONLY) from the ones found in the directory </p>
 * <p>You can place images in the classpath directory, in this case take care to use a unique directory name (not already contained in a jar file)</p>
 * TODO : add some gif, bmp,... reader facilities.
 *
 * @author <a href="mailto:mag@octo.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class FileReaderRandomBackgroundGenerator extends
        AbstractBackgroundGenerator {

    private List images = new ArrayList();
    private String rootPath = ".";

    public FileReaderRandomBackgroundGenerator(Integer width,
                                               Integer height, String rootPath) {
        super(width, height);

        if (rootPath != null)
            this.rootPath = rootPath;

        File dir = findDirectory(this.rootPath);

        File[] files = dir.listFiles();

        //get all jpeg
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                BufferedImage out = null;
                if (file.isFile()) {
                    out = getImage(file);
                }
                if (out != null) {
                    images.add(images.size(), out);
                }
            }


            if (images.size() != 0) {
                for (int i = 0; i < images.size(); i++) {
                    BufferedImage bufferedImage = (BufferedImage) images.get(i);
                    images.set(i, tile(bufferedImage));
                }
            } else {
                throw new CaptchaException("Root path directory is valid but " +
                        "does not contains any image (jpg) files");
            }
        }
    }

    /**
     *
     */
    protected static final Map cachedDirectories = new HashMap();

    protected File findDirectory(String rootPath) {
        if (cachedDirectories.containsKey(rootPath)) {
            return (File) cachedDirectories.get(rootPath);
        }

        //try direct path
        File dir = new File(rootPath);
        StringBuffer triedPath = new StringBuffer();
        appendFilePath(triedPath, dir);
        if (isNotReadable(dir)) {
            //try with . parent
            dir = new File(".", rootPath);
            appendFilePath(triedPath, dir);
            if (isNotReadable(dir)) {
                //try with / parent
                dir = new File("/", rootPath);
                appendFilePath(triedPath, dir);

                if (isNotReadable(dir)) {
                    //trying with ressource
                    URL url = FileReaderRandomBackgroundGenerator.class.getClassLoader().getResource(rootPath);
                    if (url != null) {
                        dir = new File(getFilePath(url));
                        appendFilePath(triedPath, dir);

                    } else {
                        //trying the class path
                        url = ClassLoader.getSystemClassLoader().getResource(rootPath);

                        if (url != null) {
                            dir = new File(getFilePath(url));
                            appendFilePath(triedPath, dir);

                        }
                    }
                }
            }
        }
        // FIXME - avoid double-checking
        if (isNotReadable(dir)) {
            // dir is still no good -- let's try directories in the system classpath
            StringTokenizer token = getClasspathFromSystemProperty();
            while (token.hasMoreElements()) {
                String path = token.nextToken();
                if (!path.endsWith(".jar")) {
                    dir = new File(path, rootPath);
                    appendFilePath(triedPath, dir);
                    if (dir.canRead() && dir.isDirectory()) {
                        break;
                    }
                }
            }
        }


        if (isNotReadable(dir)) {
            throw new CaptchaException("All tried paths :'" + triedPath.toString() + "' is not" +
                    " a directory or cannot be read");
        }

        // cache answer for later
        cachedDirectories.put(rootPath, dir);

        return dir;
    }

	private String getFilePath(URL url) {
		String file = null;
		try {
			file = URLDecoder.decode(url.getFile(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// Do Nothing			
		}
		return file;
	}

	private boolean isNotReadable(File dir) {
		return !dir.canRead() || !dir.isDirectory();
	}

    private StringTokenizer getClasspathFromSystemProperty() {

        String classpath = System.getProperty("java.class.path");
        StringTokenizer token = new StringTokenizer(classpath, File.pathSeparator);
        return token;
    }


    private void appendFilePath(StringBuffer triedPath, File dir) {
        triedPath.append(dir.getAbsolutePath());
        triedPath.append("\n");
    }

    private BufferedImage tile(BufferedImage tileImage) {
        BufferedImage image = new BufferedImage(getImageWidth(),
                getImageHeight(), tileImage.getType());
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int NumberX = (getImageWidth() / tileImage.getWidth());
        int NumberY = (getImageHeight() / tileImage.getHeight());
        for (int k = 0; k <= NumberY; k++) {
            for (int l = 0; l <= NumberX; l++) {
                g2.drawImage(tileImage, l * tileImage.getWidth(), k *
                        tileImage.getHeight(),
                        Math.min(tileImage.getWidth(), getImageWidth()),
                        Math.min(tileImage.getHeight(), getImageHeight()),
                        null);
            }
        }
        g2.dispose();
        return image;
    }

    private static BufferedImage getImage(File o) {
        
        try {
            FileInputStream fis = new FileInputStream(o);
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
            BufferedImage out = decoder.decodeAsBufferedImage();
            fis.close();

            // Return the format name
            return out;
            
        } catch (IOException e) {
            throw new CaptchaException("Unknown error during file reading ", e);
        } catch (ImageFormatException e) {
            return null;
        }
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    public BufferedImage getBackground() {
        return (BufferedImage) images.get(myRandom.nextInt(images.size()));
    }

}

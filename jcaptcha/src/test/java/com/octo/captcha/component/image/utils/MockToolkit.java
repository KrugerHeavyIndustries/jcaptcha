/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.im.InputMethodHighlight;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.peer.*;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Mock toolkit for ToolkitFactory test
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class MockToolkit extends Toolkit {

    /**
     * Creates this toolkit's implementation of <code>Button</code> using the specified peer interface.
     *
     * @param target the button to be implemented.
     *
     * @return this toolkit's implementation of <code>Button</code>.
     *
     * @see java.awt.Button
     * @see java.awt.peer.ButtonPeer
     */
    protected ButtonPeer createButton(Button target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>TextField</code> using the specified peer interface.
     *
     * @param target the text field to be implemented.
     *
     * @return this toolkit's implementation of <code>TextField</code>.
     *
     * @see java.awt.TextField
     * @see java.awt.peer.TextFieldPeer
     */
    protected TextFieldPeer createTextField(TextField target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Label</code> using the specified peer interface.
     *
     * @param target the label to be implemented.
     *
     * @return this toolkit's implementation of <code>Label</code>.
     *
     * @see java.awt.Label
     * @see java.awt.peer.LabelPeer
     */
    protected LabelPeer createLabel(Label target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>List</code> using the specified peer interface.
     *
     * @param target the list to be implemented.
     *
     * @return this toolkit's implementation of <code>List</code>.
     *
     * @see java.awt.List
     * @see java.awt.peer.ListPeer
     */
    protected ListPeer createList(List target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Checkbox</code> using the specified peer interface.
     *
     * @param target the check box to be implemented.
     *
     * @return this toolkit's implementation of <code>Checkbox</code>.
     *
     * @see java.awt.Checkbox
     * @see java.awt.peer.CheckboxPeer
     */
    protected CheckboxPeer createCheckbox(Checkbox target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Scrollbar</code> using the specified peer interface.
     *
     * @param target the scroll bar to be implemented.
     *
     * @return this toolkit's implementation of <code>Scrollbar</code>.
     *
     * @see java.awt.Scrollbar
     * @see java.awt.peer.ScrollbarPeer
     */
    protected ScrollbarPeer createScrollbar(Scrollbar target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>ScrollPane</code> using the specified peer interface.
     *
     * @param target the scroll pane to be implemented.
     *
     * @return this toolkit's implementation of <code>ScrollPane</code>.
     *
     * @see java.awt.ScrollPane
     * @see java.awt.peer.ScrollPanePeer
     * @since JDK1.1
     */
    protected ScrollPanePeer createScrollPane(ScrollPane target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>TextArea</code> using the specified peer interface.
     *
     * @param target the text area to be implemented.
     *
     * @return this toolkit's implementation of <code>TextArea</code>.
     *
     * @see java.awt.TextArea
     * @see java.awt.peer.TextAreaPeer
     */
    protected TextAreaPeer createTextArea(TextArea target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Choice</code> using the specified peer interface.
     *
     * @param target the choice to be implemented.
     *
     * @return this toolkit's implementation of <code>Choice</code>.
     *
     * @see java.awt.Choice
     * @see java.awt.peer.ChoicePeer
     */
    protected ChoicePeer createChoice(Choice target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Frame</code> using the specified peer interface.
     *
     * @param target the frame to be implemented.
     *
     * @return this toolkit's implementation of <code>Frame</code>.
     *
     * @see java.awt.Frame
     * @see java.awt.peer.FramePeer
     */
    protected FramePeer createFrame(Frame target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Canvas</code> using the specified peer interface.
     *
     * @param target the canvas to be implemented.
     *
     * @return this toolkit's implementation of <code>Canvas</code>.
     *
     * @see java.awt.Canvas
     * @see java.awt.peer.CanvasPeer
     */
    protected CanvasPeer createCanvas(Canvas target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Panel</code> using the specified peer interface.
     *
     * @param target the panel to be implemented.
     *
     * @return this toolkit's implementation of <code>Panel</code>.
     *
     * @see java.awt.Panel
     * @see java.awt.peer.PanelPeer
     */
    protected PanelPeer createPanel(Panel target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Window</code> using the specified peer interface.
     *
     * @param target the window to be implemented.
     *
     * @return this toolkit's implementation of <code>Window</code>.
     *
     * @see java.awt.Window
     * @see java.awt.peer.WindowPeer
     */
    protected WindowPeer createWindow(Window target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Dialog</code> using the specified peer interface.
     *
     * @param target the dialog to be implemented.
     *
     * @return this toolkit's implementation of <code>Dialog</code>.
     *
     * @see java.awt.Dialog
     * @see java.awt.peer.DialogPeer
     */
    protected DialogPeer createDialog(Dialog target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>MenuBar</code> using the specified peer interface.
     *
     * @param target the menu bar to be implemented.
     *
     * @return this toolkit's implementation of <code>MenuBar</code>.
     *
     * @see java.awt.MenuBar
     * @see java.awt.peer.MenuBarPeer
     */
    protected MenuBarPeer createMenuBar(MenuBar target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Menu</code> using the specified peer interface.
     *
     * @param target the menu to be implemented.
     *
     * @return this toolkit's implementation of <code>Menu</code>.
     *
     * @see java.awt.Menu
     * @see java.awt.peer.MenuPeer
     */
    protected MenuPeer createMenu(Menu target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>PopupMenu</code> using the specified peer interface.
     *
     * @param target the popup menu to be implemented.
     *
     * @return this toolkit's implementation of <code>PopupMenu</code>.
     *
     * @see java.awt.PopupMenu
     * @see java.awt.peer.PopupMenuPeer
     * @since JDK1.1
     */
    protected PopupMenuPeer createPopupMenu(PopupMenu target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>MenuItem</code> using the specified peer interface.
     *
     * @param target the menu item to be implemented.
     *
     * @return this toolkit's implementation of <code>MenuItem</code>.
     *
     * @see java.awt.MenuItem
     * @see java.awt.peer.MenuItemPeer
     */
    protected MenuItemPeer createMenuItem(MenuItem target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>FileDialog</code> using the specified peer interface.
     *
     * @param target the file dialog to be implemented.
     *
     * @return this toolkit's implementation of <code>FileDialog</code>.
     *
     * @see java.awt.FileDialog
     * @see java.awt.peer.FileDialogPeer
     */
    protected FileDialogPeer createFileDialog(FileDialog target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>CheckboxMenuItem</code> using the specified peer interface.
     *
     * @param target the checkbox menu item to be implemented.
     *
     * @return this toolkit's implementation of <code>CheckboxMenuItem</code>.
     *
     * @see java.awt.CheckboxMenuItem
     * @see java.awt.peer.CheckboxMenuItemPeer
     */
    protected CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem target) {
        return null;
    }

    /**
     * Creates this toolkit's implementation of <code>Font</code> using the specified peer interface.
     *
     * @return this toolkit's implementation of <code>Font</code>.
     *
     * @see java.awt.Font
     * @see java.awt.peer.FontPeer
     * @see java.awt.GraphicsEnvironment#getAllFonts
     * @deprecated see java.awt.GraphicsEnvironment#getAllFonts
     */
    protected FontPeer getFontPeer(String name, int style) {
        return null;
    }

    /**
     * Gets the size of the screen.
     *
     * @return the size of this toolkit's screen, in pixels.
     */
    public Dimension getScreenSize() {
        return null;
    }

    /**
     * Returns the screen resolution in dots-per-inch.
     *
     * @return this toolkit's screen resolution, in dots-per-inch.
     */
    public int getScreenResolution() {
        return 0;
    }

    /**
     * Determines the color model of this toolkit's screen.
     * <p/>
     * <code>ColorModel</code> is an abstract class that encapsulates the ability to translate between the pixel values
     * of an image and its red, green, blue, and alpha components.
     * <p/>
     * This toolkit method is called by the <code>getColorModel</code> method of the <code>Component</code> class.
     *
     * @return the color model of this toolkit's screen.
     *
     * @see java.awt.image.ColorModel
     * @see java.awt.Component#getColorModel
     */
    public ColorModel getColorModel() {
        return null;
    }

    /**
     * Returns the names of the available fonts in this toolkit.<p> For 1.1, the following font names are deprecated
     * (the replacement name follows): <ul> <li>TimesRoman (use Serif) <li>Helvetica (use SansSerif) <li>Courier (use
     * Monospaced) </ul><p> The ZapfDingbats fontname is also deprecated in 1.1 but the characters are defined in
     * Unicode starting at 0x2700, and as of 1.1 Java supports those characters.
     *
     * @return the names of the available fonts in this toolkit.
     *
     * @see java.awt.GraphicsEnvironment#getAvailableFontFamilyNames()
     * @deprecated see {@link java.awt.GraphicsEnvironment#getAvailableFontFamilyNames()}
     */
    public String[] getFontList() {
        return new String[0];
    }

    /**
     * Gets the screen device metrics for rendering of the font.
     *
     * @param font a font.
     *
     * @return the screen metrics of the specified font in this toolkit.
     *
     * @see java.awt.font.LineMetrics
     * @see java.awt.Font#getLineMetrics
     * @see java.awt.GraphicsEnvironment#getScreenDevices
     * @deprecated This returns integer metrics for the default screen.
     */
    public FontMetrics getFontMetrics(Font font) {
        return null;
    }

    /**
     * Synchronizes this toolkit's graphics state. Some window systems may do buffering of graphics events.
     * <p/>
     * This method ensures that the display is up-to-date. It is useful for animation.
     */
    public void sync() {

    }

    /**
     * Returns an image which gets pixel data from the specified file, whose format can be either GIF, JPEG or PNG. The
     * underlying toolkit attempts to resolve multiple requests with the same filename to the same returned Image. Since
     * the mechanism required to facilitate this sharing of Image objects may continue to hold onto images that are no
     * longer of use for an indefinate period of time, developers are encouraged to implement their own caching of
     * images by using the createImage variant wherever available.
     *
     * @param filename the name of a file containing pixel data in a recognized file format.
     *
     * @return an image which gets its pixel data from the specified file.
     *
     * @see #createImage(String)
     */
    public Image getImage(String filename) {
        return null;
    }

    /**
     * Returns an image which gets pixel data from the specified URL. The pixel data referenced by the specified URL
     * must be in one of the following formats: GIF, JPEG or PNG. The underlying toolkit attempts to resolve multiple
     * requests with the same URL to the same returned Image. Since the mechanism required to facilitate this sharing of
     * Image objects may continue to hold onto images that are no longer of use for an indefinate period of time,
     * developers are encouraged to implement their own caching of images by using the createImage variant wherever
     * available.
     *
     * @param url the URL to use in fetching the pixel data.
     *
     * @return an image which gets its pixel data from the specified URL.
     *
     * @see #createImage(java.net.URL)
     */
    public Image getImage(URL url) {
        return null;
    }

    /**
     * Returns an image which gets pixel data from the specified file. The returned Image is a new object which will not
     * be shared with any other caller of this method or its getImage variant.
     *
     * @param filename the name of a file containing pixel data in a recognized file format.
     *
     * @return an image which gets its pixel data from the specified file.
     *
     * @see #getImage(String)
     */
    public Image createImage(String filename) {
        return null;
    }

    /**
     * Returns an image which gets pixel data from the specified URL. The returned Image is a new object which will not
     * be shared with any other caller of this method or its getImage variant.
     *
     * @param url the URL to use in fetching the pixel data.
     *
     * @return an image which gets its pixel data from the specified URL.
     *
     * @see #getImage(java.net.URL)
     */
    public Image createImage(URL url) {
        return null;
    }

    /**
     * Prepares an image for rendering.
     * <p/>
     * If the values of the width and height arguments are both <code>-1</code>, this method prepares the image for
     * rendering on the default screen; otherwise, this method prepares an image for rendering on the default screen at
     * the specified width and height.
     * <p/>
     * The image data is downloaded asynchronously in another thread, and an appropriately scaled screen representation
     * of the image is generated.
     * <p/>
     * This method is called by components <code>prepareImage</code> methods.
     * <p/>
     * Information on the flags returned by this method can be found with the definition of the
     * <code>ImageObserver</code> interface.
     *
     * @param image    the image for which to prepare a screen representation.
     * @param width    the width of the desired screen representation, or <code>-1</code>.
     * @param height   the height of the desired screen representation, or <code>-1</code>.
     * @param observer the <code>ImageObserver</code> object to be notified as the image is being prepared.
     *
     * @return <code>true</code> if the image has already been fully prepared; <code>false</code> otherwise.
     *
     * @see java.awt.Component#prepareImage(java.awt.Image, java.awt.image.ImageObserver)
     * @see java.awt.Component#prepareImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
     * @see java.awt.image.ImageObserver
     */
    public boolean prepareImage(Image image, int width, int height,
                                ImageObserver observer) {
        return false;
    }

    /**
     * Indicates the construction status of a specified image that is being prepared for display.
     * <p/>
     * If the values of the width and height arguments are both <code>-1</code>, this method returns the construction
     * status of a screen representation of the specified image in this toolkit. Otherwise, this method returns the
     * construction status of a scaled representation of the image at the specified width and height.
     * <p/>
     * This method does not cause the image to begin loading. An application must call <code>prepareImage</code> to
     * force the loading of an image.
     * <p/>
     * This method is called by the component's <code>checkImage</code> methods.
     * <p/>
     * Information on the flags returned by this method can be found with the definition of the
     * <code>ImageObserver</code> interface.
     *
     * @param image    the image whose status is being checked.
     * @param width    the width of the scaled version whose status is being checked, or <code>-1</code>.
     * @param height   the height of the scaled version whose status is being checked, or <code>-1</code>.
     * @param observer the <code>ImageObserver</code> object to be notified as the image is being prepared.
     *
     * @return the bitwise inclusive <strong>OR</strong> of the <code>ImageObserver</code> flags for the image data that
     *         is currently available.
     *
     * @see java.awt.Toolkit#prepareImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
     * @see java.awt.Component#checkImage(java.awt.Image, java.awt.image.ImageObserver)
     * @see java.awt.Component#checkImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
     * @see java.awt.image.ImageObserver
     */
    public int checkImage(Image image, int width, int height,
                          ImageObserver observer) {
        return 0;
    }

    /**
     * Creates an image with the specified image producer.
     *
     * @param producer the image producer to be used.
     *
     * @return an image with the specified image producer.
     *
     * @see java.awt.Image
     * @see java.awt.image.ImageProducer
     * @see java.awt.Component#createImage(java.awt.image.ImageProducer)
     */
    public Image createImage(ImageProducer producer) {
        return null;
    }

    /**
     * Creates an image which decodes the image stored in the specified byte array, and at the specified offset and
     * length. The data must be in some image format, such as GIF or JPEG, that is supported by this toolkit.
     *
     * @param imagedata   an array of bytes, representing image data in a supported image format.
     * @param imageoffset the offset of the beginning of the data in the array.
     * @param imagelength the length of the data in the array.
     *
     * @return an image.
     *
     * @since JDK1.1
     */
    public Image createImage(byte[] imagedata, int imageoffset,
                             int imagelength) {
        return null;
    }

    /**
     * Gets a <code>PrintJob</code> object which is the result of initiating a print operation on the toolkit's
     * platform.
     * <p/>
     * Each actual implementation of this method should first check if there is a security manager installed. If there
     * is, the method should call the security manager's <code>checkPrintJobAccess</code> method to ensure initiation of
     * a print operation is allowed. If the default implementation of <code>checkPrintJobAccess</code> is used (that is,
     * that method is not overriden), then this results in a call to the security manager's <code>checkPermission</code>
     * method with a <code> RuntimePermission("queuePrintJob")</code> permission.
     *
     * @param frame    the parent of the print dialog. May not be null.
     * @param jobtitle the title of the PrintJob. A null title is equivalent to "".
     * @param props    a Properties object containing zero or more properties. Properties are not standardized and are
     *                 not consistent across implementations. Because of this, PrintJobs which require job and page
     *                 control should use the version of this function which takes JobAttributes and PageAttributes
     *                 objects. This object may be updated to reflect the user's job choices on exit. May be null.
     *
     * @return a <code>PrintJob</code> object, or <code>null</code> if the user cancelled the print job.
     *
     * @throws NullPointerException if frame is null
     * @throws SecurityException    if this thread is not allowed to initiate a print job request
     * @see java.awt.PrintJob
     * @see RuntimePermission
     * @since JDK1.1
     */
    public PrintJob getPrintJob(Frame frame, String jobtitle, Properties props) {
        return null;
    }

    /**
     * Emits an audio beep.
     *
     * @since JDK1.1
     */
    public void beep() {

    }

    /**
     * Gets the singleton instance of the system Clipboard which interfaces with clipboard facilities provided by the
     * native platform. This clipboard enables data transfer between Java programs and native applications which use
     * native clipboard facilities.
     * <p/>
     * In addition to any and all formats specified in the flavormap.properties file, or other file specified by the
     * <code>AWT.DnD.flavorMapFileURL </code> Toolkit property, text returned by the system Clipboard's <code>
     * getTransferData()</code> method is available in the following flavors: <ul> <li>DataFlavor.stringFlavor</li>
     * <li>DataFlavor.plainTextFlavor (<b>deprecated</b>)</li> </ul> As with <code>java.awt.datatransfer.StringSelection</code>,
     * if the requested flavor is <code>DataFlavor.plainTextFlavor</code>, or an equivalent flavor, a Reader is
     * returned. <b>Note:</b> The behavior of the system Clipboard's <code>getTransferData()</code> method for <code>
     * DataFlavor.plainTextFlavor</code>, and equivalent DataFlavors, is inconsistent with the definition of
     * <code>DataFlavor.plainTextFlavor </code>. Because of this, support for <code> DataFlavor.plainTextFlavor</code>,
     * and equivalent flavors, is <b>deprecated</b>.
     * <p/>
     * Each actual implementation of this method should first check if there is a security manager installed. If there
     * is, the method should call the security manager's <code>checkSystemClipboardAccess</code> method to ensure it's
     * ok to to access the system clipboard. If the default implementation of <code>checkSystemClipboardAccess</code> is
     * used (that is, that method is not overriden), then this results in a call to the security manager's
     * <code>checkPermission</code> method with an <code> AWTPermission("accessClipboard")</code> permission.
     *
     * @return the system Clipboard
     *
     * @see java.awt.datatransfer.Clipboard
     * @see java.awt.datatransfer.StringSelection
     * @see java.io.Reader
     * @see java.awt.AWTPermission
     * @since JDK1.1
     */
    public Clipboard getSystemClipboard() {
        return null;
    }

    /*
     * Get the application's or applet's EventQueue instance, without
     * checking access.  For security reasons, this can only be called
     * from a Toolkit subclass.  Implementations wishing to modify
     * the default EventQueue support should subclass this method.
     */
    protected EventQueue getSystemEventQueueImpl() {
        return null;
    }

    /**
     * create the peer for a DragSourceContext
     */
    public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge) throws InvalidDnDOperationException {
        return null;
    }

    /**
     * Returns a map of visual attributes for the abstract level description of the given input method highlight, or
     * null if no mapping is found. The style field of the input method highlight is ignored. The map returned is
     * unmodifiable.
     *
     * @param highlight input method highlight
     *
     * @return style attribute map, or null
     *
     * @since 1.3
     */
    public Map mapInputMethodHighlight(InputMethodHighlight highlight) {
        return null;
    }

}

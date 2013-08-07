/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.jmx;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.ManageableCaptchaService;


/**
 * Helper that providdes methods to register and unregister a ManageableCaptchaService to a MBean Server.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class JMXRegistrationHelper {
//    /**
//     * Register self to the first MBean server available in the JVM, if
//     * any.
//     *
//     * @param name the name the service will be registered
//     *             to the MBean server.
//     * @throws com.octo.captcha.service.CaptchaServiceException
//     *          in case of error. Possible
//     *          error details are :
//     *          <ul>
//     *          <li> CaptchaServiceException</li>
//     *          </ul>
//     * @see com.octo.captcha.service.CaptchaServiceException
//     */

    public static void registerToMBeanServer(ManageableCaptchaService service, String name)
            throws CaptchaServiceException {
//        if (name == null) throw new CaptchaServiceException("Service registration name can't be null");
//        ArrayList mbeanServers = MBeanServerFactory.findMBeanServer(null);
//        if (mbeanServers.size() == 0) {
//            throw new CaptchaServiceException("No current MBean Server, skiping the registering process");
//        } else {
//            MBeanServer mbeanServer = (MBeanServer) mbeanServers.get(0);
//            try {
//                ObjectName objectName = new ObjectName(name);
//                mbeanServer.registerMBean(service, objectName);
//            } catch (MalformedObjectNameException e) {
//                throw new CaptchaServiceException(e);
//            } catch (InstanceAlreadyExistsException e) {
//                throw new CaptchaServiceException(e);
//            } catch (MBeanRegistrationException e) {
//                // this exception should never be raised (raised
//                // only by an MBean that implements the MBeanRegistration
//                // interface.
//                throw new CaptchaServiceException("An unexpected exception has been raised : "
//                        + "CaptchaService needs maintenance !",
//                        e);
//            } catch (NotCompliantMBeanException e) {
//                // this should never happens
//                throw new CaptchaServiceException("Exception trying to register the service to"
//                        + " the MBean server",
//                        e);
//            }
//        }
    }
//
//    /**
//     * Unregister self from the first MBean server available in the JVM, if any
//     */

    public static void unregisterFromMBeanServer(String name) {

//        if (name != null) {
//            ArrayList mbeanServers = MBeanServerFactory.findMBeanServer(null);
//            MBeanServer mbeanServer = (MBeanServer) mbeanServers.get(0);
//            try {
//                ObjectName objectName = new ObjectName(name);
//                mbeanServer.unregisterMBean(objectName);
//            } catch (MalformedObjectNameException e) {
//                // this should never happens
//                throw new CaptchaServiceException("Exception trying to create the object name under witch"
//                        + " the service is registered",
//                        e);
//            } catch (InstanceNotFoundException e) {
//                // this should never happens
//                throw new CaptchaServiceException("Exception trying to unregister the ImageCaptchaFilter from"
//                        + " the MBean server",
//                        e);
//            } catch (MBeanRegistrationException e) {
//                // this remains silent for the client
//                throw new CaptchaServiceException("Exception trying to unregister the ImageCaptchaFilter from"
//                        + "the MBean server",
//                        e);
//            }
//        } else {
//            throw new CaptchaServiceException("Service registration name can't be null");
//        }
    }
//

}

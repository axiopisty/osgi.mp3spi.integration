# osgi.mp3spi.integration

As explained in the [Apache Aries SPI Fly documentation](http://aries.apache.org/modules/spi-fly.html):

> **The Problem**
> 
> Java's java.util.ServiceLoader.load(), other similar methods such as sun.misc.Service.providers() and also other static finder methods such as the FactoryFinder.find() methods try to locate 'service' implementations by looking for resources in the META-INF/services directory of all the jars visible to the Thread Context ClassLoader (TCCL).
> 
> There are a number of issues with the above mechanisms when used in OSGi:
> 
>   1. The Thread Context ClassLoader is not defined in general in an OSGi context. It can and has to be set by the caller and OSGi cannot generally enforce that.
>   1. A bundle can't Import-Package META-INF/services as potentially many bundles will contain this pseudo-package and the OSGi framework will only bind a single exporter to an importer for a given package.
>   1. Instantiating an SPI provider generally requires access to internal implementation classes, by exporting these classes an implementing bundle would break its encapsulation.
>   1. Even if an implementation class was exported, importing this class in a consumer bundle would bind it to the specific implementation package provided, which violates the principle of loose coupling.
>   1. Bundles have a dynamic life-cycle which means that provided services could disappear when a bundle is updated or uninstalled. The java.util.ServiceLoader API does not provide a mechanism to inform service consumers of such an event.
> 
> The SPI Fly project makes it possible to use existing code that uses ServiceLoader.load() and similar mechanisms under OSGi.
> 
> **Making it Work**
> 
> In order to make ServiceLoader (and other similar SPI or plugin mechanisms) work under OSGi, calling code can be woven to set the TCCL to the appropriate providers very briefly, only for the duration of the call. The SPI Fly component does precisely this.
> 
> SPI Fly supports two modes of operation:
> 
>   1. OSGi Specification compliant configuration. This is based on OSGi generic requirements and capabilities and provides portability across implementations of this specification. However, it only covers java.util.ServiceLoader. Find it here.
>   1. If you need to handle cases other than java.util.ServiceLoader, such as the various FactoryFinders, javax.imageio.spi.ServiceRegistry, javax.sound.sampled.AudioSystem or others that use the TCCL to find an implementation, you can use the SPI Fly-specific configuration.
> 
> Additionally, services found in the META-INF/services location in opted-in bundles will be registered in the OSGi Service Registry so that OSGi-aware consumers can simply find them there. This is supported in both the spec-compliant as well as the proprietary configuration modes. Each such service is registered in the OSGi Service Registry with the serviceloader.mediator service registration property set.


This project demonstrates how to "Make it Work" by showing how to play an MP3 
file inside an OSGi container using the Java Sound API.

To see an example of ***dynamic weaving*** checkout the `dynamic-weaving` branch.
To see an example of ***static weaving*** checkout the `static-weaving` branch.

On either of these branches you can execute the `deploy.sh` script to build the 
OSGi bundles that use the Java Sound API, deploy them inside Felix, and start
the OSGi container. Running this script launches the example application which
is a simple Java FX GUI that allows you to select an MP3 file to play. To
stop the application just stop bundle 0 from the gogo shell.

    g! stop 0



[![Build Status](https://travis-ci.org/axiopisty/osgi.mp3spi.integration.svg?branch=master)](https://travis-ci.org/axiopisty/osgi.mp3spi.integration)

# SpringContextFactory (English description)

**com.isban.rtp.naming.SpringContextFactory** It's an implementation of javax.naming.spi.InitialContextFactory which allows to get objects from a JNDI context (lookup method) by using a Spring context. This class allows to apply the same Spring context configuration in many places (it has been tested with Flume and JMeter) and simplifies the way you connect JMeter to MQ due to it's not neccesary to use .bindings (see the JMeter example project). 

The mapping between JNDI and Spring context is as follows:
 + Initial Context Factory: com.isban.rtp.naming.SpringContextFactory.
 + URL Provider: Pathe to the Spring context file.
 + Connection Factory: Name of the bean representing a Connection Factory object inside the Spring context.
 + Destination: Name of the bean representing a Destination object inside the Spring context.

**JMeter Use Case**: The file spring-util-jmeter-howto.jmx is a sample of using com.isban.rtp.naming.SpringContextFactory with JMeter. The sample Spring context (jms-spring-mq.xml) contains objects used for connecting to a MQ instance.

Required libraries:
 + spring-beans
 + spring-context
 + spring-core
 + spring-jms
 + spring-tx
 (JMeter tested version 2.5.6)
        
To avoid keeping open connections after executions (for example: after JMeter project executions) is needed to add some special beans to the Spring Context:
 + com.isban.rtp.naming.FinishMethodsByClass: Declares what methods of a particular class must be called when the context is closed.
 + com.isban.rtp.naming.FinishMethodsByBean: Links single objects to FinishMethodsByClass beans. By doing it so, it's not needed to declare what methods have to be called for each single object, they just have to reference the group (FinishMethodsByClass) they belong to.

SpringContextFactory leverages this information to accomplish its close method. 
    
----------------------------------------------------------------        

# SpringContextFactory (Spanish description)

**com.isban.rtp.naming.SpringContextFactory** Es una implementación del interfaz javax.naming.spi.InitialContextFactory que permite recuperar los objetos del contexto (método lookup) a partir de un contexto de Spring. Esta clase permite aplicar la misma configuration de contexto de Spring en múltiples sitios (ha sido testeada con Flume y JMeter) y simplifica la manera en la que conectar JMeter a MQ dado que no es necesario el uso del .bindings (ver el proyecto JMeter de ejemplo).

El mapeo entre propiedades del contexto y Spring es el siguiente:
 + Initial Context Factory: com.isban.rtp.naming.SpringContextFactory.
 + URL Provider: Ubicación del fichero de contexto Spring.
 + Connection Factory: Nombre del bean que representa el objecto Connection Factory en el contexto de Spring.
 + Destination: Nombre del bean que representa el objeto Destination en el contexto de Spring.

**Uso con JMeter**: El fichero spring-util-jmeter-howto.jmx es un ejemplo de cómo utilizar com.isban.rtp.naming.SpringContextFactory en JMeter. El contexto de ejemplo de Spring (jms-spring-mq.xml) contiene objetos utilizados para conectarse a una instancia de MQ.

Librerías requeridas:
 + spring-beans
 + spring-context
 + spring-core
 + spring-jms
 + spring-tx
 (Pruebas realizadas con la versión 2.5.6)

Para evitar mantener conexiones abiertas después de haber utilizado el contexto de Spring (por ejemplo: después de haber ejecutado el proyecto de JMeter) es necesario añadir al contexto de Spring los siguientes beans:
 + com.isban.rtp.naming.FinishMethodsByClass: Declara qué métodos de una clase concreta han de ser invocados cuando se cierra el contexto.
 + com.isban.rtp.naming.FinishMethodsByBean: Relaciona objetos con beans del tipo FinishMethodsByClass. De esta manera, no es necesario declarar qué métodos de cierre han de ser invocados para un bean concreto, basta con referenciar el grupo (FinishMethodsByClass) al que pertenecen.

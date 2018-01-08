# analiseGenoma
Sistema para analise de genoma

1  - Install webserver
=========
mkdir /java/src

cd /java/src

Download do wildfly-10.1.0.Final

wget http://download.jboss.org/wildfly/11.0.0.Final/wildfly-11.0.0.Final.tar.gz

mkdir /java/servers

cd /java/servers

tar -xvzf wildfly-11.0.0.Final.tar.gz

vim /java/servers/wildfly/standalone/configuration/standalone-full.xml


            <datasources>
                <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
                    <driver>h2</driver>
                    <security>
                        <user-name>sa</user-name>
                        <password>sa</password>
                    </security>
                </datasource>
                <datasource jndi-name="java:jboss/datasources/analiseGenomaDS" pool-name="analiseGenomaDS">
                    <connection-url>jdbc:mysql://localhost:3306/analise_genoma</connection-url>
                    <connection-property name="DatabaseName">
                        analise_genoma
                    </connection-property>
                    <driver>mysql</driver>
                    <pool>
                        <min-pool-size>10</min-pool-size>
                        <max-pool-size>20</max-pool-size>
                    </pool>
                    <security>
                        <user-name>usuario</user-name>
                        <password>senha</password>
                    </security>
                </datasource>
                <drivers>
                    <driver name="h2" module="com.h2database.h2">
                        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
                    </driver>
                    <driver name="mysql" module="com.mysql">
                        <datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlDataSource</datasource-class>
                    </driver>
                </drivers>
            </datasources>

...
<http-listener name="default" socket-binding="http" max-post-size="974247881" max-header-size="974247881" redirect-socket="https" enable-http2="true"/>



mkdir -p /java/servers/wildfly/modules/com/mysql/main
cd /java/servers/wildfly/modules/com/mysql/main
wget mysql-connector-java-5.1.38.jar
vim module.xml
<?xml version='1.0' encoding='UTF-8'?>

<module xmlns="urn:jboss:module:1.1" name="com.mysql">

    <resources>
        <resource-root path="mysql-connector-java-5.1.38.jar"/>
    </resources>

    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>




